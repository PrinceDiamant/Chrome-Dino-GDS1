import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Objects;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Dino extends JPanel implements ActionListener {

    int boardWidth = 800;
    int boardHeight = 550;

    // images
    Image dinobackground;
    Image dino;
    Image cactus1;
    Image cactus2;
    Image cactus3;

    // dino position
    int dinoX = 50;
    int dinoY = 400;

    // cactus positions
    int cactusX1 = 560;
    int cactusX2 = 1120;
    int cactusX3 = 1680;
    int cactusY = 400;
    Random random = new Random();

    boolean spaceTap = false;
    boolean gameOver = false;
    double score = 0;
    Timer gameTimer;
    Timer jumpTimer;

    Dino() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);

        // keine Ahnung über das Layout aber es funktioniert gut
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE && !spaceTap) {
                    spaceTap = true;
                    if (gameOver) {
                        resetGame();
                    } else {
                        startJump();
                    }
                }
            }
        });

        // load images
        dinobackground = new ImageIcon(Objects.requireNonNull(getClass().getResource("./desertdarkbg.png"))).getImage();
        dino = new ImageIcon(Objects.requireNonNull(getClass().getResource("./dinoyellow.png"))).getImage();
        cactus1 = new ImageIcon(Objects.requireNonNull(getClass().getResource("./cactus1.png"))).getImage();
        cactus2 = new ImageIcon(Objects.requireNonNull(getClass().getResource("./cactus2.png"))).getImage();
        cactus3 = new ImageIcon(Objects.requireNonNull(getClass().getResource("./cactus3.png"))).getImage();

        gameTimer = new Timer(1000/60, this);
        gameTimer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(dinobackground, 0, 0, boardWidth, boardHeight, null);

        g.drawImage(dino, dinoX, dinoY, 70, 70, null);

        drawCactus(g);

        // score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));

        if (gameOver == true) {
            g.drawString("Game Over: " + String.valueOf((int) score), boardWidth / 2 - 100, boardHeight / 2);

            // Highscore

            /*
            create File if doesnt exist

            //Read highscores from highscores.txt
    File save = new File("highscores.txt");
    if (!save.exists()){
        save.createNewFile();
        System.out.println("\n----------------------------------");
        System.out.println("The file has been created.");
        System.out.println("------------------------------------");
        //Places the default 0 values in the file for the high scores
        PrintWriter writer = new PrintWriter("highscores.txt", "UTF-8");
        writer.println(String.valueOf(0));
        writer.println(String.valueOf(0));
        writer.println(String.valueOf(0));
        writer.close();
    }

    
            (when File exists read out the first three numbers (Highscores) rest safe in the file) -> Variables
            if the now set score is higher than one of the three highscores before it will be safed in a Variable
            the now new Highscore goes in one of the slots and the previous is set to the score down below





             */

        } else {
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }

    public void drawCactus(Graphics g) {
        g.drawImage(cactus1, cactusX1, cactusY, 50, 50, null);
        g.drawImage(cactus2, cactusX2, cactusY, 50, 50, null);
        g.drawImage(cactus3, cactusX3, cactusY, 50, 50, null);
    }

    public void updateDino() {
        if (collision()) {
            gameOver = true;
        }

        if (dinoY > boardHeight) {
            gameOver = true;
        }
    }

    public void startJump() {
        jumpTimer = new Timer(4, new ActionListener() {
            int jumpHeight = 130;
            int jumpStep = 5; // jumpStep < 3 jump dauert lange

            @Override
            public void actionPerformed(ActionEvent e) {
                if (jumpHeight > 0) {
                    dinoY -= jumpStep;
                    jumpHeight -= jumpStep;
                } else if (dinoY < 400) {
                    dinoY += jumpStep;
                } else {
                    jumpTimer.stop();
                    spaceTap = false;
                }
                repaint();
            }
        });
        jumpTimer.start();
    }

    public void updateCactus() {
        // schritte oder Geschwindigkeit für Kakteen
        cactusX1 -= 10;
        cactusX2 -= 10;
        cactusX3 -= 10;

        // die Random sorgt für ein abwechslungsreiches Spiel und ist immer in Phassen unterteilt
        int cactusDistance = random.nextInt(200, 500);

        if (cactusX1 <= 0) {
            cactusX1 = boardWidth + cactusDistance;
        }
        if (cactusX2 <= 0) {
            cactusX2 = boardWidth + cactusDistance;
        }
        if (cactusX3 <= 0) {
            cactusX3 = boardWidth + cactusDistance;
        }
    }

    boolean collision() {
        // Hitbox Dino
        int dinoRight = dinoX + 60;
        int dinoBottom = dinoY + 60;

        // Hitbox Cactus
        int cactusRight1 = cactusX1 + 40;
        int cactusBottom1 = cactusY + 40;

        int cactusRight2 = cactusX2 + 40;
        int cactusBottom2 = cactusY + 40;

        int cactusRight3 = cactusX3 + 40;
        int cactusBottom3 = cactusY + 40;

        return (dinoX < cactusRight1 && dinoRight > cactusX1 && dinoY < cactusBottom1 && dinoBottom > cactusY) ||
                (dinoX < cactusRight2 && dinoRight > cactusX2 && dinoY < cactusBottom2 && dinoBottom > cactusY) ||
                (dinoX < cactusRight3 && dinoRight > cactusX3 && dinoY < cactusBottom3 && dinoBottom > cactusY);
    }

    public void resetGame() {
        dinoY = 400;
        cactusX1 = 560;
        cactusX2 = 1120;
        cactusX3 = 1680;
        score = 0;
        gameOver = false;
        spaceTap = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // action while the game runs
        if (!gameOver) {
            updateDino();
            updateCactus();
            score += 0.1; // higher score over Time
        }
        repaint();
    }
}
