/* 
 * File: myPanel.java
 * Description: The JPanel for the JFrame extended by the myFrame.java class. Since a 
 * Graphics2D object must be contained in a Container, the panel will be the canvas upon
 * which graphics are designed. The purpose of this class is to generate diagrams for the
 * HTML textbook automatically. Another class will handle a specifically formatted file
 * and make Label objects, which will be added to the panel.
 * 
 * Author: Matthew Ricci
 * Version: 14.0.1+7
 */

package mainpackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import Exceptions.OrientationException;

public class myPanel extends JPanel {
	
	myPanel() {
		this.setPreferredSize(new Dimension(1020, 750));
	}
	
	/*
	 * The brains of the panel that draws shapes and text. It only takes a graphics object.
	 * It will be casted to a Graphics2D object and the magic begins from there.
	 * 
	 * @param g		A Graphics object.
	 */
	public void paint(Graphics g) {
		Graphics g2D = (Graphics2D) g;
		


		
		/* Lines 44-53 generate the base blue rectangles. */
		g2D.setColor(new Color(100, 149, 237));
		g2D.fillRoundRect(385, 5, 250, 100, 50, 50);
		g2D.fillRoundRect(5, 200, 250, 100, 50, 50);
		g2D.fillRoundRect(385, 200, 250, 100, 50, 50);
		g2D.fillRoundRect(765, 200, 250, 100, 50, 50);
		
		g2D.setColor(Color.BLACK);
		g2D.drawLine(510, 105, 510, 152);
		g2D.drawLine(510, 152, 890, 152);
		g2D.drawLine(510, 152, 127, 152);
		
		/* Draw the arrows to point to the bottom rectangles. */
		try {
			arrow((Graphics2D) g2D, new int[] {510, 152}, new int[] {510, 200});
			arrow((Graphics2D) g2D, new int[] {127, 152}, new int[] {127, 200});
			arrow((Graphics2D) g2D, new int[] {890, 152}, new int[] {890, 200});
		} catch (OrientationException e) {
			e.printStackTrace();
		}
		
		/* 
		 * Lines 65-79 generate the text of the bottom 3 default rectangles and make new 
		 * Label objects for them. A loop will call centerRect, which centers any given
		 * text of any given size in a rectangle of any given size.
		 * 
		 */
		
		//Custom, tag font.
		Font curFont = new Font("Helvetica", Font.PLAIN, 52);
		Label tag = new Label("<www>", new int[] {385, 5, 250, 100}, Directions.NONE, 
				new Font("Helvetica", Font.PLAIN, 72));
		centerRect(tag, (Graphics2D) g2D);
		
		Label CSS = new Label("CSS", new int[] {5, 200, 250, 100}, Directions.NONE, new Font("Helvetica", Font.PLAIN, 56));
		Label attribs = new Label("Attributes", new int[] {385, 200, 250, 100}, Directions.NONE, new Font("Helvetica", Font.PLAIN, 48));
		Label pseudo = new Label("Pseudo-", new int[] {765, 200, 250, 100}, Directions.TOP_HALF, new Font("Helvetica", Font.PLAIN, 40));
		Label classes = new Label("Classes", new int[] {765, 200, 250, 100}, Directions.BOTTOM_HALF, new Font("Helvetica", Font.PLAIN, 40));
		Label[] initialLabels = new Label[] {CSS, attribs, pseudo, classes};
		
//		for (int i = 0; i < initialLabels.length; i++) {
//			Label curLabel = initialLabels[i];
//			centerRect(curLabel,
//					(Graphics2D) g2D);
//		}
//		
		/*Now experiment with width vs. fontSize. We may have to update Label class in that
		 * case. Make a table of values and at least try to find some pattern. Or a better
		 * idea: Find the longest and the shortest HTML tags and calibrate THEIR size.
		 * Anyways, the goal is to make some kind of .txt file where we put info for the
		 * tags, then we'd read it in and make Label objects based off of that.
		 */
		
		
		//Consumer<void?> = (int[]) -> {centerRect...};
		//Or just do ForEach.

	}
	
	
	private static int getWidth(String str, Graphics2D g2D) {
		g2D.setFont(new Font("Helvetica", Font.PLAIN, 40));
		FontMetrics fm = g2D.getFontMetrics();
		int width = 0;
		for (int i = 0; i < str.length(); i++) {
			char curChar = str.charAt(i);
			width += fm.charWidth(curChar);
		}
		System.out.println(width + " is the width of " + str);
		return width;
	}
	
	private void populateRectangles() {
		
	}
	
