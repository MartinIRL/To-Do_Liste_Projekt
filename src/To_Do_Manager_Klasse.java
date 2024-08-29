import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class To_Do_Manager_Klasse {



    //Liste todos
    private final ArrayList<To_Do_Klasse> myToDoList;

    public To_Do_Manager_Klasse(){
        this.myToDoList = new ArrayList<>();
    }


    public ArrayList<To_Do_Klasse> getMyToDoList() {
        return myToDoList;
    }

    //add todo hinzufügen

    public void add(To_Do_Klasse todo){

        if(!myToDoList.contains(todo)){
            myToDoList.add(todo);
        }
    }

    //remove
    public void remove(To_Do_Klasse todo){
        myToDoList.remove(todo);
    }

    //gettimedTodos
    public ArrayList<To_Do_Begrenzt_Klasse> getTimedToDos(){

        final ArrayList<To_Do_Begrenzt_Klasse> myToDos = new ArrayList<>();

        if(myToDoList != null) {
            for (int i = 0; i < myToDoList.size(); i++) {

                if (myToDoList.get(i) instanceof To_Do_Begrenzt_Klasse) {
                    myToDos.add((To_Do_Begrenzt_Klasse) myToDoList.get(i));
                }
            }
        }
        return myToDos;
    }

    //get(normal)todos
    public ArrayList<To_Do_Klasse> getNormalToDos(){

       final ArrayList<To_Do_Klasse> myToDos = new ArrayList<>();

        if(myToDoList != null) {
            for (int i = 0; i < myToDoList.size(); i++) {
                if (!(myToDoList.get(i) instanceof To_Do_Begrenzt_Klasse)) {
                    myToDos.add(myToDoList.get(i));
                }
            }
        }
        return myToDos;
    }

    //entfernen der zeitlich abgelaufenen todos

    public void removeExpiredToDos() {

        final ArrayList<To_Do_Begrenzt_Klasse> timedToDos = getTimedToDos();
        for (int i = 0; i < timedToDos.size(); i++) {
            final To_Do_Begrenzt_Klasse time = timedToDos.get(i);
                /*
                int summe = TimedToDos.getTime().getHour() + TimedToDos.getTime().getMinute() + TimedToDos.getTime().getSecond();
                if(summe == 0){
                    myToDoList.remove(timedToDos);
                }
                */
            if (time.getTime().isBefore(LocalDateTime.now())) {
                remove(time);
            }
        }
    }


    public void saveToDos(){
        final File file = new File("ToDoListe.txt");
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            final FileWriter writer = new FileWriter(file);
            if (myToDoList != null) {
               for(int i =0; i<myToDoList.size();i++){
                  final To_Do_Klasse todos = myToDoList.get(i);

                   String tmp = todos.getTitel() + ";" + todos.getBeschreibung()+";"+todos.isErledigt();
                   if(todos instanceof To_Do_Begrenzt_Klasse){
                       tmp+= ";" +((To_Do_Begrenzt_Klasse) todos).getTime();
                   }
                   writer.write(tmp +"\n");
               }
            }
            writer.close();
        }catch (IOException e){
            System.out.println("Datei konnte nicht gespeichert werden");
        }
    }

    public void loadData(){

        final File file = new File("ToDoListe.txt");
        if(file.exists()) {
            String allCharactersAsString = "";
            try {
                final FileReader reader = new FileReader("ToDoListe.txt");
                int zeichen = reader.read();
                while (zeichen != -1) {

                    if ((char) zeichen == '\n') {
                        final String[] arr = allCharactersAsString.split(";");
                        if(arr.length <4) {
                            myToDoList.add(new To_Do_Klasse(arr[0],arr[1],Boolean.parseBoolean(arr[2])));
                            allCharactersAsString = "";
                        }else{
                            myToDoList.add(new To_Do_Begrenzt_Klasse(arr[0],arr[1],Boolean.parseBoolean(arr[2]),LocalDateTime.parse(arr[3])));
                            allCharactersAsString = "";
                        }
                    } else {
                        allCharactersAsString += (char) zeichen;
                    }
                    zeichen = reader.read();
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("Es konnten keine Daten geladen werden");
            }
        }

    }






}
