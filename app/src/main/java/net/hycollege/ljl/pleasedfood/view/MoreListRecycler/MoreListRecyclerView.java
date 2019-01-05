package net.hycollege.ljl.pleasedfood.view.MoreListRecycler;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gc.materialdesign.views.ButtonFloatSmall;
import com.gc.materialdesign.views.LayoutRipple;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nineoldandroids.view.ViewHelper;
import com.sun.easysnackbar.EasySnackBar;

import net.hycollege.ljl.pleasedfood.R;
import net.hycollege.ljl.pleasedfood.bean.ArrayListFoodBean;
import net.hycollege.ljl.pleasedfood.bean.FoodBean;
import net.hycollege.ljl.pleasedfood.bean.OrderFoodBean;
import net.hycollege.ljl.pleasedfood.utils.OrderFoodData;
import net.hycollege.ljl.pleasedfood.utils.Powers;
import net.hycollege.ljl.pleasedfood.utils.SerializableMap;
import net.hycollege.ljl.pleasedfood.view.OrderPages;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现加载菜单列表的类
 */
public class MoreListRecyclerView extends RecyclerView.Adapter<MoreListRecyclerView.Viewholder> implements View.OnClickListener {
    private final Context mContext;
    private ArrayList<String> notelist;
    List<FoodBean> foodBean;
    private boolean mFirstOnlyEnable = false;
    View iten_recycler;

    public MoreListRecyclerView(Context mcontext, List<FoodBean> foodBeans) {
        this.mContext = mcontext;
        notelist = new ArrayList<>();
      //  listitem = new ArrayList<Map<String, Object>>();
        foodBean = foodBeans;
        notelist.add("aa");
        notelist.add("bb");
    }

    /**
     * 负责承载每个子项的布局
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        iten_recycler = LayoutInflater.from(parent.getContext()).inflate(R.layout.morelist, parent, false);
        Viewholder viewHolder = new Viewholder(iten_recycler);


        return viewHolder;
    }

    /**
     * 设置按钮背景色点击延申
     *
     * @param layoutRipple
     */
    private void setOriginRiple(final LayoutRipple layoutRipple) {

        layoutRipple.post(new Runnable() {

            @Override
            public void run() {
                View v = layoutRipple.getChildAt(0);
                layoutRipple.setxRippleOrigin(ViewHelper.getX(v) + v.getWidth() / 2);
                layoutRipple.setyRippleOrigin(ViewHelper.getY(v) + v.getHeight() / 2);
                layoutRipple.setRippleColor(R.color.colorFloatingButton4);
                layoutRipple.setRippleSpeed(30);
            }
        });

    }

    Powers mPowers = Powers.off;
    int backgroundColor = Color.parseColor("#1afa29");
    public static List<Map<String, Object>> listitem;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:

                break;
        }
    }




    /**
     * 负责将每个子项holder绑定数据
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {


        setMenuListener(holder, position);
        Glide.with(mContext).load(foodBean.get(position).getPicture_f()).into(holder.iv);
        //设置名字
        holder.food_name.setText(foodBean.get(position).getName_f());
        //设置价格
        holder.food_price.setText("$ " + foodBean.get(position).getPrice_f());

        addAnimations(holder);
        switch (position) {
            case 0:
                break;
            case 1:

                break;
        }
    }

    //菜单列表监听
    private void setMenuListener(Viewholder viewholder, final int s) {
        final List<FoodBean> list = new ArrayList<>();
        Toast.makeText(mContext, "666", Toast.LENGTH_SHORT).show();
        //设置监听
        viewholder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FoodBean str = foodBean.get(s);
                list.add(str);
                Log.e("what", str + "");
                //保存零时订单列表
                OrderFoodData.initFoodData(list);
                //下单提示SnackBar,
                View contentView = EasySnackBar.convertToContentView(iten_recycler, R.layout.layout_custom);
                final EasySnackBar easySnackBar = EasySnackBar.make(iten_recycler, contentView, EasySnackBar.LENGTH_INDEFINITE, false).setCallback(new EasySnackBar.Callback() {
                    //显示与隐藏监听
                    @Override
                    public void onShown(EasySnackBar sb) {
                        super.onShown(sb);
                        //  Toast.makeText(mContext,"onShown!",Toast.LENGTH_SHORT).show();
                    }

                    //显示与隐藏监听
                    @Override
                    public void onDismissed(EasySnackBar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        // Toast.makeText(mContext,"onDismissed!",Toast.LENGTH_SHORT).show();
                    }
                });
                LayoutRipple layoutRipple = (LayoutRipple) contentView.findViewById(R.id.itemButtons);
                setOriginRiple(layoutRipple);
                layoutRipple.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {

                    }
                });
                //  Toast.makeText(mContext,"测试",Toast.LENGTH_SHORT).show();
                Button buttons = contentView.findViewById(R.id.buttonPanel);
                buttons.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, OrderPages.class);

                        mContext.startActivity(intent);

                        mPowers = Powers.off;
                        easySnackBar.dismiss();
                    }
                });
                //第一次点击才显示
                if (mPowers == Powers.off) {
                    easySnackBar.show();
                    mPowers = Powers.on;
                }

               // Toast.makeText(mContext, "测试" + OrderFoodBean.getFoodBean(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取Item的个数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return foodBean == null ? 0 : foodBean.size();
    }

    public class Viewholder extends ViewHolder {
        ImageView recycler_image_notetype, recycler_image_menu, iv;
        TextView recycler_text_note, food_name, food_price;
        ButtonFloatSmall button;

        public Viewholder(View itemView) {
            super(itemView);
            button = iten_recycler.findViewById(R.id.button);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            food_name = (TextView) itemView.findViewById(R.id.food_name);
            food_price = (TextView) itemView.findViewById(R.id.food_price);
            //按图片的原来size居中显示，当图片长/宽超过View的长/宽，则截取图片的居中部分显示
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    private boolean mOpenAnimationEnable = true;
    private int mLastPosition = -1;
    private BaseAnimation mSelectAnimation = new AlphaInAnimation();
    private int mDuration = 300;
    private Interpolator mInterpolator = new LinearInterpolator();

    /**
     * To open the animation when loading
     */
    public void openLoadAnimation() {
        this.mOpenAnimationEnable = true;
    }

    /**
     * 添加动画
     *
     * @param holder
     */
    private void addAnimations(RecyclerView.ViewHolder holder) {
        if (mOpenAnimationEnable) {
            if (!mFirstOnlyEnable || holder.getLayoutPosition() > mLastPosition) {
                BaseAnimation animation = null;
                if (mSelectAnimation != null) {
                    animation = mSelectAnimation;
                } else {
                    animation = mSelectAnimation;
                }
                for (Animator anim : animation.getAnimators(holder.itemView)) {
                    startAnim(anim);
                }
                mLastPosition = holder.getLayoutPosition();
            }
        }
    }

    public void isFirstOnly(boolean firstOnly) {
        this.mFirstOnlyEnable = firstOnly;
    }

    /**
     * 开启动画
     *
     * @param animator
     */
    private void startAnim(Animator animator) {
        animator.setDuration(mDuration).start();
        animator.setInterpolator(mInterpolator);
    }

    /**
     * 设置动画效果
     *
     * @param animation
     */
    public void setAnimation(BaseAnimation animation) {
        this.mOpenAnimationEnable = true;
        this.mSelectAnimation = animation;
    }

}
