package com.campus.android.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.campus.android.R;
import com.campus.android.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lebron on 17-5-27.
 */

public class ScoreActivity extends BaseActivity implements TextView.OnEditorActionListener {
    @BindView(R.id.edit_search)
    EditText mSearchEdit;
    @BindView(R.id.btn_clear)
    ImageButton mClearButton;
    @BindView(R.id.search_recycler)
    RecyclerView mRecyclerView;

    private String mSearchContent;

    public static void start(Context context) {
        Intent intent = new Intent(context, ScoreActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_score;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addListener();
    }

    private void addListener() {
        mSearchEdit.setOnEditorActionListener(this);
        mSearchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = mSearchEdit.getText().toString();
                if (text.length() > 0) {
                    mClearButton.setVisibility(View.VISIBLE);
                } else {
                    mClearButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.btn_clear)
    public void clear() {
        mSearchEdit.setText("");
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            mSearchContent = mSearchEdit.getText().toString();
        }
        return true;
    }
}
