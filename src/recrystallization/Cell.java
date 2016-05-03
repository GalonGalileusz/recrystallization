/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recrystallization;

import java.awt.Color;
import java.util.Random;

/**
 *
 * @author Dendelion
 */
public class Cell {
    private int id;
    private int nbhd[];
    private Color color;
    private int nbhdSize;
    
    public Cell(int size){  //size is number of neighbours
        this.id = 0;
        this.nbhdSize = size;
        this.nbhd = new int[size];
        for(int i=0; i<size; i++)
            this.nbhd[i]=0;
        this.color = Color.WHITE;
    }
    
    public void setID(int id){
        this.id = id;
    }
    
    public int getID(){
        return this.id;
    }
    
    public void drawColor(){
        
        float hsb[] = new float[3];
        Random random = new Random();
        Color.RGBtoHSB(random.nextInt(255), random.nextInt(255), random.nextInt(255), hsb);
        this.color = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);  
    }
    
    public void setColor(Color c){
        this.color = c;
    }
    
    public Color getColor(){ 
        return this.color;
    }
    
    public void resetColor(){
        this.color = Color.WHITE;
    }
    
    public void setNBHD(int index, int value){
        this.nbhd[index] = value;
    }
    
    public int[] getNBHD(){
        return this.nbhd;
    }
    
    public void showNBHD(){
        for(int i=0; i<nbhdSize; i++)
            System.out.print(this.nbhd[i]+" ");
    }
    
    public void cleanNBHD(){
        for(int i=0; i<nbhdSize; i++)
            this.nbhd[i]=0;
    }
    
    public int chooseSeed(){
        
        int zeroCounter = 0;
        int grain = 0;
        int grainCounter = 0;
        double halfSize = 0.0;
        int index = 0;
        int tempCounter = 0;
        int repeats = 0;
        
        /*-------------------bubble sort-----------*/
        int a, b, c, temp;
        
        b=nbhdSize-1;
        
        while(b>=1){
            c=1;
            a=0;
            
            while(a<b){
                if(nbhd[a]>nbhd[a+1]){
                    temp = nbhd[a];
                    nbhd[a] = nbhd[a+1];
                    nbhd[a+1] = temp;
                    c = 0;
                }
                a++;
            }
            
            if(c!=1)
                b--;
            
            else
                break;
        }
        
        /*-----------------------------------------*/
        
        for(int i=index; i<nbhdSize; i++){
            if(nbhd[i]==0)
                zeroCounter++;
            else{
                index = i;
                break;
            }
        }
        
        if(zeroCounter!=nbhdSize){
            
            int repeatSize = nbhdSize - zeroCounter;
            
            int repeatTab [] = new int [repeatSize];
            for(int k=0; k<repeatSize; k++)
                repeatTab[k]=0;
            
            halfSize = (double)(nbhdSize - zeroCounter)/2;
            
            while(grainCounter<=halfSize){
                
                halfSize = (double)((halfSize * 2 - grainCounter)/2);
                grainCounter = 0;
                
                grain = nbhd[index];
                grainCounter++;
                
                for(int j=index+1; j<nbhdSize; j++){
                    if(nbhd[j]==grain)
                        grainCounter++;
                    else
                        break;
                }
                
                index++;
                
                if(grainCounter>tempCounter){
                    for(int k=0; k<repeatSize; k++)
                        repeatTab[k] = 0;
                    
                    repeats = 0;
                    repeatTab[repeats] = nbhd[index-1];
                    tempCounter = grainCounter;
                    repeats++;
                }
                else if(grainCounter==tempCounter){
                    repeatTab[repeats] = nbhd[index-1];
                    repeats++;
                }                
            }
            
            System.out.println("repeatTab: ");
            
            Random random = new Random();
            index = 0;
            for(int i=0; i<repeatSize; i++){
                System.out.print(repeatTab[i]+" ");
                if(repeatTab[i]!=0)
                    index++;
            }
            System.out.println();
            System.out.println("index "+index);
//            int chuj = random.nextInt(index);
//            System.out.println("chuj "+chuj);
            grain = repeatTab[random.nextInt(index)];
            System.out.println("grain "+grain);
        }
        return grain;   //grain is ID which this cell will get
    }
    
    
}
