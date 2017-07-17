package com.fatchao.gangedrecyclerview.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.fatchao.gangedrecyclerview.R;
import com.fatchao.gangedrecyclerview.other.RvHolder;
import com.fatchao.gangedrecyclerview.other.RvListener;
import com.fatchao.gangedrecyclerview.bean.SortBean;
import java.util.List;

public class ClassifyDetailAdapter extends RvAdapter<SortBean> {

    public ClassifyDetailAdapter(Context context, List<SortBean> list, RvListener listener) {
        super(context, list, listener);
    }

    /**
     *  getLayoutId 是自己写的
     */
    @Override
    protected int getLayoutId(int viewType) {
        return viewType == 0 ? R.layout.item_title : R.layout.item_classify_detail;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).isTitle() ? 0 : 1;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new ClassifyHolder(view, viewType, listener);
    }

    private class ClassifyHolder extends RvHolder<SortBean> {
        TextView tvCity;
        ImageView avatar;
        TextView tvTitle;

        private ClassifyHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);
            switch (type) {
                case 0:
                    tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
                    break;
                case 1:
                    tvCity = (TextView) itemView.findViewById(R.id.tvCity);
                    avatar = (ImageView) itemView.findViewById(R.id.ivAvatar);
                    break;
            }
        }

        @Override
        public void bindHolder(SortBean sortBean, int position) {
            int itemViewType = ClassifyDetailAdapter.this.getItemViewType(position);
            switch (itemViewType) {
                case 0:
                    tvTitle.setText("测试数据" + sortBean.getTag());
                    break;
                case 1:
                    tvCity.setText(sortBean.getName());
                    break;
            }
        }
    }
}