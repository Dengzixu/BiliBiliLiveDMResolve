package net.dengzixu.java.message;

import net.dengzixu.java.constant.BodyCommand;

import java.util.HashMap;

public class Message {
    public HashMap<String, Object> content;

    public BodyCommand bodyCommand;

    public UserInfo userInfo;
    public FansMedal fansMedal;

    public HashMap<String, Object> getContent() {
        return content;
    }

    public void setContent(HashMap<String, Object> content) {
        this.content = content;
    }

    public BodyCommand getBodyCommand() {
        return bodyCommand;
    }

    public void setBodyCommand(BodyCommand bodyCommand) {
        this.bodyCommand = bodyCommand;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public FansMedal getFansMedal() {
        return fansMedal;
    }

    public void setFansMedal(FansMedal fansMedal) {
        this.fansMedal = fansMedal;
    }

    @Override
    public String toString() {
        return "Message{" +
                "content=" + content +
                ", bodyCommand=" + bodyCommand +
                ", userInfo=" + userInfo +
                ", fansMedal=" + fansMedal +
                '}';
    }
}
