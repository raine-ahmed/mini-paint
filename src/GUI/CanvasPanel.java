/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CanvasPanel.java
 *
 * Created on Sep 14, 2011, 10:16:14 PM
 */
package GUI;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.swing.*;
import javax.imageio.*;
import minipaint.Coordinate;
import minipaint.StepInfo;

/**
 *
 * @author Yousuf
 */
public class CanvasPanel extends javax.swing.JPanel implements Serializable {

    protected final static int LINE = 1, SQUARE = 2, OVAL = 3, POLYGON = 4, ROUND_RECT = 5, FREE_HAND = 6,
            SOLID_SQUARE = 22, SOLID_OVAL = 33, SOLID_POLYGON = 44,
            SOLID_ROUND_RECT = 55, ERASE = 66;
    protected static Vector vLine, vSquare, vOval, vPolygon, vRoundRect, vFreeHand,
            vSolidSquare, vSolidOval, vSolidPolygon, vSolidRoundRect, vFile,
            xPolygon, yPolygon, vErase;
    protected Stack undoStack, redoStack;
    private Color foreGroundColor, backGroundColor;
    private int x1, y1, x2, y2, linex1, linex2, liney1, liney2, erasex1, erasex2, erasey1, erasey2, drawMode = 0;
    private boolean solidMode, polygonBuffer;
    private File fileName;

    public CanvasPanel() {

        vLine = new Vector();
        vSquare = new Vector();
        vOval = new Vector();
        vPolygon = new Vector();
        vRoundRect = new Vector();
        vFreeHand = new Vector();
        vSolidSquare = new Vector();
        vSolidOval = new Vector();
        vSolidPolygon = new Vector();
        vSolidRoundRect = new Vector();
        vFile = new Vector();
        xPolygon = new Vector();
        yPolygon = new Vector();
        vErase = new Vector();
        solidMode = false;
        polygonBuffer = false;
        foreGroundColor = Color.BLACK;
        backGroundColor = Color.WHITE;
        undoStack = new Stack();
        redoStack = new Stack();

        this.setBackground(backGroundColor);
        //ForegroundLabel.setBackground(Color.BLACK);

        repaint();

        initComponents();
    }

    ///**************************************************    Drawing Part    ********************************************///
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        redrawVectorBuffer(g);

        g.setColor(foreGroundColor);
        if (drawMode == LINE) {
            g.drawLine(x1, y1, x2, y2);
        }
        if (drawMode == OVAL) {
            if (solidMode) {
                if (x1 > x2 || y1 > y2) {
                    g.fillOval(x2, y2, x1 - x2, y1 - y2);
                } else {
                    g.fillOval(x1, y1, x2 - x1, y2 - y1);
                }
            } else {
                if (x1 > x2 || y1 > y2) {
                    g.drawOval(x2, y2, x1 - x2, y1 - y2);
                } else {
                    g.drawOval(x1, y1, x2 - x1, y2 - y1);
                }
            }
        }
        if (drawMode == ROUND_RECT) {
            if (solidMode) {
                if (x1 > x2 || y1 > y2) {
                    g.fillRoundRect(x2, y2, x1 - x2, y1 - y2, 25, 25);
                } else {
                    g.fillRoundRect(x1, y1, x2 - x1, y2 - y1, 25, 25);
                }
            } else {
                if (x1 > x2 || y1 > y2) {
                    g.drawRoundRect(x2, y2, x1 - x2, y1 - y2, 25, 25);
                } else {
                    g.drawRoundRect(x1, y1, x2 - x1, y2 - y1, 25, 25);
                }
            }
        }

