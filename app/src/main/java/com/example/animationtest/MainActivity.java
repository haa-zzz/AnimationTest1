package com.example.animationtest;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView textView;
    private Button button;
    private final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button1:
                demo1();
                break;
            case R.id.button2:
                demo2();
                break;
            case R.id.button3:
                demo3();
                break;
            case R.id.button4:
                demo4();
                break;
            case R.id.button5:
                demo5();
                break;
            case R.id.button6:
                demo6();
                break;
        }
    }
    private void demo1() {
        //view动画

            /*
            1.使用XML格式
             */
             Animation animation = AnimationUtils.loadAnimation(this, R.anim.filename);
             textView.startAnimation(animation);

            /*
            //使用代码来写
            AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
            alphaAnimation.setDuration(2000);
            //设置监听器
            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                     Animation animation1= AnimationUtils.loadAnimation(MainActivity.this, R.anim.filename);
                     textView.startAnimation(animation1);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            textView.startAnimation(alphaAnimation);

             */

    }
    private void demo2() {
        //帧动画
        /*
        1、在res/drawable目录下定义一个XML文件，根节点为系统提供的animation-list，然后放入定义更好的图片；
        2.使用AnimationDrawable类播放第一步定义好的Drawable中的图片，形成动画效果；
         */
        button.setBackgroundResource(R.drawable.frame_animation);
        AnimationDrawable drawable = (AnimationDrawable) button.getBackground();
        drawable.start();
        //注意：在使用帧动画是应该尽量避免使用过多尺寸较大的图片
    }
    private void demo3() {
        //属性动画ValueAnimator使用，看打印效果
        ValueAnimator anim = ValueAnimator.ofInt(0, 100);
        anim.setDuration(300);
        anim.addUpdateListener(animation -> {
            int currentValue = (int) animation.getAnimatedValue();
            Log.d(TAG, "cuurent value is " + currentValue);
        });
        anim.addListener(new AnimatorListenerAdapter() {
        });
        anim.start();
    }

    private void demo4() {
        //属性动画的使用
        //第一种：改变一个对象的translationX/translationY,实现平移
        ObjectAnimator animator1 =  ObjectAnimator.ofFloat(textView,"translationX",0,1000F,0);
        animator1.setDuration(5000);
        animator1.setInterpolator(new LinearInterpolator());
        animator1.start();


        //第二种：改变一个对象的背景属于,典型是改变VIew的背景色
        ValueAnimator animator2 = ObjectAnimator.ofInt(textView,"backgroundColor",
                0xFFFF8080,0xFF8080FF);
        animator2.setDuration(3000);        //设置持续时间
        animator2.setEvaluator(new ArgbEvaluator());
        animator2.setRepeatCount(ValueAnimator.INFINITE);       //设置动画循环播放的次数,这里是无限循环
        //设置循环播放的模式,循环模式包括RESTART和REVERSE两种，分别表示重新播放和倒序播放的意思
        animator2.setRepeatMode(ValueAnimator.REVERSE);
        //animator2.start();


        //第三种，动画集合，5秒内对View的旋转，平移，缩放，和透明度进行改变
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(textView,"rotationX",0,360,0),     //在水平方向进行旋转
                ObjectAnimator.ofFloat(textView,"rotationY",0,180,0),     //在垂直方向进行旋转

                ObjectAnimator.ofFloat(textView,"rotation",0,-90,0),      //旋转

                ObjectAnimator.ofFloat(textView,"translationX",0,90,0),   //在水平方向平移
                ObjectAnimator.ofFloat(textView,"translationY",0,90,0),   //在垂直方向平移

                ObjectAnimator.ofFloat(textView,"scaleX",1,1.5f,1),       //在水平方向上进行缩放
                ObjectAnimator.ofFloat(textView,"scaleY",0,0.5f,1),       //在垂直方向上进行缩放

                ObjectAnimator.ofFloat(textView,"alpha",1,0.25f,1)      //透明度
        );
       // set.setDuration(5 * 1000).start();

        //第四种，xml
        Animator animator = AnimatorInflater.loadAnimator(MainActivity.this,R.animator.anim1);
        animator.setTarget(textView);
        animator.start();
        
    }

    private void demo5() {
        //属性动画的高级使用，处理width,s使button宽变大
        /*
        第一种：用一个类来包装原始对象，间接提供get和set方法
         */
        //ObjectAnimator.ofInt(new ViewWrapper(button),"width",500).setDuration(5000).start();
        /*
        第二种：采用ValueAnimator，监听动画过程，自己实现属性的改变
         */
        performAnimate(button,button.getLayoutParams().width,500);

    }

    private void performAnimate(View view, int start, int end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1,100);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            private IntEvaluator intEvaluator = new IntEvaluator();
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //获取当前动画的进度值
                int currentValue = (Integer) animation.getAnimatedValue();

                //获取当前动画占整个动画的比例
                float fraction = animation.getAnimatedFraction();
                //调用整型估值器，通过比例算出宽度，然后设给View,当然这里也可直接自己算
                view.getLayoutParams().width = intEvaluator.evaluate(fraction,start,end);
                view.requestLayout();
            }
        });
        valueAnimator.setDuration(5000).start();
    }

    private void demo6() {
        //自定义View,
        MyAnimView myAnimView = findViewById(R.id.myAnimView);
        myAnimView.setVisibility(View.VISIBLE);


    }
}