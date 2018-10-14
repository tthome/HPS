/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static model.BidirectionalSystem.Type.DIRECT_CHAMBER;
import static model.BidirectionalSystem.Type.REVERSE_CHAMBER;
import static model.HydroSystem.Type.DOUBLE;
import presenters.ModelPresenter;
import utils.Constants;

/**
 *
 * @author Rasputina_T
 */
public class BidirectionalSystem extends HydroSystem {
    
    public static enum Type {
        BOTH_CHAMBERS,
        DIRECT_CHAMBER,
        REVERSE_CHAMBER
    }
    
    public final static List<String> STRIKER_VARIANT_PARAMS = 
            Arrays.asList("q0", "pn", "eta0", "m", "x1", "Sa", "Sb", "Vn", "p3", "Vi", "Xmax", "p0", "V0", "Pmin", "Pmax", "f", "Fmax", "N", "eta");
    
    public final static List<String> STRIKER_VARIANT_PARAMS_TO_SHOW = 
            Arrays.asList("q0", "pn", "eta0", "m", "x1", "Sa", "Sb", "p3", "Vi", "Xmax", "p0", "V0", "Pmin", "Pmax", "f", "Fmax", "N", "eta");
    
    public final static List<String> STRIKER_VARIANT_PARAMS_TO_SHOW_WITH_UNITS = 
            Arrays.asList("q0 л/с", "pn МПа", "eta0", "m кг", "x1 мм", 
                    "Sa см" + (char) Integer.parseInt("00b2", 16), 
                    "Sb см" + (char) Integer.parseInt("00b2", 16), 
                    "p3 МПа", "Vi м/с", "Xmax мм", "p0 МПа", 
                    "V0 см" + (char) Integer.parseInt("00b3", 16), 
                    "Pmin МПа", "Pmax МПа", "f Гц", "Fmax Н", "N", "eta");
    
    public BidirectionalSystem() {
        type = DOUBLE;
        
        setSystemData(new SystemData());
        setSystemUpBounds(new SystemData());
        setSystemDownBounds(new SystemData());
        getSystemData().setValueByIdentifer("gamma", 1.4, false);
        getSystemData().setValueByIdentifer("ro", 7800, false);
        getSystemData().userSetted.set(10, true);
        getSystemData().userSetted.set(21, true);
        setResultParamsTitles(STRIKER_VARIANT_PARAMS_TO_SHOW);
    }
    
    @Override
    public List<String> getResultParamsTitlesToShowWithUtits(List<String> paramsTitlesToShow) {
        List<String> resultTitles = new ArrayList<>();
        for (int i = 0; i < STRIKER_VARIANT_PARAMS_TO_SHOW.size(); ++i) {
            String title = STRIKER_VARIANT_PARAMS_TO_SHOW.get(i);
            if (paramsTitlesToShow.contains(title)) {
                resultTitles.add(STRIKER_VARIANT_PARAMS_TO_SHOW_WITH_UNITS.get(i));
            }
        }
        return resultTitles;
    }

    @Override
    public boolean isSettedEnough() {
        int count = 0;
        count = getSettedBasicVariablesCount();
        return count >= 6;
    }
    
    @Override
    public boolean isComplete() {
        return getSettedBasicVariablesCount() == indexesOfValues.size();
    }

    @Override
    public boolean isValid() {
        return isBasicParamsValid();
    }
    
