package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.DishDTO;
import com.sky.result.Result;
import com.sky.service.DishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dish Management Controller
 */
@RestController
@RequestMapping("/admin/dish")
@Api("Dish Management API")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * Add a new dish
     *
     * @param categoryDTO the dish data transfer object
     * @return Result indicating success or failure
     */
    @PostMapping
    @ApiOperation("Add a new dish")
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("Received request to save dish: {}", dishDTO);
        // Logic to save a dish
        dishService.saveWithFlavor(dishDTO);

        return Result.success();
    }
}
