package fr.delcey.github_mvvm_repository_java.data.github.model;

import androidx.annotation.Keep;

import com.google.gson.annotations.SerializedName;

@Keep
public class Owner {

    @SerializedName("avatar_url")
    private final String avatarUrl;
    @SerializedName("html_url")
    private final String htmlUrl;
    @SerializedName("id")
    private final String id;
    @SerializedName("login")
    private final String login;
    @SerializedName("repos_url")
    private final String reposUrl;
    @SerializedName("url")
    private final String url;

    public Owner(String avatarUrl, String htmlUrl, String id, String login, String reposUrl, String url) {
        this.avatarUrl = avatarUrl;
        this.htmlUrl = htmlUrl;
        this.id = id;
        this.login = login;
        this.reposUrl = reposUrl;
        this.url = url;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getReposUrl() {
        return reposUrl;
    }

    public String getUrl() {
        return url;
    }
}