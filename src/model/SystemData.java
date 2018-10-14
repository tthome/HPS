/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author etb-t
 */
public class SystemData {
    
//    Pump
    private double q0 = -1; //идеальная подача q0
    private double pn = -1;     //номинальное давление pn
    private double eta0 = -1;     //объемный КПД eta0
    
//    Striker
    private double m = -1; // m
    private double x1 = -1; // x1
    private double Sa = -1;
    private double Sb = -1;
    private double Pbg0 = -1;
    private double xbg0 = -1;
    private double Vi = -1;
    private double Xmax = -1; // xmax
    
//    Accumulator
    private double Vn = -1;
    private double gamma = -1;
    private double p0 = -1;
    private double V0 = -1;
    private double Pmin = -1; // pmin
    private double Pmax = -1; // pmax
    
//    Controller
    private double p3 = -1;
    
//    System
    private double f = -1;
    private double Fmax = -1;
    private double N = -1;
    private double eta = -1;
    
    private double ro;
    
    private double l1;
    private double l2;
    private double l3;
    private double l4;
    private double l5;
    private double da;
    private double db;
    private double dc;
    private double L;
    private double D;
    private double calculatedM;
    
    public ArrayList<Boolean> userSetted = new ArrayList<>();
    public ArrayList<Boolean> calculated = new ArrayList<>();
    
    private ArrayList<Double> t0Array = new ArrayList<>();
    private ArrayList<Double> x1Array = new ArrayList<>();
    private ArrayList<Double> v2Array = new ArrayList<>();
    private ArrayList<Double> p3Array = new ArrayList<>();

    public void setT0Array(ArrayList<Double> t0Array) {
        this.t0Array = t0Array;
    }

    public void setX1Array(ArrayList<Double> x1Array) {
        this.x1Array = x1Array;
    }

    public void setV2Array(ArrayList<Double> v2Array) {
        this.v2Array = v2Array;
    }

    public void setP3Array(ArrayList<Double> p3Array) {
        this.p3Array = p3Array;
    }

    public ArrayList<Double> getT0Array() {
        return t0Array;
    }

    public ArrayList<Double> getX1Array() {
        return x1Array;
    }

    public ArrayList<Double> getV2Array() {
        return v2Array;
    }

    public ArrayList<Double> getP3Array() {
        return p3Array;
    }
    
    public SystemData() {
        for(int i = 0; i < 34; ++i) {
            userSetted.add(false);
            calculated.add(false);
        }
    }
    
    public String getIdentiferByIndex(int index) {
        switch(index) {
            case 0      : return "q0";
            case 1      : return "pn";
            case 2      : return "eta0";
            case 3      : return "vi";
            case 4      : return "xmax";
            case 5      : return "m";
            case 6      : return "x1";
            case 7      : return "sa";
            case 8      : return "sb";
            case 9      : return "vn";
            case 10     : return "gamma";
            case 11     : return "v0";
            case 12     : return "pmin";
            case 13     : return "pmax";
            case 14     : return "p3";
            case 15     : return "f";
            case 16     : return "fmax";
            case 17     : return "n";
            case 18     : return "eta";
            case 19     : return "pbg0";
            case 20     : return "xbg0";
            case 21     : return "ro";
            case 22     : return "l1";
            case 23     : return "l2";
            case 24     : return "l3";
            case 25     : return "l4";
            case 26     : return "l5";
            case 27     : return "da";
            case 28     : return "db";
            case 29     : return "dc";
            case 30     : return "l";
            case 31     : return "d";
            case 32     : return "resultm";
            case 33     : return "p0";
        }
        return null;
    }
    
    public int getIndexByIdentifer(String identifer) {
        identifer = identifer.toLowerCase();
        switch(identifer) {
            case "q0"       : return 0;
            case "pn"       : return 1;
            case "eta0"     : return 2;
            case "vi"       : return 3;
            case "xmax"     : return 4;
            case "m"        : return 5;
            case "x1"       : return 6;
            case "sa"       : return 7;
            case "sb"       : return 8;
            case "vn"       : return 9;
            case "gamma"    : return 10;
            case "v0"       : return 11;
            case "pmin"     : return 12;
            case "pmax"     : return 13;
            case "p3"       : return 14;
            case "f"        : return 15;
            case "fmax"     : return 16;
            case "n"        : return 17;
            case "eta"      : return 18;
            case "pbg0"     : return 19;
            case "xbg0"     : return 20;
            case "ro"       : return 21;
            case "l1"       : return 22;
            case "l2"       : return 23;
            case "l3"       : return 24;
            case "l4"       : return 25;
            case "l5"       : return 26;
            case "da"       : return 27;
            case "db"       : return 28;
            case "dc"       : return 29;
            case "l"        : return 30;
            case "d"        : return 31;
            case "resultm"  : return 32;
            case "p0"       : return 33;
        }
        return 0;
    }
    
