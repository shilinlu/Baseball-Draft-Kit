/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieball.gui;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import properties_manager.PropertiesManager;
import wolfieball.controller.FileController;
import wolfieball.controller.PlayerEditController;
import wolfieball.controller.TeamEditController;
import wolfieball.data.DraftDataManager;
import wolfieball.data.DraftDataView;
import wolfieball.data.Hitters;
import wolfieball.data.Pitchers;
import wolfieball.data.Team;
import wolfieball.file.DraftFileManager;
import wolfieball.file.DraftSiteExporter;
import wolfieball.file.JsonDraftFileManager;
import wolfieball_draft_kit.PropertyType;
import static wolfieball_draft_kit.StartupConstants.JSON_FILE_HITTERS;
import static wolfieball_draft_kit.StartupConstants.JSON_FILE_PITCHERS;
import static wolfieball_draft_kit.StartupConstants.PATH_IMAGES;

/**
 *
 * @author shilinlu
 */
public class AvailablePlayerScreen implements DraftDataView{
    // THIS MANAGES ALL OF THE APPLICATION'S DATA
    DraftDataManager dataManager;
    DraftFileManager fileManager;
    FileController fileController;
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    DraftSiteExporter siteExporter;
    Wolfie_GUI gui;
    BorderPane workSpacePane;
    VBox topWorkSpacePane;
    VBox fantasyStandingsPane;
    VBox fantasyStandingsItemsBox;
    HBox addDeleteBox;
    HBox radioButtonBox;
    ScrollPane workSpaceScrollPane;
    SplitPane topWorkSpaceSplitPane;
    Button addPlayerButton;
    Button deletePlayerButton;
    Label fantasyStandingLabel;
    Label searchLabel;
    TextField searchField;
    TableView playerTable;
    RadioButton all= new RadioButton("All"); RadioButton c= new RadioButton("C"); RadioButton b1= new RadioButton("1B"); 
    RadioButton ci= new RadioButton("CI"); RadioButton b3= new RadioButton("3B"); RadioButton b2= new RadioButton("2B"); 
    RadioButton mi= new RadioButton("MI"); RadioButton ss= new RadioButton("SS"); RadioButton of= new RadioButton("OF"); 
    RadioButton u= new RadioButton("U");  RadioButton p= new RadioButton("P"); 
    TableColumn first= new TableColumn("First"); TableColumn last= new TableColumn("Last"); TableColumn proTeam= new TableColumn("Pro Team"); 
    TableColumn positions= new TableColumn("Positions");TableColumn yob= new TableColumn("Year of Birth"); TableColumn rw= new TableColumn("R/W");
    TableColumn hr_sv= new TableColumn("HR/SV"); TableColumn rbi_k= new TableColumn("RBI/K"); TableColumn sb_era= new TableColumn("SB/ERA");
    TableColumn ba_whip= new TableColumn("BA/WHIP"); TableColumn estimatedValue= new TableColumn("Estimated Value"); TableColumn notes= new TableColumn("Notes");
    static final String CLASS_BORDERED_PANE = "bordered_pane";
    static final String CLASS_HEADING_LABEL = "heading_label";
   static final String CLASS_SUBHEADING_LABEL = "subheading_label";
    // THIS HANDLES REQUESTS TO ADD OR EDIT SCHEDULE STUFF
    PlayerEditController playerController;
   ObservableList <Hitters> hittersToImport= FXCollections.observableArrayList();
    ObservableList <Pitchers> pitchersToImport= FXCollections.observableArrayList();
   ObservableList <Object> playerToImport=FXCollections.observableArrayList();
     ObservableList <Hitters> startingLineUpList=FXCollections.observableArrayList();
   ObservableList <Object> allplayerToImport=FXCollections.observableArrayList();
     ObservableList <Object> draftedPlayers=FXCollections.observableArrayList();
   ObservableList<Object> teams;
     ObservableList<Hitters> hitters; int whichTeamCounter=0; int playersNeeded=23;
   AvailablePlayerScreen(){
       
        //playerController = new PlayerEditController( dataManager.getTeam(), messageDialog, yesNoCancelDialog);
    
    
    }
        public void initEverything() throws IOException{
             
            workSpacePane= new BorderPane();
        topWorkSpacePane = new VBox();
        fantasyStandingsPane = new VBox();
        //workSpaceScrollPane= new ScrollPane();
        topWorkSpaceSplitPane= new SplitPane();
        addDeleteBox=new HBox();
        searchField= new TextField();
        radioButtonBox= new HBox();
        playerTable=new TableView();
        workSpacePane.setTop(topWorkSpacePane);
        workSpacePane.setCenter(fantasyStandingsPane);
        workSpacePane.getStyleClass().add(CLASS_BORDERED_PANE);
        
        // AND NOW PUT IT IN THE WORKSPACE
        workSpaceScrollPane = new ScrollPane();
        workSpaceScrollPane.setContent(workSpacePane);
        workSpaceScrollPane.setFitToWidth(true);
        fantasyStandingLabel= initChildLabel(topWorkSpacePane, PropertyType.AVAILABLE_PLAYERS_LABEL, CLASS_HEADING_LABEL);
        // AND NOW ADD THE SPLIT PANE
        addPlayerButton = initChildButton(addDeleteBox, PropertyType.ADD_ICON, PropertyType.ADD_ITEM_TOOLTIP, false);
        deletePlayerButton = initChildButton(addDeleteBox, PropertyType.MINUS_ICON, PropertyType.DELETE_TOOLTIP, false);
        searchLabel= initChildLabel(addDeleteBox, PropertyType.SEARCH_LABEL, CLASS_SUBHEADING_LABEL);
        searchLabel.setPadding(new Insets(5,5,5,5)); searchField.setPadding(new Insets(8,8,8,8)); 
        searchField.setMinWidth(1000); 
        addDeleteBox.getChildren().add(searchField);
        radioButtonBox.getChildren().addAll(all,c,b1,ci,b3,b2,mi,ss,of,u,p); all.setSelected(true);
        ToggleGroup group= new ToggleGroup();
        all.setToggleGroup(group);c.setToggleGroup(group);b1.setToggleGroup(group);ci.setToggleGroup(group);b3.setToggleGroup(group);
        b2.setToggleGroup(group);mi.setToggleGroup(group);ss.setToggleGroup(group);of.setToggleGroup(group);u.setToggleGroup(group);p.setToggleGroup(group);
        topWorkSpacePane.getChildren().add(topWorkSpaceSplitPane);
        topWorkSpacePane.getChildren().add(addDeleteBox);
        topWorkSpacePane.getChildren().add(radioButtonBox);
        radioButtonBox.setSpacing(25); radioButtonBox.setPadding(new Insets(20,20,20,20));
        fantasyStandingsItemsBox = new VBox();
      
        playerTable=new TableView();
        playerTable.getColumns().addAll(first,last,proTeam,positions,yob,rw,hr_sv,rbi_k,sb_era,ba_whip,estimatedValue,notes);
        estimatedValue.setMinWidth(150); notes.setMinWidth(150);first.setMinWidth(90); last.setMinWidth(90);positions.setMinWidth(110); proTeam.setMinWidth(70);
        yob.setMinWidth(100); rw.setMinWidth(50); hr_sv.setMinWidth(50); rbi_k.setMinWidth(50); sb_era.setMinWidth(50); ba_whip.setMinWidth(70);
        playerTable.setMinSize(1290, 1000);
        // AND LINK THE COLUMNS TO THE DATA TO HITTERS
        
        proTeam.setCellValueFactory(new PropertyValueFactory<String, String>("teamName"));
        last.setCellValueFactory(new PropertyValueFactory<String, String>("lastName"));
        first.setCellValueFactory(new PropertyValueFactory<String, String>("firstName"));
        positions.setCellValueFactory(new PropertyValueFactory<String, String>("qp"));
        rw.setCellValueFactory(new PropertyValueFactory<String, String>("r"));
        hr_sv.setCellValueFactory(new PropertyValueFactory<String,String>("hr"));
        rbi_k.setCellValueFactory(new PropertyValueFactory<String, String>("rbi"));
        sb_era.setCellValueFactory(new PropertyValueFactory<String, String>("sb"));
        notes.setCellValueFactory(new PropertyValueFactory<String, String>("notes"));
        yob.setCellValueFactory(new PropertyValueFactory<String, String>("year_of_birth"));
        ba_whip.setCellValueFactory(new PropertyValueFactory<String, String>("ab"));
        estimatedValue.setCellValueFactory(new PropertyValueFactory<String, String>("estimatedValue"));
        //rw.setCellValueFactory(new PropertyValueFactory<String, String>("W"));
        //hr_sv.setCellValueFactory(new PropertyValueFactory<String,String>("SV"));
        //rbi_k.setCellValueFactory(new PropertyValueFactory<String,String>("K"));
        //sb_era.setCellValueFactory(new PropertyValueFactory<String, String>("ER"));
        
        
        Team team=new Team();
        JsonDraftFileManager jsonFileManager = new JsonDraftFileManager(); 
         hitters= jsonFileManager.loadHitters(JSON_FILE_HITTERS);
        ObservableList<Pitchers> pitchers= jsonFileManager.loadPitchers(JSON_FILE_PITCHERS);
        team.setHitters(hitters); team.setPitchers(pitchers);
        for(int i=0;i<hitters.size();i++){
            hitters.get(i).setEstimatedValue((int) (Math.random()*75));
        }
       
        teams=FXCollections.observableArrayList();
        for(int i=0;i<hitters.size();i++){
            teams.add(hitters.get(i));
        }
        for(int i=0;i<pitchers.size();i++){
            teams.add(pitchers.get(i));
            pitchers.get(i).setQp("P");
        }
        playerTable.setItems(teams);
      
        p.setOnAction(e -> {
             playerTable.setItems(pitchers);
         
        });
        all.setOnAction(e -> {
             playerTable.setItems(teams);
        });
        c.setOnAction(e -> {
            ObservableList<Hitters> catchers=FXCollections.observableArrayList(); 
            for(int i=0; i<hitters.size();i++){
                if(hitters.get(i).getQp().matches("C")){
                catchers.add(hitters.get(i));}
            }
            playerTable.setItems(catchers);
        
        });
        b1.setOnAction(e -> {
            ObservableList<Hitters> basemen1=FXCollections.observableArrayList(); 
            for(int i=0; i<hitters.size();i++){
                if(hitters.get(i).getQp().matches("1B")){
                basemen1.add(hitters.get(i));}
            }
            playerTable.setItems(basemen1);
        
        });
        c.setOnAction(e -> {
            ObservableList<Hitters> catchers=FXCollections.observableArrayList(); 
            for(int i=0; i<hitters.size();i++){
                if(hitters.get(i).getQp().matches("C")){
                catchers.add(hitters.get(i));}
            }
            playerTable.setItems(catchers);
        
        });
        b3.setOnAction(e -> {
            ObservableList<Hitters> basemen3=FXCollections.observableArrayList(); 
            for(int i=0; i<hitters.size();i++){
                if(hitters.get(i).getQp().matches("3B")){
                basemen3.add(hitters.get(i));}
            }
            playerTable.setItems(basemen3);
        
        });
        ci.setOnAction(e -> {
            ObservableList<Hitters> b1orb3=FXCollections.observableArrayList(); 
            for(int i=0; i<hitters.size();i++){
                if(hitters.get(i).getQp().matches("1B_3B")){
                b1orb3.add(hitters.get(i));}
            }
            playerTable.setItems(b1orb3);
        
        });
        b2.setOnAction(e -> {
            ObservableList<Hitters> basemen2=FXCollections.observableArrayList(); 
            for(int i=0; i<hitters.size();i++){
                if(hitters.get(i).getQp().matches("2B")){
                basemen2.add(hitters.get(i));}
            }
            playerTable.setItems(basemen2);
        
        });
        mi.setOnAction(e -> {
            ObservableList<Hitters> b2orss=FXCollections.observableArrayList(); 
            for(int i=0; i<hitters.size();i++){
                if(hitters.get(i).getQp().matches("SS_2B")){
                b2orss.add(hitters.get(i));}
            }
            playerTable.setItems(b2orss);
        
        });
        ss.setOnAction(e -> {
            ObservableList<Hitters> shortstop=FXCollections.observableArrayList(); 
            for(int i=0; i<hitters.size();i++){
                if(hitters.get(i).getQp().matches("SS")){
                shortstop.add(hitters.get(i));}
            }
            playerTable.setItems(shortstop);
        
        });
        of.setOnAction(e -> {
            ObservableList<Hitters> outfielder=FXCollections.observableArrayList(); 
            for(int i=0; i<hitters.size();i++){
                if(hitters.get(i).getQp().matches("OF")){
                outfielder.add(hitters.get(i));}
            }
            playerTable.setItems(outfielder);
        
        });
        u.setOnAction(e -> {
             playerTable.setItems(hitters);
        });
        
        searchField.setOnAction(e -> {
                CharSequence characters = searchField.getCharacters();
                String searchPhrase=characters.toString();
                ObservableList<Object> results=FXCollections.observableArrayList(); 
                for(int i=0;i<hitters.size();i++){
                    if(team.getHitters().get(i).getFirstName().matches(searchPhrase)|| team.getHitters().get(i).getLastName().matches(searchPhrase)){
                        results.add(hitters.get(i));
                    }
                }
                for(int i=0;i<pitchers.size();i++){
                    if(team.getPitchers().get(i).getFirstName().matches(searchPhrase)||team.getPitchers().get(i).getLastName().matches(searchPhrase)){
                        results.add(pitchers.get(i));
                    }
                }
                
                playerTable.setItems(results);
        
        });
        
        playerTable.setEditable(true); // makes the notes column editable
        notes.setCellFactory(TextFieldTableCell.forTableColumn());
        notes.setOnEditCommit(
        new EventHandler<CellEditEvent<Hitters, String>>() {
        @Override
        public void handle(CellEditEvent<Hitters, String> t) {
            ((Hitters) t.getTableView().getItems().get(
                t.getTablePosition().getRow())
                ).setNotes(t.getNewValue());
        }
    }
);
        //System.out.println(team.getHitters().get(1).getFirstName());
        //playerTable.setItems(team.getHitters());
        //fileController = new FileController(messageDialog, yesNoCancelDialog,fileManager,siteExporter);
        //fileController.handleLoadCourseRequest(this);
     
        fantasyStandingsPane.setMinSize(400, 600);
        
        fantasyStandingsPane.getChildren().add(playerTable);
        // AND NOW THE SCHEDULE ITEM ADDING AND EDITING CONTROLS
        playerController = new PlayerEditController( dataManager.getTeam(), messageDialog, yesNoCancelDialog);
        //TeamEditController teamController = new TeamEditController( messageDialog, yesNoCancelDialog);
        //playerController.getPlayerDialog().getFantasyTeamComboBox().getItems().add(teamController.getTeamDialog().getTeam().getName());
        
        addPlayerButton.setOnAction(e -> {
           
            playerController.handleAddPlayerRequest(this);
            teams.add(playerController.getPlayerDialog().getPlayer());
            team.addHitters(playerController.getPlayerDialog().getPlayer());
            hitters.add(playerController.getPlayerDialog().getPlayer());
            System.out.println(playerController.getPlayerDialog().getPlayer().getFirstName());
            System.out.println(playerController.getPlayerDialog().getPlayer().getQp());
            System.out.println(playerController.getPlayerDialog().getPlayer().getTeamName());
           // playerTable.setItems((ObservableList) team);
            
            playerTable.setItems(teams);
        });
        deletePlayerButton.setOnAction(e -> {
            playerController.handleRemovePlayerRequest(this, (Object) playerTable.getSelectionModel().getSelectedItem());
            team.removePlayer(playerTable.getSelectionModel().getSelectedItem());
            teams.remove(playerTable.getSelectionModel().getSelectedItem());
        });
        
        playerTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                // OPEN UP THE SCHEDULE ITEM EDITOR
                if(playerTable.getSelectionModel().getSelectedItem() instanceof Hitters){
                    Hitters si = (Hitters) playerTable.getSelectionModel().getSelectedItem();
                    playerController.handleEditPlayerRequest(this, si);
                    
                    System.out.println(si.getSalary()); System.out.println(si.getContract());
                    hittersToImport.add(si);
                    if(si.getContract().equals("S1") || si.getContract().equals("X") ){
                        allplayerToImport.add(si);}
                    
                    if(si.getContract().equals("S2")){
                        playerToImport.add(si);
                        playersNeeded--;
                        //gui.getFantasyStandingScreen().getStandingsTable().setItems(teams);
                    }
                    draftedPlayers.add(si);
                    teams.remove(si);
                    hitters.remove(si);
                   


                // System.out.println(hittersToImport.get(0).getFirstName());
                    //playerController.getPlayerDialog().getFantasyTeamComboBox().setItems(gui.getTeamNames());
                   // System.out.println(gui.getTeamNames());
                }
                    
                if(playerTable.getSelectionModel().getSelectedItem() instanceof Pitchers){
                    Pitchers si = (Pitchers) playerTable.getSelectionModel().getSelectedItem();
                    playerController.handleEditPlayerRequest1(this, si);
                    System.out.println(si.getSalary()); System.out.println(si.getContract());
                    pitchersToImport.add(si);
                   if(si.getContract().equals("S1") || si.getContract().equals("X") ){
                        allplayerToImport.add(si);}
                    
                   if(si.getContract().equals("S2")){
                    playerToImport.add(si);
                    }
                    draftedPlayers.add(si);
                   teams.remove(si);
                    pitchers.remove(si);
                   System.out.println(si.getQp());
                }
            }
        });
       //for(int i=0;i<hittersToImport.size();i++){
          //if(hittersToImport.get(i).getContract().equals("S1")){
             // hittersToImport.remove(i);
         // }
      
      //}
       
     
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
    public ScrollPane getWorkSpaceScrollPane(){
        return workSpaceScrollPane;
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
public FileController getFileController() {
        return fileController;
    }    
    public DraftDataManager getDataManager() {
        return dataManager;
    }

    @Override
    public void reloadCourse(Team teamToReload) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void setDataManager(DraftDataManager dataManager){
        this.dataManager=dataManager;
    }
    public void setDraftFileManager(DraftFileManager initCourseFileManager) {
        fileManager = initCourseFileManager;
    }
    public Stage getWindow() {
        return gui.getWindow();
    }
    public TableView getPlayerTable(){
        return playerTable;
    }
    public TableColumn getFirstNameColumn(){
        return first;
    }
    public PlayerEditController getPlayerEditController(){
        return playerController;
    }
    public ObservableList<Hitters> getHittersToImport(){
        return hittersToImport;
    }
    public ObservableList<Pitchers> getPitchersToImport(){
        return pitchersToImport;
    }
     public ObservableList<Object> getPlayerToImport(){
        return playerToImport;
    }
     public ObservableList<Hitters> getStartingLineUpList(){
         return startingLineUpList;
     }
      public ObservableList<Object> getAllPlayerToImport(){
        return allplayerToImport;
    }
 public ObservableList<Object> getDraftedPlayersToImport(){
        return draftedPlayers;
    }
     public ObservableList<Object> getTeamsToImport(){
        return teams;
    }
public ObservableList<Hitters> getHitters(){
        return hitters;
    }

public int getPlayersNeeded(){
    return playersNeeded;
}
public int getWhichTeamCounter(){
    return whichTeamCounter;
}


}
