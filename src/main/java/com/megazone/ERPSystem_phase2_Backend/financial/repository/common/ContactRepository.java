package com.megazone.ERPSystem_phase2_Backend.financial.repository.common;

import com.megazone.ERPSystem_phase2_Backend.financial.model.common.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}