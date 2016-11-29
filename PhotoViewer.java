import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PhotoViewer implements ActionListener, ChangeListener {

	// Status label needs to be global so it can be changed on any action
	JLabel status = new JLabel("Status");

	// Menu items need to be global for action events
	JMenuItem importFile, deleteFile, exitApp;
	JRadioButtonMenuItem photoView, gridView, splitView;

	// Tool bar items need to be global for action events
	JToggleButton vacation, school, holidays, work;
	JToggleButton drawing, text;
	JButton forward, backward;

	// Content Panel needs to be global for updating photo views
	JPanel contentPanel;
	LightTable lightTable = new LightTable();

	public PhotoViewer() {
		init();
	}

	public void init() {

		JFrame main = new JFrame("Photo Viewer");
		main.setSize(1000, 1000);
		main.setLayout(new BorderLayout());
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//put EVERYTHING ELSE here
		main.setJMenuBar(createMenu());

		JPanel tools = createToolPanel(main);
		main.add(tools, BorderLayout.WEST);

		JPanel statusBar = createStatusBar(main);
		main.add(statusBar, BorderLayout.SOUTH);

		main.add(createContentPanel(main, tools, statusBar), BorderLayout.CENTER);

		main.setVisible(true);
	}

	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == contentPanel) { System.out.println("Something changed!"); }
	}

	public JMenuBar createMenu() {

		// Menu bar
		JMenuBar menuBar = new JMenuBar(); 

		// File menu and its items
		JMenu fileMenu = new JMenu("File");

		importFile = new JMenuItem("Import");
		fileMenu.add(importFile);
		importFile.addActionListener(this);

		deleteFile = new JMenuItem("Delete");
		deleteFile.setEnabled(false);
		fileMenu.add(deleteFile);
		deleteFile.addActionListener(this);

		exitApp = new JMenuItem("Exit");
		fileMenu.add(exitApp);
		exitApp.addActionListener(this);

		menuBar.add(fileMenu);

		// View menu and its items
		JMenu viewMenu = new JMenu("View");

		ButtonGroup viewGroup = new ButtonGroup();

		photoView = new JRadioButtonMenuItem("Photo View");
		photoView.addActionListener(this);
		photoView.setSelected(true);
		viewGroup.add(photoView);
		viewMenu.add(photoView);

		gridView = new JRadioButtonMenuItem("Grid View");
		gridView.addActionListener(this);
		viewGroup.add(gridView);
		viewMenu.add(gridView);

		splitView = new JRadioButtonMenuItem("Split View");
		splitView.addActionListener(this);
		viewGroup.add(splitView);
		viewMenu.add(splitView);

		menuBar.add(viewMenu);

		// Final menu bar
		return menuBar;
	}

	public JPanel createToolPanel(JFrame mainFrame) {

		// Create a panel for the tools to live in
		JPanel toolPanel = new JPanel();
		toolPanel.setPreferredSize(new Dimension(150, mainFrame.getHeight()));
		// toolPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		toolPanel.setLayout(new GridLayout(3, 1));

		// Add tools to the panel
		toolPanel.add(createTagButtons(mainFrame));
		toolPanel.add(createModeButtons(mainFrame));
		toolPanel.add(createNavButtons(mainFrame));

		// Final tool panel
		return toolPanel;
	}

	public JPanel createTagButtons(JFrame mainFrame) {

		// Create a panel for the tag buttons to live in
		JPanel tagsPanel = new JPanel();
		tagsPanel.setPreferredSize(new Dimension(150, mainFrame.getHeight() / 3));
		// tagsPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		tagsPanel.setLayout(new GridLayout(5, 1));

		// Label tools section
		JLabel tagsLabel = new JLabel("Tag Photo:");
		tagsLabel.setHorizontalAlignment(JLabel.CENTER);
		tagsLabel.setVerticalAlignment(JLabel.BOTTOM);
		tagsPanel.add(tagsLabel);

		// Create tag buttons
		vacation = new JToggleButton("Vacation");
		vacation.setPreferredSize(new Dimension(100, 50));
		vacation.addActionListener(this);
		tagsPanel.add(vacation);

		school = new JToggleButton("School");
		school.addActionListener(this);
		tagsPanel.add(school);

		holidays = new JToggleButton("Holidays");
		holidays.addActionListener(this);
		tagsPanel.add(holidays);

		work = new JToggleButton(" Work ");
		work.addActionListener(this);
		tagsPanel.add(work);

		// Final tags panel
		return tagsPanel;

	}

	public JPanel createModeButtons(JFrame mainFrame) {

		// Create a panel for the drawing mode buttons
		JPanel modePanel = new JPanel();
		modePanel.setPreferredSize(new Dimension(150, mainFrame.getHeight() / 3));
		// modePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		modePanel.setLayout(new GridLayout(3, 1));

		// label for annotation mode
		JLabel modeLabel = new JLabel("Editing Mode:");
		modeLabel.setHorizontalAlignment(JLabel.CENTER);
		modeLabel.setVerticalAlignment(JLabel.BOTTOM);
		modePanel.add(modeLabel);

		// Create radio buttons for annotation mode
		ButtonGroup annotationMode = new ButtonGroup();

		drawing = new JToggleButton("Drawing");
		annotationMode.add(drawing);
		drawing.setSelected(true);
		drawing.addActionListener(this);
		modePanel.add(drawing);

		text = new JToggleButton("Text");
		annotationMode.add(text);
		text.addActionListener(this);
		modePanel.add(text);

		// Final mode panel
		return modePanel;

	}

	public JPanel createNavButtons(JFrame mainFrame) {

		// Create a panel for the navigation buttons
		JPanel navPanel = new JPanel();
		navPanel.setPreferredSize(new Dimension(150, mainFrame.getHeight() / 3));
		// navPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		navPanel.setLayout(new GridLayout(3, 1));

		// Create nav label
		JLabel navLabel = new JLabel("Navigate Photos:");
		navLabel.setHorizontalAlignment(JLabel.CENTER);
		navLabel.setVerticalAlignment(JLabel.BOTTOM);
		navPanel.add(navLabel);

		// Create nav buttons
		forward = new JButton(">> Next");
		forward.addActionListener(this);
		navPanel.add(forward);

		backward = new JButton(" << Previous");
		backward.addActionListener(this);
		navPanel.add(backward);

		// Final navPanel
		return navPanel;

	}

	public JPanel createContentPanel(JFrame mainFrame, JPanel tools, JPanel status) {

		// create new panel to hold main content
		contentPanel = new JPanel();
		contentPanel.setPreferredSize(new Dimension(mainFrame.getWidth() - tools.getWidth(), mainFrame.getHeight() - status.getHeight()));
		// contentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		contentPanel.setLayout(new BorderLayout());

		// light table that lives in content panel
		lightTable.setPreferredSize(new Dimension(contentPanel.getWidth(), contentPanel.getHeight()));
		lightTable.setViewMode(photoView); // TODO: change to button group get selected
		contentPanel.add(lightTable, BorderLayout.CENTER);
	
		// final content panel
		return contentPanel;
	}

	public JPanel createStatusBar(JFrame mainFrame) {

		// create a panel for the status bar to live in
		JPanel statusPanel = new JPanel();
		statusPanel.setPreferredSize(new Dimension(mainFrame.getWidth(), 50));
		// statusPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		statusPanel.setLayout(new BorderLayout());

		// add status text to the panel
		status.setHorizontalAlignment(JLabel.CENTER);
		statusPanel.add(status, BorderLayout.CENTER);

		// Final status panel
		return statusPanel;
	}

	public void actionPerformed(ActionEvent e) {
		
		// Import file(s)
		if (e.getSource() == importFile) {
			importFile();
		}

		// Delete file(s)
		if (e.getSource() == deleteFile) {
			ThumbnailComponent toDelete = lightTable.getSelectedPhoto();
			lightTable.removePhoto(toDelete);
			status.setText("Photo has been deleted.");
		}

		// Exit application
		if (e.getSource() == exitApp) {
			System.exit(0);
		}

		// Switch to photo view
		if (e.getSource() == photoView) {
			lightTable.setViewMode(photoView);
			status.setText("Switched to photo view.");
		}

		// Switch to grid view
		if (e.getSource() == gridView) {
			lightTable.setViewMode(gridView);
			status.setText("Switched to grid view.");
		}

		// Switch to split view
		if (e.getSource() == splitView) {
			lightTable.setViewMode(splitView);
			status.setText("Switched to split view.");
		}

		if (e.getSource() == vacation && vacation.isSelected()) {
			status.setText("Photo has been tagged as: Vacation");
		} else if (e.getSource() == vacation && !vacation.isSelected()) {
			status.setText("Photo has been removed from tag: Vacation");
		}

		if (e.getSource() == school && school.isSelected()) {
			status.setText("Photo has been tagged as: School");
		} else if (e.getSource() == school && !school.isSelected()) {
			status.setText("Photo has been removed from tag: School");
		}

		if (e.getSource() == holidays && holidays.isSelected()) {
			status.setText("Photo has been tagged as: Holidays");
		} else if (e.getSource() == holidays && !holidays.isSelected()) {
			status.setText("Photo has been removed from tag: Holidays");
		}

		if (e.getSource() == work && work.isSelected()) {
			status.setText("Photo has been tagged as: Work");
		} else if (e.getSource() == work && !work.isSelected()) {
			status.setText("Photo has been removed from tag: Work");
		}

		if (e.getSource() == drawing) {
			lightTable.setAnnotationMode(drawing);
			status.setText("Switched to drawing mode.");
		}

		if (e.getSource() == text) {
			lightTable.setAnnotationMode(text);
			status.setText("Switched to text mode.");
		}

		if (e.getSource() == forward) {
			lightTable.nextPhoto();
			status.setText("Switched to next photo.");
		}

		if (e.getSource() == backward) {
			lightTable.previousPhoto();
			status.setText("Switched to previous photo.");
		}

		Recognizer r = new Recognizer();
		if (lightTable.getPrimaryPhoto() != null) {
			PhotoComponent currPhoto = lightTable.getPrimaryPhoto().getPhotoComponent();
			if (currPhoto.getStrokeVector() != null) {
				String strokeVector = currPhoto.getStrokeVector();
				Recognizer.Gesture currGesture = r.match(strokeVector);
				System.out.println(currGesture.name());
				switch (currGesture) {
					case NONE:
						status.setText("Gesture was not recognized.");
						currPhoto.clearStroke();
						break;
					case NEXT:
						lightTable.nextPhoto();
						status.setText("Switched to next photo.");
						currPhoto.clearStroke();
						break;
					case PREV:
						lightTable.previousPhoto();
						status.setText("Switched to previous photo.");
						currPhoto.clearStroke();
						break; 
					case DELETE:
						ThumbnailComponent toDelete = lightTable.getSelectedPhoto();
						lightTable.removePhoto(toDelete);
						status.setText("Photo has been deleted.");
						currPhoto.clearStroke();
						break;
					case VACA:
						if (!vacation.isSelected()) {
							vacation.setSelected(true);
							status.setText("Photo has been tagged as: Vacation"); 
						} else if (vacation.isSelected()) {
							vacation.setSelected(false);
							status.setText("Photo has been removed from tag: Vacation");
						}
						currPhoto.clearStroke();
						break;
					case SCHOOL:
						if (!school.isSelected()) { 
							school.setSelected(true);
							status.setText("Photo has been tagged as: School"); 
						} else if (school.isSelected()) {
							school.setSelected(false);
							status.setText("Photo has been removed from tag: School");
						}
						currPhoto.clearStroke();
						break;
					case HOLIDAY:
						if (!holidays.isSelected()) { 
							holidays.setSelected(true);
							status.setText("Photo has been tagged as: Holidays"); 
						} else if (holidays.isSelected()) {
							holidays.setSelected(false);
							status.setText("Photo has been removed from tag: Holidays");
						}
						currPhoto.clearStroke();
						break;
					case WORK:
						if (!work.isSelected()) { 
							work.setSelected(true);
							status.setText("Photo has been tagged as: Work"); 
						} else if (work.isSelected()) {
							work.setSelected(false);
							status.setText("Photo has been removed from tag: Work");
						}
						currPhoto.clearStroke();
						break;
					case DRAWING:
						if (!drawing.isSelected()) {
							status.setText("Switched to drawing mode.");
							drawing.setSelected(true);
							text.setSelected(false);
							lightTable.setAnnotationMode(drawing);
						}
						currPhoto.clearStroke();
						break;
					case TEXT:
						if (!text.isSelected()) {
							status.setText("Switched to text mode.");
							text.setSelected(true);
							drawing.setSelected(false);
							lightTable.setAnnotationMode(text);
						}
						currPhoto.clearStroke();
						break;
				}
			}
		}
	}

	public void importFile() {

		if (lightTable.getThumbnails() == null) {
			deleteFile.setEnabled(true);
		} else {
			deleteFile.setEnabled(false);
		}

		//image to be passed around
		File image = null;

		//photoComponent created from initial image
		PhotoComponent pc;

		// Create JFileChooser and open dialog box in new frame
		JFileChooser importFileChooser = new JFileChooser();
		int returnVal = importFileChooser.showOpenDialog(new JFrame());

		// When the selected option is approved
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			image = importFileChooser.getSelectedFile();
			pc = new PhotoComponent(image);

			if (lightTable.getViewMode() == "") {
				lightTable.setViewMode(photoView);
			} else if (lightTable.getViewMode() == "Grid View") {
				ThumbnailComponent tc = new ThumbnailComponent(pc);
				lightTable.addPhoto(tc);
			} else {
				lightTable.addPhoto(pc);
			}

			status.setText("File imported successfully.");
		}

		lightTable.repaint();
		lightTable.revalidate();
	}

	public static void main(String[] args) {
		new PhotoViewer();
	}

}