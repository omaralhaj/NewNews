package com.example.android.newnews;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;


public class NewsAdapter extends ArrayAdapter {

    public NewsAdapter(Activity c, ArrayList<News> n) {
        super(c, 0, n);
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        News n = (News) getItem(position);
        if (n != null) {
            TextView title = convertView.findViewById(R.id.title);
            title.setText(n.getTitle());
            TextView section = convertView.findViewById(R.id.section);
            section.setText(n.getSection());
            TextView date = convertView.findViewById(R.id.date);
            date.setText(n.getDate());
            TextView author = convertView.findViewById(R.id.author);
            author.setText(n.getAuthor());
            final Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(n.getUrl()));
            final Bundle b = new Bundle();
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(getContext(), i, b);
                }
            });
        }
        return convertView;
    }


}
