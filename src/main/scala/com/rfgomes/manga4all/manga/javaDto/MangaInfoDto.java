package com.rfgomes.manga4all.manga.javaDto;

public class MangaInfoDto {
    private String id;
    private String name;
    private String imageUrl;
    private String source;

    public MangaInfoDto(final String id, final String name,
                        final String imageUrl, final String source) {
        this.id = id;
        this.name =name;
        this.imageUrl = imageUrl;
        this.source = source;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}

