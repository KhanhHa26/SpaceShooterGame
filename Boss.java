import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
public class Boss {
    // instance variables
    private int x;
    private int y;
    private double vx;
    private double vy;
    private int diam;
    private int lives;
    // constructor
    public Boss() {
        x = 150;
        y = -100;
        vx = 1;
        vy = 1;
        lives = 200;
    }
    
    public void setX(int xx){
        x = xx;
    }
    public int getX()
    {
        return x; 
    }
    public int getY()
    {
        return y; 
    }
    public int getDiam()
    {
        return diam; 
    }
    private double distance(int x1, int x2, int y1, int y2)
    {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }
    public int getCenterX()
    {
        return x + diam/2; 
    }

    public int getCenterY()
    {
        return y + diam/2; 
    }

    public int getLives() { 
        return lives; 
    }
    // move the enemy down the screen
    public void move() {
        y += vy; 
    }
    
    // draw the enemy
    public void drawSelf(Graphics g)
    {
        Graphics2D g2d;
        g2d = (Graphics2D)g; 
        ImageIcon enemy = new ImageIcon(LearningGraphics.class.getResource("Boss.png")); 
        g2d.drawImage(enemy.getImage(), x, y, 200, 200, null);        
    }

    public void decreaseHP(){
        lives -= 10;
    }

    //collides with the bullet 
    public boolean handleBullet(Bullet bullet)
    {
        boolean output = false; 
        int thisCenterX = getCenterX(); 
        int thisCenterY = getCenterY();
        int aBCenterX = bullet.getCenterX();
        int aBCenterY = bullet.getCenterY(); 
        int raBoss = 100; 
        int raBullet = 10; 
        double dis = distance(thisCenterX, aBCenterX, thisCenterY, aBCenterY);
        if (dis <= raBoss + raBullet) {
            output = true; 
        }
        return output; 
    }
}