    @Override
    public double calcMass(double D) {
        double Sa = getSystemData().getValueByIdentifer("Sa", true);
        double Sb = getSystemData().getValueByIdentifer("Sb", true);
        double Xmax = getSystemData().getValueByIdentifer("Xmax", true);
        double x1 = getSystemData().getValueByIdentifer("x1", true);
        
        double ro = getSystemData().getValueByIdentifer("ro", true);
        
        Type biType = ModelPresenter.getInstance().getDoubleSystemType();
        if (biType.equals(DIRECT_CHAMBER)) {
            Sb = Sb + Sa;
        }
        if (biType.equals(REVERSE_CHAMBER)) {
            Sa = Sb + Sa;
        }
        
        double da = Math.sqrt(D*D-4*Sa/Constants.PI);
        double db = Math.sqrt(D*D-4*Sb/Constants.PI);
        double dc = D * 0.95;
        double k1 = getK1();
        double k2 = getK2();
        double k3 = getK3();
        double k4 = getK4();
        double l1 = k4 * db + k1 * db + Math.sqrt(2*Sb/Constants.PI) + k3 * Xmax;
        double l2 = k2*x1+x1;
        double l3 = Xmax;
        double l4 = D;
        double l5 = Math.sqrt(4*Sa/Constants.PI) + k1 * da + k4 * da + k3 * Xmax;
        double mass = l1*db*db + l2*D*D + l3*dc*dc + l4*D*D + l5*da*da;
        mass *= ro * Constants.PI / 4;
        double L = l1 + l2 + l3 + l4 + l5;
        
        getSystemData().setValueByIdentifer("l1", l1, true);
        getSystemData().setValueByIdentifer("l2", l2, true);
        getSystemData().setValueByIdentifer("l3", l3, true);
        getSystemData().setValueByIdentifer("l4", l4, true);
        getSystemData().setValueByIdentifer("l5", l5, true);
        getSystemData().setValueByIdentifer("da", da, true);
        getSystemData().setValueByIdentifer("db", db, true);
        getSystemData().setValueByIdentifer("dc", dc, true);
        getSystemData().setValueByIdentifer("L", L, true);
        getSystemData().setValueByIdentifer("D", D, true);
    
        return mass;
    }
    
    @Override
    public boolean isDValid(double D) {
        double Sa = getSystemData().getValueByIdentifer("Sa", true);
        double Sb = getSystemData().getValueByIdentifer("Sb", true);
        Type biType = ModelPresenter.getInstance().getDoubleSystemType();
        if (biType.equals(DIRECT_CHAMBER)) {
            Sb = Sb + Sa;
        }
        if (biType.equals(REVERSE_CHAMBER)) {
            Sa = Sb + Sa;
        }
        double SaCoef = 4*Sa/Constants.PI;
        double SbCoef = 4*Sb/Constants.PI;
        
        double D2 = D*D;
        return (D2-SaCoef) > 0 && (D2-SbCoef) > 0;
    }

    @Override
    public void calcAllParams(ArrayList<Double> data) {
        boolean canCalcAny = true;
        while(canCalcAny) {
            canCalcAny = false;
            for (String identifer: STRIKER_VARIANT_PARAMS) {
                if(!(getSystemData().isValueSettedByUser(identifer) || getSystemData().isValueCalculated(identifer))) {
                    if(calcParamWithData(identifer, data)) {
                        getSystemData().setValueCalculated(identifer);
                        canCalcAny = true;
                    }
                }
            }
        }
    }

    @Override
    public ArrayList<Double> getVariantWithData(ArrayList<Double> data) {
        ArrayList<Double> dataForTable = new ArrayList<>();
        for (String identifer: STRIKER_VARIANT_PARAMS_TO_SHOW) {
            dataForTable.add(getSystemData().getValueByIdentifer(identifer, false));
        }
        return dataForTable;
    }
    
