package org.company.app.data.entity;

public class UserEntity {


    private int Id;
    private String Login;
    private String Password;
    private String FirstName;
    private String LastName;
    private String Phone;
    private String AccessMode = "default";

    public UserEntity(){
    }

    public UserEntity(int id, String login, String password, String firstName, String lastName, String phone,String accessMode) {
        this.Id = id;
        this.Login = login;
        this.Password = password;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Phone = phone;
        this.AccessMode = accessMode;
    }

    public UserEntity( String login, String password, String firstName, String lastName, String phone) {
        this.Login = login;
        this.Password = password;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Phone = phone;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "Id=" + Id +
                ", Login='" + Login + '\'' +
                ", Password='" + Password + '\'' +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Phone='" + Phone + '\'' +
                ", AccessMode='" + AccessMode + '\'' +
                '}';
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAccessMode() {
        return AccessMode;
    }

    public void setAccessMode(String accessMode) {
        AccessMode = accessMode;
    }



}
