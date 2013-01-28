package com.example.note.view;

import java.util.ArrayList;

import com.example.note.component.MultiStrokes;

import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class InkRegion extends LinearLayout{
	private int UID;
	private int regionWidth;
	private int regionHeight;
	private Point startPoint;
	public RelativeLayout.LayoutParams params;
	public ArrayList<ChunkLine> chunkLine;
	
	public InkRegion(Context context, int h, Point sP, float angle) {
		super(context);
		this.setOrientation(VERTICAL);
		startPoint = new Point(sP.x,sP.y);
		regionWidth = 0;
		regionHeight = 2*h;
		chunkLine = new ArrayList<ChunkLine>();
		
		params = new RelativeLayout.LayoutParams(regionWidth, 2*h);
//		params = new RelativeLayout.LayoutParams(500, 500);
		params.leftMargin = sP.x;
        params.topMargin = sP.y-h;
        this.setPivotX(0);
        this.setPivotY(h);
        this.setRotation(angle);
               
	}
	
	public InkRegion(Context context, int UID) {
		super(context);
		this.UID = UID;
		this.setOrientation(VERTICAL);
		chunkLine = new ArrayList<ChunkLine>();
		LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		LayoutParams params1 = new LinearLayout.LayoutParams(
                80,40);
		this.addView(chunkLine.get(0),params);
		this.addView(chunkLine.get(1),params);
               
	}


	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		//this.regionHeight = this.getWidth();
		//this.regionWidth = this.getHeight();
	}
	
	public boolean onTouchEvent(MotionEvent event){
		Point eventPoint = new Point((int)event.getX(),(int)event.getY());
		System.out.println("InkRegion  "+this.UID+" : "+eventPoint);
		return false;
		
	}
	
	public void addChunk(MultiStrokes newChunk, Point pivotPoint, Point endPoint, double scale, int width) {
		this.params.width += width;
		if (chunkLine.size()==0){
			LayoutParams params = new LinearLayout.LayoutParams(
					0, regionHeight);

			chunkLine.add(new ChunkLine(getContext(), params));
			this.addView(chunkLine.get(0), chunkLine.get(0).params);
//			LayoutParams params = new LinearLayout.LayoutParams(
//					500, 500);
//
//			chunkLine.add(new ChunkLine(getContext(), params));
//			this.addView(chunkLine.get(0), params);
		}
		
		chunkLine.get(chunkLine.size()-1).addChunk(newChunk, pivotPoint, endPoint, scale, width, this.regionHeight);
		
	}
	/**
	 * @return the regionHeight
	 */
	public int getRegionHeight() {
		return regionHeight;
	}

	/**
	 * @param regionHeight the regionHeight to set
	 */
	public void setRegionHeight(int regionHeight) {
		this.regionHeight = regionHeight;
	}

	/**
	 * @return the regionWidth
	 */
	public int getRegionWidth() {
		return regionWidth;
	}

	/**
	 * @param regionWidth the regionWidth to set
	 */
	public void setRegionWidth(int regionWidth) {
		this.regionWidth = regionWidth;
	}

	public void addLine() {
		// TODO Auto-generated method stub
		LayoutParams params = new LinearLayout.LayoutParams(
				0, regionHeight);

		chunkLine.add(new ChunkLine(getContext(), params));
		this.addView(chunkLine.get(0), chunkLine.get(0).params);
	}


	
}
