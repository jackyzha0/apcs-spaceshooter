package platformer;

import javax.swing.JFrame;

public class Main extends JFrame {

    public Main()
    {	
    	int Xbound = 500;
    	int Ybound = 1000;
    	add(new Board());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(Xbound,Ybound);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
     
    public static void main(String[] args) {
    	new Main();
    }
}