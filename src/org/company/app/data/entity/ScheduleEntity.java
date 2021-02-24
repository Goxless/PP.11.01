package org.company.app.data.entity;

import java.sql.Timestamp;
import java.util.Date;

public class ScheduleEntity {

    private int ride_id;
    private Date departureTime;
    private Date arrivalTime;
    private String StartPoint;
    private String EndPoint;
    private double price;

    public String getStartPoint() {
        return StartPoint;
    }

    public void setStartPoint(String startPoint) {
        StartPoint = startPoint;
    }

    public String getEndPoint() {
        return EndPoint;
    }

    public void setEndPoint(String endPoint) {
        EndPoint = endPoint;
    }

    public ScheduleEntity(int ride_id, Timestamp departureTime, Timestamp arrivalTime, String StartPoint, String EndPoint,int Price) {
        this.ride_id = ride_id;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.StartPoint = StartPoint;
        this.EndPoint  = EndPoint;
        this.price = Price;
    }

    public ScheduleEntity(Timestamp departureTime, Timestamp arrivalTime,String StartPoint,String EndPoint,int Price){
        this(-1,departureTime,arrivalTime,StartPoint,EndPoint,Price);
    }

    public int getRide_id() {
        return ride_id;
    }

    public void setRide_id(int ride_id) {
        this.ride_id = ride_id;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ScheduleEntity{" +
                "ride_id=" + ride_id +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                '}';
    }

    public ScheduleEntity setId(int id) {
        this.ride_id = id;
        return this;
    }

}




/*
    Timestamp timestamp = resultSet.getTimestamp("ts");
    java.util.Date date = timestamp; // You can just upcast.
 */