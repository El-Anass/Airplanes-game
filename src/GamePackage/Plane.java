/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GamePackage;

/**
 *
 * @author elabbadi
 */
public class Plane {
    private int planeposX;
    private int planeposY;
    
    public Plane() {
        planeposX = -80;
        planeposY = 150;
    }
    
    public Plane(int posY) {
        planeposX = -80;
        planeposY = posY;
    }
    
    // Getters and Setters
    public int getPlaneposX(){
        return planeposX;
    }
    
    public int getPlaneposY(){
        return planeposY;
    }
    
    public void setPlaneposX(int newpos){
        planeposX = newpos;
    }
    
    public void setBallposY(int newpos){
        planeposY = newpos;
    }
    
    // add positions
    public void addposX(int val) {
        planeposX += val;
    }
    
    public void addposY(int val) {
        planeposY += val;
    }
}
