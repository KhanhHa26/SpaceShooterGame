import java.awt.Graphics;
import java.awt.Color;
public class Bubble
{
    private int x;
    private int y;
    private int vx;
    private int vy;
    private int diam;
    private Color col;
    public Bubble(int xCoor, int yCoor, int d, Color c)
    {
        x = xCoor; 
        y = yCoor; 
        diam = d; 
        col = c; 
        vx = 1; 
        vy = 1; 

    }
    public String toString()
    {
        return "Location: (" + x + ", " + y + " )" + "\nDiameter: " + diam; 
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
    public void act(int w, int h)
    {
        //get the next x and y coordinates
        int nextX = x + vx; 
        int nextY = y + vy; 
        //if-statements to handle the Bubble bouncing off of the 4 walls
        if (nextY + diam >= 500) { 
            vy *= -1; 
        } else if (nextX + diam >= 1000) { 
            vx *= -1; 
        } else if (nextX <= 0) { 
            vx *= -1; 
        } else if (nextY <= 0) { 
            vy *= -1; 
        }
        //updating x and y
        x+= vx; 
        y += vy; 
    }

    public void drawSelf(Graphics g)
    {
        g.setColor(col);
        g.fillOval(x, y, diam, diam);
    }
    public void handleCollision(Bubble anotherBubble)
    {
        // //Getting the center of this Bubble and anotherBubble
        int thisCenterX = getCenterX(); 
        int thisCenterY = getCenterY();
        int aBCenterX = anotherBubble.getCenterX();
        int aBCenterY = anotherBubble.getCenterY(); 
        // //getting the radius of this Bubble and anotherBubble
        int raThis = getDiam()/2; 
        int raAnother = anotherBubble.getDiam()/2; 
        // //checking if this Bubble Collided with anotherBubble
        double dis = distance(thisCenterX, aBCenterX, thisCenterY, aBCenterY);

        if (dis <= raThis + raAnother) { 
            //calculating the velocities of this Bubble after colliding with anotherBubble
            vx = thisCenterX-aBCenterX;
            vy = thisCenterY-aBCenterY;
            //Slowing down the velocities. otherwise they go crazy
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
}