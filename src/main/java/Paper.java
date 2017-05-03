/**
 * Created by ZXD on 2017/4/23.
 */
public class Paper {
    private String url;
    private String title;
    private String authors;

    public Paper() {
    }

    public Paper(String url, String title, String authors) {
        this.url = url;
        this.title = title;
        this.authors = authors;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "Paper{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", authors='" + authors + '\'' +
                '}';
    }
}
