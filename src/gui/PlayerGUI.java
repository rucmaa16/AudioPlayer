/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import bl.AudioPlayer;
import bl.SongTableModel;
import data.Song;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Rucki
 */
public class PlayerGUI extends javax.swing.JFrame {

    private File f;
    private JFileChooser fileChooser;
    private AudioPlayer ap;
    private Boolean isPlaying;
    private SongTableModel stm;
    private int indexPlaying;

    public PlayerGUI() {
        initComponents();
        initFileChooser();
        initModel();
        ap = new AudioPlayer();
        isPlaying = false;
    }

    /**
     * Initsialisiert das TableModel
     */
    private void initModel() {
        this.stm = new SongTableModel();
        tbSongs.setModel(stm);
    }

    /**
     * Initsialisiert den JFileChooser Selection Mode wird auf Directories
     * beschränkt
     */
    private void initFileChooser() {
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        btSkipBack = new javax.swing.JButton();
        btPlayPause = new javax.swing.JButton();
        btSkipForward = new javax.swing.JButton();
        btLoop = new javax.swing.JButton();
        slVolume = new javax.swing.JSlider();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btAddSongs = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        paTable = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbSongs = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Audio Player");

        jPanel4.setLayout(new java.awt.GridLayout(1, 5));

        btSkipBack.setText("<");
        btSkipBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onSkipBack(evt);
            }
        });
        jPanel4.add(btSkipBack);

        btPlayPause.setText("Play");
        btPlayPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onPlayPause(evt);
            }
        });
        jPanel4.add(btPlayPause);

        btSkipForward.setText(">");
        btSkipForward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onSkipForward(evt);
            }
        });
        jPanel4.add(btSkipForward);

        btLoop.setText("Loop");
        jPanel4.add(btLoop);

        slVolume.setMajorTickSpacing(5);
        slVolume.setMinorTickSpacing(1);
        slVolume.setValue(0);
        slVolume.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                onChangeVolume(evt);
            }
        });
        jPanel4.add(slVolume);

        getContentPane().add(jPanel4, java.awt.BorderLayout.SOUTH);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.EAST);

        jPanel2.setLayout(new java.awt.GridLayout(2, 1));

        btAddSongs.setText("Songs hinzufügen");
        btAddSongs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onAddSongs(evt);
            }
        });
        jPanel2.add(btAddSongs);

        jButton5.setText("Wiedergabeliste erstellen");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onErstelleListe(evt);
            }
        });
        jPanel2.add(jButton5);

        getContentPane().add(jPanel2, java.awt.BorderLayout.WEST);

        paTable.setLayout(new java.awt.GridLayout(1, 1));

        tbSongs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbSongs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onClickPlaySong(evt);
            }
        });
        jScrollPane1.setViewportView(tbSongs);

        paTable.add(jScrollPane1);

        getContentPane().add(paTable, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Vom JFileChooser wird ein Directory Pfad zurückgegeben Im TableModel
     * werden alle Songs hinzugefügt
     *
     * @param evt
     */
    private void onAddSongs(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onAddSongs
        int i = fileChooser.showOpenDialog(this);
        if (i == JFileChooser.APPROVE_OPTION) {
            f = fileChooser.getSelectedFile();
        }
        stm.add(f);
    }//GEN-LAST:event_onAddSongs

    /**
     * Ist eine Reihe im Table ausgewählt wird der Song abgespielt Oder die
     * Musik stoppt komplett
     *
     * @param evt
     */
    private void onPlayPause(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onPlayPause
        indexPlaying = tbSongs.getSelectedRow();
        if (indexPlaying == -1) {
            JOptionPane.showMessageDialog(this, "Bitte einen Song auswählen!");
        } else {
            Song s = stm.getSong(indexPlaying);
            if (isPlaying) {
                isPlaying = false;
                ap.stopMusic();
                btPlayPause.setText("Play");
            } else {
                isPlaying = true;
                ap.starteAbspielen(new File(s.getFilePath()));
                btPlayPause.setText("Stop");
            }
        }
    }//GEN-LAST:event_onPlayPause

    private void onErstelleListe(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onErstelleListe

    }//GEN-LAST:event_onErstelleListe

    /**
     * Lautstärkeregelung mit Hilfe von nircmd nircmd führt Befehle aus, um
     * Windows Systemlautstärke zu ändern
     *
     * @param evt
     */
    private void onChangeVolume(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_onChangeVolume
        int volume = slVolume.getValue();
        if (volume < 0 || volume > 100) {
            throw new RuntimeException("Error: " + volume + " is not a valid number. Choose a number between 0 and 100");
        } else {
            double endVolume = 655.35 * volume;
            Runtime rt = Runtime.getRuntime();
            Process pr;
            try {
                pr = rt.exec("C:\\Windows\\nircmd.exe" + " setsysvolume " + endVolume);
                //pr = rt.exec(nircmdFilePath + " mutesysvolume 0");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_onChangeVolume

    /**
     * Es wird zum nächsten Song gesprungen Ist es der letzte Song in der Liste,
     * wird der Erste Song wiedergegeben
     *
     * @param evt
     */
    private void onSkipForward(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onSkipForward
        int listSize = stm.getListSize();
        ap.stopMusic();
        if (indexPlaying == listSize - 1) {
            Song s = stm.getSong(0);
            indexPlaying = 0;
            ap.starteAbspielen(new File(s.getFilePath()));
        } else {
            Song s = stm.getSong(indexPlaying + 1);
            ap.starteAbspielen(new File(s.getFilePath()));
            indexPlaying++;
        }
    }//GEN-LAST:event_onSkipForward

    /**
     * Es wird zum vorherigen Song gesprungen Ist es der Erste Song in der
     * Liste, wird der Letzte wiedergegeben
     *
     * @param evt
     */
    private void onSkipBack(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onSkipBack
        int listSize = stm.getListSize();
        ap.stopMusic();
        if (indexPlaying == 0) {
            Song s = stm.getSong(listSize - 1);
            indexPlaying = listSize - 1;
            ap.starteAbspielen(new File(s.getFilePath()));
        } else {
            Song s = stm.getSong(indexPlaying - 1);
            ap.starteAbspielen(new File(s.getFilePath()));
            indexPlaying--;
        }
    }//GEN-LAST:event_onSkipBack

    private void onClickPlaySong(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onClickPlaySong
        if (evt.getButton() == MouseEvent.BUTTON1) {
            indexPlaying = tbSongs.getSelectedRow();
            if (indexPlaying == -1) {
                JOptionPane.showMessageDialog(this, "Bitte einen Song auswählen!");
            } else {
                Song s = stm.getSong(indexPlaying);
                if (isPlaying) {
                    isPlaying = false;
                    ap.stopMusic();
                    btPlayPause.setText("Play");
                } else {
                    isPlaying = true;
                    ap.starteAbspielen(new File(s.getFilePath()));
                    btPlayPause.setText("Stop");
                }
            }
        }
    }//GEN-LAST:event_onClickPlaySong

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PlayerGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PlayerGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PlayerGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PlayerGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PlayerGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAddSongs;
    private javax.swing.JButton btLoop;
    private javax.swing.JButton btPlayPause;
    private javax.swing.JButton btSkipBack;
    private javax.swing.JButton btSkipForward;
    private javax.swing.JButton jButton5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel paTable;
    private javax.swing.JSlider slVolume;
    private javax.swing.JTable tbSongs;
    // End of variables declaration//GEN-END:variables
}
