package com.orm.MyORM.Query;

import com.orm.MyORM.Dialect.Clause.FromClause;
import com.orm.MyORM.Dialect.Clause.SelectClause;
import com.orm.MyORM.Dialect.Clause.WhereClause;
import com.orm.MyORM.Dialect.Condition.Condition;
import com.orm.MyORM.Dialect.Value.FieldValue;

import java.util.ArrayList;
import java.util.List;

public class SelectQuery extends Query {
    public SelectQuery() {
        //super(clauseFactory);
    }

    public SelectQuery select() {
        SelectClause select = new SelectClause();
        addClause(select);
        return this;
    }

    public SelectQuery select(String... columns) {
        SelectClause select = new SelectClause(columns);
        addClause(select);
        return this;
    }

    public SelectQuery select(FieldValue... fields) {
        List<String> fieldsInString = new ArrayList<>();
        for (FieldValue field : fields) {
            fieldsInString.add(field.toSql());
        }
        SelectClause select = new SelectClause(fieldsInString.toArray(String[]::new));
        addClause(select);
        return this;
    }

    public SelectQuery from(String table) {
        FromClause from = new FromClause(table);
        addClause(from);
        return this;
    }

    public SelectQuery from(FieldValue value) {
        FromClause from = new FromClause(value);
        addClause(from);
        return this;
    }

    public SelectQuery where(String condition) {
        return this;
    }

    public SelectQuery where(Condition condition) {
        WhereClause where = new WhereClause(condition);
        addClause(where);
        return this;
    }

    public SelectQuery group_by(String... columns) {
        return this;
    }

    public SelectQuery having(String condition) {
        return this;
    }

    public SelectQuery order_by(String... columns) {
        return this;
    }
}
