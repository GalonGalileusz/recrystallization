/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recrystallization;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.JPanel;

/**
 *
 * @author Dendelion
 */
public class Board extends JPanel implements Runnable{
    
    private int size = 120;
    private int free = size*size;
    private int id = 0;
    
    Random rand = new Random();
    int pent;    //0-up  1-down  2-left  3-right
    int hex ;  //0-left 1-right
    

    private Cell [][]tab = new Cell[size][size];
    private final Cell [][]temp = new Cell[size][size];
    
    public Conditions cond;
        
        public Board(){
        
        hex = rand.nextInt(2); 
        pent = rand.nextInt(4);
        
            System.out.println("random hex + pent "+hex+ " "+ pent);
            
        cond = new Conditions();
        
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
                    
                    /*-------Generating structures-----------*/
                        switch (cond.getLocation()){
                            case 0: //own
                                if(tab[r][c].getID()==0 && tab[r][c].getInRay()==0){
                                    id++;
                                    tab[r][c].setID(id);
                                    tab[r][c].drawColor();
                                }else{
                                    tab[r][c].setID(0);
                                    tab[r][c].resetColor();
                                }
                                break;
                            case 1: //random
                                
                                for(int i=0; i<cond.getAmount(); ){
                                    
                                    if(cond.getAmount()>=free)
                                        break;
                                    
                                    Random ri = new Random();
                                    
                                    int a=ri.nextInt(size);
                                    int b=ri.nextInt(size);
                                    
                                    if(tab[a][b].getID()==0){
                                        tab[a][b].setID(id);
                                        tab[a][b].drawColor();
                                        id++;
                                        i++;
                                        free--;
                                    }
                                }
                                break;
                            case 2: //evenly
                                int grains = cond.getAmount();
                                int half = size/2;
                                if(grains<= Math.pow(half, 2)){
                                    if(grains/half>=1){ // fill all line
                                        for(int i=1; i<size && grains>0; i+=2){
                                            for(int j=1; j<size && grains>0; j+=2){
                                                tab[i][j].setID(id);
                                                tab[i][j].drawColor();
                                                id++;
                                                grains--;
                                                    //chuj
                                            }
                                        } 
                                    }
                                }   
                                break;
                            case 3: //with ray
                                
                                int ray = cond.getRay();
                                
                                for(int i=0; i<cond.getAmount(); ){
                                    
                                    System.out.println(i);
                                    
                                    if(cond.getAmount()>=free)
                                        break;
                                    
                                    Random ri = new Random();
                                    
                                    int a=ri.nextInt(size);
                                    int b=ri.nextInt(size);
                                    
                                    if(tab[a][b].getID()==0 && tab[a][b].getInRay()==0){
                                        tab[a][b].setID(id);
                                        tab[a][b].drawColor();
                                        id++;
                                        i++;
                                        
                                        for(int m=a-ray; m<=a+ray; m++){
                                            for(int n=b-ray; n<=b+ray; n++){
                                                if(m<0 || m>=size)
                                                    continue;
                                            
                                                if(n<0 || n>=size)
                                                    continue;
                                            
                                                if(tab[m][n].getInRay()==0){
                                                    tab[m][n].setInRay();
                                                    free--;
                                                }
                                            }
                                        }
                                    }  
                                }
                                break;
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
                            cellW,
                            cellH);
                g2d.fill(cell);
            }
        }     
    }
    
    @Override
    public void run() {
        
        System.out.println("Running ...");
        while(true){
            if(cond.getStatus()==1){
                this.action();
                 repaint();
            }
            if(cond.getStatus()==2){
                this.clean();
                repaint();
            }
            try{
                Thread.sleep(100);
            }catch (Exception ex){
            }
        }
    }

    public void clean(){
        
        free = size*size;
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
                                                ;//System.out.println("go ");
                                            else{
                                                tab[i][j].setNBHD(nbhdIndex, tab[k][l].getID());
                                                nbhdIndex++;
                                            }
                                        }
                                    } 
                                    temp[i][j].setID(tab[i][j].chooseSeed());
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
                                                ;//System.out.println("don't check itself");
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
                                                ;//System.out.println("elo ");
                                            else{
                                                tab[i][j].setNBHD(nbhdIndex, tab[k][l].getID());
                                                nbhdIndex++;
                                            }
                                        }
                                    }
                                    temp[i][j].setID(tab[i][j].chooseSeed());
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
                                                ;//System.out.println("don't check itself");
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
                                    if(i==0 && j==0){   //up-left corner
                                        if(pent==0 || pent==2){
                                            tab[i][j].setNBHD(0, 0);
                                            tab[i][j].setNBHD(1, 0);
                                            tab[i][j].setNBHD(2, 0);
                                            tab[i][j].setNBHD(3, 0);
                                            nbhdIndex = 4; 
                                        }else{
                                            tab[i][j].setNBHD(0, 0);
                                            tab[i][j].setNBHD(1, 0);
                                            nbhdIndex = 2; 
                                        }       
                                    }else if(i==0 && j==size-1){    //up-right corner
                                        if(pent==0 || pent==3){
                                            tab[i][j].setNBHD(0, 0);
                                            tab[i][j].setNBHD(1, 0);
                                            tab[i][j].setNBHD(2, 0);
                                            tab[i][j].setNBHD(3, 0);
                                            nbhdIndex = 4;
                                        }else{
                                            tab[i][j].setNBHD(0, 0);
                                            tab[i][j].setNBHD(1, 0);
                                            nbhdIndex = 2; 
                                        }
                                    }else if(i==size-1 && j==0){    //down-left corner
                                        if(pent==0 || pent==3){
                                            tab[i][j].setNBHD(0, 0);
                                            tab[i][j].setNBHD(1, 0);
                                            nbhdIndex = 2; 
                                        }else{
                                            tab[i][j].setNBHD(0, 0);
                                            tab[i][j].setNBHD(1, 0);
                                            tab[i][j].setNBHD(2, 0);
                                            tab[i][j].setNBHD(3, 0);
                                            nbhdIndex = 4;
                                        }  
                                    }else if(i==size-1 && j==size-1){   //down-right corner
                                        if(pent==0 || pent==2){
                                            tab[i][j].setNBHD(0, 0);
                                            tab[i][j].setNBHD(1, 0);
                                            nbhdIndex = 2;
                                        }else{
                                            tab[i][j].setNBHD(0, 0);
                                            tab[i][j].setNBHD(1, 0);
                                            tab[i][j].setNBHD(2, 0);
                                            tab[i][j].setNBHD(3, 0);
                                            nbhdIndex = 4;
                                        }
                                    }else if(i==0){ //up edge
                                        if(pent==0){
                                            tab[i][j].setNBHD(0, 0);
                                            tab[i][j].setNBHD(1, 0);
                                            tab[i][j].setNBHD(2, 0);
                                            nbhdIndex = 3;
                                        }else if(pent==2 || pent==3){
                                            tab[i][j].setNBHD(0, 0);
                                            tab[i][j].setNBHD(1, 0);
                                            nbhdIndex = 2;
                                        }
                                    }else if(i==size-1){ //down edge
                                        if(pent==1){
                                            tab[i][j].setNBHD(0, 0);
                                            tab[i][j].setNBHD(1, 0);
                                            tab[i][j].setNBHD(2, 0);
                                            nbhdIndex = 3;
                                        }else if(pent==2 || pent==3){
                                            tab[i][j].setNBHD(0, 0);
                                            tab[i][j].setNBHD(1, 0);
                                            nbhdIndex = 2;
                                        }  
                                    }else if(j==0){     //left edge
                                        if(pent==0 || pent==1){
                                            tab[i][j].setNBHD(0, 0);
                                            tab[i][j].setNBHD(1, 0);
                                            nbhdIndex = 2;
                                        }else if(pent==2){
                                            tab[i][j].setNBHD(0, 0);
                                            tab[i][j].setNBHD(1, 0);
                                            tab[i][j].setNBHD(2, 0);
                                            nbhdIndex = 3;
                                        }
                                    }else if(j==size-1){    //right edge
                                        if(pent==0 || pent==1){
                                            tab[i][j].setNBHD(0, 0);
                                            tab[i][j].setNBHD(1, 0);
                                            nbhdIndex = 2;
                                        }else if(pent==3){
                                            tab[i][j].setNBHD(0, 0);
                                            tab[i][j].setNBHD(1, 0);
                                            tab[i][j].setNBHD(2, 0);
                                            nbhdIndex = 3;
                                        }
                                    }
                                    for(int k=i-1; k<i+2; k++){ //-----------------from 1 up to 1 low (3 iterations) in rows
                                        for(int l=j-1; l<j+2; l++){ //-------------from 1 up to 1 low (3 iterations) in columns
                                            if((k==i && l==j) || k<0 || k>=size || l<0 || l>=size)
                                               ;
                                            else if(pent==0 && k==i+1)
                                                ;
                                            else if(pent==1 && k==i-1)
                                                ;
                                            else if(pent==2 && l==j+1)
                                                ;
                                            else if(pent==3 && l==j-1)
                                                ;
                                            else{
                                                tab[i][j].setNBHD(nbhdIndex, tab[k][l].getID());
                                                nbhdIndex++;
                                            }
                                        }
                                    } 
                                    temp[i][j].setID(tab[i][j].chooseSeed());
                                    
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
                                            
                                            if(k==i && l==j)
                                                ;//System.out.println("don't check itself");
                                            else if(pent==0 && k==i+1)
                                                ;
                                            else if(pent==1 && k==i-1)
                                                ;
                                            else if(pent==2 && l==j+1)
                                                ;
                                            else if(pent==3 && l==j-1)
                                                ;
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
                        case 3: //--------------------HEXANGONAL left
                        case 4: //--------------------HEXAGONAL right
                        case 5: //--------------------HEXAGONAL random
                            if(cond.getNeighbour()==3 || (cond.getNeighbour()==5 && hex==0)){          //left
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
                                               ;// System.out.println("just go ");
                                            else{
                                                tab[i][j].setNBHD(nbhdIndex, tab[k][l].getID());
                                                nbhdIndex++;
                                            }
                                        }
                                    } 
                                    temp[i][j].setID(tab[i][j].chooseSeed());
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
                                                ;//System.out.println("don't check itself");
                                            else{
                                                tab[i][j].setNBHD(nbhdIndex, tab[kk][ll].getID());
                                                nbhdIndex++;
                                            }
                                        }
                                    }
                                    temp[i][j].setID(tab[i][j].chooseSeed());
                                    break;
                                }
                            }else if(cond.getNeighbour()==4 || (cond.getNeighbour()==5 && hex==1)){    //right
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
                                                ;//System.out.println("zjust go ");
                                            else{
                                                tab[i][j].setNBHD(nbhdIndex, tab[k][l].getID());
                                                nbhdIndex++;
                                            }
                                        }
                                    } 
                                    temp[i][j].setID(tab[i][j].chooseSeed());
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
                                                ;//System.out.println("don't check itself");
                                            else{
                                                tab[i][j].setNBHD(nbhdIndex, tab[kk][ll].getID());
                                                nbhdIndex++;
                                            }
                                        }
                                    }
                                    temp[i][j].setID(tab[i][j].chooseSeed());
                                    break;
                                }
                            }
                        break;              
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
    