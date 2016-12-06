import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.*;

//OBJECTS THAT'LL MOVE AROUND ON THE SCREEN	
public class MoveObject extends JComponent {

	private Timer timer;
	public final static int TENTH_OF_A_SECOND = 100;
	public int numFrames = 15;
	public int numIterations = 0;
	
	private String tag = "";

	public MoveObject(String tag) {
		Random rnd = new Random();

		this.setBorder(new LineBorder(Color.GREEN, 3));
		this.setBounds(rnd.nextInt(600), rnd.nextInt(600), 100, 100);

		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

	public int getStartX() {
		return getX();
	}

	public int getStartY() {
		return getY();
	}

	public void moveToMagnet(int x, int y) {

		int startX = getStartX();
		int startY = getStartY();

		timer = new Timer(TENTH_OF_A_SECOND, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (numIterations++ >= numFrames) {
					timer.stop();
				} else {
					setLocation(startX + numIterations * (x - startX)/numFrames, 
								startY + numIterations * (y - startY)/numFrames);
					revalidate();
					repaint();
				}
			}
		});

		timer.start();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2D = (Graphics2D) g;
		g2D.setColor(Color.GREEN);
		g2D.fill3DRect(0, 0, 100, 100, false);
		g2D.setColor(Color.BLACK);
		g2D.drawString(tag, 30, 50);
	}
}