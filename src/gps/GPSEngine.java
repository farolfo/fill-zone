package gps;

import gps.api.GPSProblem;
import gps.api.GPSRule;
import gps.api.GPSState;
import gps.exception.NotAppliableException;

import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class GPSEngine {

	private List<GPSNode> closed = new ArrayList<GPSNode>();

	protected GPSProblem problem;

	int maxHeight = Integer.MAX_VALUE;

	public GPSEngine() {

	}

	public FillZoneSolution engine(GPSProblem myProblem) {
		return engine(myProblem, maxHeight);
	}

	public FillZoneSolution engine(GPSProblem myProblem, int maxHeight) {

		this.maxHeight = maxHeight;
		long startTime = System.currentTimeMillis();
		problem = myProblem;

		GPSNode rootNode = new GPSNode(problem.getInitState(), 0, 0);
		boolean finished = false;
		boolean failed = false;
		long explosionCounter = 0;

		FillZoneSolution solution = null;

		addNode(rootNode);
		GPSNode lastNode = null;
		while (!failed && !finished) {
			GPSNode currentNode = getNode();
			if (currentNode == null) {
				failed = true;
			} else {
				lastNode = currentNode;
				closed.add(currentNode);
				if (isGoal(currentNode)) {
					finished = true;
//					solution = new FillZoneSolution(currentNode.getSolution(),
//							currentNode.getHeight(), explosionCounter,
//							getSize(), explosionCounter + getSize(), true);
				} else {
					explosionCounter++;
					explode(currentNode);
				}
			}
		}
		solution = new FillZoneSolution(
				(lastNode != null) ? lastNode.getSolution() : "",
				(lastNode != null) ? lastNode.getHeight() : 0,
				explosionCounter, getSize(), explosionCounter + getSize(),
				finished);

		Toolkit.getDefaultToolkit().beep();

		if (finished) {
			solution.setTime(System.currentTimeMillis() - startTime);
		}
		return solution;
	}

	private boolean isGoal(GPSNode currentNode) {
		if (currentNode.getState() == null) {
			return false;
		}
		for (GPSState state : problem.getGoalStates()) {
			if (state.compare(currentNode.getState())) {
				return true;
			}
		}
		return false;
	}

	private boolean explode(GPSNode node) {
		if (problem.getRules() == null) {
			System.err.println("No rules!");
			return false;
		}
		if (node.getHeight() >= maxHeight) {
			return true;
		}
		for (GPSRule rule : problem.getRules()) {

			GPSState newState = null;
			try {
				newState = rule.evalRule(node.getState());
			} catch (NotAppliableException e) {
				// Do nothing
			}
			if (newState != null
					&& !checkBranch(node, newState)
					&& !checkOpenAndClosed(node.getCost() + rule.getCost(),
							newState)) {
				GPSNode newNode = new GPSNode(newState, node.getCost()
						+ rule.getCost(), node.getHeight() + 1);
				newNode.setParent(node);
				addNode(newNode);
			}
		}
		return true;
	}

	private boolean checkOpenAndClosed(Integer cost, GPSState state) {
		Iterator<? extends GPSNode> it = getNodes();
		while (it.hasNext()) {
			GPSNode openNode = it.next();
			if (openNode.getState().compare(state) && openNode.getCost() < cost) {
				return true;
			}
		}
		for (GPSNode closedNode : closed) {
			if (closedNode.getState().compare(state)
					&& closedNode.getCost() < cost) {
				return true;
			}
		}
		return false;
	}

	private boolean checkBranch(GPSNode parent, GPSState state) {
		if (parent == null) {
			return false;
		}
		return checkBranch(parent.getParent(), state)
				|| state.compare(parent.getState());
	}

	public abstract void addNode(GPSNode node);

	public abstract GPSNode getNode();

	public abstract Iterator<GPSNode> getNodes();

	public abstract int getSize();

}
