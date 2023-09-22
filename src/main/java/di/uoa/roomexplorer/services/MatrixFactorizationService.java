package di.uoa.roomexplorer.services;

import di.uoa.roomexplorer.config.MatrixFactorization;
import di.uoa.roomexplorer.repositories.RenterRepo;
import di.uoa.roomexplorer.repositories.ResidenceRepo;
import lombok.Getter;
import org.la4j.Matrix;
import org.la4j.Vector;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class MatrixFactorizationService {

    private MatrixFactorization matrixFactorization;
    @Getter
    private Matrix matrix;


    private final RenterRepo renterRepo;
    private final ResidenceRepo residenceRepo;

    public void init(Matrix matrix) {
        this.matrix = matrix;
        this.matrixFactorization = new MatrixFactorization(matrix, 100, 0.001);
    }

    public MatrixFactorizationService(RenterRepo renterRepo, ResidenceRepo residenceRepo) {
        this.renterRepo = renterRepo;
        this.residenceRepo = residenceRepo;
    }

    public void addRow(int renterIndex) {

        if (renterIndex == this.matrix.rows()) {
            this.matrix = this.matrix.insertRow(this.matrix.rows()-1, Vector.zero(this.matrix.columns()));
            this.matrix.swapRows(this.matrix.rows()-1, this.matrix.rows()-2);

            Matrix myP = this.matrixFactorization.getP();
            myP = myP.insertRow(myP.rows()-1, Vector.zero(myP.columns()));
            myP.swapRows(myP.rows()-1, myP.rows()-2);
            this.matrixFactorization.setP(myP);
        }
        else {
            this.matrix = this.matrix.insertRow(renterIndex, Vector.zero(this.matrix.columns()));
            this.matrixFactorization.setP(this.matrixFactorization.getP().insertRow(renterIndex, Vector.zero(this.matrixFactorization.getP().columns())));
        }
    }

    public void addColumn(int residenceIndex) {

        if (residenceIndex == this.matrix.columns()) {
            this.matrix = this.matrix.insertColumn(this.matrix.columns()-1, Vector.zero(this.matrix.rows()));
            this.matrix.swapColumns(this.matrix.columns()-1, this.matrix.columns()-2);

            Matrix myQ = this.matrixFactorization.getQ();
            myQ = myQ.insertColumn(myQ.columns()-1, Vector.zero(myQ.rows()));
            myQ.swapColumns(myQ.columns()-1, myQ.columns()-2);
            this.matrixFactorization.setQ(myQ);
        }
        else {
            this.matrix = this.matrix.insertColumn(residenceIndex, Vector.zero(this.matrix.rows()));
            this.matrixFactorization.setQ(this.matrixFactorization.getQ().insertColumn(residenceIndex, Vector.zero(this.matrixFactorization.getQ().rows())));
        }
    }

    public void updateCellIndex(int renterIndex, int residenceIndex, int stars) {
        this.matrix.set(renterIndex, residenceIndex, stars);
    }

    public void updateCellId(Long renterId, Long residenceId, int stars) {
        List<Long> renters = renterRepo.findAllRenterId();
        int renterIndex = renters.indexOf(renterId);

        List<Long> residences = residenceRepo.findAllResidenceId();
        int residenceIndex = residences.indexOf(residenceId);

        this.matrix.set(renterIndex, residenceIndex, stars);
    }

    public double getCellValueId(Long renterId, Long residenceId) {
        List<Long> renters = renterRepo.findAllRenterId();
        int renterIndex = renters.indexOf(renterId);

        List<Long> residences = residenceRepo.findAllResidenceId();
        int residenceIndex = residences.indexOf(residenceId);

        return this.matrix.get(renterIndex, residenceIndex);
    }

    public void train() {
        this.matrixFactorization.train();
    }

    public List<Integer> getPredictions(Long renterId, List<Integer> reservedResidencesIndexes) {

        List<Long> renters = renterRepo.findAllRenterId();
        int renterIndex = renters.indexOf(renterId);
        Vector prediction = this.matrixFactorization.getPrediction(renterIndex);

        final int predictionsNum = 5;
        List<Integer> recommendedResidenceIndexes = new LinkedList<>();

        for (int i = 0; i < predictionsNum; i++) {
            double maxVal = prediction.max();
            int size = prediction.length();
            for (int j = 0; j < size; j++) {
                if (prediction.get(j) == maxVal) {
                    if (!reservedResidencesIndexes.contains(j)) {
                        recommendedResidenceIndexes.add(j);
                    }
                    else {
                        i--;
                    }
                    prediction.set(j, -1);
                    break;
                }
            }
        }

        return recommendedResidenceIndexes;
    }

    public Matrix getFullMatrix() { return this.matrixFactorization.getFullMatrix(); }
}
