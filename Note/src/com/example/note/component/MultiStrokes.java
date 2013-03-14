package com.example.note.component;

import java.util.LinkedList;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

public class MultiStrokes {
	
	public LinkedList<Path> chunk;
	
	public MultiStrokes() {
		// TODO Auto-generated constructor stub

		chunk = new LinkedList<Path>();
		chunk.add(new Path());
	}


	public MultiStrokes(MultiStrokes chunk2) {
		// TODO Auto-generated constructor stub

		chunk = new LinkedList<Path>();
		
		for (Path stroke: chunk2.chunk){
			chunk.add(new Path(stroke));
		}
	}
	
	public void copyChunk(MultiStrokes chunk2){

		chunk = new LinkedList<Path>();
		
		for (Path stroke: chunk2.chunk){
			chunk.add(new Path(stroke));
		}
	}


	public void addStroke(Point point) {
		// TODO Auto-generated method stub
		//add a copy of current path to the 2nd of the queue
		chunk.add(1, (new Path(chunk.peek())));
		chunk.peek().moveTo(point.x, point.y);
	}

	public void addStroke() {
		// TODO Auto-generated method stub
		chunk.add(1, (new Path(chunk.peek())));
	}

	/**
	 * add a new point to the newest stroke
	 * @param point
	 */
	public void addPoint(Point point) {
		// TODO Auto-generated method stub
		chunk.peek().lineTo(point.x, point.y);
	}


	public void clear() {
		// TODO Auto-generated method stub
		chunk.clear();
		chunk.add(new Path());
	}
	
	public void draw (Canvas c, Paint paint) {
		// TODO Auto-generated method stub
//		for(Path stroke: chunk){
//			if(!stroke.isEmpty()){
//				c.drawPath(stroke, paint);
//			}
//		}
		if (!chunk.isEmpty()){
			if (!chunk.peek().isEmpty()){
				c.drawPath(chunk.peek(), paint);
				System.out.println(chunk.peek().toString());
			}
		}
	}


//	public void transform(int maxX, int maxY, int minX, int minY, int height) {
//		// TODO Auto-generated method stub
//		for (Stroke stroke:chunk){
//			for (Point point:stroke.stroke){
//				point.set(point.x-minX, point.y=minY);
//			}
//		}
//		
//	}


	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return chunk.isEmpty();
	}

	
}
