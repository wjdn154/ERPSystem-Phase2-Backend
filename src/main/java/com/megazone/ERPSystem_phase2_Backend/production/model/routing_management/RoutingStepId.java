package com.megazone.ERPSystem_phase2_Backend.production.model.routing_management;

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

    private Long routingId;
    private Long processId;

}
