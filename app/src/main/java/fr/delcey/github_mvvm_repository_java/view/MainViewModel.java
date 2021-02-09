package fr.delcey.github_mvvm_repository_java.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import fr.delcey.github_mvvm_repository_java.data.github.GithubRepository;
import fr.delcey.github_mvvm_repository_java.data.github.model.GithubProject;
import fr.delcey.github_mvvm_repository_java.view.model.MainItemViewState;
import fr.delcey.github_mvvm_repository_java.view.model.MainViewState;

public class MainViewModel extends ViewModel {

    private final MediatorLiveData<MainViewState> mUiModelsLiveData = new MediatorLiveData<>();

    @NonNull
    private final GithubRepository githubRepository;

    private LiveData<List<GithubProject>> currentGithubProjectsLiveData;

    private String currentUserSearch;

    public MainViewModel(@NonNull GithubRepository githubRepository) {
        this.githubRepository = githubRepository;
    }

    LiveData<MainViewState> getViewStatesLiveData() {
        return mUiModelsLiveData;
    }

    /**
     * Call this method whenever the user search query changed (when he's typing a text or validate
     * its query with a "ok" button for example.
     *
     * @param user the name of the user on github
     */
    void userSearchChanged(@NonNull String user) {
        this.currentUserSearch = user;

        // User query changed : remove the old source
        mUiModelsLiveData.removeSource(currentGithubProjectsLiveData);

        // The repository will, after a delay, post the value on the LiveData.
        currentGithubProjectsLiveData = githubRepository.getGithubProjectsForUser(user);

        // For now, there's nothing in the LiveData, so we need a Mediator to "react" when
        // the new value is available.
        mUiModelsLiveData.addSource(currentGithubProjectsLiveData, new Observer<List<GithubProject>>() {
            @Override
            public void onChanged(List<GithubProject> githubProjects) {
                setNewMediatorValue(githubProjects);
            }
        });
    }

    // Retry to fetch data : the view should never pass parameters to a refresh function :
    // the ViewModel must have all the necessary information (here : currentUserSearch) to know
    // how to refresh the state of the view
    void refresh() {
        // Re-trigger a search
        userSearchChanged(currentUserSearch);
    }

    // This is what "mapping" is : this means transforming a List of GithubProject (a data model, usually
    // a POJO from a JSON) to a model for the View (MainViewState with a List of MainItemViewState)
    private void setNewMediatorValue(@Nullable List<GithubProject> githubProjects) {
        List<MainItemViewState> mainItemViewStateList = new ArrayList<>();

        if (githubProjects != null) {
            for (GithubProject githubProject : githubProjects) {
                mainItemViewStateList.add(
                    // We are MAPPING here a model from the Repository (a "GithubProject" object)
                    // to a model for the view : "ItemModelUi". The View (here an Activity) should
                    // never manipulate an object from the "data" package. Only the ViewModel knows
                    // both the "UI" model and the "Data" model.
                    new MainItemViewState(
                        githubProject.getName(),
                        githubProject.getOwner().getLogin(),
                        githubProject.getHtmlUrl()
                    )
                );
            }
        }

        mUiModelsLiveData.setValue(
            new MainViewState(
                false,
                mainItemViewStateList
            )
        );
    }
}
