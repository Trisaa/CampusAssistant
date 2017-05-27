package com.campus.android.message;

import com.campus.android.message.model.MessageModel;

import java.util.List;

/**
 * Created by lebron on 17-5-27.
 */

public interface IMessage {
    void getNews(OnGetMessageListListener listener);

    void getNotification(OnGetMessageListListener listener);

    interface OnGetMessageListListener {
        void onGetSuccess(List<MessageModel> list);
    }
}
