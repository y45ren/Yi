package com.example.note.component;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.MotionEvent;
import android.widget.TextView;

public class TestTextView extends TextView{

	public TestTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.save();
	    canvas.rotate(0,0,0);
	    super.onDraw(canvas);
	    canvas.restore();
	}
	
//	@Override
//	public boolean onTouchEvent(MotionEvent event){
//		Point eventPoint = new Point((int)event.getX(),(int)event.getY());
//		System.out.println("testTextView: "+eventPoint);
//		return true;
//		
//	}

}
