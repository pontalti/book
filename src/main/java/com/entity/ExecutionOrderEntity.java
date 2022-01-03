package com.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
@Entity
@Table(name="EXECUTION_ORDER")
public class ExecutionOrderEntity{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    private Integer quantity;

    private Long price;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name="BOOK_ID", nullable=false)
    private BookEntity book;

    public ExecutionOrderEntity(){
        super();
    }

}
