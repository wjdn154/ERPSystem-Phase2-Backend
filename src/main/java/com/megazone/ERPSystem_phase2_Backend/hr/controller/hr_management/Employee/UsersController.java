package com.megazone.ERPSystem_phase2_Backend.hr.controller.hr_management.Employee;

import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.dto.UsersDTO;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.hr_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.service.hr_management.Users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.QUsers.users;

@Controller
@RequiredArgsConstructor
public class UsersController {
    private final UsersService usersService;
    private final UsersRepository usersRepository;

    /**
     * 모든 사용자 정보를 가져옴.
     * @return 모든 사용자 정보를 담은 리스트를 반환함.
     */
    @PostMapping("/api/hr/users")
    public ResponseEntity<List<UsersDTO>> getAllUsers() {
        List<UsersDTO> users = usersService.findAllUsers();
        return ResponseEntity.ok(users);
    }
}
