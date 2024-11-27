package com.sistemareserva.service_payment.domain.entity;

public class OrderEntity {
    
    private String idOrder;
    private String linkOrder;
    private Long idReserva;
    private Long idQuarto;
    private Long idHospede;
    private String quantidadeDias;
    private String UnidadeDiaria;
    private String valorTotal;

    public OrderEntity(Long idReserva, Long idQuarto, Long idHospede, String quantidadeDias, String UnidadeDiaria, String valorTotal) {
        this.idOrder = null;
        this.linkOrder = null;
        this.idReserva = idReserva;
        this.idHospede = idHospede;
        this.quantidadeDias = quantidadeDias;
        this.UnidadeDiaria = UnidadeDiaria;
        this.valorTotal = valorTotal;
    }

    

    public String getIdOrder() {
        return idOrder;
    }



    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }



    public String getLinkOrder() {
        return linkOrder;
    }



    public void setLinkOrder(String linkOrder) {
        this.linkOrder = linkOrder;
    }



    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }
    public Long getIdQuarto() {
        return idQuarto;
    }

    public void setIdQuarto(Long idQuarto) {
        this.idQuarto = idQuarto;
    }

    public Long getIdHospede() {
        return idHospede;
    }

    public void setIdHospede(Long idHospede) {
        this.idHospede = idHospede;
    }

    public String getQuantidadeDias() {
        return quantidadeDias;
    }

    public void setQuantidadeDias(String quantidadeDias) {
        this.quantidadeDias = quantidadeDias;
    }

    public String getUnidadeDiaria() {
        return UnidadeDiaria;
    }

    public void setUnidadeDiaria(String unidadeDiaria) {
        UnidadeDiaria = unidadeDiaria;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }
}
