package gps;

public class FillZoneSolution {

	private String solution;
	private int height;
	private long exploded;
	private int frontier;
	private long total;
	
	public String getSolution() {
		return solution;
	}

	public int getHeight() {
		return height;
	}

	public long getExploded() {
		return exploded;
	}

	public int getFrontier() {
		return frontier;
	}

	public long getTotal() {
		return total;
	}

	public long getTime() {
		return time;
	}

	private long time;
	private boolean finished;

	public FillZoneSolution(String solution, Integer height, long exploded,
			int frontier, long total, boolean finished) {
		this.solution = solution;
		this.exploded = exploded;
		this.height = height;
		this.frontier = frontier;
		this.total = total;
		this.finished = finished;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	public boolean isFinished() {
		return finished;
	}

	@Override
	public String toString() {
		return solution + "\nHeight: " + height + "\nExpanded nodes: "
				+ exploded + "\nFrontier nodes: " + frontier
				+ "\nNodes created: " + total + "\nTotal time: " + time;
	}

	public void add(FillZoneSolution otherSolution) {
		solution = otherSolution.solution;
		height = otherSolution.height;
		exploded += otherSolution.exploded;
		frontier = otherSolution.frontier;
		total += otherSolution.total;
		time += otherSolution.time;
		finished = finished || otherSolution.finished;
		
	}

}
