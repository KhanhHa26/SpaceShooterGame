import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Font;
import java.util.ArrayList;

//imports for drawing Images
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public class LearningGraphics extends JComponent implements KeyListener, MouseListener, MouseMotionListener {
    //instance variables
    private int WIDTH;
    private int HEIGHT;
    private int rX, rY, rW, rH;
    private int cX, cY, diam, cVx, cVy;
    private Bubble bub; 
    private Bubble anotherBub; 
    private ArrayList<Bubble> bubbles;

    //Default Constructor
    public LearningGraphics() {
    //initializing instance variables
    WIDTH = 1000;
    HEIGHT = 500;
    rX = 300; 
    rY = 300; 
    rW = 50; 
    rH = 100; 
    cX = 500; 
    cY = 300; 
    diam = 70; 
    cVx = 5; 
    cVy = 5; 

    //Bubbles stuff 
    bub = new Bubble(400, 100, 50, Color.RED); 
    anotherBub = new Bubble(400, 110, 100, Color.PINK); 
    bubbles = new ArrayList<Bubble>(); 

    //Setting up the GUI
    JFrame gui = new JFrame(); //This makes the gui box
    gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Makes sure program can close
    gui.setTitle("Tutorial"); //This is the title of the game, you can change it
    gui.setPreferredSize(new Dimension(WIDTH + 5, HEIGHT + 30)); //Setting the size for gui
    gui.setResizable(false); //Makes it so the gui cant be resized
    gui.getContentPane().add(this); //Adding this class to the gui

    gui.pack(); //Packs everything together
    gui.setLocationRelativeTo(null); //Makes so the gui opens in the center of screen
    gui.setVisible(true); //Makes the gui visible
    gui.addKeyListener(this);//stating that this object will listen to the keyboard
    gui.addMouseListener(this); //stating that this object will listen to the Mouse
    gui.addMouseMotionListener(this); //stating that this object will acknowledge when the Mouse moves
    }

    //This method will acknowledge user input
    public void keyPressed(KeyEvent e) {
    //getting the key pressed
        int key = e.getKeyCode(); 
        System.out.println(key);

    //moving the rectangle
        if (key == 38) { 
            rY-=10; 
        } else if (key == 40) { 
            rY += 10; 
        } else if (key == 37) { 
            rX -= 10; 
        } else { 
            rX += 10; 
        }
    }
    //All your UI drawing goes in here
    public void paintComponent(Graphics g) {
        g.setColor(Color.BLUE); 
        g.fillRect(0,0,WIDTH,HEIGHT); 
        Graphics2D g2d;
        g2d = (Graphics2D)g; 
        ImageIcon im = new ImageIcon(LearningGraphics.class.getResource("Sakura.png")); 
        g2d.drawImage(im.getImage(), 100, 100, 300, 300, null);
       
    //Drawing Hello World!! at the center of the GUI
        Font font = new Font("Ariel", Font.BOLD, 50);
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString("Hello Ms.Lee!!", WIDTH/2, HEIGHT/2);
    //Drawing the user-controlled rectangle
        g.setColor(Color.RED); 
        g.fillRect(rX, rY, rW, rH);
    //Drawing the autonomous circle
        g.setColor(Color.YELLOW);
        g.fillOval(cX, cY, diam, diam);

    //Bubble stuff 
        bub.drawSelf(g);
        anotherBub.drawSelf(g);

        for(int i = 0; i < bubbles.size();i++) { 
            bubbles.get(i).drawSelf(g);
        }
    }
    public void loop() {
    //making the autonomous circle move
        cX+=cVx;
        cY+=cVy;
    //handling when the circle collides with the edges
        int nextX = cX + cVx; 
        int nextY = cY + cVy; 
        if(nextY + diam >= HEIGHT)
            cVy *= -1;
        else if(nextY <= 0)
            cVy *= -1;
        else if(nextX + diam >= WIDTH)
            cVx *= -1;
        else if(nextX <= 0)
            cVx *= -1;
    //handling the collision of the circle with the rectangle
        if (detectCollision()) { 
            cVx *= -1; 
            cVy *= -1; 
        }

    //handling the collision of the circle with circle
        bub.handleCollision(anotherBub);
        anotherBub.handleCollision(bub);

        bub.act(WIDTH, HEIGHT);
        anotherBub.act(WIDTH, HEIGHT);

        for(int i = 0; i < bubbles.size();i++) {
            for(int j = i+1; j < bubbles.size();j++) {
                bubbles.get(i).handleCollision(bubbles.get(j));
                bubbles.get(j).handleCollision(bubbles.get(i));
            }   
        }

        for (int i = 0; i < bubbles.size(); i++) { 
            bubbles.get(i).act(WIDTH, HEIGHT);
        }

    //Do not write below this
        repaint();
    }
    
    //These methods are required by the compiler.
    //You might write code in these methods depending on your goal.
    public void keyTyped(KeyEvent e)
    {
    }
    public void keyReleased(KeyEvent e)
    {
    }
    public void mousePressed(MouseEvent e)
    {
        
    }
    public void mouseReleased(MouseEvent e)
    {
    }
    public void mouseClicked(MouseEvent e) {
        int xCor = e.getX(); 
        int yCor = e.getY(); 
        Bubble newBub = new Bubble(xCor, yCor, 75, Color.MAGENTA); 
        bubbles.add(newBub); 
    }
    public void mouseEntered(MouseEvent e)
    {
    }
    public void mouseExited(MouseEvent e)
    {
    }
    public void mouseMoved(MouseEvent e)
    {
    }
    public void mouseDragged(MouseEvent e)
    {
    } 
    public void start(final int ticks) {
        Thread gameThread = new Thread(){
        public void run(){
        while(true){
            loop();
            try{
                Thread.sleep(1000 / ticks);
            }catch(Exception e){
                e.printStackTrace();
            }
        }   
        }
    };
    gameThread.start();
    }

    //calculating the distance 
    public double distance(int x1, int x2, int y1, int y2) { 
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1)); 
    }

    //detect collision between the rectangle and the circle
    public boolean detectCollision() { 
        boolean output = false; 
        int radius = diam/2; 
        int centerX, centerY; 
        int nextX = cX + cVx; 
        int nextY = cY + cVy; 
        centerX = (2*nextX + diam)/2; 
        centerY = (2*nextY + diam)/2; 
        for (int i = rX; i < rX + rW; i++) { 
            for (int j = rY; j < rY + rH; j++) { 
                if (distance(i, centerX, j, centerY) < radius) { 
                    output = true; 
                }
            }
        }
        return output; 
    }
    public static void main(String[] args)
    {
    LearningGraphics g = new LearningGraphics();
    g.start(60);
    }
}
