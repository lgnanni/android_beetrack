package models;

import api.APIController;
import pojo.NewsFeed;
import presenters.NewsPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsModel {

    private NewsPresenter presenter;

    public NewsModel(final NewsPresenter presenter) {
        this.presenter = presenter;
    }


    public void loadNews() {
        APIController.getInstance().getNews().enqueue(new Callback<NewsFeed>() {
            @Override
            public void onResponse(final Call<NewsFeed> call, final Response<NewsFeed> response) {
                if (response.isSuccessful()) {
                    presenter.newsOk(response.body());
                } else {
                    presenter.newsFail();
                }
            }

            @Override
            public void onFailure(final Call<NewsFeed> call, final Throwable t) {
                presenter.newsFail();
            }
        });
    }
}
