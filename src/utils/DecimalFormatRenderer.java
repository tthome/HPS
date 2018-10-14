/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Component;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Rasputina_T
 */
public class DecimalFormatRenderer extends DefaultTableCellRenderer {
    
    private static final DecimalFormat FORMATTER = new DecimalFormat("#.#");
    
    public DecimalFormatRenderer() {
        super();
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.ENGLISH);
        otherSymbols.setDecimalSeparator('.');
        FORMATTER.setDecimalFormatSymbols(otherSymbols);
        FORMATTER.setMinimumFractionDigits(1);
        setHorizontalAlignment(RIGHT);
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                          boolean isSelected, boolean hasFocus, int row, int column) {
        
        if(value instanceof Double) {
            value = FORMATTER.format((Number)value);
        }
        
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
