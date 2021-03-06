/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.Image;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import presenters.ModelPresenter;
import view.GraphPanel;

/**
 *
 * @author etb-t
 */
public class ExplicitResultFrame extends javax.swing.JFrame {
   
    /**
     * Creates new form ExplicitResultFrame
     */
    public ExplicitResultFrame() {
        initComponents();
        try {
            initExplicitResultFrameAndComponents();
        } catch (IOException ex) {
            Logger.getLogger(StrikerResultFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
//        setViewState();
        
//        calculationsProgressDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        
        initInputFields();
        initGraphs();
    }
    
    private void initExplicitResultFrameAndComponents() throws IOException {
        Image logo = ImageIO.read(getClass().getResourceAsStream("/logo.jpg"));
        setIconImage(logo);
//        calculationsProgressDialog.setIconImage(logo);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        inputDataPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        inputMTextField = new javax.swing.JLabel();
        inputSaTextField = new javax.swing.JLabel();
        inputSbTextField = new javax.swing.JLabel();
        inputX1TextField = new javax.swing.JLabel();
        resLabelInput = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        inputP0TextField = new javax.swing.JLabel();
        inputPnTextField = new javax.swing.JLabel();
        inputEta0TextField = new javax.swing.JLabel();
        x1Panel = new javax.swing.JPanel();
        x1GraphPanel = new GraphPanel();
        resLabelInput2 = new javax.swing.JLabel();
        v2Panel = new javax.swing.JPanel();
        v2GraphPanel = new GraphPanel();
        resLabelInput3 = new javax.swing.JLabel();
        p3Panel = new javax.swing.JPanel();
        p3GraphPanel = new GraphPanel();
        resLabelInput4 = new javax.swing.JLabel();
        inputDataPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        outputFTextField = new javax.swing.JLabel();
        outputViTextField = new javax.swing.JLabel();
        outputXmaxTextField = new javax.swing.JLabel();
        resLabelInput1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        outputPminTextField = new javax.swing.JLabel();
        outputPmaxTextField = new javax.swing.JLabel();
        outputNTextField = new javax.swing.JLabel();
        outputEtaTextField = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Результат проверочного расчета");
        setBackground(new java.awt.Color(215, 254, 255));
        setPreferredSize(new java.awt.Dimension(1050, 600));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setOpaque(false);

        jPanel1.setBackground(new java.awt.Color(215, 254, 255));

        inputDataPanel.setOpaque(false);

        jPanel2.setOpaque(false);

        inputMTextField.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        inputMTextField.setText("m = 5 кг");

        inputSaTextField.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        inputSaTextField.setText("Sa = 5 см2");

        inputSbTextField.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        inputSbTextField.setText("Sb = 5 см2");

        inputX1TextField.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        inputX1TextField.setText("x1 = 5 см");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(inputMTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputSaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputSbTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputX1TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(inputMTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(inputSaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(inputSbTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(inputX1TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        resLabelInput.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        resLabelInput.setText("Исходные параметры:");

        jPanel3.setOpaque(false);

        inputP0TextField.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        inputP0TextField.setText("p0 = 5 кг");

        inputPnTextField.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        inputPnTextField.setText("pn = 5 см2");

        inputEta0TextField.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        inputEta0TextField.setText("eta0 = 5 см2");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(inputP0TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputPnTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputEta0TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(inputP0TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(inputPnTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(inputEta0TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout inputDataPanelLayout = new javax.swing.GroupLayout(inputDataPanel);
        inputDataPanel.setLayout(inputDataPanelLayout);
        inputDataPanelLayout.setHorizontalGroup(
            inputDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(resLabelInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        inputDataPanelLayout.setVerticalGroup(
            inputDataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputDataPanelLayout.createSequentialGroup()
                .addComponent(resLabelInput, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        x1Panel.setBackground(new java.awt.Color(245, 245, 245));
        x1Panel.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(65, 151, 237)));
        x1Panel.setPreferredSize(new java.awt.Dimension(300, 332));

        x1GraphPanel.setBackground(new java.awt.Color(245, 245, 245));
        x1GraphPanel.setPreferredSize(new java.awt.Dimension(245, 180));

        javax.swing.GroupLayout x1GraphPanelLayout = new javax.swing.GroupLayout(x1GraphPanel);
        x1GraphPanel.setLayout(x1GraphPanelLayout);
        x1GraphPanelLayout.setHorizontalGroup(
            x1GraphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 245, Short.MAX_VALUE)
        );
        x1GraphPanelLayout.setVerticalGroup(
            x1GraphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );

        resLabelInput2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        resLabelInput2.setForeground(new java.awt.Color(65, 100, 255));
        resLabelInput2.setText(" Координата бойка - время:");

        javax.swing.GroupLayout x1PanelLayout = new javax.swing.GroupLayout(x1Panel);
        x1Panel.setLayout(x1PanelLayout);
        x1PanelLayout.setHorizontalGroup(
            x1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(resLabelInput2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(x1GraphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        x1PanelLayout.setVerticalGroup(
            x1PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(x1PanelLayout.createSequentialGroup()
                .addComponent(resLabelInput2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(x1GraphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        v2Panel.setBackground(new java.awt.Color(245, 245, 245));
        v2Panel.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(65, 151, 237)));
        v2Panel.setPreferredSize(new java.awt.Dimension(300, 332));

        v2GraphPanel.setBackground(new java.awt.Color(245, 245, 245));
        v2GraphPanel.setPreferredSize(new java.awt.Dimension(245, 180));

        javax.swing.GroupLayout v2GraphPanelLayout = new javax.swing.GroupLayout(v2GraphPanel);
        v2GraphPanel.setLayout(v2GraphPanelLayout);
        v2GraphPanelLayout.setHorizontalGroup(
            v2GraphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        v2GraphPanelLayout.setVerticalGroup(
            v2GraphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );

        resLabelInput3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        resLabelInput3.setForeground(new java.awt.Color(65, 100, 255));
        resLabelInput3.setText(" Скорость бойка - время:");

        javax.swing.GroupLayout v2PanelLayout = new javax.swing.GroupLayout(v2Panel);
        v2Panel.setLayout(v2PanelLayout);
        v2PanelLayout.setHorizontalGroup(
            v2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(v2GraphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(resLabelInput3, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
        );
        v2PanelLayout.setVerticalGroup(
            v2PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(v2PanelLayout.createSequentialGroup()
                .addComponent(resLabelInput3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(v2GraphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        p3Panel.setBackground(new java.awt.Color(245, 245, 245));
        p3Panel.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(65, 151, 237)));
        p3Panel.setPreferredSize(new java.awt.Dimension(300, 332));

        p3GraphPanel.setBackground(new java.awt.Color(245, 245, 245));
        p3GraphPanel.setPreferredSize(new java.awt.Dimension(245, 180));

        javax.swing.GroupLayout p3GraphPanelLayout = new javax.swing.GroupLayout(p3GraphPanel);
        p3GraphPanel.setLayout(p3GraphPanelLayout);
        p3GraphPanelLayout.setHorizontalGroup(
            p3GraphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 245, Short.MAX_VALUE)
        );
        p3GraphPanelLayout.setVerticalGroup(
            p3GraphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );

        resLabelInput4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        resLabelInput4.setForeground(new java.awt.Color(65, 100, 255));
        resLabelInput4.setText(" Давление в аккумуляторе - время:");

        javax.swing.GroupLayout p3PanelLayout = new javax.swing.GroupLayout(p3Panel);
        p3Panel.setLayout(p3PanelLayout);
        p3PanelLayout.setHorizontalGroup(
            p3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(resLabelInput4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(p3GraphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        p3PanelLayout.setVerticalGroup(
            p3PanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p3PanelLayout.createSequentialGroup()
                .addComponent(resLabelInput4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p3GraphPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        inputDataPanel1.setOpaque(false);

        jPanel4.setOpaque(false);

        outputFTextField.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        outputFTextField.setText("f = 5 кг");

        outputViTextField.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        outputViTextField.setText("VI = 5 см2");

        outputXmaxTextField.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        outputXmaxTextField.setText("Xmax = 5 см2");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(outputFTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(outputViTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(outputXmaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(outputFTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(outputViTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(outputXmaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        resLabelInput1.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        resLabelInput1.setText("Основные характеристики:");

        jPanel5.setOpaque(false);

        outputPminTextField.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        outputPminTextField.setText("Pmin = 5 кг");

        outputPmaxTextField.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        outputPmaxTextField.setText("Pmax = 5 см2");

        outputNTextField.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        outputNTextField.setText("N = 5 см2");

        outputEtaTextField.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        outputEtaTextField.setText("eta = 5 см");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(outputPminTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(outputPmaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(outputNTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(outputEtaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(393, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(outputPminTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(outputPmaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(outputNTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(outputEtaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout inputDataPanel1Layout = new javax.swing.GroupLayout(inputDataPanel1);
        inputDataPanel1.setLayout(inputDataPanel1Layout);
        inputDataPanel1Layout.setHorizontalGroup(
            inputDataPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(resLabelInput1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        inputDataPanel1Layout.setVerticalGroup(
            inputDataPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inputDataPanel1Layout.createSequentialGroup()
                .addComponent(resLabelInput1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(inputDataPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(inputDataPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(x1Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(v2Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(p3Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 15, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(inputDataPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inputDataPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(v2Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                    .addComponent(x1Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                    .addComponent(p3Panel, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void initInputFields() {
        String mFormat = "m = %1$.2f кг";
        String saFormat = "Sa = %1$.2f см" + (char) Integer.parseInt("00b2", 16);
        String sbFormat = "Sb = %1$.2f см" + (char) Integer.parseInt("00b2", 16);
        String x1Format = "x1 = %1$.1f мм";
        
        String p0Format = "p0 = %1$.1f МПа";
        String pnFormat = "pn = %1$.1f МПа";
        String eta0Format = "eta0 = %1$.1f";
        
        String fFormat = "f = %1$.1f Гц";
        String viFormat = "Vi = %1$.1f м/с";
        String xmaxFormat = "Xmax = %1$.1f мм";
        
        String pminFormat = "Pmin = %1$.1f МПа";
        String pmaxFormat = "Pmax = %1$.1f МПа";
        String nFormat = "N = %1$.1f";
        String etaFormat = "eta = %1$.1f";
        
        double m = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIdentifer("m", false);
        double Sa = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIdentifer("Sa", false);
        double Sb = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIdentifer("Sb", false);
        double x1 = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIdentifer("x1", false);
        
        double p0 = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIdentifer("p0", false);
        double pn = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIdentifer("pn", false);
        double eta0 = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIdentifer("eta0", false);
        
        double f = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIdentifer("f", false);
        double vi = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIdentifer("vi", false);
        double Xmax = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIdentifer("Xmax", false);
        
        double Pmin = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIdentifer("pmin", false);
        double Pmax = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIdentifer("pmax", false);
        double N = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIdentifer("n", false);
        double eta = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIdentifer("eta", false);
        
        String mText = String.format(Locale.ENGLISH, mFormat, m);
        String saText = String.format(Locale.ENGLISH, saFormat, Sa);
        String sbText = String.format(Locale.ENGLISH, sbFormat, Sb);
        String x1Text = String.format(Locale.ENGLISH, x1Format, x1);
        
        String p0Text = String.format(Locale.ENGLISH, p0Format, p0);
        String pnText = String.format(Locale.ENGLISH, pnFormat, pn);
        String eta0Text = String.format(Locale.ENGLISH, eta0Format, eta0);
        
        String fText = String.format(Locale.ENGLISH, fFormat, f);
        String viText = String.format(Locale.ENGLISH, viFormat, vi);
        String xmaxText = String.format(Locale.ENGLISH, xmaxFormat, Xmax);
        
        String pminText = String.format(Locale.ENGLISH, pminFormat, Pmin);
        String pmaxText = String.format(Locale.ENGLISH, pmaxFormat, Pmax);
        String nText = String.format(Locale.ENGLISH, nFormat, N);
        String etaText = String.format(Locale.ENGLISH, etaFormat, eta);
        
        inputMTextField.setText(mText);
        inputSaTextField.setText(saText);
        inputSbTextField.setText(sbText);
        inputX1TextField.setText(x1Text);
        
        inputP0TextField.setText(p0Text);
        inputPnTextField.setText(pnText);
        inputEta0TextField.setText(eta0Text);
        
        outputFTextField.setText(fText);
        outputViTextField.setText(viText);
        outputXmaxTextField.setText(xmaxText);
        
        outputPminTextField.setText(pminText);
        outputPmaxTextField.setText(pmaxText);
        outputNTextField.setText(nText);
        outputEtaTextField.setText(etaText);
    }
    
    private void initGraphs() {
        ((GraphPanel) x1GraphPanel).setXValues(ModelPresenter.getInstance().getSystem().getSystemData().getT0Array());
        ((GraphPanel) x1GraphPanel).setYValues(ModelPresenter.getInstance().getSystem().getSystemData().getX1Array());
        
        ((GraphPanel) v2GraphPanel).setXValues(ModelPresenter.getInstance().getSystem().getSystemData().getT0Array());
        ((GraphPanel) v2GraphPanel).setYValues(ModelPresenter.getInstance().getSystem().getSystemData().getV2Array());
        
        ((GraphPanel) p3GraphPanel).setXValues(ModelPresenter.getInstance().getSystem().getSystemData().getT0Array());
        ((GraphPanel) p3GraphPanel).setYValues(ModelPresenter.getInstance().getSystem().getSystemData().getP3Array());
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ExplicitResultFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ExplicitResultFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel inputDataPanel;
    private javax.swing.JPanel inputDataPanel1;
    private javax.swing.JLabel inputEta0TextField;
    private javax.swing.JLabel inputMTextField;
    private javax.swing.JLabel inputP0TextField;
    private javax.swing.JLabel inputPnTextField;
    private javax.swing.JLabel inputSaTextField;
    private javax.swing.JLabel inputSbTextField;
    private javax.swing.JLabel inputX1TextField;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel outputEtaTextField;
    private javax.swing.JLabel outputFTextField;
    private javax.swing.JLabel outputNTextField;
    private javax.swing.JLabel outputPmaxTextField;
    private javax.swing.JLabel outputPminTextField;
    private javax.swing.JLabel outputViTextField;
    private javax.swing.JLabel outputXmaxTextField;
    private javax.swing.JPanel p3GraphPanel;
    private javax.swing.JPanel p3Panel;
    private javax.swing.JLabel resLabelInput;
    private javax.swing.JLabel resLabelInput1;
    private javax.swing.JLabel resLabelInput2;
    private javax.swing.JLabel resLabelInput3;
    private javax.swing.JLabel resLabelInput4;
    private javax.swing.JPanel v2GraphPanel;
    private javax.swing.JPanel v2Panel;
    private javax.swing.JPanel x1GraphPanel;
    private javax.swing.JPanel x1Panel;
    // End of variables declaration//GEN-END:variables
}
