package org.company.app;

import org.company.app.data.entity.ScheduleEntity;
import org.company.app.data.entity.TicketEntity;
import org.company.app.data.entity.TrainEntity;
import org.company.app.data.entity.UserEntity;
import org.company.app.data.manager.TicketEntityManager;
import org.company.app.data.manager.UserEntityManager;
import org.company.app.ui.AddScheduleForm;
import org.company.app.ui.ScheduleForm;
import org.company.app.ui.TrainForm;
import org.company.app.util.*;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;

public class Application
{
    private static Application instance;
    // adr port dbName user pass
    private final MysqlDatabase database = new MysqlDatabase("127.0.0.1",3306,"mydb","root","1234");
    //private final MysqlDatabase database = new MysqlDatabase("nleontnr.beget.tech", "nleontnr_docker", "nleontnr_docker", "8udwX&9bdw");
    private static final String ADMIN_PASS = "0000";
    private static boolean adminMode = false;
    private static boolean userMode = false;

    private static UserEntity user;

    public Application()
    {

        /*Class personClass = BaseManager.class;

//Get the methods
        Method[] methods = getDeclaredMethods();

//Loop through the methods and print out their names
        for (Method method : methods)
        {
            System.out.println(method.getName()+"()");
        }*/

        instance = this;

        initDatabase();
        //checkAdminMode();
        //initUi();
        new ScheduleForm();

        //new ClientTableForm();

    }

    private void initDatabase()
    {
        try {
            Connection c = database.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            DialogUtil.showError("Соедиение с базой установить не удалось");
            System.exit(228);
        }
    }

    private void initUi()
    {
        BaseForm.setBaseApplicationTitle("Медицинский центр трубочист " + (adminMode ? "(режим администратора)" : "(режим пользователя)"));

        //не используйте тут формат .ico - он не работает с ним
        BaseForm.setBaseApplicationIcon(Toolkit.getDefaultToolkit().getImage(Application.class.getClassLoader().getResource("service_logo.png")));

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
        2 способа
        - выделять все элементы в десигнере
        - и тот что ниже
         */
        FontUtil.changeAllFonts(new FontUIResource("Comic Sans MS", Font.TRUETYPE_FONT, 12));
    }

    public static void checkAdminMode()
    {
        adminMode = ADMIN_PASS.equals(JOptionPane.showInputDialog(
                null,
                "Enter admin password",
                "Admin Mode",
                JOptionPane.QUESTION_MESSAGE
        ));
    }

    public static UserEntity getUser() {
        return user;
    }

    public static void setUser(UserEntity user) {
        Application.user = user;
    }

    public MysqlDatabase getDatabase() {
        return database;
    }

    public static void main(String[] args)
    {
        new Application();
    }

    public static Application getInstance() {
        return instance;
    }

    public static boolean isAdminMode() {
        return adminMode;
    }

    public static void setAdminMode(Boolean mode) {
        adminMode = mode;
    }

    public static void setUserMode(Boolean mode) {userMode = mode;}

    public static boolean isUserMode(){return userMode;}

}
