package com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.client;

import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Category;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByCode(String code);
    List<Client> findByPrintClientNameContaining(String printClientName);

    @Modifying
    @Query("UPDATE client c SET c.employee = NULL WHERE c.employee.id = :employeeId")
    void updateEmployeeToNull(@Param("employeeId") Long employeeId);
}