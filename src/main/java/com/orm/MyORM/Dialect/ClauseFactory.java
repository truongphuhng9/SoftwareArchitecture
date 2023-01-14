package  com.orm.MyORM.Dialect;

import  com.orm.MyORM.Dialect.Clause.*;
import  com.orm.MyORM.Dialect.Condition.Condition;

public interface ClauseFactory {
    public SelectClause createSelectClause();
    public FromClause createFromClause(String table);
    public WhereClause createWhereClause(Condition condition);
    public InsertClause createInsertClause(String table, String... columns);
    public ValuesClause createValuesClause(String... values);
    public UpdateClause createUpdateClause();
}
