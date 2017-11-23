package com.example.jsonresttest;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class PaperDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "Paper";
    private static final int DB_VERSION = 1;

    private static final String TABLE_SOURCES = "sources";
    private static final String COL_SOURCE_ID = "sourceId";
    private static final String COL_SOURCE_NAME = "name";
    private static final String COL_SOURCE_DESCRIPTION = "description";
    private static final String COL_SOURCE_LANGUAGE = "language";
    private static final String COL_SOURCE_URL = "url";
    private static final String COL_SOURCE_CATEGORY = "category";
    private static final String COL_SOURCE_COUNTRY = "country";

    private static final String TABLE_ARTICLES = "articles";
    private static final String COL_ARTICLE_SOURCE_ID = "id";
    private static final String COL_ARTICLE_AUTHOR = "author";
    private static final String COL_ARTICLE_TITLE = "title";
    private static final String COL_ARTICLE_DESCRIPTION = "articleDescription";
    private static final String COL_ARTICLE_URL = "articleURL";
    private static final String COL_ARTICLE_IMAGE_URL = "uRLToImage";
    private static final String COL_ARTICLE_PUBLICATION_TIME = "publishedAt";
    private static final String COL_ARTICLE_ID = "articleId";

    PaperDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    List<Source> getSourceList(){
        String sourceId;
        String name;
        String description;
        String language;
        String url;
        String category;
        String country;

        List<Source> sources = new ArrayList<>();

        Cursor sourceCursor = getWritableDatabase().query(TABLE_SOURCES,
                null,
                null,
                null,
                null,
                null,
                null);
        if (sourceCursor == null) {
            return sources;
        }

        if (!sourceCursor.moveToFirst()) {
            sourceCursor.close();
            return sources;
        }

        do {
            sourceId = sourceCursor.getString(0);
            name = sourceCursor.getString(1);
            description = sourceCursor.getString(2);
            language = sourceCursor.getString(3);
            url = sourceCursor.getString(4);
            category = sourceCursor.getString(5);
            country = sourceCursor.getString(6);
            sources.add(new Source(sourceId, name, description, language, url, category, country));
        } while (sourceCursor.moveToNext());
        sourceCursor.close();

        return sources;
    }

    List<Article> getArticleList(String dbID){
        List<Article> articleList = new ArrayList<>();
        long id;
        String author;
        String title;
        String description;
        String url;
        String imageURL;
        String publicationTime;
        Date date;

        Cursor articleCursor = getWritableDatabase().query(
                TABLE_ARTICLES,
                null,
                COL_ARTICLE_SOURCE_ID + "=?",
                new String[]{dbID},
                null,
                null,
                null
        );

        if (articleCursor == null) {
            return articleList;
        }

        if (!articleCursor.moveToFirst()) {
            articleCursor.close();
            return articleList;
        }

        do {
            id = articleCursor.getLong(0);
            author = articleCursor.getString(1);
            title = articleCursor.getString(2);
            description = articleCursor.getString(3);
            url = articleCursor.getString(4);
            imageURL = articleCursor.getString(5);
            publicationTime = articleCursor.getString(6);
            date = new Date(publicationTime);
            articleList.add(new Article(author, title, description, url, imageURL, date, id));
        } while (articleCursor.moveToNext());
        articleCursor.close();

        return articleList;
    }
    void updateArticleTable(List<Article> articleList, String dbID) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues articleCV = new ContentValues();

        HashSet<Long> articleHashSet = getColumnSetFromDB(new HashSet<Long>(20),
                TABLE_ARTICLES,
                new String[] {COL_ARTICLE_SOURCE_ID, COL_ARTICLE_ID},
                COL_ARTICLE_SOURCE_ID + " =?",
                new String[] {dbID},
                COL_ARTICLE_ID);

        database.beginTransaction();
        for (int i = 0; i < articleList.size(); i++) {
            if (articleHashSet.contains(articleList.get(i).getId())){
                continue;
            }

            articleCV.clear();
            articleCV.put(COL_ARTICLE_ID, articleList.get(i).getId());
            articleCV.put(COL_ARTICLE_SOURCE_ID, dbID);
            articleCV.put(COL_ARTICLE_AUTHOR, articleList.get(i).getAuthor());
            articleCV.put(COL_ARTICLE_TITLE, articleList.get(i).getTitle());
            articleCV.put(COL_ARTICLE_DESCRIPTION, articleList.get(i).getDescription());
            articleCV.put(COL_ARTICLE_URL, articleList.get(i).getUrl());
            articleCV.put(COL_ARTICLE_IMAGE_URL, articleList.get(i).getUrlToImage());
            articleCV.put(COL_ARTICLE_PUBLICATION_TIME, articleList.get(i).getPublishedAt().toString());
            getWritableDatabase().insert(TABLE_ARTICLES, null, articleCV);
        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_SOURCES + " ("
                + COL_SOURCE_ID + " text primary key,"
                + COL_SOURCE_NAME + " text,"
                + COL_SOURCE_DESCRIPTION + " text,"
                + COL_SOURCE_LANGUAGE + " text,"
                + COL_SOURCE_URL + " text,"
                + COL_SOURCE_CATEGORY + " text,"
                + COL_SOURCE_COUNTRY + " text"
                + ");");

        db.execSQL("create table " + TABLE_ARTICLES + " ("
                + COL_ARTICLE_ID + " long primary key not null,"
                + COL_ARTICLE_AUTHOR + " text,"
                + COL_ARTICLE_TITLE + " text,"
                + COL_ARTICLE_DESCRIPTION + " text,"
                + COL_ARTICLE_URL + " text,"
                + COL_ARTICLE_IMAGE_URL + " text,"
                + COL_ARTICLE_PUBLICATION_TIME + " text,"
                + COL_ARTICLE_SOURCE_ID + " text not null,"
                + " FOREIGN KEY ("+COL_ARTICLE_SOURCE_ID+") REFERENCES "
                + TABLE_SOURCES + "("+COL_SOURCE_ID+")"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    void updateSourceTable(List<Source> sourcesList) {
        ContentValues sourceCV = new ContentValues();

        HashSet<String> namesHashSet = getColumnSetFromDB(new HashSet<String>(200),
                TABLE_SOURCES,
                new String[] {COL_SOURCE_ID},
                null,
                null,
                COL_SOURCE_ID);

        SQLiteDatabase database = getWritableDatabase();
        database.beginTransaction();
        for (Source source: sourcesList) {
            if (namesHashSet.contains(source.getId())){
                continue;
            }

            sourceCV.clear();
            sourceCV.put(COL_SOURCE_ID, source.getId());
            sourceCV.put(COL_SOURCE_NAME, source.getName());
            sourceCV.put(COL_SOURCE_DESCRIPTION, source.getDescription());
            sourceCV.put(COL_SOURCE_LANGUAGE, source.getLanguage());
            sourceCV.put(COL_SOURCE_URL, source.getUrl());
            sourceCV.put(COL_SOURCE_CATEGORY, source.getCategory());
            sourceCV.put(COL_SOURCE_COUNTRY, source.getCountry());

            database.insert(TABLE_SOURCES, null, sourceCV);
        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    private <T> HashSet<T> getColumnSetFromDB(HashSet<T> set,
                                              String targetTable,
                                              String [] columns,
                                              String selection,
                                              String[] selectionArgs,
                                              String targetColumn){

        Cursor sourceCursor = getWritableDatabase().query(targetTable,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if (sourceCursor == null){
            return set;
        }

        if (!sourceCursor.moveToFirst()){
            sourceCursor.close();
            return set;
        }

        int index = sourceCursor.getColumnIndex(targetColumn);

        do {
            if (targetTable.equals(TABLE_SOURCES)) {
                set.add((T) sourceCursor.getString(index));
            } else {
                set.add((T) new Long(sourceCursor.getLong(index)));
            }
        } while (sourceCursor.moveToNext());

        sourceCursor.close();
        return set;
    }
}