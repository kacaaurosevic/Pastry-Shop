/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacija.stavkaPorudzbine;

import java.util.List;
import model.Porudzbina;
import model.StavkaPorudzbina;
import operacija.ApstraktnaGenerickaOperacija;

/**
 *
 * @author katar
 */
public class UcitajStavkaPorudzbineSO extends ApstraktnaGenerickaOperacija {

    private List<StavkaPorudzbina> listaStavki;

    @Override
    protected void preduslovi(Object param) throws Exception {
        if (!(param instanceof Porudzbina)) {
            throw new Exception("Nije poslat ispravan objekat za pretragu stavki porudzbine.");
        }
    }

    @Override
    protected void izvrsiOperaciju(Object param, String kljuc) throws Exception {
        Porudzbina porudzbina = (Porudzbina) param;
        String uslov = " JOIN poslastica ON stavkaporudzbina.poslastica = poslastica.idPoslastica WHERE stavkaporudzbina.porudzbina = " + porudzbina.getIdPorudzbina();
        this.listaStavki = broker.getAll(new StavkaPorudzbina(), uslov);
    }

    public List<StavkaPorudzbina> getListaStavki() {
        return listaStavki;
    }
}
