package com.mclientui.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.mclientui.beans.Commande;
import com.mclientui.beans.ProductBean;
import com.mclientui.beans.Paiement;
import com.mclientui.controller.Product;
import com.mclientui.proxies.MicroserviceCommandesProxy;
import com.mclientui.proxies.MicroservicePaiementsProxy;
import com.mclientui.proxies.MicroserviceProduitsProxy;


@Controller
public class ClientController {
	
	@Autowired
	MicroserviceProduitsProxy microserviceProduits;
	
	@Autowired
	MicroserviceCommandesProxy microserviceCommandes;
	
	@Autowired
	MicroservicePaiementsProxy microservicePaiements;
	
	@GetMapping("/")
	public String accueil(Model model, RestTemplate restTemplate) {
		
		List<ProductBean> produits= microserviceProduits.listeDesProduits();
		
		model.addAttribute("result", produits);
		
		return "Accueil";
	}
	
	
	@GetMapping("/details-produit/{id}")
	public String detailsProduit(@PathVariable("id") int id, Model model) {
		ProductBean produit= microserviceProduits.recupererUnProduit(id);
		model.addAttribute("produit", produit);
		Commande commande= new Commande();
		commande.setProductId(produit.getId());
		model.addAttribute("commande", commande );
		return "Produit";
	}
	
	@GetMapping("/details-produit/commander-produit/{id}")
	public String envoyerCommande(@PathVariable("id") int id, Model model) throws Exception {
		ProductBean produit= microserviceProduits.recupererUnProduit(id);
		Commande commande= new Commande();
		commande.setProductId(produit.getId());
		commande.setDateCommande(new Date(System.currentTimeMillis()));
		commande.setQuantite(1);
		commande.setCommandePayee(false);
		
		ResponseEntity<Commande> resultatCommande= microserviceCommandes.ajouterCommande(commande);
		
		if(resultatCommande.getStatusCode() != HttpStatus.CREATED) throw new Exception();
	
		Optional<Commande> commandeEnregistree= microserviceCommandes.recupererUneCommande(resultatCommande.getBody().getId());
		model.addAttribute("commandeEnregistree", commandeEnregistree.get());
		
		return "CommandeEnregistree";

	}
	
	@PostMapping("/commandes")
	public String envoyerCommande(@ModelAttribute("commande") Commande commande, Model model) throws Exception {
		ResponseEntity<Commande> resultatCommande= microserviceCommandes.ajouterCommande(commande);
		
		if(resultatCommande.getStatusCode() != HttpStatus.CREATED) throw new Exception();
	
		Optional<Commande> commandeEnregistree= microserviceCommandes.recupererUneCommande(resultatCommande.getBody().getId());
		model.addAttribute("commandeEnregistree", commandeEnregistree.get());
		
		return "CommandeEnregistree";
	}
	
	@GetMapping("/payer-commande/{commandeId}")
	public String payerCommande(@PathVariable("commandeId") int commandeId, Model model) {
		Optional<Commande> commande= microserviceCommandes.recupererUneCommande(commandeId);
		ProductBean produit= microserviceProduits.recupererUnProduit(commande.get().getProductId());
		Paiement paiement= new Paiement();
		paiement.setIdCommande(commandeId);
		paiement.setMontant( produit.getPrix().intValue() );
		paiement.setNumeroCarte(new Long(1111));
		ResponseEntity<Paiement> resultatPaiement= microservicePaiements.payerUneCommande(paiement);
		
		//il faut verifier que le paiement a bien passer d'abord
		Commande updatedCommand= commande.get();
		updatedCommand.setCommandePayee(true);
		ResponseEntity<Commande> resultatCommande= microserviceCommandes.ajouterCommande(updatedCommand);
		
		model.addAttribute("paiement", resultatPaiement.getBody());
		model.addAttribute("commande", resultatCommande.getBody());
		
		return "PaiementEnregistre";
	}
}
	

