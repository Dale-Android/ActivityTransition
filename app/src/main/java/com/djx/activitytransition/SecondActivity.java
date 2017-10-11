package com.djx.activitytransition;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mText;
    private ImageView mImage;

    private int mTransitionType;
    private int mSharedType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mText = findViewById(R.id.text);
        mImage = findViewById(R.id.image);

        findViewById(R.id.start).setOnClickListener(this);

        mTransitionType = getIntent().getIntExtra(MainActivity.KEY_TRANSITION, MainActivity.TYPE_ENTER_EXIT);
        switch (mTransitionType) {
            case MainActivity.TYPE_ENTER_EXIT:
                mText.setVisibility(View.GONE);
                mImage.setVisibility(View.GONE);
                getWindow().setEnterTransition(new Explode());

                break;
            case MainActivity.TYPE_NONE:
                break;
            case MainActivity.TYPE_SHARED:
                mSharedType = getIntent().getIntExtra(MainActivity.KEY_SHARED_TYPE, 0);
                switch (mSharedType) {
                    case MainActivity.TYPE_SHARED_TEXT:
                        mText.setVisibility(View.VISIBLE);
                        mImage.setVisibility(View.GONE);
                        break;
                    case MainActivity.TYPE_SHARED_IMAGE:
                        mText.setVisibility(View.GONE);
                        mImage.setVisibility(View.VISIBLE);
                        break;
                    case MainActivity.TYPE_SHARED_TEXT_IMAGE:
                        mText.setVisibility(View.VISIBLE);
                        mImage.setVisibility(View.VISIBLE);
                        break;
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, ThirdActivity.class);
        ActivityOptions options = null;
        switch (mTransitionType) {
            case MainActivity.TYPE_ENTER_EXIT: {
                getWindow().setExitTransition(new Slide(Gravity.LEFT));
                options = ActivityOptions.makeSceneTransitionAnimation(this);
            }
            break;
            case MainActivity.TYPE_SHARED: {
                switch (mSharedType) {
                    case MainActivity.TYPE_SHARED_TEXT:
                        options = ActivityOptions.makeSceneTransitionAnimation(this, mText, "test_text");
                        break;
                    case MainActivity.TYPE_SHARED_IMAGE:
                        options = ActivityOptions.makeSceneTransitionAnimation(this, mImage, "test_image");
                        break;
                    case MainActivity.TYPE_SHARED_TEXT_IMAGE:
                        options = ActivityOptions.makeSceneTransitionAnimation(this,
                                new Pair<View, String>(mImage, "test_image"), new Pair<View, String>(mText, "test_text"));
                        break;
                }
            }
            break;
        }

        intent.putExtra(MainActivity.KEY_TRANSITION, mTransitionType);
        intent.putExtra(MainActivity.KEY_SHARED_TYPE, mSharedType);
        startActivity(intent, options != null ? options.toBundle() : null);
    }
}
