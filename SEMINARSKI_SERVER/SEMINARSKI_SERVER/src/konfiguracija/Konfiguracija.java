/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package konfiguracija;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author katar
 */
public class Konfiguracija {//pravim je kao singleton klasu, cita config.properties i setuje i getuje fajl

    private static Konfiguracija instance;
    private Properties konfiguracija;//omogucice mi citanje iz fajla properties

    private Konfiguracija() {

        try {
            konfiguracija = new Properties();
            konfiguracija.load(new FileInputStream("config\\config.properties"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Konfiguracija.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            ex.printStackTrace();
            Logger.getLogger(Konfiguracija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Konfiguracija getInstance() {
        if (instance == null) {
            instance = new Konfiguracija();
        }
        return instance;
    }

    public String getProperty(String key) {
        return konfiguracija.getProperty(key, "n/a");
    }

    public void setProperty(String key, String value) {
        konfiguracija.setProperty(key, value);
    }
    
        
    public void sacuvajIzmene(){//treba nam zbog buttona u formi FormaKonfiguracijaBaza
        try{
            konfiguracija.store(new FileOutputStream("config\\config.properties"), null);
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

}
