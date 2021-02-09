package fr.delcey.github_mvvm_repository_java.data.github;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import fr.delcey.github_mvvm_repository_java.data.github.model.GithubProject;

/**
 * The purpose of this Repository is to get data asynchronously for the ViewModel
 */
public interface GithubRepository {

    LiveData<List<GithubProject>> getGithubProjectsForUser(@NonNull String user);
}
