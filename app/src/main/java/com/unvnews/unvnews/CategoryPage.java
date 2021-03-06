package com.unvnews.unvnews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.unvnews.unvnews.databinding.ActivityCategoryPageBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryPage extends AppCompatActivity {
    ActivityCategoryPageBinding binding;
    Retrofit retrofit;
    MyAdapter adapter;
    List<Articles> articles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Bundle extra = getIntent().getExtras();
        String title = extra.getString("TITLE");
        articles = new ArrayList<>();
        binding.categoryProgressBar.setVisibility(View.VISIBLE);
        binding.materialToolbarCategory.setTitle(title);
        binding.materialToolbarCategory.setNavigationOnClickListener(v -> finish());
        binding.materialToolbarCategory.setOnMenuItemClickListener(item -> {
            Intent intent = new Intent();
            if (item.getItemId() == R.id.toolbarAbout) {
                intent.setClass(CategoryPage.this, AboutActivity.class);
            } else {
                intent.setClass(CategoryPage.this, SearchActivity.class);
            }
            startActivity(intent);
            return true;
        });
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<News> call;

        switch (title) {
            case "Top Headlines":
                call = apiInterface.getArticle(Constants.COUNTRY, Constants.API_KEY);
                break;

            case "Sports":
                call = apiInterface.getArticleByCategory(Constants.COUNTRY, "sports", Constants.API_KEY);
                break;

            case "Entertainment":
                call = apiInterface.getArticleByCategory(Constants.COUNTRY, "entertainment", Constants.API_KEY);
                break;

            case "Technology":
                call = apiInterface.getArticleByCategory(Constants.COUNTRY, "technology", Constants.API_KEY);
                break;

            case "Health":
                call = apiInterface.getArticleByCategory(Constants.COUNTRY, "health", Constants.API_KEY);
                break;

            case "Business":
                call = apiInterface.getArticleByCategory(Constants.COUNTRY, "business", Constants.API_KEY);
                break;

            case "Science":
                call = apiInterface.getArticleByCategory(Constants.COUNTRY, "science", Constants.API_KEY);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + title);
        }

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NotNull Call<News> call, @NotNull Response<News> response) {
                if (response.body() != null) {
                    articles = response.body().getArticles();
                }
                adapter = new MyAdapter(CategoryPage.this, articles);
                binding.categoryRecView.setAdapter(adapter);
                binding.categoryProgressBar.setVisibility(View.INVISIBLE);
                adapter.notifyDataSetChanged();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NotNull Call<News> call, @NotNull Throwable t) {
            }
        });


    }
}