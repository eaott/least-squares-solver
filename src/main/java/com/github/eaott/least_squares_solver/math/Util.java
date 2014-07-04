package com.github.eaott.least_squares_solver.math;

public class Util {
	public static double mean(double[] arr)
	{
		double sum = 0;
		for (double d : arr)
			sum += d;
		return sum / arr.length;
	}
	
	public static double stddev(double[] arr)
	{
		double mean = mean(arr);
		double sum = 0;
		for (double d : arr)
		{
			sum += Math.pow(d-mean, 2);
		}
		return Math.sqrt(sum / (arr.length - 1));
	}
}
