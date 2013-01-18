package com.example.note.component;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class InkRegion extends LinearLayout{
	private int UID;
	private int width;
	private int height;
	
	private ArrayList<ChunkLine> chunkLine;
	
	public InkRegion(Context context) {
		super(context);
		this.setOrientation(VERTICAL);
		chunkLine = new ArrayList<ChunkLine>();
		chunkLine.add(new ChunkLine(context));
		chunkLine.add(new ChunkLine(context));
		LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		LayoutParams params1 = new LinearLayout.LayoutParams(
                80,40);
		this.addView(chunkLine.get(0),params);
		this.addView(chunkLine.get(1),params);
               
	}
	
	public InkRegion(Context context, int UID) {
		super(context);
		this.UID = UID;
		this.setOrientation(VERTICAL);
		chunkLine = new ArrayList<ChunkLine>();
		chunkLine.add(new ChunkLine(context));
		chunkLine.add(new ChunkLine(context));
		LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		LayoutParams params1 = new LinearLayout.LayoutParams(
                80,40);
		this.addView(chunkLine.get(0),params);
		this.addView(chunkLine.get(1),params);
               
	}


	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		this.width = this.getWidth();
		this.height = this.getHeight();
	}
	
	private void populateText(LinearLayout  noteLayout, View[] views , Context mContext) { 
        noteLayout.removeAllViews();
        
        int maxWidth = width - 20;
        System.out.println("KKKK: "+maxWidth);
        LinearLayout.LayoutParams params;
        LinearLayout newLL = new LinearLayout(mContext);
        newLL.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
        newLL.setGravity(Gravity.LEFT);
        newLL.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout LL;
        int widthSoFar = 0;

        for (int i = 0 ; i < views.length ; i++ ){
            LL = new LinearLayout(mContext);
            LL.setOrientation(LinearLayout.HORIZONTAL);
            LL.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
            LL.setLayoutParams(new ListView.LayoutParams(
                    LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            //my old code
            TextView TV = new TextView(mContext);
            TV.setText("faa");
            
            TV.measure(0, 0);
            views[i].measure(0,0);
            params = new LinearLayout.LayoutParams(TV.getMeasuredWidth(),
                    LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 0, 5, 0);  // YOU CAN USE THIS
            //LL.addView(TV, params);
            LL.addView(views[i], params);
            LL.measure(0, 0);
            widthSoFar += views[i].getMeasuredWidth();// YOU MAY NEED TO ADD THE MARGINS
            if (widthSoFar >= maxWidth) {
            	System.out.println("i: "+i+" newLine");
                noteLayout.addView(newLL);

                newLL = new LinearLayout(mContext);
                newLL.setLayoutParams(new LayoutParams(
                        LayoutParams.FILL_PARENT,
                        LayoutParams.WRAP_CONTENT));
                newLL.setOrientation(LinearLayout.HORIZONTAL);
                newLL.setGravity(Gravity.LEFT);
                params = new LinearLayout.LayoutParams(LL
                		.getMeasuredWidth(), LL.getMeasuredHeight());
                newLL.addView(LL, params);
                widthSoFar = LL.getMeasuredWidth();
            } else {
            	System.out.println("i: "+i+" newWord");
                newLL.addView(LL);
            }
            
        }
        noteLayout.addView(newLL);
    }
    
	public boolean onTouchEvent(MotionEvent event){
		Point eventPoint = new Point((int)event.getX(),(int)event.getY());
		System.out.println("InkRegion  "+this.UID+" : "+eventPoint);
		return false;
		
	}
	
}
