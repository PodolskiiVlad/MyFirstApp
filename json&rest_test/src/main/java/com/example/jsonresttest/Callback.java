package com.example.jsonresttest;

import java.util.List;

public interface Callback<T> {

    void onResult(List<T> articleList);
}
