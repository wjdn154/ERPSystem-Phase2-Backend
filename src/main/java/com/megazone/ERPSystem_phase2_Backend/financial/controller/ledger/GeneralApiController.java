package com.megazone.ERPSystem_phase2_Backend.financial.controller.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.*;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.UnresolvedVoucherShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.service.ledger.GeneralService;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

// 총계정원장 Api Controller
@RestController
@RequiredArgsConstructor
public class GeneralApiController {
    private final UsersRepository usersRepository;
    private final GeneralService generalService;

    @PostMapping("/api/financial/ledger/general/show")
    public ResponseEntity<Object> generalShow(@RequestBody GeneralDTO dto) {

        Users users = usersRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).orElseThrow(
                () -> new RuntimeException("사용자를 찾을 수 없습니다."));
        Long companyId = users.getCompany().getId();
        GeneralShowAllDTO generalShowAllDTO = generalService.getGeneralShow(dto);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("개발진행중");

    }

}
