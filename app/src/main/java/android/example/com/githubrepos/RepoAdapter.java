package android.example.com.githubrepos;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RepoAdapter extends ArrayAdapter<Repos> {

    private Context mContext;

    public RepoAdapter(Activity context, ArrayList<Repos> news) {
        super(context, 0, news);
        mContext = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.repo_item, parent, false);
        }

        final Repos currentRepo = getItem(position);


        TextView repoTitle = (TextView) listItemView.findViewById(R.id.repo_title);
        repoTitle.setText(currentRepo.getTitle());

        TextView language = (TextView) listItemView.findViewById(R.id.repo_language);
            language.setText(currentRepo.getLanguage());

        return listItemView;
    }


}



