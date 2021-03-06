package com.campus.android.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.campus.android.R;
import com.campus.android.common.base.BaseActivity;
import com.campus.android.search.interactor.IScore;
import com.campus.android.search.interactor.IScoreImpl;
import com.campus.android.search.model.UserCourseModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lebron on 17-5-27.
 */

public class ScoreActivity extends BaseActivity {
    @BindView(R.id.search_progressbar)
    ProgressBar mProgressbar;
    @BindView(R.id.search_recycler)
    RecyclerView mRecyclerView;

    private List<UserCourseModel> mDatas;
    private BaseQuickAdapter<UserCourseModel> mAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, ScoreActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_score;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRecyclerView();
        getData();
    }

    private void getData() {
        new IScoreImpl().getExamList(true, new IScore.OnGetScoreListener() {
            @Override
            public void onFinished(List<UserCourseModel> list) {
                if (mDatas == null) {
                    mDatas = new ArrayList<UserCourseModel>();
                }
                mDatas.clear();
                mDatas.addAll(list);
                mAdapter.notifyDataSetChanged();
                mProgressbar.setVisibility(View.GONE);
            }
        });
        mProgressbar.setVisibility(View.VISIBLE);
    }

    private void initRecyclerView() {
        mDatas = new ArrayList<>();
        mAdapter = new BaseQuickAdapter<UserCourseModel>(R.layout.score_item, mDatas) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, UserCourseModel userCourseModel) {
                baseViewHolder.setText(R.id.score_item_name, userCourseModel.getCourse().getName());
                baseViewHolder.setText(R.id.score_item_score, "分数：" + userCourseModel.getScore());
                baseViewHolder.setText(R.id.score_item_teacher, "授课教师：" + userCourseModel.getCourse().getTeacher());
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.common_empty_layout, (ViewGroup) mRecyclerView.getParent(), false));
        mRecyclerView.setAdapter(mAdapter);
    }
}
