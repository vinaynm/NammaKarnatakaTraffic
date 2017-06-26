package customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by one on 3/12/15.
 */
public class robotoText extends TextView {

    public robotoText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public robotoText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public robotoText(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Existence-Light.otf");
            setTypeface(tf);
        }
    }

}