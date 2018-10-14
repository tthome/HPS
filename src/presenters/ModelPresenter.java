/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import model.BidirectionalSystem;
import static model.BidirectionalSystem.Type.BOTH_CHAMBERS;
import model.HydroSystem;
import model.HydroSystem.Type;
import static model.HydroSystem.Type.DIRECT;
import static model.HydroSystem.Type.DOUBLE;

/**
 *
 * @author etb-t
 */
public class ModelPresenter {
    
    private HydroSystem system;
    private static ModelPresenter presenterInstance;
    private Type requestedType;
    private BidirectionalSystem.Type biType = BOTH_CHAMBERS;
    
    private ArrayList<ArrayList<Double>> tables = new ArrayList<>();
    private ArrayList<ArrayList<Double>> foundVariants;
    private ArrayList<ArrayList<Double>> filteredFoundVariants;
    private LinkedHashMap<Integer, List<Double>> filteredSelection = new LinkedHashMap<>();
    private int selectedConfiguration;
    private boolean searchComplete;
    
    private int selectionForCalc;
   
    private ModelPresenter(HydroSystem system) {
        this.system = system;
    }
    
    public static ModelPresenter getInstance() {
        if(presenterInstance == null) {
            presenterInstance = new ModelPresenter(new BidirectionalSystem());
            presenterInstance.requestedType = DOUBLE;
        }
        return presenterInstance;
    }
    
    public BidirectionalSystem.Type getDoubleSystemType() {
        return biType;
    }
    
    public void setDoubleSystemType(BidirectionalSystem.Type biType) {
        this.biType = biType;
    }

    public boolean isSearchComplete() {
        return searchComplete;
    }

    public int getSelectedConfiguration() {
        return selectedConfiguration;
    }

    public ArrayList<ArrayList<Double>> getTables() {
        return tables;
    }

    public void setSelectedConfiguration(int selectedConfiguration) {
        this.selectedConfiguration = selectedConfiguration;
    }
    
    public HydroSystem getSystem() {
        return system;
    }

    public void setSystem(HydroSystem system) {
        this.system = system;
        requestedType = system.type;
    }

    public Type getRequestedType() {
        return requestedType;
    }

    public void setRequestedType(Type requestedType) {
        this.requestedType = requestedType;
    }

    public void setSelectionForCalculations(int selectionForCalc) {
        this.selectionForCalc = selectionForCalc;
    }
    
    public void addTablesContent(ArrayList<Double> tableContent) {
        tables.add(tableContent);
    }
    
    public void clearFoundVariants() {
        if (foundVariants != null) {
            foundVariants.clear();
        }
        clearFilteredFoundVariants();
    }
    
    public void clearFilteredFoundVariants() {
        if (filteredFoundVariants != null) {
            filteredFoundVariants.clear();
            filteredFoundVariants = null;
            filteredSelection.clear();
        }
    }
    
    public void clearTables() {
        if (tables != null) {
            tables.clear();
        }
    }
    
    public void clearAll() {
        clearTables();
        clearFoundVariants();
        system.clearResultParamsTitlesToShow();
        presenterInstance = null;
    }
    
    public void clearTableColumnsNaming(){
        system.clearResultParamsTitlesToShow();
    }
    
    public void clearModel() {
        system.clearCalculated();
    }
    
    public void makeSearch(javax.swing.JDialog progressDialog, javax.swing.JProgressBar progressBar) {
        searchComplete = false;
        progressBar.setMinimum(0);
        progressBar.setMaximum(tables.size());
        progressBar.setValue(0);
        foundVariants = new ArrayList<>();
        for(int i = 0; i < tables.size(); ++i) {
            ArrayList<Double> data = tables.get(i);
            system.clearNotSettedParams();
            system.calcAllParams(data);
            if(system.isVariant()) {
                foundVariants.add(system.getVariantWithData(data));
            }
            progressBar.setValue(i);
        }
        
        system.clearNotSettedParams();
        
        searchComplete = true;
    }
    
    public List<Double> getFilterSelection(int column) {
        return filteredSelection.get(column);
    }
    
    public void setFilteredFoundVariants(int column, Double min, Double max) {
        List<Integer> resultParamsIndexes = system.getResultParamsIndexes();
        if(foundVariants != null) {
            filteredSelection.put(column, Arrays.asList(min, max));
            filteredFoundVariants = new ArrayList<>();
            
            for(int i = 0; i < foundVariants.size(); ++i) {
                boolean isValueFit = true;
                for(Integer selectedColumn: filteredSelection.keySet()) {
                    double value = foundVariants.get(i).get(resultParamsIndexes.get(selectedColumn));
                    Double selectedMin = filteredSelection.get(selectedColumn).get(0);
                    Double selectedMax = filteredSelection.get(selectedColumn).get(1);
                    isValueFit = isValueFit && selectedMin != null && (value > selectedMin || Math.abs((value-selectedMin)/selectedMin) < 1e-5);
                    isValueFit = isValueFit && selectedMax != null && (value < selectedMax || Math.abs((value-selectedMax)/selectedMax) < 1e-5);
                    if(!isValueFit) {
                        break;
                    }
                }
                if(isValueFit) {
                    filteredFoundVariants.add(foundVariants.get(i));
                }
            }
        }
    }
    
