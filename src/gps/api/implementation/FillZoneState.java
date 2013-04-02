package gps.api.implementation;

import gps.api.GPSState;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import util.Graph;

public class FillZoneState implements GPSState, Cloneable {

	private int[][] board;
	private int movesLeft = FillZoneProblem.getMaxMoves();

	@Override
	public boolean compare(GPSState state) {
		if (!(state instanceof FillZoneState)) {
			return false;
		}
		FillZoneState other = (FillZoneState) state;
		for (int i = 0; i < FillZoneProblem.getBoardDimension(); i++) {
			for (int j = 0; j < FillZoneProblem.getBoardDimension(); j++) {
				if (board[i][j] != other.board[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	public FillZoneState(int[][] board) {
		this.board = board;
	}

	public FillZoneState() {
		this(new int[FillZoneProblem.getBoardDimension()][FillZoneProblem
				.getBoardDimension()]);
		for (int i = 0; i < FillZoneProblem.getBoardDimension(); i++) {
			for (int j = 0; j < FillZoneProblem.getBoardDimension(); j++) {
				board[i][j] = (int) (Math.random() * FillZoneProblem
						.getColors());
			}
		}
//		firstBoard();
//		secondBoard();
//		thirdBoard();
//		fourthBoard();
//		fifthBoard();
	}

	public FillZoneState(int color) {
		this(new int[FillZoneProblem.getBoardDimension()][FillZoneProblem
				.getBoardDimension()]);
		if (color >= FillZoneProblem.getColors()) {
			board = null;
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < FillZoneProblem.getBoardDimension(); i++) {
			for (int j = 0; j < FillZoneProblem.getBoardDimension(); j++) {
				board[i][j] = color;
			}
		}
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		int[][] newBoard = new int[FillZoneProblem.getBoardDimension()][FillZoneProblem
				.getBoardDimension()];
		for (int i = 0; i < FillZoneProblem.getBoardDimension(); i++) {
			for (int j = 0; j < FillZoneProblem.getBoardDimension(); j++) {
				newBoard[i][j] = board[i][j];
			}
		}
		FillZoneState newState = new FillZoneState(newBoard);
		newState.movesLeft = movesLeft;
		return newState;
	}

	public void changeColor(int color) {
		if (color == board[0][0]) {
			return;
		}
		changeColor(color, 0, 0, board[0][0]);
		movesLeft--;
	}

	public void changeColor(int newColor, int x, int y, int oldColor) {
		if (x < 0 || y < 0 || x >= FillZoneProblem.getBoardDimension()
				|| y >= FillZoneProblem.getBoardDimension()
				|| board[x][y] != oldColor) {
			return;
		}
		board[x][y] = newColor;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (Math.abs(i) != Math.abs(j)) {
					changeColor(newColor, x + i, y + j, oldColor);
				}
			}
		}
		return;
	}

	public boolean hasLost() {
		return movesLeft == 0 && !isFinished();
	}

	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();
		for (int i = 0; i < FillZoneProblem.getBoardDimension(); i++) {
			for (int j = 0; j < FillZoneProblem.getBoardDimension(); j++) {
				strBuilder.append(board[i][j]);
			}
			strBuilder.append("\n");
		}
//		strBuilder.append("Dominated: " + getDominatedCells() + "\n");
//		strBuilder.append("Islands: " + getIslands() + "\n");
//		strBuilder.append("Heuristic Value: " + FillZoneProblem.kNeighboursHeuristic(this) + "\n");
//		strBuilder.append("Value choto: " + ((int)Math.pow(FillZoneProblem.getBoardDimension(), 2) - getDominatedCells()) + "\n");
//		strBuilder.append("Corner value: " + getCornerValue() + "\n");
		return strBuilder.toString();
	}

	public boolean isFinished() {
		int color = board[0][0];
		for (int i = 0; i < FillZoneProblem.getBoardDimension(); i++) {
			for (int j = 0; j < FillZoneProblem.getBoardDimension(); j++) {
				if (board[i][j] != color) {
					return false;
				}
			}
		}
		return true;
	}

	public int getDominatedCells() {
		boolean[][] usedCells = new boolean[FillZoneProblem.getBoardDimension()][FillZoneProblem
				.getBoardDimension()];
		int[] counter = new int[1];
		counter[0] = 0;
		getDominatedCells(0, 0, usedCells, counter, board[0][0]);
		return counter[0];
	}

	private void getDominatedCells(int x, int y, boolean[][] usedCells,
			int[] counter, int color) {
		if (x < 0 || y < 0 || x >= FillZoneProblem.getBoardDimension()
				|| y >= FillZoneProblem.getBoardDimension() || usedCells[x][y]
				|| board[x][y] != color) {
			return;
		}
		usedCells[x][y] = true;
		if (counter != null) {
			counter[0]++;
		}
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (Math.abs(i) != Math.abs(j)) {
					getDominatedCells(x + i, y + j, usedCells, counter, color);
				}
			}
		}
		return;
	}

