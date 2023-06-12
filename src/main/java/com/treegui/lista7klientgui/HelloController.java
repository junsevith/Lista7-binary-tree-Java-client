package com.treegui.lista7klientgui;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
   @FXML
   private Label label;
   @FXML
   private ChoiceBox<command> chooseCommand;
   @FXML
   private Label display;
   @FXML
   private Button button;
   @FXML
   private TextField input;
   private ChoiceBox<String> chooseType;

   Stage stage;
   TreeClient client;

   public void setStage(Stage stage) {
      this.stage = stage;
      popup();
   }

   public void initialize() throws IOException {
      client = new TreeClient();
      client.startConnection("127.0.0.1", 6666);
      client.out.println("start");
      readOutput();
   }

   public void popup() {
      stage.setOnCloseRequest(p->{
         try {
            client.stopConnection();
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      });
      final Stage dialog = new Stage();
      dialog.setOnCloseRequest(p->{
         stage.close();
      });
      dialog.initModality(Modality.APPLICATION_MODAL);
      dialog.initOwner(stage);
      VBox dialogVbox = new VBox(20);
      dialogVbox.setAlignment(Pos.TOP_CENTER);
      dialogVbox.setPadding(new Insets(20, 20, 20, 20));
      dialogVbox.setSpacing(10);

      dialogVbox.getChildren().add(new Text("Wybierz typ drzewa"));
      chooseType = new ChoiceBox<>();
      chooseType.getItems().addAll(
            "integer",
            "double",
            "string"
      );
      dialogVbox.getChildren().add(chooseType);
      chooseType.setOnAction(p -> {
         startTree();
         dialog.close();
      });

      Scene dialogScene = new Scene(dialogVbox, 200, 100);
      dialog.setScene(dialogScene);
      dialog.show();
   }

   public void readOutput() {
      try{
         String input = client.in.readLine().replace('\0','\n');
         input = input.substring(0,input.length()-4);
         label.setText(input);
      } catch (Exception e){
         System.out.println(e);
      }
   }
   public void displayTree() {
      try{
         client.out.println("draw");
         String input = client.in.readLine().replace('\0','\n');
         input = input.substring(0,input.length()-4);
         display.setText(input);
      } catch (Exception e){
         System.out.println(e);
      }
   }
   public void startTree(){
      client.out.println(chooseType.getValue());
      readOutput();
      chooseCommand.getItems().add(new command("Dodaj", "insert"));
      chooseCommand.getItems().add(new command("Usuń", "remove"));
      chooseCommand.getItems().add(new command("Wyszukaj", "search"));
      chooseCommand.getSelectionModel().selectFirst();
      button.setOnAction(p ->{
         client.out.println(chooseCommand.getValue().command + " " + input.getText());
         readOutput();
         displayTree();
      });
   }

   private static class command{
      String name;
      String command;
      command(String name, String command){
         this.name = name;
         this.command = command;
      }
      @Override
      public String toString() {
         return name;
      }
   }
}