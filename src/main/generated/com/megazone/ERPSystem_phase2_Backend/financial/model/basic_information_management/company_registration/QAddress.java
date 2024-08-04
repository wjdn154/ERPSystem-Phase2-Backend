package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company_registration;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAddress is a Querydsl query type for Address
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAddress extends EntityPathBase<Address> {

    private static final long serialVersionUID = 1359579500L;

    public static final QAddress address = new QAddress("address");

    public final StringPath businessAddress = createString("businessAddress");

    public final StringPath businessPlace = createString("businessPlace");

    public final StringPath businessPostalCode = createString("businessPostalCode");

    public final StringPath headquarterAddress = createString("headquarterAddress");

    public final StringPath headquarterPlace = createString("headquarterPlace");

    public final StringPath headquarterPostalCode = createString("headquarterPostalCode");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QAddress(String variable) {
        super(Address.class, forVariable(variable));
    }

    public QAddress(Path<? extends Address> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAddress(PathMetadata metadata) {
        super(Address.class, metadata);
    }

}

