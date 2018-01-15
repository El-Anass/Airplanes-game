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
import java.util.Random;
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
    private boolean play;
    private int score;
    
    private Timer timer; 
    private int delay;
    
    private int playerX;
    
    private ArrayList<Ball> balls;
    private ArrayList<Plane> planes;
    
    private long timePlane;
    
    private Image img, plane, tank;
    
    public GamePlay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        play = true;
        delay = 8;
        timer = new Timer(delay, this);
        timer.start();
        
        balls = new ArrayList();
        planes = new ArrayList();
        
        planes.add(new Plane());
        
        timePlane = System.currentTimeMillis();
        
        playerX = 400;
        score = 0;
        
        try {
            img = ImageIO.read(new File("background.png"));
        } catch (IOException ex) {
            Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            plane = ImageIO.read(new File("plane.png"));
        } catch (IOException ex) {
            Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            tank = ImageIO.read(new File("tank.png"));
        } catch (IOException ex) {
            Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void paint(Graphics g) {
        // draw the background image
        g.drawImage(img, 0, 0, null);
        
        //the tank
        g.drawImage(tank, playerX, 490, 120, 60, null);
        
        //the ball
        g.setColor(Color.black);
        
        Random rand = new Random();

        int  randPlane = rand.nextInt(5) + 2;
        
        int  randY = rand.nextInt(250) + 20;
        
        long nowMillis = System.currentTimeMillis();
        
        int diff = (int)((nowMillis - timePlane) / 1000);
        
        if(diff >= randPlane) {
            planes.add(new Plane(randY));
            timePlane = nowMillis;
        }
        
        // draw balls
        for (int counter = 0; counter < balls.size(); counter++) {          
            g.fillOval(balls.get(counter).getBallposX(), balls.get(counter).getBallposY(), 20, 20);
        }
        
        // draw planes
        for (int counter = 0; counter < planes.size(); counter++) {          
            g.drawImage(plane, planes.get(counter).getPlaneposX(), planes.get(counter).getPlaneposY(), 80, 50, null);
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
                
                if(new Rectangle(playerX, 490 , 120, 60).intersects(new Rectangle(balls.get(counter).getBallposX(), balls.get(counter).getBallposY(), 20, 20))){
                    balls.remove(counter);
                    play = false;
                    break;
                }
            }
            
            int idPlane = -1;
            boolean intersectes = false;
            
            for (int counter = 0; counter < planes.size(); counter++) {   
                // check if plane hit the ball
                for (int i = 0; i < balls.size(); i++) {
                    if(new Rectangle(planes.get(counter).getPlaneposX(), planes.get(counter).getPlaneposY() , 80, 50).intersects(new Rectangle(balls.get(i).getBallposX(), balls.get(i).getBallposY(), 20, 20))){
                        balls.remove(i);
                        idPlane = counter;
                        intersectes = true;
                        break;
                    }
                }
                
                if(intersectes)
                    break;
                
                planes.get(counter).addposX(1);
                planes.get(counter).addposY(0);

                if (planes.get(counter).getPlaneposX() > 870) {
                    planes.remove(counter);
                }
                
                Random rand = new Random();
                int  randBomb = rand.nextInt(700) + 1;
                if(randBomb == 2) {
                    balls.add(new Ball(planes.get(counter).getPlaneposX() + 30, planes.get(counter).getPlaneposY() + 50, 0, 1));
                }
            }
            
            if(intersectes) {
                planes.remove(idPlane);
                score++;
                System.out.println(score);
            }
        }
        
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(play) {
            // move the baddle
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
            
            // create a new bomb
            if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                balls.add(new Ball(playerX));
            }
        }
        
        // Change the play variable
        if(e.getKeyCode() == KeyEvent.VK_P) {
            if(play)
                play = false;
            else 
                play = true;
        }
    }
    
    public void moveRight() {
        playerX += 20;
    }
    
    public void moveLeft() {
        playerX -= 20;
    }

    @Override
    public void keyReleased(KeyEvent e) { }
    
    @Override
    public void keyTyped(KeyEvent e) { }
}
