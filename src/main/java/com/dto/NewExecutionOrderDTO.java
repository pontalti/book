package com.dto;

import com.enums.BookStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonInclude(Include.NON_NULL)
public class NewExecutionOrderDTO extends OpenBookDTO{

	private Long id;

    private Integer quantity;

    private Long price;

    @Builder(builderMethodName = "bookBuilder")
    public NewExecutionOrderDTO(Long id, Integer quantity, Long price, Long bookId, BookStatus status, String bookName) {
        super(bookId, status, bookName);
        this.id         = id;
        this.quantity   = quantity;
        this.price      = price;
    }

}
