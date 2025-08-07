package com.mt.pharmacy_be.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapConfig {
    /**
     * Provides a ModelMapper bean for object mapping.
     * Author: Tri Dung
     * Date: 21/7/2025
     * Description: This configuration creates a ModelMapper instance
     * */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
