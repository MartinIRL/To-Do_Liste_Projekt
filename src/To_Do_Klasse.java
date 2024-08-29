public class To_Do_Klasse {

    private  boolean erledigt;
    private String titel;
    private String beschreibung;



    public To_Do_Klasse(String titel, String beschreibung, boolean erledigt){
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.erledigt = erledigt;
    }





    public boolean isErledigt() {
        return erledigt;
    }

    public void setErledigt(boolean erledigt) {
        this.erledigt = erledigt;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }


    @Override
    public String toString(){
        return "{ToDo Titel: " + getTitel() + " - " + " Beschreibung: " + getBeschreibung() + " - " + " erledigt?:" + isErledigt() +
                " }";
    }
}
