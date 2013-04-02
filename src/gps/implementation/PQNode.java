package gps.implementation;

import gps.GPSNode;

public class PQNode implements Comparable<PQNode>{

	private GPSNode node;
	private Integer value;

	public PQNode(GPSNode node, Integer value) {
		this.node = node;
		this.value = value;
	}
	
	@Override
	public int compareTo(PQNode o) {
		return value.compareTo(o.value);
	}
	
	public GPSNode getNode() {
		return node;
	}
	
}
