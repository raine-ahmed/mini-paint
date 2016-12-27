/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * HomeFrame.java
 *
 * Created on Sep 11, 2011, 11:21:07 PM
 */
package GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import minipaint.Coordinate;
import minipaint.StepInfo;

/**
 *
 * @author Dell
 */
public class HomeFrame extends javax.swing.JFrame {

   private CanvasPanel canvas;
    //String title;

    /** Creates new form HomeFrame */
    public HomeFrame(String title) {
        super(title);
        canvas = new CanvasPanel();
        initComponents();
    }

    public void setsuper(String title) {
        this.setTitle(title);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BackgroundLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        NewMenuItem1 = new javax.swing.JMenuItem();
        OpenMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        AboutMenuItem = new javax.swing.JMenuItem();
        HelpMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setBounds(new java.awt.Rectangle(270, 130, 0, 0));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowDeiconified(java.awt.event.WindowEvent evt) {
                formWindowDeiconified(evt);
            }
        });

        BackgroundLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        BackgroundLabel.setOpaque(true);

        jMenuBar1.setBackground(new java.awt.Color(51, 51, 51));
        jMenuBar1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 0, 0)));
        jMenuBar1.setForeground(new java.awt.Color(255, 255, 255));

        jMenu1.setForeground(new java.awt.Color(255, 255, 255));
        jMenu1.setMnemonic('F');
        jMenu1.setText("File");

        NewMenuItem1.setText("New");
        NewMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(NewMenuItem1);

        OpenMenuItem.setText("Open");
        OpenMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(OpenMenuItem);

        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenuItem.setText("Save");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(saveMenuItem);

        saveAsMenuItem.setText("Save As");
        saveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(saveAsMenuItem);

        jMenuItem6.setText("Exit");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem6);

        jMenuBar1.add(jMenu1);

        jMenu3.setForeground(new java.awt.Color(255, 255, 255));
        jMenu3.setMnemonic('H');
        jMenu3.setText("Help");

        AboutMenuItem.setText("About");
        AboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AboutMenuItemActionPerformed(evt);
            }
        });
        jMenu3.add(AboutMenuItem);

        HelpMenuItem.setText("Help Topic");
        HelpMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HelpMenuItemActionPerformed(evt);
            }
        });
        jMenu3.add(HelpMenuItem);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(332, 332, 332)
                .addComponent(BackgroundLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(435, 435, 435))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(356, 356, 356)
                .addComponent(BackgroundLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(92, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void HelpMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HelpMenuItemActionPerformed
// TODO add your handling code here:
//    JOptionPane.showMessageDialog(HomeFrame.this, "Please contact the authors for help", "Mini Paint", JOptionPane.INFORMATION_MESSAGE);
//    HelpPanel hp = new HelpPanel();
//    hp.setVisible(true);
    // JOptionPane.showMessageDialog(HomeFrame.this, "*************************Mini Paint***************************\n###Features:\n\n1.Free Hand Drawing\n\n2.Basic Shapes:\n    Line Drawing\n    Rectangle Drawing\n    Oval Drawing\n    Rounded Rectangle Drawing\n    Polygon Drawing\n\n3.Editing \n    Undo\n    Redo\n    Erase\n    Clear Canvas\n\n4.Color Options:\n    Foreground Color setting\n    Background Color setting\n    Fill Color \n\n5.Saving Options:\n    Saving Drawn Image\n    Opening Saved Image\n\n6.Cursor Position\n\n\n###Description:\n\n-->Free Hand Drawing: \n   Its like a pencil tool. Press the mouse button and drag free hand to draw any type of shapes. Release the mouse button when drawing is finished.\n\n-->Line Drawing:\n   Tool for drawing a line. Select the starting point by pressing the mouse button, drag the mouse and to select the ending point release the mouse button.\n\n-->Rectangle Drawing:\n   Press the mouse button drag and release the button to draw a rectangle. Two types of rectangle are valid. To draw a fill rectangle tick the Fill color checkbox.\n\n-->Oval Drawing:\n   Press the mouse button , drag and release to draw an oval shape. Fill oval shape is available.\n\n-->Rounded Rectangle:\n    Press the mouse button , drag and release to draw an rounded rectangle. Fill rounded rectangle is available.\n\n-->Polygon Drawing:\n   Press the mouse button to select the 1st point of a polygon. then press again to set another node of a polygon. Press n times for a n noded polygon. Fill polygon is    available.\n\n--> Undo & Redo:\n   Press the button for undo or redo operation\n\n-->Erase:\n   To erase a certain part of an object, press hold and drag the cursor throug the part to be erased.\n\n-->Clear: \nPress clear button to clear the canvas.\n\n-->Save:\n   Press save, type a name and press ok to save a drawn image.\n\n-->Open:\n   To view a saved image, press open and select the file, click open.\n\n********************************************************************************************************************************\n\nFor furthur query Contact the authors.\nAuthors:\n\nMd.Faishal Yousuf\nH.M. Raine Ahmed \nFarhat Aman", "Mini Paint", JOptionPane.INFORMATION_MESSAGE);
    //this.setVisible(false);
    HelpFrame hf = new HelpFrame();
    hf.setVisible(true);
    
    canvas.repaint();
}//GEN-LAST:event_HelpMenuItemActionPerformed

private void AboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AboutMenuItemActionPerformed
// TODO add your handling code here:
    JOptionPane.showMessageDialog(HomeFrame.this, "Name : Mini Paint\nVersion : 1.0\nAuthors:\n Faishal Yousuf   (094448)\n Raine Ahmed     (094446)\n Farhat Aman      (094445)\n\n\n\n Islamic University of Technology      ", "Mini Paint", JOptionPane.INFORMATION_MESSAGE);
    canvas.repaint();
}//GEN-LAST:event_AboutMenuItemActionPerformed

