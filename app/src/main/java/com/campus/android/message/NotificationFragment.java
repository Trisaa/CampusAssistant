package com.campus.android.message;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.campus.android.R;
import com.campus.android.common.base.BaseFragment;
import com.campus.android.message.model.MessageModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lebron on 17-5-27.
 */

public class NotificationFragment extends BaseFragment {
    @BindView(R.id.common_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.loading_progress)
    ProgressBar mProgressBar;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    private BaseQuickAdapter<MessageModel> mAdapter;
    private List<MessageModel> mDatas;

    @Override
    protected int getContentViewID() {
        return R.layout.common_recyclerview;
    }

    @Override
    protected void initViewsAndData(View view) {
        initRecyclerView();
        getData(true);
    }

    private void getData(boolean first) {
        new IMessageImpl().getNotification(new IMessage.OnGetMessageListListener() {
            @Override
            public void onGetSuccess(List<MessageModel> list) {
                if (mDatas == null) {
                    mDatas = new ArrayList<MessageModel>();
                }
                mDatas.clear();
                mDatas.addAll(list);
                mAdapter.notifyDataSetChanged();
                mProgressBar.setVisibility(View.GONE);
                mRefreshLayout.setVisibility(View.VISIBLE);
                mRefreshLayout.setRefreshing(false);
            }
        });
        if (first) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshLayout.setVisibility(View.GONE);
        }
    }

    private void initRecyclerView() {
        mDatas = new ArrayList<>();
        mAdapter = new BaseQuickAdapter<MessageModel>(R.layout.notification_item, mDatas) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, MessageModel messageModel) {
                baseViewHolder.setText(R.id.news_item_title_txv, messageModel.getTitle());
                baseViewHolder.setText(R.id.news_item_content_txv, messageModel.getContent());
                baseViewHolder.setText(R.id.news_item_date_txv, "发布于 " + messageModel.getUpdatedAt());
                baseViewHolder.getView(R.id.news_item_top_txv).setVisibility(messageModel.isTop() ? View.VISIBLE : View.GONE);
            }
        };
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                NotificationDetailActivity.start(getActivity(), mDatas.get(i));
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter.setEmptyView(getActivity().getLayoutInflater().inflate(R.layout.common_empty_layout, (ViewGroup) mRecyclerView.getParent(), false));
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(false);
            }
        });
    }
}
