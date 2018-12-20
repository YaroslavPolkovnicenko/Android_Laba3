package com.example.a21091.thirdproject.Entity;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.animation.Animation;

import com.example.a21091.thirdproject.DrawView;

import java.io.Serializable;

public class Square extends Figure implements Serializable {

    private transient Path pathFigure;
    private transient Path pathBorder;
    private transient Matrix matrix;

    private int dir = 0;

    public Square(float x, float y) {

        super();
        this.x = (int)x;
        this.y = (int)y;
        r = 100;
        dir=0;
        flag=false;
    }

    @Override
    public void draw(Canvas canvas) {

        pathFigure = new Path();
        pathBorder = new Path();
        Matrix matrix = new Matrix();

        Rect rect = new Rect();

        if(pen==null&&selectedPen==null){
            penInit();
        }

        if(flag) {

            if (dir == 0) {
                if (r > 200) {
                    dir = 1;
                }
                r += 1;
                rect.set(this.x-this.r, this.y-this.r, this.x+this.r, this.y+this.r);
                canvas.drawRect(rect, pen);
            } else {
                if (r == 100 && flag == true) {
                    dir = 0;
                    r = 100;
                    flag = false;
                }
                r -= 1;
                rect.set(this.x-this.r, this.y-this.r, this.x+this.r, this.y+this.r);
                canvas.drawRect(rect, pen);
            }
            pathFigure.transform(matrix);
            pathBorder.transform(matrix);
        }

        rect.set(this.x-this.r, this.y-this.r, this.x+this.r, this.y+this.r);
        canvas.drawRect(rect, pen);

        if (selected){
            canvas.drawRect(rect, selectedPen);
        }
        DrawView.getInstance().invalidate();
    }

    @Override
    public boolean onArea(float x, float y) {

        if((x>=this.x-this.r && x<=this.x+this.r) && (y>=this.y-this.r && y<=this.y+this.r)){
            return true;
        }

        return false;
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