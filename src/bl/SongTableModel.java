package bl;

import data.Song;
import db.AccessDB;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import sort.CompTitle;

public class SongTableModel extends AbstractTableModel {

    List<Song> list = null;

    public SongTableModel() {
        this.list = new ArrayList<>();
    }

    public SongTableModel(List<Song> list) {
        this.list = list;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return SongEnum.values().length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SongEnum index = SongEnum.values()[columnIndex];
        Song s = list.get(rowIndex);
        switch (index) {
            case TITLE:
                String title = s.getTitle();
                String titleNoEx = title.substring(0, title.length() - 4);
                return titleNoEx;
            default:
                return "?";
        }
    }

    @Override
    public String getColumnName(int column) {
        return SongEnum.values()[column].getName();
    }

    public void add(File f) {
        File[] files;
        files = f.listFiles();
        for (File file : files) {
            Song s = new Song(file.getAbsolutePath(), file.getName());
            list.add(s);
        }
        fireTableDataChanged();
    }

    public void remove(int index) {
        list.remove(index);
        Collections.sort(list, new CompTitle());
        fireTableDataChanged();
    }

    public void set(int index, Song song) {
        list.set(index, song);
        fireTableDataChanged();
    }

    public List<Song> getList() {
        return this.list;
    }

    @Override
    public void setValueAt(Object o, int rowIndex, int columnIndex) {
        SongEnum index = SongEnum.values()[columnIndex];
        Song fAlt = list.get(rowIndex);
        String path = fAlt.getFilePath();
        String titleAlt = fAlt.getTitle();
        switch (index) {
            case TITLE:
                titleAlt = (String) o;
                break;

        }
        Song neu = new Song(path, titleAlt);
        list.set(rowIndex, neu);
    }

    public Song getSong(int i) {
        return list.get(i);
    }

    public boolean isCellEditable(int row, int column) {
        return true;
    }

    public int getListSize() {
        return list.size();
    }

    public void addToDB() {
        AccessDB adb = AccessDB.getInstance();
        try {
            for (Song song : list) {

                adb.persistEntity(song);
            }
        } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Es gab ein Problem beim Speichern!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
}
