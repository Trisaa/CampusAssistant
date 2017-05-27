package com.campus.android.user;

import android.util.Log;


import com.campus.android.user.model.UserModel;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by lebron on 17-4-19.
 */

public class ILoginImpl implements ILogin {

    @Override
    public void doLogin(UserModel userModel, boolean loginOrQuery, final LoginResponseListener listener) {
        try {
            BmobQuery<UserModel> bmobQuery = new BmobQuery<UserModel>();
            bmobQuery.addWhereEqualTo("studentId", userModel.getStudentId());
            if (loginOrQuery) {
                bmobQuery.addWhereEqualTo("password", userModel.getPassword());
            }
            bmobQuery.findObjects(new FindListener<UserModel>() {
                @Override
                public void done(List<UserModel> list, BmobException e) {
                    if (e == null) {
                        Log.i("Lebron", "查询成功");
                        if (listener != null) {
                            listener.OnLoginResponseSuccess(list.get(0));
                        }
                    } else {
                        Log.i("Lebron", "查询失败：" + e.getMessage());
                        if (listener != null) {
                            listener.onResponseFailed();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onResponseFailed();
            }
        }
    }

    @Override
    public void doRegister(UserModel userModel, final RegisterResponseListener listener) {
        try {
            userModel.save(new SaveListener<String>() {
                @Override
                public void done(String objectId, BmobException e) {
                    if (e == null) {
                        Log.i("Lebron", "添加数据成功，返回objectId为：" + objectId);
                        if (listener != null) {
                            listener.OnRegisterResponseSuccess(objectId);
                        }
                    } else {
                        Log.i("Lebron", "创建数据失败：" + e.getMessage());
                        if (listener != null) {
                            listener.onResponseFailed();
                        }
                    }
                }
            });
        } catch (Exception e) {
            if (listener != null) {
                listener.onResponseFailed();
            }
        }
    }

    @Override
    public void doUpdate(UserModel userModel, final UpdateResponseListener listener) {
        try {
            userModel.update(userModel.getObjectId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        Log.i("Lebron", "更新成功:");
                        if (listener != null) {
                            listener.onUpdateSuccess();
                        }
                    } else {
                        Log.i("Lebron", "更新失败：" + e.getMessage());
                        if (listener != null) {
                            listener.onUpdateFailed();
                        }
                    }
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
