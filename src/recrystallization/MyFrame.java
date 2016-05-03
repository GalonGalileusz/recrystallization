/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recrystallization;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Dendelion
 */
public class MyFrame extends JFrame{
    
    private String[] bcs = new String[]{"Non-periodic", "Periodic"};
    private String[] neighbours = new String[]{"Moor", "Von Neuman", "Pentagonal (random)", "Hexagonal (left)", "Hexagonal (right)", "Hexagonal (random)"};
    private String[] locations = new String[]{"Own", "Ranodm", "Evenly", "With ray"};
    private JPanel contentPane;
    private Board board;
    private Conditions c;
    
    public MyFrame(){
        
        board = new Board();
        c = board.cond;
        
        setTitle("Recrystallization");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());      
        setSize(this.getPrefferredSize());
        
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        
        setContentPane(contentPane);
        
        JPanel panel = new JPanel();
	contentPane.add(panel, BorderLayout.NORTH);
        
        JLabel amountLabel = new JLabel ("Number of grains ");
        JTextArea amountArea = new JTextArea("0");
        panel.add(amountLabel);
        panel.add(amountArea);
        
        amountArea.addInputMethodListener(new InputMethodListener() {

            @Override
            public void inputMethodTextChanged(InputMethodEvent e) {
                c.setAmount(Integer.valueOf(amountArea.getText()));
            }

            @Override
            public void caretPositionChanged(InputMethodEvent e) {
                
            }           
        });
  
        JLabel bcLabel = new JLabel("BC ");
        panel.add(bcLabel);
        
        JComboBox bcCombo = new JComboBox();
        bcCombo.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                c.setBC(bcCombo.getSelectedIndex());
            }
        });
        
        bcCombo.setModel(new DefaultComboBoxModel(this.bcs));
        panel.add(bcCombo);
        
        JLabel nbhdLabel = new JLabel("Nbhd ");
        panel.add(nbhdLabel);
        
        JComboBox nbhdCombo = new JComboBox();
        nbhdCombo.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                c.setNeighbour(nbhdCombo.getSelectedIndex());
            }
        });
        
        nbhdCombo.setModel(new DefaultComboBoxModel(this.neighbours));
        panel.add(nbhdCombo);
                
        JLabel locationLabel = new JLabel("Location ");
        panel.add(locationLabel);
        
        JComboBox locationCombo = new JComboBox();
        locationCombo.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                c.setLocation(locationCombo.getSelectedIndex());
            }
        });

        locationCombo.setModel(new DefaultComboBoxModel(this.locations));
        panel.add(locationCombo);
        
        JLabel rayLabel = new JLabel ("Ray ");
        JTextArea rayArea = new JTextArea("0");
        panel.add(rayLabel);
        panel.add(rayArea);
        
        rayArea.addInputMethodListener(new InputMethodListener() {

            @Override
            public void inputMethodTextChanged(InputMethodEvent e) {
                c.setRay(Integer.valueOf(rayArea.getText()));
            }

            @Override
            public void caretPositionChanged(InputMethodEvent e) {
                
            }           
        });
        
        JButton startButton = new JButton("Start");
        startButton.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e){
                c.setStatus(1);
            }
        });
        
        JButton stopButton = new JButton("Stop");
        stopButton.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e){
                c.setStatus(0);
            }
        });
        
        JButton cleanButton = new JButton("Clean");
        cleanButton.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e){
                c.setStatus(2); 
            }
        });
        
        panel.add(startButton);
        panel.add(stopButton);
        panel.add(cleanButton);
        
        
        contentPane.add(board, BorderLayout.CENTER);
        
        new Thread(board).start();
        
        
    } 
    
    public Dimension getPrefferredSize(){
        return new Dimension(800, 800);
    }
    

}
