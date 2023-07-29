import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Lives {
    private int x, y; 
    private static int numLives; 

    public Lives() { 
        x = 0; 
        y = 720; 
        numLives = 3; 
    }

    public int getLives() { 
        return numLives; 
    }

    public void setLives() { 
        numLives--; 
    }

    public void drawSelf(Graphics g, int location) { 
        Graphics2D g2d2;
        g2d2 = (Graphics2D)g; 
        ImageIcon lives = new ImageIcon(LearningGraphics.class.getResource("heart_full.png")); 
        g2d2.drawImage(lives.getImage(), location , y, 30, 30, null);
    }
}
