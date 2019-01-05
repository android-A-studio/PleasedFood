package net.hycollege.ljl.pleasedfood.view.MoreListRecycler;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Custom动画
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class CustomAnimation implements BaseAnimation {

    @Override
    public Animator[] getAnimators(View view) {
        return new Animator[]{
                ObjectAnimator.ofFloat(view, "scaleY", 1, 1.1f, 1),
                ObjectAnimator.ofFloat(view, "scaleX", 1, 1.1f, 1)
        };
    }
}
