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
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.*;

public class MainActivity extends Activity {
	
	private ArrayList<InkRegion> inkRegion;
	private CanvasView canvasView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        RelativeLayout noteLayout = (RelativeLayout)findViewById(R.id.notePanel);

        canvasView = new CanvasView(this);
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
    	
		return false;
    
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
