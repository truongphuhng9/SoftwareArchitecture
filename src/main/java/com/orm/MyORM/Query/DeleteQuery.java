package  com.orm.MyORM.Query;

import com.orm.MyORM.Dialect.Clause.DeleteClause;
import com.orm.MyORM.Dialect.Clause.FromClause;
import com.orm.MyORM.Dialect.Clause.WhereClause;
import com.orm.MyORM.Dialect.Condition.Condition;

public class DeleteQuery extends Query {

    public DeleteQuery(){

    }

    public DeleteQuery delete(){
        DeleteClause deleteClause=new DeleteClause();
        addClause(deleteClause);
        return this;
    }

    public DeleteQuery from(String tableName) {
        FromClause fromClause = new FromClause(tableName);
        addClause(fromClause);
        return this;
    }

    public DeleteQuery where(Condition condition) {
        WhereClause whereClause = new WhereClause(condition);
        addClause(whereClause);
        return this;
    }

    public DeleteQuery where(String condition) {
        WhereClause whereClause = new WhereClause(condition);
        addClause(whereClause);
        return this;
    }
}
