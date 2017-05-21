/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieball.gui;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import wolfieball.controller.DraftEditController;
import wolfieball.controller.FileController;
import wolfieball.controller.TeamEditController;
import wolfieball.data.DraftDataManager;
import wolfieball.data.DraftDataView;
import wolfieball.data.Hitters;
import wolfieball.data.Pitchers;
import wolfieball.data.Team;
import wolfieball.file.DraftFileManager;
import wolfieball.file.DraftSiteExporter;
import wolfieball.file.JsonDraftFileManager;
import static wolfieball.gui.AvailablePlayerScreen.CLASS_BORDERED_PANE;
import static wolfieball.gui.AvailablePlayerScreen.CLASS_SUBHEADING_LABEL;
import wolfieball_draft_kit.PropertyType;
import static wolfieball_draft_kit.StartupConstants.CLOSE_BUTTON_LABEL;
import static wolfieball_draft_kit.StartupConstants.PATH_CSS;
import static wolfieball_draft_kit.StartupConstants.PATH_DRAFT;
import static wolfieball_draft_kit.StartupConstants.PATH_IMAGES;

/**
 *
 * @author shilinlu
 */
public class Wolfie_GUI implements DraftDataView{
    // THESE CONSTANTS ARE FOR TYING THE PRESENTATION STYLE OF
    // THIS GUI'S COMPONENTS TO A STYLE SHEET THAT IT USES
    
    static final String PRIMARY_STYLE_SHEET = PATH_CSS + "csb_style.css";
    static final String CLASS_BORDERED_PANE = "bordered_pane";
    static final String CLASS_SUBJECT_PANE = "subject_pane";
    static final String CLASS_HEADING_LABEL = "heading_label";
    static final String CLASS_SUBHEADING_LABEL = "subheading_label";
    static final String CLASS_PROMPT_LABEL = "prompt_label";
    static final String EMPTY_TEXT = "";
    static final int LARGE_TEXT_FIELD_LENGTH = 20;
    static final int SMALL_TEXT_FIELD_LENGTH = 5;
    SplitPane splitScheduleInfoPane;
     GridPane draftInfoPane;
     // THIS HANDLES INTERACTIONS WITH COURSE INFO CONTROLS
    DraftEditController draftController;
    FantasyStandingScreen fantasyStandingScreen= new FantasyStandingScreen();;
    DraftSummaryScreen draftSummaryScreen= new DraftSummaryScreen();
    MLBTeamScreen mlbTeamScreen= new MLBTeamScreen();;
    AvailablePlayerScreen availablePlayerScreen= new AvailablePlayerScreen();;
    
// THIS MANAGES ALL OF THE APPLICATION'S DATA
    DraftDataManager dataManager;
    DraftFileManager fileManager;
// THIS IS THE APPLICATION WINDOW
    Stage primaryStage;

    // THIS IS THE STAGE'S SCENE GRAPH
    Scene primaryScene;

    // THIS PANE ORGANIZES THE BIG PICTURE CONTAINERS FOR THE
    // APPLICATION GUI
    BorderPane csbPane;
    
    // THIS IS THE TOP TOOLBAR AND ITS CONTROLS
    FlowPane fileToolbarPane;
    Button newDraftButton;
    Button loadDraftButton;
    Button saveDraftButton;
    Button exportDraftButton;
    Button exitButton;

    // THIS IS THE BOTTOM TOOLBAR AND ITS CONTROLS
    FlowPane screenToolbarPane;
    Button availablePlayersButton;
    Button draftSummaryButton;
    Button fantasyStandingButton;
    Button fantasyTeamsButton;
    Button mlbTeamButton;


// WE'LL ORGANIZE OUR WORKSPACE COMPONENTS USING A BORDER PANE
    BorderPane workspacePane;
    boolean workspaceActivated;
    
    // WE'LL PUT THE WORKSPACE INSIDE A SCROLL PANE
    ScrollPane workspaceScrollPane;

    // WE'LL PUT THIS IN THE TOP OF THE WORKSPACE, IT WILL
    // HOLD TWO OTHER PANES FULL OF CONTROLS AS WELL AS A LABEL
    VBox topWorkspacePane;
    Label courseHeadingLabel;
    SplitPane topWorkspaceSplitPane;

    
    // THIS REGION IS FOR MANAGING FANTASY TEAMS 
    VBox fantasyTeamItemsBox;
    HBox fantasyTeamItemsToolbar;
    Button addfantasyTeamItemButton;
    Button removefantasyTeamItemButton;
    Button editfantasyTeamItemButton;
    Label fantasyTeamItemsLabel;
    TableView<Hitters> fantasyTeamsTable;
    TableColumn itemDescriptionsColumn;
    TableColumn itemDatesColumn;
    TableColumn linkColumn;
    HBox addDeleteBox;
    // SCHEDULE CONTROLS
    VBox fantasyTeamsPane;
    VBox fantasyTeamsInfoPane;
    Label fantasyTeamsInfoHeadingLabel;
    SplitPane splitFantasyTeamsInfoPane;

    // AND TABLE COLUMNS
    static final String COL_DESCRIPTION = "Description";
    static final String COL_DATE = "Date";
    static final String COL_LINK = "Link";
    static final String COL_TOPIC = "Topic";
    static final String COL_SESSIONS = "Number of Sessions";
    static final String COL_NAME = "Name";
    static final String COL_TOPICS = "Topics";

    // HERE ARE OUR DIALOGS
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    // THIS MANAGES EXPORTING OUR SITE PAGES
    DraftSiteExporter siteExporter;

    // THIS HANDLES INTERACTIONS WITH FILE-RELATED CONTROLS
    FileController fileController;
    HBox draftNameBox;
    ComboBox fantasyTeamComboBox;
    HBox startingLineUpBox;
    HBox taxiSquadBox;
    TeamEditController teamController;
    ObservableList <Team> teams;
    ObservableList <String> teamNames;
    TableView startingLineUpTable;
    Team newTeam;
    TableView taxiSquadTable;
    ObservableList <Object> randomDraftedPlayer=FXCollections.observableArrayList();
    int i=1; int whichTeamCounter=0; int playersNeeded=23; int moneyLeft=260;
    Task<Void> task ;
   ObservableList<Team> listOfTeams=FXCollections.observableArrayList();
    public Wolfie_GUI(Stage initPrimaryStage) {
        primaryStage = initPrimaryStage;
    }