        if (drawMode == SQUARE) {
            if (solidMode) {
                if (x1 > x2 || y1 > y2) {
                    g.fillRect(x2, y2, x1 - x2, y1 - y2);
                } else {
                    g.fillRect(x1, y1, x2 - x1, y2 - y1);
                }
            } else {
                if (x1 > x2 || y1 > y2) {
                    g.drawRect(x2, y2, x1 - x2, y1 - y2);
                } else {
                    g.drawRect(x1, y1, x2 - x1, y2 - y1);
                }
            }
        }
        if (drawMode == POLYGON || drawMode == SOLID_POLYGON) {
            int xPos[] = new int[xPolygon.size()];
            int yPos[] = new int[yPolygon.size()];

            for (int count = 0; count < xPos.length; count++) {
                xPos[count] = ((Integer) (xPolygon.elementAt(count))).intValue();
                yPos[count] = ((Integer) (yPolygon.elementAt(count))).intValue();
            }
            g.drawPolyline(xPos, yPos, xPos.length);
            polygonBuffer = true;
        }
        if (drawMode == FREE_HAND) {
            g.drawLine(linex1, liney1, linex2, liney2);
        }
    }
    ///*--------------------------------------------------------------------------*//

    private void redrawVectorBuffer(Graphics g) {

        for (int i = 0; i < vFreeHand.size(); i++) {
            g.setColor(((Coordinate) vFreeHand.elementAt(i)).colour());
            g.drawLine(((Coordinate) vFreeHand.elementAt(i)).getX1(), ((Coordinate) vFreeHand.elementAt(i)).getY1(), ((Coordinate) vFreeHand.elementAt(i)).getX2(), ((Coordinate) vFreeHand.elementAt(i)).getY2());
        }
        for (int i = 0; i < vLine.size(); i++) {
            g.setColor(((Coordinate) vLine.elementAt(i)).colour());
            g.drawLine(((Coordinate) vLine.elementAt(i)).getX1(), ((Coordinate) vLine.elementAt(i)).getY1(), ((Coordinate) vLine.elementAt(i)).getX2(), ((Coordinate) vLine.elementAt(i)).getY2());
        }
        for (int i = 0; i < vOval.size(); i++) {
            g.setColor(((Coordinate) vOval.elementAt(i)).colour());
            g.drawOval(((Coordinate) vOval.elementAt(i)).getX1(), ((Coordinate) vOval.elementAt(i)).getY1(), ((Coordinate) vOval.elementAt(i)).getX2() - ((Coordinate) vOval.elementAt(i)).getX1(), ((Coordinate) vOval.elementAt(i)).getY2() - ((Coordinate) vOval.elementAt(i)).getY1());
        }
        for (int i = 0; i < vRoundRect.size(); i++) {
            g.setColor(((Coordinate) vRoundRect.elementAt(i)).colour());
            g.drawRoundRect(((Coordinate) vRoundRect.elementAt(i)).getX1(), ((Coordinate) vRoundRect.elementAt(i)).getY1(), ((Coordinate) vRoundRect.elementAt(i)).getX2() - ((Coordinate) vRoundRect.elementAt(i)).getX1(), ((Coordinate) vRoundRect.elementAt(i)).getY2() - ((Coordinate) vRoundRect.elementAt(i)).getY1(), 25, 25);
        }
        for (int i = 0; i < vSquare.size(); i++) {
            g.setColor(((Coordinate) vSquare.elementAt(i)).colour());
            g.drawRect(((Coordinate) vSquare.elementAt(i)).getX1(), ((Coordinate) vSquare.elementAt(i)).getY1(), ((Coordinate) vSquare.elementAt(i)).getX2() - ((Coordinate) vSquare.elementAt(i)).getX1(), ((Coordinate) vSquare.elementAt(i)).getY2() - ((Coordinate) vSquare.elementAt(i)).getY1());
        }
        for (int i = 0; i < vPolygon.size(); i++) {
            int xPos[] = new int[((Coordinate) vPolygon.elementAt(i)).getXPolygon().size()];
            int yPos[] = new int[((Coordinate) vPolygon.elementAt(i)).getYPolygon().size()];

            for (int count = 0; count < xPos.length; count++) {
                xPos[count] = ((Integer) ((Coordinate) vPolygon.elementAt(i)).getXPolygon().elementAt(count)).intValue();
                yPos[count] = ((Integer) ((Coordinate) vPolygon.elementAt(i)).getYPolygon().elementAt(count)).intValue();
            }
            g.setColor(((Coordinate) vPolygon.elementAt(i)).colour());
            g.drawPolygon(xPos, yPos, xPos.length);
        }
        for (int i = 0; i < vSolidOval.size(); i++) {
            g.setColor(((Coordinate) vSolidOval.elementAt(i)).colour());
            g.fillOval(((Coordinate) vSolidOval.elementAt(i)).getX1(), ((Coordinate) vSolidOval.elementAt(i)).getY1(), ((Coordinate) vSolidOval.elementAt(i)).getX2() - ((Coordinate) vSolidOval.elementAt(i)).getX1(), ((Coordinate) vSolidOval.elementAt(i)).getY2() - ((Coordinate) vSolidOval.elementAt(i)).getY1());
        }
        for (int i = 0; i < vSolidRoundRect.size(); i++) {
            g.setColor(((Coordinate) vSolidRoundRect.elementAt(i)).colour());
            g.fillRoundRect(((Coordinate) vSolidRoundRect.elementAt(i)).getX1(), ((Coordinate) vSolidRoundRect.elementAt(i)).getY1(), ((Coordinate) vSolidRoundRect.elementAt(i)).getX2() - ((Coordinate) vSolidRoundRect.elementAt(i)).getX1(), ((Coordinate) vSolidRoundRect.elementAt(i)).getY2() - ((Coordinate) vSolidRoundRect.elementAt(i)).getY1(), 25, 25);
        }
        for (int i = 0; i < vSolidSquare.size(); i++) {
            g.setColor(((Coordinate) vSolidSquare.elementAt(i)).colour());
            g.fillRect(((Coordinate) vSolidSquare.elementAt(i)).getX1(), ((Coordinate) vSolidSquare.elementAt(i)).getY1(), ((Coordinate) vSolidSquare.elementAt(i)).getX2() - ((Coordinate) vSolidSquare.elementAt(i)).getX1(), ((Coordinate) vSolidSquare.elementAt(i)).getY2() - ((Coordinate) vSolidSquare.elementAt(i)).getY1());
        }

        for (int i = 0; i < vSolidPolygon.size(); i++) {
            int xPos[] = new int[((Coordinate) vSolidPolygon.elementAt(i)).getXPolygon().size()];
            int yPos[] = new int[((Coordinate) vSolidPolygon.elementAt(i)).getYPolygon().size()];

            for (int count = 0; count < xPos.length; count++) {
                xPos[count] = ((Integer) ((Coordinate) vSolidPolygon.elementAt(i)).getXPolygon().elementAt(count)).intValue();
                yPos[count] = ((Integer) ((Coordinate) vSolidPolygon.elementAt(i)).getYPolygon().elementAt(count)).intValue();
            }
            g.setColor(((Coordinate) vSolidPolygon.elementAt(i)).colour());
            g.fillPolygon(xPos, yPos, xPos.length);
        }

        for (int i = 0; i < vErase.size(); i++) {
            g.setColor(backGroundColor);
            g.fillRect(((Coordinate) vErase.elementAt(i)).getX1(), ((Coordinate) vErase.elementAt(i)).getY1(), ((Coordinate) vErase.elementAt(i)).getX2() - ((Coordinate) vErase.elementAt(i)).getX1(), ((Coordinate) vErase.elementAt(i)).getY2() - ((Coordinate) vErase.elementAt(i)).getY1());
        }
    }

    /*----------------------------------------------------------------------------*/
    public void flushPolygonBuffer() {
        if (!solidMode) {
            vPolygon.add(new Coordinate(xPolygon, yPolygon, foreGroundColor));
            undoStack.push(new StepInfo(POLYGON, new Coordinate(xPolygon, yPolygon, foreGroundColor)));
        } else {
            vSolidPolygon.add(new Coordinate(xPolygon, yPolygon, foreGroundColor));
            undoStack.push(new StepInfo(SOLID_POLYGON, new Coordinate(xPolygon, yPolygon, foreGroundColor)));
        }

        xPolygon.removeAllElements();
        yPolygon.removeAllElements();

        polygonBuffer = false;
        repaint();
    }

    public boolean isExistPolygonBuffer() {
        return polygonBuffer;
    }

    /*--------------------------------------------------------------------------*/
    public void setDrawMode(int mode) {
        drawMode = mode;
    }

    public int getDrawMode() {
        return drawMode;
    }
    /*--------------------------------------------------------------------------*/

    public void setSolidMode(Boolean inSolidMode) {
        solidMode = inSolidMode.booleanValue();
    }

    public Boolean getSolidMode() {
        return Boolean.valueOf(solidMode);
    }

    /*****************************************************   Drawing Ends   ******************************************///
    /*****************************************************    Color  *************************************************/
    public void setForeGroundColor() {

        foreGroundColor = JColorChooser.showDialog(null, "ForeGround Color", foreGroundColor);
        if (foreGroundColor != null) {
            ForegroundLabel.setBackground(foreGroundColor);
            // foreGroundColor = foreGroundColor;
            //setForeGroundColor(foreColor);
        }
    }

    public void setForeGroundColor(Color inputColor) {
        foreGroundColor = inputColor;
        ForegroundLabel.setBackground(foreGroundColor);
    }

    public Color getForeGroundColor() {
        return foreGroundColor;
    }

    public void setBackGroundColor() {
        backGroundColor = JColorChooser.showDialog(null, "BackGround Color", backGroundColor);
        if (backGroundColor != null) {
            BackgroundLabel.setBackground(backGroundColor);
            this.setBackground(backGroundColor);
            //setBackground(backColor);
        }
    }

    public void setBackGroundColor(Color inputColor) {
        backGroundColor = inputColor;
        if (backGroundColor != null) {
            BackgroundLabel.setBackground(backGroundColor);
            this.setBackground(backGroundColor);

        }

    }

    public Color getBackGroundColor() {
        return backGroundColor;
    }
