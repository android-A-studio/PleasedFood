package net.hycollege.ljl.pleasedfood.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import net.hycollege.ljl.pleasedfood.R;
import net.hycollege.ljl.pleasedfood.bean.FoodBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 实现加载菜单列表的类
 */
class OrderRecyclerView extends RecyclerView.Adapter<OrderRecyclerView.Viewholder> {
    private final Context context;
    private List<FoodBean> notelist;
    private List<Map<String, String>> notelistAndNum;
    public List<Integer> amount;
    public OrderRecyclerView(Context mcontext) {
        this.context = mcontext;
        notelist = new ArrayList<>();
    }
    //传List<FoodBean>对象
    public OrderRecyclerView(Context mcontext, List<FoodBean> foodBean) {
        this.context = mcontext;
        notelist=foodBean;
    }
    //传List<Map<String, String>>对象
    public OrderRecyclerView(List<Map<String, String>> foodBeans,Context mcontext) {
        notelistAndNum=new ArrayList<>();
        notelistAndNum=foodBeans;
        this.context = mcontext;
        amount=new ArrayList<Integer>();
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
        View iten_recycler = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderrecyclerview, parent, false);
        Viewholder viewHolder = new Viewholder(iten_recycler);
        return viewHolder;
    }
    int amounts=0;
    /**
     * 负责将每个子项holder绑定数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        amount=new ArrayList<Integer>();
        setMenuListener(holder, position);
        //注意：" pic"前面加个空格,问题出在StringToMap类
        String url=notelistAndNum.get(position).get(" pic");
        Glide.with(context).load(url).into(holder.recycler_image_menu);
        holder.order_data_title.setText(notelistAndNum.get(position).get("name"));
        //数量
        String nums=notelistAndNum.get(position).get("num");
        holder.order_data_item_sum.setText(nums);
        //金额
        String money=notelistAndNum.get(position).get(" money");
        holder.order_data_single_price.setText(money);
        //注意：" money"前面加个空格,问题出在StringToMap类
        //单个总金额
        int oneOrdermonuys=Integer.parseInt(nums)*Integer.parseInt(money);
        holder.item_order_price.setText(oneOrdermonuys+"");
        //保存单个总金额
//        amount.add(oneOrdermonuys);
        amounts +=oneOrdermonuys;
        mListener.onAddListAmount(amounts);
    }
    /**
     * 通过接口回调解决当前点击位置
     */
    public interface OnAmountListener {
        void onAddListAmount(int amounts);
    }
    OnAmountListener mListener;
    public void setGerAmountListener(OnAmountListener listener) {
        this.mListener = listener;
    }
    //菜单列表监听事件
    private void setMenuListener(Viewholder holder, final int s) {
        //删除订单
        holder.order_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notelistAndNum.remove(s);
                notifyItemRemoved(s);
                //删除item坑
                //必须调用这行代码
                notifyItemRangeChanged(s, notelistAndNum.size());
            }
        });
    }


    @Override
    public int getItemCount() {
        return notelistAndNum.size();
    }

    public static class Viewholder extends ViewHolder {
        ImageView recycler_image_notetype, recycler_image_menu;
        TextView order_data_item_sum, order_data_title,item_order_price,order_data_single_price,order_delete;

        public Viewholder(View itemView) {
            super(itemView);
            recycler_image_menu = (ImageView) itemView.findViewById(R.id.order_data_icon);
            order_delete = (TextView) itemView.findViewById(R.id.order_delete);
            order_data_title = (TextView) itemView.findViewById(R.id.order_data_title);
            order_data_item_sum = (TextView) itemView.findViewById(R.id.order_data_item_sum);
            item_order_price = (TextView) itemView.findViewById(R.id.item_order_price);
            order_data_single_price = (TextView) itemView.findViewById(R.id.order_data_single_price);
        }
    }
}
