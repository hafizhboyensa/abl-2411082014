package com.hafizh.order.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.hafizh.order.entity.PelangganDto;
import com.hafizh.order.entity.ProdukDto;

@Service
public class ClientService {
    private final RestClient produkClient =
            RestClient.create("http://localhost:8081/api/produk");
    private final RestClient pelangganClient =
            RestClient.create("http://localhost:8082/api/pelanggan");

    public ProdukDto getProduk(Long id) {
        try {
            return produkClient.get().uri("/{id}", id)
                    .retrieve().body(ProdukDto.class);
        } catch (Exception e) {
            return null;
        }
    }

    public PelangganDto getPelanggan(Long id) {
        try {
            return pelangganClient.get().uri("/{id}", id)
                    .retrieve().body(PelangganDto.class);
        } catch (Exception e) {
            return null;
        }
    }
}