//****************************************************  Color Ends    ***********************************************//

//****************************************************   UNDO & REDO  ***********************************************//
    public void undo() {
        StepInfo UndoStempInfo;

        if (undoStack.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Can't Undo", "Mini Paint", JOptionPane.INFORMATION_MESSAGE);
        } else {
            UndoStempInfo = (StepInfo) undoStack.pop();

            switch (UndoStempInfo.getStepType()) {
                case 1:
                    vLine.remove(vLine.size() - 1);
                    break;
                case 2:
                    vSquare.remove(vSquare.size() - 1);
                    break;
                case 3:
                    vOval.remove(vOval.size() - 1);
                    break;
                case 4:
                    vPolygon.remove(vPolygon.size() - 1);
                    break;
                case 5:
                    vRoundRect.remove(vRoundRect.size() - 1);
                    break;
                case 6:
                    vFreeHand.remove(vFreeHand.size() - 1);
                    break;
                case 22:
                    vSolidSquare.remove(vSolidSquare.size() - 1);
                    break;
                case 33:
                    vSolidOval.remove(vSolidOval.size() - 1);
                    break;
                case 44:
                    vSolidPolygon.remove(vSolidPolygon.size() - 1);
                    break;
                case 55:
                    vSolidRoundRect.remove(vSolidRoundRect.size() - 1);
                    break;
                    case 66:
                    vErase.remove(vErase.size() - 1);
                    break;
            }
            redoStack.push(UndoStempInfo);
        }
        repaint();
    }
    /*----------------------------------------------------------------------------*/

    public void redo() {
        StepInfo RedoStempInfo;

        if (redoStack.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Can't Redo", "Mini Paint", JOptionPane.INFORMATION_MESSAGE);
        } else {
            RedoStempInfo = (StepInfo) redoStack.pop();

            switch (RedoStempInfo.getStepType()) {
                case 1:
                    vLine.add(RedoStempInfo.getStepCoordinate());
                    break;
                case 2:
                    vSquare.add(RedoStempInfo.getStepCoordinate());
                    break;
                case 3:
                    vOval.add(RedoStempInfo.getStepCoordinate());
                    break;
                case 4:
                    vPolygon.add(RedoStempInfo.getStepCoordinate());
                    break;
                case 5:
                    vRoundRect.add(RedoStempInfo.getStepCoordinate());
                    break;
                case 6:
                    vFreeHand.add(RedoStempInfo.getStepCoordinate());
                    break;
                case 22:
                    vSolidSquare.add(RedoStempInfo.getStepCoordinate());
                    break;
                case 33:
                    vSolidOval.add(RedoStempInfo.getStepCoordinate());
                    break;
                case 44:
                    vSolidPolygon.add(RedoStempInfo.getStepCoordinate());
                    break;
                case 55:
                    vSolidRoundRect.add(RedoStempInfo.getStepCoordinate());
                    break;
                    case 66:
                    vErase.add(RedoStempInfo.getStepCoordinate());
                    break;
            }
            undoStack.push(RedoStempInfo);
        }
        repaint();
    }
    //****************************************************   UNDO & REDO Ends ***********************************************//

    /*----------------------------------------------------------------------------*/
    public void clearCanvas() {
        if (isExistPolygonBuffer() != false) {
            flushPolygonBuffer();
        }
        vFreeHand.removeAllElements();
        vLine.removeAllElements();
        vOval.removeAllElements();
        vPolygon.removeAllElements();
        vRoundRect.removeAllElements();
        vSolidOval.removeAllElements();
        vSolidPolygon.removeAllElements();
        vSolidRoundRect.removeAllElements();
        vSolidSquare.removeAllElements();
        vSquare.removeAllElements();
        vErase.removeAllElements();
        undoStack.clear();
        redoStack.clear();
        foreGroundColor = Color.BLACK;
        backGroundColor = Color.WHITE;
        this.setBackground(backGroundColor);
        this.setBackGroundColor(backGroundColor);
        this.setForeGroundColor(foreGroundColor);
        
        repaint();
    }

    //**********************************************  Saving part   ********************************************//
    public String SaveCanvasToFile() {
        if (fileName != null) {
            vFile.removeAllElements();
            vFile.addElement(vFreeHand);
            vFile.addElement(vLine);
            vFile.addElement(vOval);
            vFile.addElement(vPolygon);
            vFile.addElement(vRoundRect);
            vFile.addElement(vSolidOval);
            vFile.addElement(vSolidPolygon);
            vFile.addElement(vSolidRoundRect);
            vFile.addElement(vSolidSquare);
            vFile.addElement(vSquare);
            vFile.addElement(vErase);
            vFile.addElement(new Color(getBackGroundColor().getRGB()));


            try {
                FileOutputStream fos = new FileOutputStream(fileName);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(vFile);
                JOptionPane.showMessageDialog(null, "File Saved", "Mini Paint", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exp) {
            }


        } else {
            SaveAsCanvasToFile();
        }
        repaint();
        String title = fileName.getName();
        return title;
    }
    /*----------------------------------------------------------------------------*/

    public String SaveAsCanvasToFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.CANCEL_OPTION) {
            return null;
        }

        fileName = fileChooser.getSelectedFile();



        if (fileName == null || fileName.getName().equals("")) {
            JOptionPane.showMessageDialog(null, "Invalid File Name", "Mini Paint", JOptionPane.ERROR_MESSAGE);
        } else {
            vFile.removeAllElements();
            vFile.addElement(vFreeHand);
            vFile.addElement(vLine);
            vFile.addElement(vOval);
            vFile.addElement(vPolygon);
            vFile.addElement(vRoundRect);
            vFile.addElement(vSolidOval);
            vFile.addElement(vSolidPolygon);
            vFile.addElement(vSolidRoundRect);
            vFile.addElement(vSolidSquare);
            vFile.addElement(vSquare);
            vFile.addElement(vErase);
            vFile.addElement(new Color(getBackGroundColor().getRGB()));

            try {
                FileOutputStream fos = new FileOutputStream(fileName);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(vFile);
                JOptionPane.showMessageDialog(null, "File Saved", "Mini Paint", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exp) {
            }
        }
        repaint();
        String title = fileName.getName();
        return title;
    }


    /*----------------------------------------------------------------------------*/
    public String OpenCanvasFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.CANCEL_OPTION) {
            return null;
        }

        fileName = fileChooser.getSelectedFile();
        String title = fileName.getName();
        if (fileName != null) {
            try {
                FileInputStream fis = new FileInputStream(fileName);
                ObjectInputStream ois = new ObjectInputStream(fis);
                vFile = (Vector) ois.readObject();

                this.clearCanvas();
                vFreeHand = (Vector) vFile.elementAt(0);
                vLine = (Vector) vFile.elementAt(1);
                vOval = (Vector) vFile.elementAt(2);
                vPolygon = (Vector) vFile.elementAt(3);
                vRoundRect = (Vector) vFile.elementAt(4);
                vSolidOval = (Vector) vFile.elementAt(5);
                vSolidPolygon = (Vector) vFile.elementAt(6);
                vSolidRoundRect = (Vector) vFile.elementAt(7);
                vSolidSquare = (Vector) vFile.elementAt(8);
                vSquare = (Vector) vFile.elementAt(9);
                vErase = (Vector) vFile.elementAt(10);
                backGroundColor = (Color) vFile.elementAt(11);

                this.setBackground(backGroundColor);
                repaint();
            } catch (Exception exp) {

                JOptionPane.showMessageDialog(null, "Can't Open File");
            }
        } else {
            fileName = null;
        }
        return title;
    }
