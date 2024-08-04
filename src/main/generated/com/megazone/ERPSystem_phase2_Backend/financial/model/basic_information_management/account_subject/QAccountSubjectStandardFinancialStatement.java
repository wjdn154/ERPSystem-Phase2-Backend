package com.megazone.ERPSystem_phase2_Backend.financial.model.basic_information_management.account_subject;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAccountSubjectStandardFinancialStatement is a Querydsl query type for AccountSubjectStandardFinancialStatement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAccountSubjectStandardFinancialStatement extends EntityPathBase<AccountSubjectStandardFinancialStatement> {

    private static final long serialVersionUID = -588680863L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAccountSubjectStandardFinancialStatement accountSubjectStandardFinancialStatement = new QAccountSubjectStandardFinancialStatement("accountSubjectStandardFinancialStatement");

    public final QAccountSubjectStructure accountSubjectStructure;

    public final StringPath code = createString("code");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QAccountSubjectStandardFinancialStatement(String variable) {
        this(AccountSubjectStandardFinancialStatement.class, forVariable(variable), INITS);
    }

    public QAccountSubjectStandardFinancialStatement(Path<? extends AccountSubjectStandardFinancialStatement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAccountSubjectStandardFinancialStatement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAccountSubjectStandardFinancialStatement(PathMetadata metadata, PathInits inits) {
        this(AccountSubjectStandardFinancialStatement.class, metadata, inits);
    }

    public QAccountSubjectStandardFinancialStatement(Class<? extends AccountSubjectStandardFinancialStatement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.accountSubjectStructure = inits.isInitialized("accountSubjectStructure") ? new QAccountSubjectStructure(forProperty("accountSubjectStructure")) : null;
    }

}

