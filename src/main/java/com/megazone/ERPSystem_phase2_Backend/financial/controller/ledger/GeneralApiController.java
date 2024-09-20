package com.megazone.ERPSystem_phase2_Backend.financial.controller.ledger;

import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.*;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.UnresolvedVoucherShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.service.ledger.GeneralService;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tools.ant.taskdefs.condition.Http;
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
    private final GeneralService generalService;

    @PostMapping("/api/financial/ledger/general/show")
    public ResponseEntity<Object> generalShow(@RequestBody GeneralDTO dto) {

        List<GeneralAccountListDTO> generalShowDTOS = generalService.getGeneralShow(dto);

        return generalShowDTOS.isEmpty() ? ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("조회가능한 전표가 없습니다.") :
                ResponseEntity.status(HttpStatus.OK).body(generalShowDTOS);
    }

    @PostMapping("/api/financial/ledget/general/selectShow")
    public ResponseEntity<GeneralShowAllDTO> selectGeneralShow(@RequestBody GeneralSelectDTO dto) {

        GeneralShowAllDTO generalShowAllDTO = generalService.getGeneralSelectShow(dto);

        return generalShowAllDTO != null ? ResponseEntity.status(HttpStatus.OK).body(generalShowAllDTO) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
