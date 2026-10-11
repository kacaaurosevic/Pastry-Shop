/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.poslastice;

import java.util.List;
import model.Poslastica;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author katar
 */
public class UcitajPoslasticeSO extends ApstraktnaGenerickaOperacija {

    List<Poslastica> poslastice;

    @Override
    protected void preduslovi(Object objekat) throws Exception {
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        poslastice = broker.getAll(new Poslastica(), "");
    }

    public List<Poslastica> getPoslastice() {
        return poslastice;
    }

    public void setPoslastice(List<Poslastica> poslastice) {
        this.poslastice = poslastice;
    }
}
