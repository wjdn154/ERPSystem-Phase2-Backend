package com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Users;

import com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant.SchemaBasedMultiTenantConnectionProvider;
import com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant.TenantContext;
import com.megazone.ERPSystem_phase2_Backend.common.config.security.AuthRequest;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company.CompanyRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Permission;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.UsersPermissionDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.UsersShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.enums.UserPermission;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Permission.PermissionRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class UsersServiceImpl implements UsersService{

    private final UsersRepository usersRepository;
    private final PermissionRepository permissionRepository;
    private final CompanyRepository companyRepository;
    //private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final SchemaBasedMultiTenantConnectionProvider multiTenantConnectionProvider;
    private final JdbcTemplate jdbcTemplate;


    @Override
    public ResponseEntity<String> registerUser(AuthRequest authRequest) throws SQLException {

        // 이메일 형식 검증 정규식
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

        // 이메일 형식 검증
        if (!pattern.matcher(authRequest.getUserName()).matches()) throw new IllegalArgumentException("잘못된 이메일 형식입니다.");

        // 유저 검증
        if (usersRepository.findByUserName(authRequest.getUserName()).isPresent()) {
            throw new RuntimeException("이미 존재하는 사용자입니다.");
        }

        // 테넌트 스키마에 저장할 사용자 생성
        Users tenantUser = new Users();
        tenantUser.setUserName(authRequest.getUserName());
        tenantUser.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        tenantUser.setPermission(new Permission());
        tenantUser.setCompany(companyRepository.findById(authRequest.getCompanyId())
                .orElseThrow(() -> new RuntimeException("회사 정보를 찾을 수 없습니다.")));
        tenantUser.setUserNickname(authRequest.getUserNickname());

        usersRepository.save(tenantUser);

        return ResponseEntity.ok("사용자 등록 완료 - 테넌트: tenant_" + authRequest.getCompanyId());
    }



























    public Optional<Users> getUserById(Long id) {
        return usersRepository.findById(id);
    }

    public Optional<Users> getUserByUsersName(String usersName) {
        return usersRepository.findByUserName(usersName);
    }

    public Users updateUser(Long id, Users user) {
        if (usersRepository.existsById(id)) {
            user.setId(id);
            return usersRepository.save(user);
        }
        return null;
    }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }


    public UsersPermissionDTO assignPermissionToUser(Long userId, Long permissionId) {
        Optional<Users> userOpt = usersRepository.findById(userId);
        Optional<Permission> permissionOpt = permissionRepository.findById(permissionId);

        if (userOpt.isPresent() && permissionOpt.isPresent()) {
            Users user = userOpt.get();
            Permission permission = permissionOpt.get();
            user.setPermission(permission);
            Users savedUser = usersRepository.save(user);

            return convertTodDTO(savedUser);  // Convert Users to UsersShowDTO
        }
        return null;
    }
    private UsersPermissionDTO convertTodDTO(Users user) {
        UsersPermissionDTO dto = new UsersPermissionDTO();
        dto.setId(user.getId());
        dto.setUserName(user.getUserNickname());
        dto.setUsersId(user.getUserName());

        // Check if Employee is not null
        if (user.getEmployee() != null) {
            dto.setEmployeeId(user.getEmployee().getId());
            dto.setEmployeeNumber(user.getEmployee().getEmployeeNumber());
            dto.setEmployeeName(user.getEmployee().getFirstName());
            dto.setEmployeeName(user.getEmployee().getLastName());
        }

        dto.setPermissionId(user.getPermission() != null ? user.getPermission().getId() : null);
        return dto;
    }
    // 권한을 설정하거나 업데이트하는 메서드
//    public UsersShowDTO assignPermissionToUser(Long userId, Long permissionId) {
//        Optional<Users> userOpt = usersRepository.findById(userId);
//        Optional<Permission> permissionOpt = permissionRepository.findById(permissionId);
//
//        if (userOpt.isPresent() && permissionOpt.isPresent()) {
//            Users user = userOpt.get();
//            Permission permission = permissionOpt.get();
//            user.setPermission(permission);
//            return usersRepository.save(user);
//        }
//        return null;
//    }
    public List<UsersShowDTO> findAllUsers() {
        return usersRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private UsersShowDTO convertToDTO(Users users) {
        UsersShowDTO dto = new UsersShowDTO();
        dto.setId(users.getId());
        dto.setUsersId(users.getUserName());
        dto.setUserName(users.getUserNickname());
        dto.setEmployeeNumber(users.getEmployee().getEmployeeNumber());
        dto.setFirstName(users.getEmployee().getFirstName());
        dto.setLastName(users.getEmployee().getLastName());
        dto.setPassword(users.getPassword());
        dto.setPermissionId(dto.getPermissionId());


        //dto.setEmployeeId(users.getEmployee() != null ? users.getEmployee().getId() : null);


        return dto;
    }

    @Override
    public UsersShowDTO findUserById(Long id) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user);
    }

    @Override
    public UsersShowDTO updateUser(Long id, UsersShowDTO usersDTO) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setUserName(usersDTO.getUserName());
        Users updatedUser = usersRepository.save(user);
        return convertToDTO(updatedUser);

    }

    @Override
    public void deleteUsers(Long id) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        usersRepository.delete(user);
    }




//    @Override
//    public UsersResponseDTO assignRoleToUser(Long userId, Long roleId) {
//        // 사용자와 역할을 조회
//        Users user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//
//        UsersResponseDTO responseDTO = new UsersResponseDTO();
//        responseDTO.setId(user.getId());
//        responseDTO.setUsername(user.getUserName());
//        // 사용자의 역할 목록에
//        // 새로운 역할 추가
//
//
//        // 사용자 정보를 업데이트
//        usersRepository.save(user);
//
//        return responseDTO;
//    }



}
