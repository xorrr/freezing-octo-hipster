package earth.xor.db;

import com.google.gson.annotations.SerializedName;

public class Link {

    private String url;
    private String title;
    private String user;
    private String timestamp;

    @SerializedName(value = "_id")
    private String objectId;

    public Link(String url, String title, String user, String string) {
        setEveryThingButTimeAndId(url, title, user);
        this.timestamp = string;
    }

    public Link(String url, String title, String user) {
        setEveryThingButTimeAndId(url, title, user);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getObjectId() {
        return this.objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getTimeStamp() {
        return this.timestamp;
    }

    private void setEveryThingButTimeAndId(String url, String title, String user) {
        this.url = url;
        this.title = title;
        this.user = user;
    }
}
