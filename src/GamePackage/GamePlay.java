/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GamePackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author elabbadi
 */
public class GamePlay extends JPanel implements KeyListener, ActionListener{
    private boolean play = false;
    private int score = 0;
    
    private int totalPlans = 20;
    
    private Timer timer; 
    private int delay = 8;
    
    private int playerX = 400;
    
    private int ballposX = 120;
    private int ballposY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;
    
    private Image img;
    
    public GamePlay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
        
        try {
            img = ImageIO.read(new File("background.png"));
        } catch (IOException ex) {
            Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void paint(Graphics g) {
        //background
        //g.setColor(Color.black);
        //g.fillRect(1, 1, 692, 592);
        
        g.drawImage(img, 0, 0, null);

        //Border 
        /*g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 892, 3);
        g.fillRect(892, 0, 3, 592);*/
        
        //the paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);
        
        //the ball
        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);
        
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play) {
            if(new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
                ballYdir = -ballYdir;
            }
            
            ballposX += ballXdir;
            ballposY += ballYdir;
            
            if(ballposX < 0) {
                ballXdir = -ballXdir;
            } else if (ballposX > 870) {
                ballXdir = -ballXdir;
            }
            
            if(ballposY < 0) {
                ballYdir = -ballYdir;
            }
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(playerX >= 800) {
                playerX = 800;
            }
            else {
                moveRight();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(playerX < 10) {
                playerX = 10;
            }
            else {
                moveLeft();
            }
        }
    }
    
    public void moveRight() {
        play = true;
        playerX += 20;
    }
    
    public void moveLeft() {
        play = true;
        playerX -= 20;
    }

    @Override
    public void keyReleased(KeyEvent e) { }
    
    @Override
    public void keyTyped(KeyEvent e) { }
}
