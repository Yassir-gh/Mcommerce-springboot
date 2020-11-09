package com.mclientui.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mclientui.beans.ProductBean;

@FeignClient(name="microservice-produits", url="localhost:9001")
public interface MicroserviceProduitsProxy {
	
	@GetMapping("/Produits")
	List<ProductBean> listeDesProduits();
	
	@GetMapping("/Produits/{id}")
	ProductBean recupererUnProduit(@PathVariable("id") int id);

}
