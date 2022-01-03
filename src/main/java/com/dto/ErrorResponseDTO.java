package com.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ErrorResponseDTO {

	private String message;
	private List<String> details;

	public ErrorResponseDTO() {
		super();
	}

}