//**********************************************  Saving part  Ends ********************************************//

    //*********************************************************************************************************////
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonPanel = new javax.swing.JPanel();
        undoBtn = new javax.swing.JButton();
        redoButton = new javax.swing.JButton();
        lineBtn = new javax.swing.JButton();
        rectangleButton = new javax.swing.JButton();
        ovalButton = new javax.swing.JButton();
        polygonButton = new javax.swing.JButton();
        roundedRec = new javax.swing.JButton();
        pencilButton = new javax.swing.JButton();
        eraseButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        fullChk = new java.awt.Checkbox();
        ForegroundButton = new javax.swing.JButton();
        ForegroundLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        BackgroundButton = new javax.swing.JButton();
        BackgroundLabel = new javax.swing.JLabel();
        ColorButtonPanel = new javax.swing.JPanel();
        Position = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setBackground(getBackground());
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                formMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formComponentMoved(evt);
            }
        });
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });

        buttonPanel.setBackground(new java.awt.Color(51, 51, 51));
        buttonPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 0, 0), 1, true));
        buttonPanel.setAlignmentX(0.0F);
        buttonPanel.setAlignmentY(0.0F);
        buttonPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                buttonPanelMouseEntered(evt);
            }
        });

        undoBtn.setBackground(new java.awt.Color(51, 51, 51));
        undoBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Undo-32.png"))); // NOI18N
        undoBtn.setToolTipText("Undo");
        undoBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        undoBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        undoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoBtnActionPerformed(evt);
            }
        });

        redoButton.setBackground(new java.awt.Color(51, 51, 51));
        redoButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Redo-32.png"))); // NOI18N
        redoButton.setToolTipText("Redo");
        redoButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        redoButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        redoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redoButtonActionPerformed(evt);
            }
        });

        lineBtn.setBackground(new java.awt.Color(51, 51, 51));
        lineBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Draw-Line-32.png"))); // NOI18N
        lineBtn.setToolTipText("Line");
        lineBtn.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lineBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lineBtn.setDoubleBuffered(true);
        lineBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lineBtnMousePressed(evt);
            }
        });
        lineBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lineBtnActionPerformed(evt);
            }
        });

        rectangleButton.setBackground(new java.awt.Color(51, 51, 51));
        rectangleButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Shape-Square-32.png"))); // NOI18N
        rectangleButton.setToolTipText("Rectangle");
        rectangleButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        rectangleButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        rectangleButton.setDoubleBuffered(true);
        rectangleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rectangleButtonActionPerformed(evt);
            }
        });

        ovalButton.setBackground(new java.awt.Color(51, 51, 51));
        ovalButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Draw-Ellipse-32.png"))); // NOI18N
        ovalButton.setToolTipText("Oval");
        ovalButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ovalButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ovalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ovalButtonActionPerformed(evt);
            }
        });

        polygonButton.setBackground(new java.awt.Color(51, 51, 51));
        polygonButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Draw-Polygon-32.png"))); // NOI18N
        polygonButton.setToolTipText("Polygon");
        polygonButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        polygonButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        polygonButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                polygonButtonActionPerformed(evt);
            }
        });

        roundedRec.setBackground(new java.awt.Color(51, 51, 51));
        roundedRec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/sdfd.jpg"))); // NOI18N
        roundedRec.setToolTipText("Rounded Rectangle");
        roundedRec.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        roundedRec.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        roundedRec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedRecActionPerformed(evt);
            }
        });

        pencilButton.setBackground(new java.awt.Color(51, 51, 51));
        pencilButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/pencil_32.png"))); // NOI18N
        pencilButton.setToolTipText("Pencil");
        pencilButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pencilButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pencilButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pencilButtonActionPerformed(evt);
            }
        });

        eraseButton.setBackground(new java.awt.Color(51, 51, 51));
        eraseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Draw-Eraser-32.png"))); // NOI18N
        eraseButton.setToolTipText("Erase");
        eraseButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        eraseButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        eraseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eraseButtonActionPerformed(evt);
            }
        });

        clearButton.setBackground(new java.awt.Color(51, 51, 51));
        clearButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Gnome-Edit-Clear-32.png"))); // NOI18N
        clearButton.setToolTipText("Clear");
        clearButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        clearButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        fullChk.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        fullChk.setForeground(new java.awt.Color(51, 51, 51));
        fullChk.setLabel("Full");
        fullChk.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                fullChkItemStateChanged(evt);
            }
        });
        fullChk.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                fullChkPropertyChange(evt);
            }
        });

        ForegroundButton.setBackground(new java.awt.Color(51, 51, 51));
        ForegroundButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Colors-32.png"))); // NOI18N
        ForegroundButton.setToolTipText("Foreground");
        ForegroundButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ForegroundButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ForegroundButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ForegroundButtonActionPerformed(evt);
            }
        });

        ForegroundLabel.setBackground(getForeGroundColor());
        ForegroundLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        ForegroundLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ForegroundLabel.setOpaque(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 13));
        jLabel1.setForeground(new java.awt.Color(204, 0, 0));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/colorize-48.png"))); // NOI18N
        jLabel1.setText("Mini Paint™");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Fill-32.png"))); // NOI18N
        jLabel2.setToolTipText("Fill Color\n");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        BackgroundButton.setBackground(new java.awt.Color(51, 51, 51));
        BackgroundButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/Color-Wheel-32.png"))); // NOI18N
        BackgroundButton.setToolTipText("Background");
        BackgroundButton.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BackgroundButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        BackgroundButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackgroundButtonActionPerformed(evt);
            }
        });

        BackgroundLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        BackgroundLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BackgroundLabel.setOpaque(true);

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, buttonPanelLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(undoBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(redoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(lineBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rectangleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ovalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(polygonButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundedRec, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pencilButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(eraseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(ForegroundButton, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ForegroundLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BackgroundButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BackgroundLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fullChk, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(262, 262, 262))
        );

        buttonPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {BackgroundButton, BackgroundLabel, ForegroundButton, ForegroundLabel, clearButton, eraseButton, jLabel2, lineBtn, ovalButton, pencilButton, polygonButton, rectangleButton, redoButton, roundedRec, undoBtn});

        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(eraseButton, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                    .addComponent(clearButton, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                    .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(lineBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rectangleButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ovalButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, Short.MAX_VALUE)
                        .addComponent(polygonButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, Short.MAX_VALUE)
                        .addComponent(roundedRec, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pencilButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(undoBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                        .addComponent(redoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(ForegroundButton, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                    .addComponent(ForegroundLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                    .addComponent(BackgroundLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BackgroundButton, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                    .addComponent(fullChk, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
                .addContainerGap())
        );

        buttonPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {BackgroundButton, ForegroundButton, clearButton, eraseButton, jLabel2, lineBtn, ovalButton, pencilButton, polygonButton, rectangleButton, redoButton, roundedRec});

        buttonPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {BackgroundLabel, ForegroundLabel});

        ColorButtonPanel.setBackground(new java.awt.Color(51, 51, 51));
        ColorButtonPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        ColorButtonPanel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        Position.setBackground(new java.awt.Color(51, 51, 51));
        Position.setFont(new java.awt.Font("Tahoma", 1, 14));
        Position.setForeground(new java.awt.Color(255, 255, 0));
        Position.setOpaque(true);
        Position.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                PositionAncestorMoved(evt);
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("Mini Paint © 2011. All rights reserved");

        javax.swing.GroupLayout ColorButtonPanelLayout = new javax.swing.GroupLayout(ColorButtonPanel);
        ColorButtonPanel.setLayout(ColorButtonPanelLayout);
        ColorButtonPanelLayout.setHorizontalGroup(
            ColorButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ColorButtonPanelLayout.createSequentialGroup()
                .addContainerGap(544, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(467, 467, 467)
                .addComponent(Position, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );
        ColorButtonPanelLayout.setVerticalGroup(
            ColorButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ColorButtonPanelLayout.createSequentialGroup()
                .addGroup(ColorButtonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(Position, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(buttonPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ColorButtonPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(buttonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 397, Short.MAX_VALUE)
                .addComponent(ColorButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

private void undoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoBtnActionPerformed
    // TODO add your handling code here:
    undo();
}//GEN-LAST:event_undoBtnActionPerformed

private void redoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redoButtonActionPerformed
    // TODO add your handling code here:
    if (isExistPolygonBuffer() != false) {
        flushPolygonBuffer();
    }
    redo();
}//GEN-LAST:event_redoButtonActionPerformed

private void lineBtnMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lineBtnMousePressed
// TODO add your handling code here:
}//GEN-LAST:event_lineBtnMousePressed

private void lineBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lineBtnActionPerformed
    // TODO add your handling code here:
    if (isExistPolygonBuffer() != false) {
        flushPolygonBuffer();
    }
    setDrawMode(LINE);
}//GEN-LAST:event_lineBtnActionPerformed

private void rectangleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rectangleButtonActionPerformed
    // TODO add your handling code here:
    if (isExistPolygonBuffer() != false) {
        flushPolygonBuffer();
    }
    setDrawMode(SQUARE);
}//GEN-LAST:event_rectangleButtonActionPerformed

private void ovalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ovalButtonActionPerformed
    // TODO add your handling code here:
    if (isExistPolygonBuffer() != false) {
        flushPolygonBuffer();
    }
    setDrawMode(OVAL);
}//GEN-LAST:event_ovalButtonActionPerformed

private void polygonButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_polygonButtonActionPerformed
    // TODO add your handling code here:
    if (isExistPolygonBuffer() != false) {
        flushPolygonBuffer();
    }
    setDrawMode(POLYGON);
}//GEN-LAST:event_polygonButtonActionPerformed

private void roundedRecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedRecActionPerformed
    // TODO add your handling code here:
    if (isExistPolygonBuffer() != false) {
        flushPolygonBuffer();
    }
    setDrawMode(ROUND_RECT);
}//GEN-LAST:event_roundedRecActionPerformed

private void pencilButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pencilButtonActionPerformed
    // TODO add your handling code here:
    if (isExistPolygonBuffer() != false) {
        flushPolygonBuffer();
    }
    setDrawMode(FREE_HAND);
}//GEN-LAST:event_pencilButtonActionPerformed

private void eraseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eraseButtonActionPerformed
    // TODO add your handling code here:
    if (isExistPolygonBuffer() != false) {
        flushPolygonBuffer();
    }
    setDrawMode(ERASE);
    //HelpPanel.gethelp(10);
}//GEN-LAST:event_eraseButtonActionPerformed

private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
    // TODO add your handling code here:
    clearCanvas();
}//GEN-LAST:event_clearButtonActionPerformed

private void fullChkItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_fullChkItemStateChanged
// TODO add your handling code here:

    if (fullChk.getState()) {
        setSolidMode(Boolean.TRUE);
    } else {
        setSolidMode(Boolean.FALSE);
    }
}//GEN-LAST:event_fullChkItemStateChanged

private void fullChkPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_fullChkPropertyChange
// TODO add your handling code here:
}//GEN-LAST:event_fullChkPropertyChange

private void buttonPanelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonPanelMouseEntered
// TODO add your handling code here:
    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    repaint();
}//GEN-LAST:event_buttonPanelMouseEntered

private void ForegroundButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ForegroundButtonActionPerformed
    // TODO add your handling code here:
    setForeGroundColor();

}//GEN-LAST:event_ForegroundButtonActionPerformed

