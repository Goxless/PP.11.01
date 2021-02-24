package org.company.app.ui;

import org.company.app.Application;
import org.company.app.data.entity.TicketEntity;
import org.company.app.data.manager.TicketEntityManager;
import org.company.app.util.BaseSubForm;
import org.company.app.util.CustomTableModel;
import org.company.app.util.DialogUtil;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

public class ProfileForm extends BaseSubForm<ScheduleForm> {
    private JPanel mainPanel;
    private JButton ProfileButton;
    private JButton BackButton;
    private JScrollPane ticketsTable;
    private JTable table;
    private JButton logOutButton;
    private JLabel userNameField;

    private CustomTableModel<TicketEntity> model;

    private TicketEntityManager ticketEntityManager = new TicketEntityManager(Application.getInstance().getDatabase());

    public ProfileForm(ScheduleForm mainForm) {
        super(mainForm);
        setContentPane(mainPanel);
        setVisible(true);

        initTable();
        initButtons();
        initField();
        //pack();

    }

    private void initField(){
        userNameField.setText(Application.getUser().getLogin());

    }
    private void initButtons()
    {

        BackButton.addActionListener(e -> {
            closeSubForm();
        });

        logOutButton.addActionListener(e->{
            Application.setUserMode(false);
            Application.setAdminMode(false);
            mainForm.addScheduleButton.setVisible(false);
            closeSubForm();
        });

        table.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                    int row = table.getSelectedRow();
                    if (e.getKeyCode() == KeyEvent.VK_DELETE && row != -1) {
                        if (DialogUtil.showConfirm(ProfileForm.this, "Are you sure you want to cancel your ticket?")) {
                            try {
                                ticketEntityManager.delete(model.getValues().get(row));
                                model.getValues().remove(row);
                                model.fireTableDataChanged();

                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                }
            }
        });


    }

    private void initTable() {
        table.getTableHeader().setReorderingAllowed(false);

        table.setRowHeight(128);

        try {
            model = new CustomTableModel<>(
                    TicketEntity.class,
                    new String[]{
                            "Ticket Number","User_ID","TicketDest", "departureTime", "arrivalTime", "ticketPrice", "Status"
                    },
                    ticketEntityManager.getAllById(Application.getUser().getId()));

            /**
         Ticket_Number
         *  userID
         *  TicketDest
         *  departureTime
         *  arrivalTime
         *  ticketPrice
         *  CarID
         *  Status
             */

            table.setModel(model);

            if (model.getValues().isEmpty()) {
              //  DialogUtil.showInfo(this, "В базе отсутствуют записи");
            }



            //updateRowCountLabel(model.getValues().size());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public int getFormWidth() {return 640 ;}

    @Override
    public int getFormHeight() {return 480;}
}
