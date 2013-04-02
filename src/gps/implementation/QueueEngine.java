package gps.implementation;

import gps.GPSEngine;
import gps.GPSNode;

import java.util.Iterator;
import java.util.PriorityQueue;

public abstract class QueueEngine extends GPSEngine{
	protected PriorityQueue<PQNode> open = new PriorityQueue<PQNode>();
	
	@Override
	public GPSNode getNode() {
		PQNode node;
		if ( (node = open.poll()) == null ) {
			return null;
		}
		return node.getNode();
	}

	@Override
	public Iterator<GPSNode> getNodes() {
		final Iterator<? extends PQNode> it = open.iterator();
		return new Iterator<GPSNode>() {

			@Override
			public boolean hasNext() {
				return it.hasNext();
			}

			@Override
			public GPSNode next() {
				return it.next().getNode();
			}

			@Override
			public void remove() {
				it.remove();
			}
		};
	}

	@Override
	public int getSize() {
		return open.size();
	}
	
}
