package org.company.app.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BaseManager
{
    protected MysqlDatabase database;

    public BaseManager(MysqlDatabase database) {
        this.database = database;
    }

    public int createTable(String tableName, String fields) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "CREATE TABLE IF NOT EXISTS ?(?)";
            PreparedStatement s = c.prepareStatement(sql);
            s.setString(1, tableName);
            s.setString(2, fields);

            return s.executeUpdate();
        }
    }

    public int deleteTable(String tableName) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "DROP TABLE IF EXISTS ?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setString(1, tableName);

            return s.executeUpdate();
        }
    }

    public MysqlDatabase getDatabase() {
        return database;
    }

    public void setDatabase(MysqlDatabase database) {
        this.database = database;
    }
}
