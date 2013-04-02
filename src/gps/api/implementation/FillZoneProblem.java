package gps.api.implementation;

import gps.api.GPSProblem;
import gps.api.GPSRule;
import gps.api.GPSState;
import gps.api.implementation.FillZoneState.Island;
import gps.api.implementation.rule.BlueRule;
import gps.api.implementation.rule.GreenRule;
import gps.api.implementation.rule.MagentaRule;
import gps.api.implementation.rule.RedRule;
import gps.api.implementation.rule.WhiteRule;
import gps.api.implementation.rule.YellowRule;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import util.Graph;

public class FillZoneProblem implements GPSProblem {

	private static Integer boardDimension = 10;
	private static Integer colors = 6;
	private static Integer maxMoves = 30;
	private static int heuristic = 1;
	private int[][] board = null;

	public FillZoneProblem() {
	}
	
	public FillZoneProblem(int[][] board) {
		this.board = board;
	}
	
	public static Integer getBoardDimension() {
		return boardDimension;
	}

	public static void setBoardDimension(Integer boardDimension) {
		FillZoneProblem.boardDimension = boardDimension;
	}

	public static Integer getColors() {
		return colors;
	}

	public static Integer getMaxMoves() {
		return maxMoves;
	}

	public static void setMaxMoves(Integer maxMoves) {
		FillZoneProblem.maxMoves = maxMoves;
	}

	@Override
	public GPSState getInitState() {
		if (board == null){
			return new FillZoneState();
		}
		return new FillZoneState(board);
	}

	@Override
	public List<GPSState> getGoalStates() {
		List<GPSState> list = new LinkedList<GPSState>();
		for (int i = 0; i < colors; i++) {
			list.add(new FillZoneState(i));
		}
		return list;
	}

	@Override
	public List<GPSRule> getRules() {
		List<GPSRule> rules = new LinkedList<GPSRule>();
		rules.add(BlueRule.getInstance());
		rules.add(GreenRule.getInstance());
		rules.add(MagentaRule.getInstance());
		rules.add(RedRule.getInstance());
		rules.add(WhiteRule.getInstance());
		rules.add(YellowRule.getInstance());
		return rules;
	}

	@Override
	public Integer getHValue(GPSState state) {
		switch (heuristic){
		case 1:
			return (int)Math.pow(boardDimension, 2) - ((FillZoneState)state).getDominatedCells();
		case 2:
			return ((FillZoneState)state).getIslands();
		case 3:
			return ((FillZoneState)state).getCornerValue();
		case 4:
			return kNeighboursHeuristic(state);
	}
		return null;
	}

	
	public static Integer kNeighboursHeuristic(GPSState state) {
		Map<Integer/*Color!*/, Integer/*Max. K founded*/> colorsAnalized = new HashMap<Integer, Integer>();
		Graph<Island> graph = ((FillZoneState) state).getIslandGraph();
		Set<Island> islands = graph.getElements();
		boolean dominatedColorIsSingle = false;
		// first check for single islands
		for (int i = 0; i < FillZoneProblem.colors; i++) {
			int cant = 0;
			for (Island island : islands) {
				if (island.color == i) {
					cant++;
					if (cant == 2) {
						break;
					}
				}
			}
			if(cant == 1){
				if(((FillZoneState)state).getDominatedColor() == i ){
					dominatedColorIsSingle = true;
				}
				colorsAnalized.put(i, 1);
			}
		}
		for (Island island : islands) {
			if (!colorsAnalized.containsKey(island.color)) {
				boolean colorHasBeenAnalized = false;
				for (int i = islands.size() - 1; i >= 2 && !colorHasBeenAnalized ; i--) { // i=1 impossible case in the problem, i=0 non important
					List<Island> iNeighbours = graph.getKNeighbours(island, i);
					for (Island neighbour : iNeighbours) {
						if (neighbour.color == island.color) {
							/*Found the bigest K in that color*/
							colorsAnalized.put(island.color, i-1); // -1 because : 2Neighbour means One node between them
							colorHasBeenAnalized = true;
							break;
						}
					}
				}
			}
		}
		// THE heruistic function !
		int value = 0;
		for(Integer kValues : colorsAnalized.values()){
			value += kValues;
		}
		return (dominatedColorIsSingle)? value-1: value;
	}

	public static void setHeuristic(Integer heuristic2) {
		FillZoneProblem.heuristic = heuristic2;
	}
}
