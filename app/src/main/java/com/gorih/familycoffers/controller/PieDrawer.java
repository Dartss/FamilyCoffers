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

import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

public class PieDrawer extends View {
    Paint paintColors;
    Paint paintText;
    HashMap<String , Float> toDraw;
    RectF rectf;
    float pieDiameter;
    float margin;
    float screenWidth;
    int viewHeight;
    float colorRectSize;

    public PieDrawer(Context context, HashMap<String, Float> expanses) {
        super(context);
        paintColors = new Paint();
        paintText = new Paint();

        this.toDraw = expanses;

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        screenWidth = metrics.widthPixels;
        margin = (float) (screenWidth * 0.1);
        pieDiameter = screenWidth - margin*2;
        colorRectSize = screenWidth / 20;

        viewHeight = (int) (pieDiameter + margin*2 + (colorRectSize+margin/2) * toDraw.size());

    }


    @Override
    protected void onDraw(Canvas canvas) {
        paintColors.setColor(Color.WHITE);

        Formatter formatter;

        final float rectXY1 = margin;
        final float rectXY2 = screenWidth - margin;

        final float circleCenterCords = pieDiameter / 2 + margin;
        final float backgroundCircleRadius = pieDiameter / 2 + 3;

        final float foregroundCircleRadius = pieDiameter / 5;

        final float paddingLegend = margin / 2;
        final float textPadding= colorRectSize + margin;

        float legendStartLocation = pieDiameter + margin*2;

        paintText.setTextSize(colorRectSize);

        float mStartAngle = 0;

        canvas.drawCircle(circleCenterCords, circleCenterCords, backgroundCircleRadius, paintColors);
        rectf = new RectF(rectXY1, rectXY1, rectXY2, rectXY2);

        for(Map.Entry<String, Float> entry: toDraw.entrySet()) {
            formatter = new Formatter();
            int mColorId = Categories.getInstance().getAllCategoriesMap().get(entry.getKey()).getColor();
            int mColor = ContextCompat.getColor(getContext(), mColorId);
            float mAngel = entry.getValue();

            paintColors.setColor(mColor);

            canvas.drawArc(rectf, mStartAngle, mAngel, true, paintColors);

            mStartAngle += mAngel;

            canvas.drawRect(paddingLegend, legendStartLocation, paddingLegend + colorRectSize,
                    legendStartLocation + colorRectSize, paintColors);
            formatter.format("%.2f", (100 * mAngel) / 360);
            canvas.drawText(" - " + entry.getKey() +
                    " (" + formatter + "%)", textPadding, legendStartLocation+colorRectSize, paintText);

            legendStartLocation += paddingLegend /2 + colorRectSize;

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
