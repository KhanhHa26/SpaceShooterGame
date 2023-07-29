import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
public class Player {
    
    // instance variables
    private int x;
    private int y;
    private int vx;
    private int vy;
    private int diam;
    // constructor
    public Player() {
        x = 195;
        y = 670;
        vx = 5;
        vy = 5;
    }
    public int getX()
    {
        return x; 
    }
    public int getY()
    {
        return y; 
    }

    public void setX(int newX)
    {
        x = newX; 
    }
    public void setY(int newY)
    {
        y = newY; 
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
    
    //draw the explosion
    public void drawDamage(Graphics g){
        Graphics2D g2d1;
        g2d1 = (Graphics2D)g; 
        ImageIcon bomb = new ImageIcon(LearningGraphics.class.getResource("Explosion.png")); 
        g2d1.drawImage(bomb.getImage(), x, y, 50, 50, null);   
    }

    // draw the enemy
    public void drawSelf(Graphics g)
    {
        Graphics2D g2d1;
        g2d1 = (Graphics2D)g; 
        ImageIcon spaceship = new ImageIcon(LearningGraphics.class.getResource("Player.png")); 
        g2d1.drawImage(spaceship.getImage(), x, y, 100, 100, null);
    }

    //when collides with an enemy
    public boolean handleCollision(Enemy anotherEnemy)
    {
        boolean output = false; 
        int thisCenterX = getCenterX(); 
        int thisCenterY = getCenterY();
        int aBCenterX = anotherEnemy.getCenterX();
        int aBCenterY = anotherEnemy.getCenterY(); 
        int raPlayer = 40; 
        int raEnemy = 25; 
        double dis = distance(thisCenterX, aBCenterX, thisCenterY, aBCenterY);
        if (dis <= raEnemy + raPlayer) { 
            output = true; 
        }
        return output; 
    }

    //when collides with boss bullet 
    public boolean handleCollision(BossBullet anotherBossBu)
    {
        boolean output = false; 
        int thisCenterX = getCenterX(); 
        int thisCenterY = getCenterY();
        int aBCenterX = anotherBossBu.getCenterX();
        int aBCenterY = anotherBossBu.getCenterY(); 
        int raPlayer = 30; 
        int raBossBu = 20; 
        double dis = distance(thisCenterX, aBCenterX, thisCenterY, aBCenterY);
        if (dis <= raBossBu + raPlayer) { 
            output = true; 
        }
        return output; 
    }
}