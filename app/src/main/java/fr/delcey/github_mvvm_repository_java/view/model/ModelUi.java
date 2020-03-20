package fr.delcey.github_mvvm_repository_java.view.model;

import java.util.List;

public class ModelUi {
    private final boolean isLoading;
    private final List<ItemModelUi> itemModelUiList;

    public ModelUi(boolean isLoading, List<ItemModelUi> itemModelUiList) {
        this.isLoading = isLoading;
        this.itemModelUiList = itemModelUiList;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public List<ItemModelUi> getItemModelUiList() {
        return itemModelUiList;
    }
}
