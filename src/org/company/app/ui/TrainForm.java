package org.company.app.ui;

import org.company.app.Application;
import org.company.app.data.entity.TrainEntity;
import org.company.app.data.manager.CarEntityManger;
import org.company.app.data.manager.TrainEntityManager;
import org.company.app.util.BaseSubForm;
import org.company.app.util.CustomTableModel;
import org.company.app.util.DialogUtil;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class TrainForm extends BaseSubForm<ScheduleForm> {

    private JPanel mainPanel;
    private JTable table;
    private JButton enterAsAdminButton;
    private JButton Back;
    private JButton createTrainButton;
    private JButton routesButton;
    private ScheduleForm mainForm;
    private CustomTableModel<TrainEntity> model;

    private final TrainEntityManager trainEntityManager = new TrainEntityManager(Application.getInstance().getDatabase());
    private final CarEntityManger carEntityManger = new CarEntityManger(Application.getInstance().getDatabase());
    //private final ScheduleEntityManager trainEntityManager = new ScheduleEntityManager(Application.getInstance().getDatabase());

    public TrainForm(ScheduleForm scheduleForm) {
        super(scheduleForm);
        this.mainForm = scheduleForm;
        setContentPane(mainPanel);
        setVisible(true);

        initTable();
        initButtons();

        //pack();
    }

    private void initTable()
    {
        table.getTableHeader().setReorderingAllowed(false);

        table.setRowHeight(128);

        try {
            model = new CustomTableModel<>(
                    TrainEntity.class,
                    new String[]{
                            "TrainID", "trainNumber", "trainName", "trainType", "trainDest","carCount","Schedule_ride_id"
                    },
                    trainEntityManager.getAll()
            );

            /**TrainID        | int(11)
             trainNumber      | varchar(45)
             trainName        | varchar(45)
             trainType        | varchar(45)
             trainDest        | varchar(45)
             carCount         | int(11)
             Schedule_ride_id | int(11)
             */

            table.setModel(model);

            if(model.getValues().isEmpty()) {
                DialogUtil.showInfo(this, "В базе отсутствуют записи");
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

    private void initButtons()
    {
       /* if(Application.isAdminMode()) {
            enterAsAdminButton.setVisible(false);
            createTrainButton.setVisible(true);
        }*/

        createTrainButton.addActionListener(e -> {
             if(!Application.isAdminMode()) {
                DialogUtil.showInfo(this, "You need admin mode");

            }
             else{
                 dispose();
                 new AddTrainForm(mainForm);
             }
        });
        Back.addActionListener(e -> {
            closeSubForm();
        });

        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (Application.isAdminMode()) {
                    int row = table.getSelectedRow();
                    if (e.getKeyCode() == KeyEvent.VK_DELETE && row != -1) {
                        if (DialogUtil.showConfirm(TrainForm.this, "Are you sure you want to delete this entry?")) {
                            try {

                                trainEntityManager.delete(model.getValues().get(row));

                                int a = carEntityManger.deleteByTrainId(model.getValues().get(row).getTrainID());

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

        //FOREIGN KEY (TrainID) REFERENCES Customers (TrainID) ON DELETE CASCAD

        //ALTER TABLE Car ADD CONSTRAINT 'car_ibfk_1' FOREIGN KEY (Train_ID) REFERENCES Train(TrainID) ON DELETE CASCAD;

        //ALTER TABLE Car DROP Foreign Key Train_ID;

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (Application.isAdminMode()) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (e.getClickCount() == 2 && row != -1) {
                        new EditTrainForm(model.getValues().get(row), row, TrainForm.this);
                    }
                }
            }
        });


    }

    public void updateRowCountLabel(int newMax)
    {
      //  rowCountLabel.setText("( " + model.getValues().size() + " / " + newMax + " )");
    }
    public CustomTableModel<TrainEntity> getModel() {
        return model;
    }


    @Override
    public int getFormWidth() {
        return 640 ;
    }

    @Override
    public int getFormHeight() {
        return 480;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
