package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccountSubjectCashMemo is a Querydsl query type for AccountSubjectCashMemo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccountSubjectCashMemo extends EntityPathBase<AccountSubjectCashMemo> {

    private static final long serialVersionUID = 1078707051L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAccountSubjectCashMemo accountSubjectCashMemo = new QAccountSubjectCashMemo("accountSubjectCashMemo");

    public final QAccountSubject accountSubject;

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QAccountSubjectCashMemo(String variable) {
        this(AccountSubjectCashMemo.class, forVariable(variable), INITS);
    }

    public QAccountSubjectCashMemo(Path<? extends AccountSubjectCashMemo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAccountSubjectCashMemo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAccountSubjectCashMemo(PathMetadata metadata, PathInits inits) {
        this(AccountSubjectCashMemo.class, metadata, inits);
    }

    public QAccountSubjectCashMemo(Class<? extends AccountSubjectCashMemo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.accountSubject = inits.isInitialized("accountSubject") ? new QAccountSubject(forProperty("accountSubject"), inits.get("accountSubject")) : null;
    }

}

