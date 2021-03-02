package com.github.tobi812.sprykerplugin.ui

import com.github.tobi812.sprykerplugin.ui.ClassSelectionItem.isSelected
import com.github.tobi812.sprykerplugin.ui.ClassSelectionItem.classTypeLabel
import com.github.tobi812.sprykerplugin.ui.ClassSelectionItem.className
import com.github.tobi812.sprykerplugin.ui.ClassSelectionItem.isClassNameEditable
import javax.swing.table.AbstractTableModel

class ClassSelectionModel(private val items: List<ClassSelectionItem>) : AbstractTableModel() {
    override fun getRowCount(): Int {
        return items.size
    }

    override fun getColumnCount(): Int {
        return 3
    }

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        val item = items[rowIndex]
        return when (columnIndex) {
            0 -> item.isSelected!!
            1 -> item.classTypeLabel!!
            2 -> item.className
            else -> ""
        }
    }

    override fun isCellEditable(rowIndex: Int, columnIndex: Int): Boolean {
        return when (columnIndex) {
            0 -> true
            1 -> false
            2 -> items[rowIndex].isClassNameEditable!!
            else -> false
        }
    }

    override fun getColumnClass(column: Int): Class<*> {
        if (column == 0) {
            return Boolean::class.java
        }
        return if (column == 2) {
            String::class.java
        } else super.getColumnClass(column)
    }

    override fun setValueAt(aValue: Any, rowIndex: Int, columnIndex: Int) {
        val item = items[rowIndex]
        if (columnIndex == 0 && aValue is Boolean) {
            item.isSelected = aValue
        }
        if (columnIndex == 2 && aValue is String) {
            item.className = aValue
        }
        fireTableCellUpdated(rowIndex, columnIndex)
    }
}