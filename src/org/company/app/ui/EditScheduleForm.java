package org.company.app.ui;

import org.company.app.Application;
import org.company.app.data.entity.ScheduleEntity;
import org.company.app.data.manager.ScheduleEntityManager;
import org.company.app.util.BaseSubForm;

import javax.swing.*;
import javax.swing.text.TabableView;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class EditScheduleForm extends BaseSubForm<ScheduleForm> {
    private JPanel mainPanel;
    private JTextField idField;
    private JTextField StartField;
    private JTextField EndField;
    private JTextField DepField;
    private JTextField ArrField;
    private JTextField regDateField;
    private JTextField emailField;
    private JTextField phoneField;
    private JComboBox genderBox;
    private JTextField imgPathField;
    private JButton cancelButton;
    private JButton saveButton;
    private JTextField priceField;

    private final ScheduleEntityManager scheduleEntityManager = new ScheduleEntityManager(Application.getInstance().getDatabase());
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private ScheduleEntity scheduleEntity;
    private int rowIndex;

    public EditScheduleForm(ScheduleForm mainForm, ScheduleEntity scheduleEntity, int rowIndex)
    {
        super(mainForm);
        this.scheduleEntity = scheduleEntity;
        this.rowIndex = rowIndex;

        setContentPane(mainPanel);

        initFields();
        initButtons();

        setVisible(true);
    }

    private void initFields()
    {
        idField.setEditable(false);

        idField.setText(String.valueOf(scheduleEntity.getRide_id()));

        StartField.setText(scheduleEntity.getStartPoint());
        EndField.setText(scheduleEntity.getEndPoint());
        DepField.setText(format.format(scheduleEntity.getDepartureTime()));
        ArrField.setText(format.format(scheduleEntity.getArrivalTime()));
        priceField.setText(Integer.toString((int)scheduleEntity.getPrice()));

    }

    private void initButtons()
    {
        cancelButton.addActionListener(e -> closeSubForm());

        saveButton.addActionListener(e -> {

            try {
                scheduleEntity.setStartPoint(StartField.getText());
                scheduleEntity.setEndPoint(EndField.getText());
                scheduleEntity.setArrivalTime(format.parse(ArrField.getText()));
                scheduleEntity.setDepartureTime(format.parse(DepField.getText()));
                scheduleEntity.setPrice((Double.parseDouble(priceField.getText())));


                scheduleEntityManager.update(scheduleEntity);
                mainForm.getModel().getValues().set(rowIndex, scheduleEntity);
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
