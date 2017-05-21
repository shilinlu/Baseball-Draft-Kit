/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieball_draft_kit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import wolfieball.data.DraftDataManager;
import wolfieball.data.Hitters;
import wolfieball.data.Pitchers;
import wolfieball.error.ErrorHandler;
import wolfieball.file.DraftSiteExporter;
import wolfieball.file.JsonDraftFileManager;
import wolfieball.gui.Wolfie_GUI;
import static wolfieball_draft_kit.PropertyType.PROP_APP_TITLE;
import static wolfieball_draft_kit.StartupConstants.JSON_FILE_HITTERS;
import static wolfieball_draft_kit.StartupConstants.JSON_FILE_PITCHERS;
import static wolfieball_draft_kit.StartupConstants.PATH_BASE;
import static wolfieball_draft_kit.StartupConstants.PATH_DATA;
import static wolfieball_draft_kit.StartupConstants.PATH_SITES;
import static wolfieball_draft_kit.StartupConstants.PROPERTIES_FILE_NAME;
import static wolfieball_draft_kit.StartupConstants.PROPERTIES_SCHEMA_FILE_NAME;
import xml_utilities.InvalidXMLFileFormatException;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 *
 * @author shilinlu
 */
public class Wolfieball_Draft_Kit extends Application {
    // THIS IS THE FULL USER INTERFACE, WHICH WILL BE INITIALIZED
    // AFTER THE PROPERTIES FILE IS LOADED
   Wolfie_GUI gui;
    
   @Override
    public void start(Stage primaryStage) {
        ErrorHandler eH = ErrorHandler.getErrorHandler();
        eH.initMessageDialog(primaryStage);
        
        // LOAD APP SETTINGS INTO THE GUI AND START IT UP
        boolean success = loadProperties();
        if (success) {
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String appTitle = props.getProperty(PROP_APP_TITLE);
            try {                
                // WE WILL SAVE OUR COURSE DATA USING THE JSON FILE
                // FORMAT SO WE'LL LET THIS OBJECT DO THIS FOR US
               // JsonDraftFileManager jsonFileManager = new JsonDraftFileManager();
                
                // AND THIS ONE WILL DO THE COURSE WEB PAGE EXPORTING
                DraftSiteExporter exporter = new DraftSiteExporter(PATH_BASE, PATH_SITES);
                
               // ArrayList<String> pitchers = jsonFileManager.loadPitchers(JSON_FILE_PITCHERS);
                
                //ObservableList<Hitters> hitters= jsonFileManager.loadHitters(JSON_FILE_HITTERS);
                                
                // AND NOW GIVE ALL OF THIS STUFF TO THE GUI
                // INITIALIZE THE USER INTERFACE COMPONENTS
                gui = new Wolfie_GUI(primaryStage);
                //gui.getAvailablePlayerScreen().setDraftFileManager(jsonFileManager);
                gui.setSiteExporter(exporter);
                
                // CONSTRUCT THE DATA MANAGER AND GIVE IT TO THE GUI
                DraftDataManager dataManager = new DraftDataManager(gui.getAvailablePlayerScreen()); 
                gui.getAvailablePlayerScreen().setDataManager(dataManager);
                
                // FINALLY, START UP THE USER INTERFACE WINDOW AFTER ALL
                // REMAINING INITIALIZATION
                gui.initGUI(appTitle);                
                
                //gui.getAvailablePlayerScreen().getFirstNameColumn().setCellValueFactory(new PropertyValueFactory<Hitters, String>("FIRST_NAME"));
               //gui.getAvailablePlayerScreen().getPlayerTable().setItems(hitters);
                //System.out.println(hitters.get(0).getNation_of_birth());
            }
            catch(IOException ioe) {
                eH = ErrorHandler.getErrorHandler();
                eH.handlePropertiesFileError();
            }
        }
    }
    
    /**
     * Loads this application's properties file, which has a number of settings
     * for initializing the user interface.
     * 
     * @return true if the properties file was loaded successfully, false otherwise.
     */
    public boolean loadProperties() {
        try {
            // LOAD THE SETTINGS FOR STARTING THE APP
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            props.addProperty(PropertiesManager.DATA_PATH_PROPERTY, PATH_DATA);
            props.loadProperties(PROPERTIES_FILE_NAME, PROPERTIES_SCHEMA_FILE_NAME);
            return true;
       } catch (InvalidXMLFileFormatException ixmlffe) {
            // SOMETHING WENT WRONG INITIALIZING THE XML FILE
            ErrorHandler eH = ErrorHandler.getErrorHandler();
            eH.handlePropertiesFileError();
            return false;
        }        
    }

    /**
     * This is where program execution begins. Since this is a JavaFX app
     * it will simply call launch, which gets JavaFX rolling, resulting in
     * sending the properly initialized Stage (i.e. window) to our start
     * method in this class.
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        launch(args);
    }
    
}
