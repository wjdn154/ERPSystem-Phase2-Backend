package com.megazone.ERPSystem_phase2_Backend.hr.controller.basic_information_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.UsersDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.QUsers.users;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr")
public class UsersController {

    private final UsersService usersService;

    private final UsersRepository usersRepository;

    /**
     * 모든 사용자 정보를 가져옴.
     * @return 모든 사용자 정보를 담은 리스트를 반환함.
     */
    @PostMapping("/users/all")
    public ResponseEntity<List<UsersDTO>> getAllUsers() {
        List<UsersDTO> users = usersService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * ID로 특정 사용자를 조회함.
     * @param id 조회할 사용자의 ID
     * @return 조회된 사용자 정보를 반환함.
     */
    @PostMapping("/users/{id}")
    public ResponseEntity<UsersDTO> getUserById(@PathVariable Long id) {
        UsersDTO user = usersService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * 새로운 사용자를 생성함.
     * @param usersDTO 생성할 사용자 정보
     * @return 생성된 사용자 정보를 반환함.
     */
    @PostMapping("/users/create")
    public ResponseEntity<UsersDTO> createUser(@RequestBody UsersDTO usersDTO) {
        UsersDTO createdUser = usersService.createUser(usersDTO);
        return ResponseEntity.ok(createdUser);
    } // 되네? id 값 변경해주고

    /**
     * 사용자를 수정함.
     * @param id 수정할 사용자의 ID
     * @param usersDTO 수정할 사용자 정보
     * @return 수정된 사용자 정보를 반환함.
     */
    @PutMapping("/users/put/{id}")
    public ResponseEntity<UsersDTO> updateUser(@PathVariable Long id, @RequestBody UsersDTO usersDTO) {
        UsersDTO updatedUser = usersService.updateUser(id, usersDTO);
        return ResponseEntity.ok(updatedUser);
    } // 다시 해야 됨

    /**
     * ID로 사용자를 삭제함.
     * @param id 삭제할 사용자의 ID
     * @return 삭제 성공 메시지를 반환함.
     */
    @DeleteMapping("/users/del/{id}")
    public ResponseEntity<String> deleteUsers(@PathVariable Long id) {
        usersService.deleteUsers(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
