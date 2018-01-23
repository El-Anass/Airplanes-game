/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GamePackage;

import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author elabbadi
 */
public class GamePlay extends JPanel implements KeyListener, ActionListener{
    private boolean play;
    
    public static int score;
    public String nom;
    
    private Timer timer; 
    private int delay;
    
    private int playerX;
    
    private ArrayList<Ball> balls;
    private ArrayList<Plane> planes;
    private ArrayList<Ball> enemyBomb;
    
    private long timePlane;
    
    //an array for the tub images
    private String[] arrayTube = {"tube_left_2.png", "tube_left.png", "tube.png", "tube_right.png", "tube_right_2.png"};
    private int tubeIndex = 2;

    private Image img, plane, tank, enemyB, defendB, tube,exp;
    
    public GamePlay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        play = true;
        delay = 8;
        timer = new Timer(delay, this);
        timer.start();
       setFocusable(true);
       requestFocus();

       
        
        
        balls = new ArrayList();
        planes = new ArrayList();
        enemyBomb = new ArrayList();
        
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
            tank = ImageIO.read(new File("tank.png"));
            exp= ImageIO.read(new File("exp.png"));
            enemyB = ImageIO.read(new File("enemy.png"));
            defendB = ImageIO.read(new File("defend.png"));
            tube = ImageIO.read(new File(arrayTube[tubeIndex]));
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
        g.drawImage(tube, playerX + 50, 455, 20, 40, null);
        
        //score
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 30));
        g.drawString("" + score, 850, 40);
        
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
            g.fillOval(balls.get(counter).getBallposX(), balls.get(counter).getBallposY(), 10, 10);
        }
        
        for (int counter = 0; counter < enemyBomb.size(); counter++) {          
            g.drawImage(enemyB, enemyBomb.get(counter).getBallposX(), enemyBomb.get(counter).getBallposY(), 20, 20, null);
        }
        
        // draw planes
        for (int counter = 0; counter < planes.size(); counter++) {          
            g.drawImage(plane, planes.get(counter).getPlaneposX(), planes.get(counter).getPlaneposY(), 80, 50, null);
        }
        
        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      
        if(play) {
            for (int counter = 0; counter < balls.size(); counter++) {           
                balls.get(counter).addposX(balls.get(counter).getBallXdir());
                balls.get(counter).addposY(balls.get(counter).getBallYdir());

                // if the ball hit the right or left border
                if(balls.get(counter).getBallposX() < 0) {
                    
                } else if (balls.get(counter).getBallposX() > 870) {
                    
                } 
                
                // remove the ball if it's hits the top of the panel
                if(balls.get(counter).getBallposY() < 0) {
                    balls.remove(counter);
                }
            }
            
            // check if enemy bomb hits the tunk
            for (int counter = 0; counter < enemyBomb.size(); counter++) {   
                enemyBomb.get(counter).addposX(enemyBomb.get(counter).getBallXdir());
                enemyBomb.get(counter).addposY(enemyBomb.get(counter).getBallYdir());
                
                if(new Rectangle(playerX, 490 , 120, 60).intersects(new Rectangle(enemyBomb.get(counter).getBallposX(), enemyBomb.get(counter).getBallposY(), 20, 20))){
                	tank=exp;
                    play = false;
                    JOptionPane.showMessageDialog(null, "Game Over");
                    nom=JOptionPane.showInputDialog(null, "Entrer votre nom");
                    if (nom == null)
                    	nom = "inconnu";
                    
                    new Players(nom, score);
                    int playagain=JOptionPane.showConfirmDialog(null, "Play Again !");
                    if(playagain==0){
                    	score=0;
                    	new Main();
                    }
                    	
                    
                    else 
                    	System.exit(0);
                    
                }
                
                // remove the bomb if it's hits the bottom of the panel
                if(enemyBomb.get(counter).getBallposY() > 530 ) {
                    enemyBomb.remove(counter);
                }
            }
            
            int idPlane = -1;
            boolean intersectes = false;
            
            for (int counter = 0; counter < planes.size(); counter++) {   
                // check if plane hit the ball
                for (int i = 0; i < balls.size(); i++) {
                    if(new Rectangle(planes.get(counter).getPlaneposX(), planes.get(counter).getPlaneposY() , 80, 50).intersects(new Rectangle(balls.get(i).getBallposX(), balls.get(i).getBallposY(), 10, 10))){
                        balls.remove(i);
                        idPlane = counter;
                        intersectes = true;
                        break;
                    }
                }
                
                if(intersectes)
                    break;
                
                planes.get(counter).addposX(2);
                planes.get(counter).addposY(0);
                    
                // destroy the plane if it's hits the right border and decrease the score
                if (planes.get(counter).getPlaneposX() > 870) {
                    planes.remove(counter);
                    if(score > 0)
                        score--;
                    else
                        score = 0;
                }
                
                Random rand = new Random();
                int  randBomb = rand.nextInt(700) + 1;
                if(randBomb == 2) {
                    enemyBomb.add(new Ball(planes.get(counter).getPlaneposX() + 30, planes.get(counter).getPlaneposY() + 50, 0, 1));
                }
            }
            
            if(intersectes) {
                planes.remove(idPlane);
                score++;
            }
        }
        
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(play) {
            // move the baddle
            if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                if(playerX >= 780) {
                    playerX = 780;
                }
                else {
                    moveRight();
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                if(playerX <= -10) {
                    playerX = -10;
                }
                else {
                    moveLeft();
                }
            }
            
            // create a new bomb
            if(e.getKeyCode() == KeyEvent.VK_SPACE) {                
                
                if(balls.size()<3){
                	 switch(tubeIndex){ 
                	 case 0: balls.add(new Ball(playerX + 44, 455 , -2, -2)); break;  
                     case 1: balls.add(new Ball(playerX + 48, 455 , -1, -3)); break;  
                     case 2: balls.add(new Ball(playerX)); break;  
                     case 3: balls.add(new Ball(playerX + 58, 455 , 1, -3)); break;  
                     case 4: balls.add(new Ball(playerX + 62, 455 , 2, -2)); break;  
                     default: System.out.println("Error while creating a new ball");                             
                 }
                }
                   
            }
            
            // change position of the tube
            if(e.getKeyCode() == KeyEvent.VK_D ) {
                if(tubeIndex < 4) {
                    tubeIndex++;
                    try {
                        tube = ImageIO.read(new File(arrayTube[tubeIndex]));
                    } catch (IOException ex) {
                        Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } else if(e.getKeyCode() == KeyEvent.VK_Q) {
                if(tubeIndex > 0) {
                    tubeIndex--;
                    try {
                        tube = ImageIO.read(new File(arrayTube[tubeIndex]));
                    } catch (IOException ex) {
                        Logger.getLogger(GamePlay.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
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
        playerX   += 10;
    }
    
    public void moveLeft() {
        playerX -= 10;
    }

    @Override
    public void keyReleased(KeyEvent e) { }
    
    @Override
    public void keyTyped(KeyEvent e) { }

	public boolean isPlay() {
		return play;
	}

	public void setPlay(boolean play) {
		this.play = play;
	}
    
    
}
