/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recrystallization;

import java.awt.EventQueue;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Dendelion
 */
public class Recrystallization {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        new Recrystallization();
    }
    
    public Recrystallization(){
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {                            
            }
            
            MyFrame mf = new MyFrame();
            mf.setVisible(true);
        });
    }
}
        
    
    

