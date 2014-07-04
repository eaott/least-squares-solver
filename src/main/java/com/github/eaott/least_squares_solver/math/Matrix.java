package com.github.eaott.least_squares_solver.math;

public class Matrix
{
	double[][] mat;
	public Matrix(double[][] m)
	{
		mat = m;
	}
	public double[][] getMat()
	{
		return mat;
	}
	public double get(int r, int c)
	{
		return mat[r][c];
	}
	public int rows()
	{
		return mat.length;
	}
	public int cols()
	{
		return mat[0].length;
	}
	public Matrix transpose()
	{
		return transpose(this);
	}
	public static Matrix transpose(Matrix a)
	{
		double[][] mat = new double[a.cols()][a.rows()];
		for (int r = 0; r < a.cols(); r++)
			for (int c = 0; c < a.rows(); c++)
				mat[r][c] = a.mat[c][r];
		return new Matrix(mat);
	}
	public Matrix mult(Matrix o)
	{
		return mult(this, o);
	}
	public static Matrix mult(Matrix a, Matrix b)
	{
		double[][] res = new double[a.rows()][b.cols()];
		
		for (int r = 0; r < res.length; r++)
			for (int c = 0; c < res[0].length; c++)
			{
				double sum = 0;
				// a.cols() == b.rows()
				for (int k = 0; k < a.cols(); k++)
				{
					sum += a.get(r, k) * b.get(k, c);
				}
				res[r][c] = sum;
			}
		return new Matrix(res);
	}
	public Matrix inverse()
	{
		return inverse(this);
	}
	
	private void multRow(int r, double val)
	{
		for (int c = 0; c < cols(); c++)
			mat[r][c] *= val;
	}
	private void addRow(int r, double mult, double[] vals)
	{
		for (int c = 0; c < cols(); c++)
			mat[r][c] += vals[c] * mult;
	}
	public static Matrix inverse(Matrix m)
	{
		// do Gauss-Jordan elimination...
		// Copy into augmented matrix including I.
		double[][] mat = new double[m.rows()][m.cols() * 2];
		for (int r = 0; r < m.rows(); r++)
		{
			mat[r][m.cols() + r] = 1;
			for (int c = 0; c < m.cols(); c++)
			{
				mat[r][c] = m.mat[r][c];
			}
		}
		
		// Get rid of the lower-left triangle
		Matrix temp = new Matrix(mat);

		for (int r = 0; r < m.rows(); r++)
		{
			temp.multRow(r, 1.0 / temp.mat[r][r]);
			for (int r2 = r + 1; r2 < m.rows(); r2++)
			{
				temp.addRow(r2, -temp.mat[r2][r], temp.mat[r]);
			}
		}

		// Get rid of upper-left triangle
		for (int r = m.rows() - 1; r > 0; r--)
		{
			for (int r2 = 0; r2 < r; r2++)
			{
				temp.addRow(r2, -temp.mat[r2][r], temp.mat[r]);
			}
		}

		double[][] ret = new double[m.rows()][m.cols()];
		for (int r = 0; r < m.rows(); r++)
			for (int c = 0; c < m.cols(); c++)
				ret[r][c] = temp.mat[r][m.cols() + c]; 
		return new Matrix(ret);
	}
}
