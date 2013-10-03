package awesome.tony.matrixfactoring;

import java.util.Collection;

import awesome.tony.matrixfactoring.utils.MatrixUtils;
import awesome.tony.matrixfactoring.utils.VectorUtils;


public class ProbabilisticMatrixFactorization {
	int latentDimensions = 10;
	double learningRate = .0001;
	double regularizationStrength = 0.1;

	boolean hasConverged = false;
	double[][] users;
	double[][] items;
	double[][] newUsers;
	double[][] newItems;
	int numUsers;
	int numItems;
	private Collection<RatingTuple> ratings;


	public ProbabilisticMatrixFactorization(int latentDimensions) {
		this.latentDimensions = latentDimensions;
	}

	public void setMatrix(Collection<RatingTuple> ratings){
		this.ratings = ratings;
		int maxUser = -1;
		int maxItem = -1;
		for(RatingTuple tuple : ratings){
			maxUser = Math.max(tuple.userId, maxUser);
			maxItem = Math.max(tuple.itemId, maxItem);
		}
		this.numUsers = maxUser+1;
		this.numItems = maxItem+1;
		users = new double[numUsers][latentDimensions];
		items = new double[numItems][latentDimensions];
		newUsers = new double[numUsers][latentDimensions];
		newItems = new double[numItems][latentDimensions];
		
		MatrixUtils.fillRandom(users);
		MatrixUtils.fillRandom(items);
		MatrixUtils.fillRandom(newUsers);
		MatrixUtils.fillRandom(newItems);
		
	}

	public double likelihood(){
		return likelihood(users, items);
	}
	
	private double likelihood(double[][] users, double[][] items){
		double sq_error = 0;
        
        for(RatingTuple tuple : ratings){
            int i = tuple.userId;
            int j = tuple.itemId;
            double score = tuple.score;
            double weight = tuple.weight;
           
            double rHat = VectorUtils.multiplyAndSum(users[i], items[j]);
            
            
            sq_error += weight * (score - rHat)*(score -rHat);
        }
        double L2_norm = 0;
        for(int i=0; i < numUsers; i++){
            for(int d=0; d < latentDimensions; d++){	
                L2_norm += users[i][d]*users[i][d];
            }   
        }
        for(int i=0; i < numItems; i++){
        	for(int d=0; d < latentDimensions; d++){
                L2_norm += items[i][d]*items[i][d];
        	}
		}
        return -sq_error - regularizationStrength * L2_norm;
	}

	public boolean update(){

		double [][] updatesO = new double[numUsers][latentDimensions];
		double [][] updatesD = new double[numItems][latentDimensions];

		for(RatingTuple rating : ratings){
			int i = rating.userId;
			int j = rating.itemId;
			double weight = rating.weight;
			double score = rating.score;
			double rHat = VectorUtils.multiplyAndSum(users[i], items[j]);

			for(int d = 0; d < latentDimensions; d++){
				updatesO[i][d] += items[j][d] * (score - rHat) * weight;
				updatesD[j][d] += users[i][d] * (score - rHat) * weight;
			}
		}

		while (hasConverged == false){
			double initialLikelihood = this.likelihood(users, items);

			System.out.println("  setting learning rate =" +  learningRate);
			tryUpdates(updatesO, updatesD);

			double finalLikelihood = likelihood(newUsers, newItems);

			if(finalLikelihood > initialLikelihood){
				applyUpdates(updatesO, updatesD);
				learningRate *= 1.25;

				if(finalLikelihood - initialLikelihood < .1){
					hasConverged = true;
				}
				break;
			}
			else{
				learningRate *= .5;
				undoUpdates();
			}
			if(learningRate < 1e-10){
				hasConverged = true;
			}
		}
		return !hasConverged;



	}

	private void applyUpdates(double[][] updatesO, double[][] updatesD) {
		for(int i=0; i < numUsers; i++){
			System.arraycopy(newUsers[i], 0, users[i], 0, latentDimensions);
		}
		for(int i=0; i < numItems; i++){
			System.arraycopy(newItems[i], 0, items[i], 0, latentDimensions);      
		}
	}

	private void undoUpdates() {

	}

	private void tryUpdates(double[][] updatesO, double[][] updatesD) {
		double alpha = learningRate;
		double beta = -regularizationStrength;

		for(int i=0; i < numUsers; i++){
			for(int d=0; d < latentDimensions; d++){
				newUsers[i][d] = users[i][d] + alpha * (beta * users[i][d] + updatesO[i][d]);
			}
		}
		for(int i=0; i < numItems; i++){
			for(int d=0; d < latentDimensions; d++){
				newItems[i][d] = items[i][d] + alpha * (beta * items[i][d] + updatesD[i][d]);
			}
		}

	}
}
