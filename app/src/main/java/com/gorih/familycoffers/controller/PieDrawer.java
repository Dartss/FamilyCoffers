package com.gorih.familycoffers.controller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.gorih.familycoffers.model.Categories;

import java.util.HashMap;
import java.util.Map;

public class PieDrawer extends View {
    Paint paintColors;
    Paint paintText;
    HashMap<String , Float> toDraw;
    RectF rectf;

    public PieDrawer(Context context, HashMap<String, Float> expanses) {
        super(context);
        paintColors = new Paint();
        paintText = new Paint();
        this.toDraw = expanses;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        float scale = metrics.densityDpi / 160;
        Log.d("PieDrawer", String.valueOf(scale));

        paintColors.setColor(Color.WHITE);
        canvas.drawCircle(200 * scale, 170 * scale, 153*scale, paintColors);
        rectf = new RectF(50*scale, 20*scale, 350*scale, 320*scale);

        int columnSeparator = 0;
        float mStartAngle = 0;
        float mSpaces = 340*scale;

        for(Map.Entry<String, Float> entry: toDraw.entrySet()){
            int mColorId = Categories.getInstance().getAllCategoriesMap().get(entry.getKey()).getColor();
            int mColor = ContextCompat.getColor(getContext(), mColorId);
            float mAngel = entry.getValue();

            paintColors.setColor(mColor);

            canvas.drawArc(rectf, mStartAngle, mAngel, true, paintColors);

            mStartAngle += mAngel;

            paintText.setColor(Color.BLUE);
            paintText.setTextSize(25);

            if (columnSeparator % 2 == 0) {
                canvas.drawRect(20, mSpaces, 40, mSpaces + 20, paintColors);
                canvas.drawText(" - " + entry.getKey() +
                        " (" + (100 * mAngel) / 360 + "%)", 60, mSpaces + 15, paintText);
            } else {
                canvas.drawRect(320, mSpaces, 340, mSpaces + 20, paintColors);
                paintColors.setTextSize(25);
                canvas.drawText(" - " + entry.getKey() +
                        " (" + (100 * mAngel) / 360 + "%)", 360, mSpaces + 15, paintText);
                mSpaces += 40;
            }

            columnSeparator ++;
        }

        paintColors.setColor(Color.WHITE);
        canvas.drawCircle(200 * scale, 170 * scale, 50*scale, paintColors);

    }
}
