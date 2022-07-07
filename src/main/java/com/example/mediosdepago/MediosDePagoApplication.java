package com.example.mediosdepago;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mercadopago.MercadoPagoConfig;

@SpringBootApplication
public class MediosDePagoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediosDePagoApplication.class, args);
		MercadoPagoConfig.setAccessToken("APP_USR-7783764570942435-062913-da10e8bd15d44f912ec797aae298bbe2-1151515626");
	}

}
