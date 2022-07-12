package com.example.myapplication.activities.leave;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RoomData {
    @SerializedName("id")
    private int roomId;
    @SerializedName("img")
    private String imageUrl;
    @SerializedName("roomName")
    private String roomName;
    @SerializedName("exitTime")
    private String roomDetails;
//    private String hostName;
//    private String hostDetails;
    @SerializedName("user_id")
    private int hostCode;

    @SerializedName("other_id")
    private Integer otherId;

    @SerializedName("NAME")
    private String hostName;

    @SerializedName("IMG")
    private String hostImg;

    @SerializedName("GENDER")
    private String hostGender;

//    private String otherName;
//    private String otherImg;
//    private String otherGender;

    public RoomData(String imageUrl,String roomName, String roomDetails, int hostCode) {
        this.roomName = roomName;
        this.roomDetails = roomDetails;
        this.hostCode = hostCode;
        this.imageUrl = imageUrl;
    }

    public RoomData(int otherId) {
        this.otherId = otherId;
    }

    public Integer getOtherId() {
        return otherId;
    }

    public void setOtherId(int otherId) {
        this.otherId = otherId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomDetails() {
        return roomDetails;
    }

    public void setRoomDetails(String roomDetails) {
        this.roomDetails = roomDetails;
    }

    public int getHostCode() {
        return hostCode;
    }

    public void setHostCode(int hostCode) {
        this.hostCode = hostCode;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostImg() {
        return hostImg;
    }

    public void setHostImg(String hostImg) {
        this.hostImg = hostImg;
    }

    public String getHostGender() {
        return hostGender;
    }

    public void setHostGender(String hostGender) {
        this.hostGender = hostGender;
    }
}
