package com.example.cleverkoi;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * @author Jack_Ou  created on 2020/9/3.
 */
public class FishRelativeLayout extends RelativeLayout {
    public FishRelativeLayout(Context context) {
        this(context, null);
    }

    public FishRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FishRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //viewGroup默认不会调用onDraw方法的需要设置一下
        setWillNotDraw(false);
    }
}
