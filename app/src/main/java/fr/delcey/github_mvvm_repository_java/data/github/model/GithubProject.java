package fr.delcey.github_mvvm_repository_java.data.github.model;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

@Keep
public class GithubProject {
    @SerializedName("html_url")
    private final String htmlUrl;
    @SerializedName("name")
    private final String name;
    @SerializedName("owner")
    private final Owner owner;

    public GithubProject(String htmlUrl, String name, Owner owner) {
        this.htmlUrl = htmlUrl;
        this.name = name;
        this.owner = owner;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getName() {
        return name;
    }

    public Owner getOwner() {
        return owner;
    }
}