import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import static java.awt.Component.LEFT_ALIGNMENT;

public class ToDo_App {


    public static void main(String[] args) {

       final To_Do_Manager_Klasse toDoManagerKlasse = new To_Do_Manager_Klasse();
        toDoManagerKlasse.loadData();
        toDoManagerKlasse.removeExpiredToDos();
        new To_Do_Window_Klasse(toDoManagerKlasse);
    }


}













