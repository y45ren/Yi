package com.example.note.component;

import java.util.ArrayList;

import android.graphics.*;

public class Stroke {
	public ArrayList<Point> stroke;
	
	public Stroke(){
		stroke = new ArrayList<Point>();
	}

	public Stroke(Stroke stroke2) {
		// TODO Auto-generated constructor stub
		stroke = new ArrayList<Point>();
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
		// TODO Auto-generated method stub
		if (stroke.size() > 0) {
			Point p0 = stroke.get(0);
			for (int i = 1; i < stroke.size(); i++) {
				Point p1 = stroke.get(i);
				
				c.drawLine(p0.x, p0.y, p1.x, p1.y, paint);
				
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
