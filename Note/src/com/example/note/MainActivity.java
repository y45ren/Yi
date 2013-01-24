package com.example.note;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.example.note.Listener.Listener;
import com.example.note.component.InkRegion;
import com.example.note.component.MultiStrokes;
import com.example.note.component.Status;
import com.example.note.view.CanvasView;
import com.example.note.view.MagnifiedView;


import android.os.Bundle;
import android.os.Vibrator;

import android.app.Activity;

import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.*;

public class MainActivity extends Activity{

	private CanvasView canvasView;
	private Switch switchy;
	private Button next;
	public RelativeLayout noteLayout;
	public MagnifiedView magnifiedView;
	public ArrayList<InkRegion> inkRegion;
	/**
	 * sensors and timers
	 */
	private SensorManager sm=null;  
	private Sensor aSensor=null;  
	private Sensor mSensor=null;  
	private Vibrator vibrator;
	
	private Timer timer;
	private Timer anchorTimer;
	final private int activeTime = 750;
	final private int holdAchorTime = 650;
	final private int vibrationTime = 100;
	/**
	 * Listeners
	 */
	Listener listener;
	/**
	 * status
	 */
	private Status status;

	/**
	 * components
	 */
	private Point setAnchorPoint;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Open app in fullscreen mode
    	super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        
        //sensors and timers
        sm=(SensorManager)getSystemService(this.SENSOR_SERVICE);  
        aSensor=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);  
        mSensor=sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);  
        vibrator = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
        
        //Listeners
        listener = new Listener(this);
        //status
        status = Status.WRITING;
        
        //conponent
        setAnchorPoint = new Point();
        
        noteLayout = (RelativeLayout)findViewById(R.id.notePanel);
        inkRegion = new ArrayList<InkRegion>();
        new MultiStrokes();
        canvasView = new CanvasView(this);
        magnifiedView = new MagnifiedView(this);
        switchy = new Switch(this);
        switchy = (Switch)findViewById(R.id.switch1);
        switchy.setOnCheckedChangeListener(listener);
        
        next = new Button(this);
        next = (Button)findViewById(R.id.button1);
        next.setOnClickListener(listener);
        
        
        inkRegion.add(new InkRegion(this,0));
        inkRegion.add(new InkRegion(this,1));
        //inkRegion.get(0).setBackgroundColor(Color.YELLOW);
        //inkRegion.get(1).setBackgroundColor(Color.CYAN);
        
        //noteLayout.addView(canvasView);
        noteLayout.addView(canvasView);
        noteLayout.addView(magnifiedView);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(200, 200);
        params.leftMargin = 300;
        params.topMargin = 200;
        inkRegion.get(0).setBackgroundColor(Color.GREEN);
        inkRegion.get(0).setPivotX(0);
        inkRegion.get(0).setPivotY(0);
        inkRegion.get(0).setRotation(45);
        inkRegion.get(0).setScaleX((float) 0.2);
        inkRegion.get(0).setScaleY((float) 0.3);
        noteLayout.addView(inkRegion.get(0),params);
        
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(200, 200);
        params1.leftMargin = 300;
        params1.topMargin = 200;
        inkRegion.get(1).setBackgroundColor(Color.BLUE);
        inkRegion.get(1).setPivotX(0);
        inkRegion.get(1).setPivotY(0);
        inkRegion.get(1).setRotation(90);
        inkRegion.get(1).setScaleX((float) 0.2);
        inkRegion.get(1).setScaleY((float) 0.2);
        noteLayout.addView(inkRegion.get(1), params1);
        
    }
    
    
    @Override
    public boolean onTouchEvent(MotionEvent event){
    	Point eventPoint = new Point((int)event.getX(),(int)event.getY());
    	if (!switchy.isChecked()){
    		// Non-magnify mode
    	switch(event.getActionMasked()){
			case MotionEvent.ACTION_DOWN:
				canvasView.largeStrokes.addStroke(eventPoint);
				canvasView.invalidate();			
				break;
			case MotionEvent.ACTION_CANCEL:
				break;
			case MotionEvent.ACTION_MOVE:
				canvasView.largeStrokes.addPoint(eventPoint);	
				canvasView.invalidate();
				break;
			case MotionEvent.ACTION_UP:
				canvasView.invalidate();
				
				break;
			default:
				break;
			}
    	}else{
    		// Magnify mode
    		switch(event.getActionMasked()){
			case MotionEvent.ACTION_DOWN:
				
				switch(status){
				case WRITING:
					setAnchorPoint.set(eventPoint.x, eventPoint.y);
					magnifiedView.largeStrokes.addStroke(eventPoint);
					magnifiedView.invalidate();
					break;
				case LOCATINGANCHOR:
					magnifiedView.anchor.setPoint(eventPoint);
					break;
				case SCALINGANCHOR:
					break;
				}
				startAnchorTimer(eventPoint);
				
				break;
			case MotionEvent.ACTION_CANCEL:
				break;
			case MotionEvent.ACTION_MOVE:
				
				switch(status){
				case WRITING:
					if (!isCLose(eventPoint,setAnchorPoint)){
						anchorTimer.cancel();
						anchorTimer.purge();
					}
					magnifiedView.largeStrokes.addPoint(eventPoint);
					magnifiedView.invalidate();
					break;
				case LOCATINGANCHOR:
					magnifiedView.anchor.setPoint(eventPoint);
					break;
				case SCALINGANCHOR:
					break;
				}	
				break;
			case MotionEvent.ACTION_UP:
				
				switch(status){
				case WRITING:
					anchorTimer.cancel();
					anchorTimer.purge();
					magnifiedView.invalidate();
					break;
				case LOCATINGANCHOR:
					status = Status.WRITING;
					sm.unregisterListener(myListener);
					if (inkRegion.get(inkRegion.size()-1).chunkLine.size()!=0){
						inkRegion.add(new InkRegion(this, magnifiedView.anchor.getHeight(), magnifiedView.anchor.getPoint()));
						RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(200, magnifiedView.anchor.getAnchorLen());
				        params.leftMargin = magnifiedView.anchor.getPoint().x;
				        params.topMargin = magnifiedView.anchor.getPoint().y;
				        inkRegion.get(inkRegion.size()-1).setPivotX(0);
				        inkRegion.get(inkRegion.size()-1).setPivotY(0);
				        inkRegion.get(inkRegion.size()-1).setRotation(magnifiedView.anchor.getAngleInDegrees());
						this.noteLayout.addView(inkRegion.get(inkRegion.size()-1),params);
						inkRegion.get(inkRegion.size()-1).setBackgroundColor(Color.RED);	
						System.out.println("HIHIHIHI"+magnifiedView.anchor.getAngle()+"ADASD"+magnifiedView.anchor.getAnchorLen());
					}
					break;
				case SCALINGANCHOR:
					status = Status.WRITING;
					break;
				}	
				break;
			default:
				break;
			}    		
    	}
    	
		return true;
    }


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }


    /**
     * start timer for anchor
     */
	private void startAnchorTimer(final Point eventPoint){
		anchorTimer = new Timer();
		anchorTimer.schedule(new TimerTask(){
			public void run(){
				vibrator.vibrate(vibrationTime);
				
				if (!magnifiedView.anchor.onAnchor(eventPoint)){
					status = Status.LOCATINGANCHOR;
					magnifiedView.anchor.setPoint(eventPoint);
					sm.registerListener(myListener, aSensor, SensorManager.SENSOR_DELAY_GAME);
				}else{
					status = Status.SCALINGANCHOR;
				} 
				magnifiedView.postInvalidate();
			}
		}, activeTime);
	}
    
    /**
     * judge if 2 points are close enough
     * @param p1
     * @param p2
     * @return
     */
	private boolean isCLose(Point p1, Point p2) {
		// TODO Auto-generated method stub
		if (Math.sqrt(Math.pow((p1.x-p2.x), 2)+Math.pow(p1.y-p2.y, 2))<40){
			return true;
		}
		return false;
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
            	magnifiedView.anchor.setRotate(accelerometerValues);
            	magnifiedView.postInvalidate();
            }
        }
    };
    
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(myListener);
    }

}
