package di.uoa.roomexplorer.config;

import org.la4j.Matrix;
import org.la4j.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MatrixFactorization {

    private Matrix R;
    private int K;
    private double learningRate;
    private Matrix P;
    private Matrix Q;


    public MatrixFactorization(Matrix r, int k, double learningRate) {
        R = r;
        K = k;
        this.learningRate = learningRate;
    }

    public void train() {

        this.P = Matrix.random(this.R.rows(), this.K, new Random());
        this.Q = Matrix.random(this.K, this.R.columns(), new Random());

//        System.out.println(P.toString());
//        System.out.println(Q.toString());

        List<List<Integer>> nonEmptyCells = new ArrayList<List<Integer>>();
        for (int row = 0; row < R.rows(); row++) {
            for (int col = 0; col < R.columns(); col++) {
                if (R.get(row, col) > 0) {
                    List<Integer> cords = new ArrayList<Integer>();
                    cords.add(row);
                    cords.add(col);
                    nonEmptyCells.add(cords);
                }
            }
        }

        int nonEmptyCount = nonEmptyCells.size();

        double rmsePrev = -2;
        double rmse = -1;
        int count = 0;
        while (Math.abs(rmse - rmsePrev) > 0.00008) {
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

            count++;
        }
    }

    public Vector getPrediction(int i) {
//        Vector predictions = this.P.getRow(i).multiply(this.Q);
//        List<Double> predictionsList = new ArrayList<>();
//        for (double value : predictions) {
//            predictionsList.add(value);
//        }
//
//        return predictionsList;
        return this.P.getRow(i).multiply(this.Q);
    }

    public Matrix getFullMatrix() {
        return this.P.multiply(this.Q);
    }
}
