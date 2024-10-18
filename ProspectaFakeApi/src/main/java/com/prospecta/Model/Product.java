package com.prospecta.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    
    @NotBlank(message = "Title is mandatory")
    private String title;
    
    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be greater than zero")
    private Double price;
    
    @NotBlank(message = "Description is mandatory")
    private String description;
    
    @NotBlank(message = "Category is mandatory")
    private String category;
    
    @NotBlank(message = "Image URL is mandatory")
    private String image;
    
    @Valid
    @JsonProperty("rating")
    private Rating rating;
   
}




