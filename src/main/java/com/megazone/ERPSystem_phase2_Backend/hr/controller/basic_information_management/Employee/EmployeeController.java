package com.megazone.ERPSystem_phase2_Backend.hr.controller.basic_information_management.Employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.googlejavaformat.Op;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.*;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.Employee.EmployeeService;
import com.megazone.ERPSystem_phase2_Backend.hr.service.basic_information_management.EmployeeImage.EmployeeImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeImageService employeeImageService;

    @PostMapping("/employee/permission/admin/{companyId}")
    public ResponseEntity<Object> getAdminPermissionEmployee(@PathVariable("companyId") Long companyId) {
        return employeeService.getAdminPermissionEmployee(companyId);
    }


    // 사원 리스트 조회
    @PostMapping("/employee/all")
    public ResponseEntity<List<EmployeeShowDTO>> getAllEmployees() {
        List<EmployeeShowDTO> employees = employeeService.findAllEmployees();
        return ResponseEntity.ok(employees);
    }

    // ERP 사용자인 사원 리스트 조회
    @PostMapping("/employee/user/all")
    public ResponseEntity<List<EmployeeShowDTO>> getAllEmployeesAndUsers() {
        List<EmployeeShowDTO> employees = employeeService.findAllUsers();
        return ResponseEntity.ok(employees);
    }

    // 사원 상세 조회
    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeOneDTO> getEmployeeById(@PathVariable("id") Long id) {
        // 서비스에서 해당 아이디의 사원 상세 정보를 가져옴
        Optional<EmployeeOneDTO> employee = employeeService.findEmployeeById(id);

        // 해당 아이디의 사원정보가 존재하지 않을 경우 404 상태 반환.
        return employee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 사원 등록
    @PostMapping("/employee/createEmployee")
    public ResponseEntity<EmployeeShowToDTO> saveEmployeeByEmployeeNumber(
            @RequestParam("formattedValues") String formattedValues,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws JsonProcessingException {

        // JSON 문자열을 EmployeeCreateDTO로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        // ObjectMapper의 readValue함수가 Json 파싱할때 LocalDate 타입을 지원하지않음.
        // 별도의 String 으로받아서 LocalDate 변환 처리
        EmployeeCreateParseDTO employeeCreateParseDTO = objectMapper.readValue(formattedValues, EmployeeCreateParseDTO.class);
        EmployeeCreateDTO employeeCreateDTO = EmployeeCreateDTO.create(employeeCreateParseDTO);
        // EmployeeDTO를 사용하여 사원을 저장
        Optional<EmployeeShowToDTO> employeeDTO = employeeService.saveEmployee(employeeCreateDTO, imageFile);

        // 저장된 사원이 존재하는 경우 OK 응답을 반환
        return employeeDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }



    // 사원 정보 수정
    @PostMapping("/employee/updateEmployee/{id}")
    public ResponseEntity<EmployeeShowToDTO> updateEmployeeById(@PathVariable("id") Long id,
                                                                @RequestParam("formattedValues") String formattedValues,
                                                                @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws JsonProcessingException {
        // JSON 문자열을 DTO로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        EmployeeDataDTO employeeDataDTO = objectMapper.readValue(formattedValues, EmployeeDataDTO.class);

        Optional<EmployeeShowToDTO> updatedEmployee = employeeService.updateEmployee(id, employeeDataDTO, imageFile);
        return updatedEmployee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @GetMapping("/uploads/{filename}")
    public ResponseEntity<Resource> serveFile(@PathVariable("filename") String filename) {
        try {
            Path file = Paths.get("src/main/resources/static/uploads/").resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                // Content-Type을 파일의 MIME 타입으로 설정
                String contentType = Files.probeContentType(file);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //사원 삭제 : 나중에
    @DeleteMapping("/employee/del/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity.status(HttpStatus.OK).body("사원을 삭제하였습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("사원삭제 중 오류가 발생했습니다.");
        }
    }
}