private void BackgroundButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackgroundButtonActionPerformed
// TODO add your handling code here:
    setBackGroundColor();
}//GEN-LAST:event_BackgroundButtonActionPerformed

    /*----------------------------------------------------------------------------*/
private void PositionAncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_PositionAncestorMoved
// TODO add your handling code here:
}//GEN-LAST:event_PositionAncestorMoved

private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
// TODO add your handling code here:
    repaint();
    setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

}//GEN-LAST:event_formMouseEntered

private void formMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseExited
// TODO add your handling code here:
    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
}//GEN-LAST:event_formMouseExited

private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
// TODO add your handling code here:
    x2 = evt.getX();
    y2 = evt.getY();

    if (drawMode == this.FREE_HAND) {
        linex1 = linex2;
        liney1 = liney2;
        linex2 = x2;
        liney2 = y2;

        vFreeHand.add(new Coordinate(linex1, liney1, linex2, liney2, foreGroundColor));
        undoStack.push(new StepInfo(FREE_HAND, new Coordinate(linex1, liney1, linex2, liney2, foreGroundColor)));
    }
    if (drawMode == this.ERASE) {
        erasex1 = x2;
        erasey1 = y2;
        erasex2 = x2 + 12;
        erasey2 = y2 + 12;
        if (erasex1 > erasex2 || erasey1 > erasey2) {
            vErase.add(new Coordinate(erasex2, erasey2, erasex1, erasey1, this.getBackground()));
            undoStack.push(new StepInfo(ERASE, new Coordinate(erasex2, erasey2, erasex1, erasey1, this.getBackground())));
        } else {
            vErase.add(new Coordinate(erasex1, erasey1, erasex2, erasey2, this.getBackground()));
            undoStack.push(new StepInfo(ERASE, new Coordinate(erasex1, erasey1, erasex2, erasey2, this.getBackground())));
        }
    }
    repaint();
}//GEN-LAST:event_formMouseDragged