    public double getValueByIndex(int index, boolean isInSI) {
        switch(index) {
            case 0      : return isInSI ? q0 * 1e-3 / 60 : q0;
            case 1      : return isInSI ? pn * 1e+6 : pn;
            case 2      : return eta0;
            case 3      : return Vi;
            case 4      : return isInSI ? Xmax * 1e-3 : Xmax;
            case 5      : return m;
            case 6      : return isInSI ? x1 * 1e-3 : x1;
            case 7      : return isInSI ? Sa * 1e-4 : Sa;
            case 8      : return isInSI ? Sb * 1e-4 : Sb;
            case 9      : return isInSI ? Vn * 1e-6 : Vn;
            case 10     : return gamma;
            case 11     : return isInSI ? V0 * 1e+6 : V0;
            case 12     : return isInSI ? Pmin * 1e+6 : Pmin;
            case 13     : return isInSI ? Pmax * 1e+6 : Pmax;
            case 14     : return isInSI ? p3 * 1e+6 : p3;
            case 15     : return f;
            case 16     : return isInSI ? Fmax * 1e+3 : Fmax;
            case 17     : return N;
            case 18     : return eta;
            case 19     : return isInSI ? Pbg0 * 1e+6 : Pbg0;
            case 20     : return xbg0;
            case 21     : return ro;
            case 22     : return isInSI ? l1 * 1e-3 : l1;
            case 23     : return isInSI ? l2 * 1e-3 : l2;
            case 24     : return isInSI ? l3 * 1e-3 : l3;
            case 25     : return isInSI ? l4 * 1e-3 : l4;
            case 26     : return isInSI ? l5 * 1e-3 : l5;
            case 27     : return isInSI ? da * 1e-3 : da;
            case 28     : return isInSI ? db * 1e-3 : db;
            case 29     : return isInSI ? dc * 1e-3 : dc;
            case 30     : return isInSI ? L * 1e-3 : L;
            case 31     : return isInSI ? D * 1e-3 : D;
            case 32     : return calculatedM;
            case 33     : return isInSI ? p0 * 1e+6 : p0;
        }
        return 0;
    }
	
    public void setValueByIndex(int index, double value, boolean isInSI) {
        switch(index) {
            case 0      : q0 = isInSI ? value / 1e-3 * 60 : value; break;
            case 1      : pn = isInSI ? value / 1e+6 : value; break;
            case 2      : eta0 = value; break;
            case 3      : Vi = value; break;
            case 4      : Xmax = isInSI ? value / 1e-3 : value; break;
            case 5      : m = value; break;
            case 6      : x1 = isInSI ? value / 1e-3 : value; break;
            case 7      : Sa = isInSI ? value / 1e-4 : value; break;
            case 8      : Sb = isInSI ? value / 1e-4 : value; break;
            case 9      : Vn = isInSI ? value / 1e-6 : value; break;
            case 10     : gamma = value; break;
            case 11     : V0 = isInSI ? value / 1e-6 : value; break;
            case 12     : Pmin = isInSI ? value / 1e+6 : value; break;
            case 13     : Pmax = isInSI ? value / 1e+6 : value; break;
            case 14     : p3 = isInSI ? value / 1e+6 : value; break;
            case 15     : f = value; break;
            case 16     : Fmax = isInSI ? value / 1e+3 : value; break;
            case 17     : N = value; break;
            case 18     : eta = value; break;
            case 19     : Pbg0 = isInSI ? value / 1e+6 : value; break;
            case 20     : xbg0 = value; break;
            case 21     : ro = value; break;
            case 22     : l1 = isInSI ? value / 1e-3 : value; break;
            case 23     : l2 = isInSI ? value / 1e-3 : value; break;
            case 24     : l3 = isInSI ? value / 1e-3 : value; break;
            case 25     : l4 = isInSI ? value / 1e-3 : value; break;
            case 26     : l5 = isInSI ? value / 1e-3 : value; break;
            case 27     : da = isInSI ? value / 1e-3 : value; break;
            case 28     : db = isInSI ? value / 1e-3 : value; break;
            case 29     : dc = isInSI ? value / 1e-3 : value; break;
            case 30     : L = isInSI ? value / 1e-3 : value; break;
            case 31     : D = isInSI ? value / 1e-3 : value; break;
            case 32     : calculatedM = value; break;
            case 33     : p0 = isInSI ? value / 1e+6 : value; break;
        }
    }
    
