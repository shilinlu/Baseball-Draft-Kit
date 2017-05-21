/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieball.gui;

import java.time.LocalDate;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import wolfieball.data.Hitters;
import wolfieball.data.Pitchers;
import wolfieball.data.Team;
import static wolfieball.gui.Wolfie_GUI.CLASS_HEADING_LABEL;
import static wolfieball.gui.Wolfie_GUI.CLASS_PROMPT_LABEL;
import static wolfieball.gui.Wolfie_GUI.PRIMARY_STYLE_SHEET;
import static wolfieball_draft_kit.StartupConstants.PATH_FLAG_IMAGES;
import static wolfieball_draft_kit.StartupConstants.PATH_PLAYER_IMAGES;

/**
 *
 * @author shilinlu
 */
public class PlayerDialog extends Stage{
    // THIS IS THE OBJECT DATA BEHIND THIS UI
    Hitters player;
    Pitchers player1;
// GUI CONTROLS FOR OUR DIALOG
    ComboBox fantasyTeamComboBox; ComboBox positionComboBox; ComboBox contractComboBox;TextField salaryTextField;
    GridPane realGridPane;
    BorderPane gridPane;
    Scene dialogScene;
    Label headingLabel;
    Label firstNameLabel;
    TextField firstNameTextField;
    Label lastNameLabel;
    TextField lastNameTextField;
    Label proTeamLabel;
    ComboBox proTeamComboBox;
    CheckBox c,b1,b3,b2,ss,of,p;
    Button completeButton;
    Button cancelButton;
    // THIS IS FOR KEEPING TRACK OF WHICH BUTTON THE USER PRESSED
    String selection;
    // CONSTANTS FOR OUR UI
    public static final String COMPLETE = "Complete";
    public static final String CANCEL = "Cancel";
    public static final String FIRST_NAME_PROMPT = "First Name: ";
    public static final String LAST_NAME_PROMPT = "Last Name:  ";
    public static final String PRO_TEAM_PROMPT = "Pro  Team:  ";
    public static final String PLAYER_HEADING = "Player Details";
    public static final String ADD_SCHEDULE_ITEM_TITLE = "Add New Player";
    public static final String EDIT_SCHEDULE_ITEM_TITLE = "Edit Player";
    int i=1;ObservableList<String> options = FXCollections.observableArrayList("ATL","AZ","CHC","CIN","COL","LAD","MIA","MIL","NYM","PHI","PIT","SD","SF","STL","WAS");
    /**
     * Initializes this dialog so that it can be used for either adding
     * new schedule items or editing existing ones.
     * 
     * @param primaryStage The owner of this modal dialog.
     */
    public PlayerDialog(Team team,  MessageDialog messageDialog) {       
         // MAKE THIS DIALOG MODAL, MEANING OTHERS WILL WAIT
        // FOR IT WHEN IT IS DISPLAYED
        initModality(Modality.WINDOW_MODAL);
        //initOwner(primaryStage);
        
    }
    /**
     * Accessor method for getting the selection the user made.
     * 
     * @return Either YES, NO, or CANCEL, depending on which
     * button the user selected when this dialog was presented.
     */
    public String getSelection() {
        return selection;
    }
    
    public Hitters getPlayer() { 
        return player;
    }
    public Pitchers getPlayer1() { 
        return player1;
    }
    
    /**
     * This method loads a custom message into the label and
     * then pops open the dialog.
     * 
     * @param message Message to appear inside the dialog.
     */
    public Hitters showAddPlayerDialog() {
        // SET THE DIALOG TITLE
        setTitle(ADD_SCHEDULE_ITEM_TITLE);
        
        // RESET THE SCHEDULE ITEM OBJECT WITH DEFAULT VALUES
        player = new Hitters();
        
       // FIRST OUR CONTAINER
        gridPane = new BorderPane();
        gridPane.setPadding(new Insets(10, 20, 20, 20));
       // gridPane.setHgap(10);
       // gridPane.setVgap(10);
        // PUT THE HEADING IN THE GRID, NOTE THAT THE TEXT WILL DEPEND
        // ON WHETHER WE'RE ADDING OR EDITING
        headingLabel = new Label(PLAYER_HEADING);
        headingLabel.getStyleClass().add(CLASS_HEADING_LABEL);
    
        // NOW THE FIRSTNAME 
        firstNameLabel = new Label(FIRST_NAME_PROMPT);
        firstNameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        firstNameTextField = new TextField();
        firstNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            player.setFirstName(newValue);
        });
        // NOW THE LASTNAME 
        lastNameLabel = new Label(LAST_NAME_PROMPT);
        lastNameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        lastNameTextField = new TextField();
        lastNameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
           player.setLastName(newValue);
        });
        // NOW THE PRO TEAM
        proTeamLabel = new Label(PRO_TEAM_PROMPT);
        proTeamLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        
        proTeamComboBox=new ComboBox(options);
        
        
        proTeamComboBox.setOnAction(e -> {
            player.setTeamName(proTeamComboBox.getSelectionModel().getSelectedItem().toString());
        });
        //System.out.println(proTeamComboBox.getItems().get(0));
