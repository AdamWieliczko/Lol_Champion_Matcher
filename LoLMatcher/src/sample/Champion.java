package sample;

//JEDNA FUNKCJONALNOSC, PRZECHOWYWANIE DANYCH O BOHATERZE
public class Champion {

     private String name;
     private byte[] attribute;
    //KONSTRUKTOR
    public Champion(String name, byte[] attribute) {
        this.attribute = new byte[12];
        this.name = name;
        System.arraycopy(attribute, 0, this.attribute, 0, 12);
    }

    public String getName() {
        return name;
    }

    public byte getAttribute(int index) {
        return attribute[index];
    }
}
