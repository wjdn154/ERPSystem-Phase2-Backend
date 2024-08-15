package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company_registration;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCorporateType is a Querydsl query type for CorporateType
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCorporateType extends EntityPathBase<CorporateType> {

    private static final long serialVersionUID = 284902583L;

    public static final QCorporateType corporateType = new QCorporateType("corporateType");

    public final StringPath code = createString("code");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath type = createString("type");

    public QCorporateType(String variable) {
        super(CorporateType.class, forVariable(variable));
    }

    public QCorporateType(Path<? extends CorporateType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCorporateType(PathMetadata metadata) {
        super(CorporateType.class, metadata);
    }

}

