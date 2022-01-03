package com.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dto.ExecutionOrderDTO;
import com.dto.NewExecutionOrderDTO;
import com.entity.BookEntity;
import com.entity.ExecutionOrderEntity;
import com.entity.OrderExecutionResultEntity;
import com.enums.BookStatus;
import com.enums.ExecutionOrderResultType;
import com.enums.OrderType;
import com.repository.BookRepository;
import com.repository.ExecutionOrderRepository;
import com.repository.OrderExecutionResultRepository;
import com.service.ExecutionOrderService;

@Service
public class ExecutionOrderServiceimpl implements ExecutionOrderService{
    
    @Autowired
    private ExecutionOrderRepository executionOrderRepository;

    @Autowired
    private OrderExecutionResultRepository orderExecutionResultRepository;

    @Autowired
    private BookRepository bookRep;

    @Transactional
	@Override
	public <T, S> T create(S obj, Class<T> c) {
		var dto = (ExecutionOrderDTO) obj;
        var book = this.bookRep.findById(dto.getBookId()).orElseThrow(()-> new IllegalArgumentException("Invalid book id"));
        if( book.getStatus().equals(BookStatus.CLOSED) ) {
        	throw new IllegalArgumentException("The book is closed, please open a new book and create new orders, before execute a order.");
        }else if(null == book.getOrders() || book.getOrders().isEmpty()){
        	throw new IllegalArgumentException("Please create new orders, before before execute a order.");
        }
        var entity = extractExecutionOrder(dto, book);
        var orderExecList =  extractOrderExecList(dto, book);
        distributionOfOrderQuantity(dto, orderExecList);
        this.orderExecutionResultRepository.saveAll(orderExecList);
        this.executionOrderRepository.save(entity);
        var newExecutionOrderDTO = NewExecutionOrderDTO.bookBuilder()
        													.id(entity.getId())
                                                            .quantity(entity.getQuantity())
                                                            .price(entity.getPrice())
                                                            .bookId(entity.getBook().getId())
                                                            .bookName(entity.getBook().getName())
                                                            .status(entity.getBook().getStatus())
                                                            .build();
        return c.cast(newExecutionOrderDTO);
	}

    private void distributionOfOrderQuantity(final ExecutionOrderDTO dto, final List<OrderExecutionResultEntity> orderExecList) {
        Long quantityOfExecution = orderExecList.stream().filter(o->o.getStatus().equals(ExecutionOrderResultType.EXECUTED)).count();
        AtomicInteger counter = new AtomicInteger(dto.getQuantity());
        counter.incrementAndGet();
        Integer initial  = 1;
        if( quantityOfExecution > 0 ) {
        	List<Integer> quantList =  IntStream.range(initial, counter.intValue())
					.map(i -> Integer.valueOf(counter.intValue() - i + initial - 1))
					.boxed().collect(Collectors.toList());
        	
	        if (!quantList.isEmpty() && quantList.get(0) > 1 ) {
	        	
	        	Map<Integer, List<Integer>> groups = quantList
	        											.stream()
	        											.collect(Collectors.groupingBy(s -> (s - 1) / (quantList.size()/quantityOfExecution.intValue()) ));
	        	List<List<Integer>> subSets = new ArrayList<List<Integer>>(groups.values());
	        	
	            orderExecList.stream().forEach(o->{
			                    if( o.getStatus().equals(ExecutionOrderResultType.EXECUTED) ){
			                        o.setQuantity(subSets.get(0).size());
			                        o.setPrice(dto.getPrice());
			                    }
                });
	  			if( subSets.size() > quantityOfExecution.intValue() ) {
	  				int total = subSets.size() - quantityOfExecution.intValue();
	  				for( int x = 0 ; x < total ; x++ ) {
	  					for(OrderExecutionResultEntity o : orderExecList ) {
	  						if( o.getStatus().equals(ExecutionOrderResultType.EXECUTED) ){
	  							Integer newQuant = o.getQuantity();
	  							newQuant++;
	  							o.setQuantity(newQuant);
	  							o.setPrice(dto.getPrice());
	  							break;
	  						}	  						
	  					}
	  				} 
	  			}
	        }else{
	            for(OrderExecutionResultEntity o : orderExecList){
	                if( o.getStatus().equals(ExecutionOrderResultType.EXECUTED) ){
	                    o.setQuantity(dto.getQuantity());
	                    o.setPrice(dto.getPrice());
	                    break;
	                }
	            }
	            orderExecList.stream().forEach(o->{
	                if( o.getStatus().equals(ExecutionOrderResultType.EXECUTED) && o.getQuantity() == -1){
	                    o.setQuantity(0);
	                    o.setStatus(ExecutionOrderResultType.INVALID);
	                    o.setPrice(dto.getPrice());
	                }
	            });
	        }
        }else {
        	orderExecList.stream().forEach(o->{
                    o.setQuantity(0);
                    o.setStatus(ExecutionOrderResultType.INVALID);
                    o.setPrice(dto.getPrice());
            });
        }
    }

    private ExecutionOrderEntity extractExecutionOrder(final ExecutionOrderDTO dto, final BookEntity book) {
        book.setStatus(BookStatus.CLOSED);
        ExecutionOrderEntity entity = ExecutionOrderEntity.builder()
                                                            .quantity(dto.getQuantity())
                                                            .price(dto.getPrice())
                                                            .book(book)
                                                            .build();
        return entity;
    }

    private List<OrderExecutionResultEntity> extractOrderExecList(final ExecutionOrderDTO dto, final BookEntity book) {
        return  book.getOrders()
                    .stream()
                    .map(o->{
                            OrderExecutionResultEntity e;
                            if(o.getType().equals(OrderType.LIMIT) && 
                                   ( o.getPrice() == dto.getPrice() ||  dto.getPrice() > o.getPrice() ) ){
                                e = OrderExecutionResultEntity
                                    .builder()
                                    .quantity(-1)
                                    .price(dto.getPrice())
                                    .status(ExecutionOrderResultType.EXECUTED)
                                    .book(book)
                                    .build();
                            }else if(o.getType().equals(OrderType.MARKET)){
                                e = OrderExecutionResultEntity
                                    .builder()
                                    .quantity(-1)
                                    .price(dto.getPrice())
                                    .status(ExecutionOrderResultType.EXECUTED)
                                    .book(book)
                                    .build();
                            }else{
                                e = OrderExecutionResultEntity
                                    .builder()
                                    .quantity(0)
                                    .price(dto.getPrice())
                                    .status(ExecutionOrderResultType.INVALID)
                                    .book(book)
                                    .build();
                            }
                            return e;
                        }).collect(Collectors.toList());
    }

	@Override
	public <T> T findById(Long id, Class<T> c) {
		ExecutionOrderEntity e = this.executionOrderRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("Invalid id"));
		NewExecutionOrderDTO order = NewExecutionOrderDTO.bookBuilder()
									.id(e.getId())
									.price(e.getPrice())
									.quantity(e.getQuantity())
									.bookId(e.getBook().getId())
									.build();
		return c.cast(order);
	}
    
}