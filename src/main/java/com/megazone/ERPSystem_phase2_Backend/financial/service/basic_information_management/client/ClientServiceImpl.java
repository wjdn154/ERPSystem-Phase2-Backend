package com.megazone.ERPSystem_phase2_Backend.financial.service.basic_information_management.client;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.AccountSubject;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.*;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.dto.ClientDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.dto.ClientDTO2;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.dto.fetchClientListDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.enums.TransactionType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.common.Address;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client.*;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.common.AddressRepository;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.common.BankRepository;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.dto.PermissionDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientService {

    private final AddressRepository addressRepository;
    private final ClientBankAccountRepository bankAccountRepository;
    private final BankRepository bankRepository;
    private final ClientRepository clientRepository;
    private final BusinessInfoRepository businessInfoRepository;
    private final CategoryRepository categoryRepository;
    private final ContactInfoRepository contactInfoRepository;
    private final DepartmentEmployeeRepository departmentEmployeeRepository;
    private final FinancialInfoRepository financialInfoRepository;
    private final LiquorRepository liquorRepository;
    private final ManageInfoRepository manageInfoRepository;

    /**
     * 거래처 정보를 저장하고 DTO로 반환     *
     * @param clientDTO 저장할 거래처 정보가 담긴 DTO
     * @return 저장된 거래처 정보를 DTO로 변환하여 Optional로 반환
     */
    @Override
    public Long saveClient(ClientDTO clientDTO) {
        Client client = new Client();

        createAddress(clientDTO, client); // 주소 정보
        createBusinessInfo(clientDTO, client); // 업태 및 종목 정보
        createContact(clientDTO, client); // 연락처 정보
        createFinancialInfo(clientDTO, client); // 재무 정보
        createManagerInfo(clientDTO, client); // 업체 담당자 정보
        createBankAccount(clientDTO, client); // 은행 계좌 정보
        getLiquor(clientDTO, client); // 주류 정보
        getCategory(clientDTO, client); // 거래 유형

        Client savedClient = createClient(clientDTO, client); // 거래처 정보 저장

        return savedClient.getId();
    }

    /**
     * 거래처 정보를 수정하고 DTO로 반환
     * @param clientDTO 수정할 거래처 정보가 담긴 DTO
     * @return 수정된 거래처 정보를 DTO로 변환하여 Optional로 반환
     */
    @Override
    public Long updateClient(ClientDTO clientDTO) {
        return clientRepository.findById(clientDTO.getId()).map(client -> {

            updateAddress(clientDTO, client); // 주소 정보 수정
            updateBusinessInfo(clientDTO, client); // 업태 및 종목 정보 수정
            updateContactInfo(clientDTO, client); // 연락처 정보 수정
            updateFinancialInfo(clientDTO, client); // 재무 정보 수정
            updateManagerInfo(clientDTO, client); // 업체 담당자 정보 수정
            updateBankAccount(clientDTO, client); // 은행 계좌 정보 수정
            getLiquor(clientDTO, client); // 주류 정보 수정
            getCategory(clientDTO, client); // 거래처 분류 수정

            Client savedClient;
            try{
                savedClient = updateClient(clientDTO, client); // 거래처 정보 저장
                System.out.println("savedClient = " + savedClient);
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("e = " + e);
                System.out.println("e = " + e.getMessage());
                throw new RuntimeException(e);
            }


            return savedClient.getId();
        }).orElse(null);
    }

    @Override
    public List<ClientDTO> searchClient(String searchText) {
        List<Client> client;

        if(searchText != null) {
            client = clientRepository.findByPrintClientNameContaining(searchText);
        }else {
            client = clientRepository.findAll();
        }

        ModelMapper modelMapper = new ModelMapper();
        return client.stream().map(c -> modelMapper.map(c, ClientDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<Object> fetchClientList() {
        List<fetchClientListDTO> client = clientRepository.fetchClientList();
        return client.isEmpty() ? ResponseEntity.status(HttpStatus.BAD_REQUEST).body("거래처 데이터가 없습니다.") : ResponseEntity.ok(client);
    }

    @Override
    public ResponseEntity<Object> fetchClient(Long id) {

        Optional<Client> client = clientRepository.findById(id);

        ModelMapper modelMapper = new ModelMapper();
        Optional<ClientDTO> clientDTO = client.map(value -> modelMapper.map(value, ClientDTO.class));
        System.out.println("clientDTO.toString() = " + clientDTO.toString());

        if (!clientDTO.isPresent()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("해당 거래처 정보가 없습니다.");

        return ResponseEntity.ok(clientDTO);
    }

    private void updateBankAccount(ClientDTO clientDTO, Client client) {
        BankAccount bankAccount = client.getBankAccount();
        bankAccount.setBank(bankRepository.findById(clientDTO.getBankAccount().getBankId()).orElseThrow(() -> new RuntimeException("해당 은행이 존재하지 않습니다.")));
        bankAccount.setAccountNumber(clientDTO.getBankAccount().getAccountNumber());
        bankAccount.setAccountHolder(clientDTO.getBankAccount().getAccountHolder());
        bankAccountRepository.save(bankAccount);
        client.setBankAccount(bankAccount);
    }

    private void updateManagerInfo(ClientDTO clientDTO, Client client) {
        ManagerInfo managerInfo = client.getManagerInfo();
        managerInfo.setClientManagerPhoneNumber(clientDTO.getManagerInfo().getClientManagerPhoneNumber());
        managerInfo.setClientManagerEmail(clientDTO.getManagerInfo().getClientManagerEmail());
        manageInfoRepository.save(managerInfo);
        client.setManagerInfo(managerInfo);
    }

    private void updateFinancialInfo(ClientDTO clientDTO, Client client) {
        FinancialInfo financialInfo = client.getFinancialInfo();
        financialInfo.setCollateralAmount(clientDTO.getFinancialInfo().getCollateralAmount());
        financialInfo.setCreditLimit(clientDTO.getFinancialInfo().getCreditLimit());
        financialInfoRepository.save(financialInfo);
        client.setFinancialInfo(financialInfo);
    }

    private void updateContactInfo(ClientDTO clientDTO, Client client) {
        ContactInfo contactInfo = client.getContactInfo();
        contactInfo.setPhoneNumber(clientDTO.getContactInfo().getPhoneNumber());
        contactInfo.setFaxNumber(clientDTO.getContactInfo().getFaxNumber());
        contactInfoRepository.save(contactInfo);
        client.setContactInfo(contactInfo);
    }

    private void updateBusinessInfo(ClientDTO clientDTO, Client client) {
        BusinessInfo businessInfo = client.getBusinessInfo();
        businessInfo.setBusinessType(clientDTO.getBusinessInfo().getBusinessType());
        businessInfo.setBusinessItem(clientDTO.getBusinessInfo().getBusinessItem());
        businessInfoRepository.save(businessInfo);
        client.setBusinessInfo(businessInfo);
    }

    private void updateAddress(ClientDTO clientDTO, Client client) {
        Address address = client.getAddress();
        address.setPostalCode(clientDTO.getAddress().getPostalCode());
        address.setRoadAddress(clientDTO.getAddress().getRoadAddress());
        address.setDetailedAddress(clientDTO.getAddress().getDetailedAddress());
        addressRepository.save(address);
        client.setAddress(address);
    }

    private Client createClient(ClientDTO clientDTO, Client client) {
        client.setTransactionType(TransactionType.valueOf(clientDTO.getTransactionType()));
        client.setPrintClientName(clientDTO.getPrintClientName());
        client.setBusinessRegistrationNumber(clientDTO.getBusinessRegistrationNumber());
        client.setIdNumber(clientDTO.getIdNumber());
        client.setRepresentativeName(clientDTO.getRepresentativeName());
        client.setTransactionStartDate(clientDTO.getTransactionStartDate());
        client.setTransactionEndDate(clientDTO.getTransactionEndDate());
        client.setRemarks(clientDTO.getRemarks());
        client.setIsActive(clientDTO.getIsActive());
        getClientCode(clientDTO,client);

        return clientRepository.save(client);
    }

    private Client updateClient(ClientDTO clientDTO, Client client) {
        client.setTransactionType(TransactionType.valueOf(clientDTO.getTransactionType()));
        client.setBusinessRegistrationNumber(clientDTO.getBusinessRegistrationNumber());
        client.setIdNumber(clientDTO.getIdNumber());
        client.setRepresentativeName(clientDTO.getRepresentativeName());
        client.setPrintClientName(clientDTO.getPrintClientName());
        client.setTransactionStartDate(clientDTO.getTransactionStartDate());
        client.setTransactionEndDate(clientDTO.getTransactionEndDate());
        client.setRemarks(clientDTO.getRemarks());
        client.setIsActive(clientDTO.getIsActive());

        return clientRepository.save(client);
    }

    private void createBankAccount(ClientDTO clientDTO, Client client) {
        if (clientDTO.getBankAccount() != null) {
            BankAccount bankAccount = new BankAccount();
            bankAccount.setBank(bankRepository.findById(clientDTO.getBankAccount().getBankId()).orElseThrow(() -> new RuntimeException("해당 은행이 존재하지 않습니다.")));
            bankAccount.setAccountNumber(clientDTO.getBankAccount().getAccountNumber());
            bankAccount.setAccountHolder(clientDTO.getBankAccount().getAccountHolder());
            bankAccountRepository.save(bankAccount);
            client.setBankAccount(bankAccount);
        }
    }

    private void createManagerInfo(ClientDTO clientDTO, Client client) {
        if (clientDTO.getManagerInfo() != null) {
            ManagerInfo managerInfo = new ManagerInfo();
            managerInfo.setClientManagerPhoneNumber(clientDTO.getManagerInfo().getClientManagerPhoneNumber());
            managerInfo.setClientManagerEmail(clientDTO.getManagerInfo().getClientManagerEmail());
            manageInfoRepository.save(managerInfo);
            client.setManagerInfo(managerInfo);
        }
    }

    private void createFinancialInfo(ClientDTO clientDTO, Client client) {
        if (clientDTO.getFinancialInfo() != null) {
            FinancialInfo financialInfo = new FinancialInfo();
            financialInfo.setCollateralAmount(clientDTO.getFinancialInfo().getCollateralAmount());
            financialInfo.setCreditLimit(clientDTO.getFinancialInfo().getCreditLimit());
            financialInfoRepository.save(financialInfo);
            client.setFinancialInfo(financialInfo);
        }
    }

    private void createContact(ClientDTO clientDTO, Client client) {
        if (clientDTO.getContactInfo() != null) {
            ContactInfo contactInfo = new ContactInfo();
            contactInfo.setPhoneNumber(clientDTO.getContactInfo().getPhoneNumber());
            contactInfo.setFaxNumber(clientDTO.getContactInfo().getFaxNumber());
            contactInfoRepository.save(contactInfo);
            client.setContactInfo(contactInfo);
        }
    }

    private void createBusinessInfo(ClientDTO clientDTO, Client client) {
        if (clientDTO.getBusinessInfo() != null) {
            BusinessInfo businessInfo = new BusinessInfo();
            businessInfo.setBusinessType(clientDTO.getBusinessInfo().getBusinessType());
            businessInfo.setBusinessItem(clientDTO.getBusinessInfo().getBusinessItem());
            businessInfoRepository.save(businessInfo);
            client.setBusinessInfo(businessInfo);
        }
    }

    private void createAddress(ClientDTO clientDTO, Client client) {
        if (clientDTO.getAddress() != null) {
            Address address = new Address();
            address.setPostalCode(clientDTO.getAddress().getPostalCode());
            address.setRoadAddress(clientDTO.getAddress().getRoadAddress());
            address.setDetailedAddress(clientDTO.getAddress().getDetailedAddress());
            addressRepository.save(address);
            client.setAddress(address);
        }
    }

    private void getCategory(ClientDTO clientDTO, Client client) {
        client.setCategory(categoryRepository.findById(clientDTO.getCategoryId()).orElseThrow(
                () -> new RuntimeException("해당 거래처 분류 코드가 존재하지 않습니다.")));
    }

    private void getLiquor(ClientDTO clientDTO, Client client) {
        client.setLiquor(liquorRepository.findById(clientDTO.getLiquor().getId()).orElseThrow(
                () -> new RuntimeException("해당 주류 코드가 존재하지 않습니다.")));
    }

    private void getClientCode(ClientDTO clientDTO, Client client) {
        Client oldClient = clientRepository.findByCode(clientDTO.getCode()).orElse(null);

        if(oldClient == null) {
            client.setCode(clientDTO.getCode());
        }
        else {
            throw new RuntimeException("해당 거래처 코드가 이미 존재 합니다.");
        }
    }

}