package com.example.jsonresttest;

import java.util.List;

class SourcesList {

    private List<Source> sources;

    public SourcesList(List<Source> sources) {
        this.sources = sources;
    }

    List<Source> getSources() {
        return sources;
    }
}
