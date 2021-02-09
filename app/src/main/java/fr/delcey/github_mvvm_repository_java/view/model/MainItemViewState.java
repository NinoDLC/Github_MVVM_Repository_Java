package fr.delcey.github_mvvm_repository_java.view.model;

import java.util.Objects;

public class MainItemViewState {

    private final String repositoryName;
    private final String author;
    private final String repositoryUrl;

    public MainItemViewState(String repositoryName, String author, String repositoryUrl) {
        this.repositoryName = repositoryName;
        this.author = author;
        this.repositoryUrl = repositoryUrl;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public String getAuthor() {
        return author;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MainItemViewState that = (MainItemViewState) o;
        return Objects.equals(repositoryName, that.repositoryName) &&
            Objects.equals(author, that.author) &&
            Objects.equals(repositoryUrl, that.repositoryUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(repositoryName, author, repositoryUrl);
    }
}
