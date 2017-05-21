/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieball.controller;

import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import wolfieball.data.DraftDataManager;
import wolfieball.data.Hitters;
import wolfieball.data.Pitchers;
import wolfieball.data.Team;
import wolfieball.gui.AvailablePlayerScreen;
import wolfieball.gui.DraftSummaryScreen;
import wolfieball.gui.MessageDialog;
import wolfieball.gui.PlayerDialog;
import wolfieball.gui.TeamDialog;
import wolfieball.gui.YesNoCancelDialog;
import static wolfieball_draft_kit.PropertyType.REMOVE_ITEM_MESSAGE;

/**
 *
 * @author shilinlu
 */
public class PlayerEditController {
    PlayerDialog  sid;
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    
     
    
    public PlayerEditController( Team team, MessageDialog initMessageDialog, YesNoCancelDialog initYesNoCancelDialog) {
       sid = new PlayerDialog(team, initMessageDialog);
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
    }
    // THESE ARE FOR SCHEDULE ITEMS
    
    public void handleAddPlayerRequest(AvailablePlayerScreen gui) {
        DraftDataManager cdm = gui.getDataManager();
        Team team = cdm.getTeam();
        sid.showAddPlayerDialog();
        
        // DID THE USER CONFIRM?
        if (sid.wasCompleteSelected()) {
            // GET THE SCHEDULE ITEM
            Hitters si = (Hitters) sid.getPlayer();
            
            // AND ADD IT AS A ROW TO THE TABLE
            team.addHitters(si);

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
    
    public void handleEditPlayerRequest(AvailablePlayerScreen gui, Hitters itemToEdit) {
        DraftDataManager cdm = gui.getDataManager();
        Team team = cdm.getTeam();
        sid.showEditPlayerDialog(itemToEdit);
        
        // DID THE USER CONFIRM?
        if (sid.wasCompleteSelected()) {
            // UPDATE THE SCHEDULE ITEM
            Hitters si = (Hitters) sid.getPlayer();
           // si.setContract(sid.getContractComboBox().getSelectionModel().getSelectedItem().toString());
            itemToEdit.setContract(sid.getContractComboBox().getSelectionModel().getSelectedItem().toString());
            itemToEdit.setSalary(si.getSalary());
            itemToEdit.setQp(sid.getPositionComboBox().getSelectionModel().getSelectedItem().toString());
            itemToEdit.setFirstName(si.getFirstName());
            itemToEdit.setLastName(si.getLastName());
           itemToEdit.setTeamName(si.getTeamName());
            //itemToEdit.setContract(si.getContract());
            //sid.getFantasyTeamComboBox().getItems().add(gui.);
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
    public void handleEditPlayerRequestDraft1(DraftSummaryScreen gui, Pitchers itemToEdit) {
        DraftDataManager cdm = gui.getDataManager();
//        Team team = cdm.getTeam();
        sid.showEditPlayerDialog1(itemToEdit);
        
        // DID THE USER CONFIRM?
        if (sid.wasCompleteSelected()) {
            // UPDATE THE SCHEDULE ITEM
            Pitchers si = (Pitchers) sid.getPlayer1();
           // si.setContract(sid.getContractComboBox().getSelectionModel().getSelectedItem().toString());
            itemToEdit.setContract(sid.getContractComboBox().getSelectionModel().getSelectedItem().toString());
            itemToEdit.setSalary(si.getSalary());
            itemToEdit.setQp(sid.getPositionComboBox().getSelectionModel().getSelectedItem().toString());
            itemToEdit.setFirstName(si.getFirstName());
            itemToEdit.setLastName(si.getLastName());
           itemToEdit.setTeamName(si.getTeamName());
            //itemToEdit.setContract(si.getContract());
            //sid.getFantasyTeamComboBox().getItems().add(gui.);
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
    public void handleEditPlayerRequestDraft(DraftSummaryScreen gui, Hitters itemToEdit) {
        DraftDataManager cdm = gui.getDataManager();
//        Team team = cdm.getTeam();
        sid.showEditPlayerDialog(itemToEdit);
        
        // DID THE USER CONFIRM?
        if (sid.wasCompleteSelected()) {
            // UPDATE THE SCHEDULE ITEM
            Hitters si = (Hitters) sid.getPlayer();
           // si.setContract(sid.getContractComboBox().getSelectionModel().getSelectedItem().toString());
            itemToEdit.setContract(sid.getContractComboBox().getSelectionModel().getSelectedItem().toString());
            itemToEdit.setSalary(si.getSalary());
            itemToEdit.setQp(sid.getPositionComboBox().getSelectionModel().getSelectedItem().toString());
            itemToEdit.setFirstName(si.getFirstName());
            itemToEdit.setLastName(si.getLastName());
           itemToEdit.setTeamName(si.getTeamName());
            //itemToEdit.setContract(si.getContract());
            //sid.getFantasyTeamComboBox().getItems().add(gui.);
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
    public void handleEditPlayerRequest1(AvailablePlayerScreen gui, Pitchers itemToEdit) {
        DraftDataManager cdm = gui.getDataManager();
        Team team = cdm.getTeam();
        sid.showEditPlayerDialog1(itemToEdit);
        
        // DID THE USER CONFIRM?
        if (sid.wasCompleteSelected()) {
            // UPDATE THE SCHEDULE ITEM
            Pitchers si = (Pitchers) sid.getPlayer1();
             itemToEdit.setContract(sid.getContractComboBox().getSelectionModel().getSelectedItem().toString());
            itemToEdit.setSalary(si.getSalary());
            itemToEdit.setQp("P");
            itemToEdit.setFirstName(si.getFirstName());
            itemToEdit.setLastName(si.getLastName());
           itemToEdit.setTeamName(si.getTeamName());
            
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
    
    public void handleRemovePlayerRequest(AvailablePlayerScreen gui, Object itemToRemove) {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        //yesNoCancelDialog.show(PropertiesManager.getPropertiesManager().getProperty(REMOVE_ITEM_MESSAGE));
        
        // AND NOW GET THE USER'S SELECTION
        //String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN REMOVE IT
        //if (selection.equals(YesNoCancelDialog.YES)) { 
            gui.getDataManager().getTeam().removePlayer(itemToRemove);
            
            // THE COURSE IS NOW DIRTY, MEANING IT'S BEEN 
            // CHANGED SINCE IT WAS LAST SAVED, SO MAKE SURE
            // THE SAVE BUTTON IS ENABLED
            //gui.getFileController().markAsEdited(gui);
        //}
    }

    public PlayerDialog getPlayerDialog(){
        return sid;
    }

}
