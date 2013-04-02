package gps.implementation;

import gps.GPSNode;

public class AStarEngine extends QueueEngine {
	
	@Override
	public void addNode(GPSNode node) {
		open.offer(new PQNode(node, node.getCost() + problem.getHValue(node.getState())));
	}
}
