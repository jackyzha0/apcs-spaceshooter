package platformer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class e_bombership extends enemy{
	boolean exploded = false;
	int cycletimer;
	
	public e_bombership(int x, int y, int Xvel, int Yvel, int timer) {
		setX(x); setY(y); setYVel(Yvel); setXVel(Xvel);
    	width = 50;
    	height = 50;
    	cycletimer = timer;
    	
    	setHP(50);
    	try {
    		img = ImageIO.read(new File("enemySprites.png"));
    		img = img.getSubimage(50, 0, width, height);
       } catch (IOException ex) {}
	}
	
	public BufferedImage getSprite() {
		return img;
	}

	public void updateLoop() {
		
		setY(getY()+yvel);
		setX(getX()+xvel);
		cycletimer--;
		
		if (getY() > 1000 || getX() < 0 || getX() > 455 || exploded) {
			killState = true;
		}
		
		if (cycletimer == 0) {
			queue = new ArrayList<enemyBullet>();
			for (int i = 0; i < 12; i++) {
				queue.add(new enemyBullet(i-6, (int)(Math.random()*2)+2, getX()+20, getY()+20));
			}
			exploded = true;
		}
	}


}
