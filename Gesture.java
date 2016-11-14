import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Gesture extends JComponent {

	private ArrayList<Point> gesture = new ArrayList<Point>();

	public Gesture() {

		this.addListeners();

	}

	private void addListeners() {

		//listener for drawing the gesture
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point point = new Point(e.getX(), e.getY());
				repaint();
			}
		});
	}

	protected void paintComponent(Graphics g) {
		// because you have to
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;

		g.setColor(Color.RED);

		for (Point point : gesture) {
			g2d.fillOval((int) point.getX(), (int) point.getY(), 5, 5);			
		}
	}
}