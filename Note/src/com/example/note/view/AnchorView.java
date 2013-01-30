package com.example.note.view;

import com.example.note.component.anchor.Anchor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.view.View;

public class AnchorView extends View{

	public Anchor anchor;
	
	public AnchorView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		anchor = new Anchor(new Point(300,200));
	}
	@Override
	public void onSizeChanged(int w,int h,int ow, int oh){
		anchor.setHeight(h);
		anchor.setWidth(w);
	}
	
	@Override
	public void onDraw(Canvas c){
		super.onDraw(c);
		//anchor
		anchor.draw(c);
		//System.out.println("AnchorView draw CALLED!!!");
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
