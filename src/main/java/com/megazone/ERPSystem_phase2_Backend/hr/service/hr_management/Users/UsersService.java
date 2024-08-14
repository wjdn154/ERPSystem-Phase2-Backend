package com.megazone.ERPSystem_phase2_Backend.hr.service.hr_management.Users;

import com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management.Users;

public interface UsersService {
    Users saveUsers(Users users);
    Users updateUsers(Users users);

    void deleteUsers(Long usersId);
}