    public double getValueByIdentifer(String identifer, boolean isInSI) {
        identifer = identifer.toLowerCase();
        switch(identifer) {
            case "q0"       : return isInSI ? q0 * 1e-3 / 60: q0;
            case "pn"       : return isInSI ? pn * 1e+6 : pn;
            case "eta0"     : return eta0;
            case "vi"       : return Vi;
            case "xmax"     : return isInSI ? Xmax * 1e-3 : Xmax;
            case "m"        : return m;
            case "x1"       : return isInSI ? x1 * 1e-3 : x1;
            case "sa"       : return isInSI ? Sa * 1e-4 : Sa;
            case "sb"       : return isInSI ? Sb * 1e-4 : Sb;
            case "vn"       : return isInSI ? Vn * 1e-6 : Vn;
            case "gamma"    : return gamma;
            case "v0"       : return isInSI ? V0 * 1e-6 : V0;
            case "pmin"     : return isInSI ? Pmin * 1e+6 : Pmin;
            case "pmax"     : return isInSI ? Pmax * 1e+6 : Pmax;
            case "p3"       : return isInSI ? p3 * 1e+6 : p3;
            case "f"        : return f;
            case "fmax"     : return isInSI ? Fmax * 1e+3 : Fmax;
            case "n"        : return N;
            case "eta"      : return eta;
            case "pbg0"     : return isInSI ? Pbg0 * 1e+6 : Pbg0;
            case "xbg0"     : return xbg0;
            case "ro"       : return ro;
            case "l1"       : return isInSI ? l1 * 1e-3 : l1;
            case "l2"       : return isInSI ? l2 * 1e-3 : l2;
            case "l3"       : return isInSI ? l3 * 1e-3 : l3;
            case "l4"       : return isInSI ? l4 * 1e-3 : l4;
            case "l5"       : return isInSI ? l5 * 1e-3 : l5;
            case "da"       : return isInSI ? da * 1e-3 : da;
            case "db"       : return isInSI ? db * 1e-3 : db;
            case "dc"       : return isInSI ? dc * 1e-3 : dc;
            case "l"        : return isInSI ? L * 1e-3 : L;
            case "d"        : return isInSI ? D * 1e-3 : D;
            case "resultm"  : return calculatedM;
            case "p0"       : return isInSI ? p0 * 1e+6 : p0;
        }
        return 0;
    }
    
    public void setValueByIdentifer(String identifer, double value, boolean isInSI) {
        identifer = identifer.toLowerCase();
        switch(identifer) {
            case "q0"       : q0 = isInSI ? value / 1e-3 * 60 : value; break;
            case "pn"       : pn = isInSI ? value / 1e+6 : value; break;
            case "eta0"     : eta0 = value; break;
            case "vi"       : Vi = value; break;
            case "xmax"     : Xmax = isInSI ? value / 1e-3 : value; break;
            case "m"        : m = value; break;
            case "x1"       : x1 = isInSI ? value / 1e-3 : value; break;
            case "sa"       : Sa = isInSI ? value / 1e-4 : value; break;
            case "sb"       : Sb = isInSI ? value / 1e-4 : value; break;
            case "vn"       : Vn = isInSI ? value / 1e-6 : value; break;
            case "gamma"    : gamma = value; break;
            case "v0"       : V0 = isInSI ? value / 1e-6 : value; break;
            case "pmin"     : Pmin = isInSI ? value / 1e+6 : value; break;
            case "pmax"     : Pmax = isInSI ? value / 1e+6 : value; break;
            case "p3"       : p3 = isInSI ? value / 1e+6 : value; break;
            case "f"       : f = value; break;
            case "fmax"     : Fmax = isInSI ? value / 1e+3 : value; break;
            case "n"        : N = value; break;
            case "eta"      : eta = value; break;
            case "pbg0"     : Pbg0 = isInSI ? value / 1e+6 : value; break;
            case "xbg0"     : xbg0 = isInSI ? value / 1e-3 : value; break;
            case "ro"       : ro = value; break;
            case "l1"       : l1 = isInSI ? value / 1e-3 : value; break;
            case "l2"       : l2 = isInSI ? value / 1e-3 : value; break;
            case "l3"       : l3 = isInSI ? value / 1e-3 : value; break;
            case "l4"       : l4 = isInSI ? value / 1e-3 : value; break;
            case "l5"       : l5 = isInSI ? value / 1e-3 : value; break;
            case "da"       : da = isInSI ? value / 1e-3 : value; break;
            case "db"       : db = isInSI ? value / 1e-3 : value; break;
            case "dc"       : dc = isInSI ? value / 1e-3 : value; break;
            case "l"        : L = isInSI ? value / 1e-3 : value; break;
            case "d"        : D = isInSI ? value / 1e-3 : value; break;
            case "resultm"  : calculatedM = value; break;
            case "p0"       : p0 = isInSI ? value / 1e+6 : value; break;
        }
    }
    
    public void setValueCalculated(int index) {
        calculated.set(index, true);
    }
    
    public void setValueCalculated(String identifer) {
        calculated.set(getIndexByIdentifer(identifer), true);
    }
    
    public boolean isValueSetted(int index) {
        return !(getValueByIndex(index, false) < 0);
    }
    
    public boolean isValueSetted(String identifer) {
        return !(getValueByIdentifer(identifer, false) < 0);
    }
    
    public boolean isValueSettedByUser(int index) {
        return userSetted.get(index);
    }
  
    public boolean isValueSettedByUser(String identifer) {
        return userSetted.get(getIndexByIdentifer(identifer));
    }
    
    public boolean isValueCalculated(int index) {
        return calculated.get(index);
    }
  
    public boolean isValueCalculated(String identifer) {
        return calculated.get(getIndexByIdentifer(identifer));
    }
    
    public void clearCalculated() {
        for(int i = 0; i < 34; ++i) {
            calculated.set(i, false);
        }
    }
}
