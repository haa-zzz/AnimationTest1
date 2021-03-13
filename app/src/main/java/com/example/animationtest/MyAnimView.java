package com.example.animationtest;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

import androidx.annotation.Nullable;

/**
 * 实现动画画圆
 */
public class MyAnimView extends View {
    public static final float RADIUS = 80f;
    private Point currentPoint;
    private Paint mPaint;

    public MyAnimView(Context context) {
        super(context);
        init();
    }

    public MyAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        int mColor = Color.RED;
        mPaint.setColor(mColor);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (currentPoint == null) {
            currentPoint = new Point(RADIUS, RADIUS);
            drawCircle(canvas);
            startAnimation();
        } else {
            drawCircle(canvas);
        }
        
    }

    private void drawCircle(Canvas canvas) {
        //画圆
        float x = currentPoint.getX();
        float y = currentPoint.getY();
        canvas.drawCircle(x, y, RADIUS, mPaint);        //画圆x,y为圆心

    }
    private void startAnimation() {
        /*
        这里我们定义了一个startPoint和一个endPoint，坐标分别是View的左上角和右下角，并将动画的时长设为5秒。
        然后通过监听器对动画的过程进行了监听，每当Point值有改变的时候都会回调onAnimationUpdate()方法。
        在这个方法当中，我们对currentPoint对象进行了重新赋值，并调用了invalidate()方法，这样的话onDraw()方法就会重新调用，
        并且由于currentPoint对象的坐标已经改变了，那么绘制的位置也会改变，于是一个平移的动画效果也就实现了。
         */
        /*
        //从左上到有下
        Point startPoint = new Point(RADIUS, RADIUS);
        Point endPoint = new Point(getWidth() - RADIUS, getHeight() - RADIUS);
         */
        //垂直下落
        Point startPoint = new Point(getWidth() / 2, RADIUS);
        Point endPoint = new Point(getWidth() / 2, getHeight() - RADIUS);
        ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
        anim.addUpdateListener(animation -> {
            currentPoint = (Point) animation.getAnimatedValue();
            invalidate();
        });
        anim.setInterpolator(new BounceInterpolator());
        anim.setDuration(3000);
        anim.start();

    }


}
