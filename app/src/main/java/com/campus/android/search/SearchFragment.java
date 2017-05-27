package com.campus.android.search;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.campus.android.R;
import com.campus.android.common.base.BaseFragment;
import com.campus.android.common.utils.SharedPreferencesUtils;
import com.campus.android.search.interactor.IScore;
import com.campus.android.search.interactor.IScoreImpl;
import com.campus.android.search.model.MainItem;
import com.campus.android.search.model.UserCourseModel;
import com.campus.android.user.LoginActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lebron on 17-5-27.
 */

public class SearchFragment extends BaseFragment {
    @BindView(R.id.main_recyclerview)
    RecyclerView mRecyclerview;
    private List<MainItem> mDatas;
    private BaseQuickAdapter<MainItem> mAdapter;

    @Override
    protected int getContentViewID() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initViewsAndData(View view) {
        initData();
        initRecyclerView();
        getData();
    }

    private void initRecyclerView() {
        mAdapter = new BaseQuickAdapter<MainItem>(R.layout.main_item, mDatas) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, MainItem mainItem) {
                baseViewHolder.setBackgroundRes(R.id.storage_category_icon, mainItem.getIcon());
                baseViewHolder.setText(R.id.storage_category_title, mainItem.getTitle());
                //baseViewHolder.setText(R.id.storage_category_size, mainItem.getNum() + "节课");
                baseViewHolder.setText(R.id.storage_category_count, mainItem.getDescribe());
                baseViewHolder.setText(R.id.storage_category_description, "共发现" + mainItem.getNum() + "个对应事件");
            }
        };
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                switch (i) {
                    case 0:
                        break;
                    case 1:
                        if (SharedPreferencesUtils.getUserLogined()) {
                            ExamActivity.start(getActivity());
                        } else {
                            Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                            LoginActivity.start(getActivity());
                        }
                        break;
                    case 2:
                        if (SharedPreferencesUtils.getUserLogined()) {
                            ScoreActivity.start(getActivity());
                        } else {
                            Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
                            LoginActivity.start(getActivity());
                        }
                        break;
                }
            }
        });
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerview.setAdapter(mAdapter);
    }

    private void getData() {
        new IScoreImpl().getExamListSize(true, new IScore.OnGetScoreListener() {
            @Override
            public void onFinished(List<UserCourseModel> list) {
                if (mDatas != null && mDatas.size() == 3) {
                    mDatas.get(2).setNum(list.size());
                    mAdapter.notifyItemChanged(2);
                }
            }
        });
        new IScoreImpl().getExamListSize(false, new IScore.OnGetScoreListener() {
            @Override
            public void onFinished(List<UserCourseModel> list) {
                if (mDatas != null && mDatas.size() == 3) {
                    mDatas.get(1).setNum(list.size());
                    mAdapter.notifyItemChanged(1);
                }
            }
        });
    }

    private void initData() {
        mDatas = new ArrayList<>();
        MainItem mainItem = new MainItem();
        mainItem.setIcon(R.mipmap.course_ic);
        mainItem.setTitle("课程");
        mainItem.setDescribe("课程概要");
        mainItem.setContent("计算机组成原理|C++|数据库设计");
        mainItem.setNum(0);

        MainItem mainItem1 = new MainItem();
        mainItem1.setIcon(R.mipmap.exam_ic);
        mainItem1.setTitle("考试");
        mainItem1.setDescribe("考试安排");
        mainItem1.setContent("操作系统|软件测试与开发");
        mainItem1.setNum(0);

        MainItem mainItem2 = new MainItem();
        mainItem2.setIcon(R.mipmap.score_ic);
        mainItem2.setTitle("成绩");
        mainItem2.setDescribe("成绩查询");
        mainItem2.setContent("新视野英语");
        mainItem2.setNum(0);
        mDatas.add(mainItem);
        mDatas.add(mainItem1);
        mDatas.add(mainItem2);
    }
}
