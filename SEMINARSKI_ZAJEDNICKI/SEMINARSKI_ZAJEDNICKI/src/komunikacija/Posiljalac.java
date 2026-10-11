/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package komunikacija;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author katar
 */
public class Posiljalac {

    private Socket socket;

    public Posiljalac(Socket socket) {
        this.socket = socket;
    }

    public void posaljiPoruku(Object objekat) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(objekat);
            oos.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
