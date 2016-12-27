/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package minipaint;

import GUI.CanvasPanel;
import GUI.HomeFrame;
import java.awt.BorderLayout;

/**
 *
 * @author Dell
 */
public class MiniPaint {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        HomeFrame H = new HomeFrame("Untitled-Paint");
        CanvasPanel c=new CanvasPanel();
        //c.OpenCanvasFile();
        H.setContentPane(c);
        H.setVisible(true);
    }
}
