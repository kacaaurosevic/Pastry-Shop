/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import konfiguracija.Konfiguracija;
import niti.ObradaKlijentskihZahteva;

/**
 *
 * @author katar
 */
public class Server extends Thread {//za svakog klijenta koji se poveze pravi novu nit ObradaKlijentskih zahteva

    ServerSocket ss;
    boolean kraj = false;
    List<ObradaKlijentskihZahteva> klijenti;

    public Server() {
        klijenti = new ArrayList<>();
    }

    @Override
    public void run() {//imitira pokreni server
        try {
            //ne mogu samo ovako : ss = new ServerSocket(9000); jer sta mi onda znaci da menjam port ako mi ovde stoji da mora 9000
            int port = Integer.parseInt(Konfiguracija.getInstance().getProperty("port"));
            ss = new ServerSocket(port);
            System.out.println("Server ceka klijente...");
            while (!kraj) {
                Socket s = ss.accept();
                System.out.println("Klijent je povezan!");

                ObradaKlijentskihZahteva okz = new ObradaKlijentskihZahteva(s);//za svakog klijenta smo napravili nit
                klijenti.add(okz);//svaki put kad se poveze novi klijent u listu klijenti se doda novi klijent
                okz.start();
            }
        } catch (IOException ex) {
            System.out.println("Veza sa klijentima je prekinuta: " + ex.getMessage());
        }
    }

    public void zaustaviServer() {
        kraj = true;

        for (ObradaKlijentskihZahteva okz : klijenti) {//ovo sluzi ako na jednom serveru zelimo da se nakaci vise klijenata
            okz.prekiniKonekciju();
        }

        klijenti.clear();

        try {
            ss.close();
        } catch (IOException ex) {
            if (!(ex instanceof SocketException)) {
                Logger.getLogger(Server.class.getName())
                        .log(Level.SEVERE, "Greška pri zatvaranju servera", ex);
            }
        }
        System.out.println("Server je uspesno zaustavljen.");
    }
}
