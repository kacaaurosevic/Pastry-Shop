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
public class Porudzbina implements ApstraktniDomenskiObjekat {

    private Long idPorudzbina;
    private double ukupanIznos;
    private boolean obradjena;//da li ju je poslasticar obradio
    private Poslasticar poslasticar;//koji poslasticar je odgovoran za tu porudzbinu
    private Kupac kupac;
    private List<StavkaPorudzbina> stavke;

    public Porudzbina() {
    }

    public Porudzbina(Long idPorudzbina, double ukupanIznos, boolean obradjena, Poslasticar poslasticar, Kupac kupac) {
        this.idPorudzbina = idPorudzbina;
        this.ukupanIznos = ukupanIznos;
        this.obradjena = obradjena;
        this.poslasticar = poslasticar;
        this.kupac = kupac;
    }

    public Long getIdPorudzbina() {
        return idPorudzbina;
    }

    public void setIdPorudzbina(Long idPorudzbina) {
        this.idPorudzbina = idPorudzbina;
    }

    public double getUkupanIznos() {
        return ukupanIznos;
    }

    public void setUkupanIznos(double ukupanIznos) {
        this.ukupanIznos = ukupanIznos;
    }

    public boolean isObradjena() {
        return obradjena;
    }

    public void setObradjena(boolean obradjena) {
        this.obradjena = obradjena;
    }

    public Poslasticar getPoslasticar() {
        return poslasticar;
    }

    public void setPoslasticar(Poslasticar poslasticar) {
        this.poslasticar = poslasticar;
    }

    public Kupac getKupac() {
        return kupac;
    }

    public void setKupac(Kupac kupac) {
        this.kupac = kupac;
    }

    public List<StavkaPorudzbina> getStavke() {
        return stavke;
    }

    public void setStavke(List<StavkaPorudzbina> stavke) {
        this.stavke = stavke;
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
        final Porudzbina other = (Porudzbina) obj;
        if (Double.doubleToLongBits(this.ukupanIznos) != Double.doubleToLongBits(other.ukupanIznos)) {
            return false;
        }
        if (this.obradjena != other.obradjena) {
            return false;
        }
        if (!Objects.equals(this.idPorudzbina, other.idPorudzbina)) {
            return false;
        }
        if (!Objects.equals(this.poslasticar, other.poslasticar)) {
            return false;
        }
        if (!Objects.equals(this.kupac, other.kupac)) {
            return false;
        }
        return Objects.equals(this.stavke, other.stavke);
    }

    @Override
    public String toString() {
        return "Porudzbina{" + "idPorudzbina=" + idPorudzbina + ", ukupanIznos=" + ukupanIznos + ", obradjena=" + obradjena + ", poslasticar=" + poslasticar + ", kupac=" + kupac + ", stavke=" + stavke + '}';
    }

    @Override
    public String vratiNazivTabele() {
        return "porudzbina";
    }

    ;

    @Override
    public List<ApstraktniDomenskiObjekat> vratiListu(ResultSet rs) throws Exception {
        List<ApstraktniDomenskiObjekat> lista = new ArrayList<>();
        while (rs.next()) {
            Long idP = rs.getLong("porudzbina.idPorudzbina");
            double ukupanIznos = rs.getDouble("porudzbina.ukupanIznos");
            boolean obradjena = rs.getBoolean("porudzbina.obradjena");

            Long idPoslasticar = rs.getLong("poslasticar.idPoslasticar");
            String imePoslasticara = rs.getString("poslasticar.imePrezime");
            String korisnickoImePoslasticara = rs.getString("poslasticar.korisnickoIme");
            Poslasticar poslasticar = new Poslasticar(idPoslasticar, imePoslasticara, null, korisnickoImePoslasticara, null);

            Long idKupac = rs.getLong("kupac.idKupac");
            String imeKupca = rs.getString("kupac.imePrezimeKupca");
            Mesto mestoKupca = null;
            String kontaktKupca = rs.getString("kupac.kontakt");
            Kupac kupac = new Kupac(idKupac, imeKupca, kontaktKupca, mestoKupca);

            Porudzbina p = new Porudzbina(idP, ukupanIznos, obradjena, poslasticar, kupac);
            lista.add(p);
        }
        return lista;
    }

    @Override
    public String vratiKoloneZaUbacivanje() {
        return "ukupanIznos,obradjena,poslasticar,kupac";
    }

    @Override
    public String vratiVrednostiZaUbacivanje() {
        return ukupanIznos + "," + obradjena + "," + poslasticar.getIdPoslasticar() + "," + kupac.getIdKupac();
    }

    @Override
    public String vratiPrimarniKljuc() {
        return "porudzbina.idPorudzbina=" + idPorudzbina;
    }

    @Override
    public ApstraktniDomenskiObjekat vratiObjekatIzRS(ResultSet rs) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String vratiVrednostiZaIzmenu() {
        return "ukupanIznos=" + ukupanIznos
                + ", obradjena=" + obradjena
                + ", poslasticar=" + poslasticar.getIdPoslasticar()
                + ", kupac=" + kupac.getIdKupac();
    }
}
