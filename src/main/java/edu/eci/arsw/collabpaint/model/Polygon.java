package edu.eci.arsw.collabpaint.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Polygon {
	
	private List<Point> points;
	
	public Polygon() {
		points = new ArrayList<Point>();
	}
	
	/**
	 * adds a point to the polygon
	 * @param pt - the point to be added
	 */
	public void addPoint(Point pt) {
		synchronized (points) {
			points.add(pt);
		}	
	}
	
	/**
	 * returns true if the polygon has 4 points.
	 * @return true if the polygon has 4 points.
	 */
	public boolean isComplete() {
		synchronized(points) {
		return points.size() == 3;
		}
	}

	/**
	 * @return the points
	 */
	public List<Point> getPoints() {
		return points;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(List<Point> points) {
		this.points = points;
	}
	
	

}
