package sprykerFileCreator.ui;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ClassSelectionModel extends AbstractTableModel {

    private List<ClassSelectionItem> items;

    public ClassSelectionModel(List<ClassSelectionItem> items) {
        this.items = items;
    }

    @Override
    public int getRowCount() {
        return this.items.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ClassSelectionItem item = items.get(rowIndex);

        switch(columnIndex){
            case 0: return item.isSelected();
            case 1: return item.getClassTypeLabel();
            case 2: return item.getClassName();
            default: return "";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch(columnIndex){
            case 0: return true;
            case 1: return false;
            case 2: return items.get(rowIndex).isClassNameEditable();
            default: return false;
        }
    }

    @Override
    public Class<?> getColumnClass(int column) {

        if(column == 0){
            return Boolean.class;
        }

        if(column == 2){
            return String.class;
        }

        return super.getColumnClass(column);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        ClassSelectionItem item = items.get(rowIndex);

        if (columnIndex == 0 && aValue instanceof Boolean) {
            Boolean isSelected = (Boolean) aValue;
            item.setSelected(isSelected);

        }

        if (columnIndex == 2 && aValue instanceof String) {
            String className = (String) aValue;
            item.setClassName(className);
        }

        fireTableCellUpdated(rowIndex, columnIndex);
    }

}
