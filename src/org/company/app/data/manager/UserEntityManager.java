package org.company.app.data.manager;

import com.mysql.cj.result.SqlDateValueFactory;
import org.company.app.data.entity.ClientEntity;
import org.company.app.data.entity.ScheduleEntity;
import org.company.app.data.entity.UserEntity;
import org.company.app.util.BaseManager;
import org.company.app.util.DialogUtil;
import org.company.app.util.MysqlDatabase;

import java.sql.*;

public class UserEntityManager extends BaseManager {


    public UserEntityManager(MysqlDatabase database) {
        super(database);
    }

    public void add(UserEntity userEntity) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "INSERT INTO User(Login, Password, FirstName,LastName,Phone,AccessMode ) VALUES(?,?,?,?,?,?)";
            PreparedStatement s = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            s.setString(1, userEntity.getLogin());
            s.setString(2, userEntity.getPassword());
            s.setString(3, userEntity.getFirstName());
            s.setString(4, userEntity.getLastName());
            s.setString(5, userEntity.getPhone());
            s.setString(6, userEntity.getAccessMode());

            s.executeUpdate();

            ResultSet keys = s.getGeneratedKeys();

            if (keys.next()) {
                userEntity.setId(keys.getInt(1));
                return;
            }

            throw new SQLException("User not added");
        }
    }

    public UserEntity getById(int id) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "SELECT * FROM User WHERE id=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setInt(1, id);
            ResultSet resultSet = s.executeQuery();

            if(resultSet.next()) {
                return new UserEntity(
                        resultSet.getInt("Id"),
                        resultSet.getString("Login"),
                        resultSet.getString("Password"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Phone"),
                        resultSet.getString("AccessMode")
                );
            }
            return null;
        }
    }

    public UserEntity getByLogin(String login) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "SELECT * FROM User WHERE Login =?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setString(1, login);
            ResultSet resultSet = s.executeQuery();

            if(resultSet.next()) {
                return new UserEntity(
                        resultSet.getInt("Id"),
                        resultSet.getString("Login"),
                        resultSet.getString("Password"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Phone"),
                        resultSet.getString("AccessMode")
                );
            }
            return null;
        }
    }

}
