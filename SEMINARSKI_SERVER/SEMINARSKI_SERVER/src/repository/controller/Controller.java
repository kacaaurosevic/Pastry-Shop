/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.controller;

import java.util.List;
import model.Kupac;
import model.Mesto;
import model.Porudzbina;
import model.Poslastica;
import model.Poslasticar;
import model.Smena;
import model.StavkaPorudzbina;
import operacija.LoginOperacija;
import operacija.kupci.IzmeniKupcaSO;
import operacija.kupci.UbaciKupcaSO;
import operacija.kupci.ObrisiKupcaSO;
import operacija.kupci.UcitajKupceSO;
import operacija.mesto.KreirajMestoSO;
import operacija.mesto.UcitajMestoSO;
import operacija.porudzbina.IzmeniPorudzbinuSO;
import operacija.porudzbina.UbaciPorudzbinuSO;
import operacija.porudzbina.PretraziPorudzbinuSO;
import operacija.porudzbina.UcitajPorudzbinuSO;
import operacija.poslasticar.IzmeniPoslasticarSO;
import operacija.poslasticar.UcitajPoslasticarSO;
import operacija.poslastice.IzmeniPoslasticuSO;
import operacija.poslastice.KreirajPoslasticuSO;
import operacija.poslastice.ObrisiPoslasticuSO;
import operacija.poslastice.UcitajPoslasticeSO;
import operacija.smene.UbaciSmeneSO;
import operacija.smene.UcitajSmeneSO;
import operacija.stavkaPorudzbine.UcitajStavkaPorudzbineSO;
import repository.db.impl.DbRepositoryGeneric;

/**
 *
 * @author katar
 */
public class Controller {//singleton

    private DbRepositoryGeneric drg = new DbRepositoryGeneric();//povezujem kontroler i repozitorijum

    private static Controller instance;

    private Controller() {
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public Poslasticar login(Poslasticar p) throws Exception {
        LoginOperacija operacija = new LoginOperacija();
        operacija.izvrsi(p, null);
        System.out.println("Klasa controller: " + operacija.getPoslasticar());
        return operacija.getPoslasticar();
    }

    public List<Poslastica> ucitajPoslasticu() throws Exception {
        UcitajPoslasticeSO operacija = new UcitajPoslasticeSO();
        operacija.izvrsi(null, null);
        return operacija.getPoslastice();
    }

    public void obrisiPoslasticu(Poslastica p) throws Exception {
        ObrisiPoslasticuSO operacija = new ObrisiPoslasticuSO();
        operacija.izvrsi(p, null);
    }

    public void dodajPoslasticu(Poslastica novaPoslastica) throws Exception {
        KreirajPoslasticuSO operacija = new KreirajPoslasticuSO();
        operacija.izvrsi(novaPoslastica, null);
    }

    public void izmeniPoslasticu(Poslastica poslasticaZaIzmenu) throws Exception {
        IzmeniPoslasticuSO operacija = new IzmeniPoslasticuSO();
        operacija.izvrsi(poslasticaZaIzmenu, null);
    }

    public List<Kupac> ucitajKupce() throws Exception {
        UcitajKupceSO operacija = new UcitajKupceSO();
        operacija.izvrsi(null, null);
        return operacija.getKupci();
    }

    public void obrisiKupca(Kupac ku) throws Exception {
        ObrisiKupcaSO operacija = new ObrisiKupcaSO();
        operacija.izvrsi(ku, null);
    }

    public List<Mesto> ucitajMesta() throws Exception {
        UcitajMestoSO operacija = new UcitajMestoSO();
        operacija.izvrsi(null, null);
        return operacija.getMesta();
    }

    public void dodajKupca(Kupac noviKupac) throws Exception {
        UbaciKupcaSO operacija = new UbaciKupcaSO();
        operacija.izvrsi(noviKupac, null);
    }

    public void dodajMesto(Mesto novoMesto) throws Exception {
        KreirajMestoSO operacija = new KreirajMestoSO();
        operacija.izvrsi(novoMesto, null);
    }

    public void izmeniKupac(Kupac kupacZaIzmenu) throws Exception {
        IzmeniKupcaSO operacija = new IzmeniKupcaSO();
        operacija.izvrsi(kupacZaIzmenu, null);
    }

    public List<Smena> ucitajSmene() throws Exception {
        UcitajSmeneSO operacija = new UcitajSmeneSO();
        operacija.izvrsi(null, null);
        return operacija.getSmene();
    }

    public void dodajSmena(Smena novaSmena) throws Exception {
        UbaciSmeneSO operacija = new UbaciSmeneSO();
        operacija.izvrsi(novaSmena, null);
    }

    public List<Porudzbina> ucitajPorudzbine() throws Exception {
        UcitajPorudzbinuSO operacija = new UcitajPorudzbinuSO();
        operacija.izvrsi(null, null);
        return operacija.getListaPorudzbina();
    }

    public List<StavkaPorudzbina> ucitajStavkePorudzbina(Porudzbina porudzbina) throws Exception {
        UcitajStavkaPorudzbineSO operacija = new UcitajStavkaPorudzbineSO();
        operacija.izvrsi(porudzbina, null);
        return operacija.getListaStavki();
    }

    public List<Poslasticar> ucitajPoslasticare() throws Exception {
        UcitajPoslasticarSO operacija = new UcitajPoslasticarSO();
        operacija.izvrsi(null, null);
        return operacija.getPoslasticari();
    }

    public void dodajPorudzbinu(Porudzbina novaPorudzbina) throws Exception {
        UbaciPorudzbinuSO operacija = new UbaciPorudzbinuSO();
        operacija.izvrsi(novaPorudzbina, null);
    }

    public List<Porudzbina> pretraziPorudzbine(Porudzbina porudzbinaZaPretragu) throws Exception {
        PretraziPorudzbinuSO operacija = new PretraziPorudzbinuSO();
        operacija.izvrsi(porudzbinaZaPretragu, null);
        return operacija.getPorudzbine();
    }

    public void izmeniPorudzbinu(Porudzbina porudzbinaZaIzmenu) throws Exception {
        IzmeniPorudzbinuSO operacija = new IzmeniPorudzbinuSO();
        operacija.izvrsi(porudzbinaZaIzmenu, null);
    }

    public void izmeniPoslasticara(Poslasticar poslasticarZaIzmenu) throws Exception {
        IzmeniPoslasticarSO operacija = new IzmeniPoslasticarSO();
        operacija.izvrsi(poslasticarZaIzmenu, null);
    }
}
