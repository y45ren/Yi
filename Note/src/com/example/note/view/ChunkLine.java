package com.example.note.view;

import java.util.ArrayList;

import com.example.note.component.MultiStrokes;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ChunkLine extends LinearLayout{

	private ArrayList<ChunkFrame> chunkFrame;
	public LayoutParams params;
	
	public ChunkLine(Context context, LayoutParams params) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setOrientation(HORIZONTAL);
		chunkFrame = new ArrayList<ChunkFrame>();
		this.params = new LayoutParams(params);
		//this.setBackgroundColor(Color.YELLOW);
	}

	public void addChunk(MultiStrokes newChunk, Point pivotPoint, Point endPoint, double scale, int width, int regionHeight) {
		// TODO Auto-generated method stub
		chunkFrame.add(new ChunkFrame(getContext(), newChunk));
		chunkFrame.get(chunkFrame.size()-1).addChunk(newChunk, pivotPoint, endPoint, scale, width, regionHeight);
		
		//LayoutParams params = new LinearLayout.LayoutParams((int) width, regionHeight);
		
		LayoutParams childParams = new LinearLayout.LayoutParams(width, regionHeight);
		//LayoutParams childParams = new LinearLayout.LayoutParams(endPoint.x-pivotPoint.x, endPoint.y-pivotPoint.y);
//		LayoutParams childParams = new LinearLayout.LayoutParams(endPoint.x-pivotPoint.x, endPoint.y-pivotPoint.y);
//		childParams.leftMargin = -pivotPoint.x;
//		childParams.topMargin = -pivotPoint.y;
//		childParams.rightMargin = (int) ((endPoint.x-pivotPoint.x)*scale);
//		childParams.bottomMargin = (int) ((endPoint.y-pivotPoint.y)*scale);
//		childParams.rightMargin = (int) ((endPoint.x-pivotPoint.x));
//		childParams.bottomMargin = (int) ((endPoint.y-pivotPoint.y));
        
		this.addView(chunkFrame.get(chunkFrame.size()-1),childParams);

		this.params.width += width;
		System.out.println("a: "+(int)width+"b: "+this.params.width);
	}
	
}
