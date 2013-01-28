package com.example.note.view;

import com.example.note.component.Stroke;
import com.example.note.component.anchor.Anchor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;


public class MagnifiedView extends CanvasView{

	public Anchor anchor;
	
	public int minX;
	public int minY;
	public int maxX;
	public int maxY;
	
	/**
	 * attributes
	 */
	private int viewHeight;
	private int viewWidth;
	
	
	public MagnifiedView(Context context) {
		
		super(context);
		anchor = new Anchor(new Point(300,200));
		minX=4000;
		minY=4000;
		maxX=0;
		maxY=0;
		
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
		computeSize();
		//anchor
		anchor.draw(c, true);
		c.drawLine(minX, minY, maxX, maxY, notesPaint);
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
