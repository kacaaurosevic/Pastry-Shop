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
public class PretraziPorudzbinuSO extends ApstraktnaGenerickaOperacija {

    private List<Porudzbina> porudzbine;

    @Override
    protected void preduslovi(Object objekat) throws Exception {
        if (!(objekat instanceof Porudzbina)) {
            throw new Exception("Greška, prosleđeni parametar mora biti objekat klase Porudzbina.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object objekat, String kljuc) throws Exception {
        Porudzbina porudzbinaZaPretragu = (Porudzbina) objekat;

        StringBuilder whereKlauzula = new StringBuilder();

        // Uvek dodajem JOIN-ove, jer uvek ucitavam kupca i poslasticara
        whereKlauzula.append(" JOIN kupac ON porudzbina.kupac = kupac.idKupac JOIN poslasticar ON porudzbina.poslasticar = poslasticar.idPoslasticar");

        // Dodajem WHERE klauzulu, počinjem sa 1=1 da bih lakše dodavala uslove sa AND
        whereKlauzula.append(" WHERE 1=1");

        // Dodajem uslove na osnovu popunjenih polja
        if (porudzbinaZaPretragu.getIdPorudzbina() != 0) {
            whereKlauzula.append(" AND porudzbina.idPorudzbina = ").append(porudzbinaZaPretragu.getIdPorudzbina());
        }

        if (porudzbinaZaPretragu.getKupac() != null) {
            whereKlauzula.append(" AND porudzbina.kupac = ").append(porudzbinaZaPretragu.getKupac().getIdKupac());
        }

        if (porudzbinaZaPretragu.getPoslasticar() != null) {
            whereKlauzula.append(" AND porudzbina.poslasticar = ").append(porudzbinaZaPretragu.getPoslasticar().getIdPoslasticar());
        }

        whereKlauzula.append(" AND porudzbina.obradjena = ").append(porudzbinaZaPretragu.isObradjena() ? 1 : 0);

        try {
            porudzbine = broker.getAll(new Porudzbina(), whereKlauzula.toString());
        } catch (Exception e) {
            System.out.println("Greska u PretraziPorudzbinuSO: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public List<Porudzbina> getPorudzbine() {
        return porudzbine;
    }

    public void setPorudzbine(List<Porudzbina> porudzbine) {
        this.porudzbine = porudzbine;
    }
}