    private boolean calcParamWithData(String identifer, ArrayList<Double> data) {
        switch(identifer) {
            case "q0" : {
                double sigma1 = data.get(3);
                sigma1 = Math.pow(10, sigma1);
                return calcQ0(sigma1);
            }
            case "pn" : {
                double sigma1 = data.get(3);
                sigma1 = Math.pow(10, sigma1);
                double p3table = data.get(1);
                return calcPn(p3table, sigma1);
            }
            case "eta0" : {
                double p3table = data.get(1);
                double x1table = data.get(0);
                x1table = Math.pow(10, x1table);
                //x1table *= 1000;
                double sigma1 = data.get(3);
                sigma1 = Math.pow(10, sigma1);
                return calcEta0(p3table, x1table, sigma1);
            }
            case "m" : {
                double sigma1 = data.get(3);
                sigma1 = Math.pow(10, sigma1);
                return calcM(sigma1);
            }
            case "x1" : {
                double x1table = data.get(0);
                x1table = Math.pow(10, x1table);
                //x1table *= 1000;
                return calcX1(x1table);
            }
            case "Sa" : {
                double x1table = data.get(0);
                x1table = Math.pow(10, x1table);
                //x1table *= 1000;
                double sigma0 = data.get(2);
                sigma0 = Math.pow(10, sigma0);
                double sigma1 = data.get(3);
                sigma1 = Math.pow(10, sigma1);
                return calcASurface(x1table, sigma0, sigma1);
            }
            case "Sb" : {
                double x1table = data.get(0);
                x1table = Math.pow(10, x1table);
                //x1table *= 1000;
                double sigma0 = data.get(2);
                sigma0 = Math.pow(10, sigma0);
                double sigma1 = data.get(3);
                sigma1 = Math.pow(10, sigma1);
                return calcBSurface(x1table, sigma0, sigma1);
            }
            case "Vn" : {
                double x1table = data.get(0);
                x1table = Math.pow(10, x1table);
                //x1table *= 1000;
                double sigma1 = data.get(3);
                sigma1 = Math.pow(10, sigma1);
                return calcVn(x1table, sigma1);
            }
            case "p3" : {
                double p3table = data.get(1);
                return calcP3(p3table);
            }
            case "f" : {
                double TCtable = data.get(5);
                return calcF(TCtable);
            }
            case "Xmax" : {
                double xmaxtable = data.get(7);
                //xmaxtable *= 1000;
                return calcXmax(xmaxtable);
            }
            case "Vi" : {
                double Vitable = data.get(6);
                return calcVi(Vitable);
            }
            case "N" : {
                double ntable = data.get(12);
                double sigma1 = data.get(3);
                sigma1 = Math.pow(10, sigma1);
                return calcN(ntable, sigma1);
            }
            case "Pmin" : {
                double pmintable = data.get(8);
                return calcPmin(pmintable);
            }
            case "Pmax" : {
                double pmaxtable = data.get(9);
                return calcPmax(pmaxtable);
            }
            case "V0" : {
                return calcV0();
            }
            case "p0" : {
                return calcP0();
            }
            case "Fmax" : {
                return calcFmax();
            }
            case "eta" : {
                return calcEta(data.get(13));
            }
        }
        return false;
    }
    
    private boolean calcEta(double etaTable) {
        getSystemData().setValueByIdentifer("eta", etaTable, true);
        return true;
    }
    
    private boolean calcFmax() {
        if (getSystemData().isValueSetted("pmax") && getSystemData().isValueSetted("Sb")) {
            getSystemData().setValueByIdentifer("Fmax", getSystemData().getValueByIdentifer("pmax", true) * getSystemData().getValueByIdentifer("Sb", true), true);
            return true;
        }
        return false;
    }
    
    private boolean calcP0() {
        if (getSystemData().isValueSetted("pmin")) {
            getSystemData().setValueByIdentifer("p0", getSystemData().getValueByIdentifer("pmin", true) * 0.8, true);
            return true;
        }
        return false;
    }
    
    private boolean calcV0() {
        if (getSystemData().isValueSetted("pn") &&
                getSystemData().isValueSetted("p0") && 
                getSystemData().isValueSetted("Vn")) {
            double p0 = getSystemData().getValueByIdentifer("p0", true);
            double pn = getSystemData().getValueByIdentifer("pn", true);
            double gamma = getSystemData().getValueByIdentifer("gamma", true);
            double Vn = getSystemData().getValueByIdentifer("Vn", true);
            if (Math.abs(p0) > 1e-7) {
                double V0 = pn / p0;
                V0 = Math.pow(V0, 1/gamma);
                V0 *= Vn;
                getSystemData().setValueByIdentifer("V0", V0, true);
                return true;
            }
        }
        return false;
    }
    
     private boolean calcN() {
        if (getSystemData().isValueSetted("Vi") &&
                getSystemData().isValueSetted("m") && 
                getSystemData().isValueSetted("Tc")) {
            double N = Math.pow(getSystemData().getValueByIdentifer("Vi", true), 2);
            double Tc = getSystemData().getValueByIdentifer("Tc", true);
            if (Math.abs(Tc) > 1e-7) {
                N *= getSystemData().getValueByIdentifer("m", true) / 2 / Tc;
                getSystemData().setValueByIdentifer("N", N, true);
                return true;
            }
        }
        return false;
    }
    
