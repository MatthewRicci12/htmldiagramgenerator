package mainpackage;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Label {
	private String message;
	private int[] containerDimensions;
	private Directions direction;
	private Font thisFont;
	
	public Label(String message, int[] containerDimensions, Directions direction, Font thisFont) {
		this.message = message;
		this.containerDimensions = containerDimensions;
		this.direction = direction;
		this.thisFont = thisFont;
	}
	
	public Label(String message, int[] containerDimensions, Directions direction, Graphics g) {
		this.message = message;
		this.containerDimensions = containerDimensions;
		this.direction = direction;
		Graphics g2D = (Graphics2D) g;
		int width = getFontSize(message, (Graphics2D) g);
		
		//TODO: Set fontSize based on width.
		Font font = new Font("Helvetica", Font.PLAIN, 44);
		
		//this.thisFont = font;
		
		
		
		
	}
	
	public Label() {
		message = "";
		containerDimensions = new int[] {0, 0, 0, 0};
		direction = Directions.NONE;
	}
	
	public Font getFont() {
		return thisFont;
	}
	
	public void setFont(Font font) {
		thisFont = font;
	}
	
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String newMessage) {
		message = newMessage;
	}
	
	public int[] getContainerDimensions() {
		return containerDimensions;
	}
	
	public void setContainerDimensions(int[] newDimensions) {
		containerDimensions = newDimensions;
	}
	
	public Directions getDirection() {
		return direction;
	}
	
	public void setDirection(Directions newDirection) {	
		direction = newDirection;
	}
	
	private int getFontSize(String someString, Graphics2D g2D) {
		FontMetrics fm = g2D.getFontMetrics();
		int width = 0;
		for (int i = 0; i < someString.length(); i++) {
			char curChar = someString.charAt(i);
			width += fm.charWidth(curChar);
		}
		
		return 0;
	}
}
