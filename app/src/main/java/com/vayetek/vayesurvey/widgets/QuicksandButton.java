package com.vayetek.vayesurvey.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.vayetek.vayesurvey.R;
import com.vayetek.vayesurvey.utils.FontQuicksand;


public class QuicksandButton extends android.support.v7.widget.AppCompatButton {
    public QuicksandButton(Context context) {
        super(context);
    }

    public QuicksandButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.QuicksandTextView,
                0, 0);

        try {
            int font = a.getInt(R.styleable.QuicksandTextView_font, 0);
            FontQuicksand fontQuicksand = new FontQuicksand(context);
            switch (font) {
                case 0:
                    setTypeface(fontQuicksand.getRegular());
                    break;
                case 1:
                    setTypeface(fontQuicksand.getDash());
                    break;
                case 2:
                    setTypeface(fontQuicksand.getLight());
                    break;
                case 3:
                    setTypeface(fontQuicksand.getBold());
                    break;
                default:
                    setTypeface(fontQuicksand.getRegular());
                    break;
            }
        } finally {
            a.recycle();
        }
    }

    public QuicksandButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
