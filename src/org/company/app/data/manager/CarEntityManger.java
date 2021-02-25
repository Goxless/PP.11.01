package org.company.app.data.manager;

import org.company.app.Application;
import org.company.app.data.entity.CarEntity;
import org.company.app.data.entity.TrainEntity;
import org.company.app.data.entity.UserEntity;
import org.company.app.util.BaseManager;
import org.company.app.util.DialogUtil;
import org.company.app.util.MysqlDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarEntityManger extends BaseManager {

    private final TrainEntityManager trainEntityManager = new TrainEntityManager(Application.getInstance().getDatabase());

    public CarEntityManger(MysqlDatabase database) {
        super(database);
    }

    //int trainID, int capacity,String type

    public void add(CarEntity carEntity) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "INSERT INTO Car(CarType, carCapacity,Train_ID,fullness) VALUES(?,?,?,?)";
            PreparedStatement s = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            s.setString(1,carEntity.getCarType());
            s.setInt(2, carEntity.getCapacity());
            s.setInt(3, carEntity.getTrain_ID());
            s.setInt(4,carEntity.getFullness());

            s.executeUpdate();

            ResultSet keys = s.getGeneratedKeys();

            if (keys.next()) {
                carEntity.setCarID(keys.getInt(1));
                return;
            }

            throw new SQLException("Car not added");
        }
    }

    public List<CarEntity> getAll() throws SQLException
    {

        try(Connection c = database.getConnection())
        {
            String sql = "SELECT * FROM Car";
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery(sql);

            List<CarEntity> cars = new ArrayList<>();
            while(resultSet.next()) {
                cars.add(new CarEntity(
                        resultSet.getInt("CarID"),
                        resultSet.getString("CarType"),
                        resultSet.getInt("CarCapacity"),
                        resultSet.getInt("TrainID"),
                        resultSet.getInt("fullness")
                ));
            }

            return cars;
        }
    }


    public List<CarEntity> getAllByTrainID(int id) throws SQLException
    {

        try(Connection c = database.getConnection())
        {
            String sql = "SELECT * FROM Car where Train_ID = ?";
            PreparedStatement s = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            s.setInt(1,id);

            ResultSet resultSet =  s.executeQuery();

            List<CarEntity> cars = new ArrayList<>();
            while(resultSet.next()) {
                cars.add(new CarEntity(
                        resultSet.getInt("CarID"),
                        resultSet.getString("CarType"),
                        resultSet.getInt("CarCapacity"),
                        resultSet.getInt("Train_ID"),
                        resultSet.getInt("fullness")
                ));
            }

            return cars;
        }
    }



    public int setFullness(CarEntity carEntity) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "UPDATE Car SET fullness =? WHERE CarID=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setInt(1, carEntity.getFullness() + 1);
            s.setInt(2, carEntity.getCarID());

            return s.executeUpdate();
        }


    }



    public CarEntity getById(int id) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "SELECT * FROM Car WHERE id=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setInt(1, id);
            ResultSet resultSet = s.executeQuery();

            if(resultSet.next()) {
                return new CarEntity(
                        resultSet.getInt("CarID"),
                        resultSet.getString("CarType"),
                        resultSet.getInt("CarCapacity"),
                        resultSet.getInt("TrainID"),
                        resultSet.getInt("fullness")
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

    public int deleteByTrainId(int id) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "DELETE FROM Car WHERE Train_ID=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setInt(1, id);

            return s.executeUpdate();
        }
    }

    public boolean canUpdateFullness(int scheduleId) throws SQLException
    {
        try(Connection c = database.getConnection())
        {

            List<TrainEntity> trains = trainEntityManager.getByScheduleId(scheduleId);
            List<CarEntity> cars;


            for (int i = 0; i < trains.size(); i++){
                //int tmp = trains.get(i).getCarCount();
                cars = this.getAllByTrainID(trains.get(i).getTrainID());
                if(cars.size() == 0)
                    continue;

                for (int j = 0; j <trains.get(i).getCarCount(); j++){

                    if(trains.size() == 0) {
                        continue;
                    }

                    if (cars.get(j).getCapacity() == cars.get(j).getFullness() && i == trains.size() - 1 && j == trains.get(i).getCarCount()-1 ){ //типа дошли до последнего вагона и там нет мест и вообще катастрофа



                        return false;
                    }

                    else if(cars.get(j).getFullness() < cars.get(j).getCapacity()){
                        setFullness(cars.get(j));
                        return true;
                    }
                }
            }

            return false;
        }
    }




}
