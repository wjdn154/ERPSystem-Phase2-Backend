package com.megazone.ERPSystem_phase2_Backend.Integrated.repository.notification;

import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.Notification;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.QNotification;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.QUserNotification;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.dto.UserNotificationDTO;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.ModuleType;
import com.megazone.ERPSystem_phase2_Backend.Integrated.model.notification.enums.PermissionType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.QAccountSubject;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.QStandardFinancialStatement;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.QStructure;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.enums.EntryType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.enums.FinancialStatementType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.client.QClient;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.FinancialStatementsLedgerDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.FinancialStatementsLedgerSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.IncomeStatementLedgerDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.financial_statements.dto.IncomeStatementSearchDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.dto.*;
import com.megazone.ERPSystem_phase2_Backend.financial.model.ledger.enums.DailyAndMonthType;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.QResolvedVoucher;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.ResolvedVoucherDeleteDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.dto.ResolvedVoucherShowDTO;
import com.megazone.ERPSystem_phase2_Backend.financial.model.voucher_entry.general_voucher_entry.enums.VoucherType;
import com.megazone.ERPSystem_phase2_Backend.financial.repository.voucher_entry.general_voucher_entry.resolvedVoucher.ResolvedVoucherRepositoryCustom;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.QDepartment;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.QEmployee;
import com.megazone.ERPSystem_phase2_Backend.hr.model.basic_information_management.employee.QUsers;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<UserNotificationDTO> fetchNotification(Long employeeId, ModuleType module, PermissionType permission) {
        QNotification qNotification = QNotification.notification;
        QUserNotification qUserNotification = QUserNotification.userNotification;
        QUsers qUsers = QUsers.users;

        // 조건 빌더 설정
        BooleanBuilder builder = new BooleanBuilder();
        if (module != ModuleType.ALL || permission != PermissionType.ALL) {
            builder.or(qNotification.module.eq(module).and(qNotification.permission.eq(permission)))
                    .or(qNotification.module.eq(ModuleType.ALL).and(qNotification.permission.eq(permission)))
                    .or(qNotification.module.eq(module).and(qNotification.permission.eq(PermissionType.ALL)))
                    .or(qNotification.module.eq(ModuleType.ALL).and(qNotification.permission.eq(PermissionType.ALL)));
        }

        // LEFT JOIN을 사용하여 해당 employeeId의 UserNotification에 없는 Notification 조회
        return queryFactory
                .select(Projections.constructor(UserNotificationDTO.class,
                        qUsers,
                        qNotification,
                        qNotification.module,
                        qNotification.permission,
                        qNotification.type,
                        qNotification.content,
                        qNotification.createAt,
                        qUserNotification.readAt,
                        qUserNotification.readStatus
                ))
                .from(qNotification)
                .leftJoin(qUserNotification)
                .on(qUserNotification.notification.eq(qNotification)
                        .and(qUserNotification.users.id.eq(employeeId)))
                .leftJoin(qUsers).on(qUsers.id.eq(employeeId))
                .where(builder.and(qUserNotification.id.isNull())) // 아직 읽지 않은 알림만 조회
                .fetch();
    }
}
