//bunch of importing stuff
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Font;
import java.awt.FontFormatException;
import java.util.ArrayList;
import java.applet.*;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

public class SpaceShooterGame extends JComponent implements KeyListener, MouseListener, MouseMotionListener
{
    //instance variables
    //game states
    private static int titleState = 0; 
    private static int playState = 1; 
    private static int instructionState = 2; 
    private static int gameWin = 3; 
    private static int gameLose = 4; 
    private static int gameState; 
    private int commandNum = 1; 
    private ArrayList<Boss>b;
    //width and height of the window
    private int WIDTH;
    private int HEIGHT;
    //main instance variables
    private ArrayList<Bullet> bullet; 
    private ArrayList<BossBullet> bossBullet; 
    private ArrayList<Enemy> enemies;
        private ArrayList<Integer> lines;
    private ArrayList<Enemy> enemiesHit;
    private ArrayList<Enemy> enemiesTouch;
    private Player player; 
    private ArrayList<Lives> lives;
    private Boss boss = new Boss();  
    private static int record;
    private static int points;
    //sound and design
    private AudioClip shootingSound; 
    private AudioClip backgroundSound;  
    private Font marumoFont; 

    //additionals of main
    private int levels;
    private Lives live1;
    private Lives live2; 
    private Lives live3; 
    private boolean bossAppear; 

