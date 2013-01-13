package com.example.note.component;

import com.example.note.component.anchor.Anchor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;


public class CanvasView extends View{
	
	/**
	 * components
	 */
	private static Anchor anchor;
	
	/**
	 * attributes
	 */
	private int viewHeight;
	private int viewWidth;
	
	
	public CanvasView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setBackgroundColor(Color.RED);
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

	@Override
	public boolean onTouchEvent(MotionEvent event){
		Point eventPoint = new Point((int)event.getX(),(int)event.getY());
		System.out.println("CanvasView: "+eventPoint);
		return true;
		
	}
	
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


}
