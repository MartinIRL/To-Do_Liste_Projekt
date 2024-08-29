import java.time.LocalDateTime;

public class To_Do_Begrenzt_Klasse extends To_Do_Klasse {



    private  LocalDateTime time;

    public To_Do_Begrenzt_Klasse(String titel,String beschreibung,boolean erledigt, LocalDateTime time){
        super(titel,beschreibung,erledigt);
        this.time = time;
    }



    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public String toString(){
        return "{TimedToDo Titel: " + getTitel()+ " - " +" Beschreibung: " + getBeschreibung() + " - " +" Timer: " +getTime() + " - "+
                " erledigt?:" + isErledigt() +
                "}";
    }
}
