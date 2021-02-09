package fr.delcey.github_mvvm_repository_java.view;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import fr.delcey.github_mvvm_repository_java.data.github.GithubRepository;
import fr.delcey.github_mvvm_repository_java.data.github.model.GithubProject;
import fr.delcey.github_mvvm_repository_java.data.github.model.Owner;
import fr.delcey.github_mvvm_repository_java.utils.LiveDataTestUtils;
import fr.delcey.github_mvvm_repository_java.view.model.MainItemViewState;
import fr.delcey.github_mvvm_repository_java.view.model.MainViewState;

public class MainViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final MutableLiveData<List<GithubProject>> githubProjectsLiveData = new MutableLiveData<>();

    private final GithubRepository githubRepository = Mockito.mock(GithubRepository.class);

    private MainViewModel mainViewModel;

    @Before
    public void setUp() {
        Mockito.doReturn(githubProjectsLiveData).when(githubRepository).getGithubProjectsForUser(Mockito.anyString());

        mainViewModel = new MainViewModel(githubRepository);
    }

    @Test
    public void shouldExposeCorrectViewState() throws InterruptedException {
        // Given
        githubProjectsLiveData.setValue(generateGithubProjectList());

        // When
        mainViewModel.userSearchChanged("NinoDLC");
        MainViewState result = LiveDataTestUtils.getOrAwaitValue(mainViewModel.getViewStatesLiveData());

        // Then
        Assert.assertEquals(
            getDefaultMainViewState(),
            result
        );
    }

    // region IN
    private List<GithubProject> generateGithubProjectList() {
        List<GithubProject> result = new ArrayList<>();

        result.add(
            new GithubProject(
                "url",
                "name",
                new Owner(
                    "avatarUrl",
                    "ownerHtmlUrl",
                    "ownerId",
                    "login",
                    "reposUrl",
                    "ownerUrl"
                )
            )
        );

        return result;
    }
    // endregion

    // region OUT
    private MainViewState getDefaultMainViewState() {
        return new MainViewState(
            false,
            getDefaultMainItemViewStateList()
        );
    }

    private List<MainItemViewState> getDefaultMainItemViewStateList() {
        List<MainItemViewState> result = new ArrayList<>();

        result.add(
            new MainItemViewState(
                "name",
                "login",
                "url"
            )
        );

        return result;
    }
    // endregion
}