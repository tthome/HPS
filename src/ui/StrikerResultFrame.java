/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import static model.BidirectionalSystem.Type.BOTH_CHAMBERS;
import static model.BidirectionalSystem.Type.DIRECT_CHAMBER;
import static model.BidirectionalSystem.Type.REVERSE_CHAMBER;
import model.HydroSystem;
import static model.HydroSystem.Type.DIRECT;
import static model.HydroSystem.Type.DOUBLE;
import static model.HydroSystem.Type.REVERSE;
import presenters.ModelPresenter;
import utils.Constants;
import utils.ImagesUtil;
import utils.TableUtil;
import utils.TextUtil;

/**
 *
 * @author etb-t
 */
public class StrikerResultFrame extends javax.swing.JFrame {
    
    private final static Integer IMAGE_WIDTH = 690;
    private final static Integer IMAGE_HEIGHT = 210;
    
    private boolean state;

    /**
     * Creates new form StrikerResultFrame
     */
    public StrikerResultFrame() {
        initComponents();
        try {
            initStrikerResultFrameAndComponents();
        } catch (IOException ex) {
            Logger.getLogger(StrikerResultFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        setViewState();
        
        calculationsProgressDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        
        initInputFields();
    }
    
    private void paintComponentInStrikerShapePanel(Graphics g) {
        int widthOfPanel = IMAGE_WIDTH;//strikerShapePanel.getWidth();
        int heightOfPanel = IMAGE_HEIGHT;//strikerShapePanel.getHeight();
            
        double widthMargin = 0.027 * widthOfPanel;
        double heightMargin = 0.027 * heightOfPanel;
        
        double Dmid = heightOfPanel / 2.;
        double Dmin = Dmid+Dmid*5/6.;
        double Dline = Dmin-heightMargin;
        
        double Lmax = widthOfPanel - 2. * widthMargin;
        double Dmax = heightOfPanel - 2* (heightOfPanel - Dline + widthMargin);
       
        if(widthOfPanel > 0 && heightOfPanel > 0 && ModelPresenter.getInstance().getSystem().getSystemData().isValueSetted(22)) {
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

            double scaleCoef = 0;
            
            if (L > D) {
                scaleCoef = Lmax / L;
                if (D * scaleCoef > Dmax) {
                    scaleCoef *= Dmax / (D * scaleCoef);
                }
            } else {
                scaleCoef = Dmax / D;
                if (L * scaleCoef > Lmax) {
                    scaleCoef *= Lmax / (L * scaleCoef);
                }
            }
            
            l1 *= scaleCoef;
            l2 *= scaleCoef;
            l3 *= scaleCoef;
            l4 *= scaleCoef;
            l5 *= scaleCoef;
            da *= scaleCoef;
            db *= scaleCoef;
            dc *= scaleCoef;
            L *= scaleCoef;
            D *= scaleCoef;
           
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.setColor(Color.blue);
            graphics2D.setStroke(new BasicStroke(3));
            
            Double xAncor = Double.valueOf(widthMargin);
            Double yAncor = Double.valueOf(Dmid - db/2.);
            Double width = Double.valueOf(l1);
            Double height = Double.valueOf(db);
            Rectangle2D rectangle = new Rectangle2D.Double(xAncor, yAncor, width, height);
            graphics2D.draw(rectangle);
//            graphics2D.drawRect(xAncor.intValue(), yAncor.intValue(), width.intValue(), height.intValue());
            xAncor = Double.valueOf(widthMargin+l1);
            yAncor = Double.valueOf(Dmid - D/2.);
            width = Double.valueOf(l2);
            height = Double.valueOf(D);
            rectangle = new Rectangle2D.Double(xAncor, yAncor, width, height);
            graphics2D.draw(rectangle);
//            graphics2D.drawRect(xAncor.intValue(), yAncor.intValue(), width.intValue(), height.intValue());
            xAncor = Double.valueOf(widthMargin+l1+l2);
            yAncor = Double.valueOf(Dmid - dc/2.);
            width = Double.valueOf(l3);
            height = Double.valueOf(dc);
            rectangle = new Rectangle2D.Double(xAncor, yAncor, width, height);
            graphics2D.draw(rectangle);
//            graphics2D.drawRect(xAncor.intValue(), yAncor.intValue(), width.intValue(), height.intValue());
            xAncor = Double.valueOf(widthMargin+l1+l2+l3);
            yAncor = Double.valueOf(Dmid - D/2.);
            width = Double.valueOf(l4);
            height = Double.valueOf(D);
            rectangle = new Rectangle2D.Double(xAncor, yAncor, width, height);
            graphics2D.draw(rectangle);
//            graphics2D.drawRect(xAncor.intValue(), yAncor.intValue(), width.intValue(), height.intValue());
            xAncor = Double.valueOf(widthMargin+l1+l2+l3+l4);
            yAncor = Double.valueOf(Dmid - da/2.);
            width = Double.valueOf(l5);
            height = Double.valueOf(da);
            rectangle = new Rectangle2D.Double(xAncor, yAncor, width, height);
            graphics2D.draw(rectangle);
//            graphics2D.drawRect(xAncor.intValue(), yAncor.intValue(), width.intValue(), height.intValue());
            
            /*graphics2D.setStroke(new BasicStroke(2));
            graphics2D.setColor(Color.BLACK);
            Double x1 = Double.valueOf(widthMargin);
            Double x2 = Double.valueOf(widthMargin);
            Double y1 = Double.valueOf(Dmid+db/2.);
            Double y2 = Double.valueOf(Dmin);
            graphics2D.drawLine(x1.intValue(), y1.intValue(), x2.intValue(),y2.intValue());
            x1 = Double.valueOf(widthMargin+l1);
            x2 = Double.valueOf(widthMargin+l1);
            y1 = Double.valueOf(Dmid+D/2.);
            y2 = Double.valueOf(Dmin);
            graphics2D.drawLine(x1.intValue(), y1.intValue(), x2.intValue(),y2.intValue());
            x1 = Double.valueOf(widthMargin+l1+l2);
            x2 = Double.valueOf(widthMargin+l1+l2);
            y1 = Double.valueOf(Dmid+D/2.);
            y2 = Double.valueOf(Dmin);
            graphics2D.drawLine(x1.intValue(), y1.intValue(), x2.intValue(),y2.intValue());
            x1 = Double.valueOf(widthMargin+l1+l2+l3);
            x2 = Double.valueOf(widthMargin+l1+l2+l3);
            y1 = Double.valueOf(Dmid+D/2.);
            y2 = Double.valueOf(Dmin);
            graphics2D.drawLine(x1.intValue(), y1.intValue(), x2.intValue(),y2.intValue());
            x1 = Double.valueOf(widthMargin+l1+l2+l3+l4);
            x2 = Double.valueOf(widthMargin+l1+l2+l3+l4);
            y1 = Double.valueOf(Dmid+D/2.);
            y2 = Double.valueOf(Dmin);
            graphics2D.drawLine(x1.intValue(), y1.intValue(), x2.intValue(),y2.intValue());
            x1 = Double.valueOf(widthMargin+L);
            x2 = Double.valueOf(widthMargin+L);
            y1 = Double.valueOf(Dmid+da/2.);
            y2 = Double.valueOf(Dmin);
            graphics2D.drawLine(x1.intValue(), y1.intValue(), x2.intValue(),y2.intValue());
            
            double arrowH = heightMargin/2.;
            
            drawLineWithArrows(graphics2D, widthMargin, widthMargin+l1, Dline, Dline, arrowH);
            drawLineWithArrows(graphics2D, widthMargin+l1, widthMargin+l1+l2, Dline, Dline, arrowH);
            drawLineWithArrows(graphics2D, widthMargin+l1+l2, widthMargin+l1+l2+l3, Dline, Dline, arrowH);
            drawLineWithArrows(graphics2D, widthMargin+l1+l2+l3, widthMargin+l1+l2+l3+l4, Dline, Dline, arrowH);
            drawLineWithArrows(graphics2D, widthMargin+l1+l2+l3+l4, widthMargin+l1+l2+l3+l4+l5, Dline, Dline, arrowH);
            
            Double xStr = Double.valueOf(widthMargin+l1/2.-heightMargin/2.);
            Double yStr = Double.valueOf(Dline+2*heightMargin);
            graphics2D.drawString("l1", xStr.intValue(), yStr.intValue());
            xStr = Double.valueOf(widthMargin+l1+l2/2.-heightMargin/2.);
            yStr = Double.valueOf(Dline+2*heightMargin);
            graphics2D.drawString("l2", xStr.intValue(), yStr.intValue());
            xStr = Double.valueOf(widthMargin+l1+l2+l3/2.-heightMargin/2.);
            yStr = Double.valueOf(Dline+2*heightMargin);
            graphics2D.drawString("l3", xStr.intValue(), yStr.intValue());
            xStr = Double.valueOf(widthMargin+l1+l2+l3+l4/2.-heightMargin/2.);
            yStr = Double.valueOf(Dline+2*heightMargin);
            graphics2D.drawString("l4", xStr.intValue(), yStr.intValue());
            xStr = Double.valueOf(widthMargin+l1+l2+l3+l4+l5/2.-heightMargin/2.);
            yStr = Double.valueOf(Dline+2*heightMargin);
            graphics2D.drawString("l5", xStr.intValue(), yStr.intValue());
            
            drawLineWithArrows(graphics2D, widthMargin+l1/2., widthMargin+l1/2., Dmid-db/2., Dmid+db/2., arrowH);
            drawLineWithArrows(graphics2D, widthMargin+l1+l2/2., widthMargin+l1+l2/2., Dmid-D/2., Dmid+D/2., arrowH);
            drawLineWithArrows(graphics2D, widthMargin+l1+l2+l3/2., widthMargin+l1+l2+l3/2., Dmid-dc/2., Dmid+dc/2., arrowH);
            drawLineWithArrows(graphics2D, widthMargin+l1+l2+l3+l4/2., widthMargin+l1+l2+l3+l4/2., Dmid-D/2., Dmid+D/2., arrowH);
            drawLineWithArrows(graphics2D, widthMargin+l1+l2+l3+l4+l5/2., widthMargin+l1+l2+l3+l4+l5/2., Dmid-da/2., Dmid+da/2., arrowH);
            */
            graphics2D.setStroke(new BasicStroke(20));
            graphics2D.setColor(Color.BLACK);
            Double xStr = Double.valueOf(widthMargin*2./3.+l1/2.+heightMargin/4.);
            Double yStr = Double.valueOf(Dmid+heightMargin);
            graphics2D.drawString("1", xStr.intValue(), yStr.intValue());
            xStr = Double.valueOf(widthMargin*2./3.+l1+l2/2.+heightMargin/4.);
            yStr = Double.valueOf(Dmid+heightMargin);
            graphics2D.drawString("2", xStr.intValue(), yStr.intValue());
            xStr = Double.valueOf(widthMargin*2./3.+l1+l2+l3/2.+heightMargin/4.);
            yStr = Double.valueOf(Dmid+heightMargin);
            graphics2D.drawString("3", xStr.intValue(), yStr.intValue());
            xStr = Double.valueOf(widthMargin*2./3.+l1+l2+l3+l4/2.+heightMargin/4.);
            yStr = Double.valueOf(Dmid+heightMargin);
            graphics2D.drawString("4", xStr.intValue(), yStr.intValue());
            xStr = Double.valueOf(widthMargin*2./3.+l1+l2+l3+l4+l5/2.+heightMargin/4.);
            yStr = Double.valueOf(Dmid+heightMargin);
            graphics2D.drawString("5", xStr.intValue(), yStr.intValue());
        }
    }
    
    private void drawLineWithArrows(Graphics2D graphics2D, double x1, double x2, double y1, double y2, double arrowH) {
        double arrowCoef = 0.3;
        Double x1D = Double.valueOf(x1);
        Double x2D = Double.valueOf(x2);
        Double y1D = Double.valueOf(y1);
        Double y2D = Double.valueOf(y2);
        graphics2D.drawLine(x1D.intValue(), y1D.intValue(), x2D.intValue(), y2D.intValue());
        if (!x1D.equals(x2D)) {
            x1D = Double.valueOf(x1);
            x2D = Double.valueOf(x1+arrowH);
            y1D = Double.valueOf(y1);
            y2D = Double.valueOf(y1-arrowH*arrowCoef);
            graphics2D.drawLine(x1D.intValue(), y1D.intValue(), x2D.intValue(), y2D.intValue());
            x1D = Double.valueOf(x1);
            x2D = Double.valueOf(x1+arrowH);
            y1D = Double.valueOf(y1);
            y2D = Double.valueOf(y1+arrowH*arrowCoef);
            graphics2D.drawLine(x1D.intValue(), y1D.intValue(), x2D.intValue(), y2D.intValue());
            x1D = Double.valueOf(x2);
            x2D = Double.valueOf(x2-arrowH);
            y1D = Double.valueOf(y2);
            y2D = Double.valueOf(y2-arrowH*arrowCoef);
            graphics2D.drawLine(x1D.intValue(), y1D.intValue(), x2D.intValue(), y2D.intValue());
            x1D = Double.valueOf(x2);
            x2D = Double.valueOf(x2-arrowH);
            y1D = Double.valueOf(y2);
            y2D = Double.valueOf(y2+arrowH*arrowCoef);
            graphics2D.drawLine(x1D.intValue(), y1D.intValue(), x2D.intValue(), y2D.intValue());
        } else {
            x1D = Double.valueOf(x1);
            x2D = Double.valueOf(x1-arrowH*arrowCoef);
            y1D = Double.valueOf(y1);
            y2D = Double.valueOf(y1+arrowH);
            graphics2D.drawLine(x1D.intValue(), y1D.intValue(), x2D.intValue(), y2D.intValue());
            x1D = Double.valueOf(x1);
            x2D = Double.valueOf(x1+arrowH*arrowCoef);
            y1D = Double.valueOf(y1);
            y2D = Double.valueOf(y1+arrowH);
            graphics2D.drawLine(x1D.intValue(), y1D.intValue(), x2D.intValue(), y2D.intValue());
            x1D = Double.valueOf(x2);
            x2D = Double.valueOf(x2-arrowH*arrowCoef);
            y1D = Double.valueOf(y2);
            y2D = Double.valueOf(y2-arrowH);
            graphics2D.drawLine(x1D.intValue(), y1D.intValue(), x2D.intValue(), y2D.intValue());
            x1D = Double.valueOf(x2);
            x2D = Double.valueOf(x2+arrowH*arrowCoef);
            y1D = Double.valueOf(y2);
            y2D = Double.valueOf(y2-arrowH);
            graphics2D.drawLine(x1D.intValue(), y1D.intValue(), x2D.intValue(), y2D.intValue());
        }
    }
    
    private void initStrikerResultFrameAndComponents() throws IOException {
        Image logo = ImageIO.read(getClass().getResourceAsStream("/logo.jpg"));
        setIconImage(logo);
        calculationsProgressDialog.setIconImage(logo);
    }
    
    private void setResultFieldsVisible(boolean visibility) {
        strikerShapePanel.setVisible(visibility);
        resultPanel.setVisible(visibility);
        setResultDVisible(visibility);
    }
    
    private void setResultDVisible(boolean visibility) {
        dLabel.setVisible(visibility);
        dUnitLabel.setVisible(visibility);
        resultDTextField.setVisible(visibility);
    }
    
    private void setViewState() {
        try {
            setStrikerImage();
        } catch (IOException ex) {
            Logger.getLogger(StrikerResultFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        setCalcButtomState();
        setResultFieldsVisible(state);
    }
    
    private void setStrikerImage() throws IOException {
        BufferedImage myPicture = null;
        switch(ModelPresenter.getInstance().getSystem().type) {
            case DIRECT:  {
                myPicture = ImageIO.read(getClass().getResourceAsStream("/direct_striker.png"));//state ? "/striker_result.bmp" : "/direct_striker.png"));
            } break;
            case REVERSE: {
                myPicture = ImageIO.read(getClass().getResourceAsStream("/reverse_striker.png"));//state ? "/striker_result.bmp" : "/reverse_striker.png"));
            } break;
            case DOUBLE: {
                myPicture = ImageIO.read(getClass().getResourceAsStream("/double_striker.jpg"));//state ? "/striker_result.bmp" : "/double_striker.png"));
            } break;
        }
        if (myPicture != null) {
//            strikerImageView.createImage(ImagesUtil.getResizedImage(myPicture, IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB).getSource());
            strikerImageView.setIcon(new ImageIcon(
                    ImagesUtil.getResizedImage(myPicture, IMAGE_WIDTH, IMAGE_HEIGHT, state ? BufferedImage.TYPE_INT_ARGB: BufferedImage.TYPE_INT_RGB)));
        }
    }
    
    private void setCalcButtomState() {
        calculateButton.setText(state ? "Пересчитать параметры бойка" : "Рассчитать параметры бойка");
    }

    private void initInputFields() {
        String mFormat = "m = %1$.2f кг";
        String saFormat = "Sa = %1$.2f см" + (char) Integer.parseInt("00b2", 16);
        String sbFormat = "Sb = %1$.2f см" + (char) Integer.parseInt("00b2", 16);
        String x1Format = "x1 = %1$.1f мм";
        String xmaxFormat = "Xmax = %1$.1f мм";
        
        double m = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIdentifer("m", false);
        double Sa = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIdentifer("Sa", false);
        double Sb = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIdentifer("Sb", false);
        double x1 = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIdentifer("x1", false);
        double Xmax = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIdentifer("Xmax", false);
        
        String mText = String.format(Locale.ENGLISH, mFormat, m);
        String saText = String.format(Locale.ENGLISH, saFormat, Sa);
        String sbText = String.format(Locale.ENGLISH, sbFormat, Sb);
        String x1Text = String.format(Locale.ENGLISH, x1Format, x1);
        String xmaxText = String.format(Locale.ENGLISH, xmaxFormat, Xmax);
        
        inputMTextField.setText(mText);
        inputSaTextField.setText(saText);
        inputSbTextField.setText(sbText);
        inputX1TextField.setText(x1Text);
        inputXmaxTextField.setText(xmaxText);
        
        final String NUM_FORMAT = "%1$s";
        
        HydroSystem system = ModelPresenter.getInstance().getSystem();
        
        if (system.getSystemData().isValueSettedByUser(21)) {
            roTextField.setText(String.format(Locale.ENGLISH, NUM_FORMAT, 
                    system.getSystemData().getValueByIndex(21, false)));
        }
        
        validateAndSetCoefs();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        calculationsProgressDialog = new javax.swing.JDialog();
        jLabel11 = new javax.swing.JLabel();
        calculationsProgressBar = new javax.swing.JProgressBar();
        closeCalculationsButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        resultPanel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        strikerTableScrollPanel = new javax.swing.JScrollPane();
        strikerTable = new javax.swing.JTable();
        strikerShapePanel = new javax.swing.JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                paintComponentInStrikerShapePanel(g);
            }
        };
        coefPanel = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        dLabel = new javax.swing.JLabel();
        dUnitLabel = new javax.swing.JLabel();
        resultDTextField = new javax.swing.JTextField();
        calculateButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        typeComboBox = new javax.swing.JComboBox<>();
        doubleSystemLabel = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        coefK1TextField = new javax.swing.JTextField();
        coefK2TextField = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        coefK3TextField = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        coefK4TextField = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        resLabelInput = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        inputMTextField = new javax.swing.JLabel();
        inputSaTextField = new javax.swing.JLabel();
        inputSbTextField = new javax.swing.JLabel();
        inputX1TextField = new javax.swing.JLabel();
        inputXmaxTextField = new javax.swing.JLabel();
        resLabelInput1 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        roTextField = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        strikerImageView = new javax.swing.JLabel();

        jLabel11.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        jLabel11.setText("Расчет параметров конструкции бойка");

        calculationsProgressBar.setIndeterminate(true);

        closeCalculationsButton.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        closeCalculationsButton.setText("Закрыть");
        closeCalculationsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeCalculationsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout calculationsProgressDialogLayout = new javax.swing.GroupLayout(calculationsProgressDialog.getContentPane());
        calculationsProgressDialog.getContentPane().setLayout(calculationsProgressDialogLayout);
        calculationsProgressDialogLayout.setHorizontalGroup(
            calculationsProgressDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(calculationsProgressDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(calculationsProgressDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(calculationsProgressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, calculationsProgressDialogLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(closeCalculationsButton)))
                .addContainerGap())
        );
        calculationsProgressDialogLayout.setVerticalGroup(
            calculationsProgressDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(calculationsProgressDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(calculationsProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(closeCalculationsButton)
                .addContainerGap(144, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Расчёт размеров бойка");
        setBackground(new java.awt.Color(215, 254, 255));
        setPreferredSize(new java.awt.Dimension(950, 600));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jPanel1.setBackground(new java.awt.Color(215, 254, 255));

        resultPanel.setOpaque(false);

        jLabel10.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jLabel10.setText("Размеры бойка (в мм):");

        strikerTable.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        strikerTable.setModel(getResultTableModel());
        strikerTableScrollPanel.setViewportView(strikerTable);

        strikerShapePanel.setBackground(new java.awt.Color(255, 255, 255));
        strikerShapePanel.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(65, 151, 237)));
        strikerShapePanel.setFont(new java.awt.Font("Times New Roman", 0, 24)); // NOI18N
        strikerShapePanel.setPreferredSize(new java.awt.Dimension(805, 400));

        javax.swing.GroupLayout strikerShapePanelLayout = new javax.swing.GroupLayout(strikerShapePanel);
        strikerShapePanel.setLayout(strikerShapePanelLayout);
        strikerShapePanelLayout.setHorizontalGroup(
            strikerShapePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 687, Short.MAX_VALUE)
        );
        strikerShapePanelLayout.setVerticalGroup(
            strikerShapePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 192, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout resultPanelLayout = new javax.swing.GroupLayout(resultPanel);
        resultPanel.setLayout(resultPanelLayout);
        resultPanelLayout.setHorizontalGroup(
            resultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resultPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(resultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(strikerTableScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 693, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(strikerShapePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 693, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        resultPanelLayout.setVerticalGroup(
            resultPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(resultPanelLayout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(strikerTableScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(strikerShapePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        coefPanel.setOpaque(false);

        jPanel9.setOpaque(false);

        dLabel.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        dLabel.setText("D =");
        dLabel.setToolTipText("");
        dLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        dLabel.setAlignmentY(0.0F);
        dLabel.setMaximumSize(new java.awt.Dimension(120, 20));
        dLabel.setMinimumSize(new java.awt.Dimension(120, 20));
        dLabel.setPreferredSize(new java.awt.Dimension(120, 20));

        dUnitLabel.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        dUnitLabel.setText("мм");
        dUnitLabel.setToolTipText("");
        dUnitLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        resultDTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        resultDTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        resultDTextField.setAutoscrolls(false);
        resultDTextField.setMargin(new java.awt.Insets(0, 0, 0, 0));
        resultDTextField.setName(""); // NOI18N

        calculateButton.setBackground(new java.awt.Color(65, 151, 237));
        calculateButton.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        calculateButton.setText("Произвести расчёт");
        calculateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(calculateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(dLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resultDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dUnitLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(resultDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(dUnitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(dLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(calculateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setOpaque(false);

        typeComboBox.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "С двумя управляемыми камерами", "С управляемой камерой прямого хода", "С управляемой камерой обратного хода" }));
        typeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeComboBoxActionPerformed(evt);
            }
        });

        doubleSystemLabel.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        doubleSystemLabel.setText("Тип системы двухстороннего действия:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(doubleSystemLabel)
                .addGap(18, 18, 18)
                .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(doubleSystemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        jPanel10.setOpaque(false);

        jLabel17.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jLabel17.setText("k1 =");
        jLabel17.setToolTipText("");
        jLabel17.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel17.setAlignmentY(0.0F);
        jLabel17.setMaximumSize(new java.awt.Dimension(120, 20));
        jLabel17.setMinimumSize(new java.awt.Dimension(120, 20));
        jLabel17.setPreferredSize(new java.awt.Dimension(120, 20));

        coefK1TextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        coefK1TextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        coefK1TextField.setAutoscrolls(false);
        coefK1TextField.setMargin(new java.awt.Insets(0, 0, 0, 0));
        coefK1TextField.setName(""); // NOI18N

        coefK2TextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        coefK2TextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        coefK2TextField.setAutoscrolls(false);
        coefK2TextField.setMargin(new java.awt.Insets(0, 0, 0, 0));
        coefK2TextField.setName(""); // NOI18N

        jLabel21.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jLabel21.setText("k2 =");
        jLabel21.setToolTipText("");
        jLabel21.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel21.setAlignmentY(0.0F);
        jLabel21.setMaximumSize(new java.awt.Dimension(120, 20));
        jLabel21.setMinimumSize(new java.awt.Dimension(120, 20));
        jLabel21.setPreferredSize(new java.awt.Dimension(120, 20));

        coefK3TextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        coefK3TextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        coefK3TextField.setAutoscrolls(false);
        coefK3TextField.setMargin(new java.awt.Insets(0, 0, 0, 0));
        coefK3TextField.setName(""); // NOI18N

        jLabel23.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jLabel23.setText("k3 =");
        jLabel23.setToolTipText("");
        jLabel23.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel23.setAlignmentY(0.0F);
        jLabel23.setMaximumSize(new java.awt.Dimension(120, 20));
        jLabel23.setMinimumSize(new java.awt.Dimension(120, 20));
        jLabel23.setPreferredSize(new java.awt.Dimension(120, 20));

        coefK4TextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        coefK4TextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        coefK4TextField.setAutoscrolls(false);
        coefK4TextField.setMargin(new java.awt.Insets(0, 0, 0, 0));
        coefK4TextField.setName(""); // NOI18N

        jLabel20.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jLabel20.setText("k4 =");
        jLabel20.setToolTipText("");
        jLabel20.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel20.setAlignmentY(0.0F);
        jLabel20.setMaximumSize(new java.awt.Dimension(120, 20));
        jLabel20.setMinimumSize(new java.awt.Dimension(120, 20));
        jLabel20.setPreferredSize(new java.awt.Dimension(120, 20));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(coefK1TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(coefK2TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(coefK3TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(coefK4TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(coefK4TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(coefK3TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(coefK2TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(coefK1TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        resLabelInput.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        resLabelInput.setText("Исходные параметры:");

        jPanel2.setOpaque(false);

        inputMTextField.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        inputMTextField.setText("m = 5 кг");

        inputSaTextField.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        inputSaTextField.setText("Sa = 5 см2");

        inputSbTextField.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        inputSbTextField.setText("Sb = 5 см2");

        inputX1TextField.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        inputX1TextField.setText("x1 = 5 см");

        inputXmaxTextField.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        inputXmaxTextField.setText("Xmax = 5 см");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(inputMTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputSaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputSbTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputX1TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(inputXmaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(inputMTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(inputSaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(inputSbTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(inputX1TextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(inputXmaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        resLabelInput1.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        resLabelInput1.setText("Коэффициенты для расчета:");

        jPanel30.setBackground(new java.awt.Color(245, 245, 245));
        jPanel30.setOpaque(false);

        jLabel80.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jLabel80.setText("Плотность:");
        jLabel80.setToolTipText("");
        jLabel80.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel80.setAlignmentY(0.0F);
        jLabel80.setMaximumSize(new java.awt.Dimension(120, 20));
        jLabel80.setMinimumSize(new java.awt.Dimension(120, 20));
        jLabel80.setPreferredSize(new java.awt.Dimension(120, 20));

        jLabel81.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jLabel81.setText("" + (char) Integer.parseInt("03C1", 16) + " = ");

        jLabel82.setFont(new java.awt.Font("Times New Roman", 0, 16)); // NOI18N
        jLabel82.setText("кг/м" + (char) Integer.parseInt("00b3", 16));
        jLabel82.setToolTipText("");
        jLabel82.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        roTextField.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        roTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        roTextField.setAutoscrolls(false);
        roTextField.setMargin(new java.awt.Insets(0, 0, 0, 0));
        roTextField.setName(""); // NOI18N

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel30Layout.createSequentialGroup()
                        .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel82, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel81, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel82)
                    .addComponent(roTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout coefPanelLayout = new javax.swing.GroupLayout(coefPanel);
        coefPanel.setLayout(coefPanelLayout);
        coefPanelLayout.setHorizontalGroup(
            coefPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(coefPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(coefPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(resLabelInput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, coefPanelLayout.createSequentialGroup()
                        .addComponent(resLabelInput1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(602, 602, 602))))
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        coefPanelLayout.setVerticalGroup(
            coefPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, coefPanelLayout.createSequentialGroup()
                .addComponent(resLabelInput, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resLabelInput1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 23, Short.MAX_VALUE)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel5.setOpaque(false);

        strikerImageView.setBorder(javax.swing.BorderFactory.createMatteBorder(3, 3, 3, 3, new java.awt.Color(65, 151, 237)));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(strikerImageView, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(strikerImageView, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resultPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(coefPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(coefPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(resultPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 830, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 790, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void validateAndSetCoefs() {
        String text = coefK1TextField.getText();
        if (text.matches(Constants.REGEX)) {
            ModelPresenter.getInstance().getSystem().setK1(Double.parseDouble(text));
        } else {
            coefK1TextField.setText(String.valueOf(ModelPresenter.getInstance().getSystem().getK1()));
        }
        
        text = coefK2TextField.getText();
        if (text.matches(Constants.REGEX)) {
            ModelPresenter.getInstance().getSystem().setK2(Double.parseDouble(text));
        } else {
            coefK2TextField.setText(String.valueOf(ModelPresenter.getInstance().getSystem().getK2()));
        }
        
        text = coefK3TextField.getText();
        if (text.matches(Constants.REGEX)) {
            ModelPresenter.getInstance().getSystem().setK3(Double.parseDouble(text));
        } else {
            coefK3TextField.setText(String.valueOf(ModelPresenter.getInstance().getSystem().getK3()));
        }
        
        text = coefK4TextField.getText();
        if (text.matches(Constants.REGEX)) {
            ModelPresenter.getInstance().getSystem().setK4(Double.parseDouble(text));
        } else {
            coefK4TextField.setText(String.valueOf(ModelPresenter.getInstance().getSystem().getK4()));
        }
    }
    
    private void recalculate() {
        String roText = TextUtil.prepareStringToDouble(roTextField.getText());
        HydroSystem system = ModelPresenter.getInstance().getSystem();
        if (roText.matches(Constants.REGEX)) {
            system.getSystemDownBounds().setValueByIndex(21, Double.parseDouble(roText), false);
            system.getSystemDownBounds().userSetted.set(21, true);
        }
        if (state) {
            validateAndSetCoefs();
            //TODO recalc
        }
        calculationsProgressDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ModelPresenter.getInstance().makeCalculations();
                        calculationsProgressDialog.dispose();
                    }
                }).start();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                onCalculationsFinish();
            }
        });
        calculationsProgressDialog.setVisible(true);
    }
    
    private void calculateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateButtonActionPerformed
        recalculate();
    }//GEN-LAST:event_calculateButtonActionPerformed

    private void closeCalculationsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeCalculationsButtonActionPerformed
        calculationsProgressDialog.dispose();
    }//GEN-LAST:event_closeCalculationsButtonActionPerformed

    private void requestModelType(model.BidirectionalSystem.Type type) {
        if(ModelPresenter.getInstance().getDoubleSystemType() != type) {
            ModelPresenter.getInstance().setDoubleSystemType(type);
            recalculate();
        }
    }
    
    private void typeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeComboBoxActionPerformed
        int selected = typeComboBox.getSelectedIndex();
        switch (selected) {
            case 0 : {
                requestModelType(BOTH_CHAMBERS);
            } break;
            case 1 : {
                requestModelType(DIRECT_CHAMBER);
            } break;
            case 2 : {
                requestModelType(REVERSE_CHAMBER);
            } break;
        }
    }//GEN-LAST:event_typeComboBoxActionPerformed
    
    private void onCalculationsFinish() {
        state = true;
        setViewState();
        setResultOnView();
        strikerShapePanel.repaint();
    }
    
    private Object [][] getResultTable() {
        Object [][] resultTable = new Object[2][7];
        resultTable[0][0] = "Длины (l)";
        resultTable[1][0] = "Диаметры (d)";
        
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
        
        resultTable[0][1] = l1;
        resultTable[0][2] = l2;
        resultTable[0][3] = l3;
        resultTable[0][4] = l4;
        resultTable[0][5] = l5;
        resultTable[0][6] = L;
        
        resultTable[1][1] = db;
        resultTable[1][2] = D;
        resultTable[1][3] = dc;
        resultTable[1][4] = D;
        resultTable[1][5] = da;
        resultTable[1][6] = D;
        
        return resultTable;
    }
    
    public javax.swing.table.DefaultTableModel getResultTableModel() {
        return new javax.swing.table.DefaultTableModel(
            getResultTable(),
            new String [] {
                " ", "1", "2", "3", "4", "5", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
    }
    
    private void setResultOnView() {
        String corDFormat = "%1$.2f";
        double D = ModelPresenter.getInstance().getSystem().getSystemData().getValueByIndex(31, false);
        resultDTextField.setText(String.format(Locale.ENGLISH, corDFormat, D));

        strikerTable.setModel(getResultTableModel());
        TableUtil.setModelTable(strikerTable);
        
        setResultFieldsVisible(true);
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
            java.util.logging.Logger.getLogger(StrikerResultFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StrikerResultFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StrikerResultFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StrikerResultFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StrikerResultFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calculateButton;
    private javax.swing.JProgressBar calculationsProgressBar;
    private javax.swing.JDialog calculationsProgressDialog;
    private javax.swing.JButton closeCalculationsButton;
    private javax.swing.JTextField coefK1TextField;
    private javax.swing.JTextField coefK2TextField;
    private javax.swing.JTextField coefK3TextField;
    private javax.swing.JTextField coefK4TextField;
    private javax.swing.JPanel coefPanel;
    private javax.swing.JLabel dLabel;
    private javax.swing.JLabel dUnitLabel;
    private javax.swing.JLabel doubleSystemLabel;
    private javax.swing.JLabel inputMTextField;
    private javax.swing.JLabel inputSaTextField;
    private javax.swing.JLabel inputSbTextField;
    private javax.swing.JLabel inputX1TextField;
    private javax.swing.JLabel inputXmaxTextField;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel resLabelInput;
    private javax.swing.JLabel resLabelInput1;
    private javax.swing.JTextField resultDTextField;
    private javax.swing.JPanel resultPanel;
    private javax.swing.JTextField roTextField;
    private javax.swing.JLabel strikerImageView;
    private javax.swing.JPanel strikerShapePanel;
    private javax.swing.JTable strikerTable;
    private javax.swing.JScrollPane strikerTableScrollPanel;
    private javax.swing.JComboBox<String> typeComboBox;
    // End of variables declaration//GEN-END:variables
}
