package android.example.com.githubrepos;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class RepoLoader extends AsyncTaskLoader<List<Repos>> {

    private static final String LOG_TAG = RepoLoader.class.getName();
    /**
     * Query URL
     */
    private String mUrl;

    /**
     * Constructs a new {@link RepoLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public RepoLoader(Context context, String url) {
        super(context);

        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Repos> loadInBackground() {
        // Don't perform the request if there are no URLs, or the first URL is null.
        if (mUrl == null) {
            return null;
        }
// Perform the network request, parse the response, and extract a list of earthquakes.
        List<Repos> repos = QueryUtils.fetchRepoData(mUrl);

        return repos;


    }

}





