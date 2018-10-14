package ui;

import java.awt.Dialog.ModalityType;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import model.BidirectionalSystem;
import model.DirectSystem;
import model.HydroSystem;
import static model.HydroSystem.Type.*;
import model.ReverseSystem;
import presenters.ModelPresenter;
import utils.IOHelper;
import utils.ImagesUtil;
import utils.TableUtil;
import utils.TextUtil;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author etb-t
 */
public class MainFrame extends javax.swing.JFrame {
    
    private static final int NON_SELECTED = -1;
    
    private static final String NUM_FORMAT = "%1$.5f";
    private final static Integer IMAGE_WIDTH = 525;
    private final static Integer IMAGE_HEIGHT = 150;
    
    private static final String NO_SYSTEM_DATA_TEXT = "Параметры и характеристики системы ещё не заданы";
    private static final String NO_TABLES_DATA_TEXT = "Табличные данные ещё не заданы";
    private static final String SYSTEM_DATA_SETTED_TEXT = "Параметры и характеристики системы заданы";
    private static final String TABLES_DATA_SETTED_TEXT = "Табличные данные заданы";
    
    private boolean isReadingInProcess;
    private boolean isSearchInProcess;
    
    private int selectedFilterVariable = NON_SELECTED;

    /**
     * Creates new form MainFrame.
     */
    public MainFrame() {
        //auto generated initialization
        initComponents();
        try {
            //set up logo for frame and inner dialogs
            initMainFrameAndComponents();
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            setToolbarIcons();
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        initModelTable();
        
        //set initial values for programm
        requestModelType(DOUBLE, true);
        initClearWithType();
    }
    
    /**
     * Sets up logo and modality type for frame and inner dialogs.
     * 
     */
    private void initMainFrameAndComponents() throws IOException {
        Image logo = ImageIO.read(getClass().getResourceAsStream("/logo.jpg"));
        setIconImage(logo);
        changeSystemTypeDialog.setIconImage(logo);
        progressDialog.setIconImage(logo);
        dataDialog.setIconImage(logo);
        readingProgressDialog.setIconImage(logo);
        filterDialog.setIconImage(logo);
        
        changeSystemTypeDialog.setModalityType(ModalityType.APPLICATION_MODAL);
        progressDialog.setModalityType(ModalityType.APPLICATION_MODAL);
        dataDialog.setModalityType(ModalityType.APPLICATION_MODAL);
        readingProgressDialog.setModalityType(ModalityType.APPLICATION_MODAL);
        filterDialog.setAlwaysOnTop(true);
    }
    
    /**
     * Initializes new DataFrame even if one open.
     * 
     * Handles DataFrame close event (redirect call on #setViewsIfDataReady()).
     */
    private void initDataFrame() {
        if(dataFrame != null) {
            dataFrame = null;
        }
        dataFrame = new DataFrame();
        dataFrame.setVisible(false);
        dataFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                setViewsIfDataReady();
            }
        });
    }
    
    /**
     * Initializes #modelTable selection listener.
     */
    private void initModelTable() {
        modelTable.getSelectionModel().addListSelectionListener((ListSelectionEvent event) -> {
            try {
                int selectedRow = modelTable.getSelectedRow();
                if (selectedRow < modelTable.getRowCount()) {
                    int row = modelTable.convertRowIndexToModel(selectedRow);
                    ModelPresenter.getInstance().setSelectionForCalculations(row);
                    ModelPresenter.getInstance().setDataBySelection();
                    enableExplicitCalcButtons(true);
                    enableStrikerCalcButtons(true);
                }
            } catch (IndexOutOfBoundsException ex) {
                modelTable.clearSelection();
            }
        });
    }
    
    private void initStrikerResultFrame() {
        if(strikerResultFrame != null) {
            strikerResultFrame = null;
        }
        strikerResultFrame = new StrikerResultFrame();
        strikerResultFrame.setVisible(false);
    }
    
    private void initExplicitResultFrame() {
        if(explicitResultFrame != null) {
            explicitResultFrame = null;
        }
        explicitResultFrame = new ExplicitResultFrame();
        explicitResultFrame.setVisible(false);
    }
    
    private void setToolbarIcons() throws IOException {
        BufferedImage myPicture = null;
        myPicture = ImageIO.read(getClass().getResourceAsStream("/upload_icon.png"));
        if (myPicture != null) {
            int size = buttonLoadLastModel.getHeight();
            buttonLoadLastModel.setIcon(new ImageIcon(
                    ImagesUtil.getResizedImage(myPicture, size, size, BufferedImage.TYPE_INT_ARGB)));
        }
        myPicture = ImageIO.read(getClass().getResourceAsStream("/save_icon.png"));
        if (myPicture != null) {
            int size = buttonSave.getHeight();
            buttonSave.setIcon(new ImageIcon(
                    ImagesUtil.getResizedImage(myPicture, size, size, BufferedImage.TYPE_INT_ARGB)));
        }
        myPicture = ImageIO.read(getClass().getResourceAsStream("/tables_icon.png"));
        if (myPicture != null) {
            int size = buttonLoadTables.getHeight();
            buttonLoadTables.setIcon(new ImageIcon(
                    ImagesUtil.getResizedImage(myPicture, size, size, BufferedImage.TYPE_INT_ARGB)));
        }
        myPicture = ImageIO.read(getClass().getResourceAsStream("/result_icon.png"));
        if (myPicture != null) {
            int size = buttonSaveTable.getHeight();
            buttonSaveTable.setIcon(new ImageIcon(
                    ImagesUtil.getResizedImage(myPicture, size, size, BufferedImage.TYPE_INT_ARGB)));
        }
        myPicture = ImageIO.read(getClass().getResourceAsStream("/calc_with_tables_icon.png"));
        if (myPicture != null) {
            int size = buttonCalcWithTable.getHeight();
            buttonCalcWithTable.setIcon(new ImageIcon(
                    ImagesUtil.getResizedImage(myPicture, size, size, BufferedImage.TYPE_INT_ARGB)));
        }
        myPicture = ImageIO.read(getClass().getResourceAsStream("/calculations_icon_3.png"));
        if (myPicture != null) {
            int size = progCalcButton.getHeight();
            progCalcButton.setIcon(new ImageIcon(
                    ImagesUtil.getResizedImage(myPicture, size, size, BufferedImage.TYPE_INT_ARGB)));
        }
        myPicture = ImageIO.read(getClass().getResourceAsStream("/clear_icon.png"));
        if (myPicture != null) {
            int size = buttonCreate.getHeight();
            buttonCreate.setIcon(new ImageIcon(
                    ImagesUtil.getResizedImage(myPicture, size, size, BufferedImage.TYPE_INT_ARGB)));
        }
        myPicture = ImageIO.read(getClass().getResourceAsStream("/setup_data_icon_1.png"));
        if (myPicture != null) {
            int size = buttonSetData.getHeight();
            buttonSetData.setIcon(new ImageIcon(
                    ImagesUtil.getResizedImage(myPicture, size, size, BufferedImage.TYPE_INT_ARGB)));
        }
        myPicture = ImageIO.read(getClass().getResourceAsStream("/calculations_icon_4.png"));
        if (myPicture != null) {
            int size = buttonCalcStriker.getHeight();
            buttonCalcStriker.setIcon(new ImageIcon(
                    ImagesUtil.getResizedImage(myPicture, size, size, BufferedImage.TYPE_INT_ARGB)));
        }
    }
    
    private void initToolbarButtons() {
        enableSaveButtons(false);
        enableCalcButtons(false);
        enableStrikerCalcButtons(false);
        enableExplicitCalcButtons(false);
        buttonSaveTable.setEnabled(false);
        buttonSave.setEnabled(false);
        menuItemSave.setEnabled(false);
        saveTableResultMenuItem.setEnabled(false);
    }
    
    private void enableSaveButtons(boolean isEnabled) {
        buttonSaveTable.setEnabled(isEnabled);
        buttonSave.setEnabled(isEnabled);
        menuItemSave.setEnabled(isEnabled);
        saveTableResultMenuItem.setEnabled(isEnabled);
    }
    
    private void enableCalcButtons(boolean isEnabled) {
        buttonCalcWithTable.setEnabled(isEnabled);
        menuItemStartWithData.setEnabled(isEnabled);
    }
    
    private void enableStrikerCalcButtons(boolean isEnabled) {
        buttonCalcStriker.setEnabled(isEnabled);
        menuItemCalcStriker.setEnabled(isEnabled);
    }
    
    private void enableExplicitCalcButtons(boolean isEnabled) {
        progCalcButton.setEnabled(isEnabled);
        menuItemStartExplicit.setEnabled(isEnabled);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooserTable = new javax.swing.JFileChooser();
        changeSystemTypeDialog = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        changeSystemTypeAccept = new javax.swing.JButton();
        changeSystemTypeCancel = new javax.swing.JButton();
        progressDialog = new javax.swing.JDialog();
        searchProgressBar = new javax.swing.JProgressBar();
        jLabel4 = new javax.swing.JLabel();
        closeSearchButton = new javax.swing.JButton();
        dataDialog = new javax.swing.JDialog();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        readingProgressDialog = new javax.swing.JDialog();
        jLabel7 = new javax.swing.JLabel();
        readingProgressBar = new javax.swing.JProgressBar();
        closeReadingButton = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        filterDialog = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        filterVariableComboBox = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        filterMinTextField = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        filterMaxTextField = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        filterMinBoundTextField = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        filterMaxBoundTextField = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        filterCancelButton = new javax.swing.JButton();
        filterOKButton = new javax.swing.JButton();
        contentScrollPane = new javax.swing.JScrollPane();
        contentPanel = new javax.swing.JPanel();
        tablePanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        modelTable = new javax.swing.JTable();
        toolbarPanel = new javax.swing.JPanel();
        mainToolbar = new javax.swing.JToolBar();
        buttonSetData = new javax.swing.JButton();
        systemTypeBox = new javax.swing.JComboBox<>();
        buttonLoadLastModel = new javax.swing.JButton();
        buttonLoadTables = new javax.swing.JButton();
        buttonCalcWithTable = new javax.swing.JButton();
        buttonCalcStriker = new javax.swing.JButton();
        progCalcButton = new javax.swing.JButton();
        buttonCreate = new javax.swing.JButton();
        buttonSaveTable = new javax.swing.JButton();
        buttonSave = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        systemDataStatusView = new javax.swing.JLabel();
        tablesDataStatusView = new javax.swing.JLabel();
        systemImageView = new javax.swing.JLabel();
        tableFiltersButton = new javax.swing.JButton();
        clearFiltersButton = new javax.swing.JButton();
        noVariantsFoundView = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        menuFile = new javax.swing.JMenu();
        menuItemCreate = new javax.swing.JMenuItem();
        menuItemSave = new javax.swing.JMenuItem();
        saveTableResultMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuItemStartWithData = new javax.swing.JMenuItem();
        menuItemStartExplicit = new javax.swing.JMenuItem();
        menuItemCalcStriker = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuItemExit = new javax.swing.JMenuItem();
        menuData = new javax.swing.JMenu();
        menuSysType = new javax.swing.JMenu();
        menuItemSysTypeDirect = new javax.swing.JRadioButtonMenuItem();
        menuItemSysTypeReverse = new javax.swing.JRadioButtonMenuItem();
        menuItemSysTypeDouble = new javax.swing.JRadioButtonMenuItem();
        loadDataMenuItem = new javax.swing.JMenuItem();
        menuItemLoadData = new javax.swing.JMenuItem();
        menuItemSetData = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();

        changeSystemTypeDialog.setSize(new java.awt.Dimension(550, 200));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 26)); // NOI18N
        jLabel1.setText("Внимание!");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel2.setText("Выбор другого типа системы может стереть текущие настройки.");
        jLabel2.setVerticalTextPosition(javax.swing.SwingConstants.TOP);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel3.setText("Вы уверены, что хотите продолжить?");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        changeSystemTypeAccept.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        changeSystemTypeAccept.setText("Да");
        changeSystemTypeAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeSystemTypeAcceptActionPerformed(evt);
            }
        });

        changeSystemTypeCancel.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        changeSystemTypeCancel.setText("Отмена");
        changeSystemTypeCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeSystemTypeCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout changeSystemTypeDialogLayout = new javax.swing.GroupLayout(changeSystemTypeDialog.getContentPane());
        changeSystemTypeDialog.getContentPane().setLayout(changeSystemTypeDialogLayout);
        changeSystemTypeDialogLayout.setHorizontalGroup(
            changeSystemTypeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(changeSystemTypeDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(changeSystemTypeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
                    .addGroup(changeSystemTypeDialogLayout.createSequentialGroup()
                        .addGroup(changeSystemTypeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addGroup(changeSystemTypeDialogLayout.createSequentialGroup()
                                .addComponent(changeSystemTypeAccept, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(changeSystemTypeCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        changeSystemTypeDialogLayout.setVerticalGroup(
            changeSystemTypeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(changeSystemTypeDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(changeSystemTypeDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(changeSystemTypeAccept, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(changeSystemTypeCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        progressDialog.setTitle("Статус поиска");
        progressDialog.setSize(new java.awt.Dimension(600, 200));

        jLabel4.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel4.setText("Выполняется выбор основных параметров для системы");

        closeSearchButton.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        closeSearchButton.setText("Закрыть");
        closeSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeSearchButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout progressDialogLayout = new javax.swing.GroupLayout(progressDialog.getContentPane());
        progressDialog.getContentPane().setLayout(progressDialogLayout);
        progressDialogLayout.setHorizontalGroup(
            progressDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(progressDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(progressDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(searchProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, progressDialogLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(closeSearchButton)))
                .addContainerGap())
        );
        progressDialogLayout.setVerticalGroup(
            progressDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(progressDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(searchProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(closeSearchButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        dataDialog.setResizable(false);
        dataDialog.setSize(new java.awt.Dimension(700, 190));

        jLabel5.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel5.setText("Необходимо задать данные для расчета.");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel6.setText("Проверьте, что заданы все параметры системы и таблицы, необходимые для расчета.");

        jButton3.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jButton3.setText("Закрыть");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout dataDialogLayout = new javax.swing.GroupLayout(dataDialog.getContentPane());
        dataDialog.getContentPane().setLayout(dataDialogLayout);
        dataDialogLayout.setHorizontalGroup(
            dataDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dataDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dataDialogLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addContainerGap())
        );
        dataDialogLayout.setVerticalGroup(
            dataDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dataDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        readingProgressDialog.setResizable(false);
        readingProgressDialog.setSize(new java.awt.Dimension(590, 210));

        jLabel7.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel7.setText("Это может занять несколько минут.");

        readingProgressBar.setIndeterminate(false);

        closeReadingButton.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        closeReadingButton.setText("Закрыть");
        closeReadingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeReadingButtonActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel8.setText("Выполняется чтение файлов.");

        javax.swing.GroupLayout readingProgressDialogLayout = new javax.swing.GroupLayout(readingProgressDialog.getContentPane());
        readingProgressDialog.getContentPane().setLayout(readingProgressDialogLayout);
        readingProgressDialogLayout.setHorizontalGroup(
            readingProgressDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(readingProgressDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(readingProgressDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 569, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, readingProgressDialogLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(closeReadingButton))
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(readingProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        readingProgressDialogLayout.setVerticalGroup(
            readingProgressDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(readingProgressDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(readingProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(closeReadingButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        filterDialog.setTitle("Фильтры");
        filterDialog.setResizable(false);
        filterDialog.setSize(new java.awt.Dimension(450, 300));

        jLabel9.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel9.setText("Применить фильтры к переменной");
        jLabel9.setPreferredSize(new java.awt.Dimension(272, 30));

        filterVariableComboBox.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        filterVariableComboBox.setModel(ModelPresenter.getInstance().getFilterComboBoxModel());
        filterVariableComboBox.setPreferredSize(new java.awt.Dimension(110, 30));
        filterVariableComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterVariableComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(filterVariableComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(filterVariableComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel10.setText("Min значение по столбцу");
        jLabel10.setPreferredSize(new java.awt.Dimension(272, 30));

        filterMinTextField.setEditable(false);
        filterMinTextField.setPreferredSize(new java.awt.Dimension(110, 30));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(filterMinTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(filterMinTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel11.setText("Max значение по столбцу");
        jLabel11.setPreferredSize(new java.awt.Dimension(272, 30));

        filterMaxTextField.setEditable(false);
        filterMaxTextField.setPreferredSize(new java.awt.Dimension(110, 30));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(filterMaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(filterMaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel12.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel12.setText("Ограничить снизу значением");
        jLabel12.setPreferredSize(new java.awt.Dimension(272, 30));

        filterMinBoundTextField.setPreferredSize(new java.awt.Dimension(110, 30));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(filterMinBoundTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(filterMinBoundTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel13.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel13.setText("Ограничить сверху значением");
        jLabel13.setPreferredSize(new java.awt.Dimension(272, 30));

        filterMaxBoundTextField.setPreferredSize(new java.awt.Dimension(110, 30));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(filterMaxBoundTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(filterMaxBoundTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        filterCancelButton.setBackground(new java.awt.Color(65, 151, 237));
        filterCancelButton.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        filterCancelButton.setText("Закрыть");
        filterCancelButton.setPreferredSize(new java.awt.Dimension(110, 30));
        filterCancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterCancelButtonActionPerformed(evt);
            }
        });

        filterOKButton.setBackground(new java.awt.Color(65, 151, 237));
        filterOKButton.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        filterOKButton.setText("Применить");
        filterOKButton.setPreferredSize(new java.awt.Dimension(120, 30));
        filterOKButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterOKButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(filterOKButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterCancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(filterCancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(filterOKButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout filterDialogLayout = new javax.swing.GroupLayout(filterDialog.getContentPane());
        filterDialog.getContentPane().setLayout(filterDialogLayout);
        filterDialogLayout.setHorizontalGroup(
            filterDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filterDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(filterDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        filterDialogLayout.setVerticalGroup(
            filterDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filterDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("HPS (HydroHammer Parameter Select)");

        contentScrollPane.setBorder(null);
        contentScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        contentScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        contentPanel.setBackground(new java.awt.Color(250, 250, 250));

        tablePanel.setMaximumSize(new java.awt.Dimension(32767, 500));
        tablePanel.setOpaque(false);
        tablePanel.setPreferredSize(new java.awt.Dimension(1177, 500));

        jScrollPane2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane2.setMaximumSize(new java.awt.Dimension(32767, 500));
        jScrollPane2.setMinimumSize(null);

        modelTable.setAutoCreateRowSorter(true);
        modelTable.setBackground(new java.awt.Color(240, 240, 240));
        modelTable.setModel(ModelPresenter.getInstance().getTableModel());
        modelTable.setRowSelectionAllowed(true);
        modelTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(modelTable);

        javax.swing.GroupLayout tablePanelLayout = new javax.swing.GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1177, Short.MAX_VALUE)
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );

        toolbarPanel.setBackground(new java.awt.Color(193, 254, 255));
        toolbarPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        toolbarPanel.setPreferredSize(new java.awt.Dimension(700, 35));

        mainToolbar.setBackground(new java.awt.Color(215, 254, 255));
        mainToolbar.setFloatable(false);

        buttonSetData.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonSetData.setText(" ");
        buttonSetData.setToolTipText("Задать параметры");
        buttonSetData.setFocusable(false);
        buttonSetData.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        buttonSetData.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        buttonSetData.setIconTextGap(0);
        buttonSetData.setOpaque(false);
        buttonSetData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSetDataActionPerformed(evt);
            }
        });
        mainToolbar.add(buttonSetData);

        systemTypeBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Прямого действия", "Обратного действия", "Двухстороннего действия" }));
        systemTypeBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                systemTypeBoxActionPerformed(evt);
            }
        });
        mainToolbar.add(systemTypeBox);

        buttonLoadLastModel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonLoadLastModel.setText(" ");
        buttonLoadLastModel.setToolTipText("Загрузить параметры");
        buttonLoadLastModel.setFocusable(false);
        buttonLoadLastModel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        buttonLoadLastModel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        buttonLoadLastModel.setIconTextGap(0);
        buttonLoadLastModel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLoadLastModelActionPerformed(evt);
            }
        });
        mainToolbar.add(buttonLoadLastModel);

        buttonLoadTables.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonLoadTables.setText(" ");
        buttonLoadTables.setToolTipText("Загрузить таблицы");
        buttonLoadTables.setFocusable(false);
        buttonLoadTables.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        buttonLoadTables.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        buttonLoadTables.setIconTextGap(0);
        buttonLoadTables.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonLoadTablesActionPerformed(evt);
            }
        });
        mainToolbar.add(buttonLoadTables);

        buttonCalcWithTable.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonCalcWithTable.setText(" ");
        buttonCalcWithTable.setToolTipText("Расчёт выборки");
        buttonCalcWithTable.setFocusable(false);
        buttonCalcWithTable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        buttonCalcWithTable.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        buttonCalcWithTable.setIconTextGap(0);
        buttonCalcWithTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCalcWithTableActionPerformed(evt);
            }
        });
        mainToolbar.add(buttonCalcWithTable);

        buttonCalcStriker.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonCalcStriker.setText(" ");
        buttonCalcStriker.setToolTipText("Расчёт размеров бойка");
        buttonCalcStriker.setFocusable(false);
        buttonCalcStriker.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        buttonCalcStriker.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        buttonCalcStriker.setIconTextGap(0);
        buttonCalcStriker.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        buttonCalcStriker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCalcStrikerActionPerformed(evt);
            }
        });
        mainToolbar.add(buttonCalcStriker);

        progCalcButton.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        progCalcButton.setText(" ");
        progCalcButton.setToolTipText("Проверочный расчёт");
        progCalcButton.setFocusable(false);
        progCalcButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        progCalcButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        progCalcButton.setIconTextGap(0);
        progCalcButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                progCalcButtonActionPerformed(evt);
            }
        });
        mainToolbar.add(progCalcButton);

        buttonCreate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonCreate.setText(" ");
        buttonCreate.setToolTipText("Очистить расчёт");
        buttonCreate.setFocusable(false);
        buttonCreate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        buttonCreate.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        buttonCreate.setIconTextGap(0);
        buttonCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCreateActionPerformed(evt);
            }
        });
        mainToolbar.add(buttonCreate);

        buttonSaveTable.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonSaveTable.setText(" ");
        buttonSaveTable.setToolTipText("Сохранить таблицу результатов");
        buttonSaveTable.setFocusable(false);
        buttonSaveTable.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        buttonSaveTable.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        buttonSaveTable.setIconTextGap(0);
        buttonSaveTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveTableActionPerformed(evt);
            }
        });
        mainToolbar.add(buttonSaveTable);

        buttonSave.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buttonSave.setText(" ");
        buttonSave.setToolTipText("Сохранить параметры бойка");
        buttonSave.setFocusable(false);
        buttonSave.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        buttonSave.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        buttonSave.setIconTextGap(0);
        buttonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonSaveActionPerformed(evt);
            }
        });
        mainToolbar.add(buttonSave);

        javax.swing.GroupLayout toolbarPanelLayout = new javax.swing.GroupLayout(toolbarPanel);
        toolbarPanel.setLayout(toolbarPanelLayout);
        toolbarPanelLayout.setHorizontalGroup(
            toolbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolbarPanelLayout.createSequentialGroup()
                .addComponent(mainToolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        toolbarPanelLayout.setVerticalGroup(
            toolbarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(toolbarPanelLayout.createSequentialGroup()
                .addComponent(mainToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setOpaque(false);

        systemDataStatusView.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        systemDataStatusView.setText("Параметры и характеристики системы ещё не заданы.");

        tablesDataStatusView.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tablesDataStatusView.setText("Табличные данные ещё не заданы.");

        systemImageView.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(65, 151, 237)));

        tableFiltersButton.setBackground(new java.awt.Color(65, 151, 237));
        tableFiltersButton.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        tableFiltersButton.setText("Фильтры");
        tableFiltersButton.setPreferredSize(new java.awt.Dimension(150, 30));
        tableFiltersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tableFiltersButtonActionPerformed(evt);
            }
        });

        clearFiltersButton.setBackground(new java.awt.Color(65, 151, 237));
        clearFiltersButton.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        clearFiltersButton.setText("Очистить фильтры");
        clearFiltersButton.setPreferredSize(new java.awt.Dimension(190, 30));
        clearFiltersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearFiltersButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(systemDataStatusView, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tablesDataStatusView)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(tableFiltersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(clearFiltersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(systemImageView, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(846, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(systemDataStatusView)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tablesDataStatusView)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(systemImageView, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tableFiltersButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearFiltersButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        systemImageView.getAccessibleContext().setAccessibleName("systemImage");

        noVariantsFoundView.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        noVariantsFoundView.setForeground(new java.awt.Color(255, 0, 51));
        noVariantsFoundView.setText("Результатов не найдено! Очистите расчёт и задайте другие параметры и ограничения.");

        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(toolbarPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1391, Short.MAX_VALUE)
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(noVariantsFoundView))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, contentPanelLayout.createSequentialGroup()
                .addComponent(toolbarPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(noVariantsFoundView)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tablePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(368, Short.MAX_VALUE))
        );

        contentScrollPane.setViewportView(contentPanel);

        menuBar.setBackground(new java.awt.Color(159, 253, 255));
        menuBar.setAutoscrolls(true);
        menuBar.setOpaque(false);

        menuFile.setBackground(new java.awt.Color(159, 253, 255));
        menuFile.setLabel("Главная");

        menuItemCreate.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        menuItemCreate.setText("Создать");
        menuItemCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemCreateActionPerformed(evt);
            }
        });
        menuFile.add(menuItemCreate);

        menuItemSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuItemSave.setText("Сохранить параметры бойка");
        menuItemSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSaveActionPerformed(evt);
            }
        });
        menuFile.add(menuItemSave);

        saveTableResultMenuItem.setText("Сохранить таблицу результатов");
        saveTableResultMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveTableResultMenuItemActionPerformed(evt);
            }
        });
        menuFile.add(saveTableResultMenuItem);
        menuFile.add(jSeparator1);

        menuItemStartWithData.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, java.awt.event.InputEvent.CTRL_MASK));
        menuItemStartWithData.setText("Расчёт выборки");
        menuItemStartWithData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemStartWithDataActionPerformed(evt);
            }
        });
        menuFile.add(menuItemStartWithData);

        menuItemStartExplicit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, java.awt.event.InputEvent.SHIFT_MASK));
        menuItemStartExplicit.setText("Проверочный расчёт");
        menuItemStartExplicit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemStartExplicitActionPerformed(evt);
            }
        });
        menuFile.add(menuItemStartExplicit);

        menuItemCalcStriker.setText("Расчёт размеров бойка");
        menuItemCalcStriker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemCalcStrikerActionPerformed(evt);
            }
        });
        menuFile.add(menuItemCalcStriker);
        menuFile.add(jSeparator2);

        menuItemExit.setText("Выход");
        menuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemExitActionPerformed(evt);
            }
        });
        menuFile.add(menuItemExit);

        menuBar.add(menuFile);

        menuData.setBackground(new java.awt.Color(159, 253, 255));
        menuData.setLabel("Данные");

        menuSysType.setText("Тип системы");

        menuItemSysTypeDirect.setSelected(true);
        menuItemSysTypeDirect.setText("Прямого действия");
        menuItemSysTypeDirect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSysTypeDirectActionPerformed(evt);
            }
        });
        menuSysType.add(menuItemSysTypeDirect);

        menuItemSysTypeReverse.setSelected(true);
        menuItemSysTypeReverse.setText("Обратного действия");
        menuItemSysTypeReverse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSysTypeReverseActionPerformed(evt);
            }
        });
        menuSysType.add(menuItemSysTypeReverse);

        menuItemSysTypeDouble.setSelected(true);
        menuItemSysTypeDouble.setText("Двухстороннего действия");
        menuItemSysTypeDouble.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSysTypeDoubleActionPerformed(evt);
            }
        });
        menuSysType.add(menuItemSysTypeDouble);

        menuData.add(menuSysType);

        loadDataMenuItem.setText("Загрузить параметры");
        loadDataMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadDataMenuItemActionPerformed(evt);
            }
        });
        menuData.add(loadDataMenuItem);

        menuItemLoadData.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        menuItemLoadData.setText("Загрузить таблицы");
        menuItemLoadData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemLoadDataActionPerformed(evt);
            }
        });
        menuData.add(menuItemLoadData);

        menuItemSetData.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        menuItemSetData.setText("Задать данные");
        menuItemSetData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemSetDataActionPerformed(evt);
            }
        });
        menuData.add(menuItemSetData);

        menuBar.add(menuData);

        menuHelp.setBackground(new java.awt.Color(159, 253, 255));
        menuHelp.setLabel("Справка");
        menuBar.add(menuHelp);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 950, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1179, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuItemCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemCreateActionPerformed
        requestModelType(DOUBLE, true);
    }//GEN-LAST:event_menuItemCreateActionPerformed

    private void menuItemLoadDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemLoadDataActionPerformed
        openFileChooserForRead(); 
    }//GEN-LAST:event_menuItemLoadDataActionPerformed
    
    private void setSystemImage() {
        try {
            BufferedImage myPicture = null;
            switch(ModelPresenter.getInstance().getSystem().type) {
                case DIRECT:  {
                    myPicture = ImageIO.read(getClass().getResourceAsStream("/direct.bmp"));
                } break;
                case REVERSE: {
                    myPicture = ImageIO.read(getClass().getResourceAsStream("/reverse.bmp"));
                } break;
                case DOUBLE: {
                    myPicture = ImageIO.read(getClass().getResourceAsStream("/double.jpg"));
                } break;
            }
            if (myPicture != null) {
//                systemImageView.createImage(ImagesUtil.getResizedImage(myPicture, IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_ARGB).getSource());
                systemImageView.setIcon(new ImageIcon(
                        ImagesUtil.getResizedImage(myPicture, IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB)));
            }
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Function handles Model requested type.
     * 
     * If current Model type is not equal to requested, 
     * #ModelPresenter saves requested type and #changeSystemTypeDialog is launched.
     * Otherwise, if all views clear is needed, requested type ignored, 
     * Model type sets on default and all views cleared.
     * 
     * @param type requested Model type
     * @param needClear if true, all views will be cleared
     */
    private void requestModelType(model.HydroSystem.Type type, boolean needClear) {
        if(ModelPresenter.getInstance().getSystem().type != type) {
            ModelPresenter.getInstance().setRequestedType(type);
            changeSystemTypeDialog.setVisible(true);
        } else if(needClear) {
            ModelPresenter.getInstance().clearAll();
            ModelPresenter.getInstance().setSystem(new BidirectionalSystem());
            initClearWithType();
        }
    }
    
    private void systemTypeBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_systemTypeBoxActionPerformed
        int selected = systemTypeBox.getSelectedIndex();
        switch (selected) {
            case 0 : {
                requestModelType(DIRECT, false);
            } break;
            case 1 : {
                requestModelType(REVERSE, false);
            } break;
            case 2 : {
                requestModelType(DOUBLE, false);
            } break;
        }
    }//GEN-LAST:event_systemTypeBoxActionPerformed

    private void buttonLoadTablesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLoadTablesActionPerformed
        openFileChooserForRead();
    }//GEN-LAST:event_buttonLoadTablesActionPerformed

    private void menuItemSetDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSetDataActionPerformed
        openDataFrame();
    }//GEN-LAST:event_menuItemSetDataActionPerformed

    private void buttonSetDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSetDataActionPerformed
        openDataFrame();
    }//GEN-LAST:event_buttonSetDataActionPerformed

    private void selectSystemTypeOnBox() {
        switch (ModelPresenter.getInstance().getSystem().type) {
            case DIRECT : {
                systemTypeBox.setSelectedIndex(0);
            } break;
            case REVERSE : {
                systemTypeBox.setSelectedIndex(1);
            } break;
            case DOUBLE : {
                systemTypeBox.setSelectedIndex(2);
            } break;
        }
    }
    
    private void selectSystemTypeOnMenu() {
        switch (ModelPresenter.getInstance().getSystem().type) {
            case DIRECT : {
                menuItemSysTypeDirect.setSelected(true);
                menuItemSysTypeReverse.setSelected(false);
                menuItemSysTypeDouble.setSelected(false);
                ModelPresenter.getInstance().setRequestedType(DIRECT);
            } break;
            case REVERSE : {
                menuItemSysTypeDirect.setSelected(false);
                menuItemSysTypeReverse.setSelected(true);
                menuItemSysTypeDouble.setSelected(false);
                ModelPresenter.getInstance().setRequestedType(REVERSE);
            } break;
            case DOUBLE : {
                menuItemSysTypeDirect.setSelected(false);
                menuItemSysTypeReverse.setSelected(false);
                menuItemSysTypeDouble.setSelected(true);
                ModelPresenter.getInstance().setRequestedType(DOUBLE);
            } break;
        }
    }
    
    private void changeSystemTypeAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeSystemTypeAcceptActionPerformed
        if(ModelPresenter.getInstance().getSystem().type != ModelPresenter.getInstance().getRequestedType()) {
            switch (ModelPresenter.getInstance().getRequestedType()) {
                case DIRECT : {
                    ModelPresenter.getInstance().setSystem(new DirectSystem());
                } break;
                case REVERSE : {
                    ModelPresenter.getInstance().setSystem(new ReverseSystem());
                } break;
                case DOUBLE : {
                    ModelPresenter.getInstance().setSystem(new BidirectionalSystem());
                } break;
            }
        }
        
        initClearWithType();
                
        changeSystemTypeDialog.setVisible(false);
    }//GEN-LAST:event_changeSystemTypeAcceptActionPerformed
    
    /**
     * Set visibility and text status on views related on Model and Tables.
     * 
     * If Model is set enough, changes #systemDataStatusView status and 
     * enables #buttonCalcWithTable if tables set enough).
     * If Tables is set enough, changes #tablesDataStatusView status. 
     */
    private void setViewsIfDataReady() {
        boolean isTablesReady = ModelPresenter.getInstance().getTables() != null 
                && ModelPresenter.getInstance().getTables().size() > 0;
        if (isTablesReady) {
            tablesDataStatusView.setText(TABLES_DATA_SETTED_TEXT);
        } else {
            tablesDataStatusView.setText(NO_TABLES_DATA_TEXT);
        }
        if(ModelPresenter.getInstance().getSystem().isSettedEnough() && 
                ModelPresenter.getInstance().getSystem().isValid()) {
            systemDataStatusView.setText(SYSTEM_DATA_SETTED_TEXT);
            if(isTablesReady) {
                enableCalcButtons(true);
            }
        } else {
            systemDataStatusView.setText(NO_SYSTEM_DATA_TEXT);
        }
    }
    
    private void initClearWithType() {
        selectSystemTypeOnBox();
        selectSystemTypeOnMenu();
        
        ModelPresenter.getInstance().clearModel();
        
        if (dataFrame != null && dataFrame.isVisible()) {
            dataFrame.dispose();
            initDataFrame();
            openDataFrame();
        } else {
            initDataFrame();
        }
        if (strikerResultFrame != null && strikerResultFrame.isVisible()) {
            strikerResultFrame.dispose();
        }
        initStrikerResultFrame();
        
        if (explicitResultFrame != null && explicitResultFrame.isVisible()) {
            explicitResultFrame.dispose();
        }
        initExplicitResultFrame();
        
        tablePanel.setVisible(false);
        tableFiltersButton.setVisible(false);
        clearFiltersButton.setVisible(false);
        noVariantsFoundView.setVisible(false);
        
        setSystemImage();
        initToolbarButtons();
       
        setViewsIfDataReady();
    }
    
    private void changeSystemTypeCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeSystemTypeCancelActionPerformed
        selectSystemTypeOnBox();
        selectSystemTypeOnMenu();
        changeSystemTypeDialog.setVisible(false);
    }//GEN-LAST:event_changeSystemTypeCancelActionPerformed

    private void menuItemSysTypeDirectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSysTypeDirectActionPerformed
        requestModelType(DIRECT, false);
    }//GEN-LAST:event_menuItemSysTypeDirectActionPerformed

    private void menuItemSysTypeDoubleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSysTypeDoubleActionPerformed
        requestModelType(DOUBLE, false);
    }//GEN-LAST:event_menuItemSysTypeDoubleActionPerformed

    private void menuItemSysTypeReverseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSysTypeReverseActionPerformed
        requestModelType(REVERSE, false);
    }//GEN-LAST:event_menuItemSysTypeReverseActionPerformed

    private void closeSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeSearchButtonActionPerformed
        onSearchFinish();
    }//GEN-LAST:event_closeSearchButtonActionPerformed

    private void onSearchFinish() {
        isSearchInProcess = false;
        if(ModelPresenter.getInstance().isSearchComplete()) {
            if(ModelPresenter.getInstance().isVariantsFound()) {
                modelTable.removeAll();
                ModelPresenter.getInstance().clearTableColumnsNaming();
                modelTable.setModel(ModelPresenter.getInstance().getTableModel());
                TableUtil.setModelTable(modelTable);
                tablePanel.setVisible(true);
                enableSaveButtons(true);
                tableFiltersButton.setVisible(true);
            } else {
                noVariantsFoundView.setVisible(true);
            }
        } else {
            tablePanel.setVisible(false);
            tableFiltersButton.setVisible(false);
            clearFiltersButton.setVisible(false);
            enableSaveButtons(false);
        }
    }
    
    private void calcWithTables() {
        if(ModelPresenter.getInstance().getTables() != null && 
                ModelPresenter.getInstance().getTables().size() > 0 && 
                ModelPresenter.getInstance().getSystem().isSettedEnough() && 
                ModelPresenter.getInstance().getSystem().isValid()) {
            ModelPresenter.getInstance().clearFoundVariants();
            progressDialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowActivated(WindowEvent e) {
                    if (!isSearchInProcess) {
                        isSearchInProcess = true;
                        new Thread(() -> {
                            ModelPresenter.getInstance().makeSearch(progressDialog, searchProgressBar);
                            progressDialog.dispose();
                        }).start();
                    }
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    onSearchFinish();
                }
            });
            progressDialog.setVisible(true);
        } else {
            dataDialog.setVisible(true);
        }
    }
    
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        dataDialog.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void closeReadingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeReadingButtonActionPerformed
        readingProgressDialog.dispose();
        tablesDataStatusView.setText(NO_TABLES_DATA_TEXT);
        ModelPresenter.getInstance().clearTables();
    }//GEN-LAST:event_closeReadingButtonActionPerformed

    private void menuItemStartWithDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemStartWithDataActionPerformed
        calcWithTables();
    }//GEN-LAST:event_menuItemStartWithDataActionPerformed

    private void buttonLoadLastModelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonLoadLastModelActionPerformed
        onLoadLastModel();
    }//GEN-LAST:event_buttonLoadLastModelActionPerformed

    private void onLoadLastModel() {
        HydroSystem lastSystem = IOHelper.readUserModel(getClass());
        if (lastSystem != null) {
            if (lastSystem.type != ModelPresenter.getInstance().getSystem().type) {
                ModelPresenter.getInstance().setRequestedType(lastSystem.type);
                ModelPresenter.getInstance().clearFoundVariants();
            }
            ModelPresenter.getInstance().setSystem(lastSystem);
        
            initClearWithType();
        }
    }
    
    private void tableFiltersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tableFiltersButtonActionPerformed
        if (selectedFilterVariable == NON_SELECTED) {
            selectedFilterVariable = filterVariableComboBox.getSelectedIndex();
        }
        filterVariableComboBox.setModel(ModelPresenter.getInstance().getFilterComboBoxModel());
        filterVariableComboBox.setSelectedIndex(selectedFilterVariable);
        filterDialog.setVisible(true);
    }//GEN-LAST:event_tableFiltersButtonActionPerformed
   
    private void filterOKButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterOKButtonActionPerformed
        String minBound = filterMinBoundTextField.getText();
        String maxBound = filterMaxBoundTextField.getText();
        
        String min = filterMinTextField.getText();
        String max = filterMaxTextField.getText();
        Double minDouble = TextUtil.isStringDoubleValue(min) ? Double.parseDouble(min) : null;
        Double maxDouble = TextUtil.isStringDoubleValue(max) ? Double.parseDouble(max) : null;
        
        double minBoundDouble = minDouble;
        double maxBoundDouble = maxDouble;
        
        if(TextUtil.isStringDoubleValue(minBound)) {
            minBoundDouble = Double.parseDouble(minBound);
            
            if (minDouble != null && minBoundDouble < minDouble) {
                minBoundDouble = minDouble;
                filterMinBoundTextField.setText(String.valueOf(minBoundDouble));
            }
        }
        
        if (TextUtil.isStringDoubleValue(maxBound)) {
            maxBoundDouble = Double.parseDouble(maxBound);
            
            if (maxDouble != null && maxBoundDouble > maxDouble) {
                maxBoundDouble = maxDouble;
                filterMaxBoundTextField.setText(String.valueOf(maxBoundDouble));
            }
        }
        
        ModelPresenter.getInstance().setFilteredFoundVariants(filterVariableComboBox.getSelectedIndex(), minBoundDouble, maxBoundDouble);
        
        modelTable.removeAll();
        ModelPresenter.getInstance().clearTableColumnsNaming();
        modelTable.setModel(ModelPresenter.getInstance().getTableModel());
        TableUtil.setModelTable(modelTable);
        
        clearFiltersButton.setVisible(true);
    }//GEN-LAST:event_filterOKButtonActionPerformed

    private void filterCancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterCancelButtonActionPerformed
        filterDialog.dispose();
    }//GEN-LAST:event_filterCancelButtonActionPerformed

    private void filterVariableComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterVariableComboBoxActionPerformed
        selectedFilterVariable = filterVariableComboBox.getSelectedIndex();
        handleSelectedFilterVariable();
    }//GEN-LAST:event_filterVariableComboBoxActionPerformed

    private void buttonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveActionPerformed
        openFileChooserForWrite(false);
    }//GEN-LAST:event_buttonSaveActionPerformed

    private void buttonSaveTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonSaveTableActionPerformed
        openFileChooserForWrite(true);
    }//GEN-LAST:event_buttonSaveTableActionPerformed

    private void buttonCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCreateActionPerformed
        requestModelType(DOUBLE, false);
        initClearWithType();
    }//GEN-LAST:event_buttonCreateActionPerformed

    private void progCalcButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_progCalcButtonActionPerformed
        makeExplicitCalc();
    }//GEN-LAST:event_progCalcButtonActionPerformed

    private void buttonCalcWithTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCalcWithTableActionPerformed
        calcWithTables();
    }//GEN-LAST:event_buttonCalcWithTableActionPerformed

    private void loadDataMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadDataMenuItemActionPerformed
        onLoadLastModel();
    }//GEN-LAST:event_loadDataMenuItemActionPerformed

    private void saveTableResultMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveTableResultMenuItemActionPerformed
        openFileChooserForWrite(true);
    }//GEN-LAST:event_saveTableResultMenuItemActionPerformed

    private void menuItemSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemSaveActionPerformed
        openFileChooserForWrite(false);
    }//GEN-LAST:event_menuItemSaveActionPerformed

    private void menuItemStartExplicitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemStartExplicitActionPerformed
        makeExplicitCalc();
    }//GEN-LAST:event_menuItemStartExplicitActionPerformed

    private void clearFiltersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearFiltersButtonActionPerformed
        filterDialog.dispose();
        ModelPresenter.getInstance().clearFilteredFoundVariants();
        modelTable.removeAll();
        ModelPresenter.getInstance().clearTableColumnsNaming();
        modelTable.setModel(ModelPresenter.getInstance().getTableModel());
        TableUtil.setModelTable(modelTable);
