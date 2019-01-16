package platformer;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class enemyBullet extends playerBullet{
	private static int ximgsize = 10;
	private static int yimgsize = 10;
	private static int bdmg;
	private BufferedImage img;
	double xvel;
	
	public enemyBullet(double xv, double yvel, int sx, int sy) {
		super(yvel, sx, sy, 0, 100);
		xvel = xv;
		try {
    		img = ImageIO.read(new File("enemyBullet.png"));
       } catch (IOException ex) {}
	}
	
	public void updateLoop() {
		if (y < 1000 && x > 0 && x < 500) {
			y += vel;
			x += xvel;
		} else {
			killState = true;
		}
	}
	
	public Image getImg() {
		return img;
	}
	
    public int getHeight() {return yimgsize;}
    public int getWidth() {return ximgsize;}
    public int getDmg() {return bdmg;}
    public void killBullet() { killState = true;}
    public Rectangle returnHitBox() { return new Rectangle(super.getX(),super.getY(),getWidth(),getHeight());}
}
