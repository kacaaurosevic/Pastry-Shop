/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cordinator;

import forme.DodajPoslasticaForma;
import forme.FormaMod;
import forme.*;
import forme.PrikazPoslasticaForma;
import forme.PrikazKupacaForma;
import java.util.HashMap;
import java.util.Map;
import kontroleri.*;
import model.Poslasticar;
import model.Porudzbina;
import forme.IzmenaPoslasticaraForma;

/**
 *
 * @author HP
 */
public class Cordinator {

    private static Cordinator instance;
    private Poslasticar ulogovani;
    private LoginKontroler loginController;
    private GlavnaFormaKontroler glavnaFormaKontroler;
    private PrikazKupacaKontroler prikazKupacaKontroler;
    private PrikazPoslasticaKontroler prikazPoslasticaController;
    private DodajPoslasticuKontroler dkController;
    private DodajKupcaKontroler dkuController;
    private DodajMestoKontroler dMController;
    private PrikazSmenaKontroler psController;
    private DodajSmenuKontroler dsController;
    private PrikazPorudzbinaKontroler prController;
    private PrikazStavkiPorudzbineKontroler psrController;
    private DodajPorudzbinuKontroler drController;
    private IzmenaPoslasticaraKontroler ipController;
    private Map<String, Object> parametri;

    private PrikazKupacaKontroler prikazKupacaController;

    private Cordinator() {
        parametri = new HashMap<>();
    }

    public static Cordinator getInstance() {
        if (instance == null) {
            instance = new Cordinator();
        }
        return instance;
    }

    public Poslasticar getUlogovani() {
        return ulogovani;
    }

    public void setUlogovani(Poslasticar ulogovani) {
        this.ulogovani = ulogovani;
    }

    public void dodajParam(String s, Object o) {
        parametri.put(s, o);
    }

    public Object vratiParam(String s) {
        return parametri.get(s);
    }

    public void otvoriLogInFormu() {
        loginController = new LoginKontroler(new LoginForma());
        loginController.otvoriFormu();
    }

    public void otvoriGlavnuFormu() {
        glavnaFormaKontroler = new GlavnaFormaKontroler(new GlavnaForma());
        glavnaFormaKontroler.otvoriFormu();
    }

    public void otvoriDodajPoslasticaFormu() {
        dkController = new DodajPoslasticuKontroler(new DodajPoslasticaForma());
        dkController.otvoriFormu(FormaMod.DODAJ);
    }

    public void otvoriPrikazPoslasticaFormu() {
        prikazPoslasticaController = new PrikazPoslasticaKontroler(new PrikazPoslasticaForma());
        prikazPoslasticaController.otvoriFormu();
    }

    public void otvoriIzmeniPoslasticFormu() {
        dkController = new DodajPoslasticuKontroler(new DodajPoslasticaForma());
        dkController.otvoriFormu(FormaMod.IZMENI);
    }

    public void osveziFormu() {
        prikazPoslasticaController.osveziFormu();
    }

    public void otvoriPrikazKupacaFormu() {
        prikazKupacaController = new PrikazKupacaKontroler(new PrikazKupacaForma());
        prikazKupacaController.otvoriFormu();
    }

    public void otvoriDodajKupcaFormu() {
        dkuController = new DodajKupcaKontroler(new DodajKupcaForma());
        dkuController.otvoriFormu(FormaMod.DODAJ);
    }

    public void otvoriDodajMestoFormu() {
        dMController = new DodajMestoKontroler(new DodajMestoForma(), dkuController);
        dMController.otvoriFormu();
    }

    public void otvoriIzmeniKupacFormu() {
        dkuController = new DodajKupcaKontroler(new DodajKupcaForma());
        dkuController.otvoriFormu(FormaMod.IZMENI);
    }

    public void osveziFormuKupac() {
        prikazKupacaController.osveziFormu();
    }

    public void otvoriPrikaziSmeneFormu() {
        psController = new PrikazSmenaKontroler(new PrikazSmenaForma());
        psController.otvoriFormu();
    }

    public void otvoriDodajSmenaFormu() {
        dsController = new DodajSmenuKontroler(new DodajSmenuForma());
        dsController.otvoriFormu();
    }

    public void otvoriPrikazPorudzbinaFormu() {
        prController = new PrikazPorudzbinaKontroler(new PrikazPorudzbinaForma());
        prController.otvoriFormu();
    }

    public void otvoriPrikazStavkiRFormu(Porudzbina odabranaPorudzbina) {
        psrController = new PrikazStavkiPorudzbineKontroler(new PrikazStavkiPorudzbineForma(), odabranaPorudzbina);
        psrController.otvoriFormu();
    }

    public void otvoriDodajPorudzbinaFormu() throws Exception {
        drController = new DodajPorudzbinuKontroler(new DodajPorudzbinaForma(), FormaMod.DODAJ, null);
        drController.otvoriFormu();
    }

    public void otvoriIzmeniPorudzbinaFormu(Porudzbina odabraniPorudzbina) throws Exception {
        drController = new DodajPorudzbinuKontroler(new DodajPorudzbinaForma(), FormaMod.IZMENI, odabraniPorudzbina);
        drController.otvoriFormu();
    }

    public void vratiSeNaGlavnuFormu() {
        if (prController != null) {
            prController.osveziTabelu();
        }
    }

    public void otvoriIzmeniPoslasticaraFormu(Poslasticar poslasticarZaIzmenu) {
        IzmenaPoslasticaraForma ipf = new IzmenaPoslasticaraForma();
        ipController = new IzmenaPoslasticaraKontroler(ipf, poslasticarZaIzmenu);
        ipController.otvoriFormu();
    }

    public void osveziPrikazPoslasticara() {
        if (glavnaFormaKontroler != null) {
            glavnaFormaKontroler.osveziPrikazUlogovanogPoslasticara();
        }
    }
}
