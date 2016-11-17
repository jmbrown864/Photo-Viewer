import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.imageio.*;

public class PhotoComponent extends JComponent {


	// global to be retrieved
	BufferedImage image = null;

	// global because it changes often
	String annotationMode;
	Boolean flipped = false;

	// hold line drawing
	ArrayList<Point> drawing = new ArrayList<Point>();

	//hold values of rectangle corners
	Point startPoint, currPoint;

	// hold sticky notes
	ArrayList<StickyNote> notes = new ArrayList<StickyNote>();

	public PhotoComponent(File path) {

		try {
			image = ImageIO.read(path);
		} catch (IOException e) {
			System.out.println("Could not find file at specified path.");
		}

		this.setMaximumSize(new Dimension(image.getWidth(), image.getHeight()));
		// this.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
		this.addListeners();
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(File path) {
		try {
			image = ImageIO.read(path);
		} catch (IOException e) {
			System.out.println("Could not find file at specified path.");
		}
	}

	public String getAnnotationMode() {
		return annotationMode;
	}

	public void setAnnotationMode(JToggleButton mode) {
		this.annotationMode = mode.getLabel();
	}

	public void addStickyNotes() {
		for (StickyNote note : notes) {
			this.add(note);
		}
	}

	public void addListeners() {

		// Listener for clicks on the photo
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (e.getClickCount() == 2) {
					flipped = !flipped;
				}

				repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
				// draw when flipped
				if (flipped) {
					
					if (annotationMode == "Drawing") {
						Point point = new Point(e.getX(), e.getY());
						drawing.add(point);
					}

					if (annotationMode == "Text") {
						startPoint = new Point(e.getX(), e.getY());
					}

				}

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// show drawing on mouse release
				if (annotationMode == "Text") {
					StickyNote note = new StickyNote(startPoint, currPoint);
					notes.add(note);
				}

				repaint();
			}

		});
		
		// Listener for dragging on the photo
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				// draw when flipped
				if (flipped) {
					
					if (annotationMode == "Drawing") {
						Point point = new Point(e.getX(), e.getY());
						drawing.add(point);
					}

					if (annotationMode == "Text") {
						currPoint = new Point(e.getX(), e.getY());
					}

				} 

				repaint();
			}

		});
	
	}

	protected void paintComponent(Graphics g) {

		// Call this because you have to
		super.paintComponent(g);

		// cast to g2d for more methods
		Graphics2D g2d = (Graphics2D) g;

		int leftPos = ((this.getWidth() / 2) - (image.getWidth() / 2));
		int topPos = ((this.getHeight() / 2) - (image.getHeight() / 2));

		if (flipped) {
			
			g2d.setColor(Color.WHITE);
			g2d.fill3DRect(leftPos, topPos, image.getWidth(), image.getHeight(), false);
			g2d.setClip(leftPos, topPos, image.getWidth(), image.getHeight());

			if (annotationMode == "Drawing") {
				g2d.setColor(Color.BLACK);
				for (Point point : drawing) {
					g2d.fillOval((int) point.getX(), (int) point.getY(), 5, 5);
				}
			}

			if (annotationMode == "Text" && currPoint != null) {
				g2d.setColor(Color.YELLOW);
				int width = (int) (currPoint.getX() - startPoint.getX());
				int height = (int) (currPoint.getY() - startPoint.getY());
				g2d.fill3DRect((int) startPoint.getX(), (int) startPoint.getY(), width, height, false);
			}

		} else if (!flipped) {
			g2d.drawImage(image, leftPos, topPos, this);
		}
	}
}