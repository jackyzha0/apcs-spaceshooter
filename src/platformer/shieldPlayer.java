package platformer;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class shieldPlayer extends Player{

	
	public shieldPlayer(int fumelength) {
		super(fumelength);
    	setX(250);
    	setY(680);
    	width = 35;
    	height = 28;
    	
    	try {
    		img = ImageIO.read(new File("playerShield.png"));
       } catch (IOException ex) {}
	}
	
    public void updateLoop() {
    	
    	if ((int)getVel()/1.2 == 0) {
    		setVel(0);
    	}
    	if (getVel() > 8) {
    		setVel(8);
    	}
    	if (getVel() < -8) {
    		setVel(-8);
    	}
    	
    	setVel((getVel()*0.90));
    	
    	if (!((Math.signum(getVel()) == -1 && getX() < 0) || (Math.signum(getVel()) == 1 && getX() > 445))) {
    		setX((int)(getX()+getVel()));
    	}
    }
	
	public BufferedImage getSprite() { return img; }
    public Rectangle returnHitBox() { return new Rectangle(getX(),getY(),width,height);}
}
