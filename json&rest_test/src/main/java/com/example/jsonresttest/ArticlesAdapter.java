package com.example.jsonresttest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ArticlesAdapter extends ArrayAdapter<Article> {

    private static final String EMPTY_ICON_URL = "https://cdn4.iconfinder.com/data/icons/adiante-apps-app-templates-incos-in-grey/512/app_type_newspaper_512px_GREY.png";

    ArticlesAdapter(Context context, List<Article> articleList) {
        super(context, 0, articleList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.article_list_item, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Article article = getItem(position);

        String imageURL;
        String author;
        String title;
        String description;
        Date publishingDate = null;

        if (article != null){
            imageURL = article.getUrlToImage();
            author = article.getAuthor();
            title = article.getTitle();
            description = article.getDescription();
            publishingDate = article.getPublishedAt();
        } else {
            imageURL = EMPTY_ICON_URL;
            author = "";
            title = "";
            description = "";
        }

        Picasso.with(parent.getContext()).load(imageURL).into(viewHolder.articleImage);
        viewHolder.author.setText(author);
        viewHolder.title.setText(title);
        viewHolder.description.setText(description);

        if (publishingDate != null) {
            SimpleDateFormat articleDateFormat = new SimpleDateFormat("dd MMM   hh:mm", Locale.getDefault());
            String publicationTime = articleDateFormat.format(article.getPublishedAt());
            viewHolder.publicationTime.setText(publicationTime);
        }

        return convertView;
    }

    private class ViewHolder {

        private ImageView articleImage;
        private TextView author;
        private TextView title;
        private TextView description;
        private TextView publicationTime;

        ViewHolder(View view) {
            articleImage = view.findViewById(R.id.articleImageView);
            author = view.findViewById(R.id.articleAuthor);
            title = view.findViewById(R.id.articleTitle);
            description = view.findViewById(R.id.articleDescription);
            publicationTime = view.findViewById(R.id.articlePublicationTime);
        }
    }
}
