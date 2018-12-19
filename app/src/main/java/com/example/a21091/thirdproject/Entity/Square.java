package com.example.a21091.thirdproject.Entity;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.animation.Animation;

import java.io.Serializable;

public class Square extends Figure implements Serializable {

    //private int r;

    public Square(float x, float y) {

        super();
        this.x = (int)x;
        this.y = (int)y;
        r = 100;
    }

    @Override
    public void draw(Canvas canvas) {

        if(pen==null&&selectedPen==null){
            penInit();
        }

        Rect r = new Rect();

        r.set(this.x-this.r, this.y-this.r, this.x+this.r, this.y+this.r);
        canvas.drawRect(r, pen);

        if (selected){
            canvas.drawRect(r, selectedPen);
        }
    }

    @Override
    public boolean onArea(float x, float y) {

        if((x>=this.x-this.r && x<=this.x+this.r) && (y>=this.y-this.r && y<=this.y+this.r)){
            return true;
        }

        return false;
    }

    @Override
    public void startAnimation(Canvas canvas) {

    }

    @Override
    public void setCoordinates(float x, float y) {

        this.x = (int) x;
        this.y = (int) y;
    }

    @Override
    public void setScale(float scale) {

        if(this.r*scale > 50 && this.r*scale < 300){
            this.r*=scale;
        }
    }
}