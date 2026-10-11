/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme.model;

import java.util.List;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;
import model.Poslastica;

/**
 *
 * @author katar
 */
public class ModelTabelePoslastica extends AbstractTableModel {

    List<Poslastica> lista;
    String[] kolone = {"Šifra", "Naziv", "Opis", "Cena"};

    public ModelTabelePoslastica(List<Poslastica> lista) {
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
        Poslastica p = lista.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return p.getIdPoslastica();
            case 1:
                return p.getNaziv();
            case 2:
                return p.getOpis();
            case 3:
                return p.getCenaP();
            default:
                return "N/A";
        }
    }

    public List<Poslastica> getLista() {
        return lista;
    }

    public void pretrazi(String naziv, String opis) {
        List<Poslastica> filteredList = lista.stream()
                .filter(p -> (naziv == null || naziv.isEmpty() || p.getNaziv().toLowerCase().contains(naziv.toLowerCase())))
                .filter(p -> (opis == null || opis.isEmpty() || p.getOpis().toLowerCase().contains(opis.toLowerCase())))
                .collect(Collectors.toList());
        this.lista = filteredList;
        fireTableDataChanged();
    }
}
