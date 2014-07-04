package com.github.eaott.least_squares_solver;

import java.util.Set;

import com.github.eaott.least_squares_solver.math.SingleVarFunction;
import com.github.eaott.least_squares_solver.math.Point;
import com.github.eaott.least_squares_solver.math.Matrix;

public class SpatialSolver {
	/**
	 * 
	 * @param N
	 * @param pts
	 *            array of points (just x,y)
	 * @return
	 */
	public static SingleVarFunction leastSquares(final int N, Set<Point> pts) {
		double[][] X = new double[pts.size()][N + 1];
		double[][] Y = new double[pts.size()][1];
		int index = 0;
		for (Point p : pts) {
			final double x = p.x;
			final double y = p.y;
			X[index][0] = 1;
			Y[index][0] = y;
			for (int i = 1; i <= N; i++) {
				X[index][i] = X[index][i - 1] * x;
			}
			index++;
		}
		Matrix matX = new Matrix(X);
		Matrix matY = new Matrix(Y);

		Matrix last = matX.transpose().mult(matY);
		Matrix first = matX.transpose().mult(matX);

		final Matrix terms = first.inverse().mult(last);

		return new SingleVarFunction() {

			@Override
			public double evaluate(double var) {
				double sum = 0;
				double mult = 1;
				for (int i = 0; i < terms.rows(); i++) {
					sum += terms.get(i, 0) * mult;
					mult *= var;
				}
				return sum;
			}

			@Override
			public double[] coeffs() {
				double[] ret = new double[N + 1];
				for (int i = 0; i < ret.length; i++)
					ret[i] = terms.get(i, 0);
				return ret;
			}
		};
	}
}
