package com.fatchao.gangedrecyclerview.fragment;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fatchao.gangedrecyclerview.other.CheckListener;
import com.fatchao.gangedrecyclerview.adapter.ClassifyDetailAdapter;
import com.fatchao.gangedrecyclerview.other.ItemHeaderDecoration;
import com.fatchao.gangedrecyclerview.R;
import com.fatchao.gangedrecyclerview.other.RvListener;
import com.fatchao.gangedrecyclerview.bean.SortBean;
import com.fatchao.gangedrecyclerview.presenter.SortDetailPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 右边部分，分类
 */
public class SortDetailFragment extends BaseFragment<SortDetailPresenter, String> implements CheckListener {
    private RecyclerView mRv;
    private ClassifyDetailAdapter mAdapter;
    private GridLayoutManager mManager;
    private List<SortBean> mDatas = new ArrayList<>();
    private ItemHeaderDecoration mDecoration;
    private boolean move = false;
    private int mIndex = 0;
    private CheckListener checkListener;

    @Override
    protected void getData() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sort_detail;
    }

    @Override
    protected void initCustomView(View view) {
        mRv = (RecyclerView) view.findViewById(R.id.rv);
    }

    @Override
    protected void initListener() {
        mRv.addOnScrollListener(new RecyclerViewListener());
    }

    @Override
    protected SortDetailPresenter initPresenter() {
        showRightPage(1);
        mManager = new GridLayoutManager(mContext, 3);

        // 一般有标题才会设置
        mManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mDatas.get(position).isTitle() ? 3 : 1;// 如果是标题，占 3 列
            }
        });
        mRv.setLayoutManager(mManager);
        mAdapter = new ClassifyDetailAdapter(mContext, mDatas, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                String content = "";
                switch (id) {
                    case R.id.root:
                        content = "title";
                        break;
                    case R.id.content:
                        content = "content";
                        break;

                }

                // Snackbar 比 Toast 更复杂
                Snackbar snackbar = Snackbar.make(mRv, "当前点击的是" + content + ":" + mDatas.get(position).getName(), Snackbar.LENGTH_SHORT);
                View mView = snackbar.getView();
                mView.setBackgroundColor(Color.BLUE);
                TextView text = (TextView) mView.findViewById(android.support.design.R.id.snackbar_text);
                text.setTextColor(Color.WHITE);
                text.setTextSize(25);
                snackbar.show();
            }
        });

        mRv.setAdapter(mAdapter);
        mDecoration = new ItemHeaderDecoration(mContext, mDatas);// 带头部的分割线
        mRv.addItemDecoration(mDecoration);
        mDecoration.setCheckListener(checkListener);// 选中状态
        initData(mContext.getResources().getStringArray(R.array.pill));// 0-14 个分组 ， 每个分组都有 10个条目
        return new SortDetailPresenter();
    }

    private void initData(final String[] data) {
        for (int i = 0; i < data.length; i++) {// 多少个分组
            SortBean titleBean = new SortBean(String.valueOf(i));
            titleBean.setTitle(true);//头部设置为true
            titleBean.setTag(String.valueOf(i));
            mDatas.add(titleBean);
            for (int j = 0; j < 10; j++) { // 每个分组有 10 个
                SortBean sortBean = new SortBean(String.valueOf(i + "行" + j + "个"));
                sortBean.setTag(String.valueOf(i));// 标签是和分组是一样的
                mDatas.add(sortBean);
            }

        }
        mAdapter.notifyDataSetChanged();
        mDecoration.setData(mDatas);// addItemDecoration，分割线也是有数据的！
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void refreshView(int code, String data) {

    }

    /**
     * Fragment 移动条目的位置，Activity 调用
     */
    public void setData(int n) {
        mIndex = n;
        mRv.stopScroll();
        smoothMoveToPosition(n);// 移动到合适的位置
    }

    /**
     * 监听选中的位置（左边和右边）
     */
    public void setListener(CheckListener listener) {
        this.checkListener = listener;
    }

    private void smoothMoveToPosition(int n) {
        int firstItem = mManager.findFirstVisibleItemPosition();
        int lastItem = mManager.findLastVisibleItemPosition();
        Log.e("chris", "first--->" + String.valueOf(firstItem));
        Log.e("chris", "last--->" + String.valueOf(lastItem));
        if (n <= firstItem) {// 在第一个可视条目之前
            mRv.scrollToPosition(n);
        } else if (n <= lastItem) {// 在第一个可视条目之后
            Log.e("chris", "pos---->" + String.valueOf(n)+"--VS--"+firstItem);
            int top = mRv.getChildAt(n - firstItem).getTop();
            Log.e("chris", "top---->" + String.valueOf(top));
            mRv.scrollBy(0, top);// 移动到底n个位置，把他置为第一个可视条目
        } else {// 在最后一个可视条目之后
            mRv.scrollToPosition(n);// 直接 Recycle 移动到当前的条目
            move = true;
        }
    }

    @Override
    public void check(int position, boolean isScroll) {
        checkListener.check(position, isScroll);
    }

    private class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                move = false;
                int n = mIndex - mManager.findFirstVisibleItemPosition();
                Log.e("chris","n---->" +  String.valueOf(n));
                if (0 <= n && n < mRv.getChildCount()) {
                    int top = mRv.getChildAt(n).getTop();
                    Log.e("chris","top--->" + String.valueOf(top));
                    mRv.smoothScrollBy(0, top);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (move ){
                move = false;
                int n = mIndex - mManager.findFirstVisibleItemPosition();
                if ( 0 <= n && n < mRv.getChildCount()){
                    int top = mRv.getChildAt(n).getTop();
                    mRv.scrollBy(0, top);
                }
            }
        }
    }

}
