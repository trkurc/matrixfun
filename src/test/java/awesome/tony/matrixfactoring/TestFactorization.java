package awesome.tony.matrixfactoring;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.Vector;

import org.junit.Ignore;
import org.junit.Test;

import awesome.tony.matrixfactoring.ProbabilisticMatrixFactorization;
import awesome.tony.matrixfactoring.RandomRatings;
import awesome.tony.matrixfactoring.RatingTuple;
import awesome.tony.matrixfactoring.utils.MatrixUtils;


public class TestFactorization {
	@Ignore
	public void testPureRandom(){
		Collection<RatingTuple> ratings = RandomRatings.generatePureRandom(10, 10, 0L);
		ProbabilisticMatrixFactorization pmf = new ProbabilisticMatrixFactorization(5);
		pmf.setMatrix(ratings);
		
		while(pmf.update()){
			System.out.println("L=" +pmf.likelihood());
		}
		for(double [] row : pmf.users){
			System.out.print("[");
			System.out.print(row[0]);
			for(int i=1; i< row.length; i++){
				System.out.print(", " +row[i]);
			}
			System.out.println("]");
		}
	}
	@Test
	public void testFromFile(){
		try{
			BufferedReader reader = new BufferedReader(new FileReader("ratings.txt"));
			String line;
			Vector<RatingTuple> ratings = new Vector<RatingTuple>();
			while((line= reader.readLine()) != null){
				String split[] = line.split("\t");
				RatingTuple t = new RatingTuple(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]));
				ratings.add(t);			
			}
			ProbabilisticMatrixFactorization pmf = new ProbabilisticMatrixFactorization(5);
			pmf.setMatrix(ratings);
			
			while(pmf.update()){
				System.out.println("L=" +pmf.likelihood());
			}
			MatrixUtils.printMultiplication(pmf.users, pmf.items);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
}
