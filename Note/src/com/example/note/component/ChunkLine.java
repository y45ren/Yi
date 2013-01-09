package com.example.note.component;

import java.util.ArrayList;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class ChunkLine extends LinearLayout{

	private ArrayList<ChunkView> chunkView;
	
	public ChunkLine(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.setOrientation(HORIZONTAL);
		chunkView = new ArrayList<ChunkView>();
		chunkView.add(new ChunkView(context));
		chunkView.add(new ChunkView(context));
		
		LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		LayoutParams params1 = new LinearLayout.LayoutParams(
                80,40);
		this.addView(chunkView.get(0),params1);
	
		this.addView(chunkView.get(1),params1);
	}

}
