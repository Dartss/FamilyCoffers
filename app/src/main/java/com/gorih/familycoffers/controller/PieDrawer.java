package com.gorih.familycoffers.controller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.gorih.familycoffers.model.Categories;
import com.gorih.familycoffers.model.Category;

import java.util.HashMap;
import java.util.Map;

public class PieDrawer extends View {
    Paint paintColors;
    HashMap<Integer , Float> toDraw = new HashMap<>();
    RectF rectf;
    float pieDiameter;
    float margin;
    float screenWidth;
    int viewHeight;

    public PieDrawer(Context context, HashMap<Integer, Float> expanses, float sumOfAllExpanses) {
        super(context);
        paintColors = new Paint();

        for (Map.Entry<Integer, Float> entry : expanses.entrySet() ) {
            toDraw.put(entry.getKey(), (entry.getValue() * 360) / sumOfAllExpanses);
        }

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        margin = (float) (screenWidth * 0.1);
        pieDiameter = screenWidth - margin*2;

        viewHeight = (int) (pieDiameter + margin*2);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        paintColors.setColor(Color.WHITE);

        final float rectXY1 = margin;
        final float rectXY2 = screenWidth - margin;

        final float circleCenterCords = pieDiameter / 2 + margin;
        final float backgroundCircleRadius = pieDiameter / 2 + 3;

        final float foregroundCircleRadius = pieDiameter / 5;

        float mStartAngle = 0;

        canvas.drawCircle(circleCenterCords, circleCenterCords, backgroundCircleRadius, paintColors);
        rectf = new RectF(rectXY1, rectXY1, rectXY2, rectXY2);

        for(Map.Entry<Integer, Float> entry: toDraw.entrySet()) {
            Category category = Categories.instance.findCategoryById(entry.getKey());
            int mColor = category.getColor();
            float mAngel = entry.getValue();

            paintColors.setColor(mColor);

            canvas.drawArc(rectf, mStartAngle, mAngel, true, paintColors);

            mStartAngle += mAngel;

            paintColors.setColor(Color.WHITE);
            canvas.drawCircle(circleCenterCords, circleCenterCords, foregroundCircleRadius, paintColors);

        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = viewHeight;
        setMeasuredDimension(width, height);
    }
}
