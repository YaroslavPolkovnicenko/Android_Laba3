package com.example.a21091.thirdproject.Entity;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.animation.Animation;

import java.io.Serializable;

public class Circle extends Figure implements Serializable{

    //private int r=100;
    private transient Path pathFigure;
    private transient Path pathBorder;
    private transient Matrix matrix;

    public Circle(float x, float y) {

        super();
        this.x = (int)x;
        this.y = (int)y;
        r = 100;
        Matrix matrix = new Matrix();
    }

    public Circle() {
        super();
    }

    @Override
    public void draw(Canvas canvas) {

        if(pen==null&&selectedPen==null){
            penInit();
        }

        canvas.drawCircle(this.x, this.y , this.r, pen);

        if (selected){
            Rect r = new Rect();

            r.set(this.x-this.r, this.y-this.r, this.x+this.r, this.y+this.r);
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