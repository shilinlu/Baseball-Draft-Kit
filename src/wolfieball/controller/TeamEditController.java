/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieball.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wolfieball.data.DraftDataManager;
import wolfieball.data.Team;
import wolfieball.gui.MessageDialog;
import wolfieball.gui.PlayerDialog;
import wolfieball.gui.TeamDialog;
import wolfieball.gui.Wolfie_GUI;
import wolfieball.gui.YesNoCancelDialog;

/**
 *
 * @author shilinlu
 */
public class TeamEditController {
    TeamDialog  sid;
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    ObservableList <Team> teams=FXCollections.observableArrayList();
     
    public TeamEditController(MessageDialog initMessageDialog, YesNoCancelDialog initYesNoCancelDialog) {
        sid = new TeamDialog(initMessageDialog);
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
    }

    // THESE ARE FOR SCHEDULE ITEMS
    
    public void handleAddTeamRequest(Wolfie_GUI gui) {
        DraftDataManager cdm = gui.getDataManager();
        //Team team = cdm.getTeam();
        sid.showAddTeamDialog();
        
        // DID THE USER CONFIRM?
        if (sid.wasCompleteSelected()) {
            // GET THE SCHEDULE ITEM
            Team si = (Team) sid.getTeam();
            
            // AND ADD IT AS A ROW TO THE TABLE
            teams.add(si);
            // THE COURSE IS NOW DIRTY, MEANING IT'S BEEN 
            // CHANGED SINCE IT WAS LAST SAVED, SO MAKE SURE
            // THE SAVE BUTTON IS ENABLED
            //gui.getFileController().markAsEdited(gui);
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }
    }

    public TeamDialog getTeamDialog(){
        return sid;
    }
     public void handleRemoveTeamRequest(Wolfie_GUI gui, Team itemToRemove) {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        //yesNoCancelDialog.show(PropertiesManager.getPropertiesManager().getProperty(REMOVE_ITEM_MESSAGE));
        
        // AND NOW GET THE USER'S SELECTION
        //String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN REMOVE IT
        //if (selection.equals(YesNoCancelDialog.YES)) { 
            gui.getDataManager().getTeams().remove(itemToRemove);
            
            // THE COURSE IS NOW DIRTY, MEANING IT'S BEEN 
            // CHANGED SINCE IT WAS LAST SAVED, SO MAKE SURE
            // THE SAVE BUTTON IS ENABLED
            //gui.getFileController().markAsEdited(gui);
        //}
    }
public void handleEditTeamRequest(Wolfie_GUI gui, Team itemToEdit) {
        DraftDataManager cdm = gui.getDataManager();
//        Team team = cdm.getTeam();
        sid.showEditTeamDialog(itemToEdit);
        
        // DID THE USER CONFIRM?
        if (sid.wasCompleteSelected()) {
            // UPDATE THE SCHEDULE ITEM
            Team si = (Team) sid.getTeam();
            
            itemToEdit.setName(si.getName());
            itemToEdit.setOwner(si.getOwner());
           
            //itemToEdit.setQp(si.getQp());
            // THE COURSE IS NOW DIRTY, MEANING IT'S BEEN 
            // CHANGED SINCE IT WAS LAST SAVED, SO MAKE SURE
            // THE SAVE BUTTON IS ENABLED
            //gui.getFileController().markAsEdited(gui);
        }
        else {
            // THE USER MUST HAVE PRESSED CANCEL, SO
            // WE DO NOTHING
        }        
    }





}
