package com.campus.android.search.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by LeBron on 2017/5/27.
 */

public class UserCourseModel extends BmobObject {
    private String studentId;
    private CourseModel course;
    private double score;
    private String location;
    private String courseId;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public CourseModel getCourse() {
        return course;
    }

    public void setCourse(CourseModel course) {
        this.course = course;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
