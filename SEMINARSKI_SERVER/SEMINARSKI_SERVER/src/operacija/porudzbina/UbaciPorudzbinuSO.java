/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.porudzbina;

import model.Porudzbina;
import model.StavkaPorudzbina;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author katar
 */
public class UbaciPorudzbinuSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Porudzbina)) {
            throw new Exception("Poslati objekat nije tipa Racun!");
        }

        Porudzbina racun = (Porudzbina) objekat;
        if (racun.getStavke() == null || racun.getStavke().isEmpty()) {
            throw new Exception("Račun mora da sadrži barem jednu stavku.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        Porudzbina porudzbina = (Porudzbina) objekat;

        // 1. Sačuvaj glavni objekat Racun i dobij automatski generisani ID
        long idP = broker.addReturnKey(porudzbina);
        porudzbina.setIdPorudzbina(idP);

        System.out.println("Novi ID računa je: " + porudzbina.getIdPorudzbina());

        // 2. Postavi ID računa na svaku stavku i sačuvaj je
        for (StavkaPorudzbina stavka : porudzbina.getStavke()) {
            stavka.setPorudzbina(porudzbina);
            broker.add(stavka);
        }
    }
}
