/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Porudzbina;

/**
 *
 * @author katar
 */
public class ModelTabelePorudzbina extends AbstractTableModel{
     private List<Porudzbina> listaPorudzbina;
    private String[] kolone = {"ID Računa", "Ukupan iznos", "Obrađena", "Poslastičar", "Kupac"};

    public ModelTabelePorudzbina(List<Porudzbina> listaRacuna) {
        this.listaPorudzbina = listaRacuna;
    }

    @Override
    public int getRowCount() {
        if (listaPorudzbina == null) {
            return 0;
        }
        return listaPorudzbina.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Porudzbina porudzbina = listaPorudzbina.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return porudzbina.getIdPorudzbina();
            case 1:
                return porudzbina.getUkupanIznos();
            case 2:
                return porudzbina.isObradjena();
            case 3:
                return porudzbina.getPoslasticar().getImePrezime();
            case 4:
                return porudzbina.getKupac().getImePrezimeKupca();
            default:
                return null;
        }
    }

    public List<Porudzbina> getListaPorudzbina() {
        return listaPorudzbina;
    }

    public void setListaPorudzbina(List<Porudzbina> listaPorudzbina) {
        this.listaPorudzbina = listaPorudzbina;
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
    /**
     * Ova metoda je ključna za ispravno sortiranje tabele.
     * TableRowSorter poziva ovu metodu da utvrdi tip podataka svake kolone.
     * Na osnovu povratne vrednosti (npr. Long.class, Double.class),
     * sorter zna da li treba da sortira kolonu kao brojeve, tekst, ili nešto drugo.
     * Bez ove metode, sve kolone bi se sortirale kao tekst (abecedno),
     * što dovodi do pogrešnog poretka za brojeve (npr. 14, 2, 1).
     *
     * Što se tiče upitnika '?', on je takozvani "wildcard" u Javi.
     * Znači da ova metoda vraća objekat tipa Class koji može da predstavlja bilo koji tip,
     * što je neophodno jer ova metoda za svaku kolonu vraća različit tip (Long, Double, Boolean, String).
     */
        switch (columnIndex) {
            case 0: // ID Racuna
                return Long.class;
            case 1: // Ukupan iznos
                return Double.class;
            case 2: // Obradjen
                return Boolean.class;
            case 3: // Poslasticar
            case 4: // Kupac
                return String.class; // Sortiraće se po toString() metodi
            default:
                return Object.class;
        }
    }
}
