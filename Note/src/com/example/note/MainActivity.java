package com.example.note;

import java.util.ArrayList;

import com.example.note.component.CanvasView;
import com.example.note.component.InkRegion;
import com.example.note.component.TestTextView;

import android.os.Bundle;
import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.*;

public class MainActivity extends Activity implements CompoundButton.OnCheckedChangeListener{
	
	private ArrayList<InkRegion> inkRegion;
	private CanvasView canvasView;
	private Switch switchy;
	private RelativeLayout noteLayout;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Open app in fullscreen mode
    	super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        
        
        noteLayout = (RelativeLayout)findViewById(R.id.notePanel);
        
        canvasView = new CanvasView(this);
        switchy = new Switch(this);
        switchy = (Switch)findViewById(R.id.switch1);
        switchy.setOnCheckedChangeListener(this);
        
        
        inkRegion = new ArrayList<InkRegion>();
        inkRegion.add(new InkRegion(this,0));
        inkRegion.add(new InkRegion(this,1));
        inkRegion.get(0).setBackgroundColor(Color.YELLOW);
        inkRegion.get(1).setBackgroundColor(Color.CYAN);
        
        //noteLayout.addView(canvasView);
        noteLayout.addView(canvasView);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(200, 200);
        params.leftMargin = 0;
        params.topMargin = 200;
        noteLayout.addView(inkRegion.get(0),params);
        
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(200, 200);
        params1.leftMargin = 0;
        params1.topMargin = 0;
        noteLayout.addView(inkRegion.get(1), params1);

        // Create an animation instance
        Animation an = new RotateAnimation(45.0f, 45.0f, 0, 0);
        // Set the animation's parameters
        an.setDuration(0);               // duration in ms
        an.setRepeatCount(0);                // -1 = infinite repeated
        an.setFillAfter(true);               // keep rotation after animation     
        // Apply animation to image view
        inkRegion.get(0).setAnimation(an);
    }
    
    
    @Override
    public boolean onTouchEvent(MotionEvent event){
    	Point eventPoint = new Point((int)event.getX(),(int)event.getY());
    	switch(event.getActionMasked()){
		case MotionEvent.ACTION_DOWN:
			canvasView.largeStrokes.addStroke(eventPoint);
			canvasView.invalidate();			
			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		case MotionEvent.ACTION_MOVE:
			canvasView.largeStrokes.addPoint(eventPoint);	
			canvasView.invalidate();
			break;
		case MotionEvent.ACTION_UP:
			canvasView.invalidate();
			
			break;
		default:
			break;
		}
    	
		return true;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if (isChecked){
			noteLayout.setBackgroundColor(0xffcdc9c8);
		}else{
			noteLayout.setBackgroundColor(0);
		}
	}
}
