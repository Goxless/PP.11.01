package org.company.app.ui;

import org.company.app.Application;
import org.company.app.data.entity.ClientEntity;
import org.company.app.data.entity.ScheduleEntity;
import org.company.app.data.manager.ScheduleEntityManager;
import org.company.app.util.BaseSubForm;
import org.company.app.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddScheduleForm extends BaseSubForm<ScheduleForm>
{
    private final ScheduleEntityManager ScheduleEntityManager = new ScheduleEntityManager(Application.getInstance().getDatabase());
    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private JPanel mainPanel;
    private JTextField StartField;
    private JTextField EndField;
    private JTextField DepTimeField;
    private JTextField ArrivalTimeField;
    private JTextField emailField;
    private JTextField phoneField;
    private JComboBox genderBox;
    private JButton backButton;
    private JButton saveButton;
    private JTextField PriceField;
    private JTextField photoPathField;

    public AddScheduleForm(ScheduleForm mainForm)
    {
        super(mainForm);
        setContentPane(mainPanel);

        initButtons();

        setVisible(true);
    }


    private void initButtons()
    {
        backButton.addActionListener(e -> {
            closeSubForm();
        });

        saveButton.addActionListener(e -> {
            Date arrTime = null;
            Date depTime = null;
            try {
                arrTime = format.parse(ArrivalTimeField.getText());
                depTime = format.parse(DepTimeField.getText());
            } catch (ParseException parseException) {
                parseException.printStackTrace();
                DialogUtil.showError(this, "Неверный формат даты");
                return;
            }

            try {
                ScheduleEntity newSchedule = new ScheduleEntity(
                        new Timestamp(depTime.getTime()),
                        new Timestamp(arrTime.getTime()),
                        StartField.getText(),
                        EndField.getText(),
                        Integer.parseInt(PriceField.getText())
                );

                ScheduleEntityManager.add(newSchedule);
                mainForm.getModel().getValues().add(newSchedule);   // --!
                mainForm.getModel().fireTableDataChanged();
                //mainForm.updateRowCountLabel();

                closeSubForm();

            } catch (SQLException ex) {
                ex.printStackTrace();
                DialogUtil.showError(AddScheduleForm.this, "Не удалось добавить расписание");
            }
        });
    }

    @Override
    public int getFormWidth() {
        return 400;
    }

    @Override
    public int getFormHeight() {
        return 500;
    }
}