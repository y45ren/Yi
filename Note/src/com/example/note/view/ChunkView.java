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
	public ChunkView(Context context, MultiStrokes chunk) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setBackgroundColor(Color.GREEN);
		notesPaint = MyPaint.createPaint(Color.BLACK, 4);
		this.chunk = chunk;
		invalidate();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawLine(0, 0, 70, 10, MyPaint.createPaint(Color.BLUE, 10));
		chunk.draw(canvas, notesPaint);
	}

	public void addChunk(MultiStrokes newChunk) {
		// TODO Auto-generated method stub
		
	}
}
