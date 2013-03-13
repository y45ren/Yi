package com.example.note.component.anchor;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.SensorManager;
import java.math.*;

import com.example.note.component.MultiStrokes;
import com.example.note.component.MyPaint;
import com.example.note.component.Stroke;

public class Anchor {
	private Point startPoint;
	private float angle;  //from -pi to pi
	private float wantedAngle;	
	private float[] rotate={1,0,0,0,1,0,0,0,1};
	private OneEuroFilter filter;
	private Paint anchorPaint;
	private Paint linePaint;
	private Paint crossLinePaint;
	private int height;
	private int width;
	private int anchorLen = 20;
	private int lineLen = 150;
	final int anchorRange = 40;
	private boolean redCross = false;

	public Anchor(Point point) {
		// TODO Auto-generated constructor stub
		startPoint = new Point(point.x, point.y);
		setAnchorPaint(MyPaint.createPaint(Color.BLUE, 8));
		setLinePaint(MyPaint.createPaint(Color.BLUE, 3));
		setCrossLinePaint(MyPaint.createPaint(Color.RED, 2));
		setAngle(0);
		setWantedAngle(0);
        double frequency = 40.3; // Hz
        double mincutoff = 1.0; // FIXME
        double beta = 1.0;      // FIXME
        double dcutoff = 1.0;   // this one should be ok
		try {
			filter = new OneEuroFilter(frequency,
			        mincutoff,
			        beta,
			        dcutoff);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setHeight(0);
		this.setWidth(0);
	}


	public void setPoint(Point point){
		this.startPoint.set(point.x, point.y);
	}
	
	public Point getPoint(){
		return this.startPoint;
	}


	public Stroke generateAnchor(){
		Stroke stroke = new Stroke();
		stroke.addPoint(rotatePoint((new Point(startPoint.x, startPoint.y))));
		stroke.addPoint(rotatePoint((new Point(startPoint.x, startPoint.y-2*getAnchorLen()))));
		return stroke;
	}
	public Stroke generateLine(){
		Stroke stroke = new Stroke();
		stroke.addPoint(rotatePoint(new Point(startPoint.x, startPoint.y)));
		stroke.addPoint(rotatePoint(new Point(startPoint.x+lineLen, startPoint.y)));

		return stroke;
	}
	public MultiStrokes generateCrossLine(){
		MultiStrokes multiStrokes = new MultiStrokes();
		double tan=Math.tan(getAngle());
		multiStrokes.addStroke(new Point((int) (startPoint.x+startPoint.y*tan),0));
		multiStrokes.addPoint(new Point((int) (startPoint.x-(getHeight()-startPoint.y)*tan),getHeight()));
		multiStrokes.addStroke(new Point(0, (int) (startPoint.y-startPoint.x*tan)));
		multiStrokes.addPoint(new Point(getWidth(), (int) (startPoint.y+(getWidth()-startPoint.x)*tan)));
		return multiStrokes;
	}
	
	public void draw(Canvas c){
		if (redCross){
			for(Stroke stroke: generateCrossLine().chunk){
				if(stroke.stroke.size()!=0){
					stroke.drawOld(c, crossLinePaint);
				}
			}
		}
		
		generateAnchor().drawOld(c, anchorPaint);
		generateLine().drawOld(c, linePaint);
	}
	
	/**
	 * @param rotate the rotate to set
	 */
	public void setRotate(float[] rotate) {
		try {
			//setAngle((float) filter.filter(Math.atan2(rotate[0], rotate[1])));
			setAngle((float) filter.filter(-Math.atan2(rotate[1], rotate[0])));
			if (Math.abs(getAngle()-getWantedAngle())<=0.175){
				setAngle(getWantedAngle());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("filter exception");
		}
	}
	
	private Point rotatePoint(Point point){
		int x=point.x-startPoint.x;
		int y=point.y-startPoint.y;
		float sin = (float) Math.sin(getAngle());
		float cos = (float) Math.cos(getAngle());
		Point tempPoint = new Point((int)(x*cos-y*sin)+startPoint.x, (int)(x*(sin)+y*cos)+startPoint.y);
		return tempPoint;		
	}

	/**
	 * @return the anchorPaint
	 */
	public Paint getAnchorPaint() {
		return anchorPaint;
	}

	/**
	 * @param anchorPaint the anchorPaint to set
	 */
	public void setAnchorPaint(Paint anchorPaint) {
		this.anchorPaint = anchorPaint;
	}

	/**
	 * @return the linePaint
	 */
	public Paint getLinePaint() {
		return linePaint;
	}

	/**
	 * @param linePaint the linePaint to set
	 */
	public void setLinePaint(Paint linePaint) {
		this.linePaint = linePaint;
	}

	/**
	 * @return the crossLinePaint
	 */
	public Paint getCrossLinePaint() {
		return crossLinePaint;
	}

	/**
	 * @param crossLinePaint the crossLinePaint to set
	 */
	public void setCrossLinePaint(Paint crossLinePaint) {
		this.crossLinePaint = crossLinePaint;  
	}

	/**
	 * @return the rotate
	 */
	public float[] getRotate() {
		return rotate;
	}

	/**
	 * @return the angle
	 */
	public float getAngle() {
		return angle;
	}
	/**
	 * @return the angle in degree
	 */
	public float getAngleInDegrees() {
		return (float) Math.toDegrees(angle);
	}
	/**
	 * @param angle the angle to set
	 */
	public void setAngle(float angle) {
		this.angle = angle;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param eventPoint the height to set
	 */
	public void setAnchorLen(Point eventPoint) {
		
//		Point a = rotatePoint((new Point(startPoint.x, startPoint.y-getAnchorLen())));
//		Point b = rotatePoint((new Point(startPoint.x, startPoint.y+getAnchorLen())));
//		this.anchorLen = ((b.x-a.x)*(eventPoint.x-a.x)+(b.y-a.y)*(eventPoint.y-a.y));
//		System.out.println("ANCHORLEN: "+ ((b.x-a.x)*(eventPoint.x-a.x)+(b.y-a.y)*(eventPoint.y-a.y)));
		int newLen = (int) Math.sqrt(Math.pow((eventPoint.x-startPoint.x),2)+Math.pow((eventPoint.y-startPoint.y),2));
		this.lineLen = (int) ((double) newLen/ (double) this.anchorLen * this.lineLen);
		this.anchorLen = newLen;
//		System.out.println("ANCHORLEN: "+this.anchorLen);
		
	}
	
	public void setHeight(int i) {
		// TODO Auto-generated method stub
		this.height = i;
	}
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	public float getWantedAngle() {
		return wantedAngle;
	}

	public void setWantedAngle(float wantedAngle) {
		this.wantedAngle = wantedAngle;
	}
	
	public boolean onAnchor(Point point){
		if (Math.sqrt(Math.pow((point.x-startPoint.x), 2)+Math.pow(point.y-startPoint.y, 2))<anchorRange){
			System.out.println("On Anchor right now!!");
			return true;
		}
		return false;
	}

	/**
	 * @return the anchorLen
	 */
	public int getAnchorLen() {
		return anchorLen;
	}

	/**
	 * @param anchorLen the anchorLen to set
	 */
	public void setAnchorLen(int anchorLen) {
		this.anchorLen = anchorLen;
	}

	public void moveX(int width) {
		// TODO Auto-generated method stub
		this.startPoint.offset((int)(width*Math.cos(this.angle)), (int)(width*Math.sin(this.angle)));
	}

	public void moveY(int width) {
		// TODO Auto-generated method stub
		moveX(-width);
		System.out.println(width);
		this.startPoint.offset(-(int)(this.anchorLen*2*Math.sin(this.angle)), (int)(this.anchorLen*2*Math.cos(this.angle)));
	}


	public void setRedCross(boolean b) {
		// TODO Auto-generated method stub
		this.redCross = b;
//		System.out.println("adsfadsfdsf"+this.redCross);
	}
}
