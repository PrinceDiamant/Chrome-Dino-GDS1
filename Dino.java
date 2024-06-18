package dinogame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Dino extends JPanel implements ActionListener, KeyListener {

	int boardWidth = 800;
    int boardHeight = 550;
    
  //images festlegen
    Image dinobackground;
    Image dino; 
    
  //dino position
    int dinoX = 50;
    int dinoY = 400;
    int dinoVelocity = 0;
    int gravity = 1;
    int jumpStrength = -20;
    boolean isJumping = false;
    Thread thread;
    
    
    Dino() {
    	
    	setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);   
        System.out.println(dinoY);
        
        addKeyListener(this);
      //  move();   
        //load von Flappy Bird
        dinobackground = new ImageIcon(getClass().getResource("./dersertdarkbg.png")).getImage();
        dino = new ImageIcon(getClass().getResource("./dinoyellow.png")).getImage();
        
        
        Timer timer = new Timer(20, e -> gameUpdate());
        timer.start();
        
        //start timer game loop
        //Um die Hindernisse später an den timer anzupassen 
        
     /*   while (true) {
			try {
				thread.sleep(30);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }*/
        
			  
     
             
          
    }
    public void gameUpdate() {
    	
    	if (isJumping==true) { 
    		dinoVelocity+=gravity;
    		dinoY+= dinoVelocity;
    		
			}
    	if (dinoY<=400) {
    		dinoY=400;
    		isJumping=false;
    		dinoVelocity=0;
    	}
    	
    }
   

	
	public void paintComponent(Graphics g) { // this method is overwritten by the JPanel and called when ever the componend needs to be repainted
		super.paintComponent(g);//ensures, that the panel is rendered well
		draw(g);
	}

	public void draw(Graphics g) {
        //background
		
        g.drawImage(dinobackground, 0, 0, this.boardWidth, this.boardHeight, null);
        
        //dino 
        g.drawImage(dino, dinoX, dinoY, 70, 70, null);
	}
	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode()==KeyEvent.VK_SPACE){
			isJumping= true;
			dinoVelocity=-9;
			dinoY+=dinoVelocity;
			System.out.println("jump");
			System.out.println(dinoY);
			
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
