package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccountSubject is a Querydsl query type for AccountSubject
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccountSubject extends EntityPathBase<AccountSubject> {

    private static final long serialVersionUID = -1503901122L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAccountSubject accountSubject = new QAccountSubject("accountSubject");

    public final ListPath<AccountSubjectCashMemo, QAccountSubjectCashMemo> accountSubjectCashMemo = this.<AccountSubjectCashMemo, QAccountSubjectCashMemo>createList("accountSubjectCashMemo", AccountSubjectCashMemo.class, QAccountSubjectCashMemo.class, PathInits.DIRECT2);

    public final ListPath<AccountSubjectFixedMemo, QAccountSubjectFixedMemo> accountSubjectFixedMemos = this.<AccountSubjectFixedMemo, QAccountSubjectFixedMemo>createList("accountSubjectFixedMemos", AccountSubjectFixedMemo.class, QAccountSubjectFixedMemo.class, PathInits.DIRECT2);

    public final QAccountSubjectStandardFinancialStatement accountSubjectStandardFinancialStatement;

    public final QAccountSubjectStructure accountSubjectStructure;

    public final ListPath<AccountSubjectTransferMemo, QAccountSubjectTransferMemo> accountSubjectTransferMemo = this.<AccountSubjectTransferMemo, QAccountSubjectTransferMemo>createList("accountSubjectTransferMemo", AccountSubjectTransferMemo.class, QAccountSubjectTransferMemo.class, PathInits.DIRECT2);

    public final StringPath code = createString("code");

    public final StringPath englishName = createString("englishName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isActive = createBoolean("isActive");

    public final BooleanPath isBusinessCar = createBoolean("isBusinessCar");

    public final BooleanPath isForeignCurrency = createBoolean("isForeignCurrency");

    public final BooleanPath modificationType = createBoolean("modificationType");

    public final StringPath name = createString("name");

    public QAccountSubject(String variable) {
        this(AccountSubject.class, forVariable(variable), INITS);
    }

    public QAccountSubject(Path<? extends AccountSubject> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAccountSubject(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAccountSubject(PathMetadata metadata, PathInits inits) {
        this(AccountSubject.class, metadata, inits);
    }

    public QAccountSubject(Class<? extends AccountSubject> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.accountSubjectStandardFinancialStatement = inits.isInitialized("accountSubjectStandardFinancialStatement") ? new QAccountSubjectStandardFinancialStatement(forProperty("accountSubjectStandardFinancialStatement"), inits.get("accountSubjectStandardFinancialStatement")) : null;
        this.accountSubjectStructure = inits.isInitialized("accountSubjectStructure") ? new QAccountSubjectStructure(forProperty("accountSubjectStructure")) : null;
    }

}

