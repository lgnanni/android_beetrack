package adapters;

import pojo.News;

public interface NewsAdapterListener {

    void saveNews(final News news, final boolean isSaved);
    void openNews(final News news);

}
