package com.campus.android.user;


import com.campus.android.user.model.UserModel;

/**
 * Created by lebron on 17-4-19.
 */

public interface ILogin {
    void doLogin(UserModel userModel, boolean loginOrQuery, LoginResponseListener listener);

    void doRegister(UserModel userModel, RegisterResponseListener listener);

    void doUpdate(UserModel userModel, UpdateResponseListener listener);

    interface LoginResponseListener {
        void OnLoginResponseSuccess(UserModel user);

        void onResponseFailed();
    }

    interface RegisterResponseListener {
        void OnRegisterResponseSuccess(String objectId);

        void onResponseFailed();
    }

    interface UpdateResponseListener {
        void onUpdateSuccess();

        void onUpdateFailed();
    }
}
