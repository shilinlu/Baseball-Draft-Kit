/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieball.gui;

import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
import wolfieball.data.Hitters;
import wolfieball.data.Pitchers;
import wolfieball.file.JsonDraftFileManager;
import static wolfieball.gui.Wolfie_GUI.CLASS_SUBHEADING_LABEL;
import wolfieball_draft_kit.PropertyType;
import static wolfieball_draft_kit.StartupConstants.JSON_FILE_HITTERS;
import static wolfieball_draft_kit.StartupConstants.JSON_FILE_PITCHERS;
import static wolfieball_draft_kit.StartupConstants.PATH_DATA;

/**
 *
 * @author shilinlu
 */
public class MLBTeamScreen {
    BorderPane workSpacePane;
    VBox topWorkSpacePane;
    VBox fantasyStandingsPane;
    VBox fantasyStandingsItemsBox;
    ScrollPane workSpaceScrollPane;
    SplitPane topWorkSpaceSplitPane;
    public static final String JSON_FILE_HITTERS=PATH_DATA + "Hitters.json";
    public static final String JSON_FILE_PITCHERS= PATH_DATA + "Pitchers.json";
    Label fantasyStandingLabel;
    static final String CLASS_BORDERED_PANE = "bordered_pane";
    static final String CLASS_HEADING_LABEL = "heading_label";
    MLBTeamScreen(){
        
    
    }
    public void initEverything() throws IOException{
        workSpacePane= new BorderPane();
        topWorkSpacePane = new VBox();
        fantasyStandingsPane = new VBox();
        workSpaceScrollPane= new ScrollPane();
        topWorkSpaceSplitPane= new SplitPane();
        workSpacePane.setTop(topWorkSpacePane);
        workSpacePane.setCenter(fantasyStandingsPane);
        workSpacePane.getStyleClass().add(CLASS_BORDERED_PANE);
        
        // AND NOW PUT IT IN THE WORKSPACE
        workSpaceScrollPane = new ScrollPane();
        workSpaceScrollPane.setContent(workSpacePane);
        workSpaceScrollPane.setFitToWidth(true);
        fantasyStandingLabel= initChildLabel(topWorkSpacePane, PropertyType.MLB_TEAMS_LABEL, CLASS_HEADING_LABEL);
        // AND NOW ADD THE SPLIT PANE
        topWorkSpacePane.getChildren().add(topWorkSpaceSplitPane);
        fantasyStandingsItemsBox = new VBox();
        
        fantasyStandingsPane.setMinSize(400, 600);
        fantasyStandingsPane.getChildren().add(fantasyStandingsItemsBox);
        HBox selectTeamBox=new HBox();
        Label selectProTeamLabel = initChildLabel(selectTeamBox, PropertyType.SELECT_PRO_TEAM_LABEL, CLASS_SUBHEADING_LABEL);
        topWorkSpacePane.getChildren().add(selectTeamBox);
        ObservableList MLBTeams=FXCollections.observableArrayList("ATL","AZ","CHC","CIN","COL","LAD","MIA","MIL","NYM","PHI","PIT","SD","SF","STL","WAS");
        ComboBox teamComboBox=new ComboBox(MLBTeams);
        selectTeamBox.getChildren().add(teamComboBox);
    
        TableView playerTable=new TableView();
        TableColumn first= new TableColumn("First"); TableColumn last= new TableColumn("Last");TableColumn positions= new TableColumn("Positions");
        playerTable.getColumns().addAll(first,last,positions);
        topWorkSpacePane.getChildren().add(playerTable);
        playerTable.setMaxWidth(450); first.setMinWidth(150);last.setMinWidth(150);positions.setMinWidth(150);
        playerTable.setMinHeight(500);
        last.setCellValueFactory(new PropertyValueFactory<String, String>("lastName"));
        first.setCellValueFactory(new PropertyValueFactory<String, String>("firstName"));
        positions.setCellValueFactory(new PropertyValueFactory<String, String>("qp"));
        
        JsonDraftFileManager jsonFileManager = new JsonDraftFileManager(); 
        ObservableList<Hitters> hitters= jsonFileManager.loadHitters(JSON_FILE_HITTERS);
        ObservableList<Pitchers> pitchers= jsonFileManager.loadPitchers(JSON_FILE_PITCHERS);
        ObservableList<Object> players=FXCollections.observableArrayList();
        
        for(int i=0;i<hitters.size();i++){
            players.add(hitters.get(i));
        }
        for(int i=0;i<pitchers.size();i++){
            players.add(pitchers.get(i));
            pitchers.get(i).setQp("P");
        }
        playerTable.setItems(players);
       
        teamComboBox.setOnAction(e -> {
            ObservableList<Object> ATL=FXCollections.observableArrayList(); 
            ObservableList<Object> AZ=FXCollections.observableArrayList();
            ObservableList<Object> CHC=FXCollections.observableArrayList();
            ObservableList<Object> CIN=FXCollections.observableArrayList();
            ObservableList<Object> COL=FXCollections.observableArrayList();
            ObservableList<Object> LAD=FXCollections.observableArrayList();
            ObservableList<Object> MIA=FXCollections.observableArrayList();
            ObservableList<Object> MIL=FXCollections.observableArrayList();
            ObservableList<Object> NYM=FXCollections.observableArrayList();
            ObservableList<Object> PHI=FXCollections.observableArrayList();
            ObservableList<Object> PIT=FXCollections.observableArrayList();
            ObservableList<Object> SD=FXCollections.observableArrayList();
            ObservableList<Object> SF=FXCollections.observableArrayList();
            ObservableList<Object> STL=FXCollections.observableArrayList();
            ObservableList<Object> WAS=FXCollections.observableArrayList();
            
            if(teamComboBox.getSelectionModel().getSelectedItem().toString().equals("ATL")){
            for(int i=0; i<hitters.size();i++){
                if(hitters.get(i).getTeamName().matches("ATL")){
                ATL.add(hitters.get(i));}
            }
            for(int i=0; i<pitchers.size();i++){
                if(pitchers.get(i).getTeamName().matches("ATL")){
                ATL.add(pitchers.get(i));}
            }
            playerTable.setItems(ATL);}
            
            if(teamComboBox.getSelectionModel().getSelectedItem().toString().equals("AZ")){
            for(int i=0; i<hitters.size();i++){
                if(hitters.get(i).getTeamName().matches("AZ")){
                AZ.add(hitters.get(i));}
            }
            for(int i=0; i<pitchers.size();i++){
                if(pitchers.get(i).getTeamName().matches("AZ")){
                AZ.add(pitchers.get(i));}
            }
            playerTable.setItems(AZ);}
        
            if(teamComboBox.getSelectionModel().getSelectedItem().toString().equals("CHC")){
            for(int i=0; i<hitters.size();i++){
                if(hitters.get(i).getTeamName().matches("CHC")){
                CHC.add(hitters.get(i));}
            }
            for(int i=0; i<pitchers.size();i++){
                if(pitchers.get(i).getTeamName().matches("CHC")){
                CHC.add(pitchers.get(i));}
            }
            playerTable.setItems(CHC);}
        
            if(teamComboBox.getSelectionModel().getSelectedItem().toString().equals("CIN")){
            for(int i=0; i<hitters.size();i++){
                if(hitters.get(i).getTeamName().matches("CIN")){
                CIN.add(hitters.get(i));}
            }
            for(int i=0; i<pitchers.size();i++){
                if(pitchers.get(i).getTeamName().matches("CIN")){
                CIN.add(pitchers.get(i));}
            }
            playerTable.setItems(CIN);}
        
            if(teamComboBox.getSelectionModel().getSelectedItem().toString().equals("COL")){
            for(int i=0; i<hitters.size();i++){
                if(hitters.get(i).getTeamName().matches("COL")){
                COL.add(hitters.get(i));}
            }
            for(int i=0; i<pitchers.size();i++){
                if(pitchers.get(i).getTeamName().matches("COL")){
                COL.add(pitchers.get(i));}
            }
            playerTable.setItems(COL);}
        
            if(teamComboBox.getSelectionModel().getSelectedItem().toString().equals("LAD")){
            for(int i=0; i<hitters.size();i++){
                if(hitters.get(i).getTeamName().matches("LAD")){
                LAD.add(hitters.get(i));}
            }
            for(int i=0; i<pitchers.size();i++){
                if(pitchers.get(i).getTeamName().matches("LAD")){
                LAD.add(pitchers.get(i));}
            }
            playerTable.setItems(LAD);}
        
            if(teamComboBox.getSelectionModel().getSelectedItem().toString().equals("MIA")){
            for(int i=0; i<hitters.size();i++){
                if(hitters.get(i).getTeamName().matches("MIA")){
                MIA.add(hitters.get(i));}
            }
            for(int i=0; i<pitchers.size();i++){
                if(pitchers.get(i).getTeamName().matches("MIA")){
                MIA.add(pitchers.get(i));}
            }
            playerTable.setItems(MIA);}
        
            if(teamComboBox.getSelectionModel().getSelectedItem().toString().equals("MIL")){
            for(int i=0; i<hitters.size();i++){
                if(hitters.get(i).getTeamName().matches("MIL")){
                MIL.add(hitters.get(i));}
            }
            for(int i=0; i<pitchers.size();i++){
                if(pitchers.get(i).getTeamName().matches("MIL")){
                MIL.add(pitchers.get(i));}
            }
            playerTable.setItems(MIL);}
        
            if(teamComboBox.getSelectionModel().getSelectedItem().toString().equals("NYM")){
            for(int i=0; i<hitters.size();i++){
                if(hitters.get(i).getTeamName().matches("NYM")){
                NYM.add(hitters.get(i));}
            }
            for(int i=0; i<pitchers.size();i++){
                if(pitchers.get(i).getTeamName().matches("NYM")){
                NYM.add(pitchers.get(i));}
            }
            playerTable.setItems(NYM);}
        
            if(teamComboBox.getSelectionModel().getSelectedItem().toString().equals("PHI")){
            for(int i=0; i<hitters.size();i++){
                if(hitters.get(i).getTeamName().matches("PHI")){
                PHI.add(hitters.get(i));}
            }
            for(int i=0; i<pitchers.size();i++){
                if(pitchers.get(i).getTeamName().matches("PHI")){
                PHI.add(pitchers.get(i));}
            }
            playerTable.setItems(PHI);}
        
            if(teamComboBox.getSelectionModel().getSelectedItem().toString().equals("PIT")){
            for(int i=0; i<hitters.size();i++){
                if(hitters.get(i).getTeamName().matches("PIT")){
                PIT.add(hitters.get(i));}
            }
            for(int i=0; i<pitchers.size();i++){
                if(pitchers.get(i).getTeamName().matches("PIT")){
                PIT.add(pitchers.get(i));}
            }
            playerTable.setItems(PIT);}
        
            if(teamComboBox.getSelectionModel().getSelectedItem().toString().equals("SD")){
            for(int i=0; i<hitters.size();i++){
                if(hitters.get(i).getTeamName().matches("SD")){
                SD.add(hitters.get(i));}
            }
            for(int i=0; i<pitchers.size();i++){
                if(pitchers.get(i).getTeamName().matches("SD")){
                SD.add(pitchers.get(i));}
            }
            playerTable.setItems(SD);}
        
            if(teamComboBox.getSelectionModel().getSelectedItem().toString().equals("SF")){
            for(int i=0; i<hitters.size();i++){
                if(hitters.get(i).getTeamName().matches("SF")){
                SF.add(hitters.get(i));}
            }
            for(int i=0; i<pitchers.size();i++){
                if(pitchers.get(i).getTeamName().matches("SF")){
                SF.add(pitchers.get(i));}
            }
            playerTable.setItems(SF);}
        
            if(teamComboBox.getSelectionModel().getSelectedItem().toString().equals("STL")){
            for(int i=0; i<hitters.size();i++){
                if(hitters.get(i).getTeamName().matches("STL")){
                STL.add(hitters.get(i));}
            }
            for(int i=0; i<pitchers.size();i++){
                if(pitchers.get(i).getTeamName().matches("STL")){
                STL.add(pitchers.get(i));}
            }
            playerTable.setItems(STL);}
        
            if(teamComboBox.getSelectionModel().getSelectedItem().toString().equals("WAS")){
            for(int i=0; i<hitters.size();i++){
                if(hitters.get(i).getTeamName().matches("WAS")){
                WAS.add(hitters.get(i));}
            }
            for(int i=0; i<pitchers.size();i++){
                if(pitchers.get(i).getTeamName().matches("WAS")){
                WAS.add(pitchers.get(i));}
            }
            playerTable.setItems(WAS);}
        });
            
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
}


