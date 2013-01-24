package com.example.note.Listener;

import com.example.note.MainActivity;
import com.example.note.component.MultiStrokes;
import com.example.note.view.MagnifiedView;

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
		newChunk.copyChunk(mA.magnifiedView.largeStrokes);
		newChunk.transform(mA.magnifiedView.maxX, mA.magnifiedView.maxY, mA.magnifiedView.minX, mA.magnifiedView.minY, mA.inkRegion.get(mA.inkRegion.size()-1).getRegionHeight());
		mA.inkRegion.get(mA.inkRegion.size()-1).addChunk(newChunk);
		
		mA.magnifiedView.clear();
		mA.magnifiedView.invalidate();
	}

}
