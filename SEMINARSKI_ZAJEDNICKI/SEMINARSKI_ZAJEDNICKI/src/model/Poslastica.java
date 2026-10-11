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
public class Poslastica implements ApstraktniDomenskiObjekat {

    private Long idPoslastica;
    private String naziv;
    private String opis;
    private int cenaP;

    public Poslastica() {
    }

    public Poslastica(Long idPoslastica, String naziv, String opis, int cenaP) {
        this.idPoslastica = idPoslastica;
        this.naziv = naziv;
        this.opis = opis;
        this.cenaP = cenaP;
    }

    public Long getIdPoslastica() {
        return idPoslastica;
    }

    public void setIdPoslastica(Long idPoslastica) {
        this.idPoslastica = idPoslastica;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public int getCenaP() {
        return cenaP;
    }

    public void setCenaP(int cenaP) {
        this.cenaP = cenaP;
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
        final Poslastica other = (Poslastica) obj;
        if (this.cenaP != other.cenaP) {
            return false;
        }
        if (!Objects.equals(this.naziv, other.naziv)) {
            return false;
        }
        if (!Objects.equals(this.opis, other.opis)) {
            return false;
        }
        return Objects.equals(this.idPoslastica, other.idPoslastica);
    }

    @Override
    public String toString() {
        return naziv;
    }

    @Override
    public String vratiNazivTabele() {
        return "poslastica";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()) {
            Long sifraPoslastice = rs.getLong("poslastica.idPoslastica");
            String naziv = rs.getString("poslastica.naziv");
            String opis = rs.getString("poslastica.opis");
            int cenaP = rs.getInt("poslastica.cenaP");

            Poslastica p = new Poslastica(sifraPoslastice, naziv, opis, cenaP);
            lista.add(p);
        }
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "naziv,opis,cenaP";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return "'" + naziv + "','" + opis + "'," + cenaP;
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "poslastica.idPoslastica=" + idPoslastica;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "naziv='" + naziv
                + "', opis='" + opis
                + "', cenaP=" + cenaP;
    }
}
