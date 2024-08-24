//package com.megazone.ERPSystem_phase2_Backend.hr.controller.basic_information_management.Employee;
//
//import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.PermissionDTO;
//import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.RolePermission.RolePermissionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//public class PermissionController {
//
//    @Autowired
//    private RolePermissionService rolePermissionService;
//
//    @PostMapping("/api/hr/permission/create")
//    public ResponseEntity<PermissionDTO> createPermission(@RequestBody PermissionDTO permissionDTO) {
//        PermissionDTO savedPermissionDTO = rolePermissionService.createPermission(permissionDTO);
//        return ResponseEntity.ok(savedPermissionDTO);
//    }
//}
