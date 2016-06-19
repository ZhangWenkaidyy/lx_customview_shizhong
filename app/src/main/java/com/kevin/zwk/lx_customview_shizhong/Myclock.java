package com.kevin.zwk.lx_customview_shizhong;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/6/19.
 */
public class Myclock extends View {
    private Paint mPaintDial;
    private Paint mPaintText;
    private int mWidth;
    private int mHeight;
    private int mDialRadius;
    private Calendar calendar;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            handler.sendEmptyMessageDelayed(10, 1000);
            invalidate();
            calendar = Calendar.getInstance();

        }
    };

    public Myclock(Context context) {
        super(context);
        init();
    }

    public Myclock(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Myclock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Myclock(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /*
    初始化画笔，之所以不放到画布方法中
    是因为画布方法会被调用多次

     */
    private void init() {
        mPaintDial = new Paint();
        mPaintDial.setColor(Color.RED);
        //笔的宽度
        mPaintDial.setStrokeWidth(10);
        //设置边缘抗锯齿
        mPaintDial.setAntiAlias(true);
        //设置画笔style，不填充
        mPaintDial.setStyle(Paint.Style.STROKE);


        mPaintText = new Paint();
        mPaintText.setColor(Color.BLUE);
        mPaintText.setStrokeWidth(10);
        mPaintText.setTextSize(80);

        //设置绘图上的文字时的居中
        mPaintText.setTextAlign(Paint.Align.CENTER);
       
        handler.sendEmptyMessageDelayed(20, 1000);
        calendar = Calendar.getInstance();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getWidth();
        mHeight = getHeight();
        mDialRadius = Math.min(mWidth, mHeight) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mWidth / 2, mHeight / 2, mDialRadius, mPaintDial);
        for (int i = 1; i < 13; i++) {
            canvas.rotate(30, mWidth / 2, mHeight / 2);
            canvas.drawLine(mWidth / 2, mHeight / 2 - mDialRadius, mWidth / 2, mHeight / 2 - mDialRadius + 30, mPaintDial);

            canvas.drawText("" + i, mWidth / 2, mHeight / 2 - mDialRadius + 100, mPaintText);
        }
        for (int i = 1; i < 60; i++) {
            canvas.rotate(6, mWidth / 2, mHeight / 2);
            canvas.drawLine(mWidth / 2, mHeight / 2 - mDialRadius, mWidth / 2, mHeight / 2 - mDialRadius + 15, mPaintDial);
        }
        canvas.save();
        int minute = calendar.get(Calendar.MINUTE);
        canvas.rotate(minute * 6, mWidth / 2, mHeight / 2);
        canvas.drawLine(mWidth / 2, mHeight / 2, mWidth / 2, mHeight / 2 - 300, mPaintDial);
        canvas.restore();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int hourDegree = (hour * 60 + minute) * 360 / (12 * 60);

        canvas.save();
        canvas.rotate(hourDegree, mWidth / 2, mHeight / 2);
        canvas.drawLine(mWidth / 2, mHeight / 2, mWidth / 2, mHeight / 2 - 220, mPaintDial);
        canvas.restore();
        int second = calendar.get(Calendar.SECOND);

        canvas.save();
        canvas.rotate(second * 6, mWidth / 2, mHeight / 2);
        canvas.drawLine(mWidth / 2, mHeight / 2, mWidth / 2, mHeight / 2 - 350, mPaintDial);
        canvas.restore();
    }
}
