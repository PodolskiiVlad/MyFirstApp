package com.example.jsonresttest;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaperDatabase extends SQLiteOpenHelper{

    private static final String DB_NAME = "Paper";
    private static final int DB_VERSION = 1;

    private static final String TABLE_SOURCES = "sources";
    private static final  String COL_PRIMARY_ID = "id";
    private static final String COL_SOURCE_ID = "sourceID";
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

    private List<Source> sourceList;
    private SQLiteDatabase sqLiteDatabase;

    PaperDatabase(Context context, List<Source> sourceList) {
        super(context, DB_NAME, null, DB_VERSION);
        this.sourceList = sourceList;
        sqLiteDatabase = getWritableDatabase();
    }

    PaperDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        sqLiteDatabase = getWritableDatabase();
    }

    List<Source> getSourceList() throws NoDatabaseException{
        return createSourceListFromDatabase();
    }

    private List<Source> createSourceListFromDatabase() throws NoDatabaseException{
        sourceList = new ArrayList<>();
        int dbID;
        String sourceId;
        String name;
        String description;
        String language;
        String url;
        String category;
        String country;

        Cursor sourceCursor = sqLiteDatabase.query("sources", null, null, null, null, null, null);
        if (sourceCursor != null && sourceCursor.moveToFirst()){
            do{
                dbID = sourceCursor.getInt(0);
                sourceId = sourceCursor.getString(1);
                name = sourceCursor.getString(2);
                description = sourceCursor.getString(3);
                language = sourceCursor.getString(4);
                url = sourceCursor.getString(5);
                category = sourceCursor.getString(6);
                country = sourceCursor.getString(7);
                sourceList.add(new Source(dbID, sourceId, name, description, language, url, category, country));
            }while(sourceCursor.moveToNext());
            sourceCursor.close();
        }
        return sourceList;
    }
    List<Article> getArticleList(String dbID) throws NoDatabaseException{
        List<Article> articleList = new ArrayList<>();
        String author;
        String title;
        String description;
        String url;
        String imageURL;
        String publicationTime;

        String sqlQuery = "SELECT * "
                + "FROM " + TABLE_ARTICLES
                + "INNER JOIN " + TABLE_SOURCES
                + " ON " + COL_ARTICLE_SOURCE_ID + " = " + dbID;
        Cursor articleCursor = sqLiteDatabase.rawQuery(sqlQuery, null);
        if (articleCursor != null) {
            do {
                author = articleCursor.getString(1);
                title = articleCursor.getString(2);
                description = articleCursor.getString(3);
                url = articleCursor.getString(4);
                imageURL = articleCursor.getString(5);
                publicationTime = articleCursor.getString(6);
                Date date = new Date(publicationTime);
                articleList.add(new Article(author, title, description, url, imageURL, date));
            } while(articleCursor.moveToNext());
            articleCursor.close();
        }
        return articleList;
    }

    void updateArticleTable(List<Article> articleList, String dbID){
        String whereClause = COL_ARTICLE_SOURCE_ID + " = " +  COL_PRIMARY_ID;
        sqLiteDatabase.delete("articles", whereClause, null);
        ContentValues articleCV = new ContentValues();

        for (int i = 0; i < articleList.size(); i++) {
            articleCV.clear();
            articleCV.put(COL_ARTICLE_SOURCE_ID, dbID);
            articleCV.put(COL_ARTICLE_AUTHOR, articleList.get(i).getAuthor());
            articleCV.put(COL_ARTICLE_TITLE, articleList.get(i).getTitle());
            articleCV.put(COL_ARTICLE_DESCRIPTION, articleList.get(i).getDescription());
            articleCV.put(COL_ARTICLE_URL, articleList.get(i).getUrl());
            articleCV.put(COL_ARTICLE_IMAGE_URL, articleList.get(i).getUrlToImage());
            articleCV.put(COL_ARTICLE_PUBLICATION_TIME, articleList.get(i).getPublishedAt().toString());
            sqLiteDatabase.insert(TABLE_ARTICLES, null, articleCV);
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        sqLiteDatabase = db;
        ContentValues sourcesCV = new ContentValues();

        db.execSQL("create table " + TABLE_SOURCES + " ("
                + COL_PRIMARY_ID + " integer primary key autoincrement,"
                + COL_SOURCE_ID + " text,"
                + COL_SOURCE_NAME + " text,"
                + COL_SOURCE_DESCRIPTION + " text,"
                + COL_SOURCE_LANGUAGE + " text,"
                + COL_SOURCE_URL + " text,"
                + COL_SOURCE_CATEGORY + " text,"
                + COL_SOURCE_COUNTRY + " text"
                + ");");

        if (sourceList != null) {
            for (int i = 0; i < sourceList.size(); i++) {
                sourcesCV.clear();
                sourcesCV.put(COL_SOURCE_ID, sourceList.get(i).getId());
                sourcesCV.put(COL_SOURCE_NAME, sourceList.get(i).getName());
                sourcesCV.put(COL_SOURCE_DESCRIPTION, sourceList.get(i).getDescription());
                sourcesCV.put(COL_SOURCE_URL, sourceList.get(i).getUrl());
                sourcesCV.put(COL_SOURCE_CATEGORY, sourceList.get(i).getCategory());
                sourcesCV.put(COL_SOURCE_COUNTRY, sourceList.get(i).getCountry());
                db.insert(TABLE_SOURCES, null, sourcesCV);
            }
        }

        db.execSQL(  "create table " + TABLE_ARTICLES + " ("
                + "id integer primary key,"
                + COL_ARTICLE_AUTHOR + " text,"
                + COL_ARTICLE_TITLE + " text,"
                + COL_ARTICLE_DESCRIPTION + " text,"
                + COL_ARTICLE_URL + " text,"
                + COL_ARTICLE_IMAGE_URL + " text,"
                + COL_ARTICLE_PUBLICATION_TIME + " text"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}