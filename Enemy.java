import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
public class Enemy {
    
    // instance variables
    private int x;
    private int y;
    private double vx;
    private double vy;
    private int diam;

    // constructor
    public Enemy() {
        x =(int)(Math.random() * (500));
        y = 50;
        vx = 1;
        vy = 1;
    }
    public Enemy(int xc, int yc) {
        x =xc;
        y = yc;
        vx = 1;
        vy = 1;
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
    // move the enemy down the screen
    public void move() {
        y += vy; 
    }
    
    // draw the enemy
    public void drawSelf(Graphics g)
    {
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, diam, diam);

        Graphics2D g2d;
        g2d = (Graphics2D)g; 
        ImageIcon enemy = new ImageIcon(LearningGraphics.class.getResource("duckLeft0.png")); 
        g2d.drawImage(enemy.getImage(), x, y, 50, 50, null);        
    }
 
    //draw the explosion when dead 
    public void drawDeath(Graphics g){
        Graphics2D g2d;
        g2d = (Graphics2D)g; 
        ImageIcon bomb = new ImageIcon(LearningGraphics.class.getResource("Explosion.png")); 
        g2d.drawImage(bomb.getImage(), x, y, 50, 50, null);   
    }

    public void handleCollision(Enemy anotherEnemy)
    {
        int thisCenterX = getCenterX(); 
        int thisCenterY = getCenterY();
        int aBCenterX = anotherEnemy.getCenterX();
        int aBCenterY = anotherEnemy.getCenterY(); 
        int raThis = getDiam()/2; 
        int raAnother = anotherEnemy.getDiam()/2; 
        double dis = distance(thisCenterX, aBCenterX, thisCenterY, aBCenterY);
        if (dis <= raThis + raAnother) { 
            vx = thisCenterX-aBCenterX;
            vy = thisCenterY-aBCenterY;
            int maxSpeed = 5;
            if(vx<=-maxSpeed)
                vx = -maxSpeed;
            else if(vx>=maxSpeed)
                vx = maxSpeed;

            if(vy <= -maxSpeed)
                vy = -maxSpeed;
            else if(vy >= maxSpeed)
                vy = maxSpeed;
        } 
    }

    //collides with the bullet 
    public boolean handleBullet(Bullet bullet)
    {
        boolean output = false; 
        int thisCenterX = getCenterX(); 
        int thisCenterY = getCenterY();
        int aBCenterX = bullet.getCenterX();
        int aBCenterY = bullet.getCenterY(); 
        int raEnemy = 25; 
        int raBullet = 10; 
        double dis = distance(thisCenterX, aBCenterX, thisCenterY, aBCenterY);
        if (dis < raEnemy + raBullet) {
            output = true; 
        }
        return output; 
    }
}