package org.company.app.data.manager;

import org.company.app.data.entity.ClientEntity;
import org.company.app.data.entity.ScheduleEntity;
import org.company.app.data.entity.TrainEntity;
import org.company.app.util.BaseManager;
import org.company.app.util.MysqlDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleEntityManager extends BaseManager {


    public ScheduleEntityManager(MysqlDatabase database) {
        super(database);
    }

    public void add(ScheduleEntity schedule) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "INSERT INTO schedule(StartPoint, EndPoint, departureTime, arrivalTime,price) VALUES(?,?,?,?,?)";
            PreparedStatement s = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            s.setString(1, schedule.getStartPoint());
            s.setString(2, schedule.getEndPoint());
            s.setTimestamp(3, new Timestamp(schedule.getDepartureTime().getTime()));
            s.setTimestamp(4, new Timestamp(schedule.getArrivalTime().getTime()));
            s.setInt(5,(int)schedule.getPrice());
            s.executeUpdate();

            ResultSet keys = s.getGeneratedKeys();

            if (keys.next()) {
                schedule.setId(keys.getInt(1));
                return;
            }

            throw new SQLException("Schedule not added");
        }
    }

    public ScheduleEntity getById(int id) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "SELECT * FROM Schedule WHERE ride_id=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setInt(1, id);

            ResultSet resultSet = s.executeQuery();
            if(resultSet.next()) {
                return new ScheduleEntity(
                        resultSet.getInt("ride_ID"),
                        resultSet.getTimestamp("departureTime"),
                        resultSet.getTimestamp("arrivalTime"),
                        resultSet.getString("StartPoint"),
                        resultSet.getString("EndPoint"),
                        resultSet.getInt("Price")
                );
            }

            return null;
        }
    }

    public List<String> getUniqueStartPoints() throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "SELECT DISTINCT StartPoint FROM schedule;";
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery(sql);

            List<String> startPoints = new ArrayList<>();

            while(resultSet.next()) {
                startPoints.add(resultSet.getString("StartPoint"));
            }

            return startPoints;

        }
    }

    public List<String> getUniqueFinishPoints() throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "SELECT DISTINCT EndPoint FROM schedule;";
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery(sql);

            List<String> startPoints = new ArrayList<>();

            while(resultSet.next()) {
                startPoints.add(resultSet.getString("EndPoint"));
            }

            return startPoints;

        }
    }
    // Дублирование кода можно было бы переписать,но мне впадлу
    public List<ScheduleEntity> getFindedRoutes(String start,String finish) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "SELECT * FROM schedule WHERE StartPoint = ? AND EndPoint = ? ;";
            PreparedStatement s = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            s.setString(1, start);
            s.setString(2, finish);

            ResultSet resultSet = s.executeQuery();

            List<ScheduleEntity> schedules = new ArrayList<>();

            while(resultSet.next()) {
                schedules.add(new ScheduleEntity(
                        resultSet.getInt("ride_ID"),
                        resultSet.getTimestamp("departureTime"),
                        resultSet.getTimestamp("arrivalTime"),
                        resultSet.getString("StartPoint"),
                        resultSet.getString("EndPoint"),
                        resultSet.getInt("Price")
                ));
            }
            return schedules;

        }
    }

    public List<ScheduleEntity> getAll() throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "SELECT * FROM Schedule";
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery(sql);

            List<ScheduleEntity> schedules = new ArrayList<>();
            while(resultSet.next()) {
                schedules.add(new ScheduleEntity(
                        resultSet.getInt("ride_ID"),
                        resultSet.getTimestamp("departureTime"),
                        resultSet.getTimestamp("arrivalTime"),
                        resultSet.getString("StartPoint"),
                        resultSet.getString("EndPoint"),
                        resultSet.getInt("Price")
                ));
            }

            return schedules;
        }
    }

    public int update(ScheduleEntity schedule) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "UPDATE schedule SET StartPoint=?, EndPoint=?, departureTime=?, arrivalTime=?,price=? WHERE ride_id=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setString(1, schedule.getStartPoint());
            s.setString(2, schedule.getEndPoint());
            s.setTimestamp(3, new Timestamp(schedule.getArrivalTime().getTime()));
            s.setTimestamp(4, new Timestamp(schedule.getDepartureTime().getTime()));
            s.setInt(5,(int)schedule.getPrice());
            s.setInt(6, schedule.getRide_id());


            return s.executeUpdate();
        }
    }

    public int deleteById(int id) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "DELETE FROM schedule WHERE ride_id=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setInt(1, id);

            return s.executeUpdate();
        }
    }

    public int delete(ScheduleEntity scheduleEntity) throws SQLException
    {
        return deleteById(scheduleEntity.getRide_id());
    }

}
