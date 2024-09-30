package com.megazone.ERPSystem_phase2_Backend.production.controller.resource_data;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.HazardousMaterialDTO;
import com.megazone.ERPSystem_phase2_Backend.production.service.resource_data.HazardousMaterial.HazardousMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/production")
public class HazardousMaterialController {

    private final UsersRepository usersRepository;
    private final HazardousMaterialService hazardousMaterialService;

    //유해물질 리스트 조회
    @PostMapping("/hazardousMaterials")
    public ResponseEntity<List<HazardousMaterialDTO>> getHazardousMaterials(){

        //서비스에서 회사아이디에 해당하는 유해물질 리스트 조회
        List<HazardousMaterialDTO> result = hazardousMaterialService.findAllHazardousMaterial();

        return (result != null)?
                ResponseEntity.status(HttpStatus.OK).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //유해물질 등록
    @PostMapping("/hazardousMaterial/createMaterial")
    public ResponseEntity<HazardousMaterialDTO> createHazardousMaterial(@RequestBody HazardousMaterialDTO dto){

        //서비스에서 유해물질 정보를 등록함
        Optional<HazardousMaterialDTO> result = hazardousMaterialService.createHazardousMaterial(dto);

        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    //유해물질 수정
    @PutMapping("/hazardousMaterial/updateMaterial/{id}")
    public ResponseEntity<HazardousMaterialDTO> updateHazardousMaterial(@PathVariable("id") Long id, @RequestBody HazardousMaterialDTO dto){

        //서비스에서 해당 아이디의 유해물질 정보를 수정함.
        Optional<HazardousMaterialDTO> result = hazardousMaterialService.updateHazardousMaterial(id, dto);

        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    //유해물질 삭제
    @DeleteMapping("/hazardousMaterial/deleteMaterial/{id}")
    public ResponseEntity<HazardousMaterialDTO> deleteHazardousMaterial(@PathVariable("id") Long id){

        //서비스에서 해당 아이디의 유해물질 정보를 삭제함
        try {
            hazardousMaterialService.deleteHazardousMaterial(id);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }
}
