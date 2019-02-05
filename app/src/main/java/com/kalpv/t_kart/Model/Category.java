package com.kalpv.t_kart.Model;

public class Category {
    private String id;
    private String categoryTitle;
    private String bannerUrl;

    public Category(String id, String categoryTitle, String bannerUrl) {
        this.categoryTitle = categoryTitle;
        this.bannerUrl = bannerUrl;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }
}
