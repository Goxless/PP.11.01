package org.company.app.data.manager;

import org.company.app.data.entity.ClientEntity;
import org.company.app.data.entity.TrainEntity;
import org.company.app.util.BaseManager;
import org.company.app.util.MysqlDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainEntityManager extends BaseManager {


    public TrainEntityManager (MysqlDatabase database) {
        super(database);
    }

    public void add(TrainEntity train) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "INSERT INTO Train(trainNumber, trainName, trainType, trainDest, carCount,Schedule_ride_id) VALUES(?,?,?,?,?,?)";
            PreparedStatement s = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            s.setString(1, train.getTrainNumber());
            s.setString(2, train.getTrainName());
            s.setString(3, train.getTrainType());
            s.setString(4, train.getTrainDest());
            s.setInt(5, train.getCarCount());
            s.setInt(6,train.getSchedule_ride_id());

            s.executeUpdate();

            ResultSet keys = s.getGeneratedKeys();

            if (keys.next()) {
                train.setId(keys.getInt(1));
                return;
            }

            //System.out.println(train);

            throw new SQLException("Train not added");
        }
    }

    public TrainEntity getById(int id) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "SELECT * FROM train WHERE id=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setInt(1, id);

            ResultSet resultSet = s.executeQuery();
            if(resultSet.next()) {
                return new TrainEntity(
                        resultSet.getInt("TrainID"),
                        resultSet.getString("trainNumber"),
                        resultSet.getString("trainName"),
                        resultSet.getString("trainType"),
                        resultSet.getString("trainDest"),
                        resultSet.getInt("carCount"),
                        resultSet.getInt("Schedule_ride_id")
                );
            }
            /**
             * private int TrainID;
             *     private String trainNumber;
             *     private String trainName;
             *     private String trainType;
             *     private String trainDest;
             *     private int carCount;
             *        private int Schedule_ride_id;
             *
             * */
            return null;
        }
    }

    public List<TrainEntity> getAll() throws SQLException
    {

        try(Connection c = database.getConnection())
        {
            String sql = "SELECT * FROM Train";
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery(sql);

            List<TrainEntity> trains = new ArrayList<>();
            while(resultSet.next()) {
                trains.add(new TrainEntity(
                        resultSet.getInt("TrainID"),
                        resultSet.getString("trainNumber"),
                        resultSet.getString("trainName"),
                        resultSet.getString("trainType"),
                        resultSet.getString("trainDest"),
                        resultSet.getInt("carCount"),
                        resultSet.getInt("Schedule_ride_id")
                ));
            }

            /**TrainID        | int(11)
             *
             *  "TrainID", "trainNumber", "trainName", "trainType", "trainDest","carCount","Schedule_ride_id"
             *
             trainNumber      | varchar(45)
             trainName        | varchar(45)
             trainType        | varchar(45)
             trainDest        | varchar(45)
             carCount         | int(11)
             Schedule_ride_id | int(11)

             INSERT INTO Train(trainNumber, trainName, trainType, trainDest, carCount,Schedule_ride_id) VALUES('123', '123', '123','123',123,123)

             */

            return trains;
        }
    }

    public int update(TrainEntity trainEntity) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "UPDATE Train SET trainNumber=?, trainName=?, trainType=?, trainDest=?, carCount=?, Schedule_ride_id=? WHERE TrainID=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setString(1, trainEntity.getTrainNumber());
            s.setString(2, trainEntity.getTrainName());
            s.setString(3, trainEntity.getTrainType());
            s.setString(4, trainEntity.getTrainDest());
            s.setInt(5, trainEntity.getCarCount());
            s.setInt(6, trainEntity.getSchedule_ride_id());
            s.setInt(7, trainEntity.getTrainID());

            return s.executeUpdate();
        }
    }

    public int deleteById(int id) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "DELETE FROM Train WHERE TrainID=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setInt(1, id);

            return s.executeUpdate();
        }
    }

    public int delete(TrainEntity trainEntity) throws SQLException
    {
        return deleteById(trainEntity.getTrainID());
    }
}
