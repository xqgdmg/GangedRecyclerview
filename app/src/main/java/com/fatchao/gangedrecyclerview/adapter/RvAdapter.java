package com.fatchao.gangedrecyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fatchao.gangedrecyclerview.other.RvHolder;
import com.fatchao.gangedrecyclerview.other.RvListener;

import java.util.List;

@SuppressWarnings("unchecked")
public abstract class RvAdapter<T> extends RecyclerView.Adapter<RvHolder> {
    protected List<T> list;
    protected Context mContext;
    protected RvListener listener;
    protected LayoutInflater mInflater;
    private RecyclerView mRecyclerView;

    public RvAdapter(Context context, List<T> list, RvListener listener) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.list = list;
        this.listener = listener;
    }

    /**
     * onCreateViewHolder 子类以后都不用写了
     */
    @Override
    public RvHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(getLayoutId(viewType), parent, false);
        return getHolder(view, viewType);
    }

    /**
     * onBindViewHolder 子类以后都不用写了
     */
    @Override
    public void onBindViewHolder(RvHolder holder, int position) {
        holder.bindHolder(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 固定返回 0
     */
    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    // ----------------------------------以下子类需要重写的方法----------------------------------//

    protected abstract int getLayoutId(int viewType);// 布局

    protected abstract RvHolder getHolder(View view, int viewType);// Holder

}
