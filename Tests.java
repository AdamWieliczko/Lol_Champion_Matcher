import org.junit.Test;


public class Tests {

    //TEST SPRAWDZAJACY, CZY NIE WYSTAPILY POWTORZENIA
    @Test
    public void shouldSayDuplicatesFound() {
        Team team = new Team("first", "first", "second", "third", "fourth");
        team.Calculate();
    }

    //TEST SPRAWDZAJACY NIEDOZWOLNE ZNAKI W PODANYCH PRZEZ UZYTKOWNIKA NAZWACH CHAMPIONOW

    @Test
    public void shouldSayWrongCharacters() {
        Team team = new Team("-", ",,,,", ";;;;;", "18", "~~");
        team.Calculate();
    }

    //TEST SPRAWDZAJACY, CZY PODANO KOMPLETNA DRUZYNE
    @Test
    public void nullChecker() {
        Team team = new Team("second", "third", "fourth", "null", null);
        team.Calculate();

    }

    //TEST SPRAWDZAJACY, CZY PODANE NAZWY BOHATEROW FAKTYCZNIE ZNAJDUJA SIE W BAZIE DANYCH
    @Test
    public static void shouldSayChampionNotInBase() {
        Team team = new Team("Elise", "Yasuo", "Talon", "Nami", "Tomato");
        team.Calculate();

    }

}