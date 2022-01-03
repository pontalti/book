package com.service.impl;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dto.NewOrderDTO;
import com.dto.OpenBookDTO;
import com.dto.OrderDTO;
import com.entity.OrderEntity;
import com.enums.BookStatus;
import com.repository.BookRepository;
import com.repository.OrderRepository;
import com.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookRepository bookRep;

    @Transactional
	@Override
	public <T, S> T create(S obj, Class<T> c) {
    	var dto = (NewOrderDTO) obj;
        var book = this.bookRep.findById(dto.getBookId()).orElseThrow(()-> new IllegalArgumentException("Invalid book id"));
        if( book.getStatus().equals(BookStatus.CLOSED) ) {
        	throw new IllegalArgumentException("The book is closed, please open a new book before create a new order.");
        }
        var entity = OrderEntity.builder()
	                            .quantity(dto.getQuantity())
	                            .price(dto.getPrice())
	                            .type(dto.getType())
	                            .book(book)
	                            .build();
        this.orderRepository.save(entity);
        Hibernate.initialize(entity.getBook());
        var orderDTOs = OrderDTO.builder()
		                        .id(entity.getId())
		                        .quantity(entity.getQuantity())
		                        .price(entity.getPrice())
		                        .type(entity.getType())
		                        .book(OpenBookDTO.bookNameBuilder()
		                                            .bookName(entity.getBook()
		                                                        .getName())
		                                            .id(entity.getBook()
		                                                        .getId())
		                                            .status(entity.getBook()
		                                                        .getStatus())
		                                .build())       
		                        .build();

        return c.cast(orderDTOs);
	}

	@Override
	public <T> T findById(Long id, Class<T> c) {
		var o = this.orderRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid book id"));
		var order = OrderDTO.builder()
							.id(o.getId())
							.quantity(o.getQuantity())
							.price(o.getPrice())
							.type(o.getType())
							.book(null)
							.build();
		return c.cast(order);
	}
    
}