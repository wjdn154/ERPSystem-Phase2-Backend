package com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Data
@AllArgsConstructor
public class Subscription {
    private final SseEmitter emitter;
    private final ModuleType module;
    private final PermissionType permission;
}