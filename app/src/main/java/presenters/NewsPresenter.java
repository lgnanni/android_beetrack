package presenters;

import models.NewsModel;
import pojo.NewsFeed;
import views.NewsView;

public class NewsPresenter {

    private final NewsView view;
    private final NewsModel model;

    public NewsPresenter(final NewsView view) {
        this.view = view;
        model = new NewsModel(this);
    }

    public void loadNews() {
        view.showProgress();
        model.loadNews();
    }

    public void newsOk(final NewsFeed news) {
        view.getNews(news);
        view.hideProgress();
    }

    public void newsFail() {
        view.hideProgress();
        view.showErrorToast();
    }
}
