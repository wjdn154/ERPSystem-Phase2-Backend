package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company_registration;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTaxOffice is a Querydsl query type for TaxOffice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTaxOffice extends EntityPathBase<TaxOffice> {

    private static final long serialVersionUID = -460804673L;

    public static final QTaxOffice taxOffice = new QTaxOffice("taxOffice");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath region = createString("region");

    public QTaxOffice(String variable) {
        super(TaxOffice.class, forVariable(variable));
    }

    public QTaxOffice(Path<? extends TaxOffice> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTaxOffice(PathMetadata metadata) {
        super(TaxOffice.class, metadata);
    }

}

