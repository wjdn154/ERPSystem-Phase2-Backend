package com.megazone.ERPSystem_phase2_Backend.financial.repository.basic_information_management.account_subject.AccountSubject;


import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.*;
import com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.dto.*;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.QAccountSubject.accountSubject;
import static com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.QCashMemo.cashMemo;
import static com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.QFixedMemo.fixedMemo;
import static com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.QStandardFinancialStatement.standardFinancialStatement;
import static com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.QNature.nature;
import static com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.QStandardFinancialStatement.*;
import static com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.QStructure.structure;
import static com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject.QTransferMemo.transferMemo;

@Repository
@RequiredArgsConstructor
public class AccountSubjectRepositoryImpl implements AccountSubjectRepositoryCustom {
    // QueryDSL을 위한 JPAQueryFactory 주입
    private final JPAQueryFactory queryFactory;

    /**
     * 모든 계정과목을 조회함.
     *
     * @return 계정과목 목록을 반환함.
     */
    @Override
    public List<AccountSubjectDTO> findAllAccountSubject() {
        return queryFactory
                .select(Projections.fields(AccountSubjectDTO.class,
                        accountSubject.structure.code.as("structureCode"), // 구조 코드
                        accountSubject.code.as("code"), // 계정과목 코드
                        accountSubject.name.as("name"), // 계정과목 이름
                        accountSubject.natureCode.as("natureCode"), // 성격 코드
                        nature.name.as("natureName"), // 성격 이름
                        accountSubject.parent.code.as("parentCode") // 부모 계정과목 코드
                ))
                .from(accountSubject)
                .leftJoin(nature).on(accountSubject.natureCode.eq(nature.code)) // nature와 조인
                .fetch(); // 결과를 리스트로 반환
    }

    /**
     * 특정 코드에 해당하는 계정과목의 상세 정보를 조회함.     *
     * @param code 계정과목 코드
     * @return 계정과목 상세 정보를 담은 Optional 객체를 반환함.
     */
    @Override
    public Optional<AccountSubjectDetailDTO> findAccountSubjectDetailByCode(String code) {

        QAccountSubject parentAlias = new QAccountSubject("parentAlias");

        // 서브쿼리로 cashMemos 가져옴
        List<CashMemoDTO> cashMemos = queryFactory
                .select(Projections.fields(CashMemoDTO.class,
                        cashMemo.id, // 현금 메모 ID
                        cashMemo.content // 현금 메모 내용
                ))
                .from(cashMemo)
                .where(cashMemo.accountSubject.code.eq(code)) // 계정과목 코드로 필터링
                .fetch();

        // 서브쿼리로 transferMemos 가져옴
        List<TransferMemoDTO> transferMemos = queryFactory
                .select(Projections.fields(TransferMemoDTO.class,
                        transferMemo.id, // 대체 메모 ID
                        transferMemo.content // 대체 메모 내용
                ))
                .from(transferMemo)
                .where(transferMemo.accountSubject.code.eq(code)) // 계정과목 코드로 필터링
                .fetch();

        // 서브쿼리로 fixedMemos 가져옴
        List<FixedMemoDTO> fixedMemos = queryFactory
                .select(Projections.fields(FixedMemoDTO.class,
                        fixedMemo.id, // 고정 메모 ID
                        fixedMemo.content, // 고정 메모 내용
                        fixedMemo.category // 고정 메모 카테고리
                ))
                .from(fixedMemo)
                .where(fixedMemo.accountSubject.code.eq(code)) // 계정과목 코드로 필터링
                .fetch();

        // 서브쿼리로 표준 재무제표 정보 가져옴
        List<StandardFinancialStatementDTO> standardFinancialStatements = queryFactory
                .select(Projections.fields(StandardFinancialStatementDTO.class,
                        standardFinancialStatement.code, // 표준 재무제표 코드
                        standardFinancialStatement.name // 표준 재무제표 이름
                ))
                .from(standardFinancialStatement)
                .where(standardFinancialStatement.structure.eq(
                        queryFactory.select(structure)
                                .from(accountSubject)
                                .where(accountSubject.code.eq(code)) // 계정과목 코드로 필터링
                                .fetchOne()
                ))
                .fetch();

        // 계정과목 상세 정보 조회
        AccountSubjectDetailDTO result = queryFactory
                .select(Projections.fields(AccountSubjectDetailDTO.class,
                        accountSubject.code.as("code"), // 계정과목 코드
                        accountSubject.name.as("name"), // 계정과목 이름
                        parentAlias.code.as("parentCode"), // 부모 계정과목 코드
                        parentAlias.name.as("parentName"), // 부모 계정과목 이름
                        accountSubject.englishName.as("englishName"), // 영문명
                        accountSubject.isActive.as("isActive"), // 활성화 여부
                        accountSubject.modificationType.as("modificationType"), // 수정 가능 여부
                        accountSubject.structure.code.as("structureCode"), // 구조 코드
                        accountSubject.isForeignCurrency.as("isForeignCurrency"), // 외화 사용 여부
                        accountSubject.isBusinessCar.as("isBusinessCar") // 업무용 차량 여부
                ))
                .from(accountSubject)
                .leftJoin(accountSubject.parent, parentAlias) // 부모 계정과목에 대한 조인
                .leftJoin(accountSubject.structure, structure) // 계정 구조에 대한 조인
                .where(accountSubject.code.eq(code)) // 계정과목 코드로 필터링
                .fetchOne();

        if (result != null) {
            // 조회된 메모들과 표준 재무제표를 DTO에 설정함
            result.setStandardFinancialStatement(standardFinancialStatements);
            result.setCashMemos(cashMemos);
            result.setTransferMemos(transferMemos);
            result.setFixedMemos(fixedMemos);
        }

        // Optional로 반환
        return Optional.ofNullable(result);
    }
}