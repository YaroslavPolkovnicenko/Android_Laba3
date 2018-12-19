package com.example.a21091.thirdproject.Entity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.animation.Animation;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Figure implements Serializable {

    protected int r = 100;
    protected int x;
    protected int y;
    protected int alpha;
    protected boolean selected = false;
    protected transient Paint pen;
    protected transient Paint selectedPen;
    private List<Integer> colorPallet = Arrays.asList(new Integer[]{Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.YELLOW});
    private int colorSelected;

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    static int a=0;

    public Figure next(int x, int y){

        switch (a){

            case 0:

                a++;

//            if(a > 3){

//                a=0;

//            }

                return new Circle(x,y);

            case 1:

                a++;

//                if(a > 3){

//                    a=0;

//                }

                return new Square(x,y);

            case 2:

                a++;

//                if(a > 3){

//                    a=0;

//                }

                return new Triangle(x,y);

        }

        a++;

        if(a > 3){

            a=0;

        }

        return new Circle(x,y);

    }

    public  Figure(){

        this.alpha=255;
        this.colorSelected=0;
        penInit();
    }

    public void penInit() {

        pen = new Paint();

        pen.setColor(colorPallet.get(colorSelected));
        pen.setAlpha(alpha);

        selectedPen = new Paint();

        selectedPen.setStyle(Paint.Style.STROKE);
        selectedPen.setColor(Color.BLACK);
        selectedPen.setStrokeWidth(5);
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected(){
        return this.selected;
    }

    public Map<String, Integer> getCoordinates() {

        Map<String, Integer> hashMap = new HashMap<>();

        hashMap.put("x", this.x);
        hashMap.put("y", this.y);

        return hashMap;
    }

    public void changeColor(){

        colorSelected++;

        pen.setColor(colorPallet.get(colorSelected));

        if(colorSelected >= colorPallet.size()-1){
            colorSelected=0;
        }
    }

    public void setAlpha(int alpha) {

        this.alpha=alpha;
        pen.setAlpha(alpha);
    }

    public int getAlpha() {
        return alpha;
    }

    public abstract void setScale(float scale);
    public abstract void setCoordinates(float x, float y);
    public abstract void draw(Canvas canvas);
    public abstract boolean onArea(float x, float y);

    public void addPointToCustomFigure(float x, float y) {};

    public abstract void startAnimation(Canvas canvas);
}