package com.example.a21091.thirdproject.Entity;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.view.animation.Animation;

import java.io.Serializable;

public class Triangle extends Figure implements Serializable {

    private int l;
    private int size = 200;
    private transient Path pathFigure;
    private transient Path pathBorder;
    private transient Matrix matrix;

    Paint paint = new Paint();
    public Triangle(float x, float y) {

        super();
        this.x=(int)x;
        this.y=(int)y;
        this.l = this.size/2;
        r=this.size/2;
        //r=100;
        reinit();
    }

    private void reinit(){

        pathFigure = new Path();
        pathBorder = new Path();
        matrix = new Matrix();

        Point point1_draw = new Point(this.x-r, this.y-r);
        Point point2_draw = new Point(this.x+r, this.y-r);
        Point point3_draw = new Point(this.x, this.y+r);

        pathFigure.moveTo(point1_draw.x, point1_draw.y);
        pathFigure.lineTo(point2_draw.x, point2_draw.y);
        pathFigure.lineTo(point3_draw.x, point3_draw.y);
        pathFigure.lineTo(point1_draw.x, point1_draw.y);

        pathFigure.close();
        pathBorder.addRect(this.x-r, this.y-r, this.x+r,this.y+r,Path.Direction.CW);
        pathBorder.close();
    }

    @Override
    public void draw(Canvas canvas) {

        if(pen==null&&selectedPen==null){

            penInit();
            reinit();
        }

        matrix.reset();
        matrix.setScale(2f, 2.5f, 375, 100);

        // применяем матрицу к path
        pathFigure.transform(matrix);

    canvas.drawPath(pathFigure, pen);

        if (this.selected){
            canvas.drawPath(pathBorder, selectedPen);
        }
    }

    @Override
    public boolean onArea(float x, float y) {

        if((x>=this.x-r && x<=this.x+r) && (y>=this.y-r && y<=this.y+r)){
            return true;
        }

        return false;
    }

    @Override
    public void startAnimation(Canvas canvas) {
        if(pen==null&&selectedPen==null){

            penInit();
            reinit();
        }

        matrix.reset();
        matrix.setScale(2f, 2.5f, 375, 100);

        // применяем матрицу к path
        pathFigure.transform(matrix);

        canvas.drawPath(pathFigure, pen);

        if (this.selected){
            canvas.drawPath(pathBorder, selectedPen);
        }
    }

    @Override
    public void setCoordinates(float x, float y) {

        int rx = (int)x - this.x;
        int ry = (int)y - this.y;
        this.x=(int)x;
        this.y=(int)y;

        matrix.reset();
        matrix.setTranslate(rx,ry);
        pathFigure.transform(matrix);
        pathBorder.transform(matrix);
    }

    @Override
    public void setScale(float scale) {

        if((r > 50 && r*scale < 400)){
            r*=scale;
//            this.ly*=scale;

            matrix.reset();
            matrix.setScale(scale, scale, this.x, this.y);
            pathFigure.transform(matrix);
            pathBorder.transform(matrix);
        }
    }
} 