package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company_registration;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBusinessItem is a Querydsl query type for BusinessItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBusinessItem extends EntityPathBase<BusinessItem> {

    private static final long serialVersionUID = 979471483L;

    public static final QBusinessItem businessItem = new QBusinessItem("businessItem");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QBusinessItem(String variable) {
        super(BusinessItem.class, forVariable(variable));
    }

    public QBusinessItem(Path<? extends BusinessItem> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBusinessItem(PathMetadata metadata) {
        super(BusinessItem.class, metadata);
    }

}

