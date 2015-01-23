package ru.mechanik_ulyanovsk.mechanik.content.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Represents catalog item as an object
 */
public class CatalogItem implements Serializable {

    @SerializedName(value = "ID")
    private long id;
    @SerializedName(value = "NAME")
    private String name;
    @SerializedName(value = "PREVIEW_PICTURE")
    private String previewUri;
    @SerializedName(value = "DETAIL_PICTURE")
    private String detailUri;
    @SerializedName(value = "PROPERTY_CML2_ARTICLE_VALUE")
    private String article;

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

    public String getArticle() {
        return article;
    }

    @Override
    public String toString() {
        return "CatalogItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", previewUri='" + previewUri + '\'' +
                ", detailUri='" + detailUri + '\'' +
                ", article='" + article + '\'' +
                '}';
    }
}
