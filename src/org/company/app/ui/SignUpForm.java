package org.company.app.ui;

import org.company.app.Application;
import org.company.app.data.entity.UserEntity;
import org.company.app.data.manager.UserEntityManager;
import org.company.app.util.BaseForm;
import org.company.app.util.BaseSubForm;
import org.company.app.util.DialogUtil;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

public class SignUpForm extends BaseSubForm<ScheduleForm> {
    private JPanel mainPanel;
    private JTextField LoginField;
    private JTextField PasswordField;
    private JTextField FirstNameField;
    private JTextField LastNameField;
    private JTextField PhoneField;
    private JButton cancelButton;
    private JButton saveButton;
    private JPasswordField passwordField;

    private final UserEntityManager userEntityManager  = new UserEntityManager(Application.getInstance().getDatabase());

    private UserEntity userEntity = new UserEntity();

    public SignUpForm(ScheduleForm scheduleForm){
        super(scheduleForm);
        setContentPane(mainPanel);
        setVisible(true);
        initButtons();
    }


    private void initButtons(){

        cancelButton.addActionListener(e -> closeSubForm());

        saveButton.addActionListener(e -> {
            try {
                userEntity.setLogin(LoginField.getText());
                userEntity.setPassword(String.valueOf(passwordField.getPassword()));
                userEntity.setFirstName(FirstNameField.getText());
                userEntity.setLastName(LastNameField.getText());
                if(!(PhoneField.getText().matches("^\\+?\\d*$") && (PhoneField.getText().length() == 12 || PhoneField.getText().length() == 11))){
                    DialogUtil.showInfo(this, "wrong telephone number ");
                    return;
                }
                userEntity.setPhone(PhoneField.getText());

                userEntityManager.add(userEntity);
                closeSubForm();

            } catch (Exception ex) {
                DialogUtil.showInfo(this, "Something went wrong");
                ex.printStackTrace();
            }

            /*Application.checkAdminMode();
            if(Application.isAdminMode()){
                DialogUtil.showInfo(this, "You have received administrator rights");
                SignInButton.setVisible(false);
                addScheduleButton.setVisible(true);
                this.updateTableBehavior(table);
            }*/

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
