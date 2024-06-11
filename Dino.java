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

public class Dino extends JPanel {

	int boardWidth = 800;
    int boardHeight = 550;
    
  //images festlegen
    Image dinobackground;
    Image dino; 
    
  //dino position
    int dinoX = 50;
    int dinoY = 200;
    int dinoVelocity = 0;
    int gravity = 100;
    int jumpStrength = -20;
    boolean isJumping = false;
    
    Dino() {
    	setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        //
       
        //load von Flappy Bird
        dinobackground = new ImageIcon(getClass().getResource("./dinobackground.png")).getImage();
        dino = new ImageIcon(getClass().getResource("./dino.png")).getImage();
        
        //start timer game loop
        //Um die Hindernisse später an den timer anzupassen 
        Timer timer = new Timer(20, e -> gameUpdate());
        timer.start(); 
        
        
    }
    
	private void gameUpdate() {
		if (isJumping) { 
			dinoVelocity += gravity;
			dinoY += dinoVelocity;
			
			if (dinoY<=400) {
				dinoY=400;
				isJumping = false;
				dinoVelocity=0;
			}
		}
		repaint();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Dino game");
		Dino game = new Dino();
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	public void paintComponent(Graphics g) { // this method is overwritten by the JPanel and called when ever the componend needs to be repainted
		super.paintComponent(g);//ensures, that the panel is rendered well
		draw(g);
	}

	public void draw(Graphics g) {
        //background
        g.drawImage(dinobackground, 0, 0, this.boardWidth, this.boardHeight, null);
        
        //dino 
        g.drawImage(dino, 50, 200, null);
	}
	private class Keyhandler extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode()==KeyEvent.VK_SPACE && !isJumping) {
				isJumping =true;
				dinoVelocity=jumpStrength;
			}
		}
	}
	
}
	
