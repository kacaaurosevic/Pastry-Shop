/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package komunikacija;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author katar
 */
public class Primalac {

    private Socket soket;

    public Primalac(Socket soket) {
        this.soket = soket;
    }

    public Object primiPoruku() {
        try {
            ObjectInputStream ois = new ObjectInputStream(soket.getInputStream());
            Object poruka = ois.readObject();
            return poruka;
        } catch (IOException ex) {
            Logger.getLogger(Primalac.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
