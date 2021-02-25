package org.company.app.ui;

import org.company.app.Application;
import org.company.app.data.entity.ScheduleEntity;
import org.company.app.data.manager.CarEntityManger;
import org.company.app.data.manager.ScheduleEntityManager;
import org.company.app.data.manager.TicketEntityManager;
import org.company.app.util.BaseForm;
import org.company.app.util.CustomTableModel;
import org.company.app.util.DialogUtil;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class ScheduleForm extends BaseForm {

    private JPanel mainPanel;
    private JTable table;
    private JButton ProfileButton;
    private JComboBox startBox;
    private JComboBox finishBox;
    private JButton trainsListButton;
    public JButton addScheduleButton;
    private JButton routesButton;
    private JScrollPane tableField;
    private JButton findRouteButton;

    private CustomTableModel<ScheduleEntity> model;

    private final ScheduleEntityManager scheduleEntityManager = new ScheduleEntityManager(Application.getInstance().getDatabase());
    private final TicketEntityManager ticketEntityManager = new TicketEntityManager(Application.getInstance().getDatabase());
    private final CarEntityManger carEntityManger = new CarEntityManger(Application.getInstance().getDatabase());



    public ScheduleForm() {
        setContentPane(mainPanel);
        setVisible(true);

        initTable();
        initButtons();
        initBoxes();
        //pack();
        routesButton.setVisible(false);
    }
    private void initBoxes(){

        try {
            startBox.setModel(new DefaultComboBoxModel<String>(scheduleEntityManager.getUniqueStartPoints().toArray(new String[0])));
            finishBox.setModel(new DefaultComboBoxModel<String>(scheduleEntityManager.getUniqueFinishPoints().toArray(new String[0])));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("!!!!!!!!!!!"+throwables.getMessage());
        }
        }
    private void initButtons(){
        addScheduleButton.setVisible(false);

        ProfileButton.addActionListener(e -> {
            /*Application.checkAdminMode();

                DialogUtil.showInfo(this, "You have received administrator rights");
            SignInButton.setVisible(false);
            addScheduleButton.setVisible(true);
            this.updateTableBehavior(table);
            }*/
            if(!Application.isUserMode())
                new SignInForm(ScheduleForm.this);

            else{
                new ProfileForm(ScheduleForm.this);
            }

        });

        addScheduleButton.addActionListener(e -> {
            new AddScheduleForm(ScheduleForm.this);
        });

        trainsListButton.addActionListener(e -> {
                new TrainForm(ScheduleForm.this);
        });

        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (Application.isAdminMode()) {
                    int row = table.getSelectedRow();
                    if (e.getKeyCode() == KeyEvent.VK_DELETE && row != -1) {
                        if (DialogUtil.showConfirm(ScheduleForm.this, "Are you sure you want to delete this entry?")) {
                            try {
                                scheduleEntityManager.delete(model.getValues().get(row));
                                model.getValues().remove(row);
                                model.fireTableDataChanged();

                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    }
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                if(!Application.isUserMode()){
                    DialogUtil.showInfo("You need to sign in first");
                    return;
                }

                int ScheduleId = (int)table.getValueAt(table.rowAtPoint(e.getPoint()),0);

                if(Application.isAdminMode()){
                    try {
                        new EditScheduleForm(ScheduleForm.this,scheduleEntityManager.getById(ScheduleId),table.rowAtPoint(e.getPoint()));
                        return;

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }

                if(DialogUtil.showConfirm(ScheduleForm.this, "Do you want to purchase this ticket with id "+ScheduleId+" ?"))
                {
                    try {
                        if(!carEntityManger.canUpdateFullness(ScheduleId)){
                            DialogUtil.showInfo("There is no empty seats,we are sorry");
                            return;
                        }

                        ticketEntityManager.add(scheduleEntityManager.getById(ScheduleId));


                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        });

        findRouteButton.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                    //ScheduleForm.this.getModel().getValues().add(newClient);
                    //ScheduleForm.this.getModel().setValues();
                    //  mainForm.getModel().fireTableDataChanged();
                    try {
                        model = new CustomTableModel<>(
                                ScheduleEntity.class,
                                new String[]{
                                        "Ride ID", "Departure Time", "Arrival time","Start Point","End Point","Price"
                                },
                                scheduleEntityManager.getFindedRoutes((String)startBox.getSelectedItem(),(String)finishBox.getSelectedItem())
                        );

                        if(model.getValues().isEmpty()) {
                            DialogUtil.showInfo(ScheduleForm.this, "No values in the database");
                            return;
                        }
                        table.setModel(model);

                        //updateRowCountLabel(model.getValues().size());

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
            }
        });
    }

    private void updateTableBehavior(JTable table){

        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int row = table.getSelectedRow();
                if (e.getKeyCode() == KeyEvent.VK_DELETE && row != -1) {
                    if (DialogUtil.showConfirm(ScheduleForm.this, "Are you sure you want to delete this ?")) {
                        try {
                            scheduleEntityManager.delete(model.getValues().get(row));
                            model.getValues().remove(row);
                            model.fireTableDataChanged();
                            //updateRowCountLabel();

                        } catch (SQLException throwables) {
                            throwables.printStackTrace();
                        }
                    }
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (e.getClickCount() == 2 && row != -1) {
                    new EditScheduleForm(ScheduleForm.this, model.getValues().get(row), row);
                }
            }
        });
    }

    private void initTable()
    {
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(128);

        try {
            model = new CustomTableModel<>(
                    ScheduleEntity.class,
                    new String[]{
                            "Ride ID", "Departure Time", "Arrival time","Start Point","End Point","Price"
                    },
                    scheduleEntityManager.getAll()
            );


            table.setModel(model);

            if(model.getValues().isEmpty()) {
                DialogUtil.showInfo(this, "No values in the database");
            }

            //updateRowCountLabel(model.getValues().size());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        /*if(Application.isAdminMode())
        {
            table.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    int row = table.getSelectedRow();
                    if(e.getKeyCode() == KeyEvent.VK_DELETE && row != -1) {
                        if(DialogUtil.showConfirm(ClientTableForm.this, "Вы точно хотите удалить данную запись?"))
                        {
                            try {
                                clientEntityManager.delete(model.getValues().get(row));
                                model.getValues().remove(row);
                                model.fireTableDataChanged();
                                updateRowCountLabel();

                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }
                    }
                }
            });

            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    int row = table.rowAtPoint(e.getPoint());
                    if(e.getClickCount() == 2 && row != -1) {
                        new EditClientForm(ClientTableForm.this, model.getValues().get(row), row);
                    }
                }
            });
        }*/
    }


    public void updateRowCountLabel(int newMax)
    {
        //  rowCountLabel.setText("( " + model.getValues().size() + " / " + newMax + " )");
    }

    public CustomTableModel<ScheduleEntity> getModel() {
        return model;
    }

    @Override
    public int getFormWidth() {return 640 ;}

    @Override
    public int getFormHeight() {return 480;}


}
