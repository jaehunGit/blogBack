package com.JJH.back.review.controller;

import com.JJH.back.common.ResponseMessage;
import com.JJH.back.review.entity.ReviewEntity;
import com.JJH.back.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/api/reviewSave")
    public ResponseEntity<ResponseMessage> saveReview(@RequestBody ReviewEntity review) {


        return ResponseEntity.status(HttpStatus.OK).body(reviewService.saveReview(review));
    }

    @GetMapping("/api/reviews")
    public ResponseEntity<List<ReviewEntity>> getReviews() {

        return ResponseEntity.status(HttpStatus.OK).body(reviewService.getAllReviews());
    }

    @PostMapping("/api/reviewDelete")
    public ResponseEntity<ResponseMessage> deleteReview(@RequestBody ReviewEntity reviewEntity) {

        return ResponseEntity.status(HttpStatus.OK).body(reviewService.deleteReview(reviewEntity));
    }
}
