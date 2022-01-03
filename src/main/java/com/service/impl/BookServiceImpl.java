package com.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.BookDTO;
import com.dto.BookNameDTO;
import com.dto.NewExecutionOrderDTO;
import com.dto.OpenBookDTO;
import com.dto.OrderDTO;
import com.dto.OrderExecutionResultDTO;
import com.entity.BookEntity;
import com.enums.BookStatus;
import com.repository.BookRepository;
import com.service.BookService;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository rep;

    @Transactional
    @Override
    public <T, S> T create(S obj, Class<T> c) {
    	var dto = (BookNameDTO) obj;
        var entity = BookEntity.builder()
				                .name(dto.getName())
				                .status(BookStatus.OPENED)
				                .build();
		this.rep.save(entity);
		var bookDTO = OpenBookDTO.bookNameBuilder()
				                    .bookName(entity.getName())
				                    .id(entity.getId())
				                    .status(entity.getStatus())
				                    .build();
		return c.cast(bookDTO);
    }

	@Override
	public <T> T findById(Long id, Class<T> c) {
		List<OrderExecutionResultDTO> results 	= null;
		NewExecutionOrderDTO order 				= null;
		List<OrderDTO> orders 					= null;
		
		var e = this.rep.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid book id"));
		
		try {
			Hibernate.initialize(e.getExecutionResult());
		} catch (HibernateException e1) {

		}
		if (null != e.getExecutionResult()) {
			results = e.getExecutionResult()
						.stream()
						.map(r -> OrderExecutionResultDTO.builder().id(r.getId()).price(r.getPrice())
								.quantity(r.getQuantity()).status(r.getStatus()).book(null).build())
						.collect(Collectors.toList());
		}
		
		if (null != e.getExecutionOrder()) {
			order = NewExecutionOrderDTO
						.bookBuilder()
							.id(e.getExecutionOrder().getId())
							.price(e.getExecutionOrder()
							.getPrice())
							.quantity(e.getExecutionOrder()
							.getQuantity())
							.bookId(null)
						.build();
		}
		
		if (null != e.getOrders()) {
			orders = e.getOrders()
						.stream()
						.map(o -> OrderDTO
									.builder()
										.id(o.getId())
										.quantity(o.getQuantity())
										.price(o.getPrice())
										.type(o.getType())
										.book(null)
									.build())
						.collect(Collectors.toList());
		}
		BookDTO dto = BookDTO.builder()
								.id(e.getId())
								.name(e.getName())
								.status(e.getStatus())
								.executionResult(results)
								.executionOrder(order)
								.orders(orders)
								.build();
		return c.cast(dto);
	}
    
}