package com.example.cleverkoi;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;

/**
 * @Author: Jack Ou
 * @CreateDate: 2020/9/2 22:19
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/9/2 22:19
 * @UpdateRemark: 更新说明
 */
public class CleverFish extends Drawable {

    private Paint mPaint;
    private Path mPath;
    private int OTHER_ALPHA = 110;

    //重心
    private PointF middlePoint;
    private int originMainAngle = 0;

    /**
     * 鱼的长度值
     */
    //鱼头半径
    private float HEAD_RADIUS = 100;
    //鱼身长度
    private float BODY_LENGTH = HEAD_RADIUS * 3.2f;
    //鱼鳍的长度
    private float RIGHT_FINS_LENGTH = HEAD_RADIUS * 1.3f;
    private float FIND_FINS_POINT_LENGTH = HEAD_RADIUS * 0.9f;

    public CleverFish() {
        init();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        //抗锯齿  圆角更光滑
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        //防抖动，颜色更连贯
        mPaint.setDither(true);
        //设置颜色
        mPaint.setColor(Color.argb(OTHER_ALPHA, 244, 92, 71));

        middlePoint = new PointF(4.19f * HEAD_RADIUS, 4.19f * HEAD_RADIUS);
    }

    @Override
    public void draw(Canvas canvas) {

        int headAngle = originMainAngle;
        //鱼头圆心坐标
        PointF headPoint = calculatePoint(middlePoint, BODY_LENGTH / 2, headAngle);
        canvas.drawCircle(headPoint.x, headPoint.y, HEAD_RADIUS, mPaint);

        //右鱼鳍起点
        PointF finsRightStartPoint = calculatePoint(headPoint, FIND_FINS_POINT_LENGTH, headAngle - 110);
        drawRightFins(canvas, finsRightStartPoint, headAngle, true);
        //左鱼鳍起点
        PointF finsLeftStartPoint = calculatePoint(headPoint, FIND_FINS_POINT_LENGTH, headAngle + 110);
        drawRightFins(canvas, finsLeftStartPoint, headAngle, false);
    }

    private void drawRightFins(Canvas canvas, PointF finsStartPoint, int headAngle, boolean isRight) {
        //贝塞尔曲线控制角度，可以自己试
        float controlAngle = 115;
        //减去180的原因是点在开始点左边
        PointF finsEndPoint = calculatePoint(finsStartPoint, RIGHT_FINS_LENGTH, headAngle - 180);
        PointF controlPoint = calculatePoint(finsStartPoint, RIGHT_FINS_LENGTH * 1.8f,
                isRight ? headAngle - controlAngle : headAngle + controlAngle);

        //绘制鱼鳍
        mPath.reset();
        mPath.moveTo(finsStartPoint.x, finsStartPoint.y);
        //二阶贝塞尔曲线方法
        mPath.quadTo(controlPoint.x, controlPoint.y, finsEndPoint.x, finsEndPoint.y);
        canvas.drawPath(mPath, mPaint);
    }

    private PointF calculatePoint(PointF startPoint, float length, float angle) {
        //X坐标计算
        float x = (float) Math.cos(Math.toRadians(angle)) * length;
        //Y坐标计算
        float y = (float) Math.sin(Math.toRadians(angle - 180)) * length;
        return new PointF(startPoint.x + x, startPoint.y + y);
    }

    @Override
    public void setAlpha(int i) {
        mPaint.setAlpha(i);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    //PixelFormat.TRANSLUCENT绘制的地方不透明，其他地方透明
    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    //Drawable的宽高
    @Override
    public int getIntrinsicWidth() {
        return (int) (2 * 4.19f * HEAD_RADIUS);
    }

    //Drawable的宽高
    @Override
    public int getIntrinsicHeight() {
        return (int) (2 * 4.19f * HEAD_RADIUS);
    }
}
