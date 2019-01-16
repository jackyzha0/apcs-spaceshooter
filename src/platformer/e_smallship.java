package platformer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class e_smallship extends enemy{
	
	int cycle = 5;
	
	public e_smallship(int x, int y, int vel) {
		setX(x); setY(y); setYVel(vel);
    	width = 50;
    	height = 50;
    	setHP((int)Math.random()*20+100);
    	try {
    		img = ImageIO.read(new File("enemySprites.png"));
    		img = img.getSubimage(100, 0, width, height);
       } catch (IOException ex) {}
 
	}

	public BufferedImage getSprite() {
		return img;
	}

	public void updateLoop() {
		queue = null;
		
		if ((int)(Math.random()*75) == 0) {
			queue = new ArrayList<enemyBullet>();
			queue.add(new enemyBullet((Math.random()*2)-0.5,(int)(Math.random()*3)+3, getX()+20, getY()+20));
			queue.add(new enemyBullet((Math.random()*2)-0.5,(int)(Math.random()*3)+3, getX()+20, getY()+20));
		}
		
		if (cycle == 5) {
			setY(getY()+yvel);
			cycle = 0;
		}
		cycle++;
		
		
		if (getY() > 1000) {
			killState = true;
		}
	}

}
