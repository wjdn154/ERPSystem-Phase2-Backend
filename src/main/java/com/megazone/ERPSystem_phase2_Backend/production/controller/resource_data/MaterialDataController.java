package com.megazone.ERPSystem_phase2_Backend.production.controller.resource_data;

import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase2_Backend.hr.repository.basic_information_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase2_Backend.logistics.model.product_registration.dto.ProductMaterialDTO;
import com.megazone.ERPSystem_phase2_Backend.production.model.resource_data.dto.*;
import com.megazone.ERPSystem_phase2_Backend.production.repository.resource_data.materialData.MaterialDataRepository;
import com.megazone.ERPSystem_phase2_Backend.production.service.resource_data.material.MaterialDataService;
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
public class MaterialDataController {

    private final MaterialDataRepository materialDataRepository;
    private final UsersRepository usersRepository;
    private final MaterialDataService materialService;

    //자재 리스트 조회
    @PostMapping("/materials")
    public ResponseEntity<List<ListMaterialDataDTO>> getMaterials() {

        //서비스에서 자재 리스트 정보를 가져옴
        List<ListMaterialDataDTO> result = materialService.findAllMaterial();
        return (result != null)?
                ResponseEntity.status(HttpStatus.OK).body(result):
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    //자재 상세 조회
    @PostMapping("/material/{id}")
    public ResponseEntity<MaterialDataShowDTO> getMaterial(@PathVariable("id") Long id){

        //서비스에서 특정 자재 정보를 가져옴
        Optional<MaterialDataShowDTO> result = materialService.findMaterialById(id);

        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    //자재 리스트 수정
    @PutMapping("/material/updateMaterial/{id}")
    public ResponseEntity<ListMaterialDataDTO> updateMaterial(@PathVariable("id") Long id, @RequestBody ListMaterialDataDTO dto){

        //서비스에서 아이디에 해당하는 자재 상세 정보를 수정함.
        Optional<ListMaterialDataDTO> result = materialService.updateMaterial(id, dto);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    //자재 상세 등록
    @PostMapping("/material/createMaterial")
    public ResponseEntity<MaterialDataShowDTO> createMaterial(@RequestBody MaterialDataShowDTO dto){

        //서비스에서 자재 상세 정보를 등록함.
        Optional<MaterialDataShowDTO> result = materialService.createMaterial(dto);
        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    //자재 리스트 삭제
    @DeleteMapping("/material/deleteMaterial/{id}")
    public ResponseEntity<String> deleteMaterial(@PathVariable("id") Long id){

        //서비스에서 자재 상세 정보를 삭제함.
        try {
            materialService.deleteMaterial(id);
            return ResponseEntity.noContent().build();   //204반환
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //해당 자재 유해물질 리스트 조회
    @PostMapping("/material/{materialId}/hazardousMaterials")
    public ResponseEntity<ListHazardousMaterialDTO> hazardousMaterials(@PathVariable("materialId") Long id){

        //아이디에 해당하는 유해물질 리스트 조회
        ListHazardousMaterialDTO result = materialService.findAllHazardousMaterialById(id);

        return ResponseEntity.ok(result);
    }

    //해당 자재 유해물질 추가(수정)
    @PostMapping("/material/hazardousMaterial/add/{materialId}")
    public ResponseEntity<ListHazardousMaterialDTO> addHazardousMaterial(
            @PathVariable("materialId") Long materialId, @RequestBody List<HazardousMaterialDTO> hazardousMaterialDTOs){

        //서비스에서 아이디에 해당하는 유해물질 추가 등록
        Optional<ListHazardousMaterialDTO> result = materialService.addHazardousMaterial(materialId, hazardousMaterialDTOs);

        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    //해당 자재 유해물질 제거
    @DeleteMapping("/material{materialId}/hazardousMaterial/{hazardousMaterialId}")
    public ResponseEntity<String> deleteHazardousMaterial(
            @PathVariable("materialId") Long materialId, @PathVariable("hazardousMaterialId") Long hazardousMaterialId ){

        //서비스에서 아이디에 해당하는 자재의 유해물질 제거
        try {
            materialService.deleteHazardousMaterial(materialId, hazardousMaterialId);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

//    //해당 자재 품목 리스트 조회
//    @PostMapping("/material/{materialId}/productMaterials")
//    public ResponseEntity<ListProductMaterialDTO> productMaterials(@PathVariable("materialId") Long materialId){
//
//        //아이디에 해당하는 품목 리스트 조회
//        ListProductMaterialDTO result = materialService.findAllProductMaterialById(materialId);
//
//        return ResponseEntity.ok(result);
//    }
//
//    //해당 자재 품목 추가
//    @PostMapping("/material/productMaterial/add/{materialId}")
//    public ResponseEntity<ListProductMaterialDTO> addProductMaterial(
//            @PathVariable("materialId") Long materialId, @RequestBody List<ProductMaterialDTO> productMaterialDTOs){
//
//        //서비스에서 아이디에 해당하는 자재 품목 추가 등록
//        Optional<ListProductMaterialDTO> result = materialService.addProductMaterial(materialId, productMaterialDTOs);
//
//        return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
//    }

//    //해당 자재 품목 삭제
//    @DeleteMapping("/material/{materialId}/productMaterial/{productCode}")
//    public ResponseEntity<String> deleteProductMaterial(
//            @PathVariable("materialId") Long materialId, @PathVariable("productCode") String productCode){
//
//        //서비스에서 아이디에 해당하는 자재 품목 제거
//        try {
//            materialService.deleteProductMaterial(materialId,productCode);
//            return ResponseEntity.noContent().build();
//        }catch (RuntimeException e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }
}
