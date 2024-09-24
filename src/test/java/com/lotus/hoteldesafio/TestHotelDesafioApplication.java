package com.lotus.hoteldesafio;

import org.springframework.boot.SpringApplication;

public class TestHotelDesafioApplication {

    public static void main(String[] args) {
        SpringApplication.from(HotelDesafioApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
