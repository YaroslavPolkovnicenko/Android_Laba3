package com.example.a21091.thirdproject.Entity;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.view.animation.Animation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Custom extends Figure implements Serializable{

    private int lx;
    private int ly;
    private List<Coordinates> points = new ArrayList<Coordinates>();
    private transient Path pathFigure;
    private transient Path pathBorder;
    private transient Matrix matrix;

    public Custom() {

        super();
        flag=false;
        reinit();
    }

    @Override
    public void draw(Canvas canvas) {

        if(pen==null&&selectedPen==null){

            penInit();
            reinit();
        }

        canvas.drawPath(pathFigure, pen);

        if (this.selected){
            canvas.drawPath(pathBorder, selectedPen);
        }
    }

    @Override
    public boolean onArea(float x, float y) {

        if((x>=this.x-this.lx && x<=this.x+this.lx) && (y>=this.y-this.ly && y<=this.y+this.ly)){
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

        for (Coordinates i: points) {
            i.x+=rx;
            i.y+=ry;
        }

        matrix.reset();
        matrix.setTranslate(rx,ry);
        pathFigure.transform(matrix);
        pathBorder.transform(matrix);
    }

    @Override
    public void setScale(float scale) {

        if((this.lx*scale > 50 && this.lx*scale < 400)&&(this.ly*scale > 50 && this.ly*scale < 400)){

            this.ly*=scale;
            this.ly*=scale;

            for (Coordinates i: points) {
                i.x*=scale;
                i.y*=scale;
            }

            matrix.reset();
            matrix.setScale(scale, scale, this.x, this.y);
            pathFigure.transform(matrix);
            pathBorder.transform(matrix);
        }
    }

    public void addPointToCustomFigure(float x, float y){

        this.points.add(new Coordinates((int)x,(int)y));
        reinit();
    }

    private void reinit(){

        pathFigure = new Path();
        pathBorder = new Path();
        matrix = new Matrix();

        if(points.size()!=0){

            this.pathFigure.reset();
            this.pathBorder.reset();

            pathFigure.moveTo(points.get(0).x, points.get(0).y);

            for (Coordinates i: points) {
                pathFigure.lineTo(i.x, i.y);
            }

            pathFigure.close();

            int maxx=0;
            int minx=10000;
            int maxy=0;
            int miny=10000;

            for (Coordinates i: points) {

                if (i.x > maxx) {
                    maxx = i.x;
                }

                if (i.x < minx) {
                    minx = i.x;
                }

                if (i.y > maxy) {
                    maxy = i.y;
                }

                if (i.y < miny) {
                    miny = i.y;
                }
            }

            this.x=minx+(maxx-minx)/2;
            this.y=miny+(maxy-miny)/2;
            this.lx=(maxx-minx)/2;
            this.ly=(maxy-miny)/2;

            pathBorder.addRect(minx,miny, maxx,maxy,Path.Direction.CW);
            pathBorder.close();
        }
    }
}