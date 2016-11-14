import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class LightTable extends JPanel {

	// determine if something is the first photo to be displayed
	private boolean initialPhoto = true;

	// has to be global because it's constantly changing
	String viewMode = "Photo View";
	String annotationMode = "Drawing";

	// has to be global because it's constantly changing
	ThumbnailComponent primaryPhoto = null;
	ArrayList<ThumbnailComponent> thumbnails = new ArrayList<ThumbnailComponent>();

	public LightTable() {

		// this.setPreferredSize(new Dimension(holder.getWidth(), holder.getHeight()));
		this.setLayout(new BorderLayout());
		// this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		this.setBackground(Color.GRAY);
	}

	public void setViewMode(JRadioButtonMenuItem mode) {
		viewMode = mode.getLabel();
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setAnnotationMode(JToggleButton mode) {
		annotationMode =  mode.getLabel();

		for (ThumbnailComponent thumb : thumbnails) {
			thumb.getPhotoComponent().setAnnotationMode(mode);
		}
	}

	public ArrayList<ThumbnailComponent> getThumbnails() {
		
		if (thumbnails.size() == 0) {
			return null;
		}

		return thumbnails;

	}

	// Any photo should be added to the thumbnail list
	public void addPhoto(PhotoComponent photo) {
		thumbnails.add(new ThumbnailComponent(photo));
		repaint();
		revalidate();
	}

	public void addPhoto(ThumbnailComponent photo) {
		thumbnails.add(photo);
		repaint();
		revalidate();
	}

	public ThumbnailComponent getSelectedPhoto() {
		ThumbnailComponent selectedThumb = null;
		while (selectedThumb == null) {
			for (ThumbnailComponent thumb : thumbnails) {
				if (thumb.isSelected) selectedThumb = thumb;
			}
		}
		return selectedThumb;
	}

	public boolean removePhoto(ThumbnailComponent photo) {

		// if the photo is in the list remove it
		if (thumbnails.contains(photo)) {

			//if the photo is the primary photo set primary to next or null
			// if (photo == primaryPhoto) {
			// 	if (thumbnails.size() > 1) {
			// 		nextPhoto();
			// 	} else if (thumbnails.size() == 1) {
			// 		primaryPhoto = null;
			// 	}
			// }
			
			thumbnails.remove(photo);

			nextPhoto();

			// update the view
			repaint();

			return true;
		}

		return false;
	}

	public void nextPhoto() {

		primaryPhoto.setIsSelected(false); // controls highlight

		// controls photoview
		int max = thumbnails.size() - 1;
		int currentIndex = thumbnails.indexOf(primaryPhoto);

		// if at the end of the list wraparound to the beginning
		// move forward one photo if not at the end of the list
		if (currentIndex < max) {
			primaryPhoto = thumbnails.get(currentIndex + 1);
		} else if (currentIndex == max) {
			primaryPhoto = thumbnails.get(0);
		}

		primaryPhoto.setIsSelected(true); // controls highlight

		repaint();
		revalidate();

	}

	public void previousPhoto() {

		primaryPhoto.setIsSelected(false); // controls highlight

		//controls photoview
		int min = 0;
		int currentIndex = thumbnails.indexOf(primaryPhoto);
		int max = thumbnails.size() - 1;

		if (currentIndex > 0) {
			primaryPhoto = thumbnails.get(currentIndex - 1);
		} else if (currentIndex == 0) {
			primaryPhoto = thumbnails.get(max);
		}

		primaryPhoto.setIsSelected(true); // controls highlight

		repaint();
		revalidate();
	}

	private void photoView() {

		this.removeAll();
		this.setLayout(new BorderLayout());
		
		if (thumbnails.size() == 1) { // if there is only one photo
			this.primaryPhoto = thumbnails.get(0);
		} else if (thumbnails.size() > 1) {
			// set selected photo to be primary photo if there is more than 1
			for (ThumbnailComponent thumb : thumbnails) {
				if (thumb.isSelected()) {
					this.primaryPhoto = thumb;
				}
			}
		}

		if (primaryPhoto != null) this.add(primaryPhoto.getPhotoComponent());

		repaint();
		revalidate();
	}

	private void gridView() {
		
		this.removeAll();
		// change to grid layout
		this.setLayout(new GridLayout(3, 2));

		// add thumbnails
		for (ThumbnailComponent thumb : thumbnails) {
			
			thumb.addMouseListener(new MouseAdapter() {
				public void mouseCliked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						System.out.println("Double click!");
					}
				}
			});

			this.add(thumb);

		}

		repaint();
		revalidate();
	}

	private void splitView() {
		this.removeAll();

		this.setLayout(new GridLayout(2, 1));

		this.add(createSplitViewTop(primaryPhoto));

		this.add(createSplitViewBottom());

		// repaint();
		revalidate();
	}

	private JPanel createSplitViewTop(ThumbnailComponent photo) {

		// create new pane
		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
		// this.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()/2));
		// top.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		top.add(photo.getPhotoComponent(), BorderLayout.CENTER);

		// final top view
		return top;
	}

	private JPanel createSplitViewBottom() {

		JPanel grid = new JPanel();
		grid.setPreferredSize(new Dimension(this.getWidth(), 300));
		// grid.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
		grid.setLayout(new GridLayout(1, 3));
		// grid.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

		for (ThumbnailComponent thumb : thumbnails) {
			grid.add(thumb);
		}

		return grid;
	}

	protected void paintComponent(Graphics g) {

		// call this because you have to
		super.paintComponent(g);

		if (viewMode == "Photo View") photoView();
		if (viewMode == "Grid View") gridView();
		if (viewMode == "Split View") splitView();
	}
}