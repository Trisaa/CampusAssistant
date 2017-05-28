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
 * Created by LeBron on 2017/5/28.
 */

public class ICourseImpl implements ICourse {
    private int i = 0;
    private List<CourseModel> mDatas = new ArrayList<>();

    @Override
    public void getCourseList(final OnGetCourseListener listener) {
        for (int i = 0; i < 20; i++) {
            CourseModel courseModel = null;
            mDatas.add(courseModel);
        }
        BmobQuery<UserCourseModel> query0 = new BmobQuery<>();
        query0.addWhereEqualTo("studentId", SharedPreferencesUtils.getString(SharedPreferencesUtils.KEY_STUDENT_ID, ""));
        query0.findObjects(new FindListener<UserCourseModel>() {
            @Override
            public void done(final List<UserCourseModel> groupList, BmobException e) {
                if (e == null) {
                    Log.i("Lebron", "获取用户课程表成功" + groupList.size());
                    i = 0;
                    for (UserCourseModel userCourseModel : groupList) {
                        BmobQuery<CourseModel> query = new BmobQuery<>();
                        query.addWhereEqualTo("objectId", userCourseModel.getCourseId());
                        query.findObjects(new FindListener<CourseModel>() {
                            @Override
                            public void done(List<CourseModel> list, BmobException e) {
                                if (e == null) {
                                    Log.i("Lebron", "获取课程详情成功" + list.size());
                                    CourseModel model = list.get(0);
                                    try {
                                        String[] array = model.getTime().split("&");
                                        for (int i = 0; i < array.length; i++) {
                                            String[] finalArray = array[i].split("#");
                                            int index = 5 * (Integer.valueOf(finalArray[1]) - 1) + Integer.valueOf(finalArray[0]) - 1;
                                            Log.i("Lebron", " valid index " + index);
                                            mDatas.set(index, model);
                                        }
                                    } catch (Exception error) {
                                        Log.i("Lebron", "获取课程表失败 " + error.toString());
                                        if (listener != null) {
                                            listener.onFinished(new ArrayList<CourseModel>());
                                        }
                                    }
                                    i++;
                                    if (listener != null & i >= groupList.size()) {
                                        Log.i("Lebron", " list size " + mDatas.size());
                                        listener.onFinished(mDatas);
                                    }
                                } else {
                                    if (listener != null) {
                                        Log.i("Lebron", "获取课程表失败 ");
                                        listener.onFinished(new ArrayList<CourseModel>());
                                    }
                                }
                            }
                        });
                    }
                } else {
                    if (listener != null) {
                        Log.i("Lebron", "获取课程表失败 ");
                        listener.onFinished(new ArrayList<CourseModel>());
                    }
                }
            }
        });
    }

    @Override
    public void getCourseListSize(final OnGetCourseSizeListener listener) {
        BmobQuery<UserCourseModel> query0 = new BmobQuery<>();
        query0.addWhereEqualTo("studentId", SharedPreferencesUtils.getString(SharedPreferencesUtils.KEY_STUDENT_ID, ""));
        query0.findObjects(new FindListener<UserCourseModel>() {
            @Override
            public void done(List<UserCourseModel> list, BmobException e) {
                if (e == null) {
                    if (listener != null) {
                        listener.onFinished(list);
                    }
                } else {
                    listener.onFinished(new ArrayList<UserCourseModel>());
                }
            }
        });
    }
}