	/*
	 * One of the most important methods, centerRect's job is to draw centered text in a
	 * given rectangle. Given the x, y, width, and height of a rectangle, it will use a 
	 * few simple mathematical formulas to generate the font's x and y. 
	 * 
	 *  @param label		The label to center inside its containing rectangle.
	 *  @param g2D			The Graphics2D object to use.
	 */
	private void centerRect(Label label, Graphics2D g2D) {
		g2D.setFont(label.getFont());
		
		/* Because the # of pixels tall a font is is 3/4 its point size, we only want to
		 * deal with font sizes that are divisible by 4. 
		 * 
		 */
		if (label.getFont().getSize() % 4 != 0) {
			return;
		}
		FontMetrics fm = g2D.getFontMetrics();
		
		/* The dimensions of the containing rectangle. */
		final int RECT_X = label.getContainerDimensions()[0];
		int RECT_Y = label.getContainerDimensions()[1];
		final int RECT_WIDTH = label.getContainerDimensions()[2];
		int RECT_HEIGHT = label.getContainerDimensions()[3];
		
		/* If we are centering it in one half of the rectangle, then the rectangle's 
		 *  height is obviously half of the original. If we are doing the bottom half,
		 *  because the higher the y the lower the object, we want to add on the height
		 *  of the rectangle to the original y to place it that much lower on the canvas.
		 *  And the reason for that is because fontX and fontY refers to the BOTTOM LEFT
		 *  corner of the font.
		 *  
		 */
		if (label.getDirection() == Directions.TOP_HALF || label.getDirection() == Directions.BOTTOM_HALF) {
			RECT_HEIGHT = RECT_HEIGHT / 2;
			if (label.getDirection() == Directions.BOTTOM_HALF) {
				RECT_Y += RECT_HEIGHT;
			}
		} 
		
		/* Calculate the total width of the string by totaling the width of each character. 
		 * Lines 157-172 are entirely for calculating the final string's x and y coordinate.
		 * 
		 */
		int width = 0;
		for (int i = 0; i < label.getMessage().length(); i++) {
			char curChar = label.getMessage().charAt(i);
			width += fm.charWidth(curChar);
		}
		System.out.println(width + " is the width of " + label.getMessage());
		
		int height = (int) (label.getFont().getSize() * (0.75));		//The height of the font in pixels is 3/4 the point size.
		int x = RECT_X + ((RECT_WIDTH - width) / 2);	//Font x = middle of the rectangle's width - string width.
		int y = RECT_Y + ((RECT_HEIGHT - height) / 2) - 1; //Font y = middle of the rectangle's height - font height.
		
		g2D.setColor(Color.WHITE);
		
		final int X_OFFSET = 1;
		int Y_OFFSET;
		if (label.getDirection() == Directions.BOTTOM_HALF || label.getDirection() == Directions.TOP_HALF) { 
			if (label.getDirection() == Directions.TOP_HALF) {
				Y_OFFSET = 5;
			} else {
				Y_OFFSET = -5;
			}
		} else {
			Y_OFFSET = -1; //For some reason, this is required for a true centering.
		}
		
		x -= X_OFFSET; 		//Move the x to the left slightly.
		y += height + Y_OFFSET; 		//Move y down by the font's height (because font x/y is bottom left corner) then offset.
		
		/* Purely for debugging purposes. */
//		System.out.println("x, y, width, height");
//		System.out.println(Arrays.toString(new int[] {x, y, width, height}));
		
		g2D.drawString(label.getMessage(), x, y);
	}
	
	
	/*
	 * Since Graphics has no inbuilt arrow method, this will draw a line then automatically
	 * draw the "wings". It only works for straight arrows; no diagonal arrows, or an exception
	 * will be thrown.
	 * 
	 * @param g2D			The Graphics2D Object to use to draw the lines.
	 * @param startpoint	The starting (x,y) of the arrow.
	 * @param endpoint		The ending (x,y) of the arrow.
	 * @exception OrientationException		Thrown if you attempt to make a diagonal arrow.
	 */
	public void arrow(Graphics2D g2D, int[] startpoint, int[] endpoint) throws OrientationException {
		/* Draw the arrow-less line first. */
		int x1 = startpoint[0];
		int y1 = startpoint[1];
		int x2 = endpoint[0];
		int y2 = endpoint[1];
		g2D.drawLine(x1, y1, x2, y2);
		
		/* Calculate what direction the arrow is in. */
		boolean up = y1 > y2 && x1 == x2;
		boolean down = y1 < y2 && x1 == x2;
		boolean right = x1 < x2 && y1 == y2;
		boolean left = x1 > x2 && y1 == y2;
		
		/* Add the wings once we calculate which direction we are in. */
		if (up) {
			addWings(g2D, endpoint, Directions.UP);
		} else if (down) {
			addWings(g2D, endpoint, Directions.DOWN);
		} else if (right) {
			addWings(g2D, endpoint, Directions.RIGHT);
		} else if (left) {
			addWings(g2D, endpoint, Directions.LEFT);
		} else {
			throw new OrientationException("You cannot make diagonal arrows.");
		}
		
	}
	
	/*
	 * The child function of arrow(), addWings will add the wings to the naked lines using 
	 * the direction information.
	 * 
	 * @param g2D			The Graphics2D Object to use to draw the lines.
	 * @param endpoint		The (x,y) endpoint to add wings to.
	 * @param direction		What direction the arrow is in.
	 * 
	 */
	private void addWings(Graphics2D g2D, int[] endpoint, Directions direction) {
		/* Grab the endpoint's x and y. */
		int endpointX = endpoint[0];
		int endpointY = endpoint[1];
		
		/* For any given endpoint, we extend in some direction 5 and move over 5. */
		switch (direction) {
			case UP:
				g2D.drawLine(endpointX, endpointY, endpointX + 5, endpointY + 5);
				g2D.drawLine(endpointX, endpointY, endpointX - 5, endpointY + 5);
				break;
			case DOWN:
				g2D.drawLine(endpointX, endpointY, endpointX + 5, endpointY - 5);
				g2D.drawLine(endpointX, endpointY, endpointX - 5, endpointY - 5);
				break;
			case RIGHT:
				g2D.drawLine(endpointX, endpointY, endpointX - 5, endpointY + 5);
				g2D.drawLine(endpointX, endpointY, endpointX - 5, endpointY - 5);
				break;
			case LEFT:
				g2D.drawLine(endpointX, endpointY, endpointX + 5, endpointY + 5);
				g2D.drawLine(endpointX, endpointY, endpointX + 5, endpointY - 5);
				break;
			default:
				break;
		}
		
	}

}
