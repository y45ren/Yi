package com.example.note.view;

import java.util.LinkedList;

import com.example.note.component.MultiStrokes;

import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class InkRegion extends LinearLayout{
	private int UID;
	private int lineWidth;
	private int lineHeight;
	private Point startPoint;
	private float angle;
	public RelativeLayout.LayoutParams params;
	public LinkedList<ChunkLine> chunkLine;
	public Point lastPosition;
	
	public InkRegion(Context context, int h, Point sP, float angle) {
		super(context);
		this.setOrientation(VERTICAL);
		startPoint = new Point(sP.x,sP.y);
		this.setAngle(angle);
		setLineWidth(0);
		setLineHeight(2*h);
		chunkLine = new LinkedList<ChunkLine>();
		addInitialLine();
		lastPosition = new Point();
		params = new RelativeLayout.LayoutParams(0, 2*h);
//		params = new RelativeLayout.LayoutParams(500, 500);
		params.leftMargin = sP.x;
        params.topMargin = sP.y-h;
        this.setPivotX(0);
        this.setPivotY(h);
        this.setRotation(angle);
	}

	
	public boolean onTouchEvent(MotionEvent event){
		Point eventPoint = new Point((int)event.getX(),(int)event.getY());
		System.out.println("InkRegion  "+this.UID+" : "+eventPoint);
		return false;
		
	}
	
	public void addChunk(MultiStrokes newChunk, Point pivotPoint, Point endPoint, double scale, int width) {
		setLineWidth(getLineWidth() + width);
		if (params.width < getLineWidth()){
			this.params.width += width;
		}
		if (chunkLine.size()==0){
			LayoutParams params = new LinearLayout.LayoutParams(
					0, getLineHeight());

			chunkLine.add(new ChunkLine(getContext(), params));
			this.addView(chunkLine.get(0), chunkLine.get(0).params);
		}
		System.out.println("in Region, chunk is: " +chunkLine.size());
		chunkLine.get(chunkLine.size()-1).addChunk(newChunk, pivotPoint, endPoint, scale, width, this.getLineHeight());
	}
	



	public void addLine() {
		// TODO Auto-generated method stub
		LayoutParams params = new LinearLayout.LayoutParams(
				0, getLineHeight());

		chunkLine.add(new ChunkLine(getContext(), params));
		this.params.height += getLineHeight();
		this.addView(chunkLine.get(chunkLine.size()-1), chunkLine.get(chunkLine.size()-1).params);
		setLineWidth(0);
	}
	public void addInitialLine() {
		// TODO Auto-generated method stub
		LayoutParams params = new LinearLayout.LayoutParams(
				0, getLineHeight());

		chunkLine.add(new ChunkLine(getContext(), params));
		this.addView(chunkLine.get(chunkLine.size()-1), chunkLine.get(chunkLine.size()-1).params);
		setLineWidth(0);
	}

	public Point generateLastPosition(){
		Point temp = new Point(this.chunkLine.peekLast().getRight(), this.chunkLine.peekLast().getBottom());
		double sin = Math.sin(Math.toRadians(angle));
		double cos = Math.cos(Math.toRadians(angle));
		System.out.println(sin+", "+cos);
		//System.out.println(Math.sin(Math.toRadians(angle))+", "+cos);
//		Point temp = new Point(400,0);
		lastPosition.set(startPoint.x+(int)(temp.x*cos-temp.y*sin), startPoint.y+(int)(temp.x*sin+temp.y*cos));
//		lastPosition.set((int)(temp.x*cos-temp.y*sin), (int)(temp.x*sin+temp.y*cos));
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
		System.out.println(this.chunkLine.size()+" "+this.chunkLine.peekLast().chunkFrame.size());
		if (chunkLine.size()==1&&chunkLine.peekLast().chunkFrame.size()==0){
			
		}else if (chunkLine.peekLast().chunkFrame.size() == 0){
			this.removeView(this.chunkLine.pollLast());
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


	
}
