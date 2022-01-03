# Book app with Spring Boot, Security, Data JPA, Maven, H2DB and Docker

1. My Dev enviroment 👍
   - Windows 10
   - Visual Studio Code
		- Plugins
			- Extension Pack for Java - Microsoft
			- Project Manager for Java - Microsoft
			- Debugger for Java - Microsoft
			- Maven for Java - Microsoft
			- Remote - Containers - Microsoft
			- Test Runner for Java - Microsoft
			- Spring Initializr Java Support - Microsoft
			- Spring Boot Dashboard - Microsoft
			- Spring Boot Extension Pack - Pivotal
			- Spring Boot Tools - Pivotal
			- Dependency Analytics - Red Hat
			- Language Support for Java(TM) by Red Hat
			- YAML - Red Hat
			- Lombok Annotations Support for VS Code
   - JDK 17
   - Maven  3.6.3
   - Postman for Windows Version 9.1.5
   - Docker
		- Docker for Windows (Docker version 20.10.11) 
		- Docker Desktop for Windows 4.3.0

2. If necessary install the JDK 17
	- Choose your distribution and install the JDK
	- Create the Java Home
	- Put the JAVA_HOME on the System Patch
	- Test JDK on command line
		- ``` java -version ```		

3. If necessary install Maven, download it on the link below
	- ``` https://maven.apache.org/download.cgi ```
	- Extract compressed file in your prefered tool folder.
	- Create the M2_HOME
		- Windows -> ``` M2_HOME = [YOUR_PATCH]\apache-maven-3.6.3 ```
		- Linux -> ``` M2_HOME = [YOUR_PATCH]/apache-maven-3.6.3 ```
	- Put the Maven on the System Patch
		- For Windows -> ``` %M2_HOME%\bin ```
		- For Linux -> ``` export PATH=$M2_HOME/bin:$PATH ```
	- Test Maven on command line
		- ``` mvn --version ```

4. To build please.
	- Go to the project root folder.
	- Run the command below.
		- ``` mvn clean install package -U ```

6. if necessary Install the project Lombok on your IDE, follow the instruction on the link below.
	- ``` https://projectlombok.org/setup/overview ```

7. Open the project in your favotite IDE

8. To run the SpringBoot application in commandline.
	- Go to the project root folder.
	- Run the command below.
		- ``` mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=*:8000" ```
	- OR
		- ``` java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000 -jar target\book.jar ```
	- To use the Spring dev tools features please configure the -> ``` Spring Boot Remote ```
		- Remote URL -> ``` http://localhost:8080/ ```
		- Remote Secret -> ``` test ```

9. To run the SpringBoot application with Docker.
	- Please install Docker.
	- Go to the project root folder.
	- Run the commands below.
		- ``` docker-compose build ```			
		- ``` docker-compose up -d ```
	- To check the log, please run the command below.
		- ``` docker logs -f book ```
	- To use the Spring dev tools features please configure the -> ``` Spring Boot Remote ```
		- Remote URL -> ``` http://localhost:8080/ ```
		- Secret -> ``` Test ```
	- To debug
		- connect in remote JVM using the port -> ``` 8000 ```

10. To access the Swagger-ui, please use the link -> ``` http://localhost:8080/swagger-ui/index.html ```
	- Use the link below for the home page and click in the link.
		- ``` http://localhost:8080/ ```

11. Using POSTMAN to call the end-points
	- Please import in the POSTMAN the API definition, -> ``` located on the postman folder in the project root folder ```
