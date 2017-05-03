package pl.edu.amu.wmi.oblicze.konferika.utils;


public class Utils {
    /**
     * Metoda sprawdza czy tekst ma długość 5. Jeżeli nie to dopisuje 0 na początku.
     * Tekst zawiera godzine w formacie HH:MM.
     * Robię tak, żeby segregowanie w TreeMap było poprawne.
     *
     * @param time
     * @return
     */

    public static String formatTime(String time) {
        if (time.length() < 5) {
            time = 0 + time;
        }
        return time;
    }

    /**
     * Metoda zmienia godzine z klucza w treeMapie tak, żeby przy wyświetleniu, nie było 0 na początku.
     *
     * @param time
     * @return
     */
    public static String deFormatTime(String time) {
        if (time.charAt(0) == '0') {
            time = time.substring(1);
        }
        return time;
    }
}
