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
public class OrderDTO{

    @NotNull(message = "Order id is mandatory")
	private Long id;

    @NotNull(message = "Order quantity is mandatory")
    private Integer quantity;

    @NotNull(message = "Order price is mandatory")
    private Long price;

    @NotNull(message = "Order type is mandatory")
    @EnumOrderTypePattern(anyOf = {OrderType.LIMIT, OrderType.MARKET})
    private OrderType type;

    private OpenBookDTO book;

}
