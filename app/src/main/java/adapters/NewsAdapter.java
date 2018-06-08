package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.beetrack.leandronanni.android_beetrack.R;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.List;

import pojo.News;
import utils.Utils;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<News> news;
    private Context context;
    private NewsAdapterListener listener;

    private int imageSize;

    public NewsAdapter(final Context context, final NewsAdapterListener listener, final List<News> news) {
        this.context = context;
        this.listener = listener;
        this.news = news;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {

        final View view = LayoutInflater.from(context).inflate(R.layout.news_feed_item, null);

        final WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final Display display = wm.getDefaultDisplay();
        final DisplayMetrics displaymetrics = new DisplayMetrics();
        display.getMetrics(displaymetrics);
        imageSize = displaymetrics.widthPixels/5 ;


        final RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new NewsFeedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final NewsFeedViewHolder vh = (NewsFeedViewHolder) holder;

        final News feed =  news.get(position);

        Picasso.get().load(feed.getUrlToImage())
                .resize(imageSize,imageSize)
                .centerCrop()
                .into(vh.image);


        vh.title.setText(feed.getTitle());

        vh.description.setText(feed.getDescription());


        vh.save.setImageResource(Utils.getInstance(context).isSaved(feed.getUrl())
                ? R.drawable.ic_baseline_star_24px
                : R.drawable.ic_baseline_star_border_24px);

        vh.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final boolean isSaved = Utils.getInstance(context).isSaved(feed.getUrl());

                vh.save.setImageResource(isSaved
                        ? R.drawable.ic_baseline_star_border_24px
                        : R.drawable.ic_baseline_star_24px);

                listener.saveNews(feed, isSaved);
            }
        });

        vh.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.openNews(feed);
            }
        });

        final DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        vh.date.setText(dateFormat.format(feed.getPublishedAt()));

    }

    public void setNews(final List<News> news, final int position) {
        this.news = news;

        if (position >= 0) {
            notifyItemRemoved(position);
        } else {
            notifyItemRangeChanged(0, news.size());
        }
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public static class NewsFeedViewHolder extends RecyclerView.ViewHolder {

        public final ImageView image;
        public final TextView title;
        public final TextView description;
        public final ImageView save;
        public final TextView date;
        public final View view;

        public NewsFeedViewHolder (final View itemView) {
            super(itemView);

            this.view = itemView.findViewById(R.id.news_item);
            this.image = itemView.findViewById(R.id.news_image);
            this.title = itemView.findViewById(R.id.news_title);
            this.description = itemView.findViewById(R.id.news_description);
            this.save = itemView.findViewById(R.id.news_save);
            this.date = itemView.findViewById(R.id.news_date);
        }
    }
}
