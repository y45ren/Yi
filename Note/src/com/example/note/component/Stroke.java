package com.example.note.component;

import java.util.LinkedList;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.*;
import android.graphics.*;



public class Stroke {
	public LinkedList<Point> stroke;
	
	public Stroke(){
		stroke = new LinkedList<Point>();
	}

	public Stroke(Stroke stroke2) {
		// TODO Auto-generated constructor stub
		stroke = new LinkedList<Point>();
		for (Point point:stroke2.stroke){
			stroke.add(new Point(point.x,point.y));
		}
		//stroke = (ArrayList<Point>) stroke2.stroke.clone();
	}

	public int size() {
		// TODO Auto-generated method stub
		return stroke.size();
	}

	public Point get(int i) {
		// TODO Auto-generated method stub
		return stroke.get(i);
	}

	public void addPoint(Point point) {
		// TODO Auto-generated method stub
		stroke.add(point);
	}

	public void draw(Canvas c, Paint paint) {
//		GoogleMap map = new GoogleMap();
//		map.addPolyline(new PolylineOptions()
//	     .add(new LatLng(51.5, -0.1), new LatLng(40.7, -74.0))
//	     .width(5)
//	     .color(Color.RED));
//		
//		Polyline lines;
//		
//		// Instantiates a new Polyline object and adds points to define a rectangle
//		PolylineOptions rectOptions = new PolylineOptions()
//		        .add(new LatLng(37.35, -122.0))
//		        .add(new LatLng(37.45, -122.0))  // North of the previous point, but at the same longitude
//		        .add(new LatLng(37.45, -122.2))  // Same latitude, and 30km to the west
//		        .add(new LatLng(37.35, -122.2))  // Same longitude, and 16km to the south
//		        .add(new LatLng(37.35, -122.0)); // Closes the polyline.
//
//		// Set the rectangle's color to red
//		rectOptions.color(Color.RED);
//
//		GoogleMap myMap = null;
//		// Get back the mutable Polyline
//		//Polyline polyline = myMap.addPolyline(rectOptions);
		
		
		if (stroke.size() > 0) {
			Point p0 = stroke.get(0);
			for (int i = 1; i < stroke.size(); i++) {
				Point p1 = stroke.get(i);
				
				c.drawLine(p0.x, p0.y, p1.x, p1.y, paint);
//				paint.setStrokeWidth(paint.getStrokeWidth()-1);
//				c.drawPoint(p0.x, p0.y, paint);
//				
				p0 = p1;
			}
		}
	}
	
	public void drawInLarge(Canvas c, Paint paint){
		Paint dotPaint = MyPaint.createPaint(Color.BLACK, 4);
		if (stroke.size() > 0) {
			Point p0 = stroke.get(0);
			for (int i = 1; i < stroke.size(); i++) {
				Point p1 = stroke.get(i);
				
				c.drawLine(p0.x, p0.y, p1.x, p1.y, paint);
				paint.setStrokeWidth(paint.getStrokeWidth());
//				c.drawPoint(p0.x, p0.y, paint);
				c.drawCircle(p0.x, p0.y, (float) 2.5, dotPaint);
				p0 = p1;
			}
		}
	}
	
	public void print() {
		// TODO Auto-generated method stub
		if (stroke.size()==0){
			System.out.println("Stroke NULL");
		}else{
			System.out.print(stroke.get(stroke.size()-1).x+"  ");
			System.out.println(stroke.get(stroke.size()-1).y);
		}
	}
	
	
}
