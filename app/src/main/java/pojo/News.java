package pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class News implements Parcelable {
    private Source source;
    private String title;
    private String description;
    private String url;
    private String urlToImage;
    private Date publishedAt;

    protected News(Parcel in) {
        source = in.readParcelable(Source.class.getClassLoader());
        title = in.readString();
        description = in.readString();
        url = in.readString();
        urlToImage = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(source, 0);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(url);
        dest.writeString(urlToImage);
    }

    public Source getSource() {
        return source;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }
}
