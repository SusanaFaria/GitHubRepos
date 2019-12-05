package android.example.com.githubrepos;

public class Repos {

    private String mLanguage;
    private String mTitle;
    private String mStars;


    public Repos(String language, String title, String stars) {

      mLanguage = language;
      mTitle = title;
      mStars = stars;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getStars() {
        return mStars;
    }

}





