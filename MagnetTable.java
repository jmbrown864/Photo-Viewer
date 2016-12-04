import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import java.util.Random;

public class MagnetTable extends JPanel {

	private String[] tags = {"Vacation", "School", "Holidays", "Work"};

	private int numObjects = 10;
	private ArrayList<MoveObject> movingObjects = new ArrayList<MoveObject>();

	public MagnetTable() {
		this.setLayout(null);
		this.setBackground(Color.GRAY);
		System.out.println("Created magnet view.");
	}

	public ArrayList<MoveObject> getObjects() {
		return movingObjects;
	}

	// public void add(Draggable d) {
	// 	tags.add(d);
	// 	repaint();
	// }

	// public void remove(Draggable d) {
	// 	tags.remove(d);
	// 	repaint();
	// }

	public void createObjects() {

		for (int i = 0; i < numObjects; i++) {
			int rnd = new Random().nextInt(tags.length);
			System.out.println("Created new object with tag: " + tags[rnd]);
			MoveObject newObj = new MoveObject(tags[rnd]);
			movingObjects.add(newObj);
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2D = (Graphics2D) g;
	}
}