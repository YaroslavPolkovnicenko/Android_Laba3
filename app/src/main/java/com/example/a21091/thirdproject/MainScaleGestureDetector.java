package com.example.a21091.thirdproject;

import android.util.Log;
import android.view.ScaleGestureDetector;

public class MainScaleGestureDetector extends android.view.ScaleGestureDetector.SimpleOnScaleGestureListener {

    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        Log.i("scale", Float.toString(scaleGestureDetector.getScaleFactor()));
    }
}