package com.djx.activitytransition;

import android.app.ActivityOptions;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWindow().setExitTransition(new Slide(Gravity.LEFT));
                startActivity(new Intent(SecondActivity.this, ThirdActivity.class),
                        ActivityOptions.makeSceneTransitionAnimation(SecondActivity.this).toBundle());
            }
        });


        int type = getIntent().getIntExtra(MainActivity.KEY, MainActivity.TYPE_ENTER_EXIT);
        switch (type) {
            case MainActivity.TYPE_ENTER_EXIT:
                getWindow().setEnterTransition(/*new Slide(Gravity.RIGHT)*/new Explode());
//                getWindow().setExitTransition(new Explode());//TODO 为什么不能同时设置？
                break;
            case MainActivity.TYPE_NONE:
//                getWindow().setEnterTransition(null);
//                getWindow().setExitTransition(new Explode());

                /*getWindow().setEnterTransition(new Slide(Gravity.RIGHT));
                getWindow().setExitTransition(new Slide(Gravity.BOTTOM));//TODO 为什么设置不了退出的（总是reverse进入，不设enter，就reverse默认的enter）
*/
//                getWindow().setEnterTransition(new Explode());
//                getWindow().setExitTransition(new Slide());
                break;
            case MainActivity.TYPE_SHARED:

                TransitionSet set = new TransitionSet();
                        set.addTransition(new ChangeBounds().addTarget("test_text").addTarget(R.id.hello));
                        set.addTransition(new TextSizeTransition().addTarget("test_text").addTarget(R.id.hello));

//                        set.addTransition(new ChangeClipBounds());
//                getWindow().setSharedElementEnterTransition(/*set*/new TextSizeTransition().addTarget("test_text"));
                getWindow().setEnterTransition(new Fade());
                getWindow().setSharedElementEnterTransition(set);
                setEnterSharedElementCallback(new SharedElementCallback() {
                    @Override
                    public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
//                        Log.i(TAG, "=== onSharedElementEnd(List<String>, List<View>, List<View>)");
                        TextView textView = (TextView) sharedElements.get(0);

                        // Record the TextView's old width/height.
                        int oldWidth = textView.getMeasuredWidth();
                        int oldHeight = textView.getMeasuredHeight();

                        // Setup the TextView's end values.
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, 90);
//                        textView.setTextColor(mEndColor);

                        // Re-measure the TextView (since the text size has changed).
                        int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                        textView.measure(widthSpec, heightSpec);

                        // Record the TextView's new width/height.
                        int newWidth = textView.getMeasuredWidth();
                        int newHeight = textView.getMeasuredHeight();

                        // Layout the TextView in the center of its container, accounting for its new width/height.
                        int widthDiff = newWidth - oldWidth;
                        int heightDiff = newHeight - oldHeight;
                        textView.layout(textView.getLeft() - widthDiff / 2, textView.getTop() - heightDiff / 2,
                                textView.getRight() + widthDiff / 2, textView.getBottom() + heightDiff / 2);
                    }
                });

                break;
        }

        final TextView hello = findViewById(R.id.hello);

//        setEnterSharedElementCallback(new SharedElementCallback() {
//            @Override
//            public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
//                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
//                hello.setText("world");
//            }
//        });
    }

//    @Override
//    public void onBackPressed() {
//        finishAfterTransition(); //TODO reverse启动动画，所以设置exit无效了！！
//    }
}
