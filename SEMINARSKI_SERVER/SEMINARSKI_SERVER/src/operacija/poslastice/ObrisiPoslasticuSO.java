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
public class ObrisiPoslasticuSO extends ApstraktnaGenerickaOperacija {

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (objekat == null || !(objekat instanceof Poslastica)) {
            throw new Exception("Sistem ne može da obriše poslasticu.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        broker.delete((Poslastica) objekat);
    }
}
