package com.example.note;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import com.example.note.Listener.Listener;
import com.example.note.component.MultiStrokes;
import com.example.note.component.Orientation;
import com.example.note.component.Status;
import com.example.note.component.myTimer;
import com.example.note.view.AnchorView;
import com.example.note.view.CanvasView;
import com.example.note.view.InkRegion;
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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.*;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends Activity{

	/**
	 * views
	 */
	public CanvasView canvasView;
	public Switch switchy;
	public Button next;
	public Button newLine;
	public Button undo;
	public Button erase;
	public RelativeLayout noteLayout;
	public MagnifiedView magnifiedView;
	public AnchorView anchorView;
	public LinkedList<InkRegion> inkRegion;
	/**
	 * sensors and timers
	 */
	public SensorManager sm=null;  
	public Sensor aSensor=null;  
	private Sensor mSensor=null;  
	private Vibrator vibrator;
	
	public Timer timer;
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
	public Orientation orientation;
	/**
	 * components
	 */
	private Point setAnchorPoint;
	private int screenWidth;
	private int screenHeight;
	

	
	
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
        orientation = Orientation.INITIAL;
        //conponent
        setAnchorPoint = new Point();
        screenWidth = 0;
        screenHeight = 0;
        
        noteLayout = (RelativeLayout)findViewById(R.id.notePanel);
        inkRegion = new LinkedList<InkRegion>();
        new MultiStrokes();
        canvasView = new CanvasView(this);
        
        magnifiedView = new MagnifiedView(this);
        
        anchorView = new AnchorView(this);
        
        initiateInkRegion();
        
        switchy = new Switch(this);
        switchy = (Switch)findViewById(R.id.switch1);
        switchy.setOnCheckedChangeListener(listener);
        
        next = new Button(this);
        next = (Button)findViewById(R.id.next);
        next.setOnClickListener(listener);
        newLine = new Button(this);
        newLine = (Button)findViewById(R.id.newLine);
        newLine.setOnClickListener(listener);
        undo = new Button(this);
        undo = (Button)findViewById(R.id.undo);
        undo.setOnClickListener(listener);
        erase = new Button(this);
        erase = (Button)findViewById(R.id.erase);
        erase.setOnClickListener(listener);
        
        //noteLayout.addView(canvasView);
        noteLayout.addView(canvasView);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(2000, 2000);
//		params.leftMargin = -750;
//        params.topMargin = -850;
        noteLayout.addView(magnifiedView);       
        noteLayout.addView(anchorView);

    }
    
    
    public void initiateInkRegion() {
		// TODO Auto-generated method stub
    	inkRegion.add(new InkRegion(this, anchorView.anchor.getAnchorLen(), 
				anchorView.anchor.getPoint(), anchorView.anchor.getAngleInDegrees()));
		this.noteLayout.addView(inkRegion.get(0),inkRegion.get(0).params);
	}


	@Override
    public boolean onTouchEvent(MotionEvent event){
    	Point eventPoint = new Point((int)event.getX(),(int)event.getY());
    	int action = event.getAction();
    	int actionCode = action & MotionEvent.ACTION_MASK;
//    	System.out.println("NUB ERVMERV: "+event.getActionIndex());
//    	System.out.println("dfdetdfg: "+event.getPointerCount());
  
    	if (!switchy.isChecked()){
    		// Non-magnify mode
    	switch(actionCode){
			case MotionEvent.ACTION_DOWN:
				canvasView.addStroke(eventPoint);
				canvasView.invalidate();			
				break;
			case MotionEvent.ACTION_CANCEL:
				break;
			case MotionEvent.ACTION_MOVE:
				canvasView.addPoint(eventPoint);	
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
    		switch(actionCode){
			case MotionEvent.ACTION_DOWN:
				
				switch(status){
				case WRITING:
					setAnchorPoint.set(eventPoint.x, eventPoint.y);
					magnifiedView.addStroke(eventPoint);
					magnifiedView.invalidate();
					try{
						timer.cancel();
						timer.purge();
					}catch(Exception e){
						
					}
					break;
				case LOCATINGANCHOR:
					anchorView.anchor.setPoint(eventPoint);
					anchorView.invalidate();
					break;
				case SCALINGANCHOR:
					anchorView.anchor.setAnchorLen(eventPoint);
					anchorView.invalidate();
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
					magnifiedView.addPoint(eventPoint);
					magnifiedView.invalidate();
					break;
				case LOCATINGANCHOR:
					anchorView.anchor.setPoint(eventPoint);
					anchorView.invalidate();
					break;
				case SCALINGANCHOR:
					anchorView.anchor.setAnchorLen(eventPoint);
					anchorView.invalidate();
					break;
				}	
				break;
			case MotionEvent.ACTION_UP:
				
				switch(status){
				case WRITING:
					anchorTimer.cancel();
					anchorTimer.purge();
					magnifiedView.invalidate();
					this.startTimer();
					break;
				case LOCATINGANCHOR:
					status = Status.WRITING;
					anchorView.anchor.setRedCross(false);
					sm.unregisterListener(anchorSensorListener);
					
					if (inkRegion.size()!=0 && inkRegion.get(inkRegion.size()-1).chunkLine.size()==0){
						noteLayout.removeView(inkRegion.get(inkRegion.size()-1));
						inkRegion.remove(inkRegion.size()-1);						
					}
					inkRegion.add(new InkRegion(this, anchorView.anchor.getAnchorLen(), 
							anchorView.anchor.getPoint(), anchorView.anchor.getAngleInDegrees()));
					
					this.noteLayout.addView(inkRegion.get(inkRegion.size()-1),inkRegion.get(inkRegion.size()-1).params);
					this.magnifiedView.setAngle(anchorView.anchor.getAngle());
//					this.magnifiedView.setRotation(anchorView.anchor.getAngleInDegrees());
					//inkRegion.get(inkRegion.size()-1).setBackgroundColor(Color.RED);	
					this.anchorView.invalidate();
					break;
				case SCALINGANCHOR:
					status = Status.WRITING;
					if (inkRegion.size()!=0 && inkRegion.get(inkRegion.size()-1).chunkLine.size()==0){
						noteLayout.removeView(inkRegion.get(inkRegion.size()-1));
						inkRegion.remove(inkRegion.size()-1);						
					}
					inkRegion.add(new InkRegion(this, anchorView.anchor.getAnchorLen(), 
							anchorView.anchor.getPoint(), anchorView.anchor.getAngleInDegrees()));
					this.noteLayout.addView(inkRegion.get(inkRegion.size()-1),inkRegion.get(inkRegion.size()-1).params);
					//inkRegion.get(inkRegion.size()-1).setBackgroundColor(Color.RED);
					anchorView.invalidate();
					break;
				}	
				break;
			default:
				break;
			}    		
    	}
    	
		return false;
    }


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	
	
	private void startTimer(){
		timer = new Timer();
		
		timer.schedule(new TimerTask(){
			public void run(){
				magnifiedView.computeSize();				
				runOnUiThread(new Runnable() {
				public void run() {
					System.out.println("timer triggered!");
				
					MultiStrokes newChunk = new MultiStrokes();
					newChunk.copyChunk(magnifiedView.getRotateChunk());
					int regionIndex = inkRegion.size()-1;
					Point pivotPoint = new Point(magnifiedView.minX, magnifiedView.minY);
					Point endPoint = new Point(magnifiedView.maxX, magnifiedView.maxY);
//					double scale = (double)inkRegion.get(regionIndex).getLineHeight() / (double)(magnifiedView.maxY - magnifiedView.minY);
					double scale = (double)inkRegion.peekLast().getLineHeight() / (double)400;
					int width = (int) (scale * (magnifiedView.maxX - magnifiedView.minX));
					inkRegion.peekLast().addChunk(newChunk, pivotPoint, endPoint, scale, width);
					anchorView.anchor.setPoint(inkRegion.peekLast().generateLastPosition());
					magnifiedView.clear();
					anchorView.invalidate();
					magnifiedView.invalidate();	
				    }
				});

				
			}
		}, activeTime);
	}
	

    /**
     * start timer for anchor
     */
	private void startAnchorTimer(final Point eventPoint){
		anchorTimer = new Timer();
		anchorTimer.schedule(new TimerTask(){
			public void run(){
				vibrator.vibrate(vibrationTime);
				magnifiedView.clear();
				if (!anchorView.anchor.onAnchor(eventPoint)){
					status = Status.LOCATINGANCHOR;
					anchorView.anchor.setPoint(eventPoint);
					anchorView.anchor.setRedCross(true);
					sm.registerListener(anchorSensorListener, aSensor, SensorManager.SENSOR_DELAY_GAME);
				}else{
					status = Status.SCALINGANCHOR;
				} 
				anchorView.postInvalidate();
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

    public SensorEventListener anchorSensorListener=new SensorEventListener(){  
        float[] accelerometerValues=new float[3];   
        public void onAccuracyChanged(Sensor sensor, int accuracy) {  
            // TODO Auto-generated method stub  
              
        }  
  
        public void onSensorChanged(SensorEvent event) {  
            // TODO Auto-generated method stub  
            accelerometerValues=event.values;  
            if (accelerometerValues[2]<8.0){		//if the screen is not horizontal
            	anchorView.anchor.setRotate(accelerometerValues);
            	magnifiedView.postInvalidate();
            }
        }
    };
    
    public SensorEventListener rotationSensorListener=new SensorEventListener(){  
        float[] accelerometerValues=new float[3];
        public void onAccuracyChanged(Sensor sensor, int accuracy) {  
            // TODO Auto-generated method stub  
              
        }  
  
        public void onSensorChanged(SensorEvent event) {  
            // TODO Auto-generated method stub  
            accelerometerValues=event.values;
            if (accelerometerValues[2]<8.0){		//if the screen is not horizontal
            	if (Math.abs(accelerometerValues[0])<2.0&&accelerometerValues[1]>4.0){
            		orientation = Orientation.CLOCKWISE90;
            	}else if (Math.abs(accelerometerValues[1])<2.0&&accelerometerValues[0]>4.0){
            		orientation = Orientation.INITIAL;
            	}else if (Math.abs(accelerometerValues[0])<2.0&&accelerometerValues[1]<-4.0){
            		orientation = Orientation.CLOCKWISE180;
            	}else if (Math.abs(accelerometerValues[1])<2.0&&accelerometerValues[0]<-4.0){
            		orientation = Orientation.CLOCKWISE270;
            	}
            	System.out.println(accelerometerValues[0]+", "+accelerometerValues[1]+", "+accelerometerValues[2]);
            }
        }
    };
    
//    Another way to keep contents when rotating the screen
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        // Read values from the "savedInstanceState"-object and put them in your textview
//    }
//
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        // Save the values you need from your textview into "outState"-object
//        super.onSaveInstanceState(outState);
//    }
    
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(anchorSensorListener);
    }
     

}
