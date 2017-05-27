package com.campus.android.message;

import android.util.Log;

import com.campus.android.message.model.MessageModel;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by lebron on 17-5-27.
 */

public class IMessageImpl implements IMessage {
    public static final int TYPE_NEWS = 0;
    public static final int TYPE_NOTIFICATION = 1;

    @Override
    public void getNews(final OnGetMessageListListener listener) {
        try {
            BmobQuery<MessageModel> bmobQuery = new BmobQuery<MessageModel>();
            bmobQuery.addWhereEqualTo("type", TYPE_NEWS);
            bmobQuery.findObjects(new FindListener<MessageModel>() {
                @Override
                public void done(List<MessageModel> list, BmobException e) {
                    if (e == null) {
                        Log.i("Lebron", "查询成功 "+list.size());
                        if (listener != null) {
                            listener.onGetSuccess(list);
                        }
                    } else {
                        Log.i("Lebron", "查询失败：" + e.getMessage());
                        if (listener != null) {
                            listener.onGetSuccess(new ArrayList<MessageModel>());
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onGetSuccess(new ArrayList<MessageModel>());
            }
        }
    }

    @Override
    public void getNotification(final OnGetMessageListListener listener) {
        try {
            BmobQuery<MessageModel> bmobQuery = new BmobQuery<MessageModel>();
            bmobQuery.addWhereEqualTo("type", TYPE_NOTIFICATION);
            bmobQuery.findObjects(new FindListener<MessageModel>() {
                @Override
                public void done(List<MessageModel> list, BmobException e) {
                    if (e == null) {
                        Log.i("Lebron", "查询成功 " + list.size());
                        List<MessageModel> finalList = new ArrayList<MessageModel>();
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).isTop()) {
                                finalList.add(list.get(i));
                                list.remove(i);
                            }
                        }
                        finalList.addAll(list);
                        if (listener != null) {
                            listener.onGetSuccess(finalList);
                        }
                    } else {
                        Log.i("Lebron", "查询失败：" + e.getMessage());
                        if (listener != null) {
                            listener.onGetSuccess(new ArrayList<MessageModel>());
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onGetSuccess(new ArrayList<MessageModel>());
            }
        }
    }
}
