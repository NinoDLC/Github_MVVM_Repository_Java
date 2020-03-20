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
import fr.delcey.github_mvvm_repository_java.data.github.model.GithubRepo;
import fr.delcey.github_mvvm_repository_java.view.model.ItemModelUi;
import fr.delcey.github_mvvm_repository_java.view.model.ModelUi;

public class MainViewModel extends ViewModel {

    private final MediatorLiveData<ModelUi> mUiModelsLiveData = new MediatorLiveData<>();

    @NonNull
    private final GithubRepository githubRepository;

    private LiveData<List<GithubRepo>> currentGithubRepoLiveData;

    private String currentUserSearch;

    public MainViewModel(@NonNull GithubRepository githubRepository) {
        this.githubRepository = githubRepository;
    }

    LiveData<ModelUi> getUiModelsLiveData() {
        return mUiModelsLiveData;
    }

    /**
     * Call this method whenever the user search query changed (when he's typing a text or validate
     * its query with a "ok" button for example.
     *
     * @param user the name of the user on github
     */
    void userSearchChanged(String user) {
        this.currentUserSearch = user;

        // User query changed : remove the old source
        mUiModelsLiveData.removeSource(currentGithubRepoLiveData);

        // The repository will, after a delay, post the value on the LiveData.
        currentGithubRepoLiveData = githubRepository.getGithubRepositoryForUser(user);

        // For now, there's nothing in the LiveData, so we need a Mediator to "react" when
        // the new value is available.
        mUiModelsLiveData.addSource(currentGithubRepoLiveData, new Observer<List<GithubRepo>>() {
            @Override
            public void onChanged(List<GithubRepo> githubRepos) {
                setNewMediatorValue(githubRepos);
            }
        });
    }

    // Retry to fetch data : the view should never pass parameters to a refresh function :
    // the viewmodel must have all the necessary informations (here : currentUserSearch) to know
    // how to refresh the state of the view
    void refresh() {
        // Re-trigger a search
        userSearchChanged(currentUserSearch);
    }

    // This is what "mapping" is : this means transforming a List of GithubRepo (a data model)
    // to a model for the View (ModelUi with a List of ItemModelUi)
    private void setNewMediatorValue(@Nullable List<GithubRepo> githubRepos) {
        List<ItemModelUi> itemModelUiList = new ArrayList<>();

        if (githubRepos != null) {
            for (GithubRepo githubRepo : githubRepos) {
                itemModelUiList.add(
                    // We are MAPPING here a model from the Repository (a "GithubRepo" object)
                    // to a model for the view : "ItemModelUi". The View (here an Activity) should
                    // never manipulate an object from the "data" package. Only the ViewModel knows
                    // both the "UI" model and the "Data" model.
                    new ItemModelUi(
                        githubRepo.getName(),
                        githubRepo.getOwner().getLogin(),
                        githubRepo.getHtmlUrl()
                    )
                );
            }
        }

        mUiModelsLiveData.setValue(
            new ModelUi(
                false,
                itemModelUiList
            )
        );
    }
}
