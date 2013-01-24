package com.example.note.component;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class InkRegion extends LinearLayout{
	private int UID;
	private int regionWidth;
	private int regionHeight;
	private Point startPoint;
	
	public ArrayList<ChunkLine> chunkLine;
	
	public InkRegion(Context context, int h, Point sP) {
		super(context);
		this.setOrientation(VERTICAL);
		startPoint = new Point(sP.x,sP.y);
		regionHeight = h;
		chunkLine = new ArrayList<ChunkLine>();
		chunkLine.add(new ChunkLine(context));
//		chunkLine.add(new ChunkLine(context));
		LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//		LayoutParams params1 = new LinearLayout.LayoutParams(
//                80,40);
		this.addView(chunkLine.get(0),params);
//		this.addView(chunkLine.get(1),params);
               
	}
	
	public InkRegion(Context context, int UID) {
		super(context);
		this.UID = UID;
		this.setOrientation(VERTICAL);
		chunkLine = new ArrayList<ChunkLine>();
		chunkLine.add(new ChunkLine(context));
		chunkLine.add(new ChunkLine(context));
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

	public void addChunk(MultiStrokes newChunk) {
		chunkLine.get(chunkLine.size()-1).addChunk(newChunk);
		
	}
	
}
