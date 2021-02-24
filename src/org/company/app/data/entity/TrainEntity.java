package org.company.app.data.entity;

public class TrainEntity {

    private int TrainID;
    private String trainNumber;
    private String trainName;
    private String trainType;
    private String trainDest;
    private int carCount;
    private int Schedule_ride_id;



    @Override
    public String toString() {
        return "TrainEntity{" +
                "TrainID=" + TrainID +
                ", trainNumber='" + trainNumber + '\'' +
                ", trainName='" + trainName + '\'' +
                ", trainType='" + trainType + '\'' +
                ", trainDest='" + trainDest + '\'' +
                ", carCount=" + carCount +
                ", Schedule_ride_id=" + Schedule_ride_id +
                '}';
    }

    public TrainEntity(int trainID,String trainNumber, String trainName, String trainType, String trainDest, int carCount,int schedule_ride_id) {
        TrainID = trainID;
        Schedule_ride_id = schedule_ride_id;
        this.trainNumber = trainNumber;
        this.trainName = trainName;
        this.trainType = trainType;
        this.trainDest = trainDest;
        this.carCount = carCount;
    }

    public TrainEntity(){
    }

    public TrainEntity setId(int id) {
        this.TrainID = id;
        return this;
    }

    public TrainEntity( String trainNumber, String trainName, String trainType, String trainDest, int carCount, int schedule_ride_id) {
        this(-1,trainNumber,trainName,trainType,trainDest,carCount,schedule_ride_id);
    }

    public int getTrainID() {
        return TrainID;
    }

    public void setTrainID(int trainID) {
        TrainID = trainID;
    }

    public int getSchedule_ride_id() {
        return Schedule_ride_id;
    }

    public void setSchedule_ride_id(int schedule_ride_id) {
        Schedule_ride_id = schedule_ride_id;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

    public String getTrainDest() {
        return trainDest;
    }

    public void setTrainDest(String trainDest) {
        this.trainDest = trainDest;
    }

    public int getCarCount() {
        return carCount;
    }

    public void setCarCount(int carCount) {
        this.carCount = carCount;
    }
}

