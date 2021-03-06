package com.hisamoto.leandro.mapaandroid.tracker;

/**
 * Created by leandro on 24/08/15.
 */
public class Rota {
    private String origem;
    private String destino;
    private String descricao;
    private String id;

    public Rota(String origem, String destino, String descricao, String id) {
        this.origem = origem;
        this.destino = destino;
        this.descricao = descricao;
        this.id = id;
    }

    public Rota() {

    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
