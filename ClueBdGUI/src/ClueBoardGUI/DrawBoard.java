package ClueBoardGUI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

public class DrawBoard extends JPanel {
	private int x, y;
	public DrawBoard()
	{
	  x = 0;
	  y = 0;
	}

	public void paintComponent(Graphics g)
	{
	  super.paintComponent(g);
	  //draws a walkway cell
	  g.setColor(Color.YELLOW);
	  g.fillRect(x, y, 25, 25);
	  g.setColor(Color.BLACK);
	  g.drawRect(x, y, 25, 25);
	  //draw a room cell
	  g.setColor(Color.DARK_GRAY);
	  g.fillRect(x, y, 25, 25);
	  //draws a horizontal doorway
	  g.setColor(Color.RED);
	  g.drawLine(x, y, x+25, y);
	  //draws a vertical doorway
	  g.setColor(Color.RED);
	  g.drawLine(x, y, x, y+25);
	  //draws a player
	  g.setColor(/**legend color*/Color.MAGENTA);
	  g.fillOval(x, y, 25, 25);
	  g.setColor( Color.BLACK);
	  g.drawOval(x, y, 25, 25);
	  
	  
	}
	public void translate(int dx, int dy)
	{
	  x += dx;
	  y += dy;
	  // Must include this to see changes
	  repaint();
	}

}
