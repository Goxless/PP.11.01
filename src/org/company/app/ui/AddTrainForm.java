package org.company.app.ui;

import org.company.app.Application;
import org.company.app.data.entity.TrainEntity;
import org.company.app.data.entity.UserEntity;
import org.company.app.data.manager.TrainEntityManager;
import org.company.app.data.manager.UserEntityManager;
import org.company.app.util.BaseSubForm;
import org.company.app.util.DialogUtil;

import javax.swing.*;

public class AddTrainForm extends BaseSubForm<ScheduleForm> {
    private JPanel mainPanel;
    private JTextField trainIdField;
    private JTextField trainNumberField;
    private JTextField trainNameField;
    private JTextField trainTypeField;
    private JTextField TrainDestField;
    private JTextField carCountField;
    private JTextField scheduleRideIdField;
    private JButton backButton;
    private JButton saveButton;

    private final TrainEntityManager trainEntityManager  = new TrainEntityManager(Application.getInstance().getDatabase());
    private TrainEntity trainEntity = new TrainEntity();

    public AddTrainForm(ScheduleForm mainForm) {
        super(mainForm);
        initButtons();
        setContentPane(mainPanel);
        setVisible(true);
    }

    private void initButtons() {

        backButton.addActionListener(e -> closeSubForm());

        saveButton.addActionListener(e -> {
            try {
                trainEntity.setTrainID(Integer.parseInt(trainIdField.getText()));
                trainEntity.setTrainNumber(trainNumberField.getText());
                trainEntity.setTrainName(trainNameField.getText());
                trainEntity.setTrainType(trainTypeField.getText());
                trainEntity.setTrainDest(TrainDestField.getText());
                trainEntity.setCarCount(Integer.parseInt(carCountField.getText()));
                trainEntity.setSchedule_ride_id(Integer.parseInt(scheduleRideIdField.getText()));
                trainEntityManager.add(trainEntity);

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