    /**
     * Accessor method for the window (i.e. stage).
     *
     * @return The window (i.e. Stage) used by this UI.
     */
    public Stage getWindow() {
        return primaryStage;
    }
    
    public MessageDialog getMessageDialog() {
        return messageDialog;
    }
    
    public YesNoCancelDialog getYesNoCancelDialog() {
        return yesNoCancelDialog;
    }

    public void initGUI(String windowTitle) throws IOException {
        // INIT THE DIALOGS
        
        initDialogs();
        
        // INIT THE TOOLBAR
        initFileToolbar();
       
        initScreenToolbar();
        // INIT THE CENTER WORKSPACE CONTROLS BUT DON'T ADD THEM
        // TO THE WINDOW YET
        initWorkspace();
        initEventHandlers();
        // NOW SETUP THE EVENT HANDLERS
        
       
        // AND FINALLY START UP THE WINDOW (WITHOUT THE WORKSPACE)
        initWindow(windowTitle);
     
    }
    private void initDialogs() {
        messageDialog = new MessageDialog(primaryStage, CLOSE_BUTTON_LABEL);
        yesNoCancelDialog = new YesNoCancelDialog(primaryStage);
        //progressDialog = new ProgressDialog(primaryStage, "Preparing");
    }
    private void initFileToolbar() {
        fileToolbarPane = new FlowPane();

        // HERE ARE OUR FILE TOOLBAR BUTTONS, NOTE THAT SOME WILL
        // START AS ENABLED (false), WHILE OTHERS DISABLED (true)
        newDraftButton = initChildButton(fileToolbarPane, PropertyType.NEW_DRAFT_ICON, PropertyType.NEW_DRAFT_TOOLTIP, false);
        loadDraftButton = initChildButton(fileToolbarPane, PropertyType.LOAD_DRAFT_ICON, PropertyType.LOAD_DRAFT_TOOLTIP, false);
        saveDraftButton = initChildButton(fileToolbarPane, PropertyType.SAVE_DRAFT_ICON, PropertyType.SAVE_DRAFT_TOOLTIP, true);
        exportDraftButton = initChildButton(fileToolbarPane, PropertyType.EXPORT_DRAFT_ICON, PropertyType.EXPORT_DRAFT_TOOLTIP, true);
        exitButton = initChildButton(fileToolbarPane, PropertyType.EXIT_ICON, PropertyType.EXIT_TOOLTIP, false);
    }
    private void initScreenToolbar() {
        screenToolbarPane = new FlowPane();

        // HERE ARE OUR FILE TOOLBAR BUTTONS, NOTE THAT SOME WILL
        // START AS ENABLED (false), WHILE OTHERS DISABLED (true)
        fantasyTeamsButton = initChildButton(screenToolbarPane, PropertyType.FANTASY_TEAMS_ICON, PropertyType.FANTASY_TEAMS_TOOLTIP, false);
        availablePlayersButton = initChildButton(screenToolbarPane, PropertyType.AVAILABLE_PLAYERS_ICON, PropertyType.AVAILABLE_PLAYERS_TOOLTIP, false);
        fantasyStandingButton = initChildButton(screenToolbarPane, PropertyType.FANTASY_STANDING_ICON, PropertyType.FANTASY_STANDING_TOOLTIP, false);
        draftSummaryButton = initChildButton(screenToolbarPane, PropertyType.DRAFT_SUMMARY_ICON, PropertyType.DRAFT_SUMMARY_TOOLTIP, false);
        mlbTeamButton = initChildButton(screenToolbarPane, PropertyType.MLB_TEAM_ICON, PropertyType.MLB_TEAM_TOOLTIP, false);
    } 


