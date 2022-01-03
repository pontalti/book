package com.cs.cs;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.BookApplication;
import com.dto.BookDTO;
import com.dto.BookNameDTO;
import com.dto.ExecutionOrderDTO;
import com.dto.NewExecutionOrderDTO;
import com.dto.NewOrderDTO;
import com.dto.OpenBookDTO;
import com.dto.OrderDTO;
import com.enums.OrderType;
import com.service.BookService;
import com.service.ExecutionOrderService;
import com.service.OrderService;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = BookApplication.class)
@AutoConfigureMockMvc
public class CsApplicationServiceFlowTests {

	@Autowired
	private BookService bookService;

	@Autowired
	private OrderService orderService;

	@Autowired 
	private ExecutionOrderService executionOrderService;
	
	@ParameterizedTest
	@DisplayName("Open book, testing the bookService creation flow")
	@CsvSource({ "Book test" })
	public void text_1(String bookName) {
		BookNameDTO bookNameDTO = BookNameDTO
											.builder()
											.name(bookName)
											.build();
		var o = this.bookService.create(bookNameDTO, BookNameDTO.class);
		assertThat(o).isInstanceOf(OpenBookDTO.class);
	}
	
	@ParameterizedTest
	@DisplayName("Creating Orders, testing the OrderService creation flow")
	@CsvSource({ "1,50,LIMIT,17","1,130,LIMIT,14","1,150,MARKET,14" })
	public void text_2(Long bookId, Integer quantity, String type, Long price) {
		
		NewOrderDTO newOrderDTO = NewOrderDTO.builder()
												.bookId(bookId)
												.quantity(quantity)
												.price(price)
												.type(OrderType.valueOf(type))
												.build();
		var o = this.orderService.create(newOrderDTO, OrderDTO.class);
		assertThat(o).isInstanceOf(OrderDTO.class);
	}
	
	@ParameterizedTest
	@DisplayName("Creating Order Execution, testing the executionOrderService creation flow")
	@CsvSource({ "1,80,15" })
	public void text_3(Long bookId, Integer quantity, Long price) {
		ExecutionOrderDTO dto = ExecutionOrderDTO
												.builder()
												.bookId(bookId)
												.quantity(quantity)
												.price(price)
												.build();
		var o = this.executionOrderService.create(dto, NewExecutionOrderDTO.class);
		assertThat(o).isInstanceOf(NewExecutionOrderDTO.class);
	}
	
	@Test
	@DisplayName("Open book exception test, testing the bookService creation flow")
	public void text_4() {
		BookNameDTO bookNameDTO = BookNameDTO
											.builder()
											.name(null)
											.build();
		
		DataIntegrityViolationException thrown = Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
			this.bookService.create(bookNameDTO, BookNameDTO.class);
		});
		Assertions.assertNotNull(thrown);
	}
	
	@ParameterizedTest
	@DisplayName("Creating Orders exception test, testing the OrderService creation flow")
	@CsvSource({ "1,50,LIMIT,17","1,130,LIMIT,14","1,150,MARKET,14" })
	public void text_5(Long bookId, Integer quantity, String type, Long price) {
		
		NewOrderDTO newOrderDTO = NewOrderDTO.builder()
												.bookId(bookId)
												.quantity(quantity)
												.price(price)
												.type(OrderType.valueOf(type))
												.build();
		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			this.orderService.create(newOrderDTO, OrderDTO.class);
		});
		Assertions.assertNotNull(thrown);
	}
		
	@ParameterizedTest
	@DisplayName("Creating Order Execution exception test, testing the executionOrderService creation flow")
	@CsvSource({ "1,80,15" })
	public void text_6(Long bookId, Integer quantity, Long price) {
		ExecutionOrderDTO dto = ExecutionOrderDTO
												.builder()
												.bookId(bookId)
												.quantity(quantity)
												.price(price)
												.build();
		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			this.executionOrderService.create(dto, NewExecutionOrderDTO.class);
		});
		Assertions.assertNotNull(thrown);
	}
	
	@ParameterizedTest
	@DisplayName("Retrieving data by book id, testing the bookService findById flow")
	@CsvSource({ "2" })
	public void text_7(Long bookId) {
		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			this.bookService.findById(bookId, BookDTO.class);
		});
		Assertions.assertNotNull(thrown);
	}

}
