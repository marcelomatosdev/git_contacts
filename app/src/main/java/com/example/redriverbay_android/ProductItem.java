package com.example.redriverbay_android;

public class ProductItem {
    private String mImageUrl;
    private String mCreator;
    private int mLikes;

    public ProductItem(String imageUrl, String creator, int likes){
        mImageUrl = imageUrl;
        mCreator = creator;
        mLikes = likes;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
    public String getCreator() {
        return mCreator;
    }
    public int getLikes() {
        return mLikes;
    }
}
