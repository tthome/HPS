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
public final class Constants {
    
    public static final Double PI = 3.1415926535897932;
    
    public static String REGEX = "[\\x00-\\x20]*[+-]?(((((\\p{Digit}+)((\\.)|(\\,))?((\\p{Digit}+)?)([eE][+-]?(\\p{Digit}+))?)|"
            + "(((\\.)|(\\,))((\\p{Digit}+))([eE][+-]?(\\p{Digit}+))?)|"
            + "(((0[xX](\\p{XDigit}+)((\\.)|(\\,))?)|"
            + "(0[xX](\\p{XDigit}+)?((\\.)|(\\,))(\\p{XDigit}+)))[pP][+-]?(\\p{Digit}+)))[fFdD]?))[\\x00-\\x20]*";
//"[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?";
    
    private Constants(){}
}
