package com.example.a21091.thirdproject.Entity;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Point;

import com.example.a21091.thirdproject.DrawView;

import java.io.Serializable;

public class Triangle extends Figure implements Serializable {
    private int l;
    private int size = 200;

    private transient Path pathFigure;
    private transient Path pathBorder;
    private transient Matrix matrix;
    private int dir = 0;
    private int counter = 0;

    public Triangle(float x, float y) {
        super();
        this.x=(int)x;
        this.y=(int)y;
        this.l = this.size/2;
        flag=false;
        dir=0;

        reinit();
    }

    private void reinit(){
        pathFigure = new Path();
        pathBorder = new Path();
        matrix = new Matrix();

        Point point1_draw = new Point(this.x-this.l, this.y-this.l);
        Point point2_draw = new Point(this.x+this.l, this.y-this.l);
        Point point3_draw = new Point(this.x, this.y+this.l);
        pathFigure.moveTo(point1_draw.x, point1_draw.y);
        pathFigure.lineTo(point2_draw.x, point2_draw.y);
        pathFigure.lineTo(point3_draw.x, point3_draw.y);
        pathFigure.lineTo(point1_draw.x, point1_draw.y);
        pathFigure.close();
        pathBorder.addRect(this.x-this.l, this.y-this.l, this.x+this.l,this.y+this.l,Path.Direction.CW);
        pathBorder.close();
    }

    @Override
    public void draw(Canvas canvas) {
        if(pen==null&&selectedPen==null){
            penInit();
            reinit();
        }
        matrix.reset();
if(flag) {

    if (dir == 0) {
        if (counter > 100) {
            dir = 1;
            counter = 0;
        }
        this.l *= 1.01;
        matrix.setScale(1.01f, 1.01f, this.x, this.y);
    } else {
        if (counter > 100 && flag == true) {
            dir = 0;
            counter = 0;
            flag = false;
        }
        this.l *= 0.99;
        matrix.setScale(0.99f, 0.99f, this.x, this.y);
    }
    counter++;
    pathFigure.transform(matrix);
    pathBorder.transform(matrix);
}
        canvas.drawPath(pathFigure, pen);
        if (this.selected){
            canvas.drawPath(pathBorder, selectedPen);
        }

        DrawView.getInstance().invalidate();
    }

    @Override
    public boolean onArea(float x, float y) {
        if((x>=this.x-this.l && x<=this.x+this.l) && (y>=this.y-this.l && y<=this.y+this.l)){
            return true;
        }
        return false;
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
        if((this.l*scale > 50 && this.l*scale < 400)){
            this.l*=scale;

            matrix.reset();
            matrix.setScale(scale, scale, this.x, this.y);
            pathFigure.transform(matrix);
            pathBorder.transform(matrix);
        }
    }
}