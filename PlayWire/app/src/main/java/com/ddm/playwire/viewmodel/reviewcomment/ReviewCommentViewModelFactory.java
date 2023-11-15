package com.ddm.playwire.viewmodel.reviewcomment;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ddm.playwire.repository.ReviewCommentRepository;
import com.ddm.playwire.viewmodel.user.UserViewModel;

public class ReviewCommentViewModelFactory implements ViewModelProvider.Factory {

    private final ReviewCommentRepository reviewCommentRepository;

    public ReviewCommentViewModelFactory(ReviewCommentRepository reviewCommentRepository) {
        this.reviewCommentRepository = reviewCommentRepository;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(ReviewCommentViewModel.class)) {
            return (T) new ReviewCommentViewModel(reviewCommentRepository);
        }
        throw new IllegalArgumentException("Classe ViewModel desconhecida: " + modelClass.getName());
    }
}
