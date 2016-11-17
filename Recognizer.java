import java.awt.*;
import java.util.*;
import java.util.regex.Pattern;
import javax.swing.*;

public class Recognizer {

	public enum Gesture {

		NEXT(new String[] {"SE", "SW"}),
		PREV(new String[] {"SW", "SE"});

		public final String[] template;

		Gesture(String[] template) {
			this.template = template;
		}
	}

	private final ArrayList<Pattern> gestures = new ArrayList<Pattern>();

	public Recognizer() {
		
		for (Gesture g : Gesture.values()) {
			gestures.add(Pattern.compile(defineRegex(g.template)));
		}

	}

	public String defineRegex(String[] template) {
		
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < template.length; i++) {
			switch (template[i]) {
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
		int counter = 0;

		for (Pattern p : gestures) {
			if (p.matcher(vector).find()) {
				return Gesture.values()[counter];
			}

			counter++;
		}

		return null;
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