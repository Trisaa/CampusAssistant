package com.campus.android.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.campus.android.R;
import com.campus.android.common.base.BaseActivity;
import com.campus.android.search.interactor.ICourse;
import com.campus.android.search.interactor.ICourseImpl;
import com.campus.android.search.model.CourseModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by LeBron on 2017/5/28.
 */

public class CourseActivity extends BaseActivity {
    @BindView(R.id.course_recyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.course_progressbar)
    ProgressBar mProgressbar;

    private List<CourseModel> mDatas;
    private BaseQuickAdapter<CourseModel> mAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, CourseActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_course;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRecyclerView();
        getData();
    }

    private void getData() {
        new ICourseImpl().getCourseList(new ICourse.OnGetCourseListener() {
            @Override
            public void onFinished(List<CourseModel> list) {
                if (mDatas == null) {
                    mDatas = new ArrayList<CourseModel>();
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
        mAdapter = new BaseQuickAdapter<CourseModel>(R.layout.course_item, mDatas) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, CourseModel courseModel) {
                if (courseModel != null) {
                    Log.i("Lebron", "model " + courseModel.getName());
                    baseViewHolder.setText(R.id.course_name, courseModel.getName());
                    baseViewHolder.setText(R.id.course_location, courseModel.getLocation());
                }
            }
        };
        mRecyclerview.setLayoutManager(new GridLayoutManager(this, 5));
        mAdapter.setEmptyView(getLayoutInflater().inflate(R.layout.common_empty_layout, (ViewGroup) mRecyclerview.getParent(), false));
        mRecyclerview.setAdapter(mAdapter);
    }

}