    private boolean calcQ0(double sigma1) {
        if(getSystemData().isValueSetted("eta0") && 
                getSystemData().isValueSetted("pn") && 
                getSystemData().isValueSetted("Vn") && 
                getSystemData().isValueSetted("m") && 
                getSystemData().isValueSetted("Sa") && 
                getSystemData().isValueSetted("Sb")) {
            double eta0 = getSystemData().getValueByIdentifer("eta0", true);
            double Vn = getSystemData().getValueByIdentifer("Vn", true);
            double pn = getSystemData().getValueByIdentifer("pn", true);
            double m = getSystemData().getValueByIdentifer("m", true);
            double Sa = getSystemData().getValueByIdentifer("Sa", true);
            double Sb = getSystemData().getValueByIdentifer("Sb", true);
            double gamma = getSystemData().getValueByIdentifer("gamma", true);
            if (Math.abs(m) > 1e-7) {
                double value = Math.sqrt(Math.pow(1-eta0, 1/gamma-1) * pn * Vn * (Sa+Sb) * (Sa+Sb) / gamma / sigma1 / m);
                getSystemData().setValueByIdentifer("q0", value, true);
                return true;
            }
        }
        return false;
    }
    
    private boolean calcPn(double p3table, double sigma1) {
        if(getSystemData().isValueSetted("eta0") && 
                getSystemData().isValueSetted("p3")) {
            double eta0 = getSystemData().getValueByIdentifer("eta0", true);
            double p3 = getSystemData().getValueByIdentifer("p3", true);
            if (Math.abs(p3table) > 1e-7) {
                double value = (1-eta0) * p3 / p3table;
                getSystemData().setValueByIdentifer("pn", value, true);
                return true;
            }
        }
        if(getSystemData().isValueSetted("eta0") && 
                getSystemData().isValueSetted("m") && 
                getSystemData().isValueSetted("q0") && 
                getSystemData().isValueSetted("Sa") && 
                getSystemData().isValueSetted("Sb") && 
                getSystemData().isValueSetted("Vn")) {
            double eta0 = getSystemData().getValueByIdentifer("eta0", true);
            double q0 = getSystemData().getValueByIdentifer("q0", true);
            double Vn = getSystemData().getValueByIdentifer("Vn", true);
            double m = getSystemData().getValueByIdentifer("m", true);
            double Sa = getSystemData().getValueByIdentifer("Sa", true);
            double Sb = getSystemData().getValueByIdentifer("Sb", true);
            double gamma = getSystemData().getValueByIdentifer("gamma", true);
            double help = Math.pow(1-eta0, 1/gamma-1);
            if (Math.abs(Sa+Sb) > 1e-7 && Math.abs(help) > 1e-7 && Math.abs(Vn) > 1e-7) {
                double value = sigma1 * gamma * m * q0 * q0 * help / (Sa+Sb) / (Sa+Sb) / Vn;
                getSystemData().setValueByIdentifer("pn", value, true);
                return true;
            }
        }
        return false;
    }
    
    private boolean calcEta0(double p3table, double x1table, double sigma1) {
        if(getSystemData().isValueSetted("pn") && 
                getSystemData().isValueSetted("q0") && 
                getSystemData().isValueSetted("m") && 
                getSystemData().isValueSetted("Sa") && 
                getSystemData().isValueSetted("Sb") && 
                getSystemData().isValueSetted("Vn")) {
            double pn = getSystemData().getValueByIdentifer("pn", true);
            double q0 = getSystemData().getValueByIdentifer("q0", true);
            double Vn = getSystemData().getValueByIdentifer("Vn", true);
            double m = getSystemData().getValueByIdentifer("m", true);
            double Sa = getSystemData().getValueByIdentifer("Sa", true);
            double Sb = getSystemData().getValueByIdentifer("Sb", true);
            double gamma = getSystemData().getValueByIdentifer("gamma", true);
            if (Math.abs(Sa+Sb) > 1e-7 && Math.abs(pn) > 1e-7 && Math.abs(Vn) > 1e-7) {
                double value = sigma1 * gamma * m * q0 * q0 / pn / Vn / (Sa+Sb) / (Sa+Sb);
                value = Math.pow(value, gamma / (1-gamma));
                value = 1 - value;
                getSystemData().setValueByIdentifer("eta0", value, true);
                return true;
            }
        }
        if(getSystemData().isValueSetted("gamma") && 
                getSystemData().isValueSetted("Sa") && 
                getSystemData().isValueSetted("Sb") && 
                getSystemData().isValueSetted("x1") && 
                getSystemData().isValueSetted("Vn")) {
            double Vn = getSystemData().getValueByIdentifer("Vn", true);
            double x1 = getSystemData().getValueByIdentifer("x1", true);
            double Sa = getSystemData().getValueByIdentifer("Sa", true);
            double Sb = getSystemData().getValueByIdentifer("Sb", true);
            double gamma = getSystemData().getValueByIdentifer("gamma", true);
            if (Math.abs(x1table) > 1e-7 && Math.abs(Vn) > 1e-7) {
                double value = gamma * (Sa + Sb) * x1 / x1table / Vn;
                value = Math.pow(value, gamma);
                value = 1 - value;
                getSystemData().setValueByIdentifer("eta0", value, true);
                return true;
            }
        }
        if(getSystemData().isValueSetted("pn") && 
                getSystemData().isValueSetted("p3")) {
            double pn = getSystemData().getValueByIdentifer("pn", true);
            double p3 = getSystemData().getValueByIdentifer("p3", true);
            if (Math.abs(p3) > 1e-7) {
                double value = 1 - p3table * pn / p3;
                getSystemData().setValueByIdentifer("eta0", value, true);
                return true;
            }
        }
        return false;
    }
    
