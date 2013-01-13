package com.example.note.component;

import java.util.ArrayList;

import android.graphics.Point;

public class MultiStrokes {
	
	public ArrayList<Stroke> chunk;
	
	public MultiStrokes() {
		// TODO Auto-generated constructor stub

		chunk = new ArrayList<Stroke>();
	}


	public MultiStrokes(MultiStrokes chunk2) {
		// TODO Auto-generated constructor stub

		chunk = new ArrayList<Stroke>();
		for (Stroke stroke: chunk2.chunk){
			chunk.add(new Stroke(stroke));
		}
	}
	
	public void copyChunk(MultiStrokes chunk2){

		chunk = new ArrayList<Stroke>();
		for (Stroke stroke: chunk2.chunk){
			chunk.add(new Stroke(stroke));
		}
	}


	public void addStroke(Point point) {
		// TODO Auto-generated method stub
		chunk.add(new Stroke());
		chunk.get(chunk.size()-1).addPoint(point);
	}

	public void addStroke() {
		// TODO Auto-generated method stub
		chunk.add(new Stroke());
	}

	/**
	 * add a new point to the newest stroke
	 * @param point
	 */
	public void addPoint(Point point) {
		// TODO Auto-generated method stub
		chunk.get(chunk.size()-1).addPoint(point);
	}


	public void clear() {
		// TODO Auto-generated method stub
		chunk.clear();
	}
}
