import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Recognizer {

	public enum Gestures {
		NEXT, PREV, NONE
	}

	private final String[] next = {"RIGHT_DOWN", "LEFT_DOWN"}; // SE, SW
	private final String[] prev = {"LEFT_DOWN", "RIGHT_DOWN"}; // SW, SE


	private String buildGesture(ArrayList<Point> gesture) {

		StringBuffer gVector = new StringBuffer();

		for (int i = 1; i < gesture.size(); i++) {
			double x1 = gesture.get(i-1).getX();
			double x2 = gesture.get(i).getX();
			double y1 = gesture.get(i-1).getY();
			double y2 = gesture.get(i).getY();

			if ((x2 - x1 > 0) && (y2 - y1 > 0)) {
				gVector.append("RIGHT_DOWN"); // SE
			}

			if ((x2 - x1 < 0) && (y2 - y1 < 0)) {
				gVector.append("LEFT_DOWN"); // SW
			}
		}

		return gVector.toString();

	}

}