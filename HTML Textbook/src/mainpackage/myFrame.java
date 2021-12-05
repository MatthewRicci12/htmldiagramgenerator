package mainpackage;

import javax.swing.JFrame;

public class myFrame extends JFrame {
	
	myPanel panel;
	
	myFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setSize(1080, 750)
		this.add(new myPanel());
		this.pack();
		this.setVisible(true);
	}
	
//	public void tutorial(Graphics g) {
//		Graphics g2D = (Graphics2D) g;
//		
//		
//	}
}
