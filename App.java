package dinogame;

import javax.swing.JFrame;

public class App {
		   
	public static void main(String[] args) throws Exception {

			int boardWidth = 800;
		    int boardHeight = 550;

		    JFrame frame = new JFrame("Dinogame");
		    frame.setSize(boardWidth, boardHeight);
		    frame.setLocationRelativeTo(null);
		    frame.setResizable(false);
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    Dino dino = new Dino();
		    frame.add(dino);
		    frame.pack(); // macht das alles richtig dargestellt wird
		    frame.setVisible(true);
		   }
		}