package com.example.gitcontacts;

public class ContactItem {
    private String mImageUrl;
    private String mUsername;
    private String mName;
    private String mBio;
    private int mRepositories;
    private int mFollowers;
    private int mId;
    private int mGitId;


    public ContactItem(String imageUrl, String userName, String name, String bio, int repositories, int followers, int id, int gitId ){
        mImageUrl = imageUrl;
        mUsername = userName;
        mName = name;
        mBio = bio;
        mRepositories = repositories;
        mFollowers = followers;
        mId = id;
        mGitId = gitId;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
    public String getUsername() {
        return mUsername;
    }
    public String getName() {
        return mName;
    }
    public String getBio() {
        return mBio;
    }
    public int getRepositories() {
        return mRepositories;
    }
    public int getFollowers() {
        return mFollowers;
    }
    public int getId() { return mId;   }
    public int getGitId() { return mGitId;   }

}
