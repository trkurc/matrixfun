package awesome.tony.matrixfactoring;

import java.io.File;
import java.io.PrintStream;
import java.util.Collection;

import org.junit.Test;

import awesome.tony.matrixfactoring.RandomRatings;
import awesome.tony.matrixfactoring.RatingTuple;

public class TestRatingMaking {
	@Test
	public void testPureRandom(){
		Collection<RatingTuple> ratings = RandomRatings.generatePureRandom(10, 10, 0L);
		try{
		PrintStream x = new PrintStream(new File("ratings.txt"));
		RatingTuple.printRatings(x, ratings);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
