package android.example.com.githubrepos;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<List<Repos>>, SharedPreferences.OnSharedPreferenceChangeListener{

    private static final String REPOS_REQUEST = "https://api.github.com/search/repositories?q=language%20java&sort=stars";
    private static final String LOG_TAG = RepoLoader.class.getName();
    private static final int REPO_LOADER = 1;
    private RepoAdapter mRepoAdapter;
    private TextView mEmptyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView reposListView = findViewById(R.id.list);
        mEmptyText = findViewById(R.id.empty_list);
        reposListView.setEmptyView(mEmptyText);

        // Create a new adapter that takes an empty list of Repos as input
        mRepoAdapter = new RepoAdapter(this, new ArrayList<Repos>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        reposListView.setAdapter(mRepoAdapter);


        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected repo.
        reposListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current repo that was clicked on
                Repos currentRepo = mRepoAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                assert currentRepo != null;
                Uri repoUri = Uri.parse(currentRepo.getUrl());

                // Create a new intent to view the news URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, repoUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connMgr != null;
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(REPO_LOADER, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.progress);
            assert loadingIndicator != null;
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyText.setText(getString(R.string.no_connection));
        }
    }



    @Override
    public Loader<List<Repos>> onCreateLoader(int i, Bundle bundle) {

        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(REPOS_REQUEST);



        Log.i(LOG_TAG, baseUri.toString());
        // Return the completed uri `https://
        return new RepoLoader(this, baseUri.toString());

    }

    @Override
    public void onLoadFinished(Loader<List<Repos>> loader, List<Repos> repos) {
        mEmptyText.setText(getString(R.string.no_data));
        // Clear the adapter of previous data
        mRepoAdapter.clear();

        // If there is a valid list of repos, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (repos != null && !repos.isEmpty()) {
            mRepoAdapter.addAll(repos);
        }

        ProgressBar myCircle = findViewById(R.id.progress);
        myCircle.setVisibility(View.GONE);

    }

    @Override
    public void onLoaderReset(Loader<List<Repos>> loader) {
        mRepoAdapter.clear();

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

    }
}
