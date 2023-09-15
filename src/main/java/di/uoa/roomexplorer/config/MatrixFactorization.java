package di.uoa.roomexplorer.config;

import org.la4j.Matrix;
import org.la4j.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MatrixFactorization {

    private final Matrix R;
    private final int K;
    private final double learningRate;
    private Matrix P;
    private Matrix Q;


    public MatrixFactorization(Matrix r, int k, double learningRate) {
        R = r;
        K = k;
        this.learningRate = learningRate;
        this.P = Matrix.random(this.R.rows(), this.K, new Random());
        this.Q = Matrix.random(this.K, this.R.columns(), new Random());
    }

    public void train() {

        List<List<Integer>> nonEmptyCells = new ArrayList<>();
        for (int row = 0; row < R.rows(); row++) {
            for (int col = 0; col < R.columns(); col++) {
                if (R.get(row, col) > 0) {
                    List<Integer> cords = new ArrayList<>();
                    cords.add(row);
                    cords.add(col);
                    nonEmptyCells.add(cords);
                }
            }
        }

        int nonEmptyCount = nonEmptyCells.size();

        double rmsePrev = -2;
        double rmse = -1;

        while (Math.abs(rmse - rmsePrev) > 0.00005) {
            double totalSquaredError = 0;
            for (List<Integer> cords : nonEmptyCells) {
                Vector v1 = P.getRow(cords.get(0));
                Vector v2 = Q.getColumn(cords.get(1));
                double error = R.get(cords.get(0), cords.get(1)) - v1.innerProduct(v2);

                P.setRow(cords.get(0), P.getRow(cords.get(0)).add(Q.getColumn(cords.get(1)).multiply(this.learningRate * 2 * error)));
                Q.setColumn(cords.get(1), Q.getColumn(cords.get(1)).add(P.getRow(cords.get(0)).multiply(this.learningRate * 2 * error)));
                totalSquaredError += Math.pow(error, 2);
                rmsePrev = rmse;
                rmse = Math.sqrt(totalSquaredError / nonEmptyCount);
            }

        }
    }

    public Vector getPrediction(int i) {
        return this.P.getRow(i).multiply(this.Q);
    }

    public Matrix getFullMatrix() {
        return this.P.multiply(this.Q);
    }
}
