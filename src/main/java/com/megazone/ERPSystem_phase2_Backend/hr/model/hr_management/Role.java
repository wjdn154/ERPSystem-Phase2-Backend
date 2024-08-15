<<<<<<< HEAD
//package com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//// 시스템 사용자의 역할 및 권한 정보 저장
//// 역할 ( 시스템 관리자, 인사 담당자, 물류 담당자, 재무/회계 담당자, 생산 담당자 )
//@Data
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//public class Role {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToMany(mappedBy = "role") // 사용자 참조
//    private List<Users> users;
//
//    @Column(nullable = false)
//    private String roleName; // 역할 이름
//
//    @Column
//    private String permissions; // 권한
//}
=======
package com.megazone.ERPSystem_phase2_Backend.hr.model.hr_management;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// 시스템 사용자의 역할 및 권한 정보 저장
// 역할 ( 시스템 관리자, 인사 담당자, 물류 담당자, 재무/회계 담당자, 생산 담당자 )
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)// 사용자 참조
    private List<Users> users;

    @Column(nullable = false)
    private String roleName; // 역할 이름

    @Column
    private String permissions; // 권한
}
>>>>>>> origin/develop
