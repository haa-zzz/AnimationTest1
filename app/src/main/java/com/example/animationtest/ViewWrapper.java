package com.example.animationtest;

import android.view.View;

public class ViewWrapper {
    private View mTarget;

    public ViewWrapper(View mTarget) {
        this.mTarget = mTarget;
    }
    public int getWidth(){
        return mTarget.getLayoutParams().width;
    }
    public void setWidth(int width){
        mTarget.getLayoutParams().width = width;
        mTarget.requestLayout();
    }
}
