package com.example.note.Listener;

import com.example.note.MainActivity;
import com.example.note.R;
import com.example.note.component.MultiStrokes;
import com.example.note.view.ChunkLine;
import com.example.note.view.MagnifiedView;

import android.graphics.Point;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.*;

public class Listener implements CompoundButton.OnCheckedChangeListener, OnClickListener{
	
	MainActivity mA;
	MultiStrokes newChunk;
	
	/**
	 * animation 
	 */
	private Animation anchorBlink;
	
	
	public Listener(MainActivity mainActivity) {
		// TODO Auto-generated constructor stub
		mA = mainActivity;
		newChunk = new MultiStrokes();
		
		//animate anchor:
        anchorBlink = new AlphaAnimation(1,0);
        anchorBlink.setDuration(500);
        anchorBlink.setRepeatCount(Animation.INFINITE);
        anchorBlink.setRepeatMode(Animation.REVERSE);
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if (isChecked){
//			mA.noteLayout.setBackgroundColor(0xffcdc9c8);
			mA.anchorView.setAnimation(this.anchorBlink);
			mA.next.setClickable(true);
			mA.newLine.setClickable(true);
		}else{
//			mA.noteLayout.setBackgroundColor(0);
			mA.anchorView.clearAnimation();
			mA.next.setClickable(false);
			mA.newLine.setClickable(false);
		}
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
			mA.magnifiedView.computeSize();
			newChunk = new MultiStrokes();
			newChunk.copyChunk(mA.magnifiedView.largeStrokes);
			int regionIndex = mA.inkRegion.size()-1;
			Point pivotPoint = new Point(mA.magnifiedView.minX, mA.magnifiedView.minY);
			Point endPoint = new Point(mA.magnifiedView.maxX, mA.magnifiedView.maxY);
			double scale = (double)mA.inkRegion.get(regionIndex).getLineHeight() / (double)(mA.magnifiedView.maxY - mA.magnifiedView.minY);
			int width = (int) (scale * (mA.magnifiedView.maxX - mA.magnifiedView.minX));
			//System.out.println(width+" sd  "+ (mA.magnifiedView.maxY - mA.magnifiedView.minY)+"  scale:"+scale);
			if (mA.magnifiedView.largeStrokes.chunk.size()!=0){
				mA.inkRegion.get(regionIndex).addChunk(newChunk, pivotPoint, endPoint, scale, width);
				mA.anchorView.anchor.moveX(width);
			}
			
			//move anchor
			
		if (v.getId()==R.id.newLine){
			mA.magnifiedView.computeSize();
			
			newChunk = new MultiStrokes();
			newChunk.copyChunk(mA.magnifiedView.largeStrokes);
			mA.inkRegion.get(regionIndex).addLine();
			int chunkIndex = mA.inkRegion.get(regionIndex).chunkLine.size();

			mA.anchorView.anchor.moveY(mA.inkRegion.get(regionIndex).chunkLine.get(chunkIndex-2).params.width);
		}
		mA.magnifiedView.clear();
		mA.anchorView.invalidate();
		mA.magnifiedView.invalidate();		
	}

}
