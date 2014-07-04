package com.github.eaott.least_squares_solver;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import com.github.eaott.least_squares_solver.math.Point;
import com.github.eaott.least_squares_solver.math.SingleVarFunction;

public class TestSolver {
	public static final double TOLERANCE = 0.0001;
	static SingleVarFunction actualFunction;
	static SingleVarFunction result;

	@BeforeClass
	public static void before() {
		actualFunction = new SingleVarFunction(){
			double[] coeffs = {2, -3, 5};
			@Override
			public double evaluate(double var) {
				double sum = 0;
				double mult = 1;
				for (int i = 0; i < coeffs.length; i++) {
					sum += coeffs[i] * mult;
					mult *= var;
				}
				return sum;
			}

			@Override
			public double[] coeffs() {
				return coeffs;
			}
			
		};
		Set<Point> set = new HashSet<>();
		for (int i = 0; i < 50; i++)
			set.add(new Point(i, actualFunction.evaluate(i)));
		result = SpatialSolver.leastSquares(actualFunction.coeffs().length - 1, set);
	}

	@Test
	public void testSize() {
		double[] coeffs = result.coeffs();
		assertTrue("Size is wrong", coeffs.length == actualFunction.coeffs().length);
	}

	@Test
	public void testCoeffs() {
		double[] coeffs = result.coeffs();
		for (int i = 0; i < coeffs.length; i++)
		{
			assertTrue(String.format("Coefficient value #%d was incorrect. Was %.2f should be %.2f.",
					i, coeffs[i], actualFunction.coeffs()[i]), 
					
					Math.abs(coeffs[i] - actualFunction.coeffs()[i]) < TOLERANCE);
		}
	}
	
	@Test
	public void testVals() {
		for (int i = 0; i < 500; i++)
		{
			double rand = Math.random() * 20000 - 10000;
			assertTrue(String.format("Value for argument %.2f was incorrect. Was %.2f should be %.2f",
					rand, result.evaluate(rand), actualFunction.evaluate(rand)),
					Math.abs(result.evaluate(rand) - actualFunction.evaluate(rand)) < TOLERANCE);
		}
	}
}
