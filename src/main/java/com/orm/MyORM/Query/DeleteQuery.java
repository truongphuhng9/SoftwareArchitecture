package com.orm.MyORM.Query;

import com.orm.MyORM.Dialect.Clause.DeleteClause;
import com.orm.MyORM.Dialect.Clause.InsertClause;
import com.orm.MyORM.Dialect.Clause.ValuesClause;
import com.orm.MyORM.Dialect.Clause.WhereClause;
import com.orm.MyORM.Dialect.Condition.Condition;

public class DeleteQuery extends Query {
    public DeleteQuery() {
    }

    public DeleteQuery delete_from(String table) {
        DeleteClause delete = new DeleteClause(table);
        addClause(delete);
        return this;
    }

    public DeleteQuery where(Condition condition) {
        WhereClause where = new WhereClause(condition);
        addClause(where);
        return this;
    }


}
