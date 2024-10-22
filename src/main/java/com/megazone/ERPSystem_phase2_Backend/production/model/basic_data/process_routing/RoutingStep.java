package com.megazone.ERPSystem_phase2_Backend.production.model.basic_data.process_routing;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "process_routing_routing_step")
@Table(name = "process_routing_routing_step", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"process_routing_id", "step_order"})
}, indexes = {
        @Index(name = "idx_routing_step_order", columnList = "process_routing_id, stepOrder")
})
@AttributeOverride(name = "id.processRoutingId", column = @Column(name = "process_routing_id"))
@AttributeOverride(name = "id.processId", column = @Column(name = "process_id"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@ToString(exclude = {"processRouting", "processDetails"})
public class RoutingStep {

    @EmbeddedId
    private RoutingStepId id; // 복합키를 위한 식별자

    @ManyToOne
    @MapsId("processRoutingId") // 복합키의 일부인 routingId를 사용하여 연관관계를 맺음
    @JoinColumn(name = "process_routing_id")
    private ProcessRouting processRouting;

    @ManyToOne
    @MapsId("processId") // 복합키의 일부인 processId를 사용하여 연관관계를 맺음
    @JoinColumn(name = "process_id")
    @JsonBackReference
    private ProcessDetails processDetails;

    @Column(name = "step_order")
    private Long stepOrder; // Routing에서의 순서를 정의하는 필드

}


