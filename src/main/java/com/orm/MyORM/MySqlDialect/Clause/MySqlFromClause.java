package com.orm.MyORM.MySqlDialect.Clause;

import com.orm.MyORM.Dialect.Clause.FromClause;

public class MySqlFromClause extends FromClause {
    public MySqlFromClause(String tableName) {
        super(tableName);
    }
}
