package platformer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class e_boss extends enemy{
	
	int cycle = 8;
	
	public e_boss(int HP) {
		setX(128); setY(0); setYVel(1);
    	width = 250;
    	height = 100;
    	setHP(HP);
    	try {
    		img = ImageIO.read(new File("bosssprite.png"));
       } catch (IOException ex) {}
	}
	
	public BufferedImage getSprite() {
		return img;
	}

	public void updateLoop() {
		queue = null;
		bshipq = null;
		queue = new ArrayList<enemyBullet>();
		
		if ((int)(Math.random()*100) == 0) {
			if ((int)(Math.random()*2) == 1) {
				for (int i = 0; i < 8; i++) {
					queue.add(new enemyBullet(i-4, (int)(Math.random()*6)+1, getX()+128, getY()+80));
				}
			} else {
				for (int i = 0; i < 12; i++) {
					queue.add(new enemyBullet(i-6, (int)(Math.random()*2)+1, getX()+128, getY()+80));
				}
			}
		}
		
		if ((int)(Math.random()*5) == 0) {
			for (int i = 0; i < 4; i++) {
				queue.add(new enemyBullet((int)(Math.random()*4)-2, 6,getX()+128, getY()+80));
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
		
		
		if (hp < 0) {
			killState = true;
		}
	}
	public int retType() { return 2; }

}
