package com.beetrack.leandronanni.android_beetrack.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.beetrack.leandronanni.android_beetrack.R;

import adapters.NewsAdapterListener;
import adapters.NewsPagerAdapter;
import fragments.SavedNewsFragment;
import pojo.News;
import utils.Utils;

public class NewsActivity extends AppCompatActivity implements NewsAdapterListener {

    private NewsPagerAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        final TabLayout tabLayout = findViewById(R.id.news_tabs);

        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.news)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.saved)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new NewsPagerAdapter(getSupportFragmentManager());

        final ViewPager mViewPager = findViewById(R.id.news_pager);
        mViewPager.setAdapter(adapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //Do nothing
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //Do nothing
            }
        });

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        mViewPager.setOffscreenPageLimit(2);

    }

    @Override
    public void saveNews(final News news, final boolean isSaved) {
        final int index = Utils.getInstance(this).saveNews(news, isSaved);
        adapter.updateFragments(index);
    }


    @Override
    public void openNews(final News news) {

        if (TextUtils.isEmpty(news.getUrl())) {

            Toast.makeText(this, getString(R.string.no_url), Toast.LENGTH_LONG).show();

        } else {
            final Intent intent = new Intent(this, WebViewActivity.class);

            intent.putExtra(WebViewActivity.URL, news.getUrl());

            startActivity(intent);
        }
    }
}
