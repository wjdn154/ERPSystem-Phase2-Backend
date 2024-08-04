package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAccountSubjectNature is a Querydsl query type for AccountSubjectNature
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccountSubjectNature extends EntityPathBase<AccountSubjectNature> {

    private static final long serialVersionUID = 1491504261L;

    public static final QAccountSubjectNature accountSubjectNature = new QAccountSubjectNature("accountSubjectNature");

    public final StringPath code = createString("code");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QAccountSubjectNature(String variable) {
        super(AccountSubjectNature.class, forVariable(variable));
    }

    public QAccountSubjectNature(Path<? extends AccountSubjectNature> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccountSubjectNature(PathMetadata metadata) {
        super(AccountSubjectNature.class, metadata);
    }

}

