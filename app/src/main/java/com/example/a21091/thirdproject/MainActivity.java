package com.example.a21091.thirdproject;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Menu menu;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(DrawView.getInstance(this));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        this.menu = menu;

        menu.add(0, 0, 0, "Круг").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                DrawView.getInstance().setState(DrawView.State.NEW_CIRCLE);

                return true;
            }
        });

        menu.add(0, 1, 1, "Квадрат").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                DrawView.getInstance().setState(DrawView.State.NEW_SQUARE);

                return true;
            }
        });

        menu.add(0, 2, 2, "Треугольник").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                DrawView.getInstance().setState(DrawView.State.NEW_TRIANGLE);

                return true;
            }
        });

        menu.add(0, 3, 3, "Рисовать").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                DrawView.getInstance().setState(DrawView.State.NEW_DRAW);

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Нарисуйте фигуру пальцем", Toast.LENGTH_SHORT);
                toast.show();

                return true;
            }
        });

        menu.add(0, 4, 4, "Экспорт в jpg").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Bitmap screenshot;

                DrawView.getInstance().setDrawingCacheEnabled(true);

                screenshot = Bitmap.createBitmap(DrawView.getInstance().getDrawingCache());

                DrawView.getInstance().setDrawingCacheEnabled(false);

                saveAsImage(screenshot,new File("/storage/emulated/0/a.jpg"));

                return true;
            }
        });

        menu.add(0, 5, 5, "Сохранить").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                save(new File("/storage/emulated/0/a.raw"));

                return true;
            }
        });

        menu.add(0, 6, 6, "Открыть").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {

                load(new File("/storage/emulated/0/a.raw"));

                return true;
            }
        });

        return true;
    }



    private void saveAsImage(Bitmap b, File f) {

        FileOutputStream fos = null;

        try {

            fos = new FileOutputStream (f);

            b.compress (Bitmap.CompressFormat.JPEG, 100, fos);

            Toast.makeText (getApplicationContext(), "Сохранено в " + f.getAbsolutePath(), Toast.LENGTH_LONG).show ();

        } catch (Throwable ex) {
            Toast.makeText (getApplicationContext(), "Error: " + ex.getMessage (), Toast.LENGTH_LONG).show ();

            ex.printStackTrace ();
        }
    }

    private void save(File f){

        FileOutputStream fos = null;
        ObjectOutputStream os = null;

        try {

            fos = new FileOutputStream(f);
            os = new ObjectOutputStream(fos);

            os.writeObject(DrawView.getInstance().getFigures());

            os.close();
            fos.close();

            Toast.makeText (getApplicationContext(), "Сохранено в " + f.getAbsolutePath(), Toast.LENGTH_LONG).show ();

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

            Toast.makeText (getApplicationContext(), "Error: " + e.getMessage (), Toast.LENGTH_LONG).show ();
        }
    }

    private void load(File f){

        FileInputStream fis = null;
        ObjectInputStream is = null;

        try {

            fis = new FileInputStream(f);
            is = new ObjectInputStream(fis);

            DrawView.getInstance().getFigures().clear();
            DrawView.getInstance().setFigures((ArrayList)is.readObject());
            DrawView.getInstance().invalidate();

            is.close();
            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

            Toast.makeText (getApplicationContext(), "Error: " + e.getMessage (), Toast.LENGTH_LONG).show ();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

