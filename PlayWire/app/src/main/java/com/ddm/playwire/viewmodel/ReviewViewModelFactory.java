package com.ddm.playwire.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ddm.playwire.repository.ReviewRepository;

public class ReviewViewModelFactory implements ViewModelProvider.Factory{

    private ReviewRepository reviewRepository;

    public ReviewViewModelFactory(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ReviewViewModel.class)) {
            return (T) new ReviewViewModel(reviewRepository);
        }
        throw new IllegalArgumentException("Classe ViewModel desconhecida: " + modelClass.getName());
    }
}
