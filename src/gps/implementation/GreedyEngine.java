package gps.implementation;

import gps.GPSNode;

public class GreedyEngine extends QueueEngine {
	
	@Override
	public void addNode(GPSNode node) {
		open.offer(new PQNode(node, problem.getHValue(node.getState())));
	}

}