package di.uoa.roomexplorer.services;

import di.uoa.roomexplorer.config.MatrixFactorization;
import di.uoa.roomexplorer.repositories.RenterRepo;
import di.uoa.roomexplorer.repositories.ResidenceRepo;
import org.la4j.Matrix;
import org.la4j.Vector;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class MatrixFactorizationService {

    private MatrixFactorization matrixFactorization;
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

    public void addRow() {
        this.matrix = this.matrix.insertRow(this.matrix.rows()-1, Vector.zero(this.matrix.columns()));
        this.matrix.swapRows(this.matrix.rows()-1, this.matrix.rows()-2);
    }

    public void addColumn() {
        this.matrix = this.matrix.insertColumn(this.matrix.columns()-1, Vector.zero(this.matrix.rows()));
        this.matrix.swapColumns(this.matrix.columns()-1, this.matrix.columns()-2);
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
                    prediction.set(j, 0);
                    break;
                }
            }
        }

        return recommendedResidenceIndexes;
    }
}
