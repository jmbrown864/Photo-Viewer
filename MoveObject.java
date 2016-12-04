import java.awt.*;
import java.util.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.*;

//OBJECTS THAT'LL MOVE AROUND ON THE SCREEN	
public class MoveObject extends JComponent {
	
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

	public void moveToMagnet(int x, int y) {
		setLocation(x, y);
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