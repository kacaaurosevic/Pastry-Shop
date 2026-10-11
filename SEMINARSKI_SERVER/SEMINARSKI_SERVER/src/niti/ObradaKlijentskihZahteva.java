/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;

import repository.controller.Controller;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import komunikacija.Odgovor;
import komunikacija.Posiljalac;
import komunikacija.Primalac;
import komunikacija.Zahtev;
import model.Kupac;
import model.Mesto;
import model.Porudzbina;
import model.Poslastica;
import model.Poslasticar;
import model.Smena;
import model.StavkaPorudzbina;

/**
 *
 * @author katar
 */
public class ObradaKlijentskihZahteva extends Thread {

    Socket s;
    Primalac primalac;
    Posiljalac posiljalac;
    boolean kraj = false;

    public ObradaKlijentskihZahteva(Socket s) {
        this.s = s;
        primalac = new Primalac(s);
        posiljalac = new Posiljalac(s);
    }

    @Override
    public void run() {
        try {
            while (!kraj) {
                Zahtev zahtev = (Zahtev) primalac.primiPoruku();
                Odgovor odgovor = new Odgovor();

                switch (zahtev.getOperacija()) {
                    case LOGIN:
                        try {
                        Poslasticar p = (Poslasticar) zahtev.getParametar();
                        p = Controller.getInstance().login(p);
                        odgovor.setOdgovor(p);
                    } catch (Exception ex) {
                        odgovor.setOdgovor(ex);
                    }
                    break;
                    case UCITAJ_POSLASTICE:
                        try {
                        List<Poslastica> Poslasticai = Controller.getInstance().ucitajPoslasticu();
                        odgovor.setOdgovor(Poslasticai);
                    } catch (Exception ex) {
                        odgovor.setOdgovor(ex);
                    }
                    break;
                    case OBRISI_POSLASTICU:
                        try {
                        Poslastica p = (Poslastica) zahtev.getParametar();
                        Controller.getInstance().obrisiPoslasticu(p);
                        odgovor.setOdgovor(null);
                    } catch (Exception e) {
                        odgovor.setOdgovor(e);
                    }
                    break;
                    case DODAJ_POSLASTICU:
                        try {
                        Poslastica noviPoslastica = (Poslastica) zahtev.getParametar();
                        Controller.getInstance().dodajPoslasticu(noviPoslastica);
                        odgovor.setOdgovor(null);
                    } catch (Exception ex) {
                        odgovor.setOdgovor(ex);
                    }
                    break;
                    case IZMENI_POSLASTICU:
                        try {
                        Poslastica PoslasticaZaIzmenu = (Poslastica) zahtev.getParametar();
                        Controller.getInstance().izmeniPoslasticu(PoslasticaZaIzmenu);
                        odgovor.setOdgovor(null);
                    } catch (Exception ex) {
                        odgovor.setOdgovor(ex);
                    }
                    break;
                    case UCITAJ_KUPCE:
                        try {
                        List<Kupac> kupci = Controller.getInstance().ucitajKupce();
                        odgovor.setOdgovor(kupci);
                    } catch (Exception ex) {
                        odgovor.setOdgovor(ex);
                    }
                    break;
                    case OBRISI_KUPCA:
                        try {
                        Kupac ku = (Kupac) zahtev.getParametar();
                        Controller.getInstance().obrisiKupca(ku);
                        odgovor.setOdgovor(null);
                    } catch (Exception e) {
                        odgovor.setOdgovor(e);
                    }
                    break;
                    case UCITAJ_MESTA:
                        try {
                        List<Mesto> mesta = Controller.getInstance().ucitajMesta();
                        odgovor.setOdgovor(mesta);
                    } catch (Exception ex) {
                        odgovor.setOdgovor(ex);
                    }
                    break;
                    case DODAJ_KUPCA:
                        try {
                        Kupac noviKupac = (Kupac) zahtev.getParametar();
                        Controller.getInstance().dodajKupca(noviKupac);
                        System.out.println(noviKupac);
                        odgovor.setOdgovor(null);
                    } catch (Exception ex) {
                        odgovor.setOdgovor(ex);
                        System.out.println("Greska, klasa ObradaKZ: " + ex.getMessage());
                    }
                    break;
                    case DODAJ_MESTO:
                    try {
                        Mesto novoMesto = (Mesto) zahtev.getParametar();
                        Controller.getInstance().dodajMesto(novoMesto);
                        odgovor.setOdgovor(null);
                    } catch (Exception ex) {
                        odgovor.setOdgovor(ex);
                        System.out.println("Greska, klasa ObradaKZ: " + ex.getMessage());
                    }
                    break;
                    case IZMENI_KUPCA:
                    try {
                        Kupac kupacZaIzmenu = (Kupac) zahtev.getParametar();
                        Controller.getInstance().izmeniKupac(kupacZaIzmenu);
                        odgovor.setOdgovor(null); // USPEH! Postavljamo null kao "dobar" odgovor
                    } catch (Exception ex) {
                        odgovor.setOdgovor(ex);
                        System.out.println("Greska, klasa ObradaKZ: " + ex.getMessage());
                    }
                    break;
                    case UCITAJ_SMENE:
                        try {
                        List<Smena> smene = Controller.getInstance().ucitajSmene();
                        odgovor.setOdgovor(smene);
                    } catch (Exception ex) {
                        odgovor.setOdgovor(ex);
                    }
                    break;
                    case DODAJ_SMENU:
                        try {
                        Smena novaSmena = (Smena) zahtev.getParametar();
                        Controller.getInstance().dodajSmena(novaSmena);
                        odgovor.setOdgovor(null);
                    } catch (Exception ex) {
                        odgovor.setOdgovor(ex);
                        System.out.println("Greska, klasa ObradaKZ: " + ex.getMessage());
                    }
                    break;
                    case UCITAJ_PORUDZBINE:
                    try {
                        List<Porudzbina> Porudzbinai = Controller.getInstance().ucitajPorudzbine();
                        odgovor.setOdgovor(Porudzbinai);
                    } catch (Exception ex) {
                        odgovor.setOdgovor(ex);
                    }
                    break;
                    case UCITAJ_STAVKE_PORUDZBINA:
                    try {
                        List<StavkaPorudzbina> stavke = Controller.getInstance().ucitajStavkePorudzbina((Porudzbina) zahtev.getParametar());
                        odgovor.setOdgovor(stavke);
                    } catch (Exception ex) {
                        odgovor.setOdgovor(ex);
                    }
                    break;
                    case UCITAJ_POSLASTICARE:
                    try {
                        List<Poslasticar> poslasticari = Controller.getInstance().ucitajPoslasticare();
                        odgovor.setOdgovor(poslasticari);
                    } catch (Exception ex) {
                        odgovor.setOdgovor(ex);
                    }
                    break;
                    case KREIRAJ_PORUDZBINU: 
                        try {
                        Porudzbina novaPorudzbina = (Porudzbina) zahtev.getParametar();
                        Controller.getInstance().dodajPorudzbinu(novaPorudzbina);
                        odgovor.setOdgovor(null);
                    } catch (Exception ex) {
                        odgovor.setOdgovor(ex);
                        System.out.println("Greska, klasa ObradaKlijentskihZahteva: " + ex.getMessage());
                    }
                    break;
                    case PRETRAZI_PORUDZBINE:
                    try {
                        Porudzbina PorudzbinaZaPretragu = (Porudzbina) zahtev.getParametar();
                        List<Porudzbina> filtriraniPorudzbinai = Controller.getInstance().pretraziPorudzbine(PorudzbinaZaPretragu);
                        odgovor.setOdgovor(filtriraniPorudzbinai);
                    } catch (Exception ex) {
                        odgovor.setOdgovor(ex);
                        System.out.println("Greska, klasa ObradaKlijentskihZahteva: " + ex.getMessage());
                    }
                    break;
                    case IZMENI_PORUDZBINU:
                    try {
                        Porudzbina PorudzbinaZaIzmenu = (Porudzbina) zahtev.getParametar();
                        Controller.getInstance().izmeniPorudzbinu(PorudzbinaZaIzmenu);
                        odgovor.setOdgovor(null);
                    } catch (Exception ex) {
                        odgovor.setOdgovor(ex);
                        System.out.println("Greska, klasa ObradaKlijentskihZahteva: " + ex.getMessage());
                    }
                    break;
                    case IZMENI_POSLASTICARA:
                    try {
                        Poslasticar poslasticarZaIzmenu = (Poslasticar) zahtev.getParametar();
                        Controller.getInstance().izmeniPoslasticara(poslasticarZaIzmenu);
                        odgovor.setOdgovor(null);
                    } catch (Exception ex) {
                        odgovor.setOdgovor(ex);
                        System.out.println("Greška prilikom izmene poslastičara: " + ex.getMessage());
                    }
                    break;
                    default:
                        System.out.println("Greska! Operacija ne postoji.");
                }
                posiljalac.posaljiPoruku(odgovor);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Klijent je prekinuo konekciju: " + e.getMessage());
        }
    }

    public void prekiniKonekciju() {//metoda koja ubija nit
        kraj = true;
        try {
            s.close();//zatvara klijentov socket
        } catch (IOException ex) {
            Logger.getLogger(ObradaKlijentskihZahteva.class.getName()).log(Level.SEVERE, null, ex);
        }
        interrupt();//ako nit spava ili ceka on je budi da ne ostaje zaglavljena, 
        //on garantuje da ce nit izaci iz blokiranog stanja i zavrsiti run metodu
    }
}
