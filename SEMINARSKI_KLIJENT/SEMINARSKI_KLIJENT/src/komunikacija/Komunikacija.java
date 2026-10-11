/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package komunikacija;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import komunikacija.*;
import model.*;
import komunikacija.Komunikacija;
import cordinator.Cordinator;

/**
 *
 * @author HP
 */
public class Komunikacija {

    private Socket socket;
    private Posiljalac posiljalac;
    private Primalac primalac;
    private static Komunikacija instance;

    private Komunikacija() {
    }

    public static Komunikacija getInstance() {
        if (instance == null) {
            instance = new Komunikacija();
        }
        return instance;
    }

    public void konekcija() {
        try {
            socket = new Socket("localhost", 9000);
            posiljalac = new Posiljalac(socket);
            primalac = new Primalac(socket);
        } catch (IOException ex) {
            System.out.println("Greska pri povezivanju sa serverom!");
        }
    }

    public Poslasticar login(String ki, String pass) {
        Poslasticar p = new Poslasticar();
        p.setKorisnickoIme(ki);
        p.setSifra(pass);

        Zahtev zahtev = new Zahtev(Operacija.LOGIN, p);
        posiljalac.posaljiPoruku(zahtev);

        Odgovor odgovor = (Odgovor) primalac.primiPoruku();

        if (odgovor == null || odgovor.getOdgovor() == null) {
            return null;
        }

        if (odgovor.getOdgovor() instanceof Exception) {
            Exception ex = (Exception) odgovor.getOdgovor();
            ex.printStackTrace();
            return null;
        }

        return (Poslasticar) odgovor.getOdgovor();
    }

    public List<Poslastica> ucitajPoslastice() {
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_POSLASTICE, null);
        List<Poslastica> Poslasticai = new ArrayList<>();

