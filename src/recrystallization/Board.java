/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recrystallization;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

/**
 *
 * @author Dendelion
 */
public class Board extends JPanel implements Runnable{
    
    private int size = 15;
    int id = 0;
    
    //int n;
        
    private Cell [][]tab = new Cell[size][size];
    private Cell [][]temp = new Cell[size][size];
    
    public Conditions cond;
        
        public Board(){
            
        cond = new Conditions();
        
        //int n;
        
//        switch (cond.getNeighbour()){
//            case 0:
//                n=8;
//                break;
//            case 1:
//                n=4;
//                break;
//            case 2:
//                n=5;
//                break;
//            case 3 | 4 | 5:
//                n=6;
//                break;
//            default:
//                n=8;
//                break;                     
//        }
//        
//        System.out.println("n "+n);
        
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                
                tab[i][j] = new Cell();
                tab[i][j].setID(0);
                          
                temp[i][j] = new Cell();
                temp[i][j].setID(0);
            }
        }
             
        MouseListener mouseHandler = new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e){
                
                int w = getWidth();
                int h = getHeight();
                              
                int cellW = w/size;
                int cellH = h/size;
                
                if(e.getX()>=0 && e.getY()>=0){
                    
                    int c = e.getX() / cellW;
                    int r = e.getY() / cellH;
                    
                    if(c>=0 && r>=0 && c<size && r<size){
                        //id++;
                    
                    /*-------Generating structures-----------*/
                        if(tab[r][c].getID()==0){
                            id++;
                            tab[r][c].setID(id);
                            System.out.println("id "+tab[r][c].getID());
                            tab[r][c].drawColor();
                            }
                            else{
                                tab[r][c].setID(0);
                                tab[r][c].resetColor();
                        }
                        
                                  
                    }
                }                
                repaint();      
            }
        };
        addMouseListener(mouseHandler);
    }
   
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        int w = getWidth();
        int h = getHeight();
                              
        int cellW = w/size;
        int cellH = h/size;
        
        int x = (w - size * cellW)/2;
        int y = (h - size * cellH)/2;
                
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){

                if(tab[i][j].getID()==0){
                    g2d.setColor(tab[i][j].getColor());
                }else{
                    g2d.setColor(tab[i][j].getColor());
                }
                
                Rectangle cell = new Rectangle(x + j*cellW,
                            y + i*cellH,
                            cellW-1,
                            cellH-1);
                g2d.fill(cell);

            }
        }     
    }
    
    @Override
    public void run() {
        
        System.out.println("Running ...");
        while(true){
            if(cond.getStatus()==1){
                //System.out.println("nbhd "+cond.getNeighbour());
                this.action();
                 repaint();
            }
            if(cond.getStatus()==2){
                this.clean();
                repaint();
            }
            try{
                Thread.sleep(1000);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public void clean(){
        
        id=0;
        
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){

                tab[i][j] = new Cell();
                tab[i][j].setID(0);
                tab[i][j].resetColor();
                
                temp[i][j] = new Cell();
                temp[i][j].setID(0);
                temp[i][j].resetColor();

            }
        }
        cond.setStatus(0);
    }
    
    public void action(){
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){       
                int nbhdIndex = 0; 
                if(tab[i][j].getID()==0){
                    switch (cond.getNeighbour()){
                        case 0: //--------------------MOOR
                            switch (cond.getBC()){
                                case 0: //----------------NON PERIODIC BC
                                    if((i==0 || i==size-1) && (j==0 || j==size-1)){
                                        tab[i][j].setNBHD(0, 0);
                                        tab[i][j].setNBHD(1, 0);
                                        tab[i][j].setNBHD(2, 0); 
                                        tab[i][j].setNBHD(3, 0);
                                        tab[i][j].setNBHD(4, 0);//corner cell
                                        nbhdIndex = 5;  
                                    }else if(i==0 || i==size-1 || j==0 || j==size-1){
                                        tab[i][j].setNBHD(0, 0);
                                        tab[i][j].setNBHD(1, 0);
                                        tab[i][j].setNBHD(2, 0);   //on the edge
                                        nbhdIndex = 3;
                                    }
                        
                                    /*-------CHECKING NEIGHBOURHOOD--------------*/           
                                    for(int k=i-1; k<i+2; k++){ //-----------------from 1 up to 1 low (3 iterations) in rows
                                        for(int l=j-1; l<j+2; l++){ //-------------from 1 up to 1 low (3 iterations) in columns
                                            if((k==i && l==j) || k<0 || k>=size || l<0 || l>=size)
                                                System.out.println("zapierdalaj ");
                                            else{
                                                System.out.println("nbhd "+nbhdIndex);
                                                tab[i][j].setNBHD(nbhdIndex, tab[k][l].getID());
                                                nbhdIndex++;
                                            }
                                        }
                                    } 
                                    tab[i][j].showNBHD();
                                    temp[i][j].setID(tab[i][j].chooseSeed());
                                
                                    System.out.println("cell: [i][j] "+i+" "+j);
                                    tab[i][j].showNBHD();
                                    break;
        
                                case 1: //----------------PERIODIC BC
                                    for(int k=i-1; k<i+2; k++){
                                        for(int l=j-1; l<j+2; l++){
                                            int kk, ll;
                                            
                                            if(k<0)
                                                kk=size-1;
                                            else if(k>=size)
                                                kk=0;
                                            else
                                                kk=k;
                                            
                                            if(l<0)
                                                ll=size-1;
                                            else if(l>=size)
                                                ll=0;
                                            else 
                                                ll=l;
                                            
                                            if(k==i && l==j)
                                                System.out.println("don't check itself");
                                            else{
                                                tab[i][j].setNBHD(nbhdIndex, tab[kk][ll].getID());
                                                nbhdIndex++;
                                            }
                                        }
                                    }
                                    temp[i][j].setID(tab[i][j].chooseSeed());
                                    break;
                            }
                            break;
                        case 1: //--------------------VON NEUMAN
                            System.out.println("neigh "+cond.getNeighbour());
                            switch (cond.getBC()){
                                case 0:
                                    if((i==0 || i==size-1) && (j==0 || j==size-1)){
                                        tab[i][j].setNBHD(0, 0);
                                        tab[i][j].setNBHD(1, 0);//corner cell
                                        nbhdIndex = 2;  
                                    }else if(i==0 || i==size-1 || j==0 || j==size-1){
                                        tab[i][j].setNBHD(0, 0);   //on the edge
                                        nbhdIndex = 1;
                                    }
                                    for(int k=i-1; k<i+2; k++){ //-----------------from 1 up to 1 low (3 iterations) in rows
                                        for(int l=j-1; l<j+2; l++){ //-------------from 1 up to 1 low (3 iterations) in columns
                                            if((k!=i && l!=j) || (k==i && l==j) || k<0 || k>=size || l<0 || l>=size)
                                                System.out.println("elo ");
                                            else{
                                                System.out.println("nbhd "+nbhdIndex);
                                                tab[i][j].setNBHD(nbhdIndex, tab[k][l].getID());
                                                nbhdIndex++;
                                            }
                                        }
                                    }
                                    tab[i][j].showNBHD();
                                    temp[i][j].setID(tab[i][j].chooseSeed());
                                
                                    System.out.println("cell: [i][j] "+i+" "+j);
                                    tab[i][j].showNBHD();
                                    break;
                                case 1:
                                    for(int k=i-1; k<i+2; k++){
                                        for(int l=j-1; l<j+2; l++){
                                            int kk, ll;
                                            
                                            if(k<0)
                                                kk=size-1;
                                            else if(k>=size)
                                                kk=0;
                                            else
                                                kk=k;
                                            
                                            if(l<0)
                                                ll=size-1;
                                            else if(l>=size)
                                                ll=0;
                                            else 
                                                ll=l;
                                            
                                            if((k==i && l==j) || (k!=i && l!=j))
                                                System.out.println("don't check itself");
                                            else{
                                                tab[i][j].setNBHD(nbhdIndex, tab[kk][ll].getID());
                                                nbhdIndex++;
                                            }
                                        }
                                    }
                                    temp[i][j].setID(tab[i][j].chooseSeed());
                                    break;
                            }
                            break;
                        case 2: //--------------------PENTAGONAL + random for up, down, left, right
                            switch (cond.getBC()){
                                case 0:
                                    break;
                                case 1:
                                    break;        
                            }
                        case 3: //--------------------HEXANGONAL left
                            switch (cond.getBC()){
                                case 0:
                                    if((i==0 && j==0) || (i==size-1 && j==size-1)){ //corners: up-left and down-right
                                        tab[i][j].setNBHD(0, 0);
                                        tab[i][j].setNBHD(1, 0);
                                        tab[i][j].setNBHD(2, 0);    //3 neighbours
                                        nbhdIndex = 3;  
                                    }else if((i==0 && j==size-1) || (i==size-1 && j==0) ){   //corners: up-right and down-left 
                                        tab[i][j].setNBHD(0, 0);  
                                        tab[i][j].setNBHD(1, 0);
                                        tab[i][j].setNBHD(2, 0);  
                                        tab[i][j].setNBHD(3, 0);
                                        nbhdIndex = 4;              // 4 neighbours
                                    }else if(i==0 || i==size-1 || j==0 || j==size-1){
                                        tab[i][j].setNBHD(0, 0);   //on the edge
                                        tab[i][j].setNBHD(1, 0);
                                        nbhdIndex = 2;
                                    }
                                    for(int k=i-1; k<i+2; k++){ //-----------------from 1 up to 1 low (3 iterations) in rows
                                        for(int l=j-1; l<j+2; l++){ //-------------from 1 up to 1 low (3 iterations) in columns
                                            if((k==i && l==j) || k<0 || k>=size || l<0 || l>=size || (k==i-1 && l==j+1) || (k==i+1 && l==j-1))
                                                System.out.println("zapierdalaj ");
                                            else{
                                                System.out.println("nbhd "+nbhdIndex);
                                                tab[i][j].setNBHD(nbhdIndex, tab[k][l].getID());
                                                nbhdIndex++;
                                            }
                                        }
                                    } 
                                    tab[i][j].showNBHD();
                                    temp[i][j].setID(tab[i][j].chooseSeed());
                                
                                    System.out.println("cell: [i][j] "+i+" "+j);
                                    tab[i][j].showNBHD();
                                    break;
                                case 1:
                                    for(int k=i-1; k<i+2; k++){
                                        for(int l=j-1; l<j+2; l++){
                                            int kk, ll;
                                            
                                            if(k<0)
                                                kk=size-1;
                                            else if(k>=size)
                                                kk=0;
                                            else
                                                kk=k;
                                            
                                            if(l<0)
                                                ll=size-1;
                                            else if(l>=size)
                                                ll=0;
                                            else 
                                                ll=l;
                                            
                                            if((k==i && l==j) || (k==i-1 && l==j+1) || (k==i+1 && l==j-1))
                                                System.out.println("don't check itself");
                                            else{
                                                tab[i][j].setNBHD(nbhdIndex, tab[kk][ll].getID());
                                                nbhdIndex++;
                                            }
                                        }
                                    }
                                    temp[i][j].setID(tab[i][j].chooseSeed());
                                    break;        
                            }
                        case 4: //--------------------HEXAGONAL right
                            switch (cond.getBC()){
                                case 0:
                                    if((i==0 && j==0) || (i==size-1 && j==size-1)){ //corners: up-left and down-right
                                        tab[i][j].setNBHD(0, 0);
                                        tab[i][j].setNBHD(1, 0);
                                        tab[i][j].setNBHD(2, 0);
                                        tab[i][j].setNBHD(3, 0);
                                        nbhdIndex = 4;//4 neighbours
                                    }else if((i==0 && j==size-1) || (i==size-1 && j==0) ){   //corners: up-right and down-left 
                                        tab[i][j].setNBHD(0, 0);  
                                        tab[i][j].setNBHD(1, 0);
                                        tab[i][j].setNBHD(2, 0); 
                                        nbhdIndex = 3;// 3 neighbours
                                    }else if(i==0 || i==size-1 || j==0 || j==size-1){
                                        tab[i][j].setNBHD(0, 0);   //on the edge
                                        tab[i][j].setNBHD(1, 0);
                                        nbhdIndex = 2;
                                    }
                                    for(int k=i-1; k<i+2; k++){ //-----------------from 1 up to 1 low (3 iterations) in rows
                                        for(int l=j-1; l<j+2; l++){ //-------------from 1 up to 1 low (3 iterations) in columns
                                            if((k==i && l==j) || k<0 || k>=size || l<0 || l>=size || (k==i-1 && l==j-1) || (k==i+1 && l==j+1))
                                                System.out.println("zapierdalaj ");
                                            else{
                                                System.out.println("nbhd "+nbhdIndex);
                                                tab[i][j].setNBHD(nbhdIndex, tab[k][l].getID());
                                                nbhdIndex++;
                                            }
                                        }
                                    } 
                                    tab[i][j].showNBHD();
                                    temp[i][j].setID(tab[i][j].chooseSeed());
                                
                                    System.out.println("cell: [i][j] "+i+" "+j);
                                    tab[i][j].showNBHD();
                                    break;
                                case 1:
                                    for(int k=i-1; k<i+2; k++){
                                        for(int l=j-1; l<j+2; l++){
                                            int kk, ll;
                                            
                                            if(k<0)
                                                kk=size-1;
                                            else if(k>=size)
                                                kk=0;
                                            else
                                                kk=k;
                                            
                                            if(l<0)
                                                ll=size-1;
                                            else if(l>=size)
                                                ll=0;
                                            else 
                                                ll=l;
                                            
                                            if((k==i && l==j) || (k==i-1 && l==j-1) || (k==i+1 && l==j+1))
                                                System.out.println("don't check itself");
                                            else{
                                                tab[i][j].setNBHD(nbhdIndex, tab[kk][ll].getID());
                                                nbhdIndex++;
                                            }
                                        }
                                    }
                                    temp[i][j].setID(tab[i][j].chooseSeed());
                                    break;
                        case 5: //--------------------HEXAGONAL random (left or right)
                            //chuj wie co tu trzeba zrobić
                            
                    }
                }  
            }
        }
        }
        for(int i=0; i<size; i++){
            for(int j=0; j<size; j++){
                for(int k=0; k<size; k++){
                    for(int l=0; l<size; l++){
                        if(tab[i][j].getID()==0 && temp[i][j].getID()==tab[k][l].getID()){
                            tab[i][j].setID(temp[i][j].getID());
                            tab[i][j].setColor(tab[k][l].getColor());
                            tab[i][j].cleanNBHD();
                            temp[i][j].setID(0);
                        }
                    }
                }
            }
        }
    }
    

}
    