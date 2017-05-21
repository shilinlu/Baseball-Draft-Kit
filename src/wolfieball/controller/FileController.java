/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieball.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.stage.FileChooser;
import properties_manager.PropertiesManager;
import wolfieball.data.DraftDataManager;
import wolfieball.data.Team;
import wolfieball.error.ErrorHandler;
import wolfieball.file.DraftFileManager;
import wolfieball.file.DraftSiteExporter;
import wolfieball.file.JsonDraftFileManager;
import wolfieball.gui.AvailablePlayerScreen;
import wolfieball.gui.MessageDialog;
import wolfieball.gui.Wolfie_GUI;
import wolfieball.gui.YesNoCancelDialog;
import static wolfieball_draft_kit.PropertyType.DRAFT_SAVED_MESSAGE;
import static wolfieball_draft_kit.PropertyType.NEW_DRAFT_CREATED_MESSAGE;
import static wolfieball_draft_kit.PropertyType.SAVE_UNSAVED_WORK_MESSAGE;
import static wolfieball_draft_kit.StartupConstants.JSON_FILE_HITTERS;
import static wolfieball_draft_kit.StartupConstants.JSON_FILE_PITCHERS;
import static wolfieball_draft_kit.StartupConstants.PATH_DATA;
import static wolfieball_draft_kit.StartupConstants.PATH_DRAFT;

/**
 *
 * @author shilinlu
 */
public class FileController {
    // WE WANT TO KEEP TRACK OF WHEN SOMETHING HAS NOT BEEN SAVED
    private boolean saved;
    // THIS GUY KNOWS HOW TO EXPORT COURSE SCHEDULE PAGES
    private DraftSiteExporter exporter;
    // THIS WILL PROVIDE FEEDBACK TO THE USER WHEN SOMETHING GOES WRONG
    ErrorHandler errorHandler;
    
    // THIS GUY KNOWS HOW TO READ AND WRITE COURSE DATA
    private DraftFileManager draftIO;

    // THIS WILL PROVIDE FEEDBACK TO THE USER AFTER
    // WORK BY THIS CLASS HAS COMPLETED
    MessageDialog messageDialog;
    
    // AND WE'LL USE THIS TO ASK YES/NO/CANCEL QUESTIONS
    YesNoCancelDialog yesNoCancelDialog;
    
    // WE'LL USE THIS TO GET OUR VERIFICATION FEEDBACK
    PropertiesManager properties;
    JsonDraftFileManager jsonDraftFileManager= new JsonDraftFileManager();
    File selectedFile;
    /**
     * This default constructor starts the program without a course file being
     * edited.
     *
     * @param primaryStage The primary window for this application, which we
     * need to set as the owner for our dialogs.
     * @param initCourseIO The object that will be reading and writing course
     * data.
     * @param initExporter The object that will be exporting courses to Web
     * sites.
     */
    public FileController(
            MessageDialog initMessageDialog,
            YesNoCancelDialog initYesNoCancelDialog,
            DraftFileManager initCourseIO,
            DraftSiteExporter initExporter) {
        // NOTHING YET
        saved = true;
        
        // KEEP THESE GUYS FOR LATER
        
        exporter = initExporter;
        
        // BE READY FOR ERRORS
        errorHandler = ErrorHandler.getErrorHandler();
        
        // AND GET READY TO PROVIDE FEEDBACK
        messageDialog = initMessageDialog;
        yesNoCancelDialog = initYesNoCancelDialog;
        
        properties = PropertiesManager.getPropertiesManager();
    
    
    
    }
    /**
     * This method marks the appropriate variable such that we know
     * that the current Course has been edited since it's been saved.
     * The UI is then updated to reflect this.
     * 
     * @param gui The user interface editing the Course.
     */
    public void markAsEdited(Wolfie_GUI gui) {
        // THE Course OBJECT IS NOW DIRTY
        saved = false;
        
        // LET THE UI KNOW
        gui.updateToolbarControls(saved);
    }
    
    
    /**
     * This method starts the process of editing a new Course. If a course is
     * already being edited, it will prompt the user to save it first.
     * 
     * @param gui The user interface editing the Course.
     */
    public void handleNewCourseRequest(Wolfie_GUI gui) {
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToMakeNew = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE WITH A CANCEL
                continueToMakeNew = promptToSave(gui);
            }

