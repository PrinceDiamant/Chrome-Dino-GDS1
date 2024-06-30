import javax.swing.JFrame;

public class App {


    public static void main(String[] args) throws Exception {

        int boardWidth = 800;
        int boardHeight = 550;

        JFrame frame = new JFrame("Dino Game");
        Dino game = new Dino();
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.startThread();

    }

}