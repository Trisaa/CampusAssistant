package com.campus.android.search.interactor;

import com.campus.android.search.model.UserCourseModel;

import java.util.List;

/**
 * Created by LeBron on 2017/5/27.
 */

public interface IScore {
    void getScoreByCourse(String keyword, OnGetScoreListener listener);

    void getExamList(boolean scoreOrexam, OnGetScoreListener listener);

    void getExamListSize(boolean scoreOrExam, OnGetScoreListener listener);

    interface OnGetScoreListener {
        void onFinished(List<UserCourseModel> list);
    }
}
