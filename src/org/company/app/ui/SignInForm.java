package org.company.app.ui;

import org.company.app.Application;
import org.company.app.data.entity.UserEntity;
import org.company.app.data.manager.UserEntityManager;
import org.company.app.util.BaseForm;
import org.company.app.util.BaseSubForm;
import org.company.app.util.DialogUtil;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class SignInForm extends BaseSubForm<ScheduleForm> {
    private JPanel mainPanel;
    private JTextField LoginField;
    private JButton cancelButton;
    private JButton SignInButton;
    private JPasswordField PasswordField;
    private JButton SignUpButton;

    private final UserEntityManager userEntityManager  = new UserEntityManager(Application.getInstance().getDatabase());
    private ScheduleForm mainForm;
    public SignInForm(ScheduleForm mainForm) {
        super(mainForm);
        this.mainForm = mainForm;
        setContentPane(mainPanel);
        setVisible(true);
        initButtons();
        //pack();
    }


    private void initButtons(){

        cancelButton.addActionListener(e -> closeSubForm());

        SignInButton.addActionListener(e -> {
            /*Application.checkAdminMode();
            if(Application.isAdminMode()){
                DialogUtil.showInfo(this, "You have received administrator rights");
                SignInButton.setVisible(false);
                addScheduleButton.setVisible(true);
                this.updateTableBehavior(table);
            }*/

                try {

                    UserEntity userEntity = userEntityManager.getByLogin(LoginField.getText());

                    if(userEntity != null && userEntity.getPassword().equals(String.valueOf(PasswordField.getPassword()))){

                        try{
                            if(userEntity.getAccessMode().equals("admin")){
                                Application.setAdminMode(true);
                                mainForm.addScheduleButton.setVisible(true);
                                DialogUtil.showInfo(this, "Admin mode activated");
                            }
                        }catch (NullPointerException npe){
                            System.out.println(npe.getMessage());
                        }

                        Application.setUserMode(true);
                        Application.setUser(userEntity);
                        closeSubForm();
                    }
                    else{
                        if(userEntity==null){
                            DialogUtil.showInfo(this, "There is no user with such name");
                        }
                        if(!userEntity.getPassword().equals(String.valueOf(PasswordField.getPassword()))){
                            DialogUtil.showInfo(this, "wrong password");
                        }

                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
        });

        SignUpButton.addActionListener(e -> {
            dispose();
            new SignUpForm(mainForm);
            //

        });

       /* addScheduleButton.addActionListener(e -> {
            new AddScheduleForm(ScheduleForm.this);
        });

        trainsListButton.addActionListener(e -> {
            new TrainForm(ScheduleForm.this);
        });*/

    }

    @Override
    public int getFormWidth() {
        return 640;
    }

    @Override
    public int getFormHeight() {
        return 480;
    }
}