private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
// TODO add your handling code here:
    System.exit(0);
}//GEN-LAST:event_jMenuItem6ActionPerformed

private void NewMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewMenuItem1ActionPerformed
// TODO add your handling code here:
    //if (canvas.undoStack.size() == 0) {
    canvas.newMenuItem();
    setTitle("Untitled-Paint");
//    canvas.setForeGroundColor(Color.BLACK);
//    canvas.setBackGroundColor(Color.WHITE);
//    canvas.clearCanvas();
//    canvas.setDrawMode(0);  
//    canvas.repaint();
//    } else if (canvas.undoStack.size() != 0) {
//
//        int i = JOptionPane.showConfirmDialog(null, "save changes to untitled?");
//        if (i == JOptionPane.YES_OPTION) {
//            canvas.SaveCanvasToFile();
//            canvas.clearCanvas();
//            canvas.setDrawMode(0);
//            canvas.setForeGroundColor(Color.BLACK);
//            canvas.setBackGroundColor(Color.WHITE);
//            canvas.repaint();
//
//        } else if (i == JOptionPane.NO_OPTION) {
//
//            canvas.clearCanvas();
//            canvas.setDrawMode(0);
//            canvas.setForeGroundColor(Color.BLACK);
//            canvas.setBackGroundColor(Color.WHITE);
//            canvas.repaint();
//
//        }


//    }




}//GEN-LAST:event_NewMenuItem1ActionPerformed

private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
// TODO add your handling code here:
    String t = canvas.SaveCanvasToFile();
    if (t == null) {
        setTitle("Untitled-Paint");
    } else {
        setTitle(t + "-Paint");
    }
}//GEN-LAST:event_saveMenuItemActionPerformed

private void saveAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsMenuItemActionPerformed
// TODO add your handling code here:
    String t = canvas.SaveAsCanvasToFile();
    if (t == null) {
        setTitle("Untitled-Paint");
    } else {
        setTitle(t + "-Paint");
    }
}//GEN-LAST:event_saveAsMenuItemActionPerformed

private void OpenMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenMenuItemActionPerformed
// TODO add your handling code here:

    // setTitle(canvas.OpenCanvasFile()+"Paint");  
    String t = canvas.OpenCanvasFile();
    if (t == null) {
        setTitle("Untitled-Paint");
    } else {
        setTitle(t + "-Paint");
    }

}//GEN-LAST:event_OpenMenuItemActionPerformed

private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
    //TODO add your handling code here:
//    if (canvas.undoStack.size() == 0) {
//        System.exit(1);
//    } else if (canvas.undoStack.size() != 0) {
        int i = JOptionPane.showConfirmDialog(null, "save changes ?");
        if (i == JOptionPane.YES_OPTION) {
            canvas.SaveCanvasToFile();
            canvas.setDrawMode(0);
            canvas.setForeGroundColor(Color.BLACK);
            canvas.setBackGroundColor(Color.WHITE);
            canvas.repaint();
            System.exit(1);

        } else if (i == JOptionPane.NO_OPTION) {
            System.exit(1);
        }
    
}//GEN-LAST:event_formWindowClosing

private void formWindowDeiconified(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowDeiconified
// TODO add your handling code here:
    canvas.repaint();
}//GEN-LAST:event_formWindowDeiconified

private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
// TODO add your handling code here:
    canvas.repaint();
}//GEN-LAST:event_formWindowActivated
    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        java.awt.EventQueue.invokeLater(new Runnable() {
//
//            public void run() {
//                new HomeFrame().setVisible(true);
//            }
//        });
//    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AboutMenuItem;
    private javax.swing.JLabel BackgroundLabel;
    private javax.swing.JMenuItem HelpMenuItem;
    private javax.swing.JMenuItem NewMenuItem1;
    private javax.swing.JMenuItem OpenMenuItem;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    // End of variables declaration//GEN-END:variables
}
