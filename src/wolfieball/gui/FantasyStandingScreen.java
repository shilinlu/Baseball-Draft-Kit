/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieball.gui;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

import wolfieball_draft_kit.PropertyType;

/**
 *
 * @author shilinlu
 */
public class FantasyStandingScreen {
    BorderPane workSpacePane;
    VBox topWorkSpacePane;
    VBox fantasyStandingsPane;
    VBox fantasyStandingsItemsBox;
    ScrollPane workSpaceScrollPane;
    SplitPane topWorkSpaceSplitPane;
    
    Label fantasyStandingLabel;
    static final String CLASS_BORDERED_PANE = "bordered_pane";
    static final String CLASS_HEADING_LABEL = "heading_label";
    TableView standingsTable;
    TableColumn playersNeeded;
    FantasyStandingScreen(){
        
    
    }
    public void initEverything(){
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
        fantasyStandingLabel= initChildLabel(topWorkSpacePane, PropertyType.FANTASY_STANDINGS_LABEL, CLASS_HEADING_LABEL);
        // AND NOW ADD THE SPLIT PANE
        topWorkSpacePane.getChildren().add(topWorkSpaceSplitPane);
        fantasyStandingsItemsBox = new VBox();
        
        fantasyStandingsPane.setMinSize(400, 600);
        fantasyStandingsPane.getChildren().add(fantasyStandingsItemsBox);
        standingsTable=new TableView();
        TableColumn teamName= new TableColumn("Team Name");playersNeeded= new TableColumn("Players Needed"); 
        TableColumn moneyLeft= new TableColumn("$ Left"); TableColumn p= new TableColumn("$ PP"); 
        TableColumn r= new TableColumn("R"); TableColumn hr= new TableColumn("HR");TableColumn rbi= new TableColumn("RBI");
        TableColumn sb= new TableColumn("SB");TableColumn ba= new TableColumn("BA");TableColumn w= new TableColumn("W");
        TableColumn sv= new TableColumn("SV");TableColumn k= new TableColumn("K");TableColumn era= new TableColumn("ERA");
        TableColumn whip= new TableColumn("WHIP"); TableColumn totalpoints= new TableColumn("Total Points");
        standingsTable.getColumns().addAll(teamName,playersNeeded,moneyLeft,p,r,hr,rbi,sb,ba,w,sv,k,era,whip,totalpoints);
        topWorkSpacePane.getChildren().add(standingsTable); playersNeeded.setMinWidth(125);playersNeeded.setMinWidth(100);
        moneyLeft.setMaxWidth(50);p.setMaxWidth(50);r.setMaxWidth(50);hr.setMaxWidth(50);rbi.setMaxWidth(50);
        sb.setMaxWidth(50);ba.setMaxWidth(50);w.setMaxWidth(50);sv.setMaxWidth(50);k.setMaxWidth(50);era.setMaxWidth(50);
        whip.setMaxWidth(50);totalpoints.setMinWidth(100);
        
        teamName.setCellValueFactory(new PropertyValueFactory<String, String>("name"));
        playersNeeded.setCellValueFactory(new PropertyValueFactory<String, String>("playersNeeded"));
        moneyLeft.setCellValueFactory(new PropertyValueFactory<String, String>("moneyLeft"));
        p.setCellValueFactory(new PropertyValueFactory<String, String>("p"));
        r.setCellValueFactory(new PropertyValueFactory<String, String>("r"));
        hr.setCellValueFactory(new PropertyValueFactory<String, String>("hr"));
        rbi.setCellValueFactory(new PropertyValueFactory<String, String>("rbi"));
        sb.setCellValueFactory(new PropertyValueFactory<String, String>("sb"));
        ba.setCellValueFactory(new PropertyValueFactory<String, String>("ba"));
        w.setCellValueFactory(new PropertyValueFactory<String, String>("w"));
        sv.setCellValueFactory(new PropertyValueFactory<String, String>("sv"));
        k.setCellValueFactory(new PropertyValueFactory<String, String>("k"));
        era.setCellValueFactory(new PropertyValueFactory<String, String>("era"));
        whip.setCellValueFactory(new PropertyValueFactory<String, String>("whip"));
        totalpoints.setCellValueFactory(new PropertyValueFactory<String, String>("totalPoints"));
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
    public TableView getStandingsTable(){
        return standingsTable;
    }
    public TableColumn getPlayersNeededColumn(){
        return playersNeeded;
    }



}
