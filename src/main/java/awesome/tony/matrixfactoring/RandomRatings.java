package awesome.tony.matrixfactoring;

import java.util.Collection;
import java.util.Random;
import java.util.Vector;


public class RandomRatings {

	
	static public Collection<RatingTuple> generatePureRandom(int users, int items, long seed){
		Vector<RatingTuple> rv = new Vector<RatingTuple>();
		Random random = new Random(seed);
		for(int i=0; i<users; i++){
			int numRatings = Math.abs(random.nextInt() % items);
			for(int j=0; j < numRatings; j++){
				int itemToRank = Math.abs(random.nextInt() % items);
				RatingTuple rating = new RatingTuple(i, itemToRank, Math.abs(random.nextGaussian()), 1);
				rv.add(rating);
			}
		}
		return rv;
	}
}
