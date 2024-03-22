package ru.mai.shop.sale.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.mai.shop.sale.config.mapper.NewSaleRequestToEntityConverter;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper mapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new NewSaleRequestToEntityConverter());

        return modelMapper;
    }
}