    // INIT A BUTTON AND ADD IT TO A CONTAINER IN A TOOLBAR
    private Button initChildButton(Pane toolbar, PropertyType icon, PropertyType tooltip, boolean disabled) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String imagePath = "file:" + PATH_IMAGES + props.getProperty(icon.toString());
        Image buttonImage = new Image(imagePath);
        Button button = new Button();
        button.setDisable(disabled);
        button.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip.toString()));
        button.setTooltip(buttonTooltip);
        toolbar.getChildren().add(button);
        return button;
    }
    // INIT A LABEL AND SET IT'S STYLESHEET CLASS
    private Label initLabel(PropertyType labelProperty, String styleClass) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String labelText = props.getProperty(labelProperty);
        Label label = new Label(labelText);
        label.getStyleClass().add(styleClass);
        return label;
    }
    private Label initChildLabel(Pane container, PropertyType labelProperty, String styleClass) {
        Label label = initLabel(labelProperty, styleClass);
        container.getChildren().add(label);
        return label;
    }
    /**
     * This method is used to activate/deactivate toolbar buttons when
     * they can and cannot be used so as to provide foolproof design.
     * 
     * @param saved Describes whether the loaded Course has been saved or not.
     */
    public void updateToolbarControls(boolean saved) {
        // THIS TOGGLES WITH WHETHER THE CURRENT COURSE
        // HAS BEEN SAVED OR NOT
        saveDraftButton.setDisable(saved);

        // ALL THE OTHER BUTTONS ARE ALWAYS ENABLED
        // ONCE EDITING THAT FIRST COURSE BEGINS
        loadDraftButton.setDisable(false);
        exportDraftButton.setDisable(false);

        // NOTE THAT THE NEW, LOAD, AND EXIT BUTTONS
        // ARE NEVER DISABLED SO WE NEVER HAVE TO TOUCH THEM
    }
    private void initWorkspace() throws IOException {
        // THIS IS FOR MANAGING SCHEDULE EDITING
        initTopWorkspace();
        initScheduleItemsControls();
        
        // THIS HOLDS ALL OUR WORKSPACE COMPONENTS, SO NOW WE MUST
        // ADD THE COMPONENTS WE'VE JUST INITIALIZED
        workspacePane = new BorderPane();
        workspacePane.setTop(topWorkspacePane);
        workspacePane.setCenter(fantasyTeamsPane);
        workspacePane.getStyleClass().add(CLASS_BORDERED_PANE);
        
        // AND NOW PUT IT IN THE WORKSPACE
        workspaceScrollPane = new ScrollPane();
        workspaceScrollPane.setContent(workspacePane);
        workspaceScrollPane.setFitToWidth(true);
        
        // STARTING LINE UP AREA
        draftNameBox=new HBox();
        Label draftNameLabel;
        draftNameLabel= initChildLabel(draftNameBox, PropertyType.DRAFT_NAME_LABEL, CLASS_SUBHEADING_LABEL);
        TextField draftNameTextField= new TextField();
        draftNameBox.getChildren().add(draftNameTextField); draftNameTextField.setMinWidth(400);
        topWorkspacePane.getChildren().add(draftNameBox);
        addDeleteBox=new HBox();
        addfantasyTeamItemButton = initChildButton(addDeleteBox, PropertyType.ADD_ICON, PropertyType.ADD_TEAM_TOOLTIP, false);
        removefantasyTeamItemButton = initChildButton(addDeleteBox, PropertyType.MINUS_ICON, PropertyType.REMOVE_TEAM_TOOLTIP, false);
        editfantasyTeamItemButton = initChildButton(addDeleteBox, PropertyType.EDIT_TEAM_ICON, PropertyType.EDIT_TEAM_TOOLTIP, false);
        Label selectFantasyTeamLabel;
        selectFantasyTeamLabel= initChildLabel(addDeleteBox, PropertyType.SELECT_FANTASY_TEAM_LABEL, CLASS_SUBHEADING_LABEL);
        fantasyTeamComboBox=new ComboBox(); 
        addDeleteBox.getChildren().add(fantasyTeamComboBox);
        topWorkspacePane.getChildren().add(addDeleteBox);
        startingLineUpBox=new HBox();
        Label startingLineUpLabel;
        startingLineUpLabel= initChildLabel(startingLineUpBox, PropertyType.STARTING_LINEUP_LABEL, CLASS_SUBHEADING_LABEL);
        fantasyTeamsPane.getChildren().add(startingLineUpBox);
        fantasyTeamsPane.getStyleClass().add(CLASS_BORDERED_PANE);
       startingLineUpTable=new TableView();
        TableColumn position= new TableColumn("Position"); TableColumn first= new TableColumn("First"); TableColumn last= new TableColumn("Last"); 
        TableColumn proTeam= new TableColumn("Pro Team");TableColumn positions= new TableColumn("Positions"); TableColumn rw= new TableColumn("R/W");
        TableColumn hr_sv= new TableColumn("HR/SV"); TableColumn rbi_k= new TableColumn("RBI/K"); TableColumn sb_era= new TableColumn("SB/ERA");
        TableColumn ba_whip= new TableColumn("BA/WHIP"); TableColumn estimatedValue= new TableColumn("Estimated Value"); TableColumn contract= new TableColumn("Contract");
        TableColumn salary= new TableColumn("Salary");
        startingLineUpTable.getColumns().addAll(position,first,last,proTeam,positions,rw,hr_sv,rbi_k,sb_era,ba_whip,estimatedValue,contract,salary);
        fantasyTeamsPane.getChildren().add(startingLineUpTable); estimatedValue.setMinWidth(150);
        proTeam.setCellValueFactory(new PropertyValueFactory<String, String>("teamName"));
        last.setCellValueFactory(new PropertyValueFactory<String, String>("lastName"));
        first.setCellValueFactory(new PropertyValueFactory<String, String>("firstName"));
        positions.setCellValueFactory(new PropertyValueFactory<String, String>("qp"));
        rw.setCellValueFactory(new PropertyValueFactory<String, String>("r"));
        hr_sv.setCellValueFactory(new PropertyValueFactory<String,String>("hr"));
        rbi_k.setCellValueFactory(new PropertyValueFactory<String, String>("rbi"));
        sb_era.setCellValueFactory(new PropertyValueFactory<String, String>("sb"));
        salary.setCellValueFactory(new PropertyValueFactory<String, String>("salary"));
        contract.setCellValueFactory(new PropertyValueFactory<String, String>("contract"));
        ba_whip.setCellValueFactory(new PropertyValueFactory<String, String>("ab"));
         position.setCellValueFactory(new PropertyValueFactory<String, String>("qp"));
        // TAXI SQUAD BOX
        taxiSquadBox= new HBox();
        Label taxiSquadLabel;
        taxiSquadLabel= initChildLabel(taxiSquadBox, PropertyType.TAXI_SQUAD_LABEL, CLASS_SUBHEADING_LABEL);
        fantasyTeamsPane.getChildren().add(taxiSquadBox);
        taxiSquadTable=new TableView();
        TableColumn position1= new TableColumn("Position"); TableColumn first1= new TableColumn("First"); TableColumn last1= new TableColumn("Last"); 
        TableColumn proTeam1= new TableColumn("Pro Team");TableColumn positions1= new TableColumn("Positions"); TableColumn rw1= new TableColumn("R/W");
        TableColumn hr_sv1= new TableColumn("HR/SV"); TableColumn rbi_k1= new TableColumn("RBI/K"); TableColumn sb_era1= new TableColumn("SB/ERA");
        TableColumn ba_whip1= new TableColumn("BA/WHIP"); TableColumn estimatedValue1= new TableColumn("Estimated Value"); TableColumn contract1= new TableColumn("Contract");
        TableColumn salary1= new TableColumn("Salary");
        taxiSquadTable.getColumns().addAll(position1,first1,last1,proTeam1,positions1,rw1,hr_sv1,rbi_k1,sb_era1,ba_whip1,estimatedValue1,contract1,salary1);
        fantasyTeamsPane.getChildren().add(taxiSquadTable); estimatedValue1.setMinWidth(150);
        proTeam1.setCellValueFactory(new PropertyValueFactory<String, String>("teamName"));
        last1.setCellValueFactory(new PropertyValueFactory<String, String>("lastName"));
        first1.setCellValueFactory(new PropertyValueFactory<String, String>("firstName"));
        positions1.setCellValueFactory(new PropertyValueFactory<String, String>("qp"));
        rw1.setCellValueFactory(new PropertyValueFactory<String, String>("r"));
        hr_sv1.setCellValueFactory(new PropertyValueFactory<String,String>("hr"));
        rbi_k1.setCellValueFactory(new PropertyValueFactory<String, String>("rbi"));
        sb_era1.setCellValueFactory(new PropertyValueFactory<String, String>("sb"));
        salary1.setCellValueFactory(new PropertyValueFactory<String, String>("salary"));
        contract1.setCellValueFactory(new PropertyValueFactory<String, String>("contract"));
        ba_whip1.setCellValueFactory(new PropertyValueFactory<String, String>("ab"));
         position1.setCellValueFactory(new PropertyValueFactory<String, String>("qp"));
       
        
//NOTE THAT WE HAVE NOT PUT THE WORKSPACE INTO THE WINDOW,
        // THAT WILL BE DONE WHEN THE USER EITHER CREATES A NEW
        // COURSE OR LOADS AN EXISTING ONE FOR EDITING
        workspaceActivated = false;
    
    }
    // INITIALIZES THE TOP PORTION OF THE WORKWPACE UI
    private void initTopWorkspace() {
        // HERE'S THE SPLIT PANE, ADD THE TWO GROUPS OF CONTROLS
        topWorkspaceSplitPane = new SplitPane();
      
        //topWorkspaceSplitPane.getItems().add(draftInfoPane);
        //topWorkspaceSplitPane.getItems().add(pagesSelectionPane);

        // THE TOP WORKSPACE PANE WILL ONLY DIRECTLY HOLD 2 THINGS, A LABEL
        // AND A SPLIT PANE, WHICH WILL HOLD 2 ADDITIONAL GROUPS OF CONTROLS
        topWorkspacePane = new VBox();
        //topWorkspacePane.getStyleClass().add(CLASS_BORDERED_PANE);

        // HERE'S THE LABEL
        courseHeadingLabel = initChildLabel(topWorkspacePane, PropertyType.FANTASY_TEAMS_LABEL, CLASS_HEADING_LABEL);

        // AND NOW ADD THE SPLIT PANE
        topWorkspacePane.getChildren().add(topWorkspaceSplitPane);
       
    }
    private void initScheduleItemsControls() {
        fantasyTeamItemsBox = new VBox();
        fantasyTeamsPane = new VBox();
        fantasyTeamsPane.setMinSize(400, 600);
        fantasyTeamsPane.getChildren().add(fantasyTeamItemsBox);
    }
    
    
   
    // INITIALIZE THE WINDOW (i.e. STAGE) PUTTING ALL THE CONTROLS
    // THERE EXCEPT THE WORKSPACE, WHICH WILL BE ADDED THE FIRST
    // TIME A NEW Course IS CREATED OR LOADED
    
    // INIT ALL THE EVENT HANDLERS
    
    private void initEventHandlers() throws IOException {
        // FIRST THE FILE CONTROLS
       
        fileController = new FileController(messageDialog, yesNoCancelDialog,fileManager,siteExporter);
       // fantasyStandingScreen= new FantasyStandingScreen();
       // draftSummaryScreen= new DraftSummaryScreen();
       // mlbTeamScreen= new MLBTeamScreen();
       // availablePlayerScreen= new AvailablePlayerScreen();
        
        newDraftButton.setOnAction(e -> {
            
            try {
                availablePlayerScreen.initEverything();
                mlbTeamScreen.initEverything();
                draftSummaryScreen.initEverything();
                fantasyStandingScreen.initEverything();
            
            } 
            catch (IOException ex) {
                Logger.getLogger(Wolfie_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            //fileController.handleLoadCourseRequest(this.getAvailablePlayerScreen());
            fileController.handleNewCourseRequest(this);
        });
        fantasyStandingButton.setOnAction(e -> {
            csbPane.setCenter(fantasyStandingScreen.getWorkSpaceScrollPane());
        });
        fantasyTeamsButton.setOnAction(e -> {
            csbPane.setCenter(workspaceScrollPane);
        });
        draftSummaryButton.setOnAction(e -> {
            csbPane.setCenter(draftSummaryScreen.getWorkSpaceScrollPane());
            //draftSummaryScreen.initEverything();
        });
        mlbTeamButton.setOnAction(e -> {
            csbPane.setCenter(mlbTeamScreen.getWorkSpaceScrollPane());
        });
        availablePlayersButton.setOnAction(e -> {
            csbPane.setCenter(availablePlayerScreen.getWorkSpaceScrollPane());
            
        
        });
       teamController = new TeamEditController( messageDialog, yesNoCancelDialog);
       teams=FXCollections.observableArrayList();
       teamNames=FXCollections.observableArrayList();
       addfantasyTeamItemButton.setOnAction(e -> {
           
            
           teamController.handleAddTeamRequest(this);
            teams.add(teamController.getTeamDialog().getTeam());
            teamNames.add(teamController.getTeamDialog().getTeam().getName());
        //team.addHitters(playerController.getPlayerDialog().getPlayer());
            System.out.println(teamController.getTeamDialog().getTeam().getName());
            System.out.println(teamController.getTeamDialog().getTeam().getOwner());
           fantasyTeamComboBox.setItems(teamNames);
            newTeam=new Team();  newTeam.setName(teamController.getTeamDialog().getTeam().getName());
           newTeam.setOwner(teamController.getTeamDialog().getTeam().getOwner());
            listOfTeams.add(newTeam);
           System.out.println(newTeam.getName());
// playerTable.setItems((ObservableList) team);
            //availablePlayerScreen.getPlayerEditController().getPlayerDialog().getFantasyTeamComboBox().setItems(teamNames);
           // playerTable.setItems(teams);
        fantasyStandingScreen.getStandingsTable().setItems(listOfTeams);
       
       });
      removefantasyTeamItemButton.setOnAction(e -> {
            //teamController.handleRemoveTeamRequest(this, (Team) fantasyTeamComboBox.getSelectionModel().getSelectedItem());
            teams.remove(fantasyTeamComboBox.getSelectionModel().getSelectedItem());
            teamNames.remove(fantasyTeamComboBox.getSelectionModel().getSelectedItem());
             fantasyTeamComboBox.setItems(teamNames);
             listOfTeams.remove(fantasyTeamComboBox.getSelectionModel().getSelectedItem());
             fantasyStandingScreen.getStandingsTable().setItems(listOfTeams);
      });
     //Team t;
      editfantasyTeamItemButton.setOnAction(e -> {
            String teamToEdit= fantasyTeamComboBox.getSelectionModel().getSelectedItem().toString();
            Team t=new Team();
            for(int i=0;i<teams.size();i++){
                if(teams.get(i).getName().equals(teamToEdit)){
                   t= teams.get(i);
                }
            }
           // teamController.handleEditTeamRequest(this, t);
            
            teamNames.remove(fantasyTeamComboBox.getSelectionModel().getSelectedItem());
            teamNames.add(teamController.getTeamDialog().showEditTeamDialog(t).getName());
           // t.setName(teamController.getTeamDialog().showEditTeamDialog(t).getName());
           // t.setOwner(teamController.getTeamDialog().showEditTeamDialog(t).getOwner());
            System.out.println(t.getName());
            System.out.println(t.getOwner());
            
            fantasyTeamComboBox.setItems(teamNames);
      });
      
    //availablePlayerScreen.getPlayerEditController().getPlayerDialog();
      ObservableList<Object>things=FXCollections.observableArrayList();
      ObservableList<Hitters> importedHitter=FXCollections.observableArrayList();
      ObservableList<Pitchers> importedPitcher=FXCollections.observableArrayList();  
      importedHitter=availablePlayerScreen.getHittersToImport();
      importedPitcher=availablePlayerScreen.getPitchersToImport();
      for(int i=0;i<importedHitter.size();i++){
          things.add(importedHitter.get(i));
      }
     // for(int i=0;i<importedPitcher.size();i++){
         // things.add(importedPitcher.get(i));
     // }

//for(int i=0;i<availablePlayerScreen.getPitchersToImport().size();i++){
         // things.add(availablePlayerScreen.getPitchersToImport().get(i));
      //}
      //startingLineUpTable.setItems(things);
      startingLineUpTable.setItems(availablePlayerScreen.getDraftedPlayersToImport());
    // taxiSquadTable.setItems(availablePlayerScreen.getAllPlayerToImport());
      
      draftSummaryScreen.getTable().setItems(availablePlayerScreen.getPlayerToImport());
    //System.out.println(availablePlayerScreen.getHittersToImport().get(0).getFirstName());
      //Team newTeam=new Team();
      startingLineUpTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                // OPEN UP THE SCHEDULE ITEM EDITOR
                if(startingLineUpTable.getSelectionModel().getSelectedItem() instanceof Hitters){
                    Hitters si = (Hitters) startingLineUpTable.getSelectionModel().getSelectedItem();
                     availablePlayerScreen.getDraftedPlayersToImport().remove(si);
                    availablePlayerScreen.getPlayerEditController().handleEditPlayerRequest(availablePlayerScreen, si);
                    //System.out.println(si.getSalary()); System.out.println(si.getContract());
                    
                    availablePlayerScreen.getDraftedPlayersToImport().add(si);
                   
                    //System.out.println(newTeam.getHitters().get(0).getFirstName());
                    startingLineUpTable.setItems(availablePlayerScreen.getDraftedPlayersToImport());
                    //System.out.println(hittersToImport.get(0).getFirstName());
                    if(si.getContract().equals("S2")){
                        availablePlayerScreen.getPlayerToImport().add(si);
                        draftSummaryScreen.getTable().setItems(availablePlayerScreen.getPlayerToImport());
                        
                    }
                
                }
                if(startingLineUpTable.getSelectionModel().getSelectedItem() instanceof Pitchers){
                    Pitchers si = (Pitchers) startingLineUpTable.getSelectionModel().getSelectedItem();
                    availablePlayerScreen.getDraftedPlayersToImport().remove(si);
                    availablePlayerScreen.getPlayerEditController().handleEditPlayerRequest1(availablePlayerScreen, si);
                    //System.out.println(si.getSalary()); System.out.println(si.getContract());
                     
                    availablePlayerScreen.getDraftedPlayersToImport().add(si);
                    startingLineUpTable.setItems(availablePlayerScreen.getDraftedPlayersToImport());
                    if(si.getContract().equals("S2")){
                        availablePlayerScreen.getPlayerToImport().add(si);
                        draftSummaryScreen.getTable().setItems(availablePlayerScreen.getPlayerToImport());
                    }
                }
            
            }
        });
       taxiSquadTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                // OPEN UP THE SCHEDULE ITEM EDITOR
                if(taxiSquadTable.getSelectionModel().getSelectedItem() instanceof Hitters){
                    Hitters si = (Hitters) taxiSquadTable.getSelectionModel().getSelectedItem();
                    
                    availablePlayerScreen.getPlayerEditController().handleEditPlayerRequest(availablePlayerScreen, si);
                    //System.out.println(si.getSalary()); System.out.println(si.getContract());
                     availablePlayerScreen.getAllPlayerToImport().remove(si);
                    availablePlayerScreen.getAllPlayerToImport().add(si);
                    //System.out.println(newTeam.getHitters().get(0).getFirstName());
                    taxiSquadTable.setItems(randomDraftedPlayer);
                    
                    //System.out.println(hittersToImport.get(0).getFirstName());
                 
                }
                if(taxiSquadTable.getSelectionModel().getSelectedItem() instanceof Pitchers){
                    Pitchers si = (Pitchers) taxiSquadTable.getSelectionModel().getSelectedItem();
                    availablePlayerScreen.getPlayerEditController().handleEditPlayerRequest1(availablePlayerScreen, si);
                    //System.out.println(si.getSalary()); System.out.println(si.getContract());
                     availablePlayerScreen.getAllPlayerToImport().remove(si);
                    availablePlayerScreen.getAllPlayerToImport().add(si);
                    taxiSquadTable.setItems(randomDraftedPlayer);
                }
            
            }
        });
       
      
      
      
      draftSummaryScreen.getTable().setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                // OPEN UP THE SCHEDULE ITEM EDITOR
                if(draftSummaryScreen.getTable().getSelectionModel().getSelectedItem() instanceof Hitters){
                    Hitters si = (Hitters) draftSummaryScreen.getTable().getSelectionModel().getSelectedItem();
                    
                    availablePlayerScreen.getPlayerEditController().handleEditPlayerRequestDraft(draftSummaryScreen, si);
                    //System.out.println(si.getSalary()); System.out.println(si.getContract());
                     availablePlayerScreen.getPlayerToImport().remove(si);
                    availablePlayerScreen.getPlayerToImport().add(si);
                    if(!si.getContract().equals("S2")){
                        availablePlayerScreen.getPlayerToImport().remove(si);
                       //availablePlayerScreen.getDraftedPlayersToImport().add(si);
                       draftSummaryScreen.getTable().setItems(availablePlayerScreen.getPlayerToImport());
                     startingLineUpTable.setItems(availablePlayerScreen.getDraftedPlayersToImport());
                    }
                    //System.out.println(newTeam.getHitters().get(0).getFirstName());
                   
                    //System.out.println(hittersToImport.get(0).getFirstName());
                 
                }
                if(draftSummaryScreen.getTable().getSelectionModel().getSelectedItem() instanceof Pitchers){
                    Pitchers si = (Pitchers) draftSummaryScreen.getTable().getSelectionModel().getSelectedItem();
                    
                    availablePlayerScreen.getPlayerEditController().handleEditPlayerRequestDraft1(draftSummaryScreen, si);
                    //System.out.println(si.getSalary()); System.out.println(si.getContract());
                     availablePlayerScreen.getPlayerToImport().remove(si);
                    availablePlayerScreen.getPlayerToImport().add(si);
                    if(!si.getContract().equals("S2")){
                        availablePlayerScreen.getPlayerToImport().remove(si);
                       
                        //availablePlayerScreen.getDraftedPlayersToImport().add(si);
                       draftSummaryScreen.getTable().setItems(availablePlayerScreen.getPlayerToImport());
                    }
                    //System.out.println(newTeam.getHitters().get(0).getFirstName());
                    startingLineUpTable.setItems(availablePlayerScreen.getDraftedPlayersToImport());
                    //System.out.println(hittersToImport.get(0).getFirstName());
                 
                }
            }
       
      
      
      });
        draftSummaryScreen.getStarButton().setOnAction(e -> {
             
            int random=(int) (Math.random()*availablePlayerScreen.getHitters().size());
            Hitters si=(Hitters) availablePlayerScreen.getHitters().get(random);
            si.setContract("S2"); si.setSalary("1");
            //availablePlayerScreen.getTeamsToImport().get(random);
            availablePlayerScreen.getPlayerToImport().add(si);
            //availablePlayerScreen.getHitters().add(si);
            si.setPick(i); i++;
            availablePlayerScreen.getTeamsToImport().remove(si);
            availablePlayerScreen.getPlayerTable().setItems(availablePlayerScreen.getTeamsToImport());
            draftSummaryScreen.getTable().setItems(availablePlayerScreen.getPlayerToImport());
            availablePlayerScreen.getDraftedPlayersToImport().add(si);
            startingLineUpTable.setItems(availablePlayerScreen.getDraftedPlayersToImport());
            
            
            //System.out.println(listOfTeams.get(whichTeamCounter).getPlayersNeeded());
            listOfTeams.remove(whichTeamCounter); 
            Team newTeam=new Team(); 
            listOfTeams.add(newTeam);  int numOfTeams=(int) (Math.random()*10);
            playersNeeded--;moneyLeft--; int r=(int) (Math.random()*100); int hr=(int) (Math.random()*100);int rbi=(int) (Math.random()*100);int sb=(int) (Math.random()*100);
            Double ba= (Math.random()); int w=(int) (Math.random()*100);int sv=(int) (Math.random()*100);int k=(int) (Math.random()+100*2);Double era=(Math.random()+3);
            Double whip= (Math.random()+1); Double newba=new BigDecimal(ba).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
            Double newera=new BigDecimal(ba).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();Double newwhip=new BigDecimal(ba).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            listOfTeams.get(whichTeamCounter).setplayerNeeded(playersNeeded);
            listOfTeams.get(whichTeamCounter).setMoneyLeft(moneyLeft); listOfTeams.get(whichTeamCounter).setP((int) (Math.random()*20));
            listOfTeams.get(whichTeamCounter).setR(r); listOfTeams.get(whichTeamCounter).setHr(hr);
            listOfTeams.get(whichTeamCounter).setRbi(rbi);listOfTeams.get(whichTeamCounter).setSv(sv);
            listOfTeams.get(whichTeamCounter).setSb(sb); listOfTeams.get(whichTeamCounter).setK(k);
            listOfTeams.get(whichTeamCounter).setBa(newba);listOfTeams.get(whichTeamCounter).setEra(newera);
            listOfTeams.get(whichTeamCounter).setW(w);listOfTeams.get(whichTeamCounter).setWhip(newwhip);
            listOfTeams.get(whichTeamCounter).setTotalPoints(numOfTeams*10);
            fantasyStandingScreen.getStandingsTable().setItems(listOfTeams); 
            // now for taxi draft after 23 players
            if(playersNeeded==0){
                playersNeeded=23;
                whichTeamCounter++;
            }
            if(i>23){
                randomDraftedPlayer.add(si);
                taxiSquadTable.setItems(randomDraftedPlayer);
            }
        
        
        });  
        // this is automated draft
        draftSummaryScreen.getPlayButton().setOnAction(e -> {
             if(i>32){
                 i=1;
             }
             task = new Task<Void>() {
            
            @Override
            protected Void call() throws Exception {
                
                while(i<32){
                    int random=(int) (Math.random()*availablePlayerScreen.getHitters().size());
                    Hitters si=(Hitters) availablePlayerScreen.getHitters().get(random);
                    si.setContract("S2"); si.setSalary("1");
                      availablePlayerScreen.getHitters().remove(i);
                    //playersNeeded--;listOfTeams.get(whichTeamCounter).setplayerNeeded(playersNeeded);
                    // WE'RE SLEEPING FIRST JUST TO LET THE FIRST MESSAGE SHOW
                        Thread.sleep(500);
                       
                        // UPDATE ANY PROGRESS DISPLAY
                        Platform.runLater(new Runnable() {
                            //int index = pageIndex;

                            public void run() {
                                // System.out.println("play button clicked");
                              si.setPick(i); i++;   
                                availablePlayerScreen.getPlayerToImport().add(si);
                                availablePlayerScreen.getTeamsToImport().remove(si);
                    availablePlayerScreen.getPlayerTable().setItems(availablePlayerScreen.getTeamsToImport());
            draftSummaryScreen.getTable().setItems(availablePlayerScreen.getPlayerToImport());
            
            availablePlayerScreen.getDraftedPlayersToImport().add(si);
            startingLineUpTable.setItems(availablePlayerScreen.getDraftedPlayersToImport());
                             /**
                     listOfTeams.remove(whichTeamCounter); 
            Team newTeam=new Team(); 
            listOfTeams.add(newTeam);  int numOfTeams=(int) (Math.random()*10);
            playersNeeded--;moneyLeft--; int r=(int) (Math.random()*100); int hr=(int) (Math.random()*100);int rbi=(int) (Math.random()*100);int sb=(int) (Math.random()*100);
            Double ba= (Math.random()); int w=(int) (Math.random()*100);int sv=(int) (Math.random()*100);int k=(int) (Math.random()+100*2);Double era=(Math.random()+3);
            Double whip= (Math.random()+1); Double newba=new BigDecimal(ba).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
            Double newera=new BigDecimal(ba).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();Double newwhip=new BigDecimal(ba).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            listOfTeams.get(whichTeamCounter).setplayerNeeded(playersNeeded);
            listOfTeams.get(whichTeamCounter).setMoneyLeft(moneyLeft); listOfTeams.get(whichTeamCounter).setP((int) (Math.random()*20));
            listOfTeams.get(whichTeamCounter).setR(r); listOfTeams.get(whichTeamCounter).setHr(hr);
            listOfTeams.get(whichTeamCounter).setRbi(rbi);listOfTeams.get(whichTeamCounter).setSv(sv);
            listOfTeams.get(whichTeamCounter).setSb(sb); listOfTeams.get(whichTeamCounter).setK(k);
            listOfTeams.get(whichTeamCounter).setBa(newba);listOfTeams.get(whichTeamCounter).setEra(newera);
            listOfTeams.get(whichTeamCounter).setW(w);listOfTeams.get(whichTeamCounter).setWhip(newwhip);
            listOfTeams.get(whichTeamCounter).setTotalPoints(numOfTeams*10);
            fantasyStandingScreen.getStandingsTable().setItems(listOfTeams); 
            // now for taxi draft after 23 players
            if(playersNeeded==0){
                playersNeeded=23;
                whichTeamCounter++;
            }
            **/
                //fantasyStandingScreen.getStandingsTable().setItems(listOfTeams);
                    if(i>24){
                randomDraftedPlayer.add(si);
                taxiSquadTable.setItems(randomDraftedPlayer);
            }
                        listOfTeams.remove(whichTeamCounter); 
            Team newTeam=new Team(); 
            listOfTeams.add(newTeam);  int numOfTeams=(int) (Math.random()*10);
            playersNeeded--;moneyLeft--; int r=(int) (Math.random()*100); int hr=(int) (Math.random()*100);int rbi=(int) (Math.random()*100);int sb=(int) (Math.random()*100);
            Double ba= (Math.random()); int w=(int) (Math.random()*100);int sv=(int) (Math.random()*100);int k=(int) (Math.random()+100*2);Double era=(Math.random()+3);
            Double whip= (Math.random()+1); Double newba=new BigDecimal(ba).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
            Double newera=new BigDecimal(ba).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();Double newwhip=new BigDecimal(ba).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            listOfTeams.get(whichTeamCounter).setplayerNeeded(playersNeeded);
            listOfTeams.get(whichTeamCounter).setMoneyLeft(moneyLeft); listOfTeams.get(whichTeamCounter).setP((int) (Math.random()*20));
            listOfTeams.get(whichTeamCounter).setR(r); listOfTeams.get(whichTeamCounter).setHr(hr);
            listOfTeams.get(whichTeamCounter).setRbi(rbi);listOfTeams.get(whichTeamCounter).setSv(sv);
            listOfTeams.get(whichTeamCounter).setSb(sb); listOfTeams.get(whichTeamCounter).setK(k);
            listOfTeams.get(whichTeamCounter).setBa(newba);listOfTeams.get(whichTeamCounter).setEra(newera);
            listOfTeams.get(whichTeamCounter).setW(w);listOfTeams.get(whichTeamCounter).setWhip(newwhip);
            listOfTeams.get(whichTeamCounter).setTotalPoints(numOfTeams*10);
            fantasyStandingScreen.getStandingsTable().setItems(listOfTeams); 
            // now for taxi draft after 23 players
            if(playersNeeded==0){
                playersNeeded=23;
                whichTeamCounter++;
            }             
                            
                            
                            }
                        });
                        //System.out.println("EXPORTING " + pages[pageIndex]);
                    };
                
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.start();
        
        
        
         });
        draftSummaryScreen.getPauseButton().setOnAction(e -> {
        task.cancel();
        
        });
        
        saveDraftButton.setOnAction(e -> {
      
            for(int i=0;i<availablePlayerScreen.getDraftedPlayersToImport().size();i++){
                    //newTeam.addPlayer(availablePlayerScreen.getDraftedPlayersToImport());
                    //newTeam.addHitters(availablePlayerScreen.getHittersToImport().get(i));
                   newTeam.addHitters((Hitters) availablePlayerScreen.getDraftedPlayersToImport().get(i));
            // newTeam.addPitchers(availablePlayerScreen.getPitchersToImport().get(i));
           }
            for(int i=0;i<availablePlayerScreen.getPitchersToImport().size();i++){
                    newTeam.addPitchers(availablePlayerScreen.getPitchersToImport().get(i));
                   // newTeam.addPitchers(availablePlayerScreen.getPitchersToImport().get(i));
           }
           
           
           
           fileController.handleSaveCourseRequest(this, newTeam);
        
       });      
      loadDraftButton.setOnAction(e -> {
            try {
                fileController.handleLoadCourseRequest(this, newTeam);
                ObservableList<Hitters>jsonHitters=FXCollections.observableArrayList();
                //for(int i=0;i<newTeam.getHitters().size();i++){
                    //jsonHitters.add(newTeam.getHitters().get(i));
                //}
               JsonDraftFileManager jsonFileManager = new JsonDraftFileManager(); 
                ObservableList<Hitters> hitters= jsonFileManager.loadHittersSaved(fileController.getSelectedFile().getAbsolutePath());
                 ObservableList<Pitchers> pitchers= jsonFileManager.loadPitchersSaved(fileController.getSelectedFile().getAbsolutePath());
                ObservableList<Object> savedplayers=FXCollections.observableArrayList();
                 for(int i=0;i<hitters.size();i++){
                     savedplayers.add(hitters.get(i));
                 }
                for(int i=0;i<pitchers.size();i++){
                     savedplayers.add(pitchers.get(i));
                 }
                
                startingLineUpTable.setItems(savedplayers);
               // System.out.println(availablePlayerScreen.getHittersToImport().get(0).getFirstName());
            } 
             catch (IOException ex) {
                Logger.getLogger(Wolfie_GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    
    //availablePlayerScreen.getPlayerTable().setOnMouseClicked(e -> {
            //if (e.getClickCount() == 2) {
              
                //playersNeeded--;
            // listOfTeams.get(whichTeamCounter).setplayerNeeded(playersNeeded);
            // System.out.println(listOfTeams.get(whichTeamCounter).getPlayersNeeded());
             // fantasyStandingScreen.getStandingsTable().setItems(listOfTeams);  
    
           // }
       //});   TESTINGGG
     
    
              
    }
    
    // INITIALIZE THE WINDOW (i.e. STAGE) PUTTING ALL THE CONTROLS
    // THERE EXCEPT THE WORKSPACE, WHICH WILL BE ADDED THE FIRST
    // TIME A NEW Course IS CREATED OR LOADED
    private void initWindow(String windowTitle) {
        // SET THE WINDOW TITLE
        primaryStage.setTitle(windowTitle);

        // GET THE SIZE OF THE SCREEN
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        // AND USE IT TO SIZE THE WINDOW
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());

        // ADD THE TOOLBAR ONLY, NOTE THAT THE WORKSPACE
        // HAS BEEN CONSTRUCTED, BUT WON'T BE ADDED UNTIL
        // THE USER STARTS EDITING A COURSE
        csbPane = new BorderPane();
        csbPane.setTop(fileToolbarPane);
      
        primaryScene = new Scene(csbPane);

        // NOW TIE THE SCENE TO THE WINDOW, SELECT THE STYLESHEET
        // WE'LL USE TO STYLIZE OUR GUI CONTROLS, AND OPEN THE WINDOW
        primaryScene.getStylesheets().add(PRIMARY_STYLE_SHEET);
        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
    public void setSiteExporter(DraftSiteExporter initSiteExporter) {
        siteExporter = initSiteExporter;
    }

    /**
     * When called this function puts the workspace into the window,
     * revealing the controls for editing a Course.
     */
    public void activateWorkspace() {
        if (!workspaceActivated) {
            // PUT THE WORKSPACE IN THE GUI
            csbPane.setBottom(screenToolbarPane);
            
            csbPane.setCenter(workspaceScrollPane);
            
            workspaceActivated = true;
        
        }
    }
    @Override
    public void reloadCourse(Team teamToReload) {
        // FIRST ACTIVATE THE WORKSPACE IF NECESSARY
        if (!workspaceActivated) {
            activateWorkspace();
        }
        fantasyTeamComboBox.setValue(teamToReload.getName());
        // WE DON'T WANT TO RESPOND TO EVENTS FORCED BY
        // OUR INITIALIZATION SELECTIONS
        //draftController.enable(false);
        // NOW WE DO WANT TO RESPOND WHEN THE USER INTERACTS WITH OUR CONTROLS
        draftController.enable(true);
    }
    public FileController getFileController() {
        return fileController;
    }    
    public DraftDataManager getDataManager() {
        return dataManager;
    }

    public void updateCourseInfo(Team team) {
        team.setName("cool");
    }
    public void setDataManager(DraftDataManager initDataManager) {
        dataManager = initDataManager;
    }

    public AvailablePlayerScreen getAvailablePlayerScreen(){
        return availablePlayerScreen;
    }
    public void setDraftFileManager(DraftFileManager initCourseFileManager) {
        fileManager = initCourseFileManager;
    }
    public ComboBox getFantasyTeamComboBox(){
        return fantasyTeamComboBox;
    }

    public ObservableList <String> getTeamNames(){
        return teamNames;
    }
    public FantasyStandingScreen getFantasyStandingScreen(){
        return fantasyStandingScreen;
    }


}

        
    
    
    
