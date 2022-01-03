package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.entity.RoleEntity;
import com.entity.UserEntity;
import com.service.IService;

@SpringBootApplication
@ComponentScan("com")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com")
public class BookApplication extends SpringBootServletInitializer implements CommandLineRunner  {

	@Autowired
	private IService<UserEntity> userService;

	@Autowired
	private IService<RoleEntity> roleService;
	
	public static void main(String[] args) {
		SpringApplication.run(BookApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Pontalti X running!!!!");
		RoleEntity roleAdmin = RoleEntity.builder()
											.id(1L)
											.name("admin")
											.build();
		this.roleService.saveOrUpdate(roleAdmin);
		
		
		RoleEntity roleUser = RoleEntity.builder()
											.id(2L)
											.name("user")
											.build();		
		this.roleService.saveOrUpdate(roleUser);
		
		UserEntity user1 = UserEntity.builder()
										.email("test@user.com")
										.name("Test user")
										.mobile("54546451565")
										.password(new BCryptPasswordEncoder().encode("user"))
										.role(this.roleService.findById(2L).get())
										.build();
		this.userService.saveOrUpdate(user1);
		
		UserEntity user2 = UserEntity.builder()
										.email("test@admin.com")
										.name("Test admin")
										.mobile("8989894830")
										.password(new BCryptPasswordEncoder().encode("admin"))
										.role(this.roleService.findById(1L).get())
										.build();
		this.userService.saveOrUpdate(user2);
	}

}
