package  com.orm.MyORM.Dialect.Clause;

public abstract class Clause {
    protected String paramsString;
    private Clause next;

    public Clause setNext(Clause next) {
        this.next = next;
        return this;
    }

    public abstract String build(String sql);
    public String build() {
        return this.build("");
    }
    protected String buildNext(String sql) {
        if (next == null) {
            return sql;
        }
        return next.build(sql);
    }
}
