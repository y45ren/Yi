package com.example.note.view;

import com.example.note.component.MultiStrokes;
import com.example.note.component.MyPaint;
import com.example.note.component.Status;
import com.example.note.component.Stroke;
import com.example.note.component.anchor.Anchor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.MotionEvent;


public class MagnifiedView extends CanvasView{

	
	private MultiStrokes rotatedChunk;
	public int minX;
	public int minY;
	public int maxX;
	public int maxY;
	protected Paint notesPaint;
	protected Paint testPaint;
	public double angle;
	private Point pivotPoint;
	/**
	 * attributes
	 */
	private int viewHeight;
	private int viewWidth;
	
	
	public MagnifiedView(Context context) {
		
		super(context);
		notesPaint = MyPaint.createPaint(Color.BLACK, 10);
		testPaint = MyPaint.createPaint(Color.GREEN, 10);
		minX=4000;
		minY=4000;
		maxX=0;
		maxY=0;
		angle = 0;
		pivotPoint = new Point(500,330);
		rotatedChunk = new MultiStrokes();
	}
	
	@Override
	public void onSizeChanged(int w,int h,int ow, int oh){
		viewHeight = h;
		viewWidth = w;
	}
	
	
	@Override
	public void onDraw(Canvas c){
		super.onDraw(c);
		computeSize();
		//System.out.println("MaginfView draw CALLED!!!");
//		c.drawLine(minX, minY, maxX, maxY, notesPaint);
		//draw large strokes
		largeStrokes.draw(c, notesPaint);
//		this.rotatedChunk.draw(c, testPaint);
	}
	
	public void computeSize(){
		for (Stroke stroke:this.rotatedChunk.chunk){
			for (Point point:stroke.stroke){
				
				if (point.x<minX+50){
					minX=point.x-50;
				}
				if (point.y<minY+50){
					minY=point.y-50;
				}
				if (point.x>maxX-50){
					maxX=point.x+50;
				}
				if (point.y>maxY-50){
					maxY=point.y+50;
				}
			}
		}
	}
	public void clear(){
		this.largeStrokes.clear();
		this.rotatedChunk.clear();
		minX=4000;
		minY=4000;
		maxX=0;
		maxY=0;
	}
	
	@Override
    public void undo(){
    	super.undo();
    }

	public void setAngle(float angle) {
		// TODO Auto-generated method stub
		this.angle = angle;
	}
	
	private Point rotatePoint(Point point){
		int x=point.x-pivotPoint.x;
		int y=point.y-pivotPoint.y;
//		float sin = (float) Math.sin(angle+Math.toRadians(90));
//		float cos = (float) Math.cos(angle+Math.toRadians(90));
		float sin = (float) Math.sin(-angle);
		float cos = (float) Math.cos(-angle);
		Point tempPoint = new Point((int)(x*cos-y*sin) + pivotPoint.x, (int)(x*(sin)+y*cos)+pivotPoint.y);
		return tempPoint;		
	}
	
	@Override
	public void setRotation(float angle){
		this.setPivotX(this.pivotPoint.x);
		this.setPivotY(this.pivotPoint.y);
		super.setRotation(angle);
	}
	
	@Override
	public void addStroke(Point eventPoint) {
		// TODO Auto-generated method stub
		this.rotatedChunk.addStroke(this.rotatePoint(eventPoint));
		super.addStroke(eventPoint);
	}


	@Override
	public void addPoint(Point eventPoint) {
		// TODO Auto-generated method stub
		this.rotatedChunk.addPoint(this.rotatePoint(eventPoint));
		super.addPoint(eventPoint);
	}
    

	public MultiStrokes getRotateChunk() {
		// TODO Auto-generated method stub
		return this.rotatedChunk;
	}
}
