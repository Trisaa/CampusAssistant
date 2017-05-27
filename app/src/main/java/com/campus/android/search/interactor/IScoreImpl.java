package com.campus.android.search.interactor;

import android.util.Log;

import com.campus.android.common.utils.SharedPreferencesUtils;
import com.campus.android.search.model.CourseModel;
import com.campus.android.search.model.UserCourseModel;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by LeBron on 2017/5/27.
 */

public class IScoreImpl implements IScore {
    private List<UserCourseModel> mDatas = new ArrayList<>();
    private int i = 0;
    private int j = 0;

    @Override
    public void getScoreByCourse(String keyword, final OnGetScoreListener listener) {
        try {
            BmobQuery<CourseModel> bmobQuery = new BmobQuery<>();
            bmobQuery.addWhereEqualTo("name", keyword);
            bmobQuery.findObjects(new FindListener<CourseModel>() {
                @Override
                public void done(final List<CourseModel> groupList, BmobException e) {
                    if (e == null) {
                        Log.i("Lebron", "查询成功1 " + groupList.size());
                        i = 0;
                        for (CourseModel model : groupList) {
                            BmobQuery<UserCourseModel> query = new BmobQuery<>();
                            query.addWhereEqualTo("course", model);
                            query.include("course");
                            query.findObjects(new FindListener<UserCourseModel>() {
                                @Override
                                public void done(List<UserCourseModel> list, BmobException e) {
                                    if (e == null) {
                                        Log.i("Lebron", "查询成功2 " + list.size());
                                        if (list != null && list.size() > 0) {
                                            mDatas.addAll(list);
                                        }
                                        i++;
                                        if (listener != null && i >= groupList.size()) {
                                            Log.i("Lebron", "get success" + mDatas.size());
                                            listener.onFinished(mDatas);
                                        }
                                    }
                                }
                            });
                        }
                    } else {
                        Log.i("Lebron", "查询失败：" + e.getMessage());
                        if (listener != null) {
                            listener.onFinished(new ArrayList<UserCourseModel>());
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onFinished(new ArrayList<UserCourseModel>());
            }
        }
    }

    @Override
    public void getExamList(boolean scoreOrexam, final OnGetScoreListener listener) {
        final BmobQuery<UserCourseModel> query = new BmobQuery<>();
        query.addWhereEqualTo("studentId", SharedPreferencesUtils.getString(SharedPreferencesUtils.KEY_STUDENT_ID, ""));
        query.include("course");
        if (scoreOrexam) {
            query.addWhereExists("score");
        } else {
            query.addWhereDoesNotExists("score");
        }
        query.findObjects(new FindListener<UserCourseModel>() {
            @Override
            public void done(final List<UserCourseModel> groupList, BmobException e) {
                if (e == null) {
                    Log.i("Lebron", " 获取考试列表成功 " + groupList.size());
                    j = 0;
                    for (final UserCourseModel model : groupList) {
                        BmobQuery<CourseModel> query1 = new BmobQuery<CourseModel>();
                        query1.addWhereEqualTo("objectId", model.getCourseId());
                        query1.findObjects(new FindListener<CourseModel>() {
                            @Override
                            public void done(List<CourseModel> list, BmobException e) {
                                if (e == null && list.get(0) != null) {
                                    model.setCourse(list.get(0));
                                }
                                j++;
                                if (listener != null && j >= groupList.size()) {
                                    listener.onFinished(groupList);
                                }
                            }
                        });
                    }
                } else {
                    Log.i("Lebron", " 获取考试列表失败 ");
                    if (listener != null) {
                        listener.onFinished(new ArrayList<UserCourseModel>());
                    }
                }
            }
        });
    }

    @Override
    public void getExamListSize(boolean scoreOrExam, final OnGetScoreListener listener) {
        final BmobQuery<UserCourseModel> query = new BmobQuery<>();
        query.addWhereEqualTo("studentId", SharedPreferencesUtils.getString(SharedPreferencesUtils.KEY_STUDENT_ID, ""));
        query.include("course");
        if (scoreOrExam) {
            query.addWhereExists("score");
        } else {
            query.addWhereDoesNotExists("score");
        }
        query.findObjects(new FindListener<UserCourseModel>() {
            @Override
            public void done(List<UserCourseModel> list, BmobException e) {
                if (e == null) {
                    if (listener != null) {
                        listener.onFinished(list);
                    }
                } else {
                    if (listener != null) {
                        listener.onFinished(new ArrayList<UserCourseModel>());
                    }
                }
            }
        });
    }
}
