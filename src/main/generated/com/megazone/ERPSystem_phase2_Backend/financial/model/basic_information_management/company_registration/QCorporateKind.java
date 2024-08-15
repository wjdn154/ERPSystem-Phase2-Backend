package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company_registration;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QCorporateKind is a Querydsl query type for CorporateKind
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCorporateKind extends EntityPathBase<CorporateKind> {

    private static final long serialVersionUID = 284619025L;

    public static final QCorporateKind corporateKind = new QCorporateKind("corporateKind");

    public final StringPath code = createString("code");

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath kind = createString("kind");

    public QCorporateKind(String variable) {
        super(CorporateKind.class, forVariable(variable));
    }

    public QCorporateKind(Path<? extends CorporateKind> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCorporateKind(PathMetadata metadata) {
        super(CorporateKind.class, metadata);
    }

}

