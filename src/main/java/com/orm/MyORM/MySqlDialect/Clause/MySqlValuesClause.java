package  com.orm.MyORM.MySqlDialect.Clause;

import  com.orm.MyORM.Dialect.Clause.ValuesClause;

public class MySqlValuesClause extends ValuesClause {
    public MySqlValuesClause(String... columns) {
        super(columns);
    }
}
