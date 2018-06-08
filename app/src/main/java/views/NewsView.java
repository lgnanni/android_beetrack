package views;

import pojo.News;
import pojo.NewsFeed;

public interface NewsView {

    void showProgress();
    void hideProgress();
    void getNews(final NewsFeed news);
    void showErrorToast();
}
