package org.company.app.data.manager;

import org.company.app.data.entity.DateEntity;
import org.company.app.util.BaseManager;
import org.company.app.util.MysqlDatabase;

import java.sql.*;

public class DateEntityManager extends BaseManager
{
    public DateEntityManager(MysqlDatabase database) {
        super(database);
    }

    public void add(DateEntity entity) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "INSERT INTO date_entities(date_value) values(?)";
            PreparedStatement s = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            s.setTimestamp(1, new Timestamp(entity.getDate().getTime()));
            s.executeUpdate();

            ResultSet keys = s.getGeneratedKeys();
            if(keys.next()) {
                entity.setId(keys.getInt(1));
                return;
            }

            throw new SQLException("User not added");
        }
    }

    public DateEntity getById(int id) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "SELECT * FROM date_entities WHERE id=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setInt(1, id);

            ResultSet resultSet = s.executeQuery();
            if(resultSet.next()) {
                return new DateEntity(
                        resultSet.getInt("id"),
                        //так как TimeStamp наследует Date то происходит неявное приведение типов и можно использовать сразу его
                        resultSet.getTimestamp("date_value")
                );
            }

            return null;
        }
    }
}