    private boolean calcM(double sigma1) {
        if(getSystemData().isValueSetted("eta0") && 
                getSystemData().isValueSetted("pn") && 
                getSystemData().isValueSetted("vn") && 
                getSystemData().isValueSetted("q0") &&
                getSystemData().isValueSetted("Sa") && 
                getSystemData().isValueSetted("Sb")) {
            double pn = getSystemData().getValueByIdentifer("pn", true);
            double q0 = getSystemData().getValueByIdentifer("q0", true);
            double Vn = getSystemData().getValueByIdentifer("Vn", true);
            double eta0 = getSystemData().getValueByIdentifer("eta0", true);
            double Sa = getSystemData().getValueByIdentifer("Sa", true);
            double Sb = getSystemData().getValueByIdentifer("Sb", true);
            double gamma = getSystemData().getValueByIdentifer("gamma", true);
            if (Math.abs(sigma1) > 1e-7 && Math.abs(q0) > 1e-7) {
                double value = Math.pow(1-eta0, 1/gamma-1) * pn * Vn * (Sa+Sb) * (Sa+Sb) / gamma / sigma1 / q0 / q0;
                getSystemData().setValueByIdentifer("m", value, true);
                return true;
            }
        }
        return false;
    }
    
    private boolean calcX1(double x1table) {
        if(getSystemData().isValueSetted("eta0") && 
                getSystemData().isValueSetted("vn") && 
                getSystemData().isValueSetted("Sa") && 
                getSystemData().isValueSetted("Sb")) {
            double Vn = getSystemData().getValueByIdentifer("Vn", true);
            double eta0 = getSystemData().getValueByIdentifer("eta0", true);
            double Sa = getSystemData().getValueByIdentifer("Sa", true);
            double Sb = getSystemData().getValueByIdentifer("Sb", true);
            double gamma = getSystemData().getValueByIdentifer("gamma", true);
            if (Math.abs(Sa+Sb) > 1e-7 ) {
                double value = x1table * Math.pow(1-eta0, 1/gamma) * Vn / gamma / (Sa+Sb);
                getSystemData().setValueByIdentifer("x1", value, true);
                return true;
            }
        }
        return false;
    }
    
