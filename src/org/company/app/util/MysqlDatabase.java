package org.company.app.util;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MysqlDatabase
{
    private final String address;
    private final int port;
    private final String db;
    private final String user;
    private final String pass;

    private MysqlDataSource source; /** подключение лежит здеся*/

    public MysqlDatabase(String address, int port, String db, String user, String pass) {
        this.address = address;
        this.port = port;
        this.db = db;
        this.user = user;
        this.pass = pass;
    }

    public MysqlDatabase(String address, String db, String user, String pass) {
        this(address, 3306, db, user, pass);
    }

    public Connection getConnection() throws SQLException
    {
        if(source == null) {
            source = new MysqlDataSource();

            source.setServerName(address);
            source.setDatabaseName(db);
            source.setPort(port);
            source.setUser(user);
            source.setPassword(pass);

            source.setServerTimezone("UTC");
            source.setCharacterEncoding("UTF-8");
        }

        
        return source.getConnection();
    }
}
