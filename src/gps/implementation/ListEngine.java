package gps.implementation;

import gps.GPSEngine;
import gps.GPSNode;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class ListEngine extends GPSEngine{

	protected List<GPSNode> open = new LinkedList<GPSNode>();

	@Override
	public GPSNode getNode() {
		try{
			return open.remove(0);
		}catch(IndexOutOfBoundsException e){
			return null;
		}
	}

	@Override
	public Iterator<GPSNode> getNodes() {
		return open.iterator();
	}

	@Override
	public int getSize() {
		return open.size();
	}

}
