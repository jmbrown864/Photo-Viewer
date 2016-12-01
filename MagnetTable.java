import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class MagnetTable extends JPanel {

	ArrayList<Draggable> tags = new ArrayList<Draggable>();

	public MagnetTable() {
		this.setLayout(null);
		this.setBackground(Color.RED);
		System.out.println("Created magnet view.");
	}

	// public void add(Draggable d) {
	// 	tags.add(d);
	// 	repaint();
	// }

	// public void remove(Draggable d) {
	// 	tags.remove(d);
	// 	repaint();
	// }

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2D = (Graphics2D) g;

		for (Draggable d : tags) {
			this.add(d);
		}
	}
}