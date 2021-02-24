package org.company.app.data.entity;

import org.company.app.Application;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Date;
import java.util.Random;

public class ClientEntity
{
    private int id;
    private String firstname;
    private String lastname;
    private String patronymic;
    private Date birthday;
    private Date regDate;
    private String email;
    private String phone;
    private char genderCode;
    private String photoPath;
    private Boolean testBoolean = new Random().nextBoolean();
    private ImageIcon icon;

    public ClientEntity(int id, String firstname, String lastname, String patronymic, Date birthday, Date regDate, String email, String phone, char genderCode, String photoPath) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.regDate = regDate;
        this.email = email;
        this.phone = phone;
        this.genderCode = genderCode;
        this.photoPath = photoPath;

        URL iconUrl = Application.class.getClassLoader().getResource(photoPath);
        if(iconUrl != null) {
            this.icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(iconUrl));
        }
    }

    public ClientEntity(String firstname, String lastname, String patronymic, Date birthday, Date regDate, String email, String phone, char genderCode, String photoPath) {
        this(-1, firstname, lastname, patronymic, birthday, regDate, email, phone, genderCode, photoPath);
    }

    @Override
    public String toString() {
        return "ClientEntity{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthday=" + birthday +
                ", regDate=" + regDate +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", genderCode=" + genderCode +
                ", photoPath='" + photoPath + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public ClientEntity setId(int id) {
        this.id = id;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public ClientEntity setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public ClientEntity setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public ClientEntity setPatronymic(String patronymic) {
        this.patronymic = patronymic;
        return this;
    }

    public Date getBirthday() {
        return birthday;
    }

    public ClientEntity setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public Date getRegDate() {
        return regDate;
    }

    public ClientEntity setRegDate(Date regDate) {
        this.regDate = regDate;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ClientEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public ClientEntity setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public char getGenderCode() {
        return genderCode;
    }

    public ClientEntity setGenderCode(char genderCode) {
        this.genderCode = genderCode;
        return this;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public ClientEntity setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
        return this;
    }
}
