package com.djx.activitytransition;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

public class MainActivity extends Activity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup mGroupType;
    private RadioGroup mGroupSharedType;

    public static final String KEY_TRANSITION = "transition_type";
    public static final String KEY_SHARED_TYPE = "shared_type";

    public static final int TYPE_ENTER_EXIT = 0;
    public static final int TYPE_SHARED = 1;
    public static final int TYPE_NONE = 2;
    private int mTransitionType;

    public static final int TYPE_SHARED_TEXT = 0;
    public static final int TYPE_SHARED_IMAGE = 1;
    public static final int TYPE_SHARED_TEXT_IMAGE = 2;
    private int mSharedType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGroupType = findViewById(R.id.group_type);
        mGroupSharedType = findViewById(R.id.shared_group);

        mGroupSharedType.setOnCheckedChangeListener(this);
        mGroupType.setOnCheckedChangeListener(this);

        mGroupType.check(R.id.none);
        mGroupSharedType.check(R.id.text);
    }

    public void onClick(View v) {
        Intent intent = new Intent(this, SecondActivity.class);
        ActivityOptions options = null;
        if (mTransitionType == TYPE_ENTER_EXIT) {
            options = ActivityOptions.makeSceneTransitionAnimation(this);
        }

        intent.putExtra(KEY_TRANSITION, mTransitionType);
        intent.putExtra(KEY_SHARED_TYPE, mSharedType);
        startActivity(intent, options != null ? options.toBundle() : null);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (radioGroup == mGroupType) {
            switch (i) {
                case R.id.enter_exit:
                    mTransitionType = TYPE_ENTER_EXIT;
                    setTypeEnabled(false);
                    break;
                case R.id.shared:
                    mTransitionType = TYPE_SHARED;
                    setTypeEnabled(true);
                    break;
                case R.id.none:
                    mTransitionType = TYPE_NONE;
                    setTypeEnabled(false);
                    break;
            }
        } else if (radioGroup == mGroupSharedType) {
            switch (i) {
                case R.id.text:
                    mSharedType = TYPE_SHARED_TEXT;
                    break;
                case R.id.picture:
                    mSharedType = TYPE_SHARED_IMAGE;
                    break;
                case R.id.text_picture:
                    mSharedType = TYPE_SHARED_TEXT_IMAGE;
                    break;
            }
        }
    }

    private void setTypeEnabled(boolean f) {
        for (int i = 0; i < mGroupSharedType.getChildCount(); i++) {
            mGroupSharedType.getChildAt(i).setEnabled(f);
        }
    }
}
