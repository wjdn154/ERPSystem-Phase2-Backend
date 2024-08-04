package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.company_registration;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompany is a Querydsl query type for Company
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCompany extends EntityPathBase<Company> {

    private static final long serialVersionUID = -837211659L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCompany company = new QCompany("company");

    public final BooleanPath active = createBoolean("active");

    public final QAddress address;

    public final QBusinessItem businessItem;

    public final StringPath businessRegistrationNumber = createString("businessRegistrationNumber");

    public final QTaxOffice businessTaxOffice;

    public final QBusinessType businessType;

    public final QContact contact;

    public final QCorporateKind corporateKinds;

    public final StringPath corporateRegistrationNumber = createString("corporateRegistrationNumber");

    public final QCorporateType corporateType;

    public final EnumPath<com.megazone.ERPSystem_phase2_Backend.financial.model.enums.EntityType> entityType = createEnum("entityType", com.megazone.ERPSystem_phase2_Backend.financial.model.enums.EntityType.class);

    public final DatePath<java.time.LocalDate> establishmentDate = createDate("establishmentDate", java.time.LocalDate.class);

    public final NumberPath<Integer> fiscalCardinalNumber = createNumber("fiscalCardinalNumber", Integer.class);

    public final DatePath<java.time.LocalDate> fiscalYearEnd = createDate("fiscalYearEnd", java.time.LocalDate.class);

    public final DatePath<java.time.LocalDate> fiscalYearStart = createDate("fiscalYearStart", java.time.LocalDate.class);

    public final QTaxOffice headquartersTaxOffice;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isSme = createBoolean("isSme");

    public final StringPath localIncomeTaxOffice = createString("localIncomeTaxOffice");

    public final StringPath name = createString("name");

    public final QRepresentative representative;

    public QCompany(String variable) {
        this(Company.class, forVariable(variable), INITS);
    }

    public QCompany(Path<? extends Company> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCompany(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCompany(PathMetadata metadata, PathInits inits) {
        this(Company.class, metadata, inits);
    }

    public QCompany(Class<? extends Company> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new QAddress(forProperty("address")) : null;
        this.businessItem = inits.isInitialized("businessItem") ? new QBusinessItem(forProperty("businessItem")) : null;
        this.businessTaxOffice = inits.isInitialized("businessTaxOffice") ? new QTaxOffice(forProperty("businessTaxOffice")) : null;
        this.businessType = inits.isInitialized("businessType") ? new QBusinessType(forProperty("businessType")) : null;
        this.contact = inits.isInitialized("contact") ? new QContact(forProperty("contact")) : null;
        this.corporateKinds = inits.isInitialized("corporateKinds") ? new QCorporateKind(forProperty("corporateKinds")) : null;
        this.corporateType = inits.isInitialized("corporateType") ? new QCorporateType(forProperty("corporateType")) : null;
        this.headquartersTaxOffice = inits.isInitialized("headquartersTaxOffice") ? new QTaxOffice(forProperty("headquartersTaxOffice")) : null;
        this.representative = inits.isInitialized("representative") ? new QRepresentative(forProperty("representative")) : null;
    }

}

