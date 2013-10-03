package awesome.tony.matrixfactoring;

import java.io.PrintStream;
import java.util.Collection;

public class RatingTuple {
	public RatingTuple(int i, int j, double nextGaussian, double k) {
		this.userId = i;
		this.itemId = j;
		this.score = nextGaussian;
		this.weight = k;			
	}
	
	int userId;
	int itemId;
	double score; 
	double weight;
	
	static public void printRatings(Collection<RatingTuple> tuples){
		printRatings(System.out, tuples);
	}
	static public void printRatings(PrintStream out, Collection<RatingTuple> tuples){
		int lastUser = -1;
		for(RatingTuple tuple : tuples){
			//if(tuple.userId != lastUser){
			//	out.println("\nUser: " + tuple.userId);
			//	lastUser = tuple.userId;
			//}
			out.printf("%d\t%d\t%f\t%f\r\n" , tuple.userId, tuple.itemId, tuple.score, tuple.weight);
		}
	}	
}
