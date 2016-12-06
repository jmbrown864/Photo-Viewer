import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class Draggable extends JComponent {
	
	String text = null;

	//for dragging
	private volatile int screenX = 0;
	private volatile int screenY = 0;
	private volatile int myX = 0;
	private volatile int myY = 0;

	private ArrayList<MoveObject> movingObjects = new ArrayList<MoveObject>();

	public Draggable(String text) {
		this.setBorder(new LineBorder(Color.BLUE, 3));
		this.setBounds(0, 0, 100, 100);
		this.text = text;

		addMouseListener(new MouseListener() {
			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseClicked(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {
				screenX = e.getXOnScreen();
				screenY = e.getYOnScreen();
				myX = getX();
				myY = getY();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				//put the phots underneath the elements
				for (MoveObject m : movingObjects) {
					System.out.println("Hoping to move objects");
					if (m.getTag() == text) {
						System.out.println("They match!");
						m.moveToMagnet(getX(), getY());
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {}
		});

		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int deltaX = e.getXOnScreen() - screenX;
				int deltaY = e.getYOnScreen() - screenY;
				setLocation(myX + deltaX, myY + deltaY);
			}

			@Override
			public void mouseMoved(MouseEvent e) {}
		});
	}

	public void setMovingObjects(ArrayList<MoveObject> movingObjects) {
		this.movingObjects = movingObjects;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2D = (Graphics2D) g;
		g2D.drawString(text, 30, 50);
	}
}