package GamePackage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Players implements Serializable {
	
	private ArrayList<Player> P;

	public Players(String nom,int score) {
		Players b=null;
		System.out.println("ook1");
        try {
            FileInputStream file = new FileInputStream
             ("/home/elabbadi/Workspace/Java/game v2/Game/GameFile/players.txt") ;
            ObjectInputStream in = new ObjectInputStream (file) ;

            b = (Players) in.readObject() ;
            System.out.println("ook");
        } catch (Exception ex)
        {
            System.err.println ("Erreur de lecture " + ex) ;
        }
		P =b.P;
		addPlayer(nom, score);
		
		/*P=new ArrayList<Player>();
		P.add(new Player("simo", 10));*/
		Ecrire();
	}
	
	public int existplayer(String nom){
		for (int i = 0; i < P.size(); i++) {
			if(P.get(i).getNom().equals(nom))
				return i;
		}
		return -1;
	}
	 public void addPlayer(String nom,int score){
		 
		 if(existplayer(nom)==-1)
			 P.add(new Player(nom, score));
		 else{
			 if(P.get(existplayer(nom)).getScore()<score){
				 P.get(existplayer(nom)).setScore(score);
				 if(!nom.equals("inconnu"))
				 JOptionPane.showMessageDialog(null, " new record ");
			 }
			
		 }
		 JOptionPane.showMessageDialog(null, " Your score  :" + score+"\n best score :  " + this.max().getScore() );
	 }
	 
	 void Ecrire ()
	    {
	        try {
	            FileOutputStream file = new
	                    FileOutputStream ("C:\\Users\\LastGame\\Documents\\Nouveau dossier\\Game\\GameFile\\players.txt") ;
	            ObjectOutputStream out = new
	                    ObjectOutputStream (file) ;
	            out.writeObject (this) ;
	            out.flush () ;
	            out.close () ;
	        } catch (IOException ex)
	        {
	            System.err.println ("Erreur d'ecriture " +
	                    ex) ;
	        }
	    }
	 
	 public Player max(){
		 Player p=P.get(0);
		 for (int i = 1; i < P.size(); i++) {
			if(P.get(i).getScore()>p.getScore())
				p=P.get(i);
		}
		 return p;
	 }
	
	
}
