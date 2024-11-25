package com.angMetal.orders.service;

import com.angMetal.orders.entity.FactureAchat;
import com.angMetal.orders.entity.FactureVente;
import com.angMetal.orders.kafka.FactureProducer;
import com.angMetal.orders.repositories.FactureAchatRepository;
import com.angMetal.orders.repositories.FactureVenteRepository;
import models.FactureEvent;
import com.angMetal.orders.enums.FactureType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FactureService {

    private final FactureProducer factureProducer;
    private final FactureVenteRepository factureVenteRepository;
    private final FactureAchatRepository factureAchatRepository;

    /**
     * Create a new vente facture and send the corresponding event to Kafka.
     * @param factureVente The vente facture to be created.
     * @return The created vente facture.
     */
    @Transactional
    public FactureVente createFactureVente(FactureVente factureVente) {
        FactureVente savedFactureVente = factureVenteRepository.save(factureVente);

        // Create FactureEvent for vente facture
        FactureEvent factureEvent = buildFactureEvent(savedFactureVente, FactureType.VENTE);

        // Send the facture event to Kafka
        factureProducer.sendFactureEvent(factureEvent);

        return savedFactureVente;
    }

    /**
     * Create a new achat facture and send the corresponding event to Kafka.
     * @param factureAchat The achat facture to be created.
     * @return The created achat facture.
     */
    @Transactional
    public FactureAchat createAchatFacture(FactureAchat factureAchat) {
        FactureAchat savedFactureAchat = factureAchatRepository.save(factureAchat);

        // Create FactureEvent for achat facture
        FactureEvent factureEvent = buildFactureEvent(savedFactureAchat, FactureType.ACHAT);

        // Send the facture event to Kafka
        factureProducer.sendFactureEvent(factureEvent);

        return savedFactureAchat;
    }

    /**
     * Build a FactureEvent from a facture (vente or achat).
     * @param facture The facture (vente or achat).
     * @param factureType The type of facture (VENTE or ACHAT).
     * @return The corresponding FactureEvent.
     */
    private FactureEvent buildFactureEvent(Object facture, FactureType factureType) {
        FactureEvent factureEvent = new FactureEvent();
        factureEvent.setType(factureType.name());

        if (factureType == FactureType.VENTE) {
            FactureVente venteFacture = (FactureVente) facture;
            factureEvent.setFactureId(venteFacture.getFactureID());
            factureEvent.setAmount(venteFacture.getMontantTotal());
            factureEvent.setCustomerId(venteFacture.getClient().getClientID());
        } else if (factureType == FactureType.ACHAT) {
            FactureAchat achatFacture = (FactureAchat) facture;
            factureEvent.setFactureId(achatFacture.getBillID());
            factureEvent.setAmount(achatFacture.getMontantTotal());
            factureEvent.setFournisseurId(achatFacture.getFournisseur().getFournisseurID());
        }

        return factureEvent;
    }

    /**
     * Retrieve a vente facture by its ID.
     * @param factureID The ID of the vente facture.
     * @return The vente facture.
     */
    public Optional<FactureVente> getVenteFactureById(Long factureID) {
        return factureVenteRepository.findById(factureID);
    }

    /**
     * Retrieve an achat facture by its ID.
     * @param billID The ID of the achat facture.
     * @return The achat facture.
     */
    public Optional<FactureAchat> getAchatFactureById(Long billID) {
        return factureAchatRepository.findById(billID);
    }
}
