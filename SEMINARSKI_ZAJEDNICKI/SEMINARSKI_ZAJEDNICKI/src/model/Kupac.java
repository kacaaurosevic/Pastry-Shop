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
public class Kupac implements ApstraktniDomenskiObjekat {

    private Long idKupac;
    private String imePrezimeKupca;
    private String kontakt;
    private Mesto mesto;

    public Kupac() {
    }

    public Kupac(Long idKupac, String imePrezimeKupca, String kontakt, Mesto mesto) {
        this.idKupac = idKupac;
        this.imePrezimeKupca = imePrezimeKupca;
        this.kontakt = kontakt;
        this.mesto = mesto;
    }

    public Long getIdKupac() {
        return idKupac;
    }

    public void setIdKupac(Long idKupac) {
        this.idKupac = idKupac;
    }

    public String getImePrezimeKupca() {
        return imePrezimeKupca;
    }

    public void setImePrezimeKupca(String imePrezimeKupca) {
        this.imePrezimeKupca = imePrezimeKupca;
    }

    public String getKontakt() {
        return kontakt;
    }

    public void setKontakt(String kontakt) {
        this.kontakt = kontakt;
    }

    public Mesto getMesto() {
        return mesto;
    }

    public void setMesto(Mesto mesto) {
        this.mesto = mesto;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Kupac other = (Kupac) obj;
        if (!Objects.equals(this.imePrezimeKupca, other.imePrezimeKupca)) {
            return false;
        }
        if (!Objects.equals(this.kontakt, other.kontakt)) {
            return false;
        }
        if (!Objects.equals(this.idKupac, other.idKupac)) {
            return false;
        }
        return Objects.equals(this.mesto, other.mesto);
    }

    @Override
    public String toString() {
        return imePrezimeKupca;
    }

    @Override
    public String vratiNazivTabele() {
        return "kupac";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()) {
            Long id = rs.getLong("kupac.idKupac");
            String imePrezime = rs.getString("kupac.imePrezimeKupca");
            String kontakt = rs.getString("kupac.kontakt");
            Long idMesta = rs.getLong("mesto.idMesto");
            String nazivMesta = rs.getString("mesto.nazivMesta");
            Mesto mesto = new Mesto(idMesta, nazivMesta);

            Kupac k = new Kupac(id, imePrezime, kontakt, mesto);
            lista.add(k);
        }
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "imePrezimeKupca,kontakt,mesto";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "'" + imePrezimeKupca + "','" + kontakt + "','" + mesto.getIdMesto() + "'";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "kupac.idKupac=" + idKupac;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "imePrezimeKupca='" + imePrezimeKupca
                + "', kontakt='" + kontakt
                + "', mesto=" + mesto.getIdMesto();
    }
}
