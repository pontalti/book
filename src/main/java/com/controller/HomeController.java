package com.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dto.BookDTO;
import com.dto.BookNameDTO;
import com.dto.ExecutionOrderDTO;
import com.dto.NewExecutionOrderDTO;
import com.dto.NewOrderDTO;
import com.dto.OpenBookDTO;
import com.dto.OrderDTO;
import com.service.BookService;
import com.service.ExecutionOrderService;
import com.service.OrderService;

@RestController
public class HomeController {
    
	@Autowired
	private BookService bookService;

	@Autowired
	private OrderService orderService;

	@Autowired 
	private ExecutionOrderService executionOrderService;

	@GetMapping
	public String home() {
		return "<h2>Rest doc ->  <a href='http://localhost:8080/swagger-ui/index.html' target='_blank'>Swagger ui for code challenge</a></h2>";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(path = "/open-book", consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<OpenBookDTO> openBook(	@RequestBody(required = true)
																@NotNull(message = "Book name cannot be null.") 
																@NotEmpty(message = "Book name cannot be empty.") 
																@Valid BookNameDTO book) {
		var o = this.bookService.create(book, OpenBookDTO.class);
		var response =  new ResponseEntity<OpenBookDTO>(o, HttpStatus.OK);
		return response;
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping(path = "/order/create", consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<OrderDTO> createOrder(  @RequestBody(required = true) 
																@NotNull(message = "Order cannot be null.") 
																@NotEmpty(message = "Order cannot be empty.") 
																@Valid NewOrderDTO dto) {
		var o = this.orderService.create(dto, OrderDTO.class);
		var response =  new ResponseEntity<OrderDTO>(o, HttpStatus.OK);
		return response;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(path = "/order/execution", consumes = MediaType.APPLICATION_JSON_VALUE ,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<NewExecutionOrderDTO> executionOrder(  @RequestBody(required = true) 
																	@NotNull(message = "Order cannot be null.") 
																	@NotEmpty(message = "Order cannot be empty.") 
																	@Valid ExecutionOrderDTO dto) {
		var o = this.executionOrderService.create(dto, NewExecutionOrderDTO.class);
		var response =  new ResponseEntity<NewExecutionOrderDTO>(o, HttpStatus.OK);
		return response;
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(path = "/order/execution/bookId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<BookDTO> getExecutionResult(@PathVariable("id") @NotNull @NotBlank Long bookId) {
		var o = this.bookService.findById(bookId, BookDTO.class);
		var response =  new ResponseEntity<BookDTO>(o, HttpStatus.OK);
		return response;	
	}

}