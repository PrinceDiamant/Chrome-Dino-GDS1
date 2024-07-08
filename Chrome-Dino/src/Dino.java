import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Dino extends JPanel implements ActionListener {

    int boardWidth = 800;
    int boardHeight = 550;

    Image dinobackground;
    Image dino;
    Image cactus1;
    Image cactus2;
    Image cactus3;

    int dinoX = 50;
    int dinoY = 400;

    int cactusX1 = 900;
    int cactusX2 = 1200;
    int cactusX3 = 1500;
    int cactusY = 400;

    Random random = new Random();

    boolean spaceTap = false;
    boolean gameOver = false;
    double score = 0;
    double highscore = 0;
    String highscoreFile = "highscores.txt";
    Timer gameTimer;
    Timer jumpTimer;

    Dino() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
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

        dinobackground = new ImageIcon(Objects.requireNonNull(getClass().getResource("./desertdarkbg.png"))).getImage();
        dino = new ImageIcon(Objects.requireNonNull(getClass().getResource("./dinoyellow.png"))).getImage();
        cactus1 = new ImageIcon(Objects.requireNonNull(getClass().getResource("./cactus1.png"))).getImage();
        cactus2 = new ImageIcon(Objects.requireNonNull(getClass().getResource("./cactus2.png"))).getImage();
        cactus3 = new ImageIcon(Objects.requireNonNull(getClass().getResource("./cactus3.png"))).getImage();

        gameTimer = new Timer(1000 / 60, this);
        gameTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            draw(g);
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) throws NumberFormatException, IOException {
        g.drawImage(dinobackground, 0, 0, boardWidth, boardHeight, null);
        g.drawImage(dino, dinoX, dinoY, 70, 70, null);
        drawCactus(g);

        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));

        if (gameOver) {
            g.drawString("Game Over: " + (int) score, boardWidth / 2 - 100, boardHeight / 2);

            highscore = readHighscore();
            if (highscore <= score) {
                highscore = score;
                g.drawString("New Highscore", boardWidth / 2 - 100, boardHeight / 2 - 100);
                saveHighscore(highscore);
            }
            g.drawString("Highscore: " + (int) highscore, boardWidth / 2 - 100, boardHeight / 2 - 50);
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
        jumpTimer = new Timer(3, new ActionListener() {
            int jumpHeight = 150;
            int jumpStep = 7;

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
            }
        });
        jumpTimer.start();
    }

    public void updateCactus() {
        cactusX1 -= 10;
        cactusX2 -= 10;
        cactusX3 -= 10;

        int cactusDistance = random.nextInt(500, 700) ;

        if (cactusX1 <= 0) {
            cactusX1 = 1000 + cactusDistance;
        }
        if (cactusX2 <= 0) {
            cactusX2 = 1000 + cactusDistance;
        }
        if (cactusX3 <= 0) {
            cactusX3 = 1000 + cactusDistance;
        }
    }

    boolean collision() {
        int dinoRight = dinoX + 40;
        int dinoBottom = dinoY + 40;

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

    public void saveHighscore(double highscore) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(highscoreFile))) {
            writer.write(String.valueOf(highscore));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double readHighscore() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(highscoreFile))) {
            return Double.parseDouble(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            updateDino();
            updateCactus();
            score += 0.1;
        }
        repaint();
    }
}
