import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class ThumbnailComponent extends JPanel {

	// global to get and set
	boolean isSelected = false;

	// global to be get and set in lighttable
	PhotoComponent photo;

	public ThumbnailComponent(PhotoComponent photo) {
		this.photo = photo;

		// panel to hold the photo
		// JPanel thumbPanel = new JPanel();
		this.setPreferredSize(new Dimension(photo.getImage().getWidth() + 10, photo.getImage().getHeight() + 10));
		// this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		this.setBackground(Color.GRAY);
		this.setLayout(new BorderLayout());
		this.add(photo, BorderLayout.CENTER);

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!isSelected()) {
					setIsSelected(true);
					setBackground(Color.DARK_GRAY);
				} else if (isSelected()) {
					setIsSelected(false);
					setBackground(Color.GRAY);
				}
				if (e.getClickCount() == 2) {
					// somehow set it as primary and switch to photo view
				}
			}
		});		
	}

	public void setIsSelected(boolean selected) {
		this.isSelected = selected;
		if (selected) this.setBackground(Color.DARK_GRAY);
		if (!selected) this.setBackground(Color.GRAY);
	}

	public boolean isSelected() {
		return isSelected;
	}

	public PhotoComponent getPhotoComponent() {
		return photo;
	}

	protected void paintComponent(Graphics g) {

		// because you have to
		super.paintComponent(g);

		Graphics2D graphics2D = (Graphics2D) g.create();

		graphics2D.scale(0.5, 0.5);
		graphics2D.drawImage(photo.getImage(), 10, 10, this);
	}
}