    public javax.swing.DefaultComboBoxModel<String> getFilterComboBoxModel() {
        return new javax.swing.DefaultComboBoxModel<>(getTableTitles());
    }
    
    private double min;
    private double max;
    
    public void setSelectedVariableMinInFoundVariants(int column) {
        List<Integer> resultParamsIndexes = system.getResultParamsIndexes();
        min = foundVariants.get(0).get(resultParamsIndexes.get(column));
        max = foundVariants.get(0).get(resultParamsIndexes.get(column));
        for(int i = 1; i < foundVariants.size(); ++i) {
            double toCompare = foundVariants.get(i).get(resultParamsIndexes.get(column));
            if(min > toCompare) {
                min = toCompare;
            }
            if(max < toCompare) {
                max = toCompare;
            }
        }
    }
    
    public double getSelectedVariableMinInFoundVariants() {
        return min;
    }
    
    public double getSelectedVariableMaxInFoundVariants() {
        return max;
    }
    
    private Object[][] getObjectTable() {
        List<Integer> resultParamsIndexes = system.getResultParamsIndexes();
        int size = resultParamsIndexes.size();
        if(filteredFoundVariants != null){
            Object[][] objects = new Object[filteredFoundVariants.size()][size];
            for(int i = 0; i < filteredFoundVariants.size(); ++i) {
                for(int j = 0; j < size; ++j) {
                    objects[i][j] = filteredFoundVariants.get(i).get(resultParamsIndexes.get(j));
                }
            }
            return objects;
        }
        if(foundVariants != null){
            Object[][] objects = new Object[foundVariants.size()][size];
            for(int i = 0; i < foundVariants.size(); ++i) {
                for(int j = 0; j < size; ++j) {
                    objects[i][j] = foundVariants.get(i).get(resultParamsIndexes.get(j));
                }
            }
            return objects;
        } else {
            return new Object[][]{};
        }
    }
    
    private String[] getTableTitles() {
        List<String> titles = system.getResultParamsTitlesWithUtits();
        String [] titlesArray = new String[titles.size()];
        for (int i = 0 ; i < titles.size(); ++i) {
            titlesArray[i] = titles.get(i);
        }
        return titlesArray;
    }
    
    private Class[] getTableClassTypes() {
        int size = system.getResultParamsTitles().size();
        Class [] classResult = new Class [size];
        for(int i = 0; i < size; ++i) {
            classResult[i] = java.lang.Double.class;
        }
        return classResult;
    }
    
    private boolean[] getTableEditSettings() {
        int size = system.getResultParamsTitles().size();
        boolean [] editSettingsResult = new boolean [size];
        for(int i = 0; i < size; ++i) {
            editSettingsResult[i] = false;
        }
        return editSettingsResult;
    }
    
    public boolean isVariantsFound() {
        return foundVariants!= null && foundVariants.size() > 0;
    }
    
    public javax.swing.table.DefaultTableModel getTableModel() {
        javax.swing.table.DefaultTableModel table = null;
        switch(system.type){
            case DOUBLE: {
                table = new javax.swing.table.DefaultTableModel(
                    getObjectTable(),
                    getTableTitles()
                ) {
                    Class[] types = getTableClassTypes();
                    boolean[] canEdit = getTableEditSettings();

                    @Override
                    public Class getColumnClass(int columnIndex) {
                        return types [columnIndex];
                    }

                    @Override
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit [columnIndex];
                    }
                };
            } break;
            case DIRECT: {
                table = new javax.swing.table.DefaultTableModel(
                    getObjectTable(),
                    getTableTitles()
                ) {
                    Class[] types = getTableClassTypes();
                    boolean[] canEdit = getTableEditSettings();

                    @Override
                    public Class getColumnClass(int columnIndex) {
                        return types [columnIndex];
                    }

                    @Override
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit [columnIndex];
                    }
                };
            } break;
            case REVERSE: {
                table = new javax.swing.table.DefaultTableModel(
                    getObjectTable(),
                    getTableTitles()
                ) {
                    Class[] types = getTableClassTypes();
                    boolean[] canEdit = getTableEditSettings();

                    @Override
                    public Class getColumnClass(int columnIndex) {
                        return types [columnIndex];
                    }

                    @Override
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit [columnIndex];
                    }
                };
            } break;
        }
        return table;        
    }
    
    public void setDataBySelection() {
        if(filteredFoundVariants != null && filteredFoundVariants.size() > selectionForCalc) {
            system.setDataBySelection(filteredFoundVariants.get(selectionForCalc));
        } else {
            if(foundVariants != null && foundVariants.size() > selectionForCalc) {
                system.setDataBySelection(foundVariants.get(selectionForCalc));
            }
        }
    }
    
    public void makeCalculations() {
        system.makeCalculationsWithData();
    }
}
