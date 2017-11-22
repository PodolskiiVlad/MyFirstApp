package com.example.jsonresttest;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ArticlesAdapter extends ArrayAdapter<Article>{

    private static final String EMPTY_ICON_URL = "https://cdn4.iconfinder.com/data/icons/adiante-apps-app-templates-incos-in-grey/512/app_type_newspaper_512px_GREY.png";

    public ArticlesAdapter(Context context, List<Article> articleList) {
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

        String imageURL = article.getUrlToImage();
        if (imageURL == null) {
            imageURL = EMPTY_ICON_URL;
        }
        Picasso.with(parent.getContext()).load(imageURL).into(viewHolder.articleImage);
        String author = article.getAuthor();
        viewHolder.author.setText(author);

        String title = article.getTitle();
        viewHolder.title.setText(title);

        String description = article.getDescription();
        viewHolder.description.setText(description);

        Date publishingDate = article.getPublishedAt();
        if (publishingDate != null) {
            SimpleDateFormat articleDateFormat = new SimpleDateFormat("dd MMM   hh:mm");
            String publicationTime = articleDateFormat.format(article.getPublishedAt());
            viewHolder.publicationTime.setText(publicationTime);
        }

        return convertView;
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            try {
                String url = getItem(position).getUrl();
                if (url != null && !(url.equalsIgnoreCase(""))){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    getContext().startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Url to article is missing", Toast.LENGTH_LONG).show();
                }
            } catch (ActivityNotFoundException ex){
                Toast.makeText(getContext(), "No application can handle this request."
                        + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }
        }
    };

    private class ViewHolder{

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
