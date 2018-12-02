package digitalsigma.com.mxplayersample.PlayVideo;

public class VideoModel {
    private String title,url;

    public VideoModel(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
