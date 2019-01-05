package net.hycollege.ljl.pleasedfood.utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.View;

/**
 * 这是一个菜单界面优化美化类
 */
public class ScaleTransformer0 implements ViewPager.PageTransformer {//改变形状的透明度
    private Context contexts;
    private float elevation;

    public ScaleTransformer0(Context context) {
        this.contexts = context;
        elevation = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                20, contexts.getResources().getDisplayMetrics());
    }

    @Override
    public void transformPage(View page, float position) {
        if (position < -1 || position > 1) {

        } else {
            if (position < 0) {
                ((CardView) page).setCardElevation((1 + position) * elevation);
            } else {
                ((CardView) page).setCardElevation((1 - position) * elevation);
            }
        }
    }
}
