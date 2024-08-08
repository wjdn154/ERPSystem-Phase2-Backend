package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company_registration;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRepresentative is a Querydsl query type for Representative
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRepresentative extends EntityPathBase<Representative> {

    private static final long serialVersionUID = -1719216859L;

    public static final QRepresentative representative = new QRepresentative("representative");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath idNumber = createString("idNumber");

    public final BooleanPath isForeign = createBoolean("isForeign");

    public final StringPath name = createString("name");

    public QRepresentative(String variable) {
        super(Representative.class, forVariable(variable));
    }

    public QRepresentative(Path<? extends Representative> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRepresentative(PathMetadata metadata) {
        super(Representative.class, metadata);
    }

}

