package com.example.note.component;

import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Cap;

public class MyPaint {
	public static Paint createPaint(int color, float width){
		Paint temp = new Paint();
		temp.setStyle(Paint.Style.STROKE);
		temp.setAntiAlias(true);
		temp.setColor(color);
		temp.setStrokeWidth(width);
		temp.setStrokeCap(Cap.ROUND);

		temp.setDither(true);
		temp.setStrokeJoin(Paint.Join.ROUND);
		temp.setPathEffect(new CornerPathEffect(100));
		  
		return temp;
	}

}
