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
        ballposY = 530;
        ballXdir = 0;
        ballYdir = -1;
    }
    
    public Ball(int playerX) {
        ballposX = playerX + 40;
        ballposY = 530;
        ballXdir = 0;
        ballYdir = -1;
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
    
    // reverse direction
    public void reverseBallXdir() {
        ballXdir = -ballXdir;
    }
    
    public void reverseBallYdir() {
        ballYdir = -ballYdir;
    }
}
