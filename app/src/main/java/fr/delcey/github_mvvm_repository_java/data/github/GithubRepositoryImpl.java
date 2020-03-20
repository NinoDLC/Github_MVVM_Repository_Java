package fr.delcey.github_mvvm_repository_java.data.github;

import android.os.AsyncTask;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

import fr.delcey.github_mvvm_repository_java.data.github.model.GithubRepo;

public class GithubRepositoryImpl implements GithubRepository {

    @Nullable
    private GetUserReposAsyncTask currentAsyncTask;

    @MainThread
    @Override
    public LiveData<List<GithubRepo>> getGithubRepositoryForUser(String user) {
        if (currentAsyncTask != null) {
            currentAsyncTask.cancel(true);
            currentAsyncTask = null;
        }

        MutableLiveData<List<GithubRepo>> liveData = new MutableLiveData<>();

        // Start an async task to go on the internet and fetch data, asynchrounously.
        // This line won't block : we return directly the LiveData that will, after a certain delay,
        // contain the result. But for now, it's an empty LiveData.
        currentAsyncTask = new GetUserReposAsyncTask(new WeakReference<>(liveData), user);
        currentAsyncTask.execute();

        return liveData;
    }

    private static class GetUserReposAsyncTask extends AsyncTask<Void, Void, List<GithubRepo>> {

        // Use a WeakReference to the LiveData to avoid a leak
        private final WeakReference<MutableLiveData<List<GithubRepo>>> liveDataWeakReference;
        private final String user;

        public GetUserReposAsyncTask(
            WeakReference<MutableLiveData<List<GithubRepo>>> liveDataWeakReference,
            String user
        ) {
            this.liveDataWeakReference = liveDataWeakReference;
            this.user = user;
        }

        @Nullable
        @Override
        protected List<GithubRepo> doInBackground(Void... voids) {
            // This call can take some time to complete, that's why we use an AsyncTask.
            try {
                return GithubApiHolder.getInstance().getRepos(user).execute().body();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(@Nullable List<GithubRepo> githubRepos) {
            // If the view didn't die during the http request
            if (liveDataWeakReference.get() != null) {
                liveDataWeakReference.get().setValue(githubRepos);
            }
        }
    }
}
