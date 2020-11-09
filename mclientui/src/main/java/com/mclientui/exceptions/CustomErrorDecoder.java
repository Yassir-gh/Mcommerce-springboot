package com.mclientui.exceptions;

import org.springframework.stereotype.Component;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class CustomErrorDecoder implements ErrorDecoder {
	
	private final ErrorDecoder defaultErrorDecoder = new Default();

	@Override
	public Exception decode(String methodKey, Response response) throws ProductBadRequestException {
		if(response.status() == 400 ) {
            return new ProductBadRequestException( "Requête incorrecte ");
        }

        else if (response.status() == 404 ) {
            return new ProductNotFoundException("Produit non trouvé ");
        }

        return defaultErrorDecoder.decode(methodKey, response);
	}

}
