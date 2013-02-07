package com.example.note.view;

import com.example.note.component.MyPaint;
import com.example.note.component.Stroke;
import com.example.note.component.anchor.Anchor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;


public class MagnifiedView extends CanvasView{

	
	
	public int minX;
	public int minY;
	public int maxX;
	public int maxY;
	protected Paint notesPaint;
	
	/**
	 * attributes
	 */
	private int viewHeight;
	private int viewWidth;
	
	
	public MagnifiedView(Context context) {
		
		super(context);
		notesPaint = MyPaint.createPaint(Color.BLACK, 8);
		minX=4000;
		minY=4000;
		maxX=0;
		maxY=0;
		
	}
	
	@Override
	public void onSizeChanged(int w,int h,int ow, int oh){
		viewHeight = h;
		viewWidth = w;
	}
	
	
	@Override
	public void onDraw(Canvas c){
		super.onDraw(c);
		computeSize();
		//System.out.println("MaginfView draw CALLED!!!");
//		c.drawLine(minX, minY, maxX, maxY, notesPaint);
		//draw large strokes
		largeStrokes.draw(c, notesPaint);
	}
	
	public void computeSize(){
		for (Stroke stroke:largeStrokes.chunk){
			for (Point point:stroke.stroke){
				if (point.x<minX){
					minX=point.x;
				}
				if (point.y<minY){
					minY=point.y;
				}
				if (point.x>maxX){
					maxX=point.x;
				}
				if (point.y>maxY){
					maxY=point.y;
				}
			}
		}
	}
	public void clear(){
		this.largeStrokes.clear();
		minX=4000;
		minY=4000;
		maxX=0;
		maxY=0;
	}
    

}
