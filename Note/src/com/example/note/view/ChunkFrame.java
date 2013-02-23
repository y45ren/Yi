package com.example.note.view;

import java.util.ArrayList;

import com.example.note.component.MultiStrokes;

import android.R.color;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class ChunkFrame extends LinearLayout{
	private ChunkView chunkView;
	public LayoutParams params;
	private int line;
	private int columns;
	
	public ChunkFrame(Context context, MultiStrokes newChunk, LayoutParams childParams, int line, int columns){
		super(context);
		chunkView = new ChunkView(context, newChunk);
		params = new LayoutParams(childParams);
		this.line = line;
		this.columns = columns;
		//this.setBackgroundColor(Color.GRAY);
	}

	public void addChunk(MultiStrokes newChunk, Point pivotPoint,
			Point endPoint, double scale, int width, int regionHeight) {
		// TODO Auto-generated method stub
		
		
		LayoutParams childParams = new LinearLayout.LayoutParams(endPoint.x, endPoint.y);
		childParams.leftMargin = -pivotPoint.x;
//		childParams.bottomMargin = regionHeight-endPoint.y;
		childParams.topMargin = -pivotPoint.y+(regionHeight-(endPoint.y-pivotPoint.y));
		
		chunkView.setPivotX(pivotPoint.x);
        chunkView.setPivotY(endPoint.y);
        chunkView.setScale(scale);
        
        chunkView.setScaleX((float) scale);
        chunkView.setScaleY((float) scale);
        
        this.addView(chunkView,childParams);
	}

	public void undo() {
		// TODO Auto-generated method stub
		this.removeAllViews();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
		
		return false;
	}
}
