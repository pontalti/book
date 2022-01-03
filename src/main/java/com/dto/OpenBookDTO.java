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
public class OpenBookDTO extends BookNameDTO{

	private Long id;

    private BookStatus status;

    @Builder(builderMethodName = "bookNameBuilder")
    public OpenBookDTO(Long id, BookStatus status, String bookName) {
        super(bookName);
        this.id = id;
        this.status = status;
    }
    
}
