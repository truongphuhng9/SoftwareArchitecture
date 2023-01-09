package com.orm.MyORM.MySqlDialect.Clause;

import com.orm.MyORM.Dialect.Clause.InsertClause;

public class MySqlInsertClause extends InsertClause {
    public MySqlInsertClause(String table, String... columns) {
        super(table, columns);
    }
}
