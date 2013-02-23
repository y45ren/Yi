package com.example.note.view;

import java.util.LinkedList;

import com.example.note.component.MultiStrokes;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class InkRegion extends LinearLayout{
	private int UID;
	private int lineWidth;
	private int lineHeight;
	private int line;
	private Point startPoint;
	private float angle;
	public RelativeLayout.LayoutParams params;
	public LinkedList<ChunkLine> chunkLine;
	public Point lastPosition;
	
	public InkRegion(Context context, int h, Point sP, float angle) {
		super(context);
		this.setOrientation(VERTICAL);
		startPoint = new Point((int)(sP.x-2*h*Math.sin(Math.toRadians(-angle))),(int)(sP.y-2*h*Math.cos(Math.toRadians(-angle))));
//		startPoint = new Point(sP.x,sP.y);
		this.setAngle(angle);
		setLineWidth(0);
		setLineHeight(2*h);
		line = 0;
		chunkLine = new LinkedList<ChunkLine>();
		addInitialLine();
		lastPosition = new Point();
		params = new RelativeLayout.LayoutParams(0, 2*h);
//		params = new RelativeLayout.LayoutParams(500, 500);
		params.leftMargin = startPoint.x;
        params.topMargin = startPoint.y;
        this.setPivotX(0);
        this.setPivotY(0);
        this.setRotation(angle);
//		this.setBackgroundColor(Color.RED);
	}

//	
//	public boolean onTouchEvent(MotionEvent event){
//		Point eventPoint = new Point((int)event.getX(),(int)event.getY());
//		System.out.println("InkRegion  "+this.UID+" : "+eventPoint);
//		return false;
//		
//	}
	
	public void addChunk(MultiStrokes newChunk, Point pivotPoint, Point endPoint, double scale, int width) {
		setLineWidth(getLineWidth() + width);
		if (params.width < getLineWidth()){
			this.params.width += width;
		}
		if (chunkLine.size()==0){
			LayoutParams params = new LinearLayout.LayoutParams(
					0, getLineHeight());

			chunkLine.add(new ChunkLine(getContext(), params, line));
			line++;
			this.addView(chunkLine.get(0), chunkLine.get(0).params);
		}
		System.out.println("in Region, chunk is: " +chunkLine.size());
		chunkLine.peekLast().addChunk(newChunk, pivotPoint, endPoint, scale, width, this.getLineHeight());
	}
	



	public void addLine() {
		// TODO Auto-generated method stub
		LayoutParams params = new LinearLayout.LayoutParams(
				0, getLineHeight());

		chunkLine.add(new ChunkLine(getContext(), params, line));
		line++;
		this.params.height += getLineHeight();
		this.addView(chunkLine.peekLast(), chunkLine.peekLast().params);
		setLineWidth(0);
	}
	public void addInitialLine() {
		// TODO Auto-generated method stub
		LayoutParams params = new LinearLayout.LayoutParams(
				0, getLineHeight());

		chunkLine.add(new ChunkLine(getContext(), params, line));
		line++;
		this.addView(chunkLine.peekLast(), chunkLine.peekLast().params);
		setLineWidth(0);
	}

	public Point generateLastPosition(){
		System.out.println("chunkLine has: "+this.chunkLine.peekLast().chunkFrame.size());
		System.out.println("in undo: "+this.chunkLine.peekLast().getRight()+", "+ this.chunkLine.peekLast().getBottom());
		Point temp = new Point(this.chunkLine.peekLast().params.width, this.chunkLine.size()*this.lineHeight);
		double sin = Math.sin(Math.toRadians(angle));
		double cos = Math.cos(Math.toRadians(angle));
		lastPosition.set(startPoint.x+(int)(temp.x*cos-temp.y*sin), startPoint.y+(int)(temp.x*sin+temp.y*cos));
		return this.lastPosition;
	}
	
	public Point generatePosition(int line, int columns){
		//MARK!
		Point temp = new Point(this.chunkLine.get(line).params.leftMargin, (line+1)*this.lineHeight);
		double sin = Math.sin(Math.toRadians(angle));
		double cos = Math.cos(Math.toRadians(angle));
		lastPosition.set(startPoint.x+(int)(temp.x*cos-temp.y*sin), startPoint.y+(int)(temp.x*sin+temp.y*cos));
		return this.lastPosition;
	}
	/**
	 * @return the lineWidth
	 */
	public int getLineWidth() {
		return lineWidth;
	}


	/**
	 * @param lineWidth the lineWidth to set
	 */
	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}


	/**
	 * @return the lineHeight
	 */
	public int getLineHeight() {
		return lineHeight;
	}


	/**
	 * @param lineHeight the lineHeight to set
	 */
	public void setLineHeight(int lineHeight) {
		this.lineHeight = lineHeight;
	}


	public void undo() {
		// TODO Auto-generated method stub
		
		if (chunkLine.size()==1&&chunkLine.peekLast().chunkFrame.size()==0){
			
		}else if (chunkLine.peekLast().chunkFrame.size() == 0){
			this.removeView(this.chunkLine.pollLast());
			line--;
		}else {
			chunkLine.peekLast().undo();
		}
	}


	/**
	 * @return the angle
	 */
	public float getAngle() {
		return angle;
	}


	/**
	 * @param angle the angle to set
	 */
	public void setAngle(float angle) {
		this.angle = angle;
	}


	/**
	 * @return the line
	 */
	public int getLine() {
		return line;
	}


	/**
	 * @param line the line to set
	 */
	public void setLine(int line) {
		this.line = line;
	}


	public Point getStartPoint() {
		// TODO Auto-generated method stub
		return this.startPoint;
	}


	
}
