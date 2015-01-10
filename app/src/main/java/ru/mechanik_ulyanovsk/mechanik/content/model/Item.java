package ru.mechanik_ulyanovsk.mechanik.content.model;

import com.google.gson.annotations.SerializedName;

/**
 * Represents item as an object
 */
public class Item {

    @SerializedName(value = "ID")
    private long id;
    @SerializedName(value = "NAME")
    private String name;
    @SerializedName(value = "PREVIEW_PICTURE")
    private String previewUri;
    @SerializedName(value = "DETAIL_PICTURE")
    private String detailUri;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPreviewUri() {
        return previewUri;
    }

    public String getDetailUri() {
        return detailUri;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", previewUri='" + previewUri + '\'' +
                ", detailUri='" + detailUri + '\'' +
                '}';
    }
}
