package edu.eci.arsw.collabpaint.model;

import java.util.Arrays;
import java.util.List;

public class Polygon {
	
	private List<Point> points;
	
	public Polygon() {
		
	}
	
	public Polygon(Point[] points) {
		this.points = Arrays.asList(points);
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
