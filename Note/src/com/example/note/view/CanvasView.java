package com.example.note.view;

import java.util.Timer;
import java.util.TimerTask;

import com.example.note.component.MultiStrokes;
import com.example.note.component.MyPaint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.View;


public class CanvasView extends View{
	
	/**
	 * components
	 */
	public MultiStrokes largeStrokes;

	/**
	 * paints
	 */
	private Paint chunkPaint;
	protected Paint notesPaint;
	private Paint framePaint;
	private Paint debugPaint;
	
	/**
	 * states
	 */
	private enum State {MAGNIFY, WRITING, EDITING, NON_MAGNIFY, MAG2NON, NON2MAG, MAG2WRITING, WRITING2MAG, WRITING2NON};
	private State state;
	

	
	
	public CanvasView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		//components
		
		largeStrokes = new MultiStrokes();
		//paints
		chunkPaint = MyPaint.createPaint(Color.BLACK, 8);
		notesPaint = MyPaint.createPaint(Color.BLACK, 6);
		framePaint = MyPaint.createPaint(Color.GREEN, 2);
		debugPaint = MyPaint.createPaint(Color.RED, 2);
		
	}
	

	
	@Override
	public void onDraw(Canvas c){
		super.onDraw(c);
		//System.out.println("canvasView draw CALLED!!!");
		//draw large strokes
		largeStrokes.draw(c, notesPaint);
	}
	
	public void undo(){
		if (!this.largeStrokes.isEmpty()){
			largeStrokes.chunk.removeLast();
		}
		this.invalidate();
	}
    

}
