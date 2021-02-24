package org.company.app.util;

import javax.swing.table.AbstractTableModel;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CustomTableModel<T> extends AbstractTableModel
{
    /*
    нельзя путать class и Class
    class - тип данных впринципе системное слово
    Class - тип данных который описывает class
     */
    private final Class<T> cls;

    private final String[] columnNames;

    private List<T> values;

    public CustomTableModel(Class<T> cls, String[] columnNames, List<T> values)
    {
        this.values = values;
        this.cls = cls;
        this.columnNames = columnNames;
    }

    @Override
    public int getRowCount() {
        return values.size();
    }

    @Override
    public int getColumnCount() {
        //получаем массив всех зарегистрированных полей UserEntity и получаем его длину
        return cls.getDeclaredFields().length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        //мы соотносим номер колонки в таблице с номером поля в классе
        //получаем также массив всех зарегистрированных полей
        //выбираем поле по номеру колонки и получаем Class этого поля
        return cls.getDeclaredFields()[columnIndex].getType();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnIndex >= columnNames.length ? cls.getDeclaredFields()[columnIndex].getName() : columnNames[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        try {
            //берем объетк который опысывает поле конкретной колонки
            Field field = cls.getDeclaredFields()[columnIndex];

            //делаем его достуным для чтения
            //если поле private, то метод field.get(...) выбросит IllegalAccessException
            field.setAccessible(true);

            //получаем значения поля из объекта
            return field.get(values.get(rowIndex));

        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    public void sort(Comparator<T> comparator) {
        Collections.sort(this.values, comparator);
        fireTableDataChanged();
    }

    public List<T> getValues() {
        return values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }
}
