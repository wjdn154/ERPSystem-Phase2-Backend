//package com.megazone.ERPSystem_phase2_Backend.hr.controller.basic_information_management.Employee;
//
//import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.RolePermissionDTO;
//import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.RolePermission.RolePermissionService;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//
//@Controller
//@AllArgsConstructor
//public class RolePermissionController {
//
//    @Autowired
//    private RolePermissionService rolePermissionService;
//
//    @PostMapping("/assign")
//    public ResponseEntity<RolePermissionDTO> assignPermissionToRole(@RequestBody RolePermissionDTO rolePermissionDTO) {
//        RolePermissionDTO savedRolePermissionDTO = rolePermissionService.assignPermissionToRole(rolePermissionDTO);
//        return ResponseEntity.ok(savedRolePermissionDTO);
//    }
//}
