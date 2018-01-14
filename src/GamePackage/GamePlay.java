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
import java.util.ArrayList;
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
    
    private ArrayList<Ball> balls;
    private ArrayList<Plane> planes;
    
    private int timeNewPlane;
    
    
    private Image img;
    
    public GamePlay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
        
        balls = new ArrayList();
        planes = new ArrayList();
        
        planes.add(new Plane());
        
        try {
            img = ImageIO.read(new File("background.png"));
        } catch (IOException ex) {
            Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void paint(Graphics g) {
        // draw the background image
        g.drawImage(img, 0, 0, null);
        
        //the paddle
        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);
        
        //the ball
        g.setColor(Color.yellow);
        
        // draw balls
        for (int counter = 0; counter < balls.size(); counter++) {          
            g.fillOval(balls.get(counter).getBallposX(), balls.get(counter).getBallposY(), 20, 20);
        }
        
        // draw planes
        for (int counter = 0; counter < planes.size(); counter++) {          
            g.fillRect(planes.get(counter).getPlaneposX(), planes.get(counter).getPlaneposY(), 80, 50);
        }
        
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play) {
            for (int counter = 0; counter < balls.size(); counter++) {           
                balls.get(counter).addposX(balls.get(counter).getBallXdir());
                balls.get(counter).addposY(balls.get(counter).getBallYdir());

                if(balls.get(counter).getBallposX() < 0) {
                    balls.get(counter).reverseBallXdir();
                } else if (balls.get(counter).getBallposX() > 870) {
                    balls.get(counter).reverseBallXdir();
                }

                if(balls.get(counter).getBallposY() < 0) {
                    balls.get(counter).reverseBallYdir();
                    balls.remove(counter);
                }
                
            }
            
            for (int counter = 0; counter < planes.size(); counter++) {           
                // check if plane hit the ball
                /*if(new Rectangle(balls.get(counter).getBallposX(), balls.get(counter).getBallposY() , 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))){
                    balls.get(counter).reverseBallYdir();
                }*/
                
                planes.get(counter).addposX(1);
                planes.get(counter).addposY(0);

                if (planes.get(counter).getPlaneposX() > 870) {
                    planes.remove(counter);
                }
                
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
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            balls.add(new Ball(playerX));
            System.out.println("Current balls list is:" + balls);
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
