package com.campus.android.search.interactor;

import com.campus.android.search.model.CourseModel;
import com.campus.android.search.model.UserCourseModel;

import java.util.List;

/**
 * Created by LeBron on 2017/5/28.
 */

public interface ICourse {
    void getCourseList(OnGetCourseListener listener);

    void getCourseListSize(OnGetCourseSizeListener listener);

    interface OnGetCourseListener {
        void onFinished(List<CourseModel> list);
    }

    interface OnGetCourseSizeListener {
        void onFinished(List<UserCourseModel> list);
    }
}