    private boolean calcASurface(double x1table, double sigma0, double sigma1) {
        if(getSystemData().isValueSetted("pn") && 
                getSystemData().isValueSetted("q0") && 
                getSystemData().isValueSetted("Vn") && 
                getSystemData().isValueSetted("eta0") && 
                getSystemData().isValueSetted("m") &&
                getSystemData().isValueSetted("Sb")) {
            double pn = getSystemData().getValueByIdentifer("pn", true);
            double q0 = getSystemData().getValueByIdentifer("q0", true);
            double Vn = getSystemData().getValueByIdentifer("Vn", true);
            double eta0 = getSystemData().getValueByIdentifer("eta0", true);
            double m = getSystemData().getValueByIdentifer("m", true);
            double Sb = getSystemData().getValueByIdentifer("Sb", true);
            double gamma = getSystemData().getValueByIdentifer("gamma", true);
            if (Math.abs(sigma1) > 1e-7 && Math.abs(m) > 1e-7) {
                double value = Math.pow(1-eta0, 1/gamma-1) * pn * Vn / gamma / sigma1 / m;
                value = Math.sqrt(q0 * q0 / value) - Sb;
                getSystemData().setValueByIdentifer("Sa", value, true);
                return true;
            }
        }
        if(getSystemData().isValueSetted("Sb")) {
            double Sb = getSystemData().getValueByIdentifer("Sb", true);
            double value = sigma0 * Sb;
            getSystemData().setValueByIdentifer("Sa", value, true);
            return true;
        }
        if(getSystemData().isValueSetted("eta0") && 
                getSystemData().isValueSetted("Vn") && 
                getSystemData().isValueSetted("x1") && 
                getSystemData().isValueSetted("Sb")) {
            double Vn = getSystemData().getValueByIdentifer("Vn", true);
            double eta0 = getSystemData().getValueByIdentifer("eta0", true);
            double x1 = getSystemData().getValueByIdentifer("x1", true);
            double Sb = getSystemData().getValueByIdentifer("Sb", true);
            double gamma = getSystemData().getValueByIdentifer("gamma", true);
            if (Math.abs(x1) > 1e-7) {
                double value = x1table * Math.pow(1-eta0, 1/gamma) * Vn / gamma / x1 - Sb;
                getSystemData().setValueByIdentifer("Sa", value, true);
                return true;
            }
        }
        if(getSystemData().isValueSetted("eta0") && 
                getSystemData().isValueSetted("m") && 
                getSystemData().isValueSetted("q0") && 
                getSystemData().isValueSetted("pn") && 
                getSystemData().isValueSetted("x1")) {
            double pn = getSystemData().getValueByIdentifer("pn", true);
            double q0 = getSystemData().getValueByIdentifer("q0", true);
            double eta0 = getSystemData().getValueByIdentifer("eta0", true);
            double x1 = getSystemData().getValueByIdentifer("x1", true);
            double m = getSystemData().getValueByIdentifer("m", true);
            if (Math.abs(x1) > 1e-7 && Math.abs(pn) > 1e-7 ) {
                double value = x1table * sigma1 * m * q0 * q0 * (1-eta0) / pn / x1;
                value = Math.pow(value, 1./3.);
                value = value / (1 + 1./sigma0);
                getSystemData().setValueByIdentifer("Sa", value, true);
                return true;
            }
        }
        return false;
    }
    
    private boolean calcBSurface(double x1table, double sigma0, double sigma1) {
        if(getSystemData().isValueSetted("Sa")) {
            double Sa = getSystemData().getValueByIdentifer("Sa", true);
            if (Math.abs(sigma0) > 1e-7) {
                double value = Sa / sigma0;
                getSystemData().setValueByIdentifer("Sb", value, true);
                return true;
            }
        }
        if(getSystemData().isValueSetted("pn") && 
                getSystemData().isValueSetted("q0") && 
                getSystemData().isValueSetted("Vn") && 
                getSystemData().isValueSetted("eta0") && 
                getSystemData().isValueSetted("m") &&
                getSystemData().isValueSetted("Sa")) {
            double pn = getSystemData().getValueByIdentifer("pn", true);
            double q0 = getSystemData().getValueByIdentifer("q0", true);
            double Vn = getSystemData().getValueByIdentifer("Vn", true);
            double eta0 = getSystemData().getValueByIdentifer("eta0", true);
            double m = getSystemData().getValueByIdentifer("m", true);
            double Sa = getSystemData().getValueByIdentifer("Sa", true);
            double gamma = getSystemData().getValueByIdentifer("gamma", true);
            if (Math.abs(sigma1) > 1e-7 && Math.abs(m) > 1e-7) {
                double value = Math.pow(1-eta0, 1/gamma-1) * pn * Vn / gamma / sigma1 / m;
                value = Math.sqrt(q0 * q0 / value) - Sa;
                getSystemData().setValueByIdentifer("Sb", value, true);
                return true;
            }
        }
        if(getSystemData().isValueSetted("eta0") && 
                getSystemData().isValueSetted("Vn") && 
                getSystemData().isValueSetted("x1") && 
                getSystemData().isValueSetted("Sa")) {
            double Vn = getSystemData().getValueByIdentifer("Vn", true);
            double eta0 = getSystemData().getValueByIdentifer("eta0", true);
            double x1 = getSystemData().getValueByIdentifer("x1", true);
            double Sa = getSystemData().getValueByIdentifer("Sa", true);
            double gamma = getSystemData().getValueByIdentifer("gamma", true);
            if (Math.abs(x1) > 1e-7) {
                double value = x1table * Math.pow(1-eta0, 1/gamma) * Vn / gamma / x1 - Sa;
                getSystemData().setValueByIdentifer("Sb", value, true);
                return true;
            }
        }
        if(getSystemData().isValueSetted("eta0") && 
                getSystemData().isValueSetted("m") && 
                getSystemData().isValueSetted("q0") && 
                getSystemData().isValueSetted("pn") && 
                getSystemData().isValueSetted("x1")) {
            double pn = getSystemData().getValueByIdentifer("pn", true);
            double q0 = getSystemData().getValueByIdentifer("q0", true);
            double eta0 = getSystemData().getValueByIdentifer("eta0", true);
            double x1 = getSystemData().getValueByIdentifer("x1", true);
            double m = getSystemData().getValueByIdentifer("m", true);
            if (Math.abs(x1) > 1e-7 && Math.abs(pn) > 1e-7 ) {
                double value = x1table * sigma1 * m * q0 * q0 * (1-eta0) / pn / x1;
                value = Math.pow(value, 1./3.);
                value = value / (1 + sigma0);
                getSystemData().setValueByIdentifer("Sb", value, true);
                return true;
            }
        }
        return false;
    }
    
