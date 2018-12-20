package com.example.a21091.thirdproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.a21091.thirdproject.Entity.Circle;
import com.example.a21091.thirdproject.Entity.Custom;
import com.example.a21091.thirdproject.Entity.Figure;
import com.example.a21091.thirdproject.Entity.Square;
import com.example.a21091.thirdproject.Entity.Triangle;
import com.example.a21091.thirdproject.MainGestureDetector;
import com.example.a21091.thirdproject.MainScaleGestureDetector;

import java.util.ArrayList;
import java.util.List;

public class DrawView extends View {

    public enum State {DRAW, DRAG, NEW_CIRCLE, NEW_SQUARE, NEW_TRIANGLE, NEW_DRAW};
    private State state = State.DRAW;
    private static volatile DrawView instance;
    private GestureDetectorCompat gestureDetector;
    private ScaleGestureDetector gestureScaleDetector;
    private Context context;

    List<Figure> figures = new ArrayList<>();
    Figure focusFigure;

    int a=0;
    public void e(){

        try {
            switch (a){

                case 0:
                    focusFigure = new Circle(focusFigure.getCoordinates().get('x'),focusFigure.getCoordinates().get('y'));

                case 1:
                    focusFigure = new Square(focusFigure.getCoordinates().get('x'),focusFigure.getCoordinates().get('y'));

                case 2:
                    focusFigure = new Triangle(focusFigure.getCoordinates().get('x'),focusFigure.getCoordinates().get('y'));
            }

        } catch (NullPointerException e){

        }

        invalidate();

        a++;

        if(a < 3){
            a=0;
        }
    }

    public DrawView(Context context) {

        super(context);
        this.context = context;

        MainGestureDetector lgd = new MainGestureDetector();

        gestureDetector = new GestureDetectorCompat(context, lgd);

        MainScaleGestureDetector lsgd = new MainScaleGestureDetector();

        gestureScaleDetector = new ScaleGestureDetector(context, lsgd);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Log.i("motevent", "draw");

        canvas.drawColor(Color.WHITE);

        for (Figure i :figures) {
            i.draw(canvas);
        }
    }

    private float dragX = 0;
    private float dragY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        gestureDetector.onTouchEvent(e);

        gestureScaleDetector.onTouchEvent(e);

        switch (e.getAction()) {

            case MotionEvent.ACTION_DOWN:

                if (this.state == State.DRAW){

                    for (Figure i :figures) {
                        if(i.onArea(e.getX(),e.getY())){

                            this.focusFigure  = i;
                            this.state = State.DRAG;

                            break;
                        }
                    }

                    if (state == State.DRAG) {

                        this.focusFigure.setSelected(true);

                        dragX = e.getX() - this.focusFigure.getCoordinates().get("x");
                        dragY = e.getY() - this.focusFigure.getCoordinates().get("y");
                    }
                }

                if (state == State.NEW_CIRCLE) {

                    figures.add(new Circle(e.getX(),e.getY()));
                    state = State.DRAW;
                }

                if (state == State.NEW_SQUARE) {

                    figures.add(new Square(e.getX(),e.getY()));
                    state = State.DRAW;
                }

                if (state == State.NEW_TRIANGLE) {

                    figures.add(new Triangle(e.getX(),e.getY()));
                    state = State.DRAW;
                }

                if (state == State.NEW_DRAW) {
                    figures.add(new Custom());
                }

                break;

            case MotionEvent.ACTION_MOVE:

                if (state == State.DRAG) {
                    this.focusFigure.setCoordinates(e.getX() - dragX, e.getY() - dragY);
                }

                if (state == State.NEW_DRAW) {
                    this.figures.get(this.figures.size() - 1).addPointToCustomFigure(e.getX(),e.getY());
                }

                break;

            case MotionEvent.ACTION_UP:

                if (state == State.DRAG) {

                    this.focusFigure.setSelected(false);
                    state = State.DRAW;
                }

                if (state == State.NEW_DRAW) {

                    this.figures.get(this.figures.size() - 1).addPointToCustomFigure(e.getX(),e.getY());
                    state = State.DRAW;
                }

                break;

        }

        invalidate();

        return true;
    }

    public static DrawView getInstance(Context context) {

        if (instance == null) {
            synchronized (DrawView.class) {

                if (instance == null) {
                    instance = new DrawView(context);
                }
            }
        }

        return instance;
    }

    public static DrawView getInstance() {
        return instance;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setScaleFocusFigure(float scale){
        this.focusFigure.setScale(scale);
    }

    public void setFocusFigure(Figure focusFigure){
        this.focusFigure = focusFigure;
    }

    public Figure getFocusFigure(){
        return this.focusFigure;
    }

    public List<Figure> getFigures() {
        return figures;
    }

    public void setFigures(List<Figure> figures) {
        this.figures = figures;
    }
}
