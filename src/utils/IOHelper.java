/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.BidirectionalSystem;
import model.DirectSystem;
import model.HydroSystem;
import static model.HydroSystem.Type.DOUBLE;
import model.ReverseSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import presenters.ModelPresenter;

/**
 *
 * @author etb-t
 */
public final class IOHelper {
    
    private IOHelper(){}

    public static void readTableFromTxtFile(File file) {
        if (file != null) {
            try {
                try (FileInputStream fileInput = new FileInputStream(file); 
                        DataInputStream dataInput = new DataInputStream(fileInput)) {
                    String line = dataInput.readLine();
                    String x1 = file.getAbsolutePath();
                    int indexStart = x1.indexOf("x1");
                    int indexEnd = x1.indexOf(".txt");
                    x1 = x1.substring(indexStart+3, indexEnd);
                    while(dataInput.available()!= 0){
                        ArrayList<Double> tableRow = new ArrayList<>();
                        tableRow.add(Double.parseDouble(x1));
                        line = dataInput.readLine();
                        String[] words = line.split(" ");
                        for(String word: words) {
                            if(!word.isEmpty()) {
                                tableRow.add(Double.parseDouble(word));
                            }
                        }
                        ModelPresenter.getInstance().addTablesContent(tableRow);
                    }
                }
            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static File getLastInputDirectory(Class context) throws Exception {
        String dirName = "saved";
        File directory = getDirectory(context, dirName);
        String filePath = directory.getAbsolutePath() + File.separatorChar + "io_settings_input.txt";
        File fileToFind = new File(filePath);
        return getLastDirectory(fileToFind);
    }
    
    public static File getLastOutputTablesDirectory(Class context) throws Exception  {
        String dirName = "saved";
        File directory = getDirectory(context, dirName);
        String filePath = directory.getAbsolutePath() + File.separatorChar + "io_settings_output_tables.txt";
        File fileToFind = new File(filePath);
        return getLastDirectory(fileToFind);
    }
    
    public static File getLastOutputStrikerDirectory(Class context) throws Exception  {
        String dirName = "saved";
        File directory = getDirectory(context, dirName);
        String filePath = directory.getAbsolutePath() + File.separatorChar + "io_settings_output_striker.txt";
        File fileToFind = new File(filePath);
        return getLastDirectory(fileToFind);
    }
    
    private static File getLastDirectory(File fileToFind) {
        File resultDirectory = null;
        if(fileToFind != null) {
            try {
                if (fileToFind.exists()) {
                    FileReader fileReader = new FileReader(fileToFind.getAbsolutePath());
                    try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                        String line = bufferedReader.readLine();
                        if(line != null && !line.trim().equals("")) {
                            resultDirectory = new File(line);
                        }
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(IOHelper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultDirectory;
    }
    
    public static void saveLastInputDirectory(Class context, String lastPath) throws Exception {
        String dirName = "saved";
        File directory = getDirectory(context, dirName);
        String filePath = directory.getAbsolutePath() + File.separatorChar + "io_settings_input.txt";
        File outputFile = new File(filePath);
        saveLastDirectory(outputFile, lastPath);
    }
    
    public static void saveLastOutputTablesDirectory(Class context, String lastPath) throws Exception {
        String dirName = "saved";
        File directory = getDirectory(context, dirName);
        String filePath = directory.getAbsolutePath() + File.separatorChar + "io_settings_output_tables.txt";
        File outputFile = new File(filePath);
        saveLastDirectory(outputFile, lastPath);
    }
    
    public static void saveLastOutputStrikerDirectory(Class context, String lastPath) throws Exception {
        String dirName = "saved";
        File directory = getDirectory(context, dirName);
        String filePath = directory.getAbsolutePath() + File.separatorChar + "io_settings_output_striker.txt";
        File outputFile = new File(filePath);
        saveLastDirectory(outputFile, lastPath);
    }
    
    private static void saveLastDirectory(File outputFile, String lastPath) {
        try {
            if (outputFile != null) {
                boolean fileExists = outputFile.exists();
                if (!fileExists) {
                    fileExists = outputFile.createNewFile();
                }
                if (fileExists) {
                    FileWriter fileWriter = new FileWriter(outputFile.getAbsolutePath());
                    try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                        bufferedWriter.write(lastPath);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(IOHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static String getRootFolder() {
        File myDocuments = new File(System.getProperty("user.home") + File.separatorChar + "My Documents");
        File programmFileInDocuments = new File(myDocuments.getAbsolutePath() + File.separatorChar + "HPS");
        if (!programmFileInDocuments.exists()) {
            programmFileInDocuments.mkdir();
        }
        return programmFileInDocuments.getAbsolutePath();
    }
    
    private static File getDirectory(Class context, String dirName) {
        String dirPath = "C:\\" + File.separatorChar + "HPS";//getRootFolder() + File.separatorChar + dirName;
        File root = new File(dirPath);
        if (!root.exists()) {
            root.mkdir();
        }
        dirPath = dirPath + File.separatorChar + dirName;
        File directory = new File(dirPath);
        if (!directory.exists()) {
            directory.mkdir();
        }
        return directory;
    }
    
    public static String getExplicitProgrammName() {
        return "ExplicitHPS.exe";
    }
    
    public static String getExplicitProgrammPath() {
        return System.getProperty("user.dir") 
                + File.separatorChar 
                + "process" 
                + File.separatorChar
                + "ExplicitHPS" 
                + File.separatorChar;
    }
    
    public static String getPathForDataForExplicitProgramm() {
        return System.getProperty("user.dir") 
                + File.separatorChar 
                + "process" 
                + File.separatorChar
                + "data" 
                + File.separatorChar;
    }
    
    public static void saveUserModel(Class context, HydroSystem system) {
        try {
            String dirName = "saved";
            File directory = getDirectory(context, dirName);
            String filePath = directory.getAbsolutePath() + File.separatorChar + "last_model.txt";
            File outputFile = new File(filePath);
            boolean fileExists = outputFile.exists();
            if (!fileExists) {
                fileExists = outputFile.createNewFile();
            }
            if (fileExists) {
                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile.getAbsolutePath()))) {
                    String outputLine = "";
                    outputLine = outputLine + system.type.toString();
                    bufferedWriter.write(outputLine);
                    bufferedWriter.newLine();
                    outputLine = "";
                    
                    for (int i = 0; i < 19; ++i) {
                        outputLine = outputLine + 
                                (system.getSystemData().userSetted.get(i) ? 1 : 0) +
                                '\t' +
                                (system.getSystemDownBounds().userSetted.get(i) ? 1 : 0) +
                                '\t' +
                                (system.getSystemUpBounds().userSetted.get(i) ? 1 : 0) + 
                                '\t';

                        if (system.getSystemData().userSetted.get(i)) {
                            outputLine = outputLine + Double.toString(system.getSystemData().getValueByIndex(i, false)) + '\t';
                        }
                        if (system.getSystemDownBounds().userSetted.get(i)) {
                            outputLine = outputLine + Double.toString(system.getSystemDownBounds().getValueByIndex(i, false)) + '\t';
                        }
                        if (system.getSystemUpBounds().userSetted.get(i)) {
                            outputLine = outputLine + Double.toString(system.getSystemUpBounds().getValueByIndex(i, false)) + '\t';
                        }
                        bufferedWriter.write(outputLine);
                        bufferedWriter.newLine();
                        outputLine = "";
                    }
                    
                    if (ModelPresenter.getInstance().getSystem().type != DOUBLE) {
                        for(int i = 19; i < 21; ++i) {
                            outputLine = outputLine +
                                    (system.getSystemData().userSetted.get(i) ? 1 : 0) +
                                    '\t' +
                                    (system.getSystemDownBounds().userSetted.get(i) ? 1 : 0) +
                                    '\t' +
                                    (system.getSystemUpBounds().userSetted.get(i) ? 1 : 0) +
                                    '\t';
                            
                            if (system.getSystemData().userSetted.get(i)) {
                                outputLine = outputLine + Double.toString(system.getSystemData().getValueByIndex(i, false)) + '\t';
                            }
                            if (system.getSystemDownBounds().userSetted.get(i)) {
                                outputLine = outputLine + Double.toString(system.getSystemDownBounds().getValueByIndex(i, false)) + '\t';
                            }
                            if (system.getSystemUpBounds().userSetted.get(i)) {
                                outputLine = outputLine + Double.toString(system.getSystemUpBounds().getValueByIndex(i, false)) + '\t';
                            }
                            bufferedWriter.write(outputLine);
                            bufferedWriter.newLine();
                            outputLine = "";
                        }
                    }
                    outputLine = outputLine + Double.toString(system.getSystemData().getValueByIndex(21, false));
                    bufferedWriter.write(outputLine);
                    bufferedWriter.newLine();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(IOHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static HydroSystem readUserModel(Class context) {
        HydroSystem system = null;
        try {
            String dirName = "saved";
            File directory = getDirectory(context, dirName);
            String filePath = directory.getAbsolutePath() + File.separatorChar + "last_model.txt";
            File outputFile = new File(filePath);
            if(outputFile.exists()) {
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(outputFile.getAbsolutePath()))) {
                    String inputLine = "";
                    inputLine = bufferedReader.readLine();
                    if(inputLine != null && !inputLine.trim().equals("")) {
                        switch(inputLine) {
                            case "DIRECT" :  system = new DirectSystem(); break;
                            case "REVERSE" :  system = new ReverseSystem(); break;
                            case "DOUBLE" :  system = new BidirectionalSystem(); break;
                        }
                        
                        for (int i = 0; i < 19; ++i) {
                            inputLine = bufferedReader.readLine();
                            String[] values = inputLine.split("\t");
                            ArrayList<Double> valuesData = new ArrayList<>();
                            for(String value: values) {
                                value = value.trim();
                                if(value != null) {
                                    valuesData.add(Double.parseDouble(value));
                                }
                            }
                            
                            boolean flagSystemData = valuesData.get(0) > 0.5;
                            boolean flagSystemDataDown = valuesData.get(1) > 0.5;
                            boolean flagSystemDataUp = valuesData.get(2) > 0.5;
                            int index = 3;
                            if (flagSystemData) {
                                system.getSystemData().setValueByIndex(i, valuesData.get(index), false);
                                system.getSystemData().userSetted.set(i, true);
                                ++index;
                            }
                            if (flagSystemDataDown) {
                                system.getSystemDownBounds().setValueByIndex(i, valuesData.get(index), false);
                                system.getSystemDownBounds().userSetted.set(i, true);
                                ++index;
                            }
                            if (flagSystemDataUp) {
                                system.getSystemUpBounds().setValueByIndex(i, valuesData.get(index), false);
                                system.getSystemUpBounds().userSetted.set(i, true);
                                ++index;
                            }
                        }
                        
                        if (system.type != DOUBLE) {
                            for(int i = 19; i < 21; ++i) {
                                inputLine = bufferedReader.readLine();
                                String[] values = inputLine.split("\t");
                                ArrayList<Double> valuesData = new ArrayList<>();
                                for(String value: values) {
                                    value = value.trim();
                                    if(value != null) {
                                        valuesData.add(Double.parseDouble(value));
                                    }
                                }
                                boolean flagSystemData = valuesData.get(0) > 0.5;
                                boolean flagSystemDataDown = valuesData.get(1) > 0.5;
                                boolean flagSystemDataUp = valuesData.get(2) > 0.5;
                                if (flagSystemData) {
                                    system.getSystemData().setValueByIndex(i, valuesData.get(3), false);
                                    system.getSystemData().userSetted.set(i, true);
                                }
                                if (flagSystemDataDown) {
                                    system.getSystemDownBounds().setValueByIndex(i, valuesData.get(4), false);
                                    system.getSystemDownBounds().userSetted.set(i, true);
                                }
                                if (flagSystemDataUp) {
                                    system.getSystemUpBounds().setValueByIndex(i, valuesData.get(5), false);
                                    system.getSystemUpBounds().userSetted.set(i, true);
                                }
                            }
                        }
                        inputLine = bufferedReader.readLine();
                        system.getSystemData().setValueByIndex(21, Double.parseDouble(inputLine), false);
                    }
                }
            }
        } catch (IOException | NumberFormatException ex) {
            Logger.getLogger(IOHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return system;
    }
    
    public static void writeStrikerDataToFile(Class context, File outputFile) throws Exception {
        if (outputFile != null) {
            boolean fileExists = outputFile.exists();
            if (!fileExists) {
                fileExists = outputFile.createNewFile();
            }
            if (fileExists) {
                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile.getAbsolutePath()))) {
                    String mFormat = "m = \t%1$.2f \tкг";
                    String saFormat = "Sa = \t%1$.2f \tсм" + (char) Integer.parseInt("00b2", 16);
                    String sbFormat = "Sb = \t%1$.2f \tсм" + (char) Integer.parseInt("00b2", 16);
                    String l1Format = "l1 = \t%1$.2f \tсм";
                    String l2Format = "l2 = \t%1$.2f \tсм";
                    String l3Format = "l3 = \t%1$.2f \tсм";
                    String l4Format = "l4 = \t%1$.2f \tсм";
                    String l5Format = "l5 = \t%1$.2f \tсм";
                    String daFormat = "Da = \t%1$.2f \tсм";
                    String dbFormat = "Db = \t%1$.2f \tсм";
                    String dcFormat = "Dc = \t%1$.2f \tсм";
                    String dFormat = "D = \t%1$.2f \tсм";
                    String lFormat = "L = \t%1$.2f \tсм";
                    
                    double m = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIndex(5, false);
                    double Sa = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIndex(7, false);
                    double Sb = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIndex(8, false);
                    double l1 = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIndex(22, false);
                    double l2 = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIndex(23, false);
                    double l3 = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIndex(24, false);
                    double l4 = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIndex(25, false);
                    double l5 = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIndex(26, false);
                    double da = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIndex(27, false);
                    double db = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIndex(28, false);
                    double dc = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIndex(29, false);
                    double L = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIndex(30, false);
                    double D = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIndex(31, false);
                    
                    String mText = String.format(Locale.ENGLISH, mFormat, m);
                    String saText = String.format(Locale.ENGLISH, saFormat, Sa);
                    String sbText = String.format(Locale.ENGLISH, sbFormat, Sb);
                    String l1Text = String.format(Locale.ENGLISH, l1Format, l1);
                    String l2Text = String.format(Locale.ENGLISH, l2Format, l2);
                    String l3Text = String.format(Locale.ENGLISH, l3Format, l3);
                    String l4Text = String.format(Locale.ENGLISH, l4Format, l4);
                    String l5Text = String.format(Locale.ENGLISH, l5Format, l5);
                    String daText = String.format(Locale.ENGLISH, daFormat, da);
                    String dbText = String.format(Locale.ENGLISH, dbFormat, db);
                    String dcText = String.format(Locale.ENGLISH, dcFormat, dc);
                    String dText = String.format(Locale.ENGLISH, dFormat, D);
                    String lText = String.format(Locale.ENGLISH, lFormat, L);
                    
                    bufferedWriter.write(mText);
                    bufferedWriter.newLine();
                    bufferedWriter.write(saText);
                    bufferedWriter.newLine();
                    bufferedWriter.write(sbText);
                    bufferedWriter.newLine();
                    bufferedWriter.write(l1Text);
                    bufferedWriter.newLine();
                    bufferedWriter.write(l2Text);
                    bufferedWriter.newLine();
                    bufferedWriter.write(l3Text);
                    bufferedWriter.newLine();
                    bufferedWriter.write(l4Text);
                    bufferedWriter.newLine();
                    bufferedWriter.write(l5Text);
                    bufferedWriter.newLine();
                    bufferedWriter.write(daText);
                    bufferedWriter.newLine();
                    bufferedWriter.write(dbText);
                    bufferedWriter.newLine();
                    bufferedWriter.write(dcText);
                    bufferedWriter.newLine();
                    bufferedWriter.write(dText);
                    bufferedWriter.newLine();
                    bufferedWriter.write(lText);
                    bufferedWriter.newLine();
                }
            }
        }
    }
    
    public static void readExplicitResult(Class context, HydroSystem system) throws IOException {
        String pathForExplicitResultData = getPathForDataForExplicitProgramm() 
                + "out" 
                + File.separatorChar;
        String graphsFilePath = pathForExplicitResultData + "o.txt";
        File graphsInputFile = new File(graphsFilePath);
        if (!graphsInputFile.exists()) {
            throw new FileNotFoundException();
        } else {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(graphsInputFile.getAbsolutePath()))) {
                String line = "";
                line = bufferedReader.readLine(); // titles - dismiss
                ArrayList<Double> t0Array = new ArrayList<>();
                ArrayList<Double> x1Array = new ArrayList<>();
                ArrayList<Double> v2Array = new ArrayList<>();
                ArrayList<Double> p3Array = new ArrayList<>();
                line = bufferedReader.readLine(); // first data line
                while(line != null && !line.trim().equals("")) {
                    ArrayList<Double> row = new ArrayList<>();
                    String[] words = line.split(" ");
                    for(String word: words) {
                        if(!word.isEmpty()) {
                            row.add(Double.parseDouble(word));
                        }
                    }
                    t0Array.add(row.get(0));
                    x1Array.add(row.get(1));
                    v2Array.add(row.get(2));
                    p3Array.add(row.get(3));
                    line = bufferedReader.readLine();
                }
                system.getSystemData().setT0Array(t0Array);
                system.getSystemData().setP3Array(p3Array);
                system.getSystemData().setV2Array(v2Array);
                system.getSystemData().setX1Array(x1Array);
            }
        }
    }
    
    public static void writeDataForExplicit(Class context, HydroSystem system) throws IOException {
        String mainFilePath = getPathForDataForExplicitProgramm() + "main.txt";
        File mainOutputFile = new File(mainFilePath);
        boolean fileExists = mainOutputFile.exists();
        if (!fileExists) {
//            fileExists = mainOutputFile.createNewFile();
            throw new FileNotFoundException();
        } 
        if (fileExists) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(mainOutputFile.getAbsolutePath()))) {
                String outputLine = "";
                outputLine = outputLine + "sgl\t1";
                bufferedWriter.write(outputLine);
                bufferedWriter.newLine();
                outputLine = ">>calculate regim >>system class\n" +
                            "\n" +
                            "calculate regim\n" +
                            "sgl (GPrc) - calculate one limit or few simple circle\n" +
                            "lln - define max&min pressure line\n" +
                            "nrm - regulated max&min pressure line point\n" +
                            "(SPrc) - calculate series out parameter\n" +
                            "itr - iteration calculate procedure\n" +
                            "opt (OPrc) - optimization procedure\n" +
                            "\n" +
                            "system class\n" +
                            "1 -double action - two directed chambers\n" +
                            "2 -double action - back directed chambers\n" +
                            "3 -double action - rigth directed chambers\n" +
                            "4 -back action\n" +
                            "5 -rigth action";
                bufferedWriter.write(outputLine);
                bufferedWriter.newLine();

                bufferedWriter.close();
            }
        }
        String pathForInputData = getPathForDataForExplicitProgramm() 
                + "1" 
                + File.separatorChar 
                + "1" 
                + File.separatorChar 
                + "in" 
                + File.separatorChar;
        String inputFilePath = pathForInputData + "iDt.txt";
        File inputOutputFile = new File(inputFilePath);
        boolean fileExistsForInput = inputOutputFile.exists();
        if (!fileExistsForInput) {
//            fileExists = inputOutputFile.createNewFile();
            throw new FileNotFoundException();
        } 
        if (fileExists) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(inputOutputFile.getAbsolutePath()))) {
                String outputLine = "q0.11\t1\t" + system.getSystemData().getValueByIdentifer("q0", true);
                bufferedWriter.write(outputLine);
                bufferedWriter.newLine();
                outputLine = "pNmn.11\t1\t" + system.getSystemData().getValueByIdentifer("pn", true);
                bufferedWriter.write(outputLine);
                bufferedWriter.newLine();
                outputLine = "eta.11\t1\t" + system.getSystemData().getValueByIdentifer("eta0", true);
                bufferedWriter.write(outputLine);
                bufferedWriter.newLine();
                outputLine = "m.21\t1\t" + system.getSystemData().getValueByIdentifer("m", true);
                bufferedWriter.write(outputLine);
                bufferedWriter.newLine();
                outputLine = "x1.21\t1\t-" + system.getSystemData().getValueByIdentifer("x1", true);//"x1.21   	1	-0.031\n" +
                bufferedWriter.write(outputLine);
                bufferedWriter.newLine();
                outputLine = "Sa.21\t1\t" + system.getSystemData().getValueByIdentifer("sa", true);
                bufferedWriter.write(outputLine);
                bufferedWriter.newLine();
                outputLine = "Sb.21\t1\t" + system.getSystemData().getValueByIdentifer("sb", true);
                bufferedWriter.write(outputLine);
                bufferedWriter.newLine();
                outputLine = "p3.22\t1\t" + system.getSystemData().getValueByIdentifer("p3", true);
                bufferedWriter.write(outputLine);
                bufferedWriter.newLine();
                outputLine = "p0.22\t1\t" + system.getSystemData().getValueByIdentifer("p0", true);
                bufferedWriter.write(outputLine);
                bufferedWriter.newLine();
                outputLine = "V0.22\t1\t" + system.getSystemData().getValueByIdentifer("v0", true);
                bufferedWriter.write(outputLine);

                bufferedWriter.close();
            }
        }
    }
    
    public static boolean isExcelTable(File file) {
        return (file != null && 
                (file.getName().contains(".xlsx") || file.getName().contains(".csv")));
    }
    
    public static void readTableDataFromFile(File file) {
        if (file != null) {
            try {
                //Create Workbook instance holding reference to .xlsx file
                try (FileInputStream fileInput = new FileInputStream(file)) {
                    //Create Workbook instance holding reference to .xlsx file
                    XSSFWorkbook workbook = new XSSFWorkbook(fileInput);
                    
                    //Get first/desired sheet from the workbook
                    XSSFSheet sheet = workbook.getSheetAt(0);
                    
                    //Iterate through each rows one by one
                    Iterator<Row> rowIterator = sheet.iterator();
                    while (rowIterator.hasNext()) 
                    {
                        Row row = rowIterator.next();
                        //For each row, iterate through all the columns
                        Iterator<Cell> cellIterator = row.cellIterator(); 
                        
                        while (cellIterator.hasNext())
                        {
                            Cell cell = cellIterator.next();
                            //Check the cell type and format accordingly
                            switch (cell.getCellType())
                            {
                                case Cell.CELL_TYPE_NUMERIC:
                                    System.out.print(cell.getNumericCellValue() + "\t");
                                    break;
                                case Cell.CELL_TYPE_STRING:
                                    System.out.print(cell.getStringCellValue() + "\t");
                                    break;
                            }
                        }
                        System.out.println("");
                    }
                }
            } catch (IOException e) {
//                e.printStackTrace();
            }
        }
    }
    
    public static void writeTableDataToExcelFile(File file, Map<String, Object[]> data) throws Exception {
        if (file != null) {
            boolean fileExists = file.exists();
            if (!fileExists) {
                fileExists = file.createNewFile();
            }
            if (fileExists) {
                //Blank workbook
                XSSFWorkbook workbook = new XSSFWorkbook();

                //Create a blank sheet
                XSSFSheet sheet = workbook.createSheet("Striker variants");

                //Iterate over data and write to sheet
                Set<String> keyset = data.keySet();

                int rownum = 0;
                for (String key : keyset) 
                {
                    //create a row of excelsheet
                    Row row = sheet.createRow(rownum++);

                    //get object array of prerticuler key
                    Object[] objArr = data.get(key);

                    int cellnum = 0;

                    for (Object obj : objArr) 
                    {
                        Cell cell = row.createCell(cellnum++);
                        if (obj instanceof String) 
                        {
                            cell.setCellValue((String) obj);
                        }
                        else if (obj instanceof Integer) 
                        {
                            cell.setCellValue((Integer) obj);
                        }
                    }
                }
                try ( //Write the workbook in file system
                        FileOutputStream out = new FileOutputStream(file)) {
                    workbook.write(out);
                }
                System.out.println("xlsx written successfully on disk.");
            }
        }
    }
}
