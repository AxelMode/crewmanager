package com.napptilus.crewmanager.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.Data;

@Data
public class UserDto {

	private Integer id;

	@NotBlank(message = "{message.name.empty.error}")
	private String name;

	private String job;

	private String description;

	@NotNull(message = "{message.age.null.error}")
	@PositiveOrZero(message = "{message.age.positiveOrZero}")
	private Integer age;

	@NotNull(message = "{message.height.null.error}")
	@Positive(message = "{message.height.positive}")
	private Float height;

	@NotNull(message = "{message.weight.null.error}")
	@Positive(message = "{message.weight.positive}")
	private Float weight;

}
