package org.company.app.data.entity;

import java.util.Date;

public class TicketEntity {
    private int Ticket_Number;
    private int userID;
    private String TicketDest ;
    private Date departureTime;
    private Date arrivalTime;
    private int ticketPrice;
    private String status;

    @Override
    public String toString() {
        return "TicketEntity{" +
                "Ticket_Number=" + Ticket_Number +
                ", userID=" + userID +
                ", TicketDest='" + TicketDest + '\'' +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", ticketPrice=" + ticketPrice +
                '}';
    }

    public TicketEntity(int ticket_Number, int userID, String ticketDest, Date departureTime, Date arrivalTime, int ticketPrice,String status) {
        Ticket_Number = ticket_Number;
        this.userID = userID;
        TicketDest = ticketDest;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.ticketPrice = ticketPrice;
        this.status = status;
    }

    public TicketEntity(int userID, String ticketDest, Date departureTime, Date arrivalTime, int ticketPrice) {
        this.userID = userID;
        TicketDest = ticketDest;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.ticketPrice = ticketPrice;
    }

    public int getTicket_Number() {
        return Ticket_Number;
    }

    public void setTicket_Number(int ticket_Number) {
        Ticket_Number = ticket_Number;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getTicketDest() {
        return TicketDest;
    }

    public void setTicketDest(String ticketDest) {
        TicketDest = ticketDest;
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

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }
}


/**
 *  Field         | Type
 * ---------------+-------------
 *  Ticket_Number | int(11)
 *  userID        | int(11)
 *  TicketDest    | varchar(45)
 *  departureTime | timestamp
 *  arrivalTime   | timestamp
 *  ticketPrice   | int(11)
 * */