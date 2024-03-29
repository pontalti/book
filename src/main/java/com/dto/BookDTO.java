package com.dto;

import java.util.List;

import com.enums.BookStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@JsonInclude(Include.NON_NULL)
public class BookDTO{

	private Long id;

    private String name;
    
    private BookStatus status;

    private List<OrderDTO> orders;

    private List<OrderExecutionResultDTO> executionResult;
	    
    private NewExecutionOrderDTO executionOrder;
}
