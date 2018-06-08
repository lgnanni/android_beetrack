package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.beetrack.leandronanni.android_beetrack.R;

import java.util.Observable;
import java.util.Observer;

import adapters.NewsAdapter;
import adapters.NewsAdapterListener;
import pojo.NewsFeed;
import presenters.NewsPresenter;
import views.NewsView;

public class NewsFragment extends Fragment implements NewsView, Observer {

    private NewsPresenter presenter;
    private FrameLayout progress;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static NewsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_news, container, false);

        progress = view.findViewById(R.id.progress);
        recyclerView = view.findViewById(R.id.news_recycler);
        swipeRefreshLayout = view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadNews();
            }
        });

        presenter = new NewsPresenter(this);

        presenter.loadNews();


        return view;
    }

    @Override
    public void showProgress() {
        if (!swipeRefreshLayout.isRefreshing()) {
            progress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
        progress.setVisibility(View.GONE);
    }


    @Override
    public void getNews(final NewsFeed news) {

        final NewsAdapterListener listener = (NewsAdapterListener) getActivity();

        if (adapter == null) {
            adapter = new NewsAdapter(getContext(), listener, news.getArticles());

            final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
        } else {
            adapter.setNews(news.getArticles(), -1);
        }

    }

    @Override
    public void showErrorToast() {
        Toast.makeText(getContext(), getString(R.string.error_get_news), Toast.LENGTH_LONG).show();
    }

    @Override
    public void update(final Observable o, final Object arg) {
        adapter.notifyDataSetChanged();
    }
}
