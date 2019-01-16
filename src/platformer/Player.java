package platformer;
import javax.imageio.ImageIO;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Player {
	
    BufferedImage img;
    private int xpos;
    private int ypos;
    private double vel;
    BufferedImage[] sprites;
    playerfume[] pfume;
    int width;
    int height;
    int rows;
    int cols;
    int cycle = 0;
    boolean isDead = false;
    
    public Player(int fumelength) {

    	xpos = 250;
    	ypos = 800;
    	pfume = new playerfume[fumelength];
    	try {
    		img = ImageIO.read(new File("playerspritesheet.png"));
       } catch (IOException ex) {}
    	width = 50;
    	height = 54;
    	rows = 6;
    	cols = 5;
    	vel = 0;
    	sprites = new BufferedImage[rows * cols];
    	
    	for (int i = 0; i < rows; i++)
    	{
    	    for (int j = 0; j < cols; j++)
    	    {
    	        sprites[(i * cols) + j] = img.getSubimage(
    	            j * width,
    	            i * height,
    	            width,
    	            height
    	        );
    	    }
    	}
    }
    
    public BufferedImage getSprite() {
    	int adjustedState;
    	if ((int) vel/2 == 0) {
    		adjustedState = 2;
    	} else {
    		if (vel > 0) {
    			if (vel > 5) {
    				adjustedState = 4;
    			} else {
    				adjustedState = 3;
    			}
    		} else {
    			if (vel < -5) {
    				adjustedState = 0;
    			} else {
    				adjustedState = 1;
    			}
    		}
    	}
    	
    	if (cycle > 4) {
    		cycle = 1;
    	}
    	

    	return sprites[((adjustedState+1)+(cycle*5))-1];
    }
    
    public void updateLoop() {
    	if ((int)vel/1.2 == 0) {
    		vel = 0;
    	}
    	if (vel > 5) {
    		vel = 5;
    	}
    	if (vel < -5) {
    		vel = -5;
    	}
    	vel *= 0.85;
    	
    	if (!((Math.signum(vel) == -1 && xpos < 0) || (Math.signum(vel) == 1 && xpos > 445))) {
    		xpos += vel;
    	}
    }
    
    public int getX() {return xpos;}
    public int getY() {return ypos;}
    public int getHeight() {return height;}
    public int getWidth() {return width;}
    public playerfume[] pfumearr() { return pfume; }
    public Rectangle returnHitBox() { return new Rectangle(xpos+25,ypos+27,width/4,height/4);}
    public void setX(int x) {xpos = x;}
    public void setY(int y) {ypos = y;}
    public void setVel(double d) {vel = d;}
    public double getVel() {return vel;}
    public void changeVel(double amt) {vel+=amt;}
    public void cycle() {cycle++;}
    public void kill() {isDead = true;}
           
}
