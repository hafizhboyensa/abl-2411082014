package com.hafizh.order.entity;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long produkId;
    private Long pelangganId;
    private LocalDate tglTrans;
    private int jumlah;
    private double total;

    // Virtual object: tidak disimpan ke tabel orders, hanya diisi dari service lain
    @Transient
    private ProdukDto produk;
    @Transient
    private PelangganDto pelanggan;

    public Orders() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProdukId() { return produkId; }
    public void setProdukId(Long produkId) { this.produkId = produkId; }
    public Long getPelangganId() { return pelangganId; }
    public void setPelangganId(Long pelangganId) { this.pelangganId = pelangganId; }
    public LocalDate getTglTrans() { return tglTrans; }
    public void setTglTrans(LocalDate tglTrans) { this.tglTrans = tglTrans; }
    public int getJumlah() { return jumlah; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public ProdukDto getProduk() { return produk; }
    public void setProduk(ProdukDto produk) { this.produk = produk; }
    public PelangganDto getPelanggan() { return pelanggan; }
    public void setPelanggan(PelangganDto pelanggan) { this.pelanggan = pelanggan; }
}