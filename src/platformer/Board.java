package platformer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.util.*;
import java.util.concurrent.TimeUnit;
import javax.swing.JPanel;

public class Board extends JPanel implements KeyListener{
	private static final long serialVersionUID = 1L;
	star[] starArr = new star[50];
	Player a = new Player(25);
	shieldPlayer b = new shieldPlayer(15);
	ArrayList<playerBullet> pb = new ArrayList<playerBullet>();
	ArrayList<enemy> e = new ArrayList<enemy>();
	ArrayList<enemyBullet> eb = new ArrayList<enemyBullet>();
	int topscore = 0;
	int upDelay = 10;
	int Level = 0;
	int bulletDelay = 12;
	private int score = 0;
	boolean l, r, s ,l2, r2, s2, skip;
	long lastTime = System.nanoTime();
	boolean gameOver = false;
	boolean debug = false;
	boolean isBoss;
	int bossHP = 0;
	
	public Board() {

    	addKeyListener(this); 
		setFocusable(true);

		for (int i = 0; i < 50; i++) {
			starArr[i] = new star((Math.random()*3)+1,(int)(Math.random()*500),(int)(Math.random()*1000), (int)(Math.random()*3)+1);
		}
		
		
	}
	
	public void paint(Graphics g){
		
		isBoss = false;
		
		//Background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 500, 1000);
		
		//FPS Limiter
		try {
			TimeUnit.MILLISECONDS.sleep(20);;
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Star Field
		for (int i = 0; i < 50; i++) {
			starArr[i].updateLoop();
			if (starArr[i].kill()) {
				starArr[i] = new star((Math.random()*3)+1,(int)(Math.random()*500), 0, (int)(Math.random()*3)+1);
			}
			g.setColor(Color.WHITE);
			Ellipse2D o = new Ellipse2D.Double(starArr[i].getX(),starArr[i].getY(),starArr[i].getSize(), starArr[i].getSize());
			Graphics2D g2 = (Graphics2D) g;
			g2.fill(o);
		}
		
		//Delay for ship animations
		if (upDelay < 0) {
			a.cycle();
			upDelay = 10;
		}
		upDelay--;
		
		
		//Create Enemy
		
		if (skip) {
			e = new ArrayList<enemy>();
			skip = !skip;
		}
		
		if (e.isEmpty()) {
			Level++;
			for (int i = 0; i < (Level*2)+3; i++) {
				int r = (int) (Math.random()*2);
				if (r == 0) {
					e.add(new e_smallship((int)(Math.random()*455), 0, (int)(Math.random()*5)+1));
				} else {
					e.add(new e_spawnership((int)(Math.random()*455), 0, (int)(Math.random()*3)+1));
				}
			}
			if (Level%5 == 0) {
				e.add(new e_boss(1000+(Level*40)));
			}
		}
		
		//Player Update
		if (!a.isDead) {
			a.updateLoop();
			if (l) { a.changeVel(-2); }
			if (r) { a.changeVel(2); }
			g.drawImage(a.getSprite(), a.getX(), a.getY(), this);
			
			//Draw PlayerTrail Main Character
			for (int i = 0; i < 20; i++) {
				if (a.pfumearr()[i] == null || a.pfumearr()[i].kill()) {
					a.pfumearr()[i] = new playerfume((Math.random()*3)+5,(int)(Math.random()*15)+(a.getX()+15), 840, (int)(Math.random()*12)+5);
				}
				a.pfumearr()[i].updateLoop();
				
				g.setColor(new Color((int) (Math.random( )*50)+200,(int) (Math.random( )*150),(int) (Math.random( )*70)));
				Ellipse2D o = new Ellipse2D.Double(a.pfumearr()[i].getX(),a.pfumearr()[i].getY(),a.pfumearr()[i].getSize(), a.pfumearr()[i].getSize());
				Graphics2D g2 = (Graphics2D) g;
				g2.fill(o);
			}
		}
		if (!b.isDead) {
			b.updateLoop();
			if (l2) { b.changeVel(-2); }
			if (r2) { b.changeVel(2); }
			g.drawImage(b.getSprite(), b.getX(), b.getY(), this);
			
			//Draw PlayerTrail Shield Ship
			for (int i = 0; i < 15; i++) {
				if (b.pfumearr()[i] == null || b.pfumearr()[i].kill()) {
					b.pfumearr()[i] = new playerfume((Math.random()*3)+8,(int)(Math.random()*15)+(b.getX()+5), 700, (int)(Math.random()*7)+2);
				}
				b.pfumearr()[i].updateLoop();
				
				g.setColor(new Color((int) (Math.random( )*150),(int) (Math.random( )*50)+150,(int) (Math.random( )*50)+150));
				Ellipse2D o = new Ellipse2D.Double(b.pfumearr()[i].getX(),b.pfumearr()[i].getY(),b.pfumearr()[i].getSize(), b.pfumearr()[i].getSize());
				Graphics2D g2 = (Graphics2D) g;
				g2.fill(o);
			}
		}
		
		//Bullet Creation
		if (s) {
			if (bulletDelay == 0) {
				bulletDelay = 12;
				pb.add(new playerBullet((Math.random()*2)+12,(int)(Math.random()*15)+(a.getX()+15), 800, 8, (int)(Math.random()*10)+Level*10));
			}
			bulletDelay--;
		}

		//Draw Bullets	
		for (int i = 0; i < pb.size(); i++) {
			playerBullet tmp = pb.get(i);
			if (tmp.getKillstate()) {
				pb.remove(i);
			} else {
				g.drawImage(tmp.getImg(), tmp.getX(), tmp.getY(), this);
				pb.get(i).updateLoop();
			}
		}
		
		//Hitbox detection for Bullets
		for (playerBullet bull: pb) {
		    if (bull.returnHitBox().intersects(b.returnHitBox())) {
		    	bull.killBullet();
		    }
		    for (enemy en : e) {
			    if (bull.returnHitBox().intersects(en.returnHitBox())) {
			    	bull.killBullet();
			    	if (en.retType() == 2) {
			    		en.hit(bull.getDmg());
			    	} else {
				    	en.kill();
			    	}
			    	
			    	//Score Modifier
			    	score += 500*Level;
			    	score *= 1+(Level/10);
			    }
		    }
		}
		
		//Update Enemy & Bullets
		ArrayList<enemy> toAdd = new ArrayList<enemy>();
		for (enemy en: e) {
			if (en.queue != null && !(en.queue.isEmpty())) {
				eb.addAll(en.queue);
			}
			
			if (en.retType() == 1 && en.bshipq != null) {
				for (e_bombership eb : en.bshipq) {
					toAdd.add(eb);
				}
			}
			
			if (en.retType() == 2) {
				isBoss = true;
				bossHP = en.getHP();
			}
			
			en.updateLoop();
			g.drawImage(en.getSprite(), en.getX(), en.getY(), this);
			
			if (en.returnHitBox().intersects(b.returnHitBox())) {
				en.kill();
			}
		}
		e.addAll(toAdd);
		
		//Enemy Bullet Loop
		for (int i = 0 ; i < eb.size(); i++) {
			enemyBullet p = eb.get(i);
			p.updateLoop();
			g.drawImage(p.getImg(), p.getX(), p.getY(), this);
			
			Rectangle r = p.returnHitBox();
		    Rectangle shieldplayer = b.returnHitBox();
		    Rectangle mainplayer = a.returnHitBox();
		    
		    if (r.intersects(shieldplayer)) {
		    	p.killBullet();
		    }
		    if (r.intersects(mainplayer)) {
		    	p.killBullet();
		    	a.kill();
		    }
		    if (p.getKillstate()) {
		    	eb.remove(i);
		    }
		}
		
		for (int i = 0; i < e.size(); i++) {
			if (e.get(i).killState) {
				e.remove(i);
			}
		}
		
		//FPS & Debug
		if (debug) {
			g.setColor(Color.WHITE);
			int fps = (int) (1000000000 / (lastTime - (lastTime = System.nanoTime())))*-1;
			g.drawString("FPS: " + fps, 0, 10);
			g.drawString("Level: " + Level, 0, 20);
			g.drawString("Enemy Count: " + e.size(), 0, 30);
			g.drawString("Enemy Bullet Count: " + eb.size(), 0, 40);
		}
		
		String tmp = "" + score;
		while (tmp.length() < 9) {
			tmp = "0" + tmp;
		}
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		g.drawString("Score : " + tmp, 160, 30);
		if (isBoss) {
			g.setColor(new Color(200,20,20));
			g.setFont(new Font("Arial", Font.BOLD, 18));
			g.drawString("Boss HP : " + bossHP, 190, 50);
		}

		
		//Check Gameover
		if (gameOver) {
			g.setFont(new Font("Arial", Font.PLAIN, 36));
			g.setColor(Color.WHITE);
			g.drawString("You died! ", 160, 500);
			
		} else {
			//Score
			//Check if Gameover
			if (a.isDead) {
				gameOver = true;
			}
			score += 1;
			repaint();
		}
	}
	
	public int arrayNullFirstOcc(ArrayList<playerBullet> arr) {
		int fin = -1;
		for (int i = 0; i < arr.size(); i++) {
			if (arr.get(i) == null) {
				return i;
			}
			
		}
		return fin;
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 37) { l = true; } 
		if (e.getKeyCode() == 39) { r = true; }
		if (e.getKeyCode() == 32) { s = true; }
		if (KeyEvent.VK_D == e.getKeyCode()) {r2 = true;}
		if (KeyEvent.VK_A == e.getKeyCode()) {l2 = true;}
		if (KeyEvent.VK_P == e.getKeyCode()) {skip = !skip;}
		if (KeyEvent.VK_BACK_SLASH == e.getKeyCode()) {debug = !debug;}
	}

	public void keyReleased(KeyEvent e) {
		
		if (e.getKeyCode() == 37) { l = false; } 
		if (e.getKeyCode() == 39) { r = false; }
		if (e.getKeyCode() == 32) { s = false; }
		if (KeyEvent.VK_D == e.getKeyCode()) {r2 = false;}
		if (KeyEvent.VK_A == e.getKeyCode()) {l2 = false;}
	}
	public void keyTyped(KeyEvent e) {}
}
