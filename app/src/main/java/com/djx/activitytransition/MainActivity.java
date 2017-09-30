package com.djx.activitytransition;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.TransitionSet;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends Activity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup mGroup;
    private RadioGroup mGroupType;
    private TextView mHello;
    private ImageView mImage;

    public static final String KEY = "type";
    public static final int TYPE_ENTER_EXIT = 0;
    public static final int TYPE_SHARED = 1;
    public static final int TYPE_NONE = 2;
    private int mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGroup = findViewById(R.id.group);
        mGroupType = findViewById(R.id.group_type);
        mHello = findViewById(R.id.hello);
        mImage = findViewById(R.id.image);

        mGroup.setOnCheckedChangeListener(this);
        mGroupType.setOnCheckedChangeListener(this);
    }

    public void onClick(View v) {
        switch (mGroup.getCheckedRadioButtonId()) {
            case R.id.text: {
                Intent intent = new Intent(this, SecondActivity.class);

                ActivityOptions options;
                switch (mType) {
                    case TYPE_ENTER_EXIT:
//                        getWindow().setEnterTransition(new Explode());
                        intent.putExtra(KEY, TYPE_ENTER_EXIT);
                        options = ActivityOptions.makeSceneTransitionAnimation(this);//TODO ok
//                        startActivity(intent);//TODO 这样的话，目标是不会进入不会有定制动画
//                        options = null;
//                        return;
                        break;
                    case TYPE_NONE:
//                        getWindow().setEnterTransition(null);
//                        getWindow().setExitTransition(new Explode());
//                        intent.putExtra(KEY, TYPE_NONE);
//                        options = ActivityOptions.makeSceneTransitionAnimation(this);
                        options = null;
//                        options = ActivityOptions.make
                        break;
                    case TYPE_SHARED:
//                        TransitionSet set = new TransitionSet();
//                        set.addTransition(new ChangeTransform());
//                        set.addTransition(new ChangeBounds());
//                        set.addTransition(new ChangeClipBounds());
                        intent.putExtra(KEY, TYPE_SHARED);
                        options = ActivityOptions.makeSceneTransitionAnimation(this, mHello, "test_text");
                        break;
                    default:
                        return;
                }
                startActivity(intent, options != null ? options.toBundle() : null);
            }
            break;
            case R.id.picture:
                Intent intent = new Intent(this, SecondActivity.class);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, mImage, "test_image");//TODO 只文本

                startActivity(intent, options.toBundle());
                break;
            case R.id.text_picture: {
                Intent intent1 = new Intent(this, SecondActivity.class);
                ActivityOptions options1 = ActivityOptions.makeSceneTransitionAnimation(this,
                        new Pair<View, String>(mImage, "test_image"), new Pair<View, String>(mHello, "test_text")); //TODO 文本加文字
                startActivity(intent1, options1.toBundle());
            }
            break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (radioGroup == mGroup) {
            switch (i) {
                case R.id.text:
                    mHello.setVisibility(View.VISIBLE);
                    mImage.setVisibility(View.GONE);
                    setTypeEnabled(true);
                    break;
                case R.id.picture:
                    mHello.setVisibility(View.GONE);
                    mImage.setVisibility(View.VISIBLE);
                    setTypeEnabled(false);
                    break;
                case R.id.text_picture:
                    mHello.setVisibility(View.VISIBLE);
                    mImage.setVisibility(View.VISIBLE);
                    setTypeEnabled(false);
                    break;
            }
        } else if (radioGroup == mGroupType) {
            switch (i) {
                case R.id.enter_exit:
                    mType = TYPE_ENTER_EXIT;
                    break;
                case R.id.shared:
                    mType = TYPE_SHARED;
                    break;
                case R.id.none:
                    mType = TYPE_NONE;
                    break;
            }
        }
    }

    private void setTypeEnabled(boolean f) {
        for (int i = 0; i < mGroupType.getChildCount(); i++) {
            mGroupType.getChildAt(i).setEnabled(f);
        }
    }
}
