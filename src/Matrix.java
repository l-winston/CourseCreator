import java.util.function.Function;

public class Matrix {
	/*
	 * Wrapper for the Colt library. Makes doing Matrix operations more clear
	 * and less verbose.
	 */
	private int rows;
	private int cols;
	private double[][] mat;

	Matrix(int r, int c) {
		rows = r;
		cols = c;
		mat = new double[r][c];
	}

	Matrix(Matrix a) {
		mat = new double[a.rows][a.cols];
		rows = a.rows;
		cols = a.cols;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++)
				mat[i][j] = a.mat[i][j];
		}
	}

	private double[][] cloneInternalObject() {
		return copy();
	}

	private double[][] copy() {
		double[][] ret = new double[this.rows][this.cols];
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				ret[i][j] = this.mat[i][j];
			}
		}
		return ret;
	}

	private double[][] toInternalObject(Matrix matObj) {
		return matObj.cloneInternalObject();
	}

	private Matrix toMatrixObject(double[][] internalObj) {
		int r = internalObj.length;
		int c = internalObj[0].length;
		Matrix matObj = new Matrix(r, c);
		for (int i = 0; i < r; i++)
			for (int j = 0; j < c; j++)
				matObj.set(i, j, internalObj[i][j]);
		return matObj;
	}

	void set(int r, int c, double v) {
		mat[r][c] = v;
	}

	double get(int r, int c) {
		return mat[r][c];
	}

	int get_rows() {
		return rows;
	}

	int get_cols() {
		return cols;
	}

	void printMatrix() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				System.out.print(mat[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println();
	}

	Matrix matrixAdd(Matrix matB) {
		double[][] newMat = cloneInternalObject();
		double[][] matBInternal = toInternalObject(matB);
		
		for(int i = 0; i < newMat.length; i++){
			for(int j =0; j < newMat[0].length; j++){
				newMat[i][j] += matBInternal[i][j];
			}
		}
		
		return toMatrixObject(newMat);
	}

	Matrix matrixSub(Matrix matB) {
		double[][] newMat = cloneInternalObject();
		double[][] matBInternal = toInternalObject(matB);
		
		for(int i = 0; i < newMat.length; i++){
			for(int j =0; j < newMat[0].length; j++){
				newMat[i][j] -= matBInternal[i][j];
			}
		}
		
		return toMatrixObject(newMat);
	}

	Matrix matrixMult(Matrix matB){
        double[][] ret = new double[rows][matB.cols];
        
        for (int i = 0; i < rows; i++) {
			for (int j = 0; j < matB.cols; j++) {
				double x = 0;
				for (int col = 0; col < cols; col++) {
						x += mat[i][col] * matB.get(col, j);
				}
				ret[i][j] = x;
			}
		}
        
        return toMatrixObject(ret);
    }

	Matrix matrixTranspose() {
		double[][] ret = new double[cols][rows];
		
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < cols; j++){
				ret[j][i] = mat[i][j];
			}
		}
		
		return toMatrixObject(ret);
	}

	Matrix schurProduct(Matrix matB) {
		Matrix newMat = new Matrix(rows, cols);
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				newMat.set(i, j, mat[i][j] * matB.get(i, j));
		return newMat;
	}
	 Matrix applyFunc(Function<Double, Double> fn){
	        double[][] newMat = cloneInternalObject();
	        for(int i = 0; i < rows; i++){
	            for(int j = 0; j < cols; j++){
	                double v = fn.apply(mat[i][j]);
	                newMat[i][j] = v;
	            }
	        }
	        return toMatrixObject(newMat);
	    }

}
