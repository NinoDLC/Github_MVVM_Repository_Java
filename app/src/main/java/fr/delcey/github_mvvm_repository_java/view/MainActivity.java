package fr.delcey.github_mvvm_repository_java.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import fr.delcey.github_mvvm_repository_java.R;
import fr.delcey.github_mvvm_repository_java.ViewModelFactory;
import fr.delcey.github_mvvm_repository_java.view.model.MainItemViewState;
import fr.delcey.github_mvvm_repository_java.view.model.MainViewState;

import static android.content.Intent.ACTION_VIEW;

public class MainActivity extends AppCompatActivity implements MainRecyclerViewAdapter.OnItemClickedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        final MainViewModel mainViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance()).get(MainViewModel.class);

        final EditText editText = findViewById(R.id.main_tiet);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mainViewModel.userSearchChanged(editable.toString());
            }
        });

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.main_srl);
        RecyclerView recyclerView = findViewById(R.id.main_rv);

        final MainRecyclerViewAdapter adapter = new MainRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mainViewModel.refresh();
            }
        });

        mainViewModel.getViewStatesLiveData().observe(this, new Observer<MainViewState>() {
            @Override
            public void onChanged(MainViewState mainViewState) {
                swipeRefreshLayout.setRefreshing(mainViewState.isLoading());
                adapter.submitList(mainViewState.getMainItemViewStateList());
            }
        });
    }

    @Override
    public void onItemClicked(MainItemViewState mainItemViewState) {
        startActivity(new Intent(ACTION_VIEW).setData(Uri.parse(mainItemViewState.getRepositoryUrl())));
    }
}
