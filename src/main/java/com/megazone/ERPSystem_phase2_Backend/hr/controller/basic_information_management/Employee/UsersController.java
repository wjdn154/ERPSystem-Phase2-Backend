package com.megazone.ERPSystem_phase2_Backend.hr.controller.basic_information_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.common.config.security.AuthRequest;
import com.megazone.ERPSystem_phase2_Backend.common.config.security.CustomUserDetails;
import com.megazone.ERPSystem_phase2_Backend.common.config.security.JwtUtil;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.company.CompanyRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Permission;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.UsersPermissionDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.UsersResponseDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.UsersShowDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr")
public class UsersController {


    private final UsersService usersService;
    private final UsersRepository usersRepository;
    private final CompanyRepository companyRepository;

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;



    @PostMapping("/auth/login")
    public String createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        // 사용자 인증
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
        );

        // 사용자 정보 가져오기
        Users user = usersRepository.findByUserName(authRequest.getUserName())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // Users를 CustomUserDetails로 변환
        UserDetails userDetails = new CustomUserDetails(user);

        // JWT 토큰 생성
        return jwtUtil.generateToken(userDetails.getUsername(), user.getUserNickname());
    }

    @PostMapping("/auth/register")
    public ResponseEntity<String> registerUser(@RequestBody AuthRequest authRequest) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(authRequest.getPassword());



        // 새 사용자 생성
        Users newUser = new Users();
        newUser.setUserName(authRequest.getUserName());
        newUser.setPassword(encodedPassword);
        newUser.setPermission(new Permission()); // 권한 설정
        newUser.setCompany(companyRepository.findById(authRequest.getCompanyId()).orElseThrow(() -> new RuntimeException("회사 정보를 찾을 수 없습니다."))); // 회사 설정
        newUser.setUserNickname(authRequest.getUserNickname());


        usersRepository.save(newUser);
        return ResponseEntity.ok("사용자 등록 완료");
    }


    @PostMapping("/{userId}/assign-permission/{permissionId}")
    public ResponseEntity<UsersPermissionDTO> assignPermissionToUser(@PathVariable Long userId, @PathVariable Long permissionId) {
        UsersPermissionDTO usersPermissionDTO = usersService.assignPermissionToUser(userId, permissionId);
        return usersPermissionDTO != null ? ResponseEntity.ok(usersPermissionDTO) : ResponseEntity.notFound().build();
    }

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
     * 새로운 사용자를 생성함.
     *
     * @param usersDTO 생성할 사용자 정보
     * @return 생성된 사용자 정보를 반환함.
     */
    @PostMapping("/users/create")
    public ResponseEntity<UsersShowDTO> createUser(@RequestBody UsersShowDTO usersDTO) {
        UsersShowDTO createdUser = usersService.createUser(usersDTO);
        return ResponseEntity.ok(createdUser);
    } // 되네? id 값 변경해주고

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


    @PostMapping("/{userId}/assignRole/{roleId}")
    public ResponseEntity<UsersResponseDTO> assignRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
        UsersResponseDTO usersResponseDTO = usersService.assignRoleToUser(userId, roleId);
        return ResponseEntity.ok(usersResponseDTO);
    }
}

