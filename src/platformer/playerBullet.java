package platformer;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class playerBullet extends particle{
	private static int ximgsize = 100;
	private static int yimgsize = 357;
	private static int bdmg;
	private BufferedImage img;
	
	public playerBullet(double svel, int sx, int sy, int ssize, int dmg) {
		super(svel, sx, sy, ssize);
		bdmg = dmg;
		try {
    		img = ImageIO.read(new File("bulletSprite.png"));
       } catch (IOException ex) {}
	}
	
	public void updateLoop() {
		if (y > 0) {
			y -= vel;
		} else {
			killState = true;
		}
	}
	
	public Image getImg() {
		Image tmp = img.getScaledInstance(ximgsize/size, yimgsize/size, Image.SCALE_SMOOTH);
		return tmp;
	}
	
    public int getHeight() {return yimgsize/size;}
    public int getWidth() {return ximgsize/size;}
    public int getDmg() {return bdmg;}
    public void killBullet() { killState = true;}
    public Rectangle returnHitBox() { return new Rectangle(super.getX(),super.getY(),getWidth(),getHeight());}
}
