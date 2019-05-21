package sort;

import data.Song;
import java.util.Comparator;

/**
 *
 * @author Rucki
 */
public class CompTitle implements Comparator<Song> {

    @Override
    public int compare(Song t, Song t1) {
        return t.getTitle().compareTo(t1.getTitle());
    }
}
