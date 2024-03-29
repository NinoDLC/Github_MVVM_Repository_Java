package fr.delcey.github_mvvm_repository_java.data.github;

import androidx.annotation.VisibleForTesting;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubApiHolder {
    private static final String BASE_URL = "https://api.github.com";

    public static GithubApi getInstance() {
        return getInstance(HttpUrl.get(BASE_URL));
    }

    @VisibleForTesting
    public static GithubApi getInstance(HttpUrl baseUrl) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build();

        return new Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubApi.class);
    }
}
