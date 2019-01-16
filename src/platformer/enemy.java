package platformer;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
public abstract class enemy {
    BufferedImage img;
    private int xpos;
    private int ypos;
    int hp;
    int xvel;
    int yvel;
    int width;
    int height;
    boolean killState = false;
    ArrayList<enemyBullet> queue;
	ArrayList<e_bombership> bshipq;
    
    public abstract BufferedImage getSprite();
    public boolean getKillstate() { return killState; }
    public abstract void updateLoop();
    public int getX() {return xpos;}
    public int getY() {return ypos;}
    public int getHeight() {return height;}
    public int getWidth() {return width;}
    public Rectangle returnHitBox() { return new Rectangle(xpos,ypos,width,height);}
    public void setX(int x) {xpos = x;}
    public void setY(int y) {ypos = y;}
    public void hit(int dmg) { hp -= dmg; }
    public void setHP(int nhp) {hp = nhp;}
    public int getHP() {return hp;};
    public int getXVel() {return xvel; }
    public void setXVel(int nvel) {xvel = nvel;}
    public int getYVel() {return yvel; }
    public void setYVel(int nvel) {yvel = nvel;}
	public void kill() {killState = true;}
	public int retType() { return 0; }
}