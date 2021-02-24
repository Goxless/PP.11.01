package org.company.app.data.manager;

import org.company.app.Application;
import org.company.app.data.entity.ScheduleEntity;
import org.company.app.data.entity.TicketEntity;
import org.company.app.data.entity.TrainEntity;
import org.company.app.util.BaseManager;
import org.company.app.util.MysqlDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketEntityManager extends BaseManager  {


    public TicketEntityManager(MysqlDatabase database) {
        super(database);
    }

    public List<TicketEntity> getAllById(int id) throws SQLException
    {

        try(Connection c = database.getConnection())
        {
            String sql = "SELECT * FROM Ticket WHERE userID = ?";
            PreparedStatement s = c.prepareStatement(sql);
            System.out.println(id);
            s.setInt(1, id);

            ResultSet resultSet = s.executeQuery();

            List<TicketEntity> tickets = new ArrayList<>();
            while(resultSet.next()) {
                tickets.add(new TicketEntity(
                        resultSet.getInt("Ticket_Number"),
                        resultSet.getInt("userID"),
                        resultSet.getString("TicketDest"),
                        resultSet.getTimestamp("departureTime"),
                        resultSet.getTimestamp("arrivalTime"),
                        resultSet.getInt("ticketPrice"),
                        resultSet.getString("Status")
                ));
            }
            /**
             *
             private int Ticket_Number;
             private int userID;
             private String TicketDest ;
             private Date departureTime;
             private Date arrivalTime;
             private int ticketPrice;

             INSERT INTO Train(trainNumber, trainName, trainType, trainDest, carCount,Schedule_ride_id) VALUES('123', '123', '123','123',123,123)

             */
            return tickets;
        }



    }
    public void add(ScheduleEntity schedule) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            TrainEntityManager trainEntityManager = new TrainEntityManager(Application.getInstance().getDatabase());

            String sql = "INSERT INTO ticket(userID,TicketDest,departureTime,arrivalTime,ticketPrice,CarID,Status) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement s = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            s.setInt(1, Application.getUser().getId());
            s.setString(2, schedule.getEndPoint());
            s.setTimestamp(3, new Timestamp(schedule.getDepartureTime().getTime()));
            s.setTimestamp(4, new Timestamp(schedule.getArrivalTime().getTime()));
            s.setInt(5, (int)schedule.getPrice());
            s.setInt(6, 4);
            s.setString(7,"Awaiting");

            s.executeUpdate();

            ResultSet keys = s.getGeneratedKeys();

            if (keys.next()) {
                return;
            }
            throw new SQLException("Ticket not added");
        }
    }

    public int deleteById(int id) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "DELETE FROM ticket WHERE Ticket_Number=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setInt(1, id);

            return s.executeUpdate();
        }
    }

    public int delete(TicketEntity ticket) throws SQLException
    {
        return deleteById(ticket.getTicket_Number());
    }



}
