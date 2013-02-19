package com.example.note.component;

import java.util.LinkedList;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class MultiStrokes {
	
	public LinkedList<Stroke> chunk;
	
	public MultiStrokes() {
		// TODO Auto-generated constructor stub

		chunk = new LinkedList<Stroke>();
	}


	public MultiStrokes(MultiStrokes chunk2) {
		// TODO Auto-generated constructor stub

		chunk = new LinkedList<Stroke>();
		for (Stroke stroke: chunk2.chunk){
			chunk.add(new Stroke(stroke));
		}
	}
	
	public void copyChunk(MultiStrokes chunk2){

		chunk = new LinkedList<Stroke>();
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
	
	public void draw (Canvas c, Paint paint) {
		// TODO Auto-generated method stub
		for(Stroke stroke: chunk){
			if(stroke.stroke.size()!=0){
				stroke.draw(c, paint);
			}
		}
	}


	public void transform(int maxX, int maxY, int minX, int minY, int height) {
		// TODO Auto-generated method stub
		for (Stroke stroke:chunk){
			for (Point point:stroke.stroke){
				point.set(point.x-minX, point.y=minY);
			}
		}
		
	}


	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return chunk.isEmpty();
	}

	
}
