import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class To_Do_Window_Klasse extends JFrame {

    private final To_Do_Manager_Klasse myToDoManager;
    private   JPanel tmpPanel;


     public To_Do_Window_Klasse(To_Do_Manager_Klasse myToDo){

         this.myToDoManager = myToDo;
         setTitle("ToDo App");
         setSize(720,1080);


         add(tmpPanel = createBoxLayoutForToDos(), BorderLayout.CENTER);
         //add(createToDoBlock(), BorderLayout.CENTER);

         add(panelBottom(),BorderLayout.SOUTH);
         setDefaultCloseOperation(EXIT_ON_CLOSE);
         addWindowListener(new WindowAdapter() {
             @Override
             public void windowClosing(WindowEvent e) {
                 super.windowClosing(e);
                 myToDo.saveToDos();
             }
         });
         setVisible(true);

     }

    private JPanel createBoxLayoutForToDos(){

         final JPanel panel = new JPanel();
         panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

         ArrayList<To_Do_Klasse> todos = myToDoManager.getMyToDoList();
         for(int i =0; i<todos.size();i++){
             panel.add(createToDoBlock(todos.get(i)));
         }

         return panel;
    }

    private JPanel createToDoBlock(To_Do_Klasse toDo){

        final JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        panel.add(new JLabel(toDo.getTitel()), BorderLayout.NORTH);
        panel.add(new JLabel(toDo.getBeschreibung()),BorderLayout.CENTER);

        final JCheckBox jCheckBox = new JCheckBox();
        jCheckBox.setSelected(toDo.isErledigt());
        jCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toDo.setErledigt(jCheckBox.isSelected());
            }
        });
        panel.add(jCheckBox,BorderLayout.EAST);

        if(toDo instanceof To_Do_Begrenzt_Klasse){
            panel.add(new JLabel(((To_Do_Begrenzt_Klasse) toDo).getTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))),BorderLayout.SOUTH);
        }

        return panel;

    }

    /**
     * Methode für die erstellung der unteren 3 Buttons sowie
     * deren funktionalität
     * @return
     */
    private JPanel panelBottom(){

        final JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        final JButton buttonAdd = new JButton("Add");
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    String titel = nichtLeereEingabe("Titel des ToDos");
                    if(titel.isEmpty()) return;
                    String beschreibung = nichtLeereEingabe("Beschreibung des ToDos angeben: ");
                    if(beschreibung.isEmpty()) return;
                    String stunden = nichtLeereEingabe("In wie vielen Stunden soll das ToDo ablaufen? Bein eingabe 0 wird ein normales ToDo erstellt");
                    if(stunden.isEmpty()) return;
                    try {
                        int stundenInteger = Integer.parseInt(stunden);
                    if(stundenInteger == 0){
                        myToDoManager.add(new To_Do_Klasse(titel,beschreibung,false));
                    }else{
                        myToDoManager.add(new To_Do_Begrenzt_Klasse(titel,beschreibung,false,LocalDateTime.now().plusMinutes(stundenInteger)));
                    }
                    }catch (Exception exception){
                        myToDoManager.add(new To_Do_Klasse(titel,beschreibung,false));
                    }

                    remove(tmpPanel);
                    add(tmpPanel = createBoxLayoutForToDos(),BorderLayout.CENTER);
                    pack();
            }
        });

        final JButton buttonDeleteAll = new JButton("Del All");
        buttonDeleteAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 myToDoManager.getMyToDoList().clear();

                remove(tmpPanel);
                pack();

            }
        });

        final JButton buttonDelDone = new JButton("Del Done");
        buttonDelDone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // start bei letztem element der liste und dann das passende löschen
                //dadurch wird sicher gestellt, dass das richtige gelöscht wird und nicht geskippt
                for(int i = myToDoManager.getMyToDoList().size() -1; i>=0;i--){
                    if(myToDoManager.getMyToDoList().get(i).isErledigt()){
                        myToDoManager.getMyToDoList().remove(i);
                    }
                }

                remove(tmpPanel);
                add(tmpPanel = createBoxLayoutForToDos(),BorderLayout.CENTER);
                pack();
            }
        });

        panel.add(buttonAdd);
        panel.add(buttonDeleteAll);
        panel.add(buttonDelDone);

        return panel;
    }



    private String nichtLeereEingabe(String eingabeAufforderung) {

        while (true) {
            String input = JOptionPane.showInputDialog(eingabeAufforderung);

            if(input == null){
                return "";
            }

            if(!input.isEmpty()){
                return input;
            }
        }
    }

}