	public int getIslands() {
		boolean[][] usedCells = new boolean[FillZoneProblem.getBoardDimension()][FillZoneProblem
				.getBoardDimension()];
		int[] counter = new int[1];
		counter[0] = 0;
		int islands = 0;
		for (int i = 0; i < FillZoneProblem.getBoardDimension(); i++) {
			for (int j = 0; j < FillZoneProblem.getBoardDimension(); j++) {
				if (!usedCells[i][j]) {
					getDominatedCells(i, j, usedCells, counter, board[i][j]);
					islands++;
				}
			}
		}
		return islands;
	}

	public Graph<Island> getIslandGraph() {
		Island[][] usedCells = new Island[FillZoneProblem.getBoardDimension()][FillZoneProblem
				.getBoardDimension()];
		Queue<QueueNode> queue = new LinkedList<QueueNode>();
		Graph<Island> graph = new Graph<Island>();
		queue.offer(new QueueNode(null, 0));
		while (!queue.isEmpty()) {
			QueueNode node = queue.poll();
			Island neighbourIsland = node.island;
			int index = node.index;
			int x = index / FillZoneProblem.getBoardDimension();
			int y = index % FillZoneProblem.getBoardDimension();
			if (usedCells[x][y] == null) {
				Island island = new Island(board[x][y]);
				graph.putNode(island);
				getIslandsGraph(x, y, usedCells, board[x][y], queue, island);
			} 
			if (neighbourIsland != null) {
				graph.setNeighbour(usedCells[x][y], neighbourIsland);
			}
		}
		return graph;
	}

	private void getIslandsGraph(int x, int y, Island[][] usedCells,
			int color, Queue<QueueNode> queue, Island island) {
		if (x < 0 || y < 0 || x >= FillZoneProblem.getBoardDimension()
				|| y >= FillZoneProblem.getBoardDimension() || usedCells[x][y] != null) {
			return;
		}
		if (board[x][y] != color) {
			queue.offer(new QueueNode(island, x
					* FillZoneProblem.getBoardDimension() + y));
			return;
		}
		usedCells[x][y] = island;
		island.addPoint(x, y);
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (Math.abs(i) != Math.abs(j)) {
					getIslandsGraph(x + i, y + j, usedCells, color, queue,
							island);
				}
			}
		}
		return;
	}

	private class QueueNode {
		Island island;
		int index;

		QueueNode(Island island, int index) {
			this.island = island;
			this.index = index;
		}
	}

	public class Island {
		int color;
		Set<Integer> points = new HashSet<Integer>();
		
		public Island(int color) {
			this.color = color;
		}

		
		
		void addPoint(int x, int y) {
			points.add(x * FillZoneProblem.getBoardDimension() + y);
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Island)) {
				return false;
			}
			return points.equals(((Island) obj).points);
		}

		@Override
		public int hashCode() {
			return points.hashCode();
		}
		
		@Override
		public String toString() {
			Integer i = -1;
			for(Integer num : points){
				i = num;
				break;
			}
			return "" + color + "("+(i/FillZoneProblem.getBoardDimension())+","+(i%FillZoneProblem.getBoardDimension())+")";
		}
	}

	public int getDominatedColor() {
		return board[0][0];
	}

	public int getCornerValue(){
		boolean[][] usedCells = new boolean[FillZoneProblem.getBoardDimension()][FillZoneProblem.getBoardDimension()];
		int[] counter = new int[1];
		counter[0] = 0;
		getDominatedCells(0, 0, usedCells, counter, board[0][0]);
		int dDistance = 0;
		boolean flag = false;
		for(int i = 0 ; i < FillZoneProblem.getBoardDimension() && !flag; i++){
			for(int j = 0, k = i; j <= i && !flag ; j++, k--){
				if( ! usedCells[FillZoneProblem.getBoardDimension() - j - 1] [ FillZoneProblem.getBoardDimension() - k - 1]){
					dDistance++;
				}else{
					flag = true;
				}	
			}
		}
		flag = false;
		dDistance*=2;
		for(int i = 0 ; i < FillZoneProblem.getBoardDimension() && !flag; i++){
			for(int j = 0, k = i; j <= i && !flag ; j++, k--){
				if( ! usedCells[j] [ FillZoneProblem.getBoardDimension() - k - 1]){
					dDistance++;
				}else{
					flag = true;
				}	
			}
		}
		flag = false;
		for(int i = 0 ; i < FillZoneProblem.getBoardDimension() && !flag; i++){
			for(int j = 0, k = i; j <= i && !flag ; j++, k--){
				if( ! usedCells[FillZoneProblem.getBoardDimension() - j - 1] [k]){
					dDistance++;
				}else{
					flag = true;
				}	
			}
		}
		dDistance*=5;
//		dDistance += 196;
//		if (dDistance  == 196){
		dDistance += ((int)Math.pow(FillZoneProblem.getBoardDimension(), 2) - counter[0]);
//		}
		return dDistance;
	}
	
