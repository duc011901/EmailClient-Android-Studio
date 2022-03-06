package vn.edu.usth.email2;

public class Messages {
    private String sender, receiver, title, detail;
    private boolean starCheck = false;

    public Messages(){
    }

    public Messages ( String sender, String receiver, String title, String detail, Boolean starCheck){
        this.sender = sender;
        this.receiver = receiver;
        this.title = title;
        this.detail = detail;
        this.starCheck = starCheck;
    }

    public boolean isStarCheck() {
        return starCheck;
    }

    public void setStarCheck(boolean starCheck) {
        this.starCheck = starCheck;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
