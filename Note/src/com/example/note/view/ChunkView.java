package com.example.note.view;

import com.example.note.component.MultiStrokes;
import com.example.note.component.MyPaint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class ChunkView extends View{
	private MultiStrokes chunk;
	private Paint notesPaint;
	private double scale;
	
	public ChunkView(Context context, MultiStrokes chunk) {
		super(context);
		// TODO Auto-generated constructor stub
		//this.setBackgroundColor(Color.GREEN);
		
		this.chunk = new MultiStrokes();
		this.chunk.copyChunk(chunk);
		
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int thickness = (int) (8/scale-1);
		notesPaint = MyPaint.createPaint(Color.BLACK, 8);
		chunk.draw(canvas, notesPaint);
		//System.out.println("chunkView draw CALLED!!!");
	}

	public void addChunk(MultiStrokes newChunk) {
		// TODO Auto-generated method stub
		
	}

	public void setScale(double scale) {
		// TODO Auto-generated method stub
		this.scale = scale;
	}
}
