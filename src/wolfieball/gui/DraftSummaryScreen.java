/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieball.gui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import properties_manager.PropertiesManager;
import wolfieball.controller.PlayerEditController;
import wolfieball.data.DraftDataManager;
import wolfieball.data.DraftDataView;
import wolfieball.data.Hitters;
import wolfieball.data.Team;
import wolfieball_draft_kit.PropertyType;
import static wolfieball_draft_kit.StartupConstants.PATH_IMAGES;

/**
 *
 * @author shilinlu
 */
public class DraftSummaryScreen {
    BorderPane workSpacePane;
    VBox topWorkSpacePane;
    VBox fantasyStandingsPane;
    VBox fantasyStandingsItemsBox;
    ScrollPane workSpaceScrollPane;
    SplitPane topWorkSpaceSplitPane;
    Button starButton;
    Button playButton;
    Button pauseButton;
    Label fantasyStandingLabel;
    TableView table = new TableView();
    PlayerEditController playerController;
    AvailablePlayerScreen screen;
    MessageDialog messageDialog;
    YesNoCancelDialog yesNoCancelDialog;
    DraftDataManager dataManager;
    static final String CLASS_BORDERED_PANE = "bordered_pane";
    static final String CLASS_HEADING_LABEL = "heading_label";
    HBox pictureBox=new HBox();
    
    public DraftSummaryScreen(){
        starButton = initChildButton(pictureBox, PropertyType.STAR_ICON, PropertyType.STAR_TOOLTIP, false);
        playButton = initChildButton(pictureBox, PropertyType.PLAY_ICON, PropertyType.START_TOOLTIP, false);
        pauseButton = initChildButton(pictureBox, PropertyType.PAUSE_ICON, PropertyType.PAUSE_TOOLTIP, false);
    }

   
    
    
    //}
    
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
        fantasyStandingLabel= initChildLabel(topWorkSpacePane, PropertyType.DRAFT_SUMMARY_LABEL, CLASS_HEADING_LABEL);
        // AND NOW ADD THE SPLIT PANE
        topWorkSpacePane.getChildren().add(topWorkSpaceSplitPane);
        fantasyStandingsItemsBox = new VBox();
        
        fantasyStandingsPane.setMinSize(400, 600);
        fantasyStandingsPane.getChildren().add(fantasyStandingsItemsBox);
        
       // HBox pictureBox=new HBox();
        //starButton = initChildButton(pictureBox, PropertyType.STAR_ICON, PropertyType.STAR_TOOLTIP, false);
       // pauseButton = initChildButton(pictureBox, PropertyType.PAUSE_ICON, PropertyType.PAUSE_TOOLTIP, false);
        //playButton = initChildButton(pictureBox, PropertyType.PLAY_ICON, PropertyType.START_TOOLTIP, false);
        topWorkSpacePane.getChildren().add(pictureBox);
        
        TableColumn first1= new TableColumn("First"); TableColumn last1= new TableColumn("Last");TableColumn team1= new TableColumn("Team");
        TableColumn pick= new TableColumn("Pick # "); TableColumn contract= new TableColumn("Contract");TableColumn salary= new TableColumn("Salary");
        table.getColumns().addAll(pick,first1,last1,team1,contract,salary);
        topWorkSpacePane.getChildren().add(table);
        table.setMaxWidth(715); first1.setMinWidth(125); last1.setMinWidth(125);team1.setMinWidth(125);contract.setMinWidth(125);salary.setMinWidth(125);
        pick.setMinWidth(90);
    
        salary.setCellValueFactory(new PropertyValueFactory<String, String>("salary"));
        contract.setCellValueFactory(new PropertyValueFactory<String, String>("contract"));
        first1.setCellValueFactory(new PropertyValueFactory<String, String>("firstName"));
        last1.setCellValueFactory(new PropertyValueFactory<String, String>("lastName"));
        team1.setCellValueFactory(new PropertyValueFactory<String, String>("teamName"));
        pick.setCellValueFactory(new PropertyValueFactory<String, String>("pick"));
       // table.setItems(screen.getPlayerToImport());
        
      //System.out.println(hittersToImport.get(0).getSalary()+"draftsummaryScreen");
    //playerController = new PlayerEditController( dataManager.getTeam(), messageDialog, yesNoCancelDialog);
        //table.setOnMouseClicked(e -> {
           // if (e.getClickCount() == 2) {
                // OPEN UP THE SCHEDULE ITEM EDITOR
               // if(table.getSelectionModel().getSelectedItem() instanceof Hitters){
                   // Hitters si = (Hitters) table.getSelectionModel().getSelectedItem();
                    //playerController.handleEditPlayerRequestDraft(this, si);
                    
                    //System.out.println(si.getSalary()); System.out.println(si.getContract());
                    
                //}
            //}
   // });
    
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
    public TableView getTable(){
        return table;
    }

 public DraftDataManager getDataManager() {
        return dataManager;
    }

   

public Button getStarButton(){
    return starButton;
}
public Button getPlayButton(){
    return playButton;
}
public Button getPauseButton(){
    return pauseButton;
}

}


