package com.example.note.Listener;

import com.example.note.MainActivity;
import com.example.note.R;
import com.example.note.component.MultiStrokes;
import com.example.note.component.Status;
import com.example.note.view.ChunkLine;
import com.example.note.view.InkRegion;
import com.example.note.view.MagnifiedView;

import android.content.Context;
import android.graphics.Point;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.*;

public class Listener implements CompoundButton.OnCheckedChangeListener, OnClickListener{
	
	MainActivity mA;
	MultiStrokes newChunk;
	
	public Listener(MainActivity mainActivity) {
		// TODO Auto-generated constructor stub
		mA = mainActivity;
		newChunk = new MultiStrokes();
		

        
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		try{
			mA.timer.cancel();
			mA.timer.purge();
		}catch(Exception e){
		
		}
		
//		mA.sm.registerListener(mA.rotationSensorListener, mA.aSensor, SensorManager.SENSOR_DELAY_GAME);
//		System.out.println(mA.orientation);
//		mA.sm.unregisterListener(mA.rotationSensorListener);
//		WindowManager mWindowManager =  (WindowManager) mA.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
//	    Display mDisplay = mWindowManager.getDefaultDisplay();
//	    System.out.println("~~~~~"+mDisplay.getRotation());
	    
		if (isChecked){
//			mA.noteLayout.setBackgroundColor(0xffcdc9c8);
			mA.anchorView.startAnimation();
			mA.next.setClickable(true);
			mA.newLine.setClickable(true);
		}else{
//			mA.noteLayout.setBackgroundColor(0);
			mA.anchorView.pauseAnimation();
			mA.magnifiedView.clear();
			mA.next.setClickable(false);
			mA.newLine.setClickable(false);
		}
		
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		try{
			mA.timer.cancel();
			mA.timer.purge();
		}catch(Exception e){
			
		}
		if (v.getId()==R.id.next){
			mA.magnifiedView.computeSize();
			newChunk = new MultiStrokes();
			newChunk.copyChunk(mA.magnifiedView.largeStrokes);
			int regionIndex = mA.inkRegion.size()-1;
			Point pivotPoint = new Point(mA.magnifiedView.minX, mA.magnifiedView.minY);
			Point endPoint = new Point(mA.magnifiedView.maxX, mA.magnifiedView.maxY);
			//double scale = (double)mA.inkRegion.get(regionIndex).getLineHeight() / (double)(mA.magnifiedView.maxY - mA.magnifiedView.minY);
			double scale = (double)mA.inkRegion.peekLast().getLineHeight() / (double)400;
			System.out.println("SCALE: "+scale);
			int width = (int) (scale * (mA.magnifiedView.maxX - mA.magnifiedView.minX));
			//System.out.println(width+" sd  "+ (mA.magnifiedView.maxY - mA.magnifiedView.minY)+"  scale:"+scale);
			if (mA.magnifiedView.largeStrokes.chunk.size()!=0){
				mA.inkRegion.get(regionIndex).addChunk(newChunk, pivotPoint, endPoint, scale, width);

				mA.anchorView.anchor.setPoint(mA.inkRegion.peekLast().generateLastPosition());
				//mA.anchorView.anchor.moveX(width);
			}
			System.out.println("!!!"+mA.anchorView.anchor.getPoint()+",  "+mA.inkRegion.peekLast().generateLastPosition());
			System.out.println("!!!"+mA.inkRegion.peekLast().chunkLine.size()+", "+mA.inkRegion.peekLast().chunkLine.peekLast().chunkFrame.size());
			mA.magnifiedView.clear();
			mA.anchorView.invalidate();
			mA.magnifiedView.invalidate();	
		}
			
		
			
		if (v.getId()==R.id.newLine){
			mA.magnifiedView.computeSize();
			newChunk = new MultiStrokes();
			newChunk.copyChunk(mA.magnifiedView.largeStrokes);
			int regionIndex = mA.inkRegion.size()-1;
			Point pivotPoint = new Point(mA.magnifiedView.minX, mA.magnifiedView.minY);
			Point endPoint = new Point(mA.magnifiedView.maxX, mA.magnifiedView.maxY);
//			double scale = (double)mA.inkRegion.get(regionIndex).getLineHeight() / (double)(mA.magnifiedView.maxY - mA.magnifiedView.minY);
			double scale = (double)mA.inkRegion.peekLast().getLineHeight() / (double)400;
			int width = (int) (scale * (mA.magnifiedView.maxX - mA.magnifiedView.minX));
			//System.out.println(width+" sd  "+ (mA.magnifiedView.maxY - mA.magnifiedView.minY)+"  scale:"+scale);
			if (mA.magnifiedView.largeStrokes.chunk.size()!=0){
				mA.inkRegion.get(regionIndex).addChunk(newChunk, pivotPoint, endPoint, scale, width);
				//mA.anchorView.anchor.setPoint(mA.inkRegion.peekLast().generateLastPosition());
			}
			
			
			mA.magnifiedView.computeSize();
			
			newChunk = new MultiStrokes();
			newChunk.copyChunk(mA.magnifiedView.largeStrokes);
			mA.inkRegion.get(regionIndex).addLine();
			int chunkIndex = mA.inkRegion.get(regionIndex).chunkLine.size();

			mA.anchorView.anchor.setPoint(mA.inkRegion.peekLast().generateLastPosition());
			
			mA.magnifiedView.clear();
			mA.anchorView.invalidate();
			mA.magnifiedView.invalidate();
		}
		
		if (v.getId()==R.id.undo){
			//in magnified mod
			
			if (mA.switchy.isChecked()){
				//not crrently drawing 
				if (mA.magnifiedView.largeStrokes.isEmpty()){
					try{
						mA.inkRegion.peekLast().undo();
						mA.anchorView.anchor.setPoint(mA.inkRegion.peekLast().generateLastPosition());
						mA.anchorView.invalidate();
						mA.magnifiedView.invalidate();	
					}catch(Exception e){
						
					}
				}
				//writing
				else{
					mA.magnifiedView.undo();
				}
			}//non magnified mod
			else{
				mA.canvasView.undo();
			}
			
		}
		
		if (v.getId()==R.id.erase){
			for (InkRegion inkRegion : mA.inkRegion){
				mA.noteLayout.removeView(inkRegion);
			}
			mA.inkRegion.clear();
			mA.canvasView.largeStrokes.clear();
			mA.magnifiedView.clear();
			//
			mA.canvasView.invalidate();
			mA.anchorView.invalidate();
			mA.magnifiedView.invalidate();
			mA.initiateInkRegion();
		}
			
	}

}
