/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GamePackage;

import javax.swing.JFrame;

/**
 *
 * @author elabbadi
 */
public class Main {
	
	public Main()
	{
		JFrame obj = new JFrame();
        GamePlay gamePlay = new GamePlay();
        obj.setBounds(10, 10, 900, 600);
        obj.setTitle("Plane Game");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gamePlay);
	}
    public static void main(String[] args) {	
        
    new Main();
    
   }
}
