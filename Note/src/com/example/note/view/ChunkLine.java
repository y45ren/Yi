package com.example.note.view;

import java.util.LinkedList;

import com.example.note.component.MultiStrokes;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ChunkLine extends LinearLayout{

	public LinkedList<ChunkFrame> chunkFrame;
	public LayoutParams params;
	private int line;
	private int columns;
	
	public ChunkLine(Context context, LayoutParams params, int line) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setOrientation(HORIZONTAL);
		this.line=line;
		this.columns=0;
		chunkFrame = new LinkedList<ChunkFrame>();
		this.params = new LayoutParams(params);
//		this.setBackgroundColor(Color.YELLOW);
	}

	public void addChunk(MultiStrokes newChunk, Point pivotPoint, Point endPoint, double scale, int width, int regionHeight) {
		// TODO Auto-generated method stub
		LayoutParams childParams = new LinearLayout.LayoutParams(width, regionHeight);
		chunkFrame.add(new ChunkFrame(getContext(), newChunk, childParams, line, columns));
		columns++;
		chunkFrame.peekLast().addChunk(newChunk, pivotPoint, endPoint, scale, width, regionHeight);
		
		//LayoutParams params = new LinearLayout.LayoutParams((int) width, regionHeight);
		
		
		//LayoutParams childParams = new LinearLayout.LayoutParams(endPoint.x-pivotPoint.x, endPoint.y-pivotPoint.y);
//		LayoutParams childParams = new LinearLayout.LayoutParams(endPoint.x-pivotPoint.x, endPoint.y-pivotPoint.y);
//		childParams.leftMargin = -pivotPoint.x;
//		childParams.topMargin = -pivotPoint.y;
//		childParams.rightMargin = (int) ((endPoint.x-pivotPoint.x)*scale);
//		childParams.bottomMargin = (int) ((endPoint.y-pivotPoint.y)*scale);
//		childParams.rightMargin = (int) ((endPoint.x-pivotPoint.x));
//		childParams.bottomMargin = (int) ((endPoint.y-pivotPoint.y));
        
		this.addView(chunkFrame.peekLast(),childParams);
		
		this.params.width += width;
		System.out.println("a: "+(int)width+"b: "+this.params.width);
	}

	public void undo() {
		// TODO Auto-generated method stub
		this.chunkFrame.peekLast().undo();
		this.params.width -= this.chunkFrame.peekLast().params.width;
		this.removeView(this.chunkFrame.pollLast());
		this.columns--;
	}

	public int getColumns() {
		// TODO Auto-generated method stub
		return this.columns;
	}
	
}
