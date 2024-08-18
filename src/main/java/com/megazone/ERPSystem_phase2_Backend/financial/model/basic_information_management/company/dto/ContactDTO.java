package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.dto;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company.Contact;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {
    private String businessPhone; // 사업장 전화번호
    private String fax; // 팩스번호

    public ContactDTO(ContactDTO contact) {
        this.businessPhone = contact.getBusinessPhone();
        this.fax = contact.getFax();
    }

    public ContactDTO(Contact contact) {
        this.businessPhone = contact.getBusinessPhone();
        this.fax = contact.getFax();
    }
}
