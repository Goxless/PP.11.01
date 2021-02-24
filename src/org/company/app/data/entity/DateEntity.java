package org.company.app.data.entity;

import java.util.Date;

public class DateEntity
{
    private int id;
    private Date date;

    public DateEntity(int id, Date date) {
        this.id = id;
        this.date = date;
    }

    public DateEntity(Date date) {
        this(-1, date);
    }

    @Override
    public String toString() {
        return "DateEntity{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
