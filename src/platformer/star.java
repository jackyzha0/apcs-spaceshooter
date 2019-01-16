package platformer;

public class star extends particle{
	
	public star(double svel, int sx, int sy, int ssize) {
		super(svel,sx,sy,ssize);
	}
	
	public void updateLoop() {
		if (y < 1000) {
			y += vel;
		} else {
			killState = true;
		}
	}
}