//        tableFiltersButtonActionPerformed(null);
    }//GEN-LAST:event_clearFiltersButtonActionPerformed

    private void buttonCalcStrikerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCalcStrikerActionPerformed
        if (strikerResultFrame.isVisible()) {
            strikerResultFrame.dispose();
        }
        initStrikerResultFrame();
        openStrikerResultFrame();
    }//GEN-LAST:event_buttonCalcStrikerActionPerformed

    private void menuItemCalcStrikerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemCalcStrikerActionPerformed
        if (strikerResultFrame.isVisible()) {
            strikerResultFrame.dispose();
        }
        initStrikerResultFrame();
        openStrikerResultFrame();
    }//GEN-LAST:event_menuItemCalcStrikerActionPerformed

    private void menuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_menuItemExitActionPerformed
    
    private void handleSelectedFilterVariable() {
        ModelPresenter.getInstance().setSelectedVariableMinInFoundVariants(selectedFilterVariable);
        List<Double> previous = ModelPresenter.getInstance().getFilterSelection(selectedFilterVariable);
        if(previous != null && previous.size() == 2) {
            filterMinBoundTextField.setText(String.format(Locale.ENGLISH, NUM_FORMAT, previous.get(0)));
            filterMaxBoundTextField.setText(String.format(Locale.ENGLISH, NUM_FORMAT, previous.get(1)));
        } else {
            filterMinBoundTextField.setText(null);//String.format(Locale.ENGLISH, NUM_FORMAT, 0.0));
            filterMaxBoundTextField.setText(null);//String.format(Locale.ENGLISH, NUM_FORMAT, 0.0));
        }
        filterMinTextField.setText(String.format(Locale.ENGLISH, NUM_FORMAT, ModelPresenter.getInstance().getSelectedVariableMinInFoundVariants()));
        filterMaxTextField.setText(String.format(Locale.ENGLISH, NUM_FORMAT, ModelPresenter.getInstance().getSelectedVariableMaxInFoundVariants()));
    }
    
    private void openDataFrame() {        
        if (!dataFrame.isVisible()) {
            dataFrame.setVisible(true);
        }
    }
    
    private void openStrikerResultFrame() {        
        if (!strikerResultFrame.isVisible()) {
            strikerResultFrame.setVisible(true);
        }
    }
    
    private void openExplicitResultFrame() {        
        if (!explicitResultFrame.isVisible()) {
            explicitResultFrame.setVisible(true);
        }
    }
    
    private void openFileChooserForWrite(boolean forTables) {
        try {
            if (forTables) {
                File lastDirectory = IOHelper.getLastOutputTablesDirectory(getClass());
                if (lastDirectory != null && lastDirectory.exists()) {
                    fileChooserTable.setCurrentDirectory(lastDirectory);
                }
                fileChooserTable.setFileFilter(new FileNameExtensionFilter("xls file","xls"));
            } else {
                File lastDirectory = IOHelper.getLastOutputStrikerDirectory(getClass());
                if (lastDirectory != null && lastDirectory.exists()) {
                    fileChooserTable.setCurrentDirectory(lastDirectory);
                }
                fileChooserTable.setFileFilter(new FileNameExtensionFilter("txt file","txt"));
            }
            fileChooserTable.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnVal = fileChooserTable.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooserTable.getSelectedFile();
                if (forTables) {
                    IOHelper.saveLastOutputTablesDirectory(getClass(), file.getParentFile().getAbsolutePath());
                } else {
                    IOHelper.saveLastOutputStrikerDirectory(getClass(), file.getParentFile().getAbsolutePath());
                }
                // return the file path
                if (forTables) {
                    IOHelper.writeTableDataToExcelFile(file, null);
                } else {
                    IOHelper.writeStrikerDataToFile(getClass(), file);
                }
            } 
            else {
                System.out.println("File access cancelled by user.");
            }
        } catch (Exception ex) {
            System.out.println("problem accessing file");
        }
    }
    
    private void clearViewsIfOpened() {
        initClearWithType();
    }

    private void openFileChooserForRead() {
        fileChooserTable.setMultiSelectionEnabled(true);
        fileChooserTable.setFileFilter(new FileNameExtensionFilter("txt file","txt"));
        File lastDirectory = null;
        try {
            lastDirectory = IOHelper.getLastInputDirectory(getClass());
        } catch (Exception ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (lastDirectory != null && lastDirectory.exists()) {
            fileChooserTable.setCurrentDirectory(lastDirectory);
        }
        int returnVal = fileChooserTable.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooserTable.getSelectedFiles(); 
            try {
                IOHelper.saveLastInputDirectory(getClass(), files[0].getParentFile().getAbsolutePath());
            } catch (Exception ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            readingProgressDialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowActivated(WindowEvent e) {
                    if (!isReadingInProcess) {
                        isReadingInProcess = true;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                readingProgressBar.setMinimum(0);
                                readingProgressBar.setMaximum(files.length);
                                readingProgressBar.setValue(0);
                                clearViewsIfOpened();
                                ModelPresenter.getInstance().clearTables();
                                ModelPresenter.getInstance().clearFoundVariants();
                                ModelPresenter.getInstance().clearModel();
                                for(int i = 0; i < files.length; ++i) {
                                    if (IOHelper.isExcelTable(files[i])) {
                                        IOHelper.readTableDataFromFile(files[i]);
                                    } else {
                                        IOHelper.readTableFromTxtFile(files[i]);
                                    }
                                    readingProgressBar.setValue(i+1);
                                }
                                if(ModelPresenter.getInstance().getTables().size() > 0) {
                                    tablesDataStatusView.setText(TABLES_DATA_SETTED_TEXT);
                                    if(ModelPresenter.getInstance().getSystem().isSettedEnough() && 
                                        ModelPresenter.getInstance().getSystem().isValid()) {
                                        enableCalcButtons(true);
                                    }
                                }

                                readingProgressDialog.dispose();
                            }
                        }).start();
                    }
                }

                @Override
                public void windowClosed(WindowEvent e) {
                    isReadingInProcess = false;
//                    ModelPresenter.getInstance().clearTables();
                }
            });
            readingProgressDialog.setVisible(true);
        } 
        else {
            System.out.println("File access cancelled by user.");
        }
    }
    
    private void makeExplicitCalc() {
        try {
            // prepare data, data is ready for sure
            IOHelper.writeDataForExplicit(getClass(), ModelPresenter.getInstance().getSystem());
            String programmName = IOHelper.getExplicitProgrammName();
            String programmPath = IOHelper.getExplicitProgrammPath();
            try {
                Process process = Runtime.getRuntime().exec(programmPath + programmName, null, new File(programmPath));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                while ((bufferedReader.readLine()) != null) {
                }
                process.waitFor();
                if (process.isAlive()) {
                    process.destroy();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                // show dialog with errors
            }
            // all was good while processing
            // try to handle result
            IOHelper.readExplicitResult(getClass(), ModelPresenter.getInstance().getSystem());
            if (explicitResultFrame.isVisible()) {
                explicitResultFrame.dispose();
            }
            initExplicitResultFrame();
            openExplicitResultFrame();
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCalcStriker;
    private javax.swing.JButton buttonCalcWithTable;
    private javax.swing.JButton buttonCreate;
    private javax.swing.JButton buttonLoadLastModel;
    private javax.swing.JButton buttonLoadTables;
    private javax.swing.JButton buttonSave;
    private javax.swing.JButton buttonSaveTable;
    private javax.swing.JButton buttonSetData;
    private javax.swing.JButton changeSystemTypeAccept;
    private javax.swing.JButton changeSystemTypeCancel;
    private javax.swing.JDialog changeSystemTypeDialog;
    private javax.swing.JButton clearFiltersButton;
    private javax.swing.JButton closeReadingButton;
    private javax.swing.JButton closeSearchButton;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JScrollPane contentScrollPane;
    private javax.swing.JDialog dataDialog;
    private javax.swing.JFileChooser fileChooserTable;
    private javax.swing.JButton filterCancelButton;
    private javax.swing.JDialog filterDialog;
    private javax.swing.JTextField filterMaxBoundTextField;
    private javax.swing.JTextField filterMaxTextField;
    private javax.swing.JTextField filterMinBoundTextField;
    private javax.swing.JTextField filterMinTextField;
    private javax.swing.JButton filterOKButton;
    private javax.swing.JComboBox<String> filterVariableComboBox;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JMenuItem loadDataMenuItem;
    private javax.swing.JToolBar mainToolbar;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuData;
    private javax.swing.JMenu menuFile;
    private javax.swing.JMenu menuHelp;
    private javax.swing.JMenuItem menuItemCalcStriker;
    private javax.swing.JMenuItem menuItemCreate;
    private javax.swing.JMenuItem menuItemExit;
    private javax.swing.JMenuItem menuItemLoadData;
    private javax.swing.JMenuItem menuItemSave;
    private javax.swing.JMenuItem menuItemSetData;
    private javax.swing.JMenuItem menuItemStartExplicit;
    private javax.swing.JMenuItem menuItemStartWithData;
    private javax.swing.JRadioButtonMenuItem menuItemSysTypeDirect;
    private javax.swing.JRadioButtonMenuItem menuItemSysTypeDouble;
    private javax.swing.JRadioButtonMenuItem menuItemSysTypeReverse;
    private javax.swing.JMenu menuSysType;
    private javax.swing.JTable modelTable;
    private javax.swing.JLabel noVariantsFoundView;
    private javax.swing.JButton progCalcButton;
    private javax.swing.JDialog progressDialog;
    private javax.swing.JProgressBar readingProgressBar;
    private javax.swing.JDialog readingProgressDialog;
    private javax.swing.JMenuItem saveTableResultMenuItem;
    private javax.swing.JProgressBar searchProgressBar;
    private javax.swing.JLabel systemDataStatusView;
    private javax.swing.JLabel systemImageView;
    private javax.swing.JComboBox<String> systemTypeBox;
    private javax.swing.JButton tableFiltersButton;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JLabel tablesDataStatusView;
    private javax.swing.JPanel toolbarPanel;
    // End of variables declaration//GEN-END:variables
    private DataFrame dataFrame;
    private StrikerResultFrame strikerResultFrame;
    private ExplicitResultFrame explicitResultFrame;
}
