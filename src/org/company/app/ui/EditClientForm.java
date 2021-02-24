package org.company.app.ui;

import org.company.app.Application;
import org.company.app.data.entity.ClientEntity;
import org.company.app.data.manager.ClientEntityManager;
import org.company.app.util.BaseSubForm;

import javax.swing.*;
import java.text.SimpleDateFormat;

public class EditClientForm extends BaseSubForm<ClientTableForm>
{
    private final ClientEntityManager clientEntityManager = new ClientEntityManager(Application.getInstance().getDatabase());
    private final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    private ClientEntity clientEntity;
    private int rowIndex;

    private JPanel mainPanel;
    private JTextField idField;
    private JTextField firstnameField;
    private JTextField surnameField;
    private JTextField patronymicField;
    private JTextField birthdayField;
    private JTextField regDateField;
    private JTextField emailField;
    private JTextField phoneField;
    private JComboBox genderBox;
    private JTextField imgPathField;
    private JButton cancelButton;
    private JButton saveButton;

    public EditClientForm(ClientTableForm mainForm, ClientEntity clientEntity, int rowIndex)
    {
        super(mainForm);
        this.clientEntity = clientEntity;
        this.rowIndex = rowIndex;

        setContentPane(mainPanel);

        initFields();
        initButtons();

        setVisible(true);
    }

    private void initFields()
    {
        idField.setEditable(false);
        idField.setText(String.valueOf(clientEntity.getId()));
        firstnameField.setText(clientEntity.getFirstname());
        surnameField.setText(clientEntity.getLastname());
        patronymicField.setText(clientEntity.getPatronymic());
        birthdayField.setText(format.format(clientEntity.getBirthday()));
        regDateField.setText(format.format(clientEntity.getRegDate()));
        emailField.setText(clientEntity.getEmail());
        phoneField.setText(clientEntity.getPhone());
        imgPathField.setText(clientEntity.getPhotoPath());

        genderBox.addItem("Мужской");
        genderBox.addItem("Женский");
        if(clientEntity.getGenderCode() == 'м') {
            genderBox.setSelectedIndex(0);
        } else if(clientEntity.getGenderCode() == 'ж') {
            genderBox.setSelectedIndex(1);
        }
    }

    private void initButtons()
    {
        cancelButton.addActionListener(e -> closeSubForm());

        saveButton.addActionListener(e -> {
            //тут нужно делать проверки на корректность полей
            try {
                clientEntity.setFirstname(firstnameField.getText());
                clientEntity.setLastname(surnameField.getText());
                clientEntity.setPatronymic(patronymicField.getText());
                clientEntity.setBirthday(format.parse(birthdayField.getText()));
                clientEntity.setRegDate(format.parse(regDateField.getText()));
                clientEntity.setEmail(emailField.getText());
                clientEntity.setPhone(phoneField.getText());
                clientEntity.setGenderCode(((String) genderBox.getSelectedItem()).toLowerCase().charAt(0));
                clientEntity.setPhotoPath(imgPathField.getText());

                clientEntityManager.update(clientEntity);
                mainForm.getModel().getValues().set(rowIndex, clientEntity);
                mainForm.getModel().fireTableDataChanged();
                closeSubForm();

            } catch (Exception ex) {
                ex.printStackTrace();
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
