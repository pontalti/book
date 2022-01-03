package com.dto;

import javax.validation.constraints.NotNull;

import com.enums.OrderType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.validation.EnumOrderTypePattern;

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
public class NewOrderDTO{

	@NotNull(message = "Book Id is mandatory")
	private Long bookId;

	@NotNull(message = "Quantity is mandatory")
    private Integer quantity;

	@NotNull(message = "Price mandatory")
    private Long price;

    @NotNull(message = "Order type is mandatory")
    @EnumOrderTypePattern(anyOf = {OrderType.LIMIT, OrderType.MARKET})
    private OrderType type;

    public NewOrderDTO() {
		super();
	}

}