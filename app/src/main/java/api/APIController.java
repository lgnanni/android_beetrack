package api;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import pojo.News;
import pojo.NewsFeed;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIController {

    static final String BASE_URL = "https://newsapi.org/v2/";

    private static APIController instance;

    private BeetrackAPI beetrackAPI;


    public static APIController getInstance() {

        if (instance == null) {
            instance = new APIController();
            instance.start();
        }

        return instance;

    }


    public void start() {

        final OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        final Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        beetrackAPI = retrofit.create(BeetrackAPI.class);
    }

    public Call<NewsFeed> getNews() {
        return beetrackAPI.getNews("us", "dbf443186b534ba18d57fac3dce0bc5d");
    }
}