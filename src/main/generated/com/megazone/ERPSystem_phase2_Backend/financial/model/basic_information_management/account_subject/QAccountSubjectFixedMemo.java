package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccountSubjectFixedMemo is a Querydsl query type for AccountSubjectFixedMemo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccountSubjectFixedMemo extends EntityPathBase<AccountSubjectFixedMemo> {

    private static final long serialVersionUID = -912453072L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAccountSubjectFixedMemo accountSubjectFixedMemo = new QAccountSubjectFixedMemo("accountSubjectFixedMemo");

    public final QAccountSubject accountSubject;

    public final StringPath category = createString("category");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QAccountSubjectFixedMemo(String variable) {
        this(AccountSubjectFixedMemo.class, forVariable(variable), INITS);
    }

    public QAccountSubjectFixedMemo(Path<? extends AccountSubjectFixedMemo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAccountSubjectFixedMemo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAccountSubjectFixedMemo(PathMetadata metadata, PathInits inits) {
        this(AccountSubjectFixedMemo.class, metadata, inits);
    }

    public QAccountSubjectFixedMemo(Class<? extends AccountSubjectFixedMemo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.accountSubject = inits.isInitialized("accountSubject") ? new QAccountSubject(forProperty("accountSubject"), inits.get("accountSubject")) : null;
    }

}