//	private void firstBoard(){
//		int array[][] = {{1,1,5,5,2,5,3,5,1,3,5,4,4,0},
//		{3,5,0,0,1,4,4,1,1,0,0,0,0,2},
//		{3,0,5,1,5,4,1,5,5,5,5,5,3,2},
//		{5,2,0,3,0,1,1,5,0,4,0,5,2,4},
//		{5,4,0,4,2,0,2,1,5,0,2,0,5,5},
//		{2,3,0,4,3,1,0,5,1,0,5,4,3,3},
//		{2,5,0,0,3,2,4,3,3,2,2,5,2,0},
//		{4,4,1,2,3,2,2,2,3,3,4,5,0,3},
//		{0,5,0,1,3,4,4,4,0,1,0,3,0,1},
//		{1,4,1,4,0,5,4,1,0,2,2,3,1,1},
//		{5,0,2,5,3,4,5,4,4,1,0,1,3,3},
//		{3,4,5,0,4,4,0,3,2,3,1,2,0,5},
//		{4,1,1,4,0,3,3,5,5,0,2,1,0,4},
//		{5,0,1,2,2,1,3,4,0,4,1,0,5,5}
//		};
//		for (int i = 0; i < FillZoneProblem.getBoardDimension(); i++) {
//			for (int j = 0; j < FillZoneProblem.getBoardDimension(); j++) {
//				 board[i][j] = array[i][j];
//			}
//		}		
//	}
//	
//	private void secondBoard(){
//		int array[][] = {{4,5,4,1,1,5,1,1,1,0,2,1,1,0},
//				{0,2,4,0,4,0,2,0,1,2,5,1,3,3},
//				{4,1,3,0,2,3,2,2,3,0,5,2,5,1},
//				{0,2,0,5,1,4,1,1,2,0,1,3,0,5},
//				{0,4,2,5,4,5,1,4,5,2,3,2,0,2},
//				{5,1,5,1,5,3,0,5,0,4,2,1,0,1},
//				{3,1,4,4,4,2,0,3,0,4,3,3,1,4},
//				{0,5,0,3,1,2,2,4,5,0,1,4,2,2},
//				{5,4,1,5,2,3,3,4,4,5,2,5,5,0},
//				{2,0,2,3,4,1,2,0,3,3,3,1,5,2},
//				{3,4,4,2,4,0,2,2,1,5,5,0,2,0},
//				{5,0,0,1,1,1,1,3,0,3,3,4,3,4},
//				{4,1,2,5,5,3,0,0,2,4,1,0,1,1},
//				{0,3,0,1,4,0,2,3,1,0,0,4,3,3}};
//		for (int i = 0; i < FillZoneProblem.getBoardDimension(); i++) {
//			for (int j = 0; j < FillZoneProblem.getBoardDimension(); j++) {
//				 board[i][j] = array[i][j];
//			}
//		}		
//	}
//	
//	private void thirdBoard(){
//		int array[][] =	{{4,0,0,3,3,1,1,2,2,3,5,5,2,1},
//		{4,5,3,0,4,0,4,2,1,2,3,3,5,4},
//		{3,3,5,4,5,3,2,2,1,0,0,2,0,5},
//		{1,2,3,2,3,4,0,0,2,5,4,4,4,1},
//		{2,2,3,2,4,4,4,5,1,1,2,3,3,4},
//		{4,0,0,0,4,0,1,5,2,5,2,4,0,3},
//		{2,2,5,1,3,3,0,1,0,0,4,2,4,4},
//		{4,1,5,5,5,3,3,5,3,3,1,2,5,5},
//		{1,0,4,0,0,3,5,0,3,3,2,0,2,4},
//		{3,0,4,4,5,1,5,0,5,2,2,2,2,3},
//		{3,0,0,4,1,1,5,3,2,0,2,2,4,5},
//		{0,2,4,4,2,0,1,5,5,4,3,2,4,0},
//		{2,2,5,5,0,1,2,4,4,0,4,2,2,5},
//		{5,5,1,5,3,3,5,1,5,5,4,0,2,1}};
//		for (int i = 0; i < FillZoneProblem.getBoardDimension(); i++) {
//			for (int j = 0; j < FillZoneProblem.getBoardDimension(); j++) {
//				 board[i][j] = array[i][j];
//			}
//		}		
//	}
//	
//	private void fourthBoard(){
//		int array[][] =	{{3,4,5,5,5,0,3,5,1,3,4,4,2,2},
//				{2,2,0,0,1,2,5,2,4,2,4,0,0,0},
//				{4,4,1,5,0,5,3,2,3,0,3,0,4,4},
//				{3,1,1,3,4,5,2,1,0,5,4,1,5,3},
//				{4,0,3,4,3,5,5,5,3,4,0,0,0,3},
//				{2,4,2,3,5,4,4,1,0,3,1,1,0,4},
//				{3,5,1,2,4,5,2,5,1,4,2,1,2,5},
//				{5,4,3,3,3,0,2,3,0,0,5,4,2,0},
//				{0,0,2,4,0,5,0,3,1,4,0,3,2,5},
//				{2,3,0,0,3,0,4,0,0,4,4,0,4,3},
//				{0,3,1,5,0,5,2,2,4,2,3,3,2,2},
//				{1,5,1,1,1,4,4,3,4,1,4,1,4,1},
//				{0,1,4,2,4,0,0,5,3,3,0,5,1,5},
//				{2,5,4,5,0,3,0,2,0,2,1,4,0,0}};
//		for (int i = 0; i < FillZoneProblem.getBoardDimension(); i++) {
//			for (int j = 0; j < FillZoneProblem.getBoardDimension(); j++) {
//				 board[i][j] = array[i][j];
//			}
//		}		
//	}
//	
//	private void fifthBoard(){
//		int array[][] =	{{1,1,0,5,5,1,3,0,2,0,5,3,2,0},
//				{5,1,5,5,2,1,4,5,1,3,3,3,0,5},
//				{0,1,3,3,3,0,5,0,3,1,2,1,1,0},
//				{4,4,0,1,4,5,1,1,0,0,4,3,2,1},
//				{1,5,3,4,1,0,2,5,0,2,3,5,4,3},
//				{5,2,5,4,4,0,4,5,3,0,1,3,3,4},
//				{5,1,4,5,3,2,0,5,3,2,3,0,4,0},
//				{1,0,0,3,1,2,0,2,3,5,1,0,2,4},
//				{5,2,4,4,4,0,3,1,2,5,4,1,2,0},
//				{1,0,2,1,5,4,5,3,0,3,3,0,5,4},
//				{5,3,4,5,0,1,1,1,4,2,1,0,5,1},
//				{5,1,0,1,3,1,1,3,5,1,2,3,0,3},
//				{4,0,0,2,4,1,0,4,5,3,4,2,1,4},
//				{0,2,1,1,3,1,5,1,3,4,0,0,5,5}};
//		for (int i = 0; i < FillZoneProblem.getBoardDimension(); i++) {
//			for (int j = 0; j < FillZoneProblem.getBoardDimension(); j++) {
//				 board[i][j] = array[i][j];
//			}
//		}		
//	}
}
