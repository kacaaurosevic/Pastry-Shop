/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.porudzbina;

import java.util.List;
import model.Porudzbina;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author katar
 */
public class UcitajPorudzbinuSO extends ApstraktnaGenerickaOperacija {

    private List<Porudzbina> listaPorudzbina;

    @Override
    protected void preduslovi(Object param) throws Exception {
        // Nema preduslova za ovu operaciju
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {

        String uslov = " JOIN kupac ON porudzbina.kupac = kupac.idKupac JOIN poslasticar ON porudzbina.poslasticar = poslasticar.idPoslasticar";

        try {
            listaPorudzbina = broker.getAll(new Porudzbina(), uslov);
        } catch (Exception e) {
            System.out.println("UcitajKupceSO: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Porudzbina> getListaPorudzbina() {
        return listaPorudzbina;
    }
}
