package com.example.note.component;

import java.util.Timer;
import java.util.TimerTask;

import com.example.note.component.anchor.Anchor;

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
	private static Anchor anchor;
	public static MultiStrokes largeStrokes;
	
	/**
	 * attributes
	 */
	private int viewHeight;
	private int viewWidth;
	
	/**
	 * paints
	 */
	private Paint chunkPaint;
	private Paint notesPaint;
	private Paint framePaint;
	private Paint debugPaint;
	
	/**
	 * states
	 */
	private enum State {MAGNIFY, WRITING, EDITING, NON_MAGNIFY, MAG2NON, NON2MAG, MAG2WRITING, WRITING2MAG, WRITING2NON};
	private State state;
	
	/**
	 * sensors and timers
	 */
	private Timer timer;
	final private int activeTime = 1000;
	
	
	public CanvasView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		//components
		anchor = new Anchor(new Point(300,200));
		largeStrokes = new MultiStrokes();
		//paints
		chunkPaint = MyPaint.createPaint(Color.BLACK, 8);
		notesPaint = MyPaint.createPaint(Color.BLACK, 4);
		framePaint = MyPaint.createPaint(Color.GREEN, 2);
		debugPaint = MyPaint.createPaint(Color.RED, 2);
		
	}
	
	@Override
	public void onSizeChanged(int w,int h,int ow, int oh){
		viewHeight = h;
		viewWidth = w;
		
		anchor.setHeight(h);
		anchor.setWidth(w);
	}

	
	@Override
	public void onDraw(Canvas c){
		super.onDraw(c);
		
		//anchor
		anchor.draw(c, true);
		
		//draw large strokes
		largeStrokes.draw(c, notesPaint);
	}
	
	
    final SensorEventListener myListener=new SensorEventListener(){  
        float[] accelerometerValues=new float[3];   
        public void onAccuracyChanged(Sensor sensor, int accuracy) {  
            // TODO Auto-generated method stub  
              
        }  
  
        public void onSensorChanged(SensorEvent event) {  
            // TODO Auto-generated method stub  
            accelerometerValues=event.values;  
            if (accelerometerValues[2]<8.0){		//if the screen is not horizontal
                anchor.setRotate(accelerometerValues);
                postInvalidate();
            }
        }
    };
    

}
