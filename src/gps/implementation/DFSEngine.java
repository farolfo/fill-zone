package gps.implementation;

import gps.GPSNode;

public class DFSEngine extends ListEngine {

	@Override
	public void addNode(GPSNode node) {
		open.add(0, node);
	}

}
