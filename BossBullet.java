import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
 
public class BossBullet { 

    // instance variables
    private int x;
    private int y;
    private int diam; 
    private static final int BULLET_SPEED = 4;

    // constructor
    public BossBullet(int x, int y) {
        this.x = x;
        this.y = y;
        diam = 10; 
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public int getCenterX()
    {
        return x + diam/2; 
    }

    public int getCenterY()
    {
        return y + diam/2; 
    }
    public int getDiam() { 
        return diam; 
    }

    // move the bullet down the screen
    public void move() {
        y += BULLET_SPEED;
    }

    // draw the bullet
    public void drawSelf(Graphics g) {
        Graphics2D g2d;
        g2d = (Graphics2D)g; 
        ImageIcon background = new ImageIcon(LearningGraphics.class.getResource("BossBullet.png")); 
        g2d.drawImage(background.getImage(), x, y, 45, 45, null);
    }

    // check if the bullet has gone off the screen
     public boolean isOffScreen() {
        boolean output = false; 
        int nextY = y - BULLET_SPEED; 
        if (nextY <= 0) { 
            return true; 
        }
        return output; 
    }
}