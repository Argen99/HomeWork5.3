package com.geektech.homework53;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.geektech.homework53.databinding.ActivityMainBinding;
import com.geektech.homework53.network.RetrofitService;
import com.geektech.homework53.network.model.Hit;
import com.geektech.homework53.network.model.PixabayModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RetrofitService retrofitService;
    private ActivityMainBinding binding;
    private ImageAdapter adapter;
    public final static String KEY = "27723662-43b1a48fa73b0ddcb14cd5408";
    private SwipeRefreshLayout swipeRefreshLayout;

    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        retrofitService = new RetrofitService();
        initClickers();

        binding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                String word = binding.editText.getText().toString();
                getImageFromApi(word, ++count, 10);
                binding.swipeContainer.setRefreshing(false);
            }
        });
    }


    private void initClickers() {

        binding.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word = binding.editText.getText().toString();
                getImageFromApi(word, 1, 10);
                count = 1;

            }
        });
        binding.btnChangePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word = binding.editText.getText().toString();
                getImageFromApi(word, ++count, 10);
            }
        });
    }


    private void getImageFromApi(String word, int page, int perPage) {
        retrofitService.getApi().getImages(KEY, word, page, perPage).enqueue(new Callback<PixabayModel>() {
            @Override
            public void onResponse(Call<PixabayModel> call, Response<PixabayModel> response) {
                if (response.isSuccessful()){
                    Log.e("ololo","onResponse: " + response.body().getHits().get(0).getLargeImageURL());
                    adapter = new ImageAdapter((ArrayList<Hit>) response.body().getHits());
                    binding.recycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<PixabayModel> call, Throwable t) {
                Log.e("ololo","onFailure: " + t.getLocalizedMessage());
            }
        });
    }
}