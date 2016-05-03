/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package recrystallization;

/**
 *
 * @author Dendelion
 */
public class Conditions {
    private int status, bc, neighbour, location, ray, amount;
        
    public Conditions(){
        status = 0;
        bc = 0;
        neighbour = 0;
        location = 0;
        ray = 0;
        amount = 0;
    }
    
    public void setStatus(int arg){
        status = arg;
    }
    
    public int getStatus(){
        return status;
    }
    
    public void setBC(int arg){
        bc = arg;
    }
    
    public int getBC(){
        return bc;
    }
    
    public void setNeighbour(int arg){
        neighbour = arg;
    }
    
    public int getNeighbour(){
        return neighbour;
    }
    
    public void setLocation(int arg){
        location = arg;
    }
    
    public int getLocation(){
        return location;
    }
    
    public void setRay(int arg){
        ray = arg;
    }
    
    public int getRay(){
        return ray;
    }
    
    public void setAmount(int arg){
        amount = arg;
    }
    
    public int getAmount(){
        return amount;
    }
}
