package com.example.note.component;

import com.example.note.component.anchor.Anchor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;


public class CanvasView extends View{

	private static Anchor anchor;
	public CanvasView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		setBackgroundColor(Color.RED);
		//setVisibility(INVISIBLE);
	}
	

	@Override
	public boolean onTouchEvent(MotionEvent event){
		Point eventPoint = new Point((int)event.getX(),(int)event.getY());
		System.out.println("CanvasView: "+eventPoint);
		return true;
		
	}
	
	

}
