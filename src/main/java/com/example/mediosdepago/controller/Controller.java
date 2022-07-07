package com.example.mediosdepago.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.mediosdepago.Bean.Item;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;

@RestController
public class Controller {

	
	
	@PostMapping(value = "/creates")
	public String create(@RequestBody Item item) throws MPException, MPApiException{
		
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

		
				
		
		try {
			PreferenceRequest request = PreferenceRequest.builder().items(items).build();

			preference =client.create(request);
		
			
			String linkDePago = preference.getSandboxInitPoint();

			return linkDePago;
		} catch (MPApiException e) {
			return e.getMessage();	
		}catch (MPException e) {
			return e.getMessage();	
		}
		
		
	}
}
