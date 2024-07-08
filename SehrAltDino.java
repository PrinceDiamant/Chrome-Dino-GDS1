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

public class Dino extends JPanel implements ActionListener, KeyListener, Runnable {

	int boardWidth = 800;
    int boardHeight = 550;
    
  //images festlegen
    Image dinobackground;
    Image dino; 
    Image cactus1;
    Image cactus2;
    Image cactus3;
    
  //dino position
    int dinoX = 50;
    int dinoY = 400;
  //
    int cactusX1 = 560;
    int cactusX2 = 1120;
    int cactusX3 = 1680;
    int cactusY=400;
    
    boolean spaceTap=false;
    Thread thread;
    Timer timer;
    
    
    Dino() {
    	
    	setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);   
        System.out.println(dinoY);
        
        addKeyListener(this);
        
        //load
        dinobackground = new ImageIcon(getClass().getResource("./dersertdarkbg.png")).getImage();
        dino = new ImageIcon(getClass().getResource("./dinoyellow.png")).getImage();
        cactus1 = new ImageIcon(getClass().getResource("./cactus1.png")).getImage();        
        cactus2 = new ImageIcon(getClass().getResource("./cactus1.png")).getImage(); 
        cactus2 = new ImageIcon(getClass().getResource("./cactus1.png")).getImage(); 
        
        
             
          
    }
    	
   
    public void startThread() {
    	thread= new Thread(this);
    	thread.start();
    }
   
    
	@Override
	public void run() {
	
		while (thread!=null) {
			//Koordinaten des Dinos Updaten
			updateDino();
	
			
			//Koordinaten der Kakteen Updaten
			//updateCactus();
			
			
			//Graphic Updaten
			repaint();
			
		}
	}
    
    

   

	
	public void paintComponent(Graphics g) { // this method is overwritten by the JPanel and called when ever the componend needs to be repainted
		super.paintComponent(g);//ensures, that the panel is rendered well
		draw(g);
		drawCactus(g);
	}

	public void draw(Graphics g) {
        //background:
        g.drawImage(dinobackground, 0, 0, this.boardWidth, this.boardHeight, null);
        
        //dino:
        g.drawImage(dino, dinoX, dinoY, 70, 70, null);
        
        //kakteen:
//        g.drawImage(cactus1, cactusX1, cactusY, 70, 70, null);
//        g.drawImage(cactus2, cactusX2, cactusY, 70, 70, null);
//        g.drawImage(cactus3, cactusX3, cactusY, 70, 70, null);

	//score
        g.setColor(Color.white);

        g.setFont(new Font("Arial", Font.PLAIN, 32));
		
	if (gameOver = 1) {
		// draw score screen + wenns geht in die Mitte
		g.drawString("Game Over: " + String.valueOf((int) score), 10, 35);
	}else{
		// draw score top corner
		g.drawString(String.valueOf((int) score), 10, 35);
	}
	public void drawCactus(Graphics g) {
		 //kakteen:
        g.drawImage(cactus1, cactusX1, cactusY, 70, 70, null);
        g.drawImage(cactus2, cactusX2, cactusY, 70, 70, null);
        g.drawImage(cactus3, cactusX3, cactusY, 70, 70, null);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode()==KeyEvent.VK_SPACE&& spaceTap==false){
			spaceTap=true;

			if (gameOver = 1) {
				//reset everything
				// score 0
				// position Dino
				// cactus place stop
				// velocity 0
				// gameOver back to 0
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		/*if (e.getKeyCode()==KeyEvent.VK_SPACE) {
			spaceTap=false;
		}*/
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void updateDino() {
		
		
		
		if (spaceTap==true) {
			
			dinoY-=130;		
			System.out.println("jump");
			System.out.println(dinoY);
			for (int i=1;i<=130;i++) {
				
				dinoY+=1;
				repaint();
			try {
				thread.sleep(4);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//repaint();
				System.out.println(dinoY);
				
			}
			
			spaceTap=false;
			
		}
		if (collision(dino, cactus)) {
                gameOver = true;
            }
        

        if (/*irgenwas mit DinoY */ > boardHeight) {
            gameOver = true;
	
		}
	}
	 boolean collision(Dino a, Cactus b) {
		return /*hier muss viel rein */;
	 }
	public void updateCactus() {
		
		while(1!=0) {
			cactusX1-=10;
			cactusX2-=10;
			cactusX3-=10;
			System.out.println(cactusX1+"cactus");
			System.out.println(cactusX2);
			System.out.println(cactusX3);
			
			try {
				thread.sleep(25);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (cactusX1<=0) {
				cactusX1=560;
		}
			if (cactusX2<=0) {
				cactusX2=1120;
		}
			if (cactusX3<=0) {
				cactusX3=1680;
			}
			repaint();

		}
		
	}


	
	
	
}
