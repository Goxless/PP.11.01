package org.company.app.data.manager;

import org.company.app.data.entity.ClientEntity;
import org.company.app.util.MysqlDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientEntityManager
{
    private MysqlDatabase database;

    public ClientEntityManager(MysqlDatabase database) {
        this.database = database;
    }

    public void add(ClientEntity client) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "INSERT INTO Client(FirstName, LastName, Patronymic, Birthday, RegistrationDate, Email, Phone, GenderCode, PhotoPath) values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement s = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            s.setString(1, client.getFirstname());
            s.setString(2, client.getLastname());
            s.setString(3, client.getPatronymic());
            s.setTimestamp(4, new Timestamp(client.getBirthday().getTime()));
            s.setTimestamp(5, new Timestamp(client.getRegDate().getTime()));
            s.setString(6, client.getEmail());
            s.setString(7, client.getPhone());
            s.setString(8, String.valueOf(client.getGenderCode()));
            s.setString(9, client.getPhotoPath());

            s.executeUpdate();

            ResultSet keys = s.getGeneratedKeys();
            if (keys.next()) {
                client.setId(keys.getInt(1));
                return;
            }

            throw new SQLException("Client not added");
        }
    }

    public ClientEntity getById(int id) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "SELECT * FROM Client WHERE id=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setInt(1, id);

            ResultSet resultSet = s.executeQuery();
            if(resultSet.next()) {
                return new ClientEntity(
                        resultSet.getInt("ID"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Patronymic"),
                        resultSet.getTimestamp("Birthday"),
                        resultSet.getTimestamp("RegistrationDate"),
                        resultSet.getString("Email"),
                        resultSet.getString("Phone"),
                        resultSet.getString("GenderCode").charAt(0),
                        resultSet.getString("PhotoPath")
                );
            }

            return null;
        }
    }

    public List<ClientEntity> getAll() throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "SELECT * FROM Client";
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery(sql);

            List<ClientEntity> clients = new ArrayList<>();
            while(resultSet.next()) {
                clients.add(new ClientEntity(
                        resultSet.getInt("ID"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("Patronymic"),
                        resultSet.getTimestamp("Birthday"),
                        resultSet.getTimestamp("RegistrationDate"),
                        resultSet.getString("Email"),
                        resultSet.getString("Phone"),
                        resultSet.getString("GenderCode").charAt(0),
                        resultSet.getString("PhotoPath")
                ));
            }
            return clients;
        }
    }

    public int update(ClientEntity client) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "UPDATE Client SET FirstName=?, LastName=?, Patronymic=?, Birthday=?, RegistrationDate=?, Email=?, Phone=?, GenderCode=?, PhotoPath=? WHERE id=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setString(1, client.getFirstname());
            s.setString(2, client.getLastname());
            s.setString(3, client.getPatronymic());
            s.setTimestamp(4, new Timestamp(client.getBirthday().getTime()));
            s.setTimestamp(5, new Timestamp(client.getRegDate().getTime()));
            s.setString(6, client.getEmail());
            s.setString(7, client.getPhone());
            s.setString(8, String.valueOf(client.getGenderCode()));
            s.setString(9, client.getPhotoPath());
            s.setInt(10, client.getId());

            return s.executeUpdate();
        }
    }

    public int deleteById(int id) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "DELETE FROM Client WHERE id=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setInt(1, id);

            return s.executeUpdate();
        }
    }

    public int delete(ClientEntity client) throws SQLException
    {
        return deleteById(client.getId());
    }
}
