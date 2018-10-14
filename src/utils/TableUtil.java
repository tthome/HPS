/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Rasputina_T
 */
public final class TableUtil {
    
    private static final DecimalFormatRenderer TABLE_RENDERER = new DecimalFormatRenderer();
    
    private TableUtil() {
    }
    
    public static void setModelTable(javax.swing.JTable table) {
        for(int column = 0; column < table.getColumnModel().getColumnCount(); ++column) {
            table.getColumnModel().getColumn(column).setCellRenderer(TABLE_RENDERER);
        }
    }
}