// NOW THE CHECK BOX
        
        HBox checkBox=new HBox();
        
        c=new CheckBox("C"); b1=new CheckBox("1B"); b3=new CheckBox("3B"); b2=new CheckBox("2B"); ss=new CheckBox("SS"); 
        of=new CheckBox("OF"); p=new CheckBox("P"); 
        checkBox.getChildren().addAll(c,b1,b3,b2,ss,of,p);
        
        c.setOnMouseClicked((MouseEvent event) -> {player.setQp("C");});  b2.setOnMouseClicked((MouseEvent event) -> {player.setQp("2B");});
        b1.setOnMouseClicked((MouseEvent event) -> {player.setQp("1B");}); ss.setOnMouseClicked((MouseEvent event) -> {player.setQp("SS");});   
        b3.setOnMouseClicked((MouseEvent event) -> {player.setQp("3B");}); of.setOnMouseClicked((MouseEvent event) -> {player.setQp("OF");});
        p.setOnMouseClicked((MouseEvent event) -> {player.setQp("P");});

        checkBox.setSpacing(5);
       // AND FINALLY, THE BUTTONS
        completeButton = new Button(COMPLETE);
        cancelButton = new Button(CANCEL);
        
        // REGISTER EVENT HANDLERS FOR OUR BUTTONS
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            PlayerDialog.this.selection = sourceButton.getText();
            PlayerDialog.this.hide();
        };
        completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);
        VBox topVBox=new VBox(); VBox midVBox=new VBox(); VBox botVBox=new VBox();
        HBox topHBox=new HBox(); HBox topHBox1=new HBox(); 
        HBox midHBox=new HBox(); HBox midHBox1=new HBox();
        HBox botHBox=new HBox(); HBox botHBox1=new HBox();
        topHBox.getChildren().add(headingLabel);
        topHBox1.getChildren().addAll(firstNameLabel,firstNameTextField);
       
        midHBox.getChildren().addAll(lastNameLabel,lastNameTextField);
        midHBox1.getChildren().addAll(proTeamLabel,proTeamComboBox);
        botHBox.getChildren().add(checkBox);
        
        botHBox1.getChildren().addAll(completeButton,cancelButton);
        botHBox1.setSpacing(30);
        topVBox.getChildren().addAll(topHBox,topHBox1);
        
        midVBox.getChildren().addAll(midHBox,midHBox1);
        botVBox.getChildren().addAll(botHBox,botHBox1);
        
        // NOW LET'S ARRANGE THEM ALL AT ONCE
        gridPane.setTop(topVBox);
       gridPane.setCenter(midVBox);
       gridPane.setBottom(botVBox);
       
        // AND PUT THE GRID PANE IN THE WINDOW
        dialogScene = new Scene(gridPane);
        dialogScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
        this.setScene(dialogScene);
        
        // AND OPEN IT UP
        this.showAndWait();
        
        return player;
    }
    
    
    
    
    
    public void loadGUIData() {
        // LOAD THE UI STUFF
        firstNameLabel.setText(player.getFirstName());
        
        lastNameLabel.setText(player.getLastName());       
    }
    public void loadGUIData1() {
        // LOAD THE UI STUFF
        firstNameLabel.setText(player1.getFirstName());
        
        lastNameLabel.setText(player1.getLastName());       
    }
    
    
    public boolean wasCompleteSelected() {
        return selection.equals(COMPLETE);
        //return true;
    }
    
    public void showEditPlayerDialog(Hitters playerToEdit) {
        // SET THE DIALOG TITLE
        setTitle(EDIT_SCHEDULE_ITEM_TITLE);
        //(Object) playerToEdit
        // LOAD THE SCHEDULE ITEM INTO OUR LOCAL OBJECT
        player = new Hitters();
        // FIRST OUR CONTAINER
        gridPane = new BorderPane();
        gridPane.setPadding(new Insets(10, 20, 20, 20));
        // PUT THE HEADING IN THE GRID, NOTE THAT THE TEXT WILL DEPEND
        // ON WHETHER WE'RE ADDING OR EDITING
        headingLabel = new Label(PLAYER_HEADING);
        headingLabel.getStyleClass().add(CLASS_HEADING_LABEL);
        // NOW THE FIRSTNAME 
        firstNameLabel = new Label(playerToEdit.getFirstName());
        firstNameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        lastNameLabel = new Label(playerToEdit.getLastName());
        lastNameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        Label qpLabel=new Label(playerToEdit.getQp());
        qpLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        Label fantasyTeamLabel= new Label("Fantasy Team:  ");
        Label positionLabel= new Label("Position:  ");
        Label contractLabel= new Label("Contract:  ");
        Label salaryLabel= new Label("Salary($):  ");
        String playerImagePath = "file:" + PATH_PLAYER_IMAGES + playerToEdit.getLastName()+playerToEdit.getFirstName()+".jpg";
        Image playerImage = new Image(playerImagePath);
        if(playerImage.isError()==true){
            playerImage = new Image("file:"+PATH_PLAYER_IMAGES+"AAA_PhotoMissing.jpg");
            System.out.println("cannot find image");
        }
        ImageView playerImageView=new ImageView(playerImage);
        
        
        String flagImagePath = "file:" + PATH_FLAG_IMAGES + playerToEdit.getNation_of_birth()+".png";
        Image flagImage = new Image(flagImagePath);
        ImageView flagImageView=new ImageView(flagImage);
        
        VBox rightVBox=new VBox();
        rightVBox.getChildren().addAll(flagImageView,firstNameLabel,lastNameLabel,qpLabel);
        rightVBox.setSpacing(10);
        fantasyTeamComboBox=new ComboBox(); positionComboBox=new ComboBox();contractComboBox=new ComboBox();salaryTextField=new TextField();
        salaryTextField.setMaxWidth(110); fantasyTeamComboBox.setMinWidth(110); positionComboBox.setMinWidth(110);contractComboBox.setMinWidth(110);
        positionComboBox.getItems().add(playerToEdit.getQp());
        contractComboBox.getItems().addAll("S1","S2","X"); 
        fantasyTeamComboBox.setItems(options);
// player.setQp(positionComboBox.getSelectionModel().getSelectedItem().toString());
        //fantasyTeamComboBox.getItems().add(ce);
        
        salaryTextField.textProperty().addListener((observable, oldValue, newValue) -> {
           player.setSalary(newValue);
        });
        

        // player.setContract(contractComboBox.getSelectionModel().getSelectedItem().toString());
        // AND FINALLY, THE BUTTONS
        completeButton = new Button(COMPLETE);
        cancelButton = new Button(CANCEL);
        
        // REGISTER EVENT HANDLERS FOR OUR BUTTONS
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            PlayerDialog.this.selection = sourceButton.getText();
            PlayerDialog.this.hide();
        };
        completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);
        realGridPane=new GridPane();
        realGridPane.add(fantasyTeamLabel, 0, 0, 2, 1);
        realGridPane.add(fantasyTeamComboBox, 5, 0, 1, 1);
        realGridPane.add(positionLabel, 0, 1, 1, 1);
        realGridPane.add(positionComboBox, 5, 1, 1, 1);
        realGridPane.add(contractLabel, 0, 2, 1, 1);
        realGridPane.add(contractComboBox, 5, 2, 1, 1);
        realGridPane.add(salaryLabel, 0, 3, 1, 1);
        realGridPane.add(salaryTextField, 5, 3, 1, 1);
        realGridPane.add(completeButton, 0, 4, 1, 1);
        realGridPane.add(cancelButton, 5, 4, 1, 1);
