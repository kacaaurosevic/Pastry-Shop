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
public class StavkaPorudzbina implements ApstraktniDomenskiObjekat {

    private Long rb;
    private int osnovnaCena;
    private int kolicina;
    private double iznosSP;
    private Poslastica poslastica;
    private Porudzbina porudzbina;

    public StavkaPorudzbina() {
    }

    public StavkaPorudzbina(Long rb, int osnovnaCena, int kolicina, double iznosSP, Poslastica poslastica, Porudzbina porudzbina) {
        this.rb = rb;
        this.osnovnaCena = osnovnaCena;
        this.kolicina = kolicina;
        this.iznosSP = iznosSP;
        this.poslastica = poslastica;
        this.porudzbina = porudzbina;
    }

    public Long getRb() {
        return rb;
    }

    public void setRb(Long rb) {
        this.rb = rb;
    }

    public int getOsnovnaCena() {
        return osnovnaCena;
    }

    public void setOsnovnaCena(int osnovnaCena) {
        this.osnovnaCena = osnovnaCena;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public double getIznosSP() {
        return iznosSP;
    }

    public void setIznosSP(double iznosSP) {
        this.iznosSP = iznosSP;
    }

    public Poslastica getPoslastica() {
        return poslastica;
    }

    public void setPoslastica(Poslastica poslastica) {
        this.poslastica = poslastica;
    }

    public Porudzbina getPorudzbina() {
        return porudzbina;
    }

    public void setPorudzbina(Porudzbina porudzbina) {
        this.porudzbina = porudzbina;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final StavkaPorudzbina other = (StavkaPorudzbina) obj;
        if (this.osnovnaCena != other.osnovnaCena) {
            return false;
        }
        if (this.kolicina != other.kolicina) {
            return false;
        }
        if (Double.doubleToLongBits(this.iznosSP) != Double.doubleToLongBits(other.iznosSP)) {
            return false;
        }
        if (!Objects.equals(this.rb, other.rb)) {
            return false;
        }
        if (!Objects.equals(this.poslastica, other.poslastica)) {
            return false;
        }
        return Objects.equals(this.porudzbina, other.porudzbina);
    }

    @Override
    public String toString() {
        return "StavkaPorudzbina{" + "rb=" + rb + ", osnovnaCena=" + osnovnaCena + ", kolicina=" + kolicina + ", iznosSP=" + iznosSP + ", poslastica=" + poslastica + ", porudzbina=" + porudzbina + '}';
    }

    @Override
    public String vratiNazivTabele() {
        return "stavkaporudzbina";
    }

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()) {
            Long rb = rs.getLong("stavkaporudzbina.rb");
            int osnovnaCena = rs.getInt("stavkaporudzbina.osnovnaCena");
            int kolicina = rs.getInt("stavkaporudzbina.kolicina");
            double iznosSP = rs.getDouble("stavkaporudzbina.iznosSP");

            Long idPoslastice = rs.getLong("poslastica.idPoslastica");
            String nazivPoslastice = rs.getString("poslastica.naziv");
            String opisPoslastice = rs.getString("poslastica.opis");
            int cenaPoslastice = rs.getInt("poslastica.cenaP");
            Poslastica p = new Poslastica(idPoslastice, nazivPoslastice, opisPoslastice, cenaPoslastice);
            StavkaPorudzbina sp = new StavkaPorudzbina(rb, osnovnaCena, kolicina, iznosSP, p, null);
            lista.add(sp);
        }
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "rb,osnovnaCena,kolicina,iznosSP,poslastica,porudzbina";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return rb + "," + osnovnaCena + "," + kolicina + "," + iznosSP + ","
                + poslastica.getIdPoslastica() + "," + porudzbina.getIdPorudzbina();
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "stavkaporudzbina.rb=" + rb + " AND stavkaporudzbina.porudzbina=" + porudzbina.getIdPorudzbina();
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "osnovnaCena=" + osnovnaCena
                + ", kolicina=" + kolicina
                + ", iznosSP=" + iznosSP
                + ", poslastica=" + poslastica.getIdPoslastica();
    }
}
