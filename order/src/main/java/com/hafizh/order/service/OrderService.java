package com.hafizh.order.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hafizh.order.entity.*;
import com.hafizh.order.repository.OrderRepository;

@Service
public class OrderService {
    @Autowired private OrderRepository orderRepository;
    @Autowired private ClientService clientService;

    public Orders buatOrder(Orders o) {
        ProdukDto produk = validasi(o);
        if (o.getTglTrans() == null) {
            o.setTglTrans(LocalDate.now());
        }
        o.setTotal(produk.getHarga() * o.getJumlah());
        return isiVirtual(orderRepository.save(o));
    }

    public List<Orders> getAll() {
        List<Orders> list = orderRepository.findAll();
        list.forEach(this::isiVirtual);
        return list;
    }

    public Orders getById(Long id) {
        Orders o = orderRepository.findById(id).orElse(null);
        return o == null ? null : isiVirtual(o);
    }

    public List<Orders> getByPelanggan(Long id) {
        List<Orders> list = orderRepository.findByPelangganId(id);
        list.forEach(this::isiVirtual);
        return list;
    }

    public Orders updateOrder(Long id, Orders o) {
        Orders existing = orderRepository.findById(id).orElse(null);
        if (existing == null) {
            return null;
        }
        ProdukDto produk = validasi(o);
        existing.setProdukId(o.getProdukId());
        existing.setPelangganId(o.getPelangganId());
        existing.setJumlah(o.getJumlah());
        if (o.getTglTrans() != null) {
            existing.setTglTrans(o.getTglTrans());
        }
        existing.setTotal(produk.getHarga() * o.getJumlah());
        return isiVirtual(orderRepository.save(existing));
    }

    public boolean deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            return false;
        }
        orderRepository.deleteById(id);
        return true;
    }

    // Cek pelanggan & produk ke project lain, kembalikan data produk
    private ProdukDto validasi(Orders o) {
        PelangganDto pelanggan = clientService.getPelanggan(o.getPelangganId());
        if (pelanggan == null || pelanggan.getId() == null) {
            throw new IllegalArgumentException("Pelanggan tidak ditemukan");
        }
        ProdukDto produk = clientService.getProduk(o.getProdukId());
        if (produk == null || produk.getId() == null) {
            throw new IllegalArgumentException("Produk tidak ditemukan");
        }
        return produk;
    }

    // Isi virtual object (produk & pelanggan) dengan memanggil URL service masing-masing
    private Orders isiVirtual(Orders o) {
        o.setProduk(clientService.getProduk(o.getProdukId()));
        o.setPelanggan(clientService.getPelanggan(o.getPelangganId()));
        return o;
    }
}