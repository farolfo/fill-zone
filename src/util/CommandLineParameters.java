package util;

import gps.GPSEngine;
import gps.implementation.AStarEngine;
import gps.implementation.BFSEngine;
import gps.implementation.DFSEngine;
import gps.implementation.GreedyEngine;
import gps.implementation.IDEngine;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class CommandLineParameters {

	@Option(name = "-M") 
	String mode;
	
	@Option(name = "-d")
	String dimension;
	
	@Option(name = "-m")
	String maxMoves;
   
  @Option(name = "-h")
	String heuristic;
	
	public void load(String[] args) {

		CmdLineParser parser = new CmdLineParser(this);

		// if you have a wider console, you could increase the value;
		// here 80 is also the default
		parser.setUsageWidth(80);

		try {
			// parse the arguments.
			parser.parseArgument(args);

		} catch (CmdLineException e) {
			System.out.println("A problem has occured involving the command line parameters.");
			return;
		}

	}
	
	public GPSEngine getEngine(){
		if ( mode == null || mode.isEmpty()) {
			System.err.println("Invalid searching mode. You must to indicateit with  '-M (DFS|BFS|ID|ASTAR|GREEDY)'");
			return null;
		}
		GPSEngine engine = null;
		if ( mode.equalsIgnoreCase("DFS") ) {
			engine = new DFSEngine();
		} else if ( mode.equalsIgnoreCase("BFS") ) {
			engine = new BFSEngine();
		} else if ( mode.equalsIgnoreCase("ID") ) {
			engine = new IDEngine();
		} else if ( mode.equalsIgnoreCase("ASTAR") ) {
			engine = new AStarEngine();
		} else if ( mode.equalsIgnoreCase("GREEDY") ) {
			engine = new GreedyEngine();
		} else{
			System.err.println("Invalid searching mode. You must to indicateit with  '-M (DFS|BFS|ID|ASTAR|GREEDY)'");
		}
		return engine;		
	}
	
	private Integer getNumber(String param, String paramFormatted){
		if( param == null || param.isEmpty()){
			return null;
		}
		Integer num = null;
		try{
			num = Integer.valueOf(param);
			if(num <= 0){
				System.out.println("Invalid "+paramFormatted+" value. It must be a positive integer. Default value's been used.");
				return null;
			}
			return num;
		} catch(NumberFormatException e){
			System.out.println("Invalid " +paramFormatted+ " syntax. Default value's been used.");
		}
		return null;
	}
	
	public Integer getDimension(){
		return getNumber(dimension, "dimension");
	}
	
  public Integer getHeuristic(){
		return getNumber(heuristic, "heuristic");
	} 
  
	public Integer getMaxMoves(){
		return getNumber(maxMoves, "max. moves");
	}

	
}