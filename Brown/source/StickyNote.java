import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class StickyNote extends JPanel{

	Point start, end;
	int width, height;

	public StickyNote(Point start, Point end) {
		
		this.start = start;
		this.end = end;

		this.width = (int) (end.getX() - start.getX());
		this.height = (int) (end.getY() - start.getY());
		this.setSize(new Dimension(width, height));
	}

	protected void paintComponent(Graphics g) {

		//because we have to
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.YELLOW);
		g2d.fill3DRect((int) start.getX(), (int) start.getY(), width, height, true);

	}
}