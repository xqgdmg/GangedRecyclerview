package com.fatchao.gangedrecyclerview.adapter;


import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.fatchao.gangedrecyclerview.R;
import com.fatchao.gangedrecyclerview.other.RvHolder;
import com.fatchao.gangedrecyclerview.other.RvListener;

import java.util.List;

/**
 * 左边分类列表
 *
 * 最上层的封装是 RvHolder 和 RvAdapter（感觉过于复杂，很繁琐，好处是只是封装的人麻烦一次，以后用的多的时候就很方便了）
 */
public class SortAdapter extends RvAdapter<String> {

    private int checkedPosition;// 选中的位置

    /**
     * 构造方法，必须添加条目点击的监听
     *
     * 奇怪的是 listener 并没有保存，他是怎么回调出去的（保存到了父类RvAdapter，再去从父类拿的 ）
     */
    public SortAdapter(Context context, List<String> list, RvListener listener) {
        super(context, list, listener);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_sort_list;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new SortHolder(view, viewType, listener);// listener 是 RvAdapter 的字段，构造方法传入的监听，直接传到了父类，现在可以直接拿出来用
    }

    /**
     * 设置左边分类的选中状态
     */
    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
        notifyDataSetChanged();
    }

    /**
     * RvHolder 里面设置了item点击监听
     */
    private class SortHolder extends RvHolder<String> {

        private TextView tvName;
        private View mView;

        SortHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);// 传给 RvHolder 的item 点击监听
            this.mView = itemView;
            tvName = (TextView) itemView.findViewById(R.id.tv_sort);
        }

        @Override
        public void bindHolder(String string, int position) {
            tvName.setText("数据" + string);
            if (position == checkedPosition) {
                mView.setBackgroundColor(Color.parseColor("#f3f3f3"));
                tvName.setTextColor(Color.parseColor("#0068cf"));
            } else {
                mView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tvName.setTextColor(Color.parseColor("#1e1d1d"));
            }
        }

    }
}
