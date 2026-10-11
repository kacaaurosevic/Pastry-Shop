/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import domen.ApstraktniDomenskiObjekat;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author katar
 */
public class Mesto implements ApstraktniDomenskiObjekat {

    private Long idMesto;
    private String nazivMesta;

    public Mesto() {
    }

    public Mesto(Long idMesto, String nazivMesta) {
        this.idMesto = idMesto;
        this.nazivMesta = nazivMesta;
    }

    public Long getIdMesto() {
        return idMesto;
    }

    public void setIdMesto(Long idMesto) {
        this.idMesto = idMesto;
    }

    public String getNazivMesta() {
        return nazivMesta;
    }

    public void setNazivMesta(String nazivMesta) {
        this.nazivMesta = nazivMesta;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Mesto other = (Mesto) obj;
        if (!Objects.equals(this.nazivMesta, other.nazivMesta)) {
            return false;
        }
        return Objects.equals(this.idMesto, other.idMesto);
    }

    @Override
    public String toString() {
        return nazivMesta;
    }

    @Override
    public String vratiNazivTabele() {
        return "mesto";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()) {
            Long id = rs.getLong("mesto.idMesto");
            String naziv = rs.getString("mesto.nazivMesta");
            Mesto m = new Mesto(id, naziv);
            lista.add(m);
        }
        return lista;    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "nazivMesta";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "'" + nazivMesta + "'";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "mesto.idMesto=" + idMesto;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "nazivMesta='" + nazivMesta + "'";
    }
}
