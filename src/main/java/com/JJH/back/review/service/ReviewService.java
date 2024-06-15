package com.JJH.back.review.service;


import com.JJH.back.common.ResponseMessage;
import com.JJH.back.review.entity.ReviewEntity;
import com.JJH.back.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ResponseMessage saveReview(ReviewEntity reviewEntity) {

        reviewEntity.setCreatedTimestamp(LocalDateTime.now());

        reviewRepository.save(reviewEntity);

        return ResponseMessage.builder().statusCode(HttpStatus.OK).message("작성완료").build();
    }

    public List<ReviewEntity> getAllReviews() {
        return reviewRepository.findAll();
    }

    public ResponseMessage deleteReview(ReviewEntity reviewEntity) {
        try {
            reviewRepository.deleteById(reviewEntity.getId());
            return ResponseMessage.builder().statusCode(HttpStatus.OK).message("삭제완료").build();
        } catch (Exception e) {
            return ResponseMessage.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR).message("삭제 실패").build();
        }
    }
}
