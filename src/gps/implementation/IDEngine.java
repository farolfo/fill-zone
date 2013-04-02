package gps.implementation;

import gps.FillZoneSolution;
import gps.api.GPSProblem;

public class IDEngine extends DFSEngine {

	@Override
	public FillZoneSolution engine(GPSProblem myProblem) {
		FillZoneSolution partialSolution = null;
		FillZoneSolution solution = new FillZoneSolution("", 0, 0, 0, 0, false);
		for ( int i = 0 ; i < Integer.MAX_VALUE ; i++ ) {
			partialSolution = super.engine(myProblem, i);
			solution.add(partialSolution);
			if ( partialSolution.isFinished() ) { 
				break;
			}
		}
		return solution;
	}

}