            // IF THE USER REALLY WANTS TO MAKE A NEW COURSE
            if (continueToMakeNew) {
                // RESET THE DATA, WHICH SHOULD TRIGGER A RESET OF THE UI
                DraftDataManager dataManager = gui.getDataManager();
                //dataManager.reset();
                gui.activateWorkspace();
               
                saved = false;

                // REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
                // THE APPROPRIATE CONTROLS
                gui.updateToolbarControls(saved);

                // TELL THE USER THE COURSE HAS BEEN CREATED
                messageDialog.show(properties.getProperty(NEW_DRAFT_CREATED_MESSAGE));
            }
        } catch (IOException ioe) {
            // SOMETHING WENT WRONG, PROVIDE FEEDBACK
            errorHandler.handleNewDraftError();
        }
    }
    /**
     * This method will exit the application, making sure the user doesn't lose
     * any data first.
     * 
     * @param gui
     */
    public void handleExitRequest(Wolfie_GUI gui) {
        try {
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToExit = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE
                continueToExit = promptToSave(gui);
            }

            // IF THE USER REALLY WANTS TO EXIT THE APP
            if (continueToExit) {
                // EXIT THE APPLICATION
                System.exit(0);
            }
        } catch (IOException ioe) {
            ErrorHandler eH = ErrorHandler.getErrorHandler();
            eH.handleExitError();
        }
    }
    private boolean promptToSave(Wolfie_GUI gui) throws IOException {
        // PROMPT THE USER TO SAVE UNSAVED WORK
        yesNoCancelDialog.show(properties.getProperty(SAVE_UNSAVED_WORK_MESSAGE));
        
        // AND NOW GET THE USER'S SELECTION
        String selection = yesNoCancelDialog.getSelection();

        // IF THE USER SAID YES, THEN SAVE BEFORE MOVING ON
        if (selection.equals(YesNoCancelDialog.YES)) {
            // SAVE THE COURSE
            DraftDataManager dataManager = gui.getDataManager();
            draftIO.saveDraft(dataManager.getTeam());
            saved = true;
            
        } // IF THE USER SAID CANCEL, THEN WE'LL TELL WHOEVER
        // CALLED THIS THAT THE USER IS NOT INTERESTED ANYMORE
        else if (selection.equals(YesNoCancelDialog.CANCEL)) {
            return false;
        }

        // IF THE USER SAID NO, WE JUST GO ON WITHOUT SAVING
        // BUT FOR BOTH YES AND NO WE DO WHATEVER THE USER
        // HAD IN MIND IN THE FIRST PLACE
        return true;
    
    
    
    
 }
    /**
     * This mutator method marks the file as not saved, which means that when
     * the user wants to do a file-type operation, we should prompt the user to
     * save current work first. Note that this method should be called any time
     * the course is changed in some way.
     */
    public void markFileAsNotSaved() {
        saved = false;
    }

    /**
     * Accessor method for checking to see if the current course has been saved
     * since it was last edited.
     *
     * @return true if the current course is saved to the file, false otherwise.
     */
    public boolean isSaved() {
        return saved;
    }

private void promptToOpen(Wolfie_GUI gui,Team teamToLoad) {
        // AND NOW ASK THE USER FOR THE COURSE TO OPEN
        FileChooser courseFileChooser = new FileChooser();
        courseFileChooser.setInitialDirectory(new File(PATH_DRAFT));
        selectedFile = courseFileChooser.showOpenDialog(gui.getWindow());

        // ONLY OPEN A NEW FILE IF THE USER SAYS OK
        if (selectedFile != null) {
            try {
                System.out.println("opened the file");
                //Team courseToLoad = gui.getDataManager().getTeam();
                jsonDraftFileManager.loadDraft(teamToLoad, selectedFile.getAbsolutePath());
                System.out.println("opened the json");
                gui.reloadCourse(teamToLoad);
                saved = true;
                gui.updateToolbarControls(saved);
               
            } catch (Exception e) {
                ErrorHandler eH = ErrorHandler.getErrorHandler();
                eH.handleLoadDraftError();
            }
        }
        //return teamToLoad;
    }
    public void handleLoadCourseRequest(Wolfie_GUI gui,Team teamToLoad) throws IOException {
        
            // WE MAY HAVE TO SAVE CURRENT WORK
            boolean continueToOpen = true;
            if (!saved) {
                // THE USER CAN OPT OUT HERE WITH A CANCEL
                continueToOpen = promptToSave(gui);
            }

            // IF THE USER REALLY WANTS TO OPEN A Course
            if (continueToOpen) {
                // GO AHEAD AND PROCEED LOADING A Course
                System.out.println("b4 promptoopen");
                promptToOpen(gui,teamToLoad);
            }
        
            // SOMETHING WENT WRONG
            //errorHandler.handleLoadDraftError();
        
    }
    public void handleSaveCourseRequest(Wolfie_GUI gui, Team teamToSave) {
        try {
            // SAVE IT TO A FILE
            jsonDraftFileManager.saveDraft(teamToSave);

            // MARK IT AS SAVED
            saved = true;

            // TELL THE USER THE FILE HAS BEEN SAVED
            messageDialog.show(properties.getProperty(DRAFT_SAVED_MESSAGE));

            // AND REFRESH THE GUI, WHICH WILL ENABLE AND DISABLE
            // THE APPROPRIATE CONTROLS
            //gui.updateToolbarControls(saved);
        } catch (IOException ioe) {
            errorHandler.handleSaveDraftError();
        }
    }
    public File getSelectedFile(){
        return selectedFile;
    }

}
