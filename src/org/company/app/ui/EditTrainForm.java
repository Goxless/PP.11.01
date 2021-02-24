package org.company.app.ui;

import org.company.app.Application;
import org.company.app.data.entity.TrainEntity;
import org.company.app.data.manager.TrainEntityManager;
import org.company.app.util.BaseSubForm;

import javax.swing.*;
import java.text.SimpleDateFormat;

public class EditTrainForm extends BaseSubForm<TrainForm> {
    private JPanel mainPanel;
    private JTextField idField;
    private JTextField trnNumberField;
    private JTextField trnNameField;
    private JTextField trnTypeField;
    private JTextField trnDestField;
    private JButton cancelButton;
    private JButton saveButton;
    private JTextField carCountField;
    private JTextField scheduleIdField;

    private final TrainEntityManager trainEntityManager = new TrainEntityManager(Application.getInstance().getDatabase());
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private TrainEntity trainEntity;
    private TrainForm trainForm;
    private int rowIndex;

    public EditTrainForm(TrainEntity trainEntity, int rowIndex,TrainForm trainForm)
    {
        super(trainForm);
        this.trainEntity = trainEntity;
        this.rowIndex = rowIndex;
        this.trainForm = trainForm;
        setContentPane(mainPanel);

        initFields();
        initButtons();

        setVisible(true);
    }


    private void initFields()
    {
        idField.setEditable(false);

        idField.setText(String.valueOf(trainEntity.getTrainID()));

        trnNumberField.setText(trainEntity.getTrainNumber());
        trnNameField.setText(trainEntity.getTrainName());
        trnTypeField.setText(trainEntity.getTrainType());
        trnDestField.setText(trainEntity.getTrainDest());
        carCountField.setText(Integer.toString((int)trainEntity.getCarCount()));
        scheduleIdField.setText(Integer.toString(trainEntity.getSchedule_ride_id()));
    }

    private void initButtons()
    {
        cancelButton.addActionListener(e -> closeSubForm());

        saveButton.addActionListener(e -> {

            try {

                trainEntity.setTrainNumber(trnNumberField.getText());
                trainEntity.setTrainName(trnNameField.getText());
                trainEntity.setTrainType(trnTypeField.getText());
                trainEntity.setTrainDest(trnDestField.getText());
                trainEntity.setCarCount(Integer.parseInt(carCountField.getText()));
                trainEntity.setSchedule_ride_id(Integer.parseInt(scheduleIdField.getText()));


                trainEntityManager.update(trainEntity);
                trainForm.getModel().getValues().set(rowIndex, trainEntity);
                mainForm.getModel().fireTableDataChanged();
                closeSubForm();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
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
