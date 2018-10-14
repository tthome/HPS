/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javafx.util.Pair;

/**
 *
 * @author etb-t
 */
public class GraphPanel extends javax.swing.JPanel {
    
    private static final String NUM_FORMAT = "%1$.1e";
    
    private final int padding = 25;
    private final int labelPadding = 35;
    private final Color lineColor = new Color(44, 102, 230, 180);
    private final Color pointColor = new Color(100, 100, 100, 180);
    private final Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private final int pointWidth = 4;
    private final int numberGraphPoints = 100;
    private final int numberYDivisions = 2;
    private final int numberXDivisions = 2;
    private List<Double> yValues;
    private List<Double> xValues;
    
    private int pointGapSize = 50;

    /**
     * Creates new form GraphPanel
     */
    public GraphPanel() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(800, 300));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        
        pointGapSize = yValues.size() / numberGraphPoints;

        double xScale = -((double) getWidth() - 2. * padding - labelPadding) / (getMaxX() - getMinX());
        double yScale = ((double) getHeight() - 2. * padding) / (getMaxY() - getMinY());

        int size = yValues.size();
        List<Pair<Double, Double>> graphPoints = new ArrayList<>();
        for (int i = 0; i < size; i+=pointGapSize) {
            double x1 = ((getMaxX() - xValues.get(i)) * xScale + padding + labelPadding) + (double) getWidth() - 2. * padding - labelPadding;
            double y1 = ((getMaxY() - yValues.get(i)) * yScale + padding);
            graphPoints.add(new Pair<>(x1, y1));
        }
        // add last
        double xLast = ((getMaxX() - xValues.get(size-1)) * xScale + padding + labelPadding) + (double) getWidth() - 2. * padding - labelPadding;
        double yLast = ((getMaxY() - yValues.get(size-1)) * yScale + padding);
        graphPoints.add(new Pair<>(xLast, yLast));
        
        // draw white background
        g2.setColor(Color.WHITE);
        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding);
        g2.setColor(Color.BLACK);

        // create hatch marks and grid lines for y axis.
        for (int i = 0; i < numberYDivisions + 1; i++) {
            int x0 = padding + labelPadding;
            int x1 = pointWidth + padding + labelPadding;
            int y0 = getHeight() - ((i * (getHeight() - padding * 2)) / numberYDivisions + padding);
            int y1 = y0;
            if (graphPoints.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                g2.setColor(Color.BLACK);
                double valueToDraw = (((getMinY() + (getMaxY() - getMinY()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0;
                String yLabel = String.format(Locale.ENGLISH, NUM_FORMAT, valueToDraw);
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(yLabel);
                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // and for x axis
        for (int i = 0; i < numberXDivisions + 1; i++) {
            int x0 = i * (getWidth() - padding * 2 - labelPadding) / numberXDivisions + padding + labelPadding;
            int x1 = x0;
            int y0 = getHeight() - padding;
            int y1 = y0 - pointWidth;
            if (graphPoints.size() > 0) {
                g2.setColor(gridColor);
                g2.drawLine(x0, getHeight() - padding - 1 - pointWidth, x1, padding);
                g2.setColor(Color.BLACK);
                double valueToDraw = (((getMinX() + (getMaxX() - getMinX()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0;
                String xLabel = String.format(Locale.ENGLISH, NUM_FORMAT, valueToDraw);
                FontMetrics metrics = g2.getFontMetrics();
                int labelWidth = metrics.stringWidth(xLabel);
                g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
            }
            g2.drawLine(x0, y0, x1, y1);
        }

        // create x and y axes 
        g2.drawLine(padding + labelPadding, getHeight() - padding, padding + labelPadding, padding);
        g2.drawLine(padding + labelPadding, getHeight() - padding, getWidth() - padding, getHeight() - padding);

        Stroke oldStroke = g2.getStroke();
        g2.setColor(lineColor);
        g2.setStroke(GRAPH_STROKE);
        Line2D line = new Line2D.Double();
        for (int i = 0; i < graphPoints.size() - 1; i++) {
            double x1 = graphPoints.get(i).getKey();
            double y1 = graphPoints.get(i).getValue();
            double x2 = graphPoints.get(i + 1).getKey();
            double y2 = graphPoints.get(i + 1).getValue();
            line.setLine(x1, y1, x2, y2);
            g2.draw(line);
        }

        g2.setStroke(oldStroke);
        g2.setColor(pointColor);
        Ellipse2D.Double point = new Ellipse2D.Double();
        for (int i = 0; i < graphPoints.size(); i++) {
            double x = graphPoints.get(i).getKey() - pointWidth / 4.;
            double y = graphPoints.get(i).getValue()- pointWidth / 4.;
            double ovalW = pointWidth / 2.;
            double ovalH = pointWidth / 2.;
            point.setFrame(x, y, ovalW, ovalH);
            g2.draw(point);
        }
    }

    public void setYValues(List<Double> yValues) {
        this.yValues = yValues;
        invalidate();
        this.repaint();
    }
    
    public void setXValues(List<Double> xValues) {
        this.xValues = xValues;
        invalidate();
        this.repaint();
    }
    
    private double getMinY() {
        return getMin(yValues);
    }

    private double getMaxY() {
        return getMax(yValues);
    }
    
    private double getMinX() {
        return getMin(xValues);
    }

    private double getMaxX() {
        return getMax(xValues);
    }
    
    private double getMin(List<Double> array) {
        double minScore = Double.MAX_VALUE;
        for (Double score : array) {
            minScore = Math.min(minScore, score);
        }
        return minScore;
    }

    private double getMax(List<Double> array) {
        double maxScore = Double.MIN_VALUE;
        for (Double score : array) {
            maxScore = Math.max(maxScore, score);
        }
        return maxScore;
    }

}
