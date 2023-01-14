package  com.orm.MyORM.Dialect.Value;

public class FieldValue implements Value{
    private String value;
    public FieldValue(String value) {
        this.value = value;
    }
    @Override
    public String toSql() {
        return this.value;
    }
}
