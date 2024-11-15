package com.example.cineplex.helpers;

import android.view.View;
import android.widget.ProgressBar;

public class LoadingHelper {

    private ProgressBar progressBar;

    public LoadingHelper(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void showLoading(ProgressBar progressBar) {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideLoading(ProgressBar progressBar) {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

}
