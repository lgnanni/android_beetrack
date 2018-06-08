package api;

import pojo.NewsFeed;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BeetrackAPI {

    @GET("top-headlines")
    Call<NewsFeed> getNews(@Query("country") final String country, @Query("apiKey") final String apiKey);
}
