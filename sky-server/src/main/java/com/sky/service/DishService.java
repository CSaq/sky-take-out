package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.result.Result;

public interface DishService {

    /**
     * Add a new dish with flavor information
     *
     * @param dishDTO the dish data transfer object
     * @return Result indicating success or failure
     */
    void saveWithFlavor(DishDTO dishDTO);

    /**
     * Update an existing dish
     *
     * @param dishDTO the dish data transfer object
     * @return Result indicating success or failure
     */
//    Result update(DishDTO dishDTO);

    /**
     * Delete a dish by its ID
     *
     * @param id the ID of the dish to delete
     * @return Result indicating success or failure
     */
//    Result delete(Long id);
}
