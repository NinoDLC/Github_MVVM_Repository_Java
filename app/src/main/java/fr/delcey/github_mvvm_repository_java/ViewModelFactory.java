package fr.delcey.github_mvvm_repository_java;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import fr.delcey.github_mvvm_repository_java.data.github.GithubRepositoryImpl;
import fr.delcey.github_mvvm_repository_java.view.MainViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static ViewModelFactory sFactory;

    private ViewModelFactory() {

    }

    public static ViewModelFactory getInstance() {
        if (sFactory == null) {
            synchronized (ViewModelFactory.class) {
                if (sFactory == null) {
                    sFactory = new ViewModelFactory();
                }
            }
        }

        return sFactory;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return (T) new MainViewModel(new GithubRepositoryImpl());
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}