        posiljalac.posaljiPoruku(zahtev);
        Odgovor odgovor = (Odgovor) primalac.primiPoruku();
        Poslasticai = (List<Poslastica>) odgovor.getOdgovor();
        return Poslasticai;
    }

    public void obrisiPoslastica(Poslastica k) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.OBRISI_POSLASTICU, k);
        posiljalac.posaljiPoruku(zahtev);

        Odgovor odgovor = (Odgovor) primalac.primiPoruku();
        if (odgovor.getOdgovor() == null) {
            System.out.println("Sistem je obrisao Poslastica.");
        } else {
            System.out.println("Greska!");
            ((Exception) odgovor.getOdgovor()).getMessage();
            throw new Exception("Greska!");
        }
    }

    public void dodajPoslastica(Poslastica noviPoslastica) {
        Zahtev zahtev = new Zahtev(Operacija.DODAJ_POSLASTICU, noviPoslastica);
        posiljalac.posaljiPoruku(zahtev);

        Odgovor odgovor = (Odgovor) primalac.primiPoruku();
        if (odgovor.getOdgovor() == null) {
            System.out.println("Uspeh!");
        } else {
            System.out.println("Greska!");
        }
    }

    public void izmeniPoslastica(Poslastica noviPoslastica) {
        Zahtev zahtev = new Zahtev(Operacija.IZMENI_POSLASTICU, noviPoslastica);
        posiljalac.posaljiPoruku(zahtev);

        Odgovor odgovor = (Odgovor) primalac.primiPoruku();
        if (odgovor.getOdgovor() == null) {
            System.out.println("Uspeh!");
            Cordinator.getInstance().osveziFormu();
        } else {
            System.out.println("Greska!");
        }
    }

    public List<Kupac> ucitajKupce() {
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_KUPCE, null);
        List<Kupac> kupci = new ArrayList<>();

        posiljalac.posaljiPoruku(zahtev);
        Odgovor odgovor = (Odgovor) primalac.primiPoruku();
        kupci = (List<Kupac>) odgovor.getOdgovor();
        return kupci;
    }

    public void obrisiKupca(Kupac ku) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.OBRISI_KUPCA, ku);
        posiljalac.posaljiPoruku(zahtev);

        Odgovor odgovor = (Odgovor) primalac.primiPoruku();
        if (odgovor.getOdgovor() == null) {
            System.out.println("Sistem je obrisao kupca.");
        } else {
            System.out.println("Greska!");
            ((Exception) odgovor.getOdgovor()).printStackTrace();
            throw new Exception("Greska!");
        }
    }

    public List<Mesto> ucitajMesta() {
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_MESTA, null);
        List<Mesto> mesta = new ArrayList<>();

        posiljalac.posaljiPoruku(zahtev);
        Odgovor odgovor = (Odgovor) primalac.primiPoruku();
        mesta = (List<Mesto>) odgovor.getOdgovor();
        return mesta;
    }

    public void dodajKupca(Kupac noviKupac) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.DODAJ_KUPCA, noviKupac);
        posiljalac.posaljiPoruku(zahtev);

        Odgovor odgovor = (Odgovor) primalac.primiPoruku();

        if (odgovor.getOdgovor() instanceof Exception) {
            throw (Exception) odgovor.getOdgovor();
        }

        if (odgovor.getOdgovor() == null) {
            System.out.println("Uspeh!");
        }
    }

    public void dodajMesto(Mesto novoMesto) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.DODAJ_MESTO, novoMesto);
        posiljalac.posaljiPoruku(zahtev);

        Odgovor odgovor = (Odgovor) primalac.primiPoruku();

        if (odgovor.getOdgovor() instanceof Exception) {
            throw (Exception) odgovor.getOdgovor();
        }

        if (odgovor.getOdgovor() == null) {
            System.out.println("Uspeh!");
        }
    }

    public void izmeniKupca(Kupac noviKupac) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.IZMENI_KUPCA, noviKupac);
        posiljalac.posaljiPoruku(zahtev);

        Odgovor odgovor = (Odgovor) primalac.primiPoruku();

        if (odgovor.getOdgovor() instanceof Exception) {
            throw (Exception) odgovor.getOdgovor();
        }

        if (odgovor.getOdgovor() == null) {
            System.out.println("Uspeh!");
            Cordinator.getInstance().osveziFormuKupac();
        }
    }

    public List<Smena> ucitajSmene() {
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_SMENE, null);
        List<Smena> smene = new ArrayList<>();

        posiljalac.posaljiPoruku(zahtev);
        Odgovor odgovor = (Odgovor) primalac.primiPoruku();
        smene = (List<Smena>) odgovor.getOdgovor();
        return smene;
    }

    public void dodajSmenu(Smena smena) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.DODAJ_SMENU, smena);
        posiljalac.posaljiPoruku(zahtev);

        Odgovor odgovor = (Odgovor) primalac.primiPoruku();

        if (odgovor.getOdgovor() instanceof Exception) {
            throw (Exception) odgovor.getOdgovor();
        }
    }

    public List<Porudzbina> ucitajListuPorudzbina() throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_PORUDZBINE, null);
        posiljalac.posaljiPoruku(zahtev);

        Odgovor odgovor = (Odgovor) primalac.primiPoruku();

        if (odgovor.getOdgovor() instanceof Exception) {
            throw (Exception) odgovor.getOdgovor();
        }

        return (List<Porudzbina>) odgovor.getOdgovor();
    }

    public List<StavkaPorudzbina> ucitajStavkePorudzbina(Porudzbina Porudzbina) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_STAVKE_PORUDZBINA, Porudzbina);
        posiljalac.posaljiPoruku(zahtev);

        Odgovor odgovor = (Odgovor) primalac.primiPoruku();

        if (odgovor.getOdgovor() instanceof Exception) {
            throw (Exception) odgovor.getOdgovor();
        }

        return (List<StavkaPorudzbina>) odgovor.getOdgovor();
    }

    public List<Poslasticar> ucitajPoslasticare() throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.UCITAJ_POSLASTICARE, null);
        posiljalac.posaljiPoruku(zahtev);

        Odgovor odgovor = (Odgovor) primalac.primiPoruku();

        if (odgovor.getOdgovor() instanceof Exception) {
            throw (Exception) odgovor.getOdgovor();
        }

        return (List<Poslasticar>) odgovor.getOdgovor();
    }

    public void kreirajPorudzbina(Porudzbina Porudzbina) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.KREIRAJ_PORUDZBINU, Porudzbina);
        posiljalac.posaljiPoruku(zahtev);

        Odgovor odgovor = (Odgovor) primalac.primiPoruku();

        // Provera da li je serverska strana vratila izuzetak
        if (odgovor.getOdgovor() instanceof Exception) {
            throw (Exception) odgovor.getOdgovor();
        }

        // Ako nije, sve je u redu
        if (odgovor.getOdgovor() == null) {
            System.out.println("Sistem je uspešno kreirao račun.");
        }
    }

    public List<Porudzbina> pretraziPorudzbine(Porudzbina PorudzbinaZaPretragu) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.PRETRAZI_PORUDZBINE, PorudzbinaZaPretragu);
        posiljalac.posaljiPoruku(zahtev);

        Odgovor odgovor = (Odgovor) primalac.primiPoruku();

        if (odgovor.getOdgovor() instanceof Exception) {
            throw (Exception) odgovor.getOdgovor();
        }

        return (List<Porudzbina>) odgovor.getOdgovor();
    }

    public void izmeniPorudzbina(Porudzbina Porudzbina) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.IZMENI_PORUDZBINU, Porudzbina);
        posiljalac.posaljiPoruku(zahtev);

        Odgovor odgovor = (Odgovor) primalac.primiPoruku();

        if (odgovor.getOdgovor() instanceof Exception) {
            throw (Exception) odgovor.getOdgovor();
        }

        if (odgovor.getOdgovor() == null) {
            System.out.println("Uspeh!");
        }
    }

    public void izmeniPoslasticara(Poslasticar poslasticar) throws Exception {
        Zahtev zahtev = new Zahtev(Operacija.IZMENI_POSLASTICARA, poslasticar);
        posiljalac.posaljiPoruku(zahtev);

        Odgovor odgovor = (Odgovor) primalac.primiPoruku();

        if (odgovor.getOdgovor() instanceof Exception) {
            throw (Exception) odgovor.getOdgovor();
        }
    }

}
