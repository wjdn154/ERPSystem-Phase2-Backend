package com.megazone.ERPSystem_phase2_Backend.hr.controller.basic_information_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant.SchemaBasedMultiTenantConnectionProvider;
import com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant.TenantContext;
import com.megazone.ERPSystem_phase2_Backend.common.config.multi_tenant.TenantService;
import com.megazone.ERPSystem_phase2_Backend.common.config.security.AuthRequest;
import com.megazone.ERPSystem_phase2_Backend.common.config.security.CustomUserDetails;
import com.megazone.ERPSystem_phase2_Backend.common.config.security.JwtUtil;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.Company;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.dto.*;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company.CompanyRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Permission;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.*;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Permission.PermissionRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Users.UsersService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;
import org.hibernate.tool.schema.internal.SchemaCreatorImpl;

import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr")
public class UsersController {


    private final UsersService usersService;
    private final UsersRepository usersRepository;
    private final PermissionRepository permissionRepository;
    private final CompanyRepository companyRepository;


    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;
    private final SchemaBasedMultiTenantConnectionProvider multiTenantConnectionProvider;
    private final PlatformTransactionManager transactionManager;



    // 로그인
    @PostMapping("/auth/login")
    public ResponseEntity<Object> createAuthenticationToken(@RequestBody AuthRequest authRequest) {

        // 회사 선택 검증
        if(authRequest.getCompanyId() == null) return ResponseEntity.badRequest().body("회사를 선택해주세요.");

        // 테넌트 식별자 설정
        String tenantId = "tenant_" + authRequest.getCompanyId();

        // 테넌트 컨텍스트 설정
        TenantContext.setCurrentTenant(tenantId);

        // 사용자 인증 & 토큰 생성
        ResponseEntity<Object> authenticationToken = usersService.createAuthenticationToken(authRequest, tenantId);
        if (authenticationToken.getStatusCode() != HttpStatus.OK) return authenticationToken;

        // 사용자 권한 조회
        ResponseEntity<Object> permissionByUsername = usersService.getPermissionByUsername(authRequest.getUserName());
        if (permissionByUsername.getStatusCode() != HttpStatus.OK) return authenticationToken;

        Company company = companyRepository.findById(authRequest.getCompanyId()).orElse(null);
        if (company == null) return ResponseEntity.badRequest().body("회사를 찾을 수 없습니다.");
        System.out.println("authRequest.getUserName() = " + authRequest.getUserName());
        boolean isAdmin = company.getAdminUsername().equals(authRequest.getUserName());
        System.out.println("isAdmin = " + isAdmin);

        Map<Object, Object> response = new HashMap<>();
        response.put("token", authenticationToken.getBody());
        response.put("permission", permissionByUsername.getBody());
        response.put("isAdmin", isAdmin);

        // 테넌트 컨텍스트 해제
        TenantContext.clear();

        return ResponseEntity.ok(response);
    }

    // 회원가입
    @PostMapping("/auth/register")
    public ResponseEntity<String> registerUser(@RequestBody AuthRequest authRequest) throws SQLException {

        // 회사 선택 검증
        if(authRequest.getCompanyId() == null) return ResponseEntity.badRequest().body("회사를 선택해주세요.");

        // 테넌트 식별자 설정
        TenantContext.setCurrentTenant("tenant_" + authRequest.getCompanyId());

        // 사용자 등록
        ResponseEntity<String> tenantResponse = usersService.registerUser(authRequest);

        // 테넌트 컨텍스트 해제
        TenantContext.clear();

        return tenantResponse;
    }

    // 사용자 권한 조회
    @PostMapping("/users/permission/{username}")
    public ResponseEntity<Object> getPermissionByUsername(@PathVariable("username") String username) {
        return usersService.getPermissionByUsername(username);
    }

    // 사용자 권한 수정
    @PostMapping("/users/permission/update")
    public ResponseEntity<Object> assignPermissionToUser(@RequestBody UserPermissionUpdateRequestDTO request) {
        return usersService.assignPermissionToUser(
                request.getUsername(),
                request.getPermissionDTO()
        );
    }











//    @PostMapping("/{userId}/assign-permission/{permissionId}")
//    public ResponseEntity<UsersPermissionDTO> assignPermissionToUser(@PathVariable Long userId, @PathVariable Long permissionId) {
//        UsersPermissionDTO usersPermissionDTO = usersService.assignPermissionToUser(userId, permissionId);
//        return usersPermissionDTO != null ? ResponseEntity.ok(usersPermissionDTO) : ResponseEntity.notFound().build();
//    }

    /**
     * 모든 사용자 정보를 가져옴.
     *
     * @return 모든 사용자 정보를 담은 리스트를 반환함.
     */
    @PostMapping("/users/all")
    public ResponseEntity<List<UsersShowDTO>> getAllUsers() {
        List<UsersShowDTO> users = usersService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * ID로 특정 사용자를 조회함.
     *
     * @param id 조회할 사용자의 ID
     * @return 조회된 사용자 정보를 반환함.
     */
    @PostMapping("/users/{id}")
    public ResponseEntity<UsersShowDTO> getUserById(@PathVariable Long id) {
        UsersShowDTO user = usersService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * 사용자를 수정함.
     *
     * @param id       수정할 사용자의 ID
     * @param usersDTO 수정할 사용자 정보
     * @return 수정된 사용자 정보를 반환함.
     */
    @PutMapping("/users/put/{id}")
    public ResponseEntity<UsersShowDTO> updateUser(@PathVariable Long id, @RequestBody UsersShowDTO usersDTO) {
        UsersShowDTO updatedUser = usersService.updateUser(id, usersDTO);
        return ResponseEntity.ok(updatedUser);
    } // 다시 해야 됨

    /**
     * ID로 사용자를 삭제함.
     *
     * @param id 삭제할 사용자의 ID
     * @return 삭제 성공 메시지를 반환함.
     */
    @DeleteMapping("/users/del/{id}")
    public ResponseEntity<String> deleteUsers(@PathVariable Long id) {
        usersService.deleteUsers(id);
        return ResponseEntity.ok("사용자 삭제되었습니다.");
    }
}

