package awesome.tony.matrixfactoring.utils;

public class VectorUtils {

	public static double multiplyAndSum(double [] left, double [] right){
		double accumulator = 0;
		int i = left.length;
		if(i != right.length) throw new RuntimeException("Bad dimensions");
		
		for(i--; i>=0; i--){
			accumulator += left[i] * right[i];
		}
		return accumulator;
	}
	                   

}
