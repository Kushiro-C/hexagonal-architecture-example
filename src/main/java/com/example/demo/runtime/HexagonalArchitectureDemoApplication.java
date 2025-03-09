package com.example.demo.runtime;

import com.example.demo.domain.DomainService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(
        basePackages = {"com.example.demo"},
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {DomainService.class})}
)
public class HexagonalArchitectureDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HexagonalArchitectureDemoApplication.class, args);
    }

}
