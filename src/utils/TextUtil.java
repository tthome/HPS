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
public final class TextUtil {
    
    private TextUtil() {
    }
    
    public static boolean isStringDoubleValue(String text) {
        return text != null && !text.isEmpty() && text.matches(Constants.REGEX);
    }
    
    public static String prepareStringToDouble(String text) {
        return text.replace(",", ".");
    }
}
