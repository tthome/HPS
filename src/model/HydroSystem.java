/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import utils.Constants;

/**
 *
 * @author Rasputina_T
 */
public abstract class HydroSystem {
   
    public static enum Type {
        DIRECT,
        REVERSE,
        DOUBLE
    }
    
    protected final List<Integer> indexesOfValues = Arrays.asList(0, 1, 2, 5, 6, 7, 8, 9, 10, 14);
    
    public Type type;
    
    private List<String> resultParamsTitles;
    private List<String> resultParamsTitlesToShow;
    
    private SystemData systemData;
    private SystemData udBounds;
    private SystemData downBounds;
    
    private double k1 = 1.2;
    private double k2 = 0.5;
    private double k3 = 1.1;
    private double k4 = 0.08;

    public double getK1() {
        return k1;
    }

    public double getK2() {
        return k2;
    }

    public double getK3() {
        return k3;
    }

    public double getK4() {
        return k4;
    }

    public void setK1(double k1) {
        this.k1 = k1;
    }

    public void setK2(double k2) {
        this.k2 = k2;
    }

    public void setK3(double k3) {
        this.k3 = k3;
    }

    public void setK4(double k4) {
        this.k4 = k4;
    }

    public List<String> getResultParamsTitlesToShow() {
        return resultParamsTitlesToShow;
    }
    
    private void findResultParamsTitles() {
        resultParamsTitlesToShow = new ArrayList<>();
        for (String parameter: resultParamsTitles) {
            if (!systemData.userSetted.get(systemData.getIndexByIdentifer(parameter.toLowerCase()))) {
                resultParamsTitlesToShow.add(parameter);
            }
        }
    }

    public List<String> getResultParamsTitles() {
        if(resultParamsTitlesToShow == null || resultParamsTitlesToShow.isEmpty()) {
            findResultParamsTitles();
        }
        return resultParamsTitlesToShow;
    }
    
    public List<String> getResultParamsTitlesWithUtits() {
        if(resultParamsTitlesToShow == null || resultParamsTitlesToShow.isEmpty()) {
            findResultParamsTitles();
        }
        return getResultParamsTitlesToShowWithUtits(resultParamsTitlesToShow);
    }
    
    public void clearResultParamsTitlesToShow(){
        if(resultParamsTitlesToShow != null) {
            resultParamsTitlesToShow.clear();
        }
    }
    
    public abstract List<String> getResultParamsTitlesToShowWithUtits(List<String> paramsTitlesToShow);
    
    public List<Integer> getResultParamsIndexes() {
        List<Integer> trueResultIndexes = new ArrayList<>();
        if (resultParamsTitles != null) {
            for (int i = 0; i < resultParamsTitles.size(); ++i) {
                if (!systemData.userSetted.get(systemData.getIndexByIdentifer(resultParamsTitles.get(i).toLowerCase()))) {
                    trueResultIndexes.add(i);
                }
            }
        }
        return trueResultIndexes;
    }

    public void setResultParamsTitles(List<String> resultParamsTitles) {
        this.resultParamsTitles = resultParamsTitles;
    }
    
    public SystemData getSystemData() {
        return systemData;
    }
    
    public void setSystemData(SystemData systemData) {
        this.systemData = systemData;
    }
    
    public SystemData getSystemUpBounds() {
        return udBounds;
    }
    
    public void setSystemUpBounds(SystemData udBounds) {
        this.udBounds = udBounds;
    }
    
    public SystemData getSystemDownBounds() {
        return downBounds;
    }
    
    public void setSystemDownBounds(SystemData downBounds) {
        this.downBounds = downBounds;
    }
    
    protected int getSettedBasicVariablesCount() {
        int count = indexesOfValues.size();
        for(Integer index: indexesOfValues) {
            if (systemData.getValueByIndex(index, false) < 0) {
                --count;
            }
        }
        return count;
    }
    
    public void clearNotSettedParams() {
        for(int i = 0; i < systemData.userSetted.size(); ++i) {
            if(!systemData.userSetted.get(i)) {
                systemData.setValueByIndex(i, -1, false);
            }
        }
        
        clearCalculated();
    }
    
    public boolean isBasicParamsValid() {
        for(int i = 0; i < 19; ++i) {            
            if(systemData.isValueSetted(i)) {
                if(udBounds.userSetted.get(i) && udBounds.getValueByIndex(i, false) < systemData.getValueByIndex(i, false)) {
                    return false;
                }

                if(downBounds.userSetted.get(i) && downBounds.getValueByIndex(i, false) > systemData.getValueByIndex(i, false)) {
                    return false;
                }
            }
            
            if (udBounds.userSetted.get(i) && downBounds.userSetted.get(i) && ((udBounds.getValueByIndex(i, false) - downBounds.getValueByIndex(i, false)) < 0)) {
                return false;
            }
        }
        return true;
    }
    
    public void clearCalculated() {
        systemData.clearCalculated();
    }
    
    public abstract boolean isSettedEnough();
    
    public abstract boolean isValid();
    
    public abstract boolean isComplete();
    
    public abstract void calcAllParams(ArrayList<Double> data);
    
    public boolean isVariant() {
        return isComplete() && isValid();
    }
    
    public abstract ArrayList<Double> getVariantWithData(ArrayList<Double> data);
    
    public abstract void setDataBySelection(ArrayList<Double> choosenValues);
    
    public abstract double calcMass(double D);
    
    public abstract boolean isDValid(double D);
    
    private double getDUpBound(double m, double ro) {
        double coef = 5;
        return Math.pow(4*m/ro/Constants.PI/coef, 1./3.);
    }
    
    private double getDDownBound(double m, double ro) {
        double coef = 500;
        return Math.pow(4*m/ro/Constants.PI/coef, 1./3.);
    }
    
    public void makeCalculationsWithData() {
        double massSum = systemData.getValueByIdentifer("m", true);
        double ro = systemData.getValueByIdentifer("ro", true);
        double eps = 1e-5;
        double epsResidual = 1e-5;
        double delta = 1e-3;
        double D;
        double Dup = getDUpBound(massSum, ro);//Math.sqrt(massSum/1e-2/ro/Constants.PI);
        double Ddown = getDDownBound(massSum, ro);
        while (!isDValid(Ddown)) {
            Ddown += delta;
        }
        Dup += Ddown;
        double systemMass = 0;
        double residual = Math.abs((systemMass-massSum)/massSum);
        double residualPrev = 1000;
        while (residual > eps) {
            D = (Dup+Ddown) / 2.;
            systemMass = calcMass(D);
            
            residual = Math.abs((systemMass-massSum)/massSum);
            if (Math.abs((residualPrev-residual)/residual) < epsResidual) {
                break;
            }
            residualPrev = residual;
            if (systemMass > massSum) {
                Dup = D;
            } else {
                Ddown = D;
            }
        }
        D = (Dup+Ddown) / 2.;
        systemMass = calcMass(D);
        systemData.setValueByIdentifer("resultm", systemMass, true);
    }
}