private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
// TODO add your handling code here:
    if (drawMode == LINE) {
        vLine.add(new Coordinate(x1, y1, evt.getX(), evt.getY(), foreGroundColor));
        undoStack.push(new StepInfo(LINE, new Coordinate(x1, y1, evt.getX(), evt.getY(), foreGroundColor)));
    }
    if (drawMode == SQUARE) {
        if (solidMode) {
            if (x1 > evt.getX() || y1 > evt.getY()) {
                vSolidSquare.add(new Coordinate(evt.getX(), evt.getY(), x1, y1, foreGroundColor));
                undoStack.push(new StepInfo(SOLID_SQUARE, new Coordinate(evt.getX(), evt.getY(), x1, y1, foreGroundColor)));
            } else {
                vSolidSquare.add(new Coordinate(x1, y1, evt.getX(), evt.getY(), foreGroundColor));
                undoStack.push(new StepInfo(SOLID_SQUARE, new Coordinate(x1, y1, evt.getX(), evt.getY(), foreGroundColor)));
            }
        } else {
            if (x1 > evt.getX() || y1 > evt.getY()) {
                vSquare.add(new Coordinate(evt.getX(), evt.getY(), x1, y1, foreGroundColor));
                undoStack.push(new StepInfo(SQUARE, new Coordinate(evt.getX(), evt.getY(), x1, y1, foreGroundColor)));
            } else {
                vSquare.add(new Coordinate(x1, y1, evt.getX(), evt.getY(), foreGroundColor));
                undoStack.push(new StepInfo(SQUARE, new Coordinate(x1, y1, evt.getX(), evt.getY(), foreGroundColor)));
            }
        }
    }
    if (drawMode == this.OVAL) {
        if (solidMode) {
            if (x1 > evt.getX() || y1 > evt.getY()) {
                vSolidOval.add(new Coordinate(evt.getX(), evt.getY(), x1, y1, foreGroundColor));
                undoStack.push(new StepInfo(SOLID_OVAL, new Coordinate(evt.getX(), evt.getY(), x1, y1, foreGroundColor)));
            } else {
                vSolidOval.add(new Coordinate(x1, y1, evt.getX(), evt.getY(), foreGroundColor));
                undoStack.push(new StepInfo(SOLID_OVAL, new Coordinate(x1, y1, evt.getX(), evt.getY(), foreGroundColor)));
            }
        } else {
            if (x1 > evt.getX() || y1 > evt.getY()) {
                vOval.add(new Coordinate(evt.getX(), evt.getY(), x1, y1, foreGroundColor));
                undoStack.push(new StepInfo(OVAL, new Coordinate(evt.getX(), evt.getY(), x1, y1, foreGroundColor)));
            } else {
                vOval.add(new Coordinate(x1, y1, evt.getX(), evt.getY(), foreGroundColor));
                undoStack.push(new StepInfo(OVAL, new Coordinate(x1, y1, evt.getX(), evt.getY(), foreGroundColor)));
            }
        }
    }
    if (drawMode == this.POLYGON || drawMode == this.SOLID_POLYGON) {
        xPolygon.add(new Integer(evt.getX()));
        yPolygon.add(new Integer(evt.getY()));
        polygonBuffer = true;
        repaint();
    }
    if (drawMode == this.ROUND_RECT) {
        if (solidMode) {
            if (x1 > evt.getX() || y1 > evt.getY()) {
                vSolidRoundRect.add(new Coordinate(evt.getX(), evt.getY(), x1, y1, foreGroundColor));
                undoStack.push(new StepInfo(SOLID_ROUND_RECT, new Coordinate(evt.getX(), evt.getY(), x1, y1, foreGroundColor)));
            } else {
                vSolidRoundRect.add(new Coordinate(x1, y1, evt.getX(), evt.getY(), foreGroundColor));
                undoStack.push(new StepInfo(SOLID_ROUND_RECT, new Coordinate(x1, y1, evt.getX(), evt.getY(), foreGroundColor)));
            }
        } else {
            if (x1 > evt.getX() || y1 > evt.getY()) {
                vRoundRect.add(new Coordinate(evt.getX(), evt.getY(), x1, y1, foreGroundColor));
                undoStack.push(new StepInfo(ROUND_RECT, new Coordinate(evt.getX(), evt.getY(), x1, y1, foreGroundColor)));
            } else {
                vRoundRect.add(new Coordinate(x1, y1, evt.getX(), evt.getY(), foreGroundColor));
                undoStack.push(new StepInfo(ROUND_RECT, new Coordinate(x1, y1, evt.getX(), evt.getY(), foreGroundColor)));
            }
        }
    }
    x1 = linex1 = x2 = linex2 = 0;
    y1 = liney1 = y2 = liney2 = 0;
}//GEN-LAST:event_formMouseReleased

