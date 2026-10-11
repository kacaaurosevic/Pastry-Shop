/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.kupci;

import model.Kupac;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author HP
 */
public class UbaciKupcaSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Kupac)) {
            throw new Exception("Sistem ne može da doda kupca.");
        }
        Kupac k = (Kupac) objekat;
        if (k.getImePrezimeKupca() == null || k.getImePrezimeKupca().isEmpty()) {
            throw new Exception("Greska za ime i prezime kupca!");
        } else if (k.getMesto() == null) {
            throw new Exception("Greska za mesto kupca!");
        } else if (k.getKontakt() == null || k.getKontakt().isEmpty()) {
            throw new Exception("Greska za kontakt kupca!");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        broker.add((Kupac) objekat);
    }
}
