package com.djx.activitytransition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    private TextView mText;
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        mText = findViewById(R.id.text);
        mImage = findViewById(R.id.image);

        int transitionType = getIntent().getIntExtra(MainActivity.KEY_TRANSITION, 0);
        int sharedType = getIntent().getIntExtra(MainActivity.KEY_SHARED_TYPE, 0);
        switch (transitionType) {
            case MainActivity.TYPE_ENTER_EXIT:
                mText.setVisibility(View.GONE);
                mImage.setVisibility(View.GONE);
                getWindow().setEnterTransition(new Explode());
                break;
            case MainActivity.TYPE_SHARED:
                switch (sharedType) {
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
            case MainActivity.TYPE_NONE:
                break;
        }
    }
}
