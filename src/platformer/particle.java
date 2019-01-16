package platformer;

import java.awt.Rectangle;

public abstract class particle {
	double vel;
	int x;
	int y;
	int size;
	boolean killState = false;
	
	public particle(double svel, int sx, int sy, int ssize) {
		y = sy;
		vel = svel;
		x = sx;
		size = ssize;
	}
	
	public abstract void updateLoop();
	
	public int getX() { return x; }
	public int getY() { return y; }
	public boolean getKillstate() { return killState; }
	public int getSize() { return size;}
	public void setY(int ynew) { y = ynew;}
	public boolean kill() { return killState;}
}
