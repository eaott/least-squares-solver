package com.github.eaott.least_squares_solver.math;

public class Point {
	public double x, y;
	public Point()
	{
		
	}
	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	public int hashCode()
	{
		return (int)(x * 31 + y);
	}
	@Override
	public boolean equals(Object obj)
	{
		if (! (obj instanceof Point))
			return false;
		Point p = (Point)obj;
		return x == p.x && y == p.y;
	}
}
