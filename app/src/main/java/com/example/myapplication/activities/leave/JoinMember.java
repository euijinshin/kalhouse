package com.example.myapplication.activities.leave;

public class JoinMember {
    private String name;
    private String score;
    private String imgUrl;

    public JoinMember(String name, String score, String imgUrl) {
        this.name = name;
        this.score = score;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
