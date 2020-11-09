package com.mclientui.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mclientui.beans.Paiement;

@FeignClient(name="microservice-paiements", url="localhost:9003")
public interface MicroservicePaiementsProxy {

	@PostMapping(value = "/paiement")
    public ResponseEntity<Paiement>  payerUneCommande(@RequestBody Paiement paiement);
    
}
