package fr.delcey.github_mvvm_repository_java.view.model;

public class ItemModelUi {

    private final String repositoryName;
    private final String author;
    private final String repositoryUrl;

    public ItemModelUi(String repositoryName, String author, String repositoryUrl) {
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
}