    //Default Constructor
    public SpaceShooterGame()
    {
        //initializing instance variables
        WIDTH = 500;
        HEIGHT = 800;
        bullet = new ArrayList<Bullet>(); 
        enemies = new ArrayList<Enemy>(); 
        enemiesHit = new ArrayList<Enemy>(); 
        enemiesTouch = new ArrayList<Enemy>(); 
        bossBullet = new ArrayList<BossBullet>(); 
        player = new Player(); 
        lives = new ArrayList<Lives>(); 
        live1 =  new Lives();
        live2 =  new Lives();
        live3 =  new Lives();
        lives.add(live1);
        lives.add(live2);
        lives.add(live3);
        backgroundSound = Applet.newAudioClip(this.getClass().getResource("souvenirs_ukbhEJI.wav"));
        shootingSound = Applet.newAudioClip(this.getClass().getResource("src_main_resources_sounds_shoo.wav"));
        lines = new ArrayList<Integer>();
        lines.add(-50);
        points = 0;
        record = 0; 
        b = new ArrayList<Boss>();
        b.add(boss);
        levels = 5;
        InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf"); 
        try {
            marumoFont = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        //Setting up the GUI
        JFrame gui = new JFrame(); //This makes the gui box
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Makes sure program can close
        gui.setTitle("Space Shooting Game"); //This is the title of the game, you can change it
        gui.setPreferredSize(new Dimension(WIDTH, HEIGHT)); //Setting the size for gui
        gui.setResizable(false); //Makes it so the gui cant be resized
        gui.getContentPane().add(this); //Adding this class to the gui

        gui.pack(); //Packs everything together
        gui.setLocationRelativeTo(null); //Makes so the gui opens in the center of screen
        gui.setVisible(true); //Makes the gui visible
        gui.addKeyListener(this);//stating that this object will listen to the keyboard
        gui.addMouseListener(this); //stating that this object will listen to the Mouse
        gui.addMouseMotionListener(this); //stating that this object will acknowledge when the Mouse moves
        }

    //All your UI drawing goes in here
    public void paintComponent(Graphics g)
    {   
        //drawings for the menu panel
        if (titleState == gameState) { 
            //background
            Graphics2D g2d;
            g2d = (Graphics2D)g; 
            ImageIcon background = new ImageIcon(LearningGraphics.class.getResource("Background2.png")); 
            g2d.drawImage(background.getImage(), 0, 0, WIDTH, HEIGHT, null);

            //create the title as well as shadow
            // Font font = new Font(marumoFont, Font.BOLD, 45);
            g.setFont(marumoFont);
            g.setFont(g.getFont().deriveFont(Font.BOLD, 51F));
            //shadow
            g.setColor(Color.GRAY);
            g.drawString("Space Shooting", WIDTH/5 - 50, HEIGHT/2 - 30);
            //title
            g.setColor(Color.WHITE);
            g.drawString("Space Shooting", WIDTH/5 - 52, HEIGHT/2 - 3 - 30);

            Graphics2D g2d1;
            g2d1 = (Graphics2D)g; 
            ImageIcon spaceship = new ImageIcon(LearningGraphics.class.getResource("Player.png")); 
            g2d1.drawImage(spaceship.getImage(), 150, 100, 200, 200, null);

            //choices
            g.setFont(marumoFont);
            g.setFont(g.getFont().deriveFont(Font.BOLD, 32F));
            //NEW GAME
            g.setColor(Color.WHITE);
            g.drawString("NEW GAME", WIDTH/3 - 10, HEIGHT/2 + 50);

            //the cursor
            if (commandNum == 0) { 
                g.drawString(">", WIDTH/3 - 10 -  50, HEIGHT/2 + 50);
            }

            //INSTRUCTION
            g.drawString("INSTRUCTION", WIDTH/3 - 15, HEIGHT/2 + 100);
            if (commandNum == 1) { 
                g.drawString(">", WIDTH/3 - 15 -  50, HEIGHT/2 + 100);
            }

            //QUIT
            g.drawString("QUIT", WIDTH/3 - 15, HEIGHT/2 + 150);
            if (commandNum == 2) { 
                g.drawString(">", WIDTH/3 - 15 -  50, HEIGHT/2 + 150);
            }

        //drawings for instructions 
        } else if (gameState == instructionState) { 
            Graphics2D g2d;
            g2d = (Graphics2D)g; 
            ImageIcon background = new ImageIcon(LearningGraphics.class.getResource("Background2.png")); 
            g2d.drawImage(background.getImage(), 0, 0, WIDTH, HEIGHT, null);

            //instructions
            g.setFont(marumoFont);
            g.setFont(g.getFont().deriveFont(Font.BOLD, 18F));
            g.setColor(Color.WHITE);
            g.drawString("What you have to do is very simple hehe: ", 70, 100);
            g.drawString("- Shoot all the ducks by clicking the mouse.", 45, 150);
            g.drawString("- Control the player by arrow control keys.", 45, 200);
            g.drawString("- If you touch the duck you die, so try to dodge!", 45, 250);
            g.drawString("- And be ready for the worst!", 45, 300);
            g.drawString("Conditions: ", 70, 350);
            g.drawString("- Shoot a duck: 10 points ", 45, 400);
            g.drawString("- Boss appears only when you reach 100 points. ", 45, 450);
            g.drawString("- ENTER: To QUIT during the game", 45, 500);

            Graphics2D g2d1;
            g2d1 = (Graphics2D)g; 
            ImageIcon duck = new ImageIcon(LearningGraphics.class.getResource("duckicon.png")); 
            g2d1.drawImage(duck.getImage(), 280, 550, 200, 200, null);

        //drawings for the main game
        } else if (gameState == playState) {
            //background
            Graphics2D g2d;
            g2d = (Graphics2D)g; 
            ImageIcon background = new ImageIcon(LearningGraphics.class.getResource("Background.png")); 
            g2d.drawImage(background.getImage(), 0, 0, WIDTH, HEIGHT, null);
        
            //draw the points 
            g.setFont(marumoFont);
            g.setFont(g.getFont().deriveFont(Font.BOLD, 18F));
            g.setColor(Color.WHITE);
            g.drawString("POINTS: " + points, WIDTH - 120, HEIGHT - 40);

            //draw the player 
            player.drawSelf(g);

            //draw the bullet 
            for(int i = 0; i < bullet.size();i++) { 
                bullet.get(i).drawSelf(g);
            }
            
            //draw the enemies 
            for(int i = 0; i < enemies.size(); i++){
                enemies.get(i).drawSelf(g);
            }

            //when the enemy is hit, explode 
            for(int i = 0; i < enemiesHit.size(); i++){
                enemiesHit.get(i).drawDeath(g);
                enemiesHit.remove(i);
                points += 10;
                if(points>record){
                    record = points; 
                }
            }

            //if the user touches the duck, explode
            for(int i = 0; i < enemiesTouch.size(); i++){
                enemiesTouch.get(i).drawDeath(g);
                enemiesTouch.remove(i); 
            }

            //update the lives
            int location = 0 ; 
            for (int i = 0; i < lives.size(); i++) { 
                live1.drawSelf(g, location);
                location += 30; 
            }

            //boss appears when the player kills 10 ducks
            if (points >= 100) { 
                boss.drawSelf(g); 
                bossAppear = true; 

            }
            if (bossAppear && boss.getLives() > 0) {
                for(int i = 0; i < bossBullet.size();i++) { 
                    bossBullet.get(i).drawSelf(g);
                }
            }
        //drawing for game over 
        //if the user wins
        } else if (gameState == gameWin) { 
            Graphics2D g2d;
            g2d = (Graphics2D)g; 
            ImageIcon background = new ImageIcon(LearningGraphics.class.getResource("Background.png")); 
            g2d.drawImage(background.getImage(), 0, 0, WIDTH, HEIGHT, null);
            g.setFont(marumoFont);
            g.setFont(g.getFont().deriveFont(Font.BOLD, 52F));
            g.setColor(Color.GRAY);
            g.drawString("GAME OVER", WIDTH/4 - 30, HEIGHT/2);
            g.setColor(Color.WHITE);
            g.drawString("GAME OVER", WIDTH/4 - 32, HEIGHT/2-3);
            g.setColor(Color.WHITE);
            g.drawString("Record: " + record, WIDTH/4-10, HEIGHT - 200);
            g.setColor(Color.GRAY);
            g.drawString("YOU WIN!", WIDTH/4-2, HEIGHT/2 - 97);
            g.setColor(Color.WHITE);
            g.drawString("YOU WIN!", WIDTH/4, HEIGHT/2 - 100);
        //if the user loses
        } else if (gameState == gameLose) { 
            Graphics2D g2d;
            g2d = (Graphics2D)g; 
            ImageIcon background = new ImageIcon(LearningGraphics.class.getResource("Background.png")); 
            g2d.drawImage(background.getImage(), 0, 0, WIDTH, HEIGHT, null);
            g.setFont(marumoFont);
            g.setFont(g.getFont().deriveFont(Font.BOLD, 52F));
            g.setColor(Color.GRAY);
            g.drawString("GAME OVER", WIDTH/4 - 30, HEIGHT/2);
            g.setColor(Color.WHITE);
            g.drawString("GAME OVER", WIDTH/4 - 32, HEIGHT/2-3);
            g.setColor(Color.WHITE);
            g.drawString("Record: " + record, WIDTH/4-10, HEIGHT - 200);
            g.setColor(Color.GRAY);
            g.drawString("YOU LOSE!", WIDTH/4-8, HEIGHT/2 - 97);
            g.setColor(Color.WHITE);
            g.drawString("YOU LOSE!", WIDTH/4-10, HEIGHT/2 - 100);            }
    }

    public void loop()
    { 
        //BULLET-------------------------------------------------------------------------
        //shoot the bullet 
        for (int i = 0; i < bullet.size(); i++) { 
            bullet.get(i).move();
        }

        //if the bullets go out of screen, remove from the list 
        for(int i = 0; i < bullet.size(); i++){
            if(bullet.get(i).getY()<0){
                bullet.remove(i);
            }
        }


        //when the bullet hits the enemy 
        for(int i = 0; i < enemies.size(); i++){
            for(int j = 0; j < bullet.size(); j++){
                boolean getHit= enemies.get(i).handleBullet(bullet.get(j));
                if(getHit){
                    if(enemies.get(i).getX()>0 && enemies.get(i).getY()<HEIGHT && enemies.get(i).getX()<WIDTH && enemies.get(i).getY()>0){
                        shootingSound.play(); 
                        bullet.remove(j);
                        enemiesHit.add(enemies.get(i)); 
                        enemies.remove(i);
                    }      
                }
            }
        }

        //ENEMIES-------------------------------------------------------------------------
        //spawn the enemies 
        int index = 0;
        if (gameState == playState) { 
            for(int i = 0; i < levels; i++){
                Enemy e = new Enemy(70 + (int)(Math.random()*WIDTH-70), lines.get(0));
                boolean close = true;
                enemies.add(e);
                enemies.get(index).move();
                index++; 
            }
        }

        //if the enemies go out of screen, remove from the list 
        for(int i = 0; i < enemies.size(); i++){
            if(enemies.get(i).getY()>HEIGHT){
                enemies.remove(i);
            }
        }

        //when the enemy hits the player 
        for (int i = 0; i < enemies.size(); i++) { 
            boolean getHit = player.handleCollision(enemies.get(i)); 
            if (getHit) {  
                enemiesTouch.add(enemies.get(i));            
                enemies.remove(i);    
                lives.remove(lives.size()- 1);
            }            
            getHit = false; 

            if (lives.size() == 0) { 
                gameState = gameLose; 
            } else if (boss.getLives() <= 0) {
                gameState = gameWin; 
            }
        }

        //BOSS-------------------------------------------------------------------------
        //if the boss is hit by the bullet 
        for(int j = 0; j < bullet.size(); j++){
            boolean hit = boss.handleBullet(bullet.get(j));
            if(hit){
                    shootingSound.play(); 
                    bullet.remove(j);
                    boss.decreaseHP();
                    if (boss.getLives() <= 0) { 
                        points+= 100; 
                        b.remove(0);
                    }
                }      
            }

        //if the boss appears 
        //the boss shoots the bullet 
        if (bossAppear) {
            if (boss.getY()<50){
                boss.move();
            } else { 
                int num = (int)(Math.random() * 100 + 1); 
                if (num < 3) {
                    BossBullet bb = new BossBullet(225, 160);
                    bossBullet.add(bb);
                }
                for (int i = 0; i < bossBullet.size(); i++) {
                    bossBullet.get(i).move();
                }
            }
        }

        //when the bullet of the boss hits the player 
        for (int i = 0; i < bossBullet.size(); i++) { 
            boolean getHit = player.handleCollision(bossBullet.get(i)); 
            if (getHit) {  
                bossBullet.remove(i);    
                lives.remove(lives.size()- 1);
            }            
            getHit = false; 
            if (lives.size() == 0) { 
                gameState = gameLose; 
            } else if (boss.getLives() <= 0) {
                gameState = gameWin; 
            } 
        }

        //if the boss bullets go out of screen, remove from the list 
        for(int i = 0; i < bossBullet.size(); i++){
            if(bossBullet.get(i).getY()>HEIGHT){
                bossBullet.remove(i);
            }
        }

    //Do not write below this
    repaint();
    }

    //This method will acknowledge user input
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode(); 
        //in the menu panel
        if (gameState == titleState) { 
        //moving the cursor 
            if (key == 38) { 
                commandNum--; 
                if (commandNum < 0) { 
                    commandNum = 2; 
                }
            } else if (key == 40) { 
                commandNum++; 
                if (commandNum > 2) { 
                    commandNum = 0; 
                }
            }
            //clicking enter 
            if (key == 10) { 
                //if the player chooses "NEW GAME"
                if (commandNum == 0) { 
                    gameState = playState;
                    backgroundSound.loop();
                //if the player chooses "QUIT"
                } else if (commandNum == 2) { 
                    System.exit(0);
                //if the player chooses "INSTRUCTION"
                } else if (commandNum == 1) { 
                    gameState = instructionState; 
                }
            }

        } else if (gameState == playState) { 
            //moving the spaceship
            //and make sure that it can't go off screen
            if (key == 38 && player.getY() - 12 >= 0) { 
                player.setY(player.getY()-12); 
            } else if (key == 40 && player.getY() + 12 <= 690) { 
                player.setY(player.getY()+12); 
            } else if (key == 37 && player.getX() - 12 >= 0) { 
                player.setX(player.getX()-12);
            } else if (key == 39 && player.getX() + 12 <= 400) { 
                player.setX(player.getX()+12);
            } else if (key == 10) { 
                System.exit(0); 
            }
        }
    }
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
    public void mouseClicked(MouseEvent e)
    {
        //if mouse is clicked, bullet is shot 
        int bX = player.getX()+ 40; 
        int bY = player.getY(); 
        Bullet aBu = new Bullet(bX, bY); 
        bullet.add(aBu); 
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

    //calculating the distance 
    public double distance(int x1, int x2, int y1, int y2) { 
        double a = Math.pow((x2 - x1), 2); 
        double b = Math.pow((y2 - y1), 2); 
        double output = Math.sqrt(a + b); 
        return output; 
    }

    public void start(final int ticks){
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

    public static void main(String[] args)
    {
        SpaceShooterGame g = new SpaceShooterGame();
        g.start(60);
    }
}

