/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import domen.ApstraktniDomenskiObjekat;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author katar
 */
public class Smena implements ApstraktniDomenskiObjekat {

    private Long idSmena;
    private String nazivSmene;
    private LocalTime pocetakSmene;
    private LocalTime krajSmene;

    public Smena() {
    }

    public Smena(Long idSmena, String nazivSmene, LocalTime pocetakSmene, LocalTime krajSmene) {
        this.idSmena = idSmena;
        this.nazivSmene = nazivSmene;
        this.pocetakSmene = pocetakSmene;
        this.krajSmene = krajSmene;
    }

    public Long getIdSmena() {
        return idSmena;
    }

    public void setIdSmena(Long idSmena) {
        this.idSmena = idSmena;
    }

    public String getNazivSmene() {
        return nazivSmene;
    }

    public void setNazivSmene(String nazivSmene) {
        this.nazivSmene = nazivSmene;
    }

    public LocalTime getPocetakSmene() {
        return pocetakSmene;
    }

    public void setPocetakSmene(LocalTime pocetakSmene) {
        this.pocetakSmene = pocetakSmene;
    }

    public LocalTime getKrajSmene() {
        return krajSmene;
    }

    public void setKrajSmene(LocalTime krajSmene) {
        this.krajSmene = krajSmene;
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
        final Smena other = (Smena) obj;
        if (!Objects.equals(this.nazivSmene, other.nazivSmene)) {
            return false;
        }
        if (!Objects.equals(this.idSmena, other.idSmena)) {
            return false;
        }
        if (!Objects.equals(this.pocetakSmene, other.pocetakSmene)) {
            return false;
        }
        return Objects.equals(this.krajSmene, other.krajSmene);
    }

    @Override
    public String toString() {
        return nazivSmene;
    }

    @Override
    public String vratiNazivTabele() {
        return "smena";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()) {
            Long idSmena = rs.getLong("smena.idSmena");
            String naziv = rs.getString("smena.nazivSmene");
            Time tPocetak = rs.getTime("smena.pocetakSmene");
            Time tKraj = rs.getTime("smena.krajSmene");
            LocalTime pocetak = (tPocetak != null) ? tPocetak.toLocalTime() : null;
            LocalTime kraj = (tKraj != null) ? tKraj.toLocalTime() : null;

            Smena s = new Smena(idSmena, naziv, pocetak, kraj);
            lista.add(s);
        }
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "nazivSmene,pocetakSmene,krajSmene";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "'" + nazivSmene + "','" + pocetakSmene.toString() + "','" + krajSmene.toString() + "'";
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "smena.idSmena=" + idSmena;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "nazivSmene='" + nazivSmene
                + "', pocetakSmene='" + pocetakSmene.toString()
                + "', krajSmene='" + krajSmene.toString() + "'";
    }
}
