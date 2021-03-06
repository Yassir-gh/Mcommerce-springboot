package com.mclientui.proxies;

import java.util.List;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mclientui.beans.ProductBean;

@FeignClient(name="spring-gateway-server")
@RibbonClient(name = "microservice-produits")
public interface MicroserviceProduitsProxy {
	
	@GetMapping("/Produits")
	List<ProductBean> listeDesProduits();
	
	@GetMapping("/Produits/{id}")
	ProductBean recupererUnProduit(@PathVariable("id") int id);

}
