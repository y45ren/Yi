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
	private static MultiStrokes largeStrokes;
	
	/**
	 * attributes
	 */
	private int viewHeight;
	private int viewWidth;
	
	/**
	 * states
	 */
	private enum State {MAGNIFY, WRITING, EDITING, NON_MAGNIFY, MAG2NON, NON2MAG, MAG2WRITING, WRITING2MAG, WRITING2NON};
	private enum buttons {MAGNIFY, NEW_CHUNK, NEW_LINE, ANCHOR_SET, UNDO, NONE};
	private State state;
	
	/**
	 * sensors and timers
	 */
	private Timer timer;
	final private int activeTime = 1000;
	
	
	public CanvasView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		anchor = new Anchor(new Point(300,200));
		//System.out.println(viewHeight);
		//setVisibility(INVISIBLE);
	}
	
	@Override
	public void onSizeChanged(int w,int h,int ow, int oh){
		viewHeight = h;
		viewWidth = w;
		
		anchor.setHeight(h);
		anchor.setWidth(w);
	}

//	@Override
//	public boolean onTouchEvent(MotionEvent event){
//		Point eventPoint = new Point((int)event.getX(),(int)event.getY());
//		
//		switch(event.getActionMasked()){
//		case MotionEvent.ACTION_DOWN:
//			if (state == State.MAGNIFY){
//				chunkIndex=notes.checkChunk(eventPoint);
//				//deny editing mod
//				chunkIndex = 500;
//				if (chunkIndex!=500){
//					state = State.EDITING;
//					currentChunk.copyChunk(notes.notesInLarge.get(chunkIndex));
//					anchor.setPoint(currentChunk.getStartPoint());
//					anchor.setAngle(currentChunk.getAngle());
//				}else{
//					if (buttonClicked(eventPoint) == buttons.ANCHOR_SET){
//						state = State.MAG2WRITING;
//					}else if(buttonClicked(eventPoint) == buttons.MAGNIFY){
//						state = State.MAG2NON;
//						//notes.addLargeChunk();
//					}else{
//						MainActivity.sm.registerListener(myListener, MainActivity.aSensor, SensorManager.SENSOR_DELAY_GAME);
//						anchor.setPoint(eventPoint);
//					}
//				}
//				
//			}else if(state == State.NON_MAGNIFY){
//				if(buttonClicked(eventPoint) == buttons.MAGNIFY){
//					state = State.NON2MAG;
//				}else if(buttonClicked(eventPoint) == buttons.UNDO){
//					if (notes.largeChunks.get(0).chunk.size()!=0){
//						notes.largeChunks.get(0).chunk.remove(notes.largeChunks.get(0).chunk.size()-1);
//						invalidate();
//					}
//				}else{
//					notes.addStroke2LargeChunk(eventPoint);
//					invalidate();
//				}
//			}else if(state == State.MAG2NON){
//				
//			}else if(state == State.NON2MAG){
//				
//			}else{
//				if (buttonClicked(eventPoint)==buttons.NEW_CHUNK){
//					notes.addChunk(currentChunk);
//					currentChunk.nextChunk();
//					//currentChunk.addStroke();
//					anchor.setPoint(currentChunk.getStartPoint());
//				}else if (buttonClicked(eventPoint)==buttons.NEW_LINE){
//					notes.addChunk(currentChunk);
//					currentChunk.nextLine();
//					//currentChunk.addStroke();
//					anchor.setPoint(currentChunk.getStartPoint());
//				}else if (buttonClicked(eventPoint)==buttons.MAGNIFY){
//					state = State.WRITING2NON;
//				}else if (buttonClicked(eventPoint)==buttons.ANCHOR_SET){
//					state = State.WRITING2MAG;
//				}else if(buttonClicked(eventPoint) == buttons.UNDO){
//					if (currentChunk.chunk.size()!=0){
//						currentChunk.chunk.remove(currentChunk.chunk.size()-1);
//					}else if(notes.existingNotes.isEmpty()!=true){
//						int index = notes.existingNotes.size()-1;
//						anchor.setPoint(notes.existingNotes.get(index).getStartPoint());
//						anchor.setAngle(notes.existingNotes.get(index).getAngle());
//						currentChunk.setLineStartPoint(notes.existingNotes.get(index).getLineStartPoint());
//						System.out.println("######## "+currentChunk.getLineStartPoint());
//						currentChunk.setStartPoint(anchor.getPoint());
//						currentChunk.setEndPoint(anchor.getPoint());
//						currentChunk.setAngle(anchor.getAngle());
//						//currentChunk.nextChunk();
//						notes.existingNotes.remove(index);
//						notes.notesInLarge.remove(index);
//					}
//					invalidate();
//				}else{
//					currentChunk.addStroke(eventPoint);
//				}
//				invalidate();
//				//timer.cancel();
//				//timer.purge();
//			}
//			break;
//		case MotionEvent.ACTION_CANCEL:
//			break;
//		case MotionEvent.ACTION_MOVE:
//			if (state == State.MAGNIFY){
//				anchor.setPoint(eventPoint);
//				invalidate();
//			}else if(state == State.NON_MAGNIFY){
//				if (buttonClicked(eventPoint)!=buttons.MAGNIFY && buttonClicked(eventPoint)!=buttons.UNDO){
//					notes.addPoint2LargeChunk(eventPoint);
//					invalidate();
//				}
//				
//			}else if(state == State.MAG2NON){
//				
//			}else if(state == State.NON2MAG){
//				
//			}else{
//				if (buttonClicked(eventPoint)==buttons.NONE){
//					currentChunk.addPoint(eventPoint);
//					invalidate();
//				}
//				
//				//timer.cancel();
//				//timer.purge();
//				
//			}
//			
//			break;
//		case MotionEvent.ACTION_UP:
//			if (state == State.MAGNIFY){
//				//start a panel where u can write, and start timer, 
//				//if no more event taking place in activeTime(420 ms), state will become READING¡¡again. 
//				//anchorLocation = new Point((int)event.getX(),(int)event.getY());				
//				MainActivity.sm.unregisterListener(myListener);
//				//adjustingAnchor = false;
//				//state = State.WRITING;
//				currentChunk.setStartPoint(eventPoint);
//				currentChunk.setLineStartPoint(eventPoint);
//				currentChunk.setAngle(anchor.getAngle());
//				//startTimer();
//			}else if(state == State.MAG2NON){
//				state = State.NON_MAGNIFY;
//				invalidate();
//			}else if(state == State.NON2MAG){
//				state = State.MAGNIFY;
//				invalidate();
//			}else if(state == State.WRITING2NON){
//				state = State.NON_MAGNIFY;
//				if (currentChunk.chunk.size()!=0){
//					notes.addChunk(currentChunk);
//					currentChunk.nextChunk();
//					currentChunk.addStroke();
//					anchor.setPoint(currentChunk.getStartPoint());
//				}
//				invalidate();
//			}else if(state == State.WRITING2MAG){
//				state = State.MAGNIFY;
//				if (currentChunk.chunk.size()!=0){
//					notes.addChunk(currentChunk);
//					currentChunk.nextChunk();
//					currentChunk.addStroke();
//					anchor.setPoint(currentChunk.getStartPoint());
//				}
//				invalidate();
//			}else if(state == State.MAG2WRITING){
//				state = State.WRITING;
//				invalidate();
//			}else if(state == State.NON_MAGNIFY){
//				
//			}else{			
//				//startTimer();
//				
//			}
//			
//			break;
//		default:
//			break;
//		}
//		
//		return true;
//		
//	}
	
	@Override
	public void onDraw(Canvas c){
		super.onDraw(c);
		//anchor
		drawStroke(anchor.generateAnchor(), c, anchor.getAnchorPaint());
		drawStroke(anchor.generateLine(), c, anchor.getLinePaint());
		//red cross
		drawChunk(anchor.generateCrossLine(), c, anchor.getCrossLinePaint());
		
		
	}
	
	private void drawChunk(MultiStrokes chunk, Canvas c, Paint paint) {
		// TODO Auto-generated method stub
		for(Stroke stroke: chunk.chunk){
			if(stroke.stroke.size()!=0){
				drawStroke(stroke, c, paint);
			}
		}
	}

	private void drawStroke(Stroke stroke, Canvas c, Paint paint) {
		// TODO Auto-generated method stub
		if (stroke.size() > 0) {
			Point p0 = stroke.get(0);
			for (int i = 1; i < stroke.size(); i++) {
				Point p1 = stroke.get(i);
				
				c.drawLine(p0.x, p0.y, p1.x, p1.y, paint);
				
				p0 = p1;
			}
		}
	}
	
	private void startTimer(){
		timer = new Timer();
		timer.schedule(new TimerTask(){
			public void run(){
				
//				if (state == State.WRITING){				
//					notes.addChunk(currentChunk);
//				}else if(state == State.EDITING){
//					notes.updateChunk(chunkIndex,currentChunk);
//				}
//				//state = State.MAGNIFY;
//				notes.print();
//				if(currentChunk.chunk.size()!=0){
//					currentChunk.nextChunk();
//				}
//				anchor.setPoint(currentChunk.getStartPoint());
				postInvalidate();
			}
		}, activeTime);
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
	
	private buttons buttonClicked(Point point) {
		// TODO Auto-generated method stub
		int bSize = 100;
		if (point.x>viewWidth-bSize && point.y<bSize)
		{
			return buttons.MAGNIFY;
		}
		if (point.x>viewWidth-bSize && point.y>viewHeight-bSize){
			return buttons.ANCHOR_SET;
		}
		if (point.x>bSize && point.x<2*bSize && point.y>viewHeight-bSize){
			return buttons.NEW_CHUNK;
		}
		if (point.x<bSize && point.y>viewHeight-bSize){
			return buttons.NEW_LINE;
		}
		if (point.x<100 && point.y<100){
			return buttons.UNDO;
		}
		return buttons.NONE;
	}


}
