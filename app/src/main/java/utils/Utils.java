package utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import pojo.News;

public class Utils {

    public static final String SAVED_NEWS = "saved_news";

    private static Utils instance;

    private final Context context;

    private SharedPreferences prefs;


    public static Utils getInstance(final Context context) {
        if (instance == null) {
            instance = new Utils(context);
        }

        return instance;
    }


    public Utils(final Context context) {
        this.context = context;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }


    public boolean isSaved(final String url) {

        final String jsonNews = prefs.getString(SAVED_NEWS, null);

        if (TextUtils.isEmpty(jsonNews)) {
            return false;
        } else {
            final List<News> newsList = new Gson().fromJson(jsonNews, new TypeToken<List<News>>() {}.getType());

            for (final News news : newsList) {
                if (url.contentEquals(news.getUrl())) {
                    return true;
                }
            }
        }

        return false;

    }

    public List<News> getNews() {
        final String jsonNews = prefs.getString(SAVED_NEWS, null);

        final List<News> newsList;
        if (TextUtils.isEmpty(jsonNews)) {
            newsList = new ArrayList<>();
        } else {
            newsList = new Gson().fromJson(jsonNews, new TypeToken<List<News>>() {}.getType());
        }

        return newsList;
    }

    public int saveNews(final News news, final boolean delete) {
        int index = -1;

        final List<News> newsList = getNews();

        if (delete) {


            for (int i = 0; i < newsList.size(); i++) {
                if (newsList.get(i).getUrl().contentEquals(news.getUrl())) {
                    index = i;
                    break;
                }
            }

            newsList.remove(index);
        } else {
            newsList.add(news);
        }

        final String saveJsonString = new Gson().toJson(newsList);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = prefs.edit();

        editor.putString(SAVED_NEWS, saveJsonString);
        editor.apply();

        return index;
    }
}
