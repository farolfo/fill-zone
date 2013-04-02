package gps;

import gps.api.implementation.FillZoneProblem;
import util.CommandLineParameters;

public class FillZone {

	public static void main(String[] args) {
		/**
		 * sintaxis de entrada: .jar -M (dfs|bfs|id|astar|greedy) -m cantMoves -d dimension -h (1|2|3|4)
		 * En cualquier orden
		 */
		CommandLineParameters clp = new CommandLineParameters();
		clp.load(args);
		GPSEngine engine = clp.getEngine();
		if( engine == null ){
			//Syso logging is in CommandLineParameters class
			return;
		}

		Integer dimmension = clp.getDimension();
		if( dimmension != null ){
			FillZoneProblem.setBoardDimension(dimmension);
		}

		Integer maxMoves = clp.getMaxMoves();
		if( maxMoves != null ){
			FillZoneProblem.setMaxMoves(maxMoves);
		}

		Integer heuristic = clp.getHeuristic();
		if( heuristic != null ){
			if(heuristic <= 0 || heuristic >= 5){
				System.out.println("Heuristic must be beetwen 1 and 4. Default value has been used.");
			}else{
				FillZoneProblem.setHeuristic(heuristic);
			}
		}
		FillZoneSolution solution = engine.engine(new FillZoneProblem());
		if ( solution == null ) {
			System.err.println("FAILED! solution not found!");
		} else {
			System.out.println("OK! solution found!");
			System.out.println(solution);
		}
	}
}
