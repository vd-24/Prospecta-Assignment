package com.prospecta.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating{
	@NotNull(message = "Rating value cannot be null")
    @DecimalMin(value = "1.0" ,message="rating must be greater than 1.0")
    @DecimalMax(value = "5.0" , message="rating must be less than 5.0")
	@JsonProperty("rate")
	private Double rate;
	
	@JsonProperty("count")
    private Integer count;
}
