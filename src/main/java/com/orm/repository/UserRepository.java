package com.orm.repository;

import com.orm.MyORM.Dialect.Condition.EqualCondition;
import com.orm.MyORM.Dialect.Value.FieldValue;
import com.orm.MyORM.Dialect.Value.StringValue;
import com.orm.MyORM.Query.InsertQuery;
import com.orm.MyORM.Query.SelectQuery;
import com.orm.MyORM.Repository.RepositoryImpl;
import com.orm.entity.User;

public class UserRepository extends RepositoryImpl<User, Integer> {

    public UserRepository() {
        super(User.class, Integer.class);
    }

    public User getUserByUsername(String username) throws Exception {
        SelectQuery selectQuery = new SelectQuery();
        String sql = selectQuery.select().from("users").where(new EqualCondition(new FieldValue("username"), new StringValue(username))).build();
        return this.query(sql);
    }

    public boolean addNewUser(String username, String password, String fullname) throws Exception {
        User user = new User();
        user.setUsername(username);
        user.setFullname(fullname);
        user.setPassword(password);

        return this.insertOne(user);
    }
}
