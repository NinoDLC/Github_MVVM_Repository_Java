package fr.delcey.github_mvvm_repository_java.data.github;

import java.util.List;

import fr.delcey.github_mvvm_repository_java.data.github.model.GithubProject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * This interface define how to interact with the distant server.
 * This is the "contract" of the API.
 */
public interface GithubApi {
    @GET("users/{user}/repos")
    Call<List<GithubProject>> getRepos(@Path("user") String user);
}