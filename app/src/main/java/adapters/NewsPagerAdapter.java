package adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Observable;
import java.util.Observer;

import fragments.NewsFragment;
import fragments.SavedNewsFragment;
import observer.FragmentObserver;

public class NewsPagerAdapter extends FragmentPagerAdapter {

    private static final int PAGES = 2;

    private NewsFragment newsFragment;
    private SavedNewsFragment savedNewsFragment;

    private Observable mObservers = new FragmentObserver();

    public NewsPagerAdapter(final FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                if (newsFragment == null) {
                    newsFragment = new NewsFragment();
                    mObservers.addObserver(newsFragment);
                }
                return newsFragment;
            case 1:
                if (savedNewsFragment == null) {
                    savedNewsFragment = new SavedNewsFragment();
                    mObservers.addObserver(savedNewsFragment);
                }
                return savedNewsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGES;
    }

    public void updateFragments(final int index) {
        mObservers.notifyObservers();
        savedNewsFragment.updateFeed(index);
    }
}
