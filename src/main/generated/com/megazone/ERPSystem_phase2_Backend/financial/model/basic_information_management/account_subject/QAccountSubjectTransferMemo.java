package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccountSubjectTransferMemo is a Querydsl query type for AccountSubjectTransferMemo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccountSubjectTransferMemo extends EntityPathBase<AccountSubjectTransferMemo> {

    private static final long serialVersionUID = 704480547L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAccountSubjectTransferMemo accountSubjectTransferMemo = new QAccountSubjectTransferMemo("accountSubjectTransferMemo");

    public final QAccountSubject accountSubject;

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QAccountSubjectTransferMemo(String variable) {
        this(AccountSubjectTransferMemo.class, forVariable(variable), INITS);
    }

    public QAccountSubjectTransferMemo(Path<? extends AccountSubjectTransferMemo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAccountSubjectTransferMemo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAccountSubjectTransferMemo(PathMetadata metadata, PathInits inits) {
        this(AccountSubjectTransferMemo.class, metadata, inits);
    }

    public QAccountSubjectTransferMemo(Class<? extends AccountSubjectTransferMemo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.accountSubject = inits.isInitialized("accountSubject") ? new QAccountSubject(forProperty("accountSubject"), inits.get("accountSubject")) : null;
    }

}

