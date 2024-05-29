/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package game.template;

import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class App extends Application
{
    private static final int NUM_ROWS = 6;
    private static final int NUM_COLS = 5;
    private VBox root;
    private TextField[][] textFields = new TextField[NUM_ROWS][NUM_COLS];
    private String word;
    private int wordscount;
    private char[] inputArray = new char[5];
    private String inputWord;


    public String[] loadWords(InputStream inputStream) { //to get the words that i'm using
    Scanner scan = new Scanner(inputStream);
    String[] words = new String[100]; //i didn't need more than 100
    int count = 0;
    while (scan.hasNextLine() && count < 100) {
        words[count] = scan.nextLine();
        count++;
    }
    scan.close();
    return Arrays.copyOf(words, count);
}
    public void clear(){
        for (int i = 0; i < NUM_ROWS; i++){
            for (int j = 0; j < NUM_COLS; j++){
                TextField textField = textFields[i][j];
                textField.setText("");
                textField.getStyleClass().removeAll("text-field-correct", "text-field-incorrect", "text-field-misplaced");
            }
        }
        newWord();
    }
    public String newWord(){
        Random random = new Random();
        String wordslist = """ 
            MAIZE
            GHOST
            PLUNK
            ROWDY
            LOOSE
            APPLE
            HAPPY
            CANDY
            VIEWS
            FOCUS
            PORTS
            ENTER
            BUILD
            FACTS
            SUGAR
            TOTAL
            SHIFT
            SALSA
            BURNT
            BURST
            WILDS
            WINDY
            WINGS
            TRAIN
            TRAIL
            TRASH
            TRUTH
            FINAL
            FIGHT
            FROST
            FRESH
            FRIED
            STARS
            STARK
            STAND
                """; //the list of words
        String[] words = loadWords(new java.io.ByteArrayInputStream(wordslist.getBytes()));
        if (words.length > 0) {
            word = words[random.nextInt(words.length)];
            //System.out.println(word); //this was for testing but it does just give the answer
        } else {
            System.out.println("No words loaded");
        }
        return word;
    }
    public void check(){
       StringBuilder inputWord = new StringBuilder();
       System.out.println(inputArray);
       inputWord.append(inputArray);
       inputWord.toString();
       String newValue = inputWord.toString(); //i don't know if this is all necessary but i put it in to dtry and make the input correct and i don't wanna risk breaking it
                if (newValue.length() == 5) {
                    int lastFilled = 0; //tracking the lowest row of the chart filled
                        for (int row = 0; row < NUM_ROWS; row++){
                            if (textFields[row][0].getText().length() > 0){
                                lastFilled = row;
                            }
                        }
                    if (newValue.equals(word)){
                        System.out.println("Correct!");
                        for (int i = 0; i < 5; i++){
                            TextField textField = textFields[lastFilled][i];
                            textField.getStyleClass().removeAll("text-field-incorrect", "text-field-misplaced");
                            textField.getStyleClass().add("text-field-correct");
                        }
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Congratulations");
                        alert.setHeaderText("The word was: " + word);
                        alert.setContentText("You have guessed the word correctly!");
                        alert.showAndWait();
                    }
                    else {
                        if (lastFilled == 5){
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("You have run out of guesses");
                            alert.setContentText("The word was: " + word);
                            alert.showAndWait();
                        }
                            for (int col = 0; col < 5; col++)
                            {
                                TextField textField = textFields[lastFilled][col];
                               // textField.textProperty().addListener((observable, oldValue, v) -> { //i had hoped this would fix the color problem but then it wouldn't run any of my if/else statements
                                if (newValue.charAt(col) == word.charAt(col)){
                                    textField.getStyleClass().removeAll("text-field-incorrect", "text-field-misplaced");
                                    textField.getStyleClass().add("text-field-correct");
                                    // inputArray[col] = ' '; //resetting it to blank so it doesn't change for double letters
                                } 
                                else if (word.contains(newValue.charAt(col) + "")){
                                    textField.getStyleClass().removeAll("text-field-incorrect", "text-field-correct");
                                    textField.getStyleClass().add("text-field-misplaced");
                                }
                                else {
                                    textField.getStyleClass().removeAll("text-field-correct", "text-field-misplaced");
                                    textField.getStyleClass().add("text-field-incorrect");
                                }
                         //   });
                        }
                    }
                 }
                    else {
                        Alert alert = new Alert(AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("The word must be 5 characters long");
                        alert.showAndWait();
                    }
                
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        root = new VBox();

        root.getChildren().add(createMenuBar());

        GridPane gridPane = new GridPane();
        root.getChildren().add(gridPane);
        gridPane.getStyleClass().add("grid-pane");
        newWord();
        for (int row = 0; row < NUM_ROWS; row++)
        {
            for (int col = 0; col < NUM_COLS; col++)
            {
                textFields[row][col] = new TextField();
                final int column = col;
                TextField textField = textFields[row][col];

                // 6 rows, 5 columns for WORDLE
                textField.setId(row + "-" + col);
                gridPane.add(textField, col, row);
                textField.textProperty().addListener((observable, oldValue, newValue) -> {
                    int indexRow = Integer.parseInt(textField.getId().split("-")[0]);
                    newValue = newValue.toUpperCase();
                    if (newValue.length() > 0){
                        inputArray[column] = newValue.charAt(0);
                        //System.out.println("Input so far:");
                        //System.out.println(inputArray); had just been using these to check the input when it wasn't working
                        inputWord = inputWord + newValue.charAt(0);
                    }
                    if (textField.getText().length() > 1){
                        textField.setText(newValue.substring(0, 1)); //prevent multiple letters in one box
                    }
                   // System.out.println("TextField " + textField.getId() + " changed from \"" + oldValue + "\" to \"" + newValue + "\"");
                });
            }
        }

        root.setOnKeyPressed(event -> {
            System.out.println("Key pressed: " + event.getCode());
            switch (event.getCode())
            {
                // check for the key input
                case ESCAPE:
                    // remove focus from the textfields by giving it to the root VBox
                    root.requestFocus();
                    System.out.println("You pressed ESC key");
                    break;
                case ENTER:
                    System.out.println("You pressed ENTER key");
                    check();
                    break;
                case TAB: 
                    System.out.println("You pressed TAB key");
                    break;
                case DELETE:
                    System.out.println("You pressed DELETE key");
                    clear();
                    break;
                default:
                    System.out.println("you typed key: " + event.getCode());
                    break;
                
            }
        });

        // don't give a width or height to the scene
        Scene scene = new Scene(root);

        URL styleURL = getClass().getResource("/style.css");
        String stylesheet = styleURL.toExternalForm();
        scene.getStylesheets().add(stylesheet);
        primaryStage.setTitle("WORDLE");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            System.out.println("oncloserequest");
        });

    }

    private MenuBar createMenuBar()
    {
        MenuBar menuBar = new MenuBar();
    	menuBar.getStyleClass().add("menubar");

        //
        // File Menu
        //
    	Menu fileMenu = new Menu("File");

        addMenuItem(fileMenu, "Load from file", () -> {
            System.out.println("Load from file");
        });

        menuBar.getMenus().add(fileMenu);

        Menu checkMenu = new Menu("Check");     
        addMenuItem(checkMenu, "Check", () -> {
            System.out.println("Check");
            check();
        });
        menuBar.getMenus().add(checkMenu);
        
        Menu clearMenu = new Menu("Clear");
        addMenuItem(clearMenu, "Clear", () -> {
            System.out.println("Clear");
            clear();
        });
        menuBar.getMenus().add(clearMenu);
        return menuBar;
    }

    private void addMenuItem(Menu menu, String name, Runnable action)
    {
        MenuItem menuItem = new MenuItem(name);
        menuItem.setOnAction(event -> action.run());
        menu.getItems().add(menuItem);
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
}
