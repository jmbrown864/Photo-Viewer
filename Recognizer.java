import java.awt.*;
import java.util.*;
import java.util.regex.Pattern;
import javax.swing.*;

public class Recognizer {

	public enum Gesture {

		NEXT (new String[] {"SE", ".", "SW"}),
		PREV (new String[] {"SW", ".", "SE"}),
		DELETE (new String[] {"SE", "E", "NE", "N", "NW", "W", "S"}),
		VACA (new String[] {"SW", "E", "NW"}),
		SCHOOL (new String[] {"S", "E", "N", "W"}),
		HOLIDAY (new String[] {"S", "E", "NW"}),
		WORK (new String[] {"SE", "NE", "SE", "NE"}),
		DRAWING (new String[] {"S", "NE", "N", "NW"}),
		TEXT (new String[] {"E", "W", "S"}),
		NONE (new String[] {"."});

		public final String[] template;

		Gesture(String[] template) {
			this.template = template;
		}
	}

	private final ArrayList<Pattern> gestures = new ArrayList<Pattern>();
	public Gesture currentGesture = Gesture.NONE;

	public Recognizer() {
		
		for (Gesture g : Gesture.values()) {
			gestures.add(Pattern.compile(defineRegex(g.template)));
		}

	}

	public Gesture getCurrentGesture() {
		return currentGesture;
	}

	public void setCurrentGesture(Gesture name) {
		currentGesture = name;
	}

	public ArrayList<Pattern> getGesturesAsRegex() {
		return gestures;
	}

	public String defineRegex(String[] template) {
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("^"); // matches the beginning of a string
		buffer.append(".{0,2}"); // matches between 0 and 2 of any character

		for (int i = 0; i < template.length; i++) {
			switch (template[i]) {
				case "." :
					buffer.append(".{0,2}");
					break;
				case "N" : 
					buffer.append("[ANB]+");
					break;
				
				case "NE": 
					buffer.append("[NBE]+");
					break;
				
				case "E" : 
					buffer.append("[BEC]+");
					break;
				
				case "SE": 
					buffer.append("[ECS]+");
					break;

				case "S" : 
					buffer.append("[CSD]+");
					break;

				case "SW": 
					buffer.append("[SDW]+");
					break;

				case "W" : 
					buffer.append("[DWA]+");
					break;

				case "NW": 
					buffer.append("[WAN]+");
					break;
			}
		}

		buffer.append(".{0,2}"); // matches up to 2 of any character
		buffer.append("$"); // matches the end of a string

		return buffer.toString();
	}

	public String buildVector(ArrayList<Point> gesture) {

		StringBuffer gVector = new StringBuffer();

		for (int i = 1; i < gesture.size(); i++) {
			double x1 = gesture.get(i-1).getX();
			double x2 = gesture.get(i).getX();
			double y1 = gesture.get(i-1).getY();
			double y2 = gesture.get(i).getY();

			if ((x1 == x2) && (y1 > y2)) {
				//North
				gVector.append("N"); 
			} else if ((x1 < x2) && (y1 > y2)) {
				// northeast
				gVector.append("B");
			} else if ((x1 < x2) && (y1 == y2)) {
				// east
				gVector.append("E");
			} else if ((x1 < x2) && (y1 < y2)) {
				// southeast
				gVector.append("C");
			} else if ((x1 == x2) && (y1 < y2)) {
				// south
				gVector.append("S");
			} else if ((x1 > x2) && (y1 < y2)) {
				// southwest
				gVector.append("D");
			} else if ((x1 > x2) && (y1 == y2)) {
				//west
				gVector.append("W");
			} else if ((x1 > x2) && (y1 > y2)) {
				//northwest
				gVector.append("A");
			}
		}

		return gVector.toString();
	}

	public Gesture match(String vector) {
		
		for (int i = 0; i < gestures.size(); i++) {
		
			if (gestures.get(i).matcher(vector).find()) {
				System.out.println(Gesture.values()[i].name());
				return Gesture.values()[i];
			}
		}

		System.out.println("Didn't find it.");
		return Gesture.NONE;
	}

}

/*

General======================================================
	Nish - NW, N, NE ............ north
	NEish - N, NE, E ............ north east
	Eish - NE, E, SE ............ east
	SEish - E, SE, S ............ south east
	Sish - SE, S, SW ............ south
	SWish - S, SW, W ............ south west
	Wish - SW, W, NW ............ west
	NWish - W, NW, N ............ north west

Translations=================================================
	N - north
	B - north east
	E - east
	C - south east
	S - south
	D - south west
	W - west
	A - north west

General Translations==========================================
	ANB - Nish
	NBE - NEish
	BEC - Eish
	ECS - SEish
	CSD - Sish
	SDW - SWish
	DWA - Wish
	WAN - NWish

*/