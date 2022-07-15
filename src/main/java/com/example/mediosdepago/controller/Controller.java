package com.example.mediosdepago.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mediosdepago.Bean.Item;
import com.example.mediosdepago.Bean.Pago;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;

@RestController
public class Controller {

	
	
	@GetMapping(value = "/pago")
	public void redirect(@RequestParam Pago pago ) {
		try {
			System.out.println(pago.getStatus());
			System.out.println(pago.getComercianteOrderId());
			System.out.println(pago.getExternalReference());
			System.out.println(pago.getPaymentId());
			
			
		} catch (Exception e) {
			e.getMessage();
		}	
	}
	
	
	@PostMapping(value = "/creates")
	public ResponseEntity<?> create(@RequestBody Item item) throws MPException, MPApiException{
		
		Preference preference = new Preference();
		// Crea un objeto de preferencia
		PreferenceClient client = new PreferenceClient();

		// Crea un ítem en la preferencia
		List<PreferenceItemRequest> items = new ArrayList<>();	 
		
	
		
		PreferenceItemRequest item1 = PreferenceItemRequest.builder()				   
				   .title(item.getTitle())				  
				   .quantity(item.getQuantity())			   
				   .unitPrice(item.getUnitPrice())
				   .categoryId(item.getCategoryId())
				   .currencyId(item.getCurrencyId())
				   .description(item.getDescription())
				   .pictureUrl(item.getPictureUrl())
				   .build();
				
		items.add(item1);
		
		PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest
				   .builder()
			       .success("localhost:8080/pago")
			       .pending("google.com")
			       .failure("google.com")
			       .build();
		
				
		
		try {
			PreferenceRequest request = PreferenceRequest.builder().items(items).backUrls(backUrls).autoReturn("approved").build();

			preference =client.create(request);
		
			
			String linkDePago = preference.getSandboxInitPoint();

			return new ResponseEntity<>(preference,HttpStatus.OK);
		} catch (MPApiException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);	
		}catch (MPException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);	
		}
		
		
	}
}
