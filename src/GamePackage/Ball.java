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
public class Ball {
    private int ballposX;
    private int ballposY;
    private int ballXdir;
    private int ballYdir;
    
    public Ball() {
        ballposX = 120;
        ballposY = 525;
        ballXdir = 0;
        ballYdir = -3;
    }
    
    public Ball(int playerX) {
        ballposX = playerX + 54;
        ballposY = 455;
        ballXdir = 0;
        ballYdir = -3;
    }
    
    public Ball(int posX, int posY, int xdir, int ydir) {
        ballposX = posX;
        ballposY = posY;
        ballXdir = xdir;
        ballYdir = ydir;
    }
    
    // Getters and Setters
    public int getBallposX(){
        return ballposX;
    }
    
    public int getBallposY(){
        return ballposY;
    }
    
    public int getBallXdir(){
        return ballXdir;
    }
    
    public int getBallYdir(){
        return ballYdir;
    }
    
    public void setBallposX(int newpos){
        ballposX = newpos;
    }
    
    public void setBallposY(int newpos){
        ballposY = newpos;
    }
    
    public void setBallXdir(int newdir){
        ballXdir = newdir;
    }
    
    public void setBallYdir(int newdir){
        ballYdir = newdir;
    }
    
    // add positions
    public void addposX(int val) {
        ballposX += val;
    }
    
    public void addposY(int val) {
        ballposY += val;
    }
}
