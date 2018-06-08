package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beetrack.leandronanni.android_beetrack.R;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import adapters.NewsAdapter;
import adapters.NewsAdapterListener;
import pojo.News;
import utils.Utils;

public class SavedNewsFragment extends Fragment implements Observer {

    private RecyclerView recyclerView;
    private TextView emptyText;
    private static NewsAdapter adapter;
    private LinearLayoutManager layoutManager;

    private List<News> savedNews;



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.fragment_saved, null);

        recyclerView = view.findViewById(R.id.saved_recycler);
        emptyText = view.findViewById(R.id.saved_empty);
        updateFeed(-1);

        setupLayout(view);

        return view;
    }

    public void updateFeed(final int index) {
        savedNews = Utils.getInstance(getContext()).getNews();
        if (adapter != null) {
            adapter.setNews(savedNews, index);
        }
    }

    private void setupLayout(final View view) {

        if (savedNews == null || savedNews.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);
        } else {

            final NewsAdapterListener listener = (NewsAdapterListener) getActivity();
            adapter = new NewsAdapter(getContext(), listener, savedNews);

            if (layoutManager == null) {
                layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            }

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);

            emptyText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void update(final Observable o, final Object arg) {
        updateFeed(-1);
    }
}
