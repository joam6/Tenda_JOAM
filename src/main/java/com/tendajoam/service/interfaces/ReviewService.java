package com.tendajoam.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.tendajoam.entity.product.Review;

public interface ReviewService {

    List<Review> findAll();
    Optional<Review> findById(String id);
    Review save(Review review);
    void delete(String id);
}
