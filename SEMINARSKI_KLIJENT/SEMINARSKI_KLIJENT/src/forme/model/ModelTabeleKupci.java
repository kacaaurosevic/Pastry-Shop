/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.model;

import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;
import model.Kupac;
import model.Mesto;

/**
 *
 * @author katar
 */
public class ModelTabeleKupci extends AbstractTableModel {

    List<Kupac> lista;
    String[] kolone = {"Id", "Ime i prezime", "Mesto"};

    public ModelTabeleKupci(List<Kupac> lista) {
        this.lista = lista;
    }

    @Override
    public int getRowCount() {
        return lista.size();
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
        Kupac k = lista.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return k.getIdKupac();
            case 1:
                return k.getImePrezimeKupca();
            case 2:
                return k.getMesto().getNazivMesta();
            default:
                return "N/A";
        }
    }

    public List<Kupac> getLista() {
        return lista;
    }

    public void pretrazi(String imePrezime, Mesto mesto) {
        List<Kupac> filteredList = lista.stream()
                .filter(k -> imePrezime == null
                || imePrezime.isEmpty()
                || k.getImePrezimeKupca().toLowerCase().contains(imePrezime.toLowerCase()))
                .filter(k -> mesto == null
                || k.getMesto().getIdMesto().equals(mesto.getIdMesto()))
                .collect(Collectors.toList());

        this.lista = filteredList;
        fireTableDataChanged();
    }

}
