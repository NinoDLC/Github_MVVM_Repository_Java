package fr.delcey.github_mvvm_repository_java.view.model;

import java.util.List;
import java.util.Objects;

public class MainViewState {
    private final boolean isLoading;
    private final List<MainItemViewState> mainItemViewStateList;

    public MainViewState(boolean isLoading, List<MainItemViewState> mainItemViewStateList) {
        this.isLoading = isLoading;
        this.mainItemViewStateList = mainItemViewStateList;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public List<MainItemViewState> getMainItemViewStateList() {
        return mainItemViewStateList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MainViewState that = (MainViewState) o;
        return isLoading == that.isLoading &&
            Objects.equals(mainItemViewStateList, that.mainItemViewStateList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isLoading, mainItemViewStateList);
    }
}
