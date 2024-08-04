package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccountSubjectRelationship is a Querydsl query type for AccountSubjectRelationship
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccountSubjectRelationship extends EntityPathBase<AccountSubjectRelationship> {

    private static final long serialVersionUID = -1708611690L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAccountSubjectRelationship accountSubjectRelationship = new QAccountSubjectRelationship("accountSubjectRelationship");

    public final QAccountSubject accountSubject;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QAccountSubject relatedAccountSubject;

    public final StringPath relationshipType = createString("relationshipType");

    public QAccountSubjectRelationship(String variable) {
        this(AccountSubjectRelationship.class, forVariable(variable), INITS);
    }

    public QAccountSubjectRelationship(Path<? extends AccountSubjectRelationship> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAccountSubjectRelationship(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAccountSubjectRelationship(PathMetadata metadata, PathInits inits) {
        this(AccountSubjectRelationship.class, metadata, inits);
    }

    public QAccountSubjectRelationship(Class<? extends AccountSubjectRelationship> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.accountSubject = inits.isInitialized("accountSubject") ? new QAccountSubject(forProperty("accountSubject"), inits.get("accountSubject")) : null;
        this.relatedAccountSubject = inits.isInitialized("relatedAccountSubject") ? new QAccountSubject(forProperty("relatedAccountSubject"), inits.get("relatedAccountSubject")) : null;
    }

}

