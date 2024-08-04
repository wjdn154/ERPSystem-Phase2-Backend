package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAccountSubjectStructure is a Querydsl query type for AccountSubjectStructure
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccountSubjectStructure extends EntityPathBase<AccountSubjectStructure> {

    private static final long serialVersionUID = -1515214635L;

    public static final QAccountSubjectStructure accountSubjectStructure = new QAccountSubjectStructure("accountSubjectStructure");

    public final StringPath code = createString("code");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> max = createNumber("max", Integer.class);

    public final NumberPath<Integer> min = createNumber("min", Integer.class);

    public final StringPath name = createString("name");

    public QAccountSubjectStructure(String variable) {
        super(AccountSubjectStructure.class, forVariable(variable));
    }

    public QAccountSubjectStructure(Path<? extends AccountSubjectStructure> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAccountSubjectStructure(PathMetadata metadata) {
        super(AccountSubjectStructure.class, metadata);
    }

}

