package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Users;

import com.megazone.ERPSystem_phase2_Backend.Integrated.model.dashboard.RecentActivity;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.dashboard.enums.ActivityType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.ModuleType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.NotificationType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.PermissionType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.repository.dashboard.RecentActivityRepository;
import com.megazone.ERPSystem_phase2_Backend.Integrated.service.notification.NotificationService;
import com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant.SchemaBasedMultiTenantConnectionProvider;
import com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant.TenantContext;
import com.megazone.ERPSystem_phase2_Backend.common.config.security.AuthRequest;
import com.megazone.ERPSystem_phase2_Backend.common.config.security.CustomUserDetails;
import com.megazone.ERPSystem_phase2_Backend.common.config.security.JwtUtil;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company.CompanyRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Permission;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.PermissionDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.UsersPermissionDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.UsersShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.UserPermission;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Permission.PermissionRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class UsersServiceImpl implements UsersService{

    private final UsersRepository usersRepository;
    private final PermissionRepository permissionRepository;
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    //private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final SchemaBasedMultiTenantConnectionProvider multiTenantConnectionProvider;
    private final JdbcTemplate jdbcTemplate;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final RecentActivityRepository recentActivityRepository;
    private final NotificationService notificationService;


    @Override
    public ResponseEntity<Object> createAuthenticationToken(AuthRequest authRequest, String tenantId) {
        // 이메일 형식 검증 정규식
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

        // 이메일 형식 검증
        if (!pattern.matcher(authRequest.getUserName()).matches()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 이메일 형식입니다.");
        }

        // 사용자 정보 가져오기
        Users user = usersRepository.findByUserName(authRequest.getUserName()).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        if (user.getCompany() == null) {
            user.setCompany(companyRepository.findById(authRequest.getCompanyId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "회사 정보를 찾을 수 없습니다.")));
            usersRepository.save(user);
        }

        try {
            // 사용자 인증
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 정보가 올바르지 않습니다.");
        }

        // Users를 CustomUserDetails로 변환
        UserDetails userDetails = new CustomUserDetails(user);

        // JWT 토큰 생성
        String jwtToken = jwtUtil.generateToken(tenantId, userDetails.getUsername(), user.getUserNickname(),
                user.getCompany().getId(), user.getEmployee().getId(), user.getPermission().getId());

        return ResponseEntity.ok(jwtToken);
    }

    @Override
    public ResponseEntity<Object> createRefreshToken(Map<String, String> refreshTokenRequest) {

        String refreshToken = refreshTokenRequest.get("refreshToken");
        if (!jwtUtil.validateRefreshToken(refreshToken)) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("리프레시 토큰이 유효하지 않음.");

        String tenantId = jwtUtil.extractTenantId(refreshToken);
        String username = jwtUtil.extractUsername(refreshToken);
        String userNickname = jwtUtil.extractUserNickname(refreshToken);
        Long companyId = jwtUtil.extractCompanyId(refreshToken);
        Long permissionId = jwtUtil.extractPermissionId(refreshToken);
        Long employeeId = jwtUtil.extractEmployeeId(refreshToken);

        Map<String, String> response = new HashMap<>();
        String jwtToken = jwtUtil.generateToken(tenantId, username, userNickname, companyId, employeeId, permissionId);
        response.put("token", jwtToken);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<String> registerUser(AuthRequest authRequest) throws SQLException {

        // 이메일 형식 검증 정규식
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

        // 이메일 형식 검증
        if (!pattern.matcher(authRequest.getUserName()).matches()) return ResponseEntity.badRequest().body("잘못된 이메일 형식입니다.");

        // 유저 검증
        if (usersRepository.findByUserName(authRequest.getUserName()).isPresent()) return ResponseEntity.badRequest().body("이미 존재하는 사용자입니다.");

        Employee employee = employeeRepository.findByEmail(authRequest.getUserName()).orElse(null);
        if(employee == null) return ResponseEntity.badRequest().body("사원 정보를 찾을 수 없습니다.");

        // 테넌트 스키마에 저장할 사용자 생성
        Users tenantUser = new Users();
        tenantUser.setUserName(authRequest.getUserName());
        tenantUser.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        tenantUser.setPermission(new Permission());
        tenantUser.setCompany(companyRepository.findById(authRequest.getCompanyId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "회사 정보를 찾을 수 없습니다.")));
        tenantUser.setEmployee(employee);
        tenantUser.setUserNickname(authRequest.getUserNickname());

        usersRepository.save(tenantUser);

        return ResponseEntity.ok("사용자 등록 완료 - 테넌트: tenant_" + authRequest.getCompanyId());
    }

    @Override
    public ResponseEntity<Object> getPermissionByUsername(String username) {

        Users users = usersRepository.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        Permission permission = users.getPermission();
        if (permission == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("권한을 조회 할 수 없습니다.");

        ModelMapper modelMapper = new ModelMapper();
        PermissionDTO permissionDTO = modelMapper.map(permission, PermissionDTO.class);

        return ResponseEntity.ok(permissionDTO);
    }

    public ResponseEntity<Object> assignPermissionToUser(String username, PermissionDTO permissionDTO) {
        // 사용자 검증
        Users user = usersRepository.findByUserName(username).orElse(null);
        if (user == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("사용자를 찾을 수 없습니다.");

        // 매핑
        ModelMapper modelMapper = new ModelMapper();
        Permission permission = modelMapper.map(permissionDTO, Permission.class);

        // 권한 저장
        user.setPermission(permission);
        Users savedUser = usersRepository.save(user);

        recentActivityRepository.save(RecentActivity.builder()
                .activityDescription(savedUser.getUserNickname() + "님의 권한이 변경되었습니다.")
                .activityType(ActivityType.HR)
                .activityTime(LocalDateTime.now())
                .build());
        notificationService.createAndSendNotification(
                ModuleType.ALL,
                PermissionType.ALL,
                savedUser.getUserNickname() + "님의 권한이 변경되었습니다.",
                NotificationType.CHANGE_PERMISSION
        );


        return ResponseEntity.ok(modelMapper.map(savedUser.getPermission(), PermissionDTO.class));
    }























    public List<UsersShowDTO> findAllUsers() {
        // 모든 Users 엔티티를 조회하고, 각각을 UsersShowDTO로 변환
        return usersRepository.findAll()
                .stream()
                .map(UsersShowDTO::toDTO)
                .collect(Collectors.toList());
    }



    private UsersShowDTO convertToDTO(Users users) {
        UsersShowDTO dto = new UsersShowDTO();
        dto.setId(users.getId());
        dto.setUserName(users.getUserName());
        dto.setUserNickname(users.getUserNickname());
        dto.setEmployeeNumber(users.getEmployee().getEmployeeNumber());
        dto.setEmployeeName(users.getEmployee().getLastName() + users.getEmployee().getFirstName());
        dto.setPassword(users.getPassword());
        dto.setPermissionId(dto.getPermissionId());

        return dto;
    }

    @Override
    public UsersShowDTO findUserById(Long id) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return convertToDTO(user);
    }

    @Override
    public UsersShowDTO updateUser(Long id, UsersShowDTO usersDTO) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        user.setUserNickname(usersDTO.getUserNickname());
        user.setPassword(usersDTO.getPassword());
        user.setUserName(usersDTO.getUserName());
        Users updatedUser = usersRepository.save(user);
        return convertToDTO(updatedUser);

    }

    @Override
    public void deleteUsers(Long id) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        usersRepository.delete(user);
    }



}
