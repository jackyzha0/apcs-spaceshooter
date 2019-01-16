package platformer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class e_spawnership extends enemy{
	
	int cycle = 8;
	
	public e_spawnership(int x, int y, int vel) {
		setX(x); setY(y); setYVel(vel);
    	width = 50;
    	height = 50;
    	setHP((int)Math.random()*50+150);
    	try {
    		img = ImageIO.read(new File("enemySprites.png"));
    		img = img.getSubimage(0, 0, width, height);
       } catch (IOException ex) {}
	}
	
	public BufferedImage getSprite() {
		return img;
	}

	public void updateLoop() {
		queue = null;
		bshipq = null;
		
		if ((int)(Math.random()*250) == 0) {
			queue = new ArrayList<enemyBullet>();
			for (int i = 0; i < 8; i++) {
				queue.add(new enemyBullet(i-4, 5, getX()+20, getY()+20));
			}
		}
		
		if ((int)(Math.random()*200) == 0) {
			bshipq = new ArrayList<e_bombership>();
			bshipq.add(new e_bombership(getX(), getY(), (int)(Math.random()*4)-2,(int)(Math.random()*3)+2, (int)(Math.random()*40)+25));
		}
		
		if (cycle == 8) {
			setY(getY()+yvel);
			cycle = 0;
		}
		cycle++;
		
		
		if (getY() > 1000) {
			killState = true;
		}
	}
	
	public int retType() { return 1; }

}
