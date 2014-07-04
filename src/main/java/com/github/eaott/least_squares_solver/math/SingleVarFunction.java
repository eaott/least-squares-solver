package com.github.eaott.least_squares_solver.math;

public interface SingleVarFunction {
	public double evaluate(double var);
	public double[] coeffs();
}
