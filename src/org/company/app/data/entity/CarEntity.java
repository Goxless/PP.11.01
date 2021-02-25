package org.company.app.data.entity;

public class CarEntity {

    private int CarID;
    private String CarType;
    private int Capacity;
    private int Train_ID;
    private int Fullness;

    public CarEntity(String carType, int capacity, int train_ID,int fullness) {
        this.CarID = -1;
        this.CarType = carType;
        this.Capacity = capacity;
        this.Train_ID = train_ID;
        this.Fullness = fullness;
    }

    public CarEntity(int carID, String carType, int capacity, int train_ID,int fullness) {
        this.CarID = carID;
        this.CarType = carType;
        this.Capacity = capacity;
        this.Train_ID = train_ID;
        this.Fullness = fullness;
    }



    public int getCarID() {
        return CarID;
    }

    public void setCarID(int carID) {
        CarID = carID;
    }

    public String getCarType() {
        return CarType;
    }

    public void setCarType(String carType) {
        CarType = carType;
    }

    public int getCapacity() {
        return Capacity;
    }

    public void setCapacity(int capacity) {
        Capacity = capacity;
    }

    public int getTrain_ID() {
        return Train_ID;
    }

    public void setTrain_ID(int train_ID) {
        Train_ID = train_ID;
    }


    public int getFullness() {
        return Fullness;
    }

    public void setFullness(int fullness) {
        Fullness = fullness;
    }



    @Override
    public String toString() {
        return "CarEntity{" +
                "CarID=" + CarID +
                ", CarType='" + CarType + '\'' +
                ", Capacity=" + Capacity +
                ", Train_ID=" + Train_ID +
                '}';
    }

}
