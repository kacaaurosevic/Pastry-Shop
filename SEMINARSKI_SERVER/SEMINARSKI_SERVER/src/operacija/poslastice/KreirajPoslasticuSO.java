/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.poslastice;

import model.Poslastica;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author katar
 */
public class KreirajPoslasticuSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Poslastica)) {
            throw new Exception("Sistem ne može da doda poslasticu.");
        }
        Poslastica p = (Poslastica) objekat;
        if (p.getNaziv() == null || p.getNaziv().isEmpty()) {
            throw new Exception("Greska naziv poslastice!");
        } else if (p.getOpis() == null || p.getOpis().isEmpty()) {
            throw new Exception("Greska opis poslastice!");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        broker.add((Poslastica) objekat);
    }
}
