package com.iksydk.interactivestory.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.iksydk.interactivestory.R;
import com.iksydk.interactivestory.model.Page;
import com.iksydk.interactivestory.model.Story;

import org.w3c.dom.Text;


public class StoryActivity extends ActionBarActivity {

    public static final String TAG = StoryActivity.class.getSimpleName();

    private Story mStory = new Story();
    private ImageView mImageView;
    private TextView mTextView;
    private Button mChoice1;
    private Button mChoice2;
    private String mName;
    private Page mPage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        Intent intent = getIntent();
        mName = intent.getStringExtra(getString(R.string.key_name));
        if(mName == null)
        {
            mName = "Friend";
        }
        Log.d(TAG, mName);

        mImageView = (ImageView) findViewById(R.id.storyImageView);
        mTextView = (TextView) findViewById(R.id.storyTextView);
        mChoice1 = (Button) findViewById(R.id.choiceButton1);
        mChoice2 = (Button) findViewById(R.id.choiceButton2);



        loadPage(0);
    }

    private void loadPage(int choice)
    {
        mPage = mStory.getPage(choice);

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), mPage.getImageId(), null);
        mImageView.setImageDrawable(drawable);

        String pageText = mPage.getText();
        // Add the name if placeholder included, Won't add if not placeholder
        pageText = String.format(pageText, mName);
        mTextView.setText(pageText);

        if(mPage.isFinal())
        {
            mChoice1.setVisibility(View.INVISIBLE);
            mChoice2.setText("PLAY AGAIN");
            mChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        else {
            mChoice1.setText(mPage.getChoice1().getText());
            mChoice2.setText(mPage.getChoice2().getText());

            mChoice1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int nextPage = mPage.getChoice1().getNextPage();
                    loadPage(nextPage);
                }
            });

            mChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int nextPage = mPage.getChoice2().getNextPage();
                    loadPage(nextPage);
                }
            });
        }
    }
}
