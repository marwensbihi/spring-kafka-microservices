package com.angMetal.payment.service;

import com.angMetal.orders.entity.FactureAchat;
import com.angMetal.orders.entity.FactureVente;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FactureService {

    private final RestTemplate restTemplate;

    public FactureService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public FactureVente getFactureVente(Long factureVenteId) {
        String url = "http://ms-order/api/facture-vente/" + factureVenteId;
        return restTemplate.getForObject(url, FactureVente.class);
    }

    public FactureAchat getFactureAchat(Long factureAchatId) {
        String url = "http://ms-order/api/facture-achat/" + factureAchatId;
        return restTemplate.getForObject(url, FactureAchat.class);
    }
}
