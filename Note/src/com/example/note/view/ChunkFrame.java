package com.example.note.view;

import java.util.ArrayList;

import com.example.note.component.MultiStrokes;

import android.R.color;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class ChunkFrame extends LinearLayout{
	private ChunkView chunkView;

	
	public ChunkFrame(Context context, MultiStrokes newChunk){
		super(context);
		chunkView = new ChunkView(context, newChunk);
		//this.setBackgroundColor(Color.GRAY);
	}

	public void addChunk(MultiStrokes newChunk, Point pivotPoint,
			Point endPoint, double scale, int width, int regionHeight) {
		// TODO Auto-generated method stub
		
		
		LayoutParams childParams = new LinearLayout.LayoutParams(endPoint.x, endPoint.y);
		childParams.leftMargin = -pivotPoint.x;
		childParams.topMargin = -pivotPoint.y;
		
		chunkView.setPivotX(pivotPoint.x);
        chunkView.setPivotY(pivotPoint.y);
        chunkView.setScale(scale);
        
        chunkView.setScaleX((float) scale);
        chunkView.setScaleY((float) scale);
        
        this.addView(chunkView,childParams);
	}

	public void undo() {
		// TODO Auto-generated method stub
		this.removeAllViews();
	}
}