    private boolean calcVn(double x1table, double sigma1) {
        if(getSystemData().isValueSetted("eta0") && 
                getSystemData().isValueSetted("m") && 
                getSystemData().isValueSetted("q0") && 
                getSystemData().isValueSetted("Sa") && 
                getSystemData().isValueSetted("Sb") && 
                getSystemData().isValueSetted("pn")) {
            double eta0 = getSystemData().getValueByIdentifer("eta0", true);
            double q0 = getSystemData().getValueByIdentifer("q0", true);
            double pn = getSystemData().getValueByIdentifer("pn", true);
            double m = getSystemData().getValueByIdentifer("m", true);
            double Sa = getSystemData().getValueByIdentifer("Sa", true);
            double Sb = getSystemData().getValueByIdentifer("Sb", true);
            double gamma = getSystemData().getValueByIdentifer("gamma", true);
            double help = Math.pow(1-eta0, 1-1/gamma);
            if (Math.abs(Sa+Sb) > 1e-7 && Math.abs(help) > 1e-7 && Math.abs(pn) > 1e-7) {
                double value = sigma1 * gamma * m * q0 * q0 * help / (Sa+Sb) / (Sa+Sb) / pn;
                getSystemData().setValueByIdentifer("Vn", value, true);
                return true;
            }
        }
        if(getSystemData().isValueSetted("eta0") && 
                getSystemData().isValueSetted("x1") && 
                getSystemData().isValueSetted("Sa") && 
                getSystemData().isValueSetted("Sb")) {
            double Sb = getSystemData().getValueByIdentifer("Sb", true);
            double eta0 = getSystemData().getValueByIdentifer("eta0", true);
            double x1 = getSystemData().getValueByIdentifer("x1", true);
            double Sa = getSystemData().getValueByIdentifer("Sa", true);
            double gamma = getSystemData().getValueByIdentifer("gamma", true);
            if (Math.abs(x1table) > 1e-7) {
                double value = gamma * (Sa+Sb) * x1 / x1table / Math.pow(1-eta0, 1/gamma);
                getSystemData().setValueByIdentifer("Vn", value, true);
                return true;
            }
        }
        return false;
    }
    
    private boolean calcP3(double p3table) {
        if(getSystemData().isValueSetted("pn") && 
                getSystemData().isValueSetted("eta0")) {
            double eta0 = getSystemData().getValueByIdentifer("eta0", true);
            double pn = getSystemData().getValueByIdentifer("pn", true);
            if (Math.abs(1-eta0) > 1e-7) {
                double value = p3table * pn / (1-eta0);
                getSystemData().setValueByIdentifer("p3", value, true);
                return true;
            }
        }
        return false;
    }
    
