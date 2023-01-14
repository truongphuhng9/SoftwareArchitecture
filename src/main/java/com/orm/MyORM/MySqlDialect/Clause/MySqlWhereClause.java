package  com.orm.MyORM.MySqlDialect.Clause;

import  com.orm.MyORM.Dialect.Clause.WhereClause;
import  com.orm.MyORM.Dialect.Condition.Condition;

public class MySqlWhereClause extends WhereClause {
    public MySqlWhereClause(Condition condition) {
        super(condition);
    }
}
