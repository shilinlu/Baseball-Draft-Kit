/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieball.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import wolfieball.data.Hitters;
import wolfieball.data.Team;
import static wolfieball.gui.PlayerDialog.CANCEL;
import static wolfieball.gui.PlayerDialog.COMPLETE;
import static wolfieball.gui.PlayerDialog.PLAYER_HEADING;
import static wolfieball.gui.Wolfie_GUI.CLASS_HEADING_LABEL;
import static wolfieball.gui.Wolfie_GUI.CLASS_PROMPT_LABEL;
import static wolfieball.gui.Wolfie_GUI.PRIMARY_STYLE_SHEET;

/**
 *
 * @author shilinlu
 */
public class TeamDialog extends Stage{
    // THIS IS THE OBJECT DATA BEHIND THIS UI
    Team team;
    VBox outerBox;
    HBox fantasyTeamsDetailBox;
    HBox nameBox;
    HBox ownerBox;
    HBox completeCancelBox;
    Scene dialogScene;
    Label headingLabel;
    Label nameLabel;
    Label ownerLabel;
    TextField nameTextField;
    TextField ownerTextField;
    Button completeButton;
    Button cancelButton;
    // THIS IS FOR KEEPING TRACK OF WHICH BUTTON THE USER PRESSED
    String selection;
    public static final String COMPLETE = "Complete";
    public static final String CANCEL = "Cancel";
    public static final String NAME_PROMPT = "Name:   ";
    public static final String OWNER_PROMPT = "Owner:  ";
    public static final String TEAM_HEADING = "Fantasy Team Details";
    public static final String ADD_FANTASY_TEAM_TITLE = "Add New Fantasy Team";
    public static final String EDIT_FANTASY_TEAM_TITLE = "Edit Fantasy Team";

    public TeamDialog( MessageDialog messageDialog) {       
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
    
    public Team getTeam() { 
        return team;
    }
     /**
     * This method loads a custom message into the label and
     * then pops open the dialog.
     * 
     * @param message Message to appear inside the dialog.
     */
    public Team showAddTeamDialog() {
        // SET THE DIALOG TITLE
        setTitle(ADD_FANTASY_TEAM_TITLE);
        
        // RESET THE SCHEDULE ITEM OBJECT WITH DEFAULT VALUES
        team = new Team();
        // FIRST OUR CONTAINER
        outerBox = new VBox();
        outerBox.setPadding(new Insets(10, 20, 20, 20));
        headingLabel = new Label(TEAM_HEADING);
        headingLabel.getStyleClass().add(CLASS_HEADING_LABEL);
        // NOW THE NAME 
        nameLabel = new Label(NAME_PROMPT);
        nameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        nameTextField = new TextField();
        nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            team.setName(newValue);
        });
        // NOW THE OWNER 
        ownerLabel = new Label(OWNER_PROMPT);
        ownerLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        ownerTextField = new TextField();
        ownerTextField.textProperty().addListener((observable, oldValue, newValue) -> {
           team.setOwner(newValue);
        });
        // AND FINALLY, THE BUTTONS
        completeButton = new Button(COMPLETE);
        cancelButton = new Button(CANCEL);
        
        // REGISTER EVENT HANDLERS FOR OUR BUTTONS
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
           TeamDialog.this.selection = sourceButton.getText();
           TeamDialog.this.hide();
        };
        completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);
        fantasyTeamsDetailBox=new HBox();
        nameBox=new HBox();
        ownerBox=new HBox();
        completeCancelBox=new HBox();
        fantasyTeamsDetailBox.getChildren().add(headingLabel);
        nameBox.getChildren().addAll(nameLabel,nameTextField);
        ownerBox.getChildren().addAll(ownerLabel,ownerTextField);
        completeCancelBox.getChildren().addAll(completeButton,cancelButton);
        completeCancelBox.setSpacing(30);
        outerBox.getChildren().addAll(fantasyTeamsDetailBox,nameBox,ownerBox,completeCancelBox);
        // AND PUT THE GRID PANE IN THE WINDOW
        dialogScene = new Scene(outerBox);
        dialogScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
        this.setScene(dialogScene);
        
        
        // AND OPEN IT UP
        this.showAndWait();
        return team;
    }
    public Team showEditTeamDialog(Team teamToEdit) {
        // SET THE DIALOG TITLE
        setTitle(EDIT_FANTASY_TEAM_TITLE);
        
        // RESET THE SCHEDULE ITEM OBJECT WITH DEFAULT VALUES
        team = new Team();
        // FIRST OUR CONTAINER
        outerBox = new VBox();
        outerBox.setPadding(new Insets(10, 20, 20, 20));
        headingLabel = new Label(PLAYER_HEADING);
        headingLabel.getStyleClass().add(CLASS_HEADING_LABEL);
        // NOW THE NAME 
        nameLabel = new Label(NAME_PROMPT);
        nameLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        nameTextField = new TextField();
        nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            teamToEdit.setName(newValue);
        });
        // NOW THE OWNER 
        ownerLabel = new Label(OWNER_PROMPT);
        ownerLabel.getStyleClass().add(CLASS_PROMPT_LABEL);
        ownerTextField = new TextField();
        ownerTextField.textProperty().addListener((observable, oldValue, newValue) -> {
           teamToEdit.setOwner(newValue);
        });
        // AND FINALLY, THE BUTTONS
        completeButton = new Button(COMPLETE);
        cancelButton = new Button(CANCEL);
        
        // REGISTER EVENT HANDLERS FOR OUR BUTTONS
        EventHandler completeCancelHandler = (EventHandler<ActionEvent>) (ActionEvent ae) -> {
            Button sourceButton = (Button)ae.getSource();
           TeamDialog.this.selection = sourceButton.getText();
           TeamDialog.this.hide();
        };
        completeButton.setOnAction(completeCancelHandler);
        cancelButton.setOnAction(completeCancelHandler);
        fantasyTeamsDetailBox=new HBox();
        nameBox=new HBox();
        ownerBox=new HBox();
        completeCancelBox=new HBox();
        fantasyTeamsDetailBox.getChildren().add(headingLabel);
        nameBox.getChildren().addAll(nameLabel,nameTextField);
        ownerBox.getChildren().addAll(ownerLabel,ownerTextField);
        completeCancelBox.getChildren().addAll(completeButton,cancelButton);
        completeCancelBox.setSpacing(30);
        outerBox.getChildren().addAll(fantasyTeamsDetailBox,nameBox,ownerBox,completeCancelBox);
        // AND PUT THE GRID PANE IN THE WINDOW
        dialogScene = new Scene(outerBox);
        dialogScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
        this.setScene(dialogScene);
        team.setName(teamToEdit.getName());
        team.setOwner(teamToEdit.getOwner());
// AND OPEN IT UP
        this.showAndWait();
        return teamToEdit;
    }
    
    
    
    public boolean wasCompleteSelected() {
        return selection.equals(COMPLETE);
        //return true;
    }
    public TextField getNameTextField(){
        return nameTextField;
    }
}
