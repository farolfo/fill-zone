package gps.implementation;

import gps.GPSNode;

public class BFSEngine extends ListEngine {

	@Override
	public void addNode(GPSNode node) {
		open.add(node);
	}

}
