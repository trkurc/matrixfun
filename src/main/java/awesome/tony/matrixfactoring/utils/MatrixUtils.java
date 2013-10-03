package awesome.tony.matrixfactoring.utils;

import java.util.Random;

public class MatrixUtils {
	public static void fillRandom(double[][] newUsers){
		fillRandom(newUsers, 0L);
	}
	
	
	public static void fillRandom(double[][] newUsers, long seed) {
		Random random = new Random(seed);
		for(double [] row : newUsers){
			for(int i=0; i < row.length; i++){
				row[i] =  Math.abs(random.nextDouble());
			}
		}
		
	}
	
	public static void printMultiplication(double A[][], double B[][]){
		  for(int i=0; i < A.length; i++){
			  for(int j=0; j < B.length; j++){
				   // dot product of A[i] B[j]
		           System.out.printf("%s%5.5f", (j==0)?"":"," ,VectorUtils.multiplyAndSum(A[i], B[j]));
			  }
			  System.out.printf("\n");
			}
	}


	                                                                        
}
