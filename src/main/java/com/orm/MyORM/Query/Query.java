package com.orm.MyORM.Query;

import com.orm.MyORM.Dialect.Clause.*;
import com.orm.MyORM.Dialect.ClauseFactory;

import java.util.ArrayList;
import java.util.List;

public class Query{
    protected Clause head;
    protected Clause tail;
    protected List<Clause> clauses;
    protected ClauseFactory clauseFactory;

    public Query() {
        this.head = null;
        this.tail = null;
        this.clauses = new ArrayList<>();
        //this.clauseFactory = clauseFactory;
    }
    protected void addClause(Clause clause) {
        if (this.head == null) {
            this.head = clause;
        } else {
            this.tail.setNext(clause);
        }
        this.tail = clause;
        this.clauses.add(clause);
    }

    public String build(String sql) {
        if(this.head != null) {
            return head.build();
        }
        return "";
    }

    public String build() {
        return this.build("");
    }
}
