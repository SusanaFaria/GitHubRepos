package android.example.com.githubrepos;

public class Repos {

    private String mLanguage;
    private String mTitle;
    private String mUrl;


    public Repos(String language, String title, String url) {

      mLanguage = language;
      mTitle = title;
        mUrl = url;

    }

    public String getLanguage() {
        return mLanguage;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }


}





