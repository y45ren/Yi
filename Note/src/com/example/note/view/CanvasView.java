package com.example.note.view;

import java.util.Timer;
import java.util.TimerTask;

import com.example.note.component.MultiStrokes;
import com.example.note.component.MyPaint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
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
		
		
//		if (this.largeStrokes.chunk.size()!=1){
//			c.drawPath(largeStrokes.chunk.get(1), notesPaint);
//			System.out.println("Printed!!!");
//		}else{
//			c.drawPath(largeStrokes.chunk.get(0), notesPaint);
//		}
	}
	
	public void undo(){
		if (!this.largeStrokes.isEmpty()){
			largeStrokes.chunk.removeFirst();
		}
		
		RectF bounds = new RectF();
		for (Path stroke:this.largeStrokes.chunk){
			
			stroke.computeBounds(bounds, true);
			System.out.println("@@@@@@@@@@@@@ "+bounds);
			
		}
		
		System.out.println("asdfasdfasdf "+ this.largeStrokes.chunk.size() + " "+ this.largeStrokes.chunk.peek().isEmpty());
		
		this.invalidate();
	}



	public void addStroke(Point eventPoint) {
		// TODO Auto-generated method stub
		this.largeStrokes.addStroke(eventPoint);
	}



	public void addPoint(Point eventPoint) {
		// TODO Auto-generated method stub
		this.largeStrokes.addPoint(eventPoint);
	}
    

}
