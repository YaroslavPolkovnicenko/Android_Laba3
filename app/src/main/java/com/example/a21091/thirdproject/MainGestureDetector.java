package com.example.a21091.thirdproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.SeekBar;

import com.example.a21091.thirdproject.DrawView;
import com.example.a21091.thirdproject.Entity.Circle;
import com.example.a21091.thirdproject.Entity.Figure;
import com.example.a21091.thirdproject.Entity.Square;

public class MainGestureDetector extends GestureDetector.SimpleOnGestureListener {

    @Override

    public boolean onDoubleTap(MotionEvent e) {

        int j = 0;

        if(DrawView.getInstance().getState() == DrawView.State.DRAW) {

            for (int i = 0 ;i < DrawView.getInstance().getFigures().size(); i++){

                if (DrawView.getInstance().getFigures().get(i).onArea(e.getX(), e.getY())) {

                    DrawView.getInstance().getFigures().get(i).changeColor();
                    DrawView.getInstance().getFigures().set(i, DrawView.getInstance().getFigures().get(i).next(DrawView.getInstance().getFigures().get(i).getX(),DrawView.getInstance().getFigures().get(i).getY()));

                    break;
                }
            }
        }

        return super.onDoubleTap(e);
    }

    @Override
    public void onLongPress(MotionEvent e) {

        String[] colors = {"Масштабирование"};

        AlertDialog.Builder builder = new AlertDialog.Builder(DrawView.getInstance().getContext());

        builder.setItems(colors, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which==0){
                    DrawView.getInstance().setState(DrawView.State.SCALE_CHANGE);

                }
            }
        });

        for (Figure i : DrawView.getInstance().getFigures()) {

            if(i.onArea(e.getX(),e.getY())){

                DrawView.getInstance().setFocusFigure(i);
                builder.show();

                break;
            }
        }
        super.onLongPress(e);    }
}