//gridPane.add(datePicker, 1, 2, 1, 1);
       //gridPane.add(urlLabel, 0, 3, 1, 1);
       // gridPane.add(urlTextField, 1, 3, 1, 1);
//        playerToEdit.setContract(contractComboBox.getSelectionModel().getSelectedItem().toString());
        
        gridPane.setTop(headingLabel);
        gridPane.setLeft(playerImageView);
        gridPane.setRight(rightVBox);
        gridPane.setBottom(realGridPane);
        VBox rightBox=new VBox();
        
        
        
        
        player.setFirstName(playerToEdit.getFirstName());
        player.setLastName(playerToEdit.getLastName());
        player.setTeamName(playerToEdit.getTeamName());
       // if(playerToEdit.getContract().equals("S2")){
        playerToEdit.setPick(i);
        i++;//}
        
        // AND PUT THE GRID PANE IN THE WINDOW
        dialogScene = new Scene(gridPane);
        dialogScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
        this.setScene(dialogScene);
        // AND THEN INTO OUR GUI
        loadGUIData();
               
        // AND OPEN IT UP
        this.showAndWait();
    }
    
    public void showEditPlayerDialog1(Pitchers playerToEdit) {
        // SET THE DIALOG TITLE
        setTitle(EDIT_SCHEDULE_ITEM_TITLE);
        
        // LOAD THE SCHEDULE ITEM INTO OUR LOCAL OBJECT
        player1 = new Pitchers();
        // FIRST OUR CONTAINER
        gridPane = new BorderPane();
        gridPane.setPadding(new Insets(10, 20, 20, 20));
        // PUT THE HEADING IN THE GRID, NOTE THAT THE TEXT WILL DEPEND
        // ON WHETHER WE'RE ADDING OR EDITING
        headingLabel = new Label(PLAYER_HEADING);
        headingLabel.getStyleClass().add(CLASS_HEADING_LABEL);
        // NOW THE FIRSTNAME 
        firstNameLabel = new Label(playerToEdit.getFirstName());
        firstNameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        lastNameLabel = new Label(playerToEdit.getLastName());
        lastNameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        Label qpLabel=new Label("P");
        qpLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        Label fantasyTeamLabel= new Label("Fantasy Team:  ");
        Label positionLabel= new Label("Position:  ");
        Label contractLabel= new Label("Contract:  ");
        Label salaryLabel= new Label("Salary($):  ");
        String playerImagePath = "file:" + PATH_PLAYER_IMAGES + playerToEdit.getLastName()+playerToEdit.getFirstName()+".jpg";
        Image playerImage = new Image(playerImagePath);
        if(playerImage.isError()==true){
            playerImage = new Image("file:"+PATH_PLAYER_IMAGES+"AAA_PhotoMissing.jpg");
            System.out.println("cannot find image");
        }
        ImageView playerImageView=new ImageView(playerImage);
        
        
        String flagImagePath = "file:" + PATH_FLAG_IMAGES + playerToEdit.getNation_of_birth()+".png";
        Image flagImage = new Image(flagImagePath);
        ImageView flagImageView=new ImageView(flagImage);
        
        VBox rightVBox=new VBox();
        rightVBox.getChildren().addAll(flagImageView,firstNameLabel,lastNameLabel,qpLabel);
        rightVBox.setSpacing(10);
        fantasyTeamComboBox=new ComboBox(); positionComboBox=new ComboBox();contractComboBox=new ComboBox();salaryTextField=new TextField();
        salaryTextField.setMaxWidth(110); fantasyTeamComboBox.setMinWidth(110); positionComboBox.setMinWidth(110);contractComboBox.setMinWidth(110);
         positionComboBox.getItems().add("P");
        contractComboBox.getItems().addAll("S1","S2","X"); 
        fantasyTeamComboBox.setItems(options);
        salaryTextField.textProperty().addListener((observable, oldValue, newValue) -> {
           player1.setSalary(newValue);
        });
       //if(playerToEdit.getContract().equals("S2")){
        playerToEdit.setPick(i);
        i++;//}
// player.setContract(contractComboBox.getSelectionModel().getSelectedItem().toString());
        // AND FINALLY, THE BUTTONS
        completeButton = new Button(COMPLETE);
        cancelButton = new Button(CANCEL);
        
        // REGISTER EVENT HANDLERS FOR OUR BUTTONS
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
            PlayerDialog.this.selection = sourceButton.getText();
            PlayerDialog.this.hide();
        };
        completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);
        realGridPane=new GridPane();
        realGridPane.add(fantasyTeamLabel, 0, 0, 2, 1);
        realGridPane.add(fantasyTeamComboBox, 5, 0, 1, 1);
        realGridPane.add(positionLabel, 0, 1, 1, 1);
        realGridPane.add(positionComboBox, 5, 1, 1, 1);
        realGridPane.add(contractLabel, 0, 2, 1, 1);
        realGridPane.add(contractComboBox, 5, 2, 1, 1);
        realGridPane.add(salaryLabel, 0, 3, 1, 1);
        realGridPane.add(salaryTextField, 5, 3, 1, 1);
        realGridPane.add(completeButton, 0, 4, 1, 1);
        realGridPane.add(cancelButton, 5, 4, 1, 1);
        
        
        gridPane.setTop(headingLabel);
        gridPane.setLeft(playerImageView);
        gridPane.setRight(rightVBox);
        gridPane.setBottom(realGridPane);
        VBox rightBox=new VBox();
        
        player1.setFirstName(playerToEdit.getFirstName());
        player1.setLastName(playerToEdit.getLastName());
        player1.setTeamName(playerToEdit.getTeamName());
        
        
        // AND PUT THE GRID PANE IN THE WINDOW
        dialogScene = new Scene(gridPane);
        dialogScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
        this.setScene(dialogScene);
        // AND THEN INTO OUR GUI
        loadGUIData1();
               
        // AND OPEN IT UP
        this.showAndWait();
    }
    public ComboBox getContractComboBox(){
        return contractComboBox;
    }
    
    public Button getCompleteButton(){
        return completeButton;
    }
    public ComboBox getFantasyTeamComboBox(){
        return fantasyTeamComboBox;
    }
    public ComboBox getPositionComboBox(){
        return positionComboBox;
    }


}
