/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recrystallization;

import java.awt.EventQueue;
import java.util.Random;
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
        
        // TODO code application logic here
        
//        int tab [] = new int[]{0,0,0,0,0,4,1,2};
//        int sortTab[] = new int[]{0,0,0,0,0,0,0,3};
//        int size = 8;
//        
//        //int[] sortTab2 = BubbleSort(tab, size);
//        
//        int ii, jj, p, tempT;
//	
//	jj=size-1;
//	
//	while(jj>=1 )
//	{
//		p=1;
//		ii=0;
//		
//		while(ii<jj)
//		{
//			if(tab[ii]>tab[ii+1])
//			{
//				//swap(T[i], T[i+1]);
//                                tempT=tab[ii];
//                                tab[ii]=tab[ii+1];
//                                tab[ii+1]=tempT;
//				p=0;
//			}
//			ii++;
//		}
//		
//		if(p!=1)
//                    jj--;
//		
//		else
//                    break;
//	}
//        
//        
//        for(int i=0; i<size; i++)
//            System.out.print(tab[i]+" ");
//        
//        
//        
//        
//        /*----------------------variables in function -------*/
//        int zeroCounter = 0;
//        int max = 0;
//        int maxCounter = 0;
//        double halfSize = 0.0;
//        int index = 0;
//        int temp = 0;
//        int tempCounter = 0;
//        int ile = 0;
//        
//         
//        for(int i=index; i<size; i++){
//            if(sortTab[i]==0)
//                zeroCounter++;
//            else{
//                index = i;
//                break;
//            }                                       
//        }
//        
//        //if zeroCounter==size cell doesn't grow cause has no grains in the neighbourhood
//        if(zeroCounter!=size){
//            
//            int s = size - zeroCounter;
//            
//            int tabPowt [] = new int [s];       //tablica powtorzen zeby wpisac id tych co maja tyle samo powtorzen zeby potem z nich wylosowac
//            for(int k=0; k<s; k++){
//                tabPowt[k] = 0;
//            }
//    
//            halfSize = (double)(size - zeroCounter)/2 ;
//            
//            while(maxCounter<=halfSize){
//                
//                halfSize = (double)((halfSize * 2 - maxCounter)/2);
//                System.out.println("half size "+halfSize);
//                maxCounter = 0;
//
//                max=sortTab[index];
//                maxCounter++;
//               
//                for(int j=index+1; j<size; j++){
//
//                    if(sortTab[j]==max)
//                        maxCounter++;    
//                    else   
//                        break;
//                }
//
//                index++;
//                
//                if(maxCounter>tempCounter){   
//                    for(int k=0; k<s; k++)
//                        tabPowt[k] = 0;  
//                    
//                    ile=0;
//                    tabPowt[ile] = sortTab[index-1];
//                    temp = max;
//                    tempCounter = maxCounter;
//                    ile = 1;
//                }else if(maxCounter==tempCounter){
//                    tabPowt[ile]=sortTab[index-1];
//                    ile++;
//                }        
//            }
//            
//            Random random = new Random();
//            
//            System.out.print("tab powt: ");
//            
//            int indexCounter = 0;
//            for(int a=0; a<s; a++){
//                System.out.print(tabPowt[a]+" ");
//                if(tabPowt[a]!=0)
//                    indexCounter++;
//                else
//                    break;
//            }
//            
//            System.out.println();
//            
//            
//            
//            int mojeId = tabPowt[random.nextInt(indexCounter)];
//         
//            System.out.println("tyle samo "+ile);
//            System.out.println("id najwiekszej ilosci sasiadow "+mojeId);
//            
//            for(int k=0; k<s; k++){
//                System.out.print(tabPowt[k]+" ");
//            }
//
//        }
    }
    

    
    
    public Recrystallization(){
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }
                
                MyFrame mf = new MyFrame();               
                mf.setVisible(true);                            
            }
        });
    }
}
        
    
    