private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
// TODO add your handling code here:
    x1 = linex1 = linex2 = erasex1 = erasex2 = evt.getX();
    y1 = liney1 = liney2 = erasey1 = erasey2 = evt.getY();
}//GEN-LAST:event_formMousePressed

    public void newMenuItem() {
if (isExistPolygonBuffer() != false) {
            flushPolygonBuffer();
        }
        vFreeHand.removeAllElements();
        vLine.removeAllElements();
        vOval.removeAllElements();
        vPolygon.removeAllElements();
        vRoundRect.removeAllElements();
        vSolidOval.removeAllElements();
        vSolidPolygon.removeAllElements();
        vSolidRoundRect.removeAllElements();
        vSolidSquare.removeAllElements();
        vSquare.removeAllElements();
        vErase.removeAllElements();
        undoStack.clear();
        redoStack.clear();
        foreGroundColor = Color.BLACK;
        backGroundColor = Color.WHITE;
        this.setBackGroundColor(backGroundColor);
        repaint();
    }

private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
// TODO add your handling code here:
    int xco = evt.getX();
    int yco = evt.getY();
    String position = String.format("                 %d,%d", xco, yco);
    Position.setText(position);
}//GEN-LAST:event_formMouseMoved

private void formComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentMoved
    // TODO add your handling code here:
}//GEN-LAST:event_formComponentMoved

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackgroundButton;
    private javax.swing.JLabel BackgroundLabel;
    private javax.swing.JPanel ColorButtonPanel;
    private javax.swing.JButton ForegroundButton;
    private javax.swing.JLabel ForegroundLabel;
    private javax.swing.JLabel Position;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton clearButton;
    private javax.swing.JButton eraseButton;
    private java.awt.Checkbox fullChk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton lineBtn;
    private javax.swing.JButton ovalButton;
    private javax.swing.JButton pencilButton;
    private javax.swing.JButton polygonButton;
    private javax.swing.JButton rectangleButton;
    private javax.swing.JButton redoButton;
    private javax.swing.JButton roundedRec;
    private javax.swing.JButton undoBtn;
    // End of variables declaration//GEN-END:variables
}