    private boolean calcF(double TCtable) {
        if(getSystemData().isValueSetted("Vn") && 
                getSystemData().isValueSetted("q0") && 
                getSystemData().isValueSetted("eta0")) {
            double Vn = getSystemData().getValueByIdentifer("Vn", true);
            double eta0 = getSystemData().getValueByIdentifer("eta0", true);
            double q0 = getSystemData().getValueByIdentifer("q0", true);
            double gamma = getSystemData().getValueByIdentifer("gamma", true);
            if (Math.abs(q0) > 1e-7) {
                double value = TCtable * Math.pow(1-eta0, 1/gamma) * Vn / gamma / q0;
                getSystemData().setValueByIdentifer("f", 1/value, true);
                return true;
            }
        }
        return false;
    }
    
    private boolean calcXmax(double xmaxtable) {
        if(getSystemData().isValueSetted("Vn") &&  
                getSystemData().isValueSetted("eta0") && 
                getSystemData().isValueSetted("Sa") && 
                getSystemData().isValueSetted("Sb")) {
            double eta0 = getSystemData().getValueByIdentifer("eta0", true);
            double Vn = getSystemData().getValueByIdentifer("Vn", true);
            double Sa = getSystemData().getValueByIdentifer("Sa", true);
            double Sb = getSystemData().getValueByIdentifer("Sb", true);
            double gamma = getSystemData().getValueByIdentifer("gamma", true);
            if (Math.abs(Sa+Sb) > 1e-7) {
                double value = xmaxtable * Math.pow(1-eta0, 1/gamma) * Vn / (Sa+Sb) / gamma;
                getSystemData().setValueByIdentifer("xmax", value, true);
                return true;
            }
        }
        return false;
    }
    
    private boolean calcVi(double vitable) {
        if(getSystemData().isValueSetted("q0") &&
                getSystemData().isValueSetted("Sa") && 
                getSystemData().isValueSetted("Sb")) {
            double q0 = getSystemData().getValueByIdentifer("q0", true);
            double Sa = getSystemData().getValueByIdentifer("Sa", true);
            double Sb = getSystemData().getValueByIdentifer("Sb", true);
            if (Math.abs(Sa+Sb) > 1e-7) {
                double value = vitable * q0 / (Sa+Sb);
                getSystemData().setValueByIdentifer("Vi", value, true);
                return true;
            }
        }
        return false;
    }
    
    private boolean calcN(double ntable, double sigma1) {
        if(getSystemData().isValueSetted("q0") &&
                getSystemData().isValueSetted("eta0") && 
                getSystemData().isValueSetted("pn")) {
            double q0 = getSystemData().getValueByIdentifer("q0", true);
            double eta0 = getSystemData().getValueByIdentifer("eta0", true);
            double pn = getSystemData().getValueByIdentifer("pn", true);
            if (Math.abs(1-eta0) > 1e-7 && Math.abs(sigma1) > 1e-7) {
                double value = ntable * q0 * pn / sigma1 / (1-eta0);
                getSystemData().setValueByIdentifer("N", value, true);
                return true;
            }
        }
        return false;
    }
    
    private boolean calcPmin(double pmintable) {
        if(getSystemData().isValueSetted("eta0") && 
                getSystemData().isValueSetted("pn")) {
            double eta0 = getSystemData().getValueByIdentifer("eta0", true);
            double pn = getSystemData().getValueByIdentifer("pn", true);
            if (Math.abs(1-eta0) > 1e-7) {
                double value = pmintable * pn / (1-eta0);
                getSystemData().setValueByIdentifer("pmin", value, true);
                return true;
            }
        }
        return false;
    }
    
    private boolean calcPmax(double pmaxtable) {
        if(getSystemData().isValueSetted("eta0") && 
                getSystemData().isValueSetted("pn")) {
            double eta0 = getSystemData().getValueByIdentifer("eta0", true);
            double pn = getSystemData().getValueByIdentifer("pn", true);
            if (Math.abs(1-eta0) > 1e-7) {
                double value = pmaxtable * pn / (1-eta0);
                getSystemData().setValueByIdentifer("pmax", value, true);
                return true;
            }
        }
        return false;
    }

    @Override
    public void setDataBySelection(ArrayList<Double> choosenValues) {
        clearNotSettedParams();
        List<Integer> resultParamsIndexes = getResultParamsIndexes();
        List<String> resultParamsIdentifers = getResultParamsTitlesToShow();
        for (int index = 0; index < resultParamsIdentifers.size(); ++index) {
            getSystemData().setValueByIdentifer(resultParamsIdentifers.get(index), 
                    choosenValues.get(resultParamsIndexes.get(index)), false);
        }
    }
}
