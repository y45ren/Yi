package com.example.note.Listener;

import com.example.note.MainActivity;
import com.example.note.R;
import com.example.note.component.MultiStrokes;
import com.example.note.view.ChunkLine;
import com.example.note.view.MagnifiedView;

import android.graphics.Point;
import android.view.View;
import android.view.View.OnClickListener;
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
		if (isChecked){
			mA.noteLayout.setBackgroundColor(0xffcdc9c8);
		}else{
			mA.noteLayout.setBackgroundColor(0);
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
			mA.inkRegion.get(regionIndex).addChunk(newChunk, pivotPoint, endPoint, scale, width);
			
			//move anchor
			mA.magnifiedView.anchor.moveX(width);
		if (v.getId()==R.id.newLine){
			mA.magnifiedView.computeSize();
			
			newChunk = new MultiStrokes();
			newChunk.copyChunk(mA.magnifiedView.largeStrokes);
			mA.inkRegion.get(regionIndex).addLine();		
			mA.magnifiedView.anchor.moveY(mA.inkRegion.get(regionIndex).chunkLine.get(mA.inkRegion.get(regionIndex).chunkLine.size()-2).params.width);
		}
		mA.magnifiedView.clear();
		mA.magnifiedView.invalidate();
		
	}

}
