
package com.example.kolin.flick.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Photo_ implements Parcelable {

    private String id;
    private String owner;
    private String secret;
    private String server;
    private Integer farm;
    private String title;
    private Integer ispublic;
    private Integer isfriend;
    private Integer isfamily;
    @SerializedName("url_s")
    private String urlS;
    private String heightS;
    private String widthS;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    protected Photo_(Parcel in) {
        id = in.readString();
        owner = in.readString();
        secret = in.readString();
        server = in.readString();
        title = in.readString();
        urlS = in.readString();
        heightS = in.readString();
        widthS = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(owner);
        dest.writeString(secret);
        dest.writeString(server);
        dest.writeString(title);
        dest.writeString(urlS);
        dest.writeString(heightS);
        dest.writeString(widthS);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Photo_> CREATOR = new Creator<Photo_>() {
        @Override
        public Photo_ createFromParcel(Parcel in) {
            return new Photo_(in);
        }

        @Override
        public Photo_[] newArray(int size) {
            return new Photo_[size];
        }
    };

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     *
     * @param owner
     * The owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     *
     * @return
     * The secret
     */
    public String getSecret() {
        return secret;
    }

    /**
     *
     * @param secret
     * The secret
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     *
     * @return
     * The server
     */
    public String getServer() {
        return server;
    }

    /**
     *
     * @param server
     * The server
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     *
     * @return
     * The farm
     */
    public Integer getFarm() {
        return farm;
    }

    /**
     *
     * @param farm
     * The farm
     */
    public void setFarm(Integer farm) {
        this.farm = farm;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The ispublic
     */
    public Integer getIspublic() {
        return ispublic;
    }

    /**
     *
     * @param ispublic
     * The ispublic
     */
    public void setIspublic(Integer ispublic) {
        this.ispublic = ispublic;
    }

    /**
     *
     * @return
     * The isfriend
     */
    public Integer getIsfriend() {
        return isfriend;
    }

    /**
     *
     * @param isfriend
     * The isfriend
     */
    public void setIsfriend(Integer isfriend) {
        this.isfriend = isfriend;
    }

    /**
     *
     * @return
     * The isfamily
     */
    public Integer getIsfamily() {
        return isfamily;
    }

    /**
     *
     * @param isfamily
     * The isfamily
     */
    public void setIsfamily(Integer isfamily) {
        this.isfamily = isfamily;
    }

    /**
     *
     * @return
     * The urlS
     */
    public String getUrlS() {
        return urlS;
    }

    /**
     *
     * @param urlS
     * The url_s
     */
    public void setUrlS(String urlS) {
        this.urlS = urlS;
    }

    /**
     *
     * @return
     * The heightS
     */
    public String getHeightS() {
        return heightS;
    }

    /**
     *
     * @param heightS
     * The height_s
     */
    public void setHeightS(String heightS) {
        this.heightS = heightS;
    }

    /**
     *
     * @return
     * The widthS
     */
    public String getWidthS() {
        return widthS;
    }

    /**
     *
     * @param widthS
     * The width_s
     */
    public void setWidthS(String widthS) {
        this.widthS = widthS;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}