package com.angMetal.orders.controller;

import models.FactureEvent;
import com.angMetal.orders.entity.FactureAchat;
import com.angMetal.orders.entity.FactureVente;
import com.angMetal.orders.service.FactureService;
import com.angMetal.orders.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/factures")
@RequiredArgsConstructor
public class FactureController {

    private final FactureService factureService;
    private final KafkaProducerService kafkaProducerService;

    /**
     * Endpoint to create a new facture vente.
     *
     * @param factureVente FactureVente object.
     * @return The created FactureVente.
     */
    @PostMapping("/vente")
    public FactureVente createFactureVente(@RequestBody @Valid FactureVente factureVente) {
        // Save factureVente in database
        FactureVente savedFacture = factureService.createFactureVente(factureVente);

        return savedFacture;
    }

    /**
     * Endpoint to create a new facture achat.
     *
     * @param factureAchat FactureAchat object.
     * @return The created FactureAchat.
     */
    @PostMapping("/achat")
    public FactureAchat createFactureAchat(@RequestBody @Valid FactureAchat factureAchat) {
        // Save factureAchat in database
        FactureAchat savedFacture = factureService.createAchatFacture(factureAchat);

        return savedFacture;
    }
}
