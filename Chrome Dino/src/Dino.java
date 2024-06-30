import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
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

    boolean spaceTap = false;
    boolean gameOver = false;
    double score = 0;
    Timer gameTimer;
    Timer jumpTimer;

    Dino() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);

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
        dinobackground = new ImageIcon(getClass().getResource("./desertdarkbg.png")).getImage();
        dino = new ImageIcon(getClass().getResource("./dinoyellow.png")).getImage();
        cactus1 = new ImageIcon(getClass().getResource("./cactus1.png")).getImage();
        cactus2 = new ImageIcon(getClass().getResource("./cactus2.png")).getImage();
        cactus3 = new ImageIcon(getClass().getResource("./cactus3.png")).getImage();

        gameTimer = new Timer(1000/40, this);
        gameTimer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // background:
        g.drawImage(dinobackground, 0, 0, boardWidth, boardHeight, null);

        // dino:
        g.drawImage(dino, dinoX, dinoY, 70, 70, null);

        // cactus:
        drawCactus(g);

        // score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));

        if (gameOver) {
            g.drawString("Game Over: " + String.valueOf((int) score), boardWidth / 2 - 100, boardHeight / 2);
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
        cactusX1 -= 10;
        cactusX2 -= 10;
        cactusX3 -= 10;

        if (cactusX1 <= 0) {
            cactusX1 = boardWidth;
        }
        if (cactusX2 <= 0) {
            cactusX2 = boardWidth;
        }
        if (cactusX3 <= 0) {
            cactusX3 = boardWidth;
        }
    }

    boolean collision() {
        int dinoRight = dinoX + 50;
        int dinoBottom = dinoY + 50;

        int cactusRight1 = cactusX1 + 50;
        int cactusBottom1 = cactusY + 50;

        int cactusRight2 = cactusX2 + 50;
        int cactusBottom2 = cactusY + 50;

        int cactusRight3 = cactusX3 + 50;
        int cactusBottom3 = cactusY + 50;

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
        if (!gameOver) {
            updateDino();
            updateCactus();
            score += 0.1; // Increment score
        }
        repaint();
    }
}
