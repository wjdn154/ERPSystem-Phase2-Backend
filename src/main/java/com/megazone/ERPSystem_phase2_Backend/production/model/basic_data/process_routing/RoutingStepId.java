package com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutingStepId implements Serializable {

    private Long processRoutingId;
    private Long processId;

}
