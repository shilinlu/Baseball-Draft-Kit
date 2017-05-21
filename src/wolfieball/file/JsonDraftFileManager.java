/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieball.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import wolfieball.data.Hitters;
import wolfieball.data.Pitchers;
import wolfieball.data.Team;
import static wolfieball_draft_kit.StartupConstants.PATH_DRAFT;

/**
 *
 * @author shilinlu
 */
public class JsonDraftFileManager  {
    //  Hitter stats
    String JSON_HITTERS="Hitters";
    String JSON_TEAM="T";
    String JSON_TEAM1="TEAM";
    String JSON_LAST_NAME="LAST_NAME";
    String JSON_FIRST_NAME="FIRST_NAME";
    String JSON_QP="QP";
    String JSON_AB="AB";
    String JSON_R="R";
    String JSON_H="H";
    String JSON_HR="A";
    String JSON_RBI="RBI";
    String JSON_SB="SB";
    String JSON_NOTES="NOTES";
    String JSON_YEAR_OF_BIRTH="YEAR_OF_BIRTH";
    String JSON_NATION_OF_BIRTH="NATION_OF_BIRTH";
    //  Pitcher stats
    String JSON_PITCHERS="Pitchers";
    String JSON_IP="IP";
    String JSON_ER="ER";
    String JSON_W="W";
    String JSON_SV="SV";
    String JSON_BB="BB";
    String JSON_K="K";
    String JSON_EXT = ".json";
    String SLASH = "/";
    
    public void saveDraft(Team draftToSave) throws IOException {
        // BUILD THE FILE PATH
        String draftListing = "" + draftToSave.getName() + draftToSave.getOwner();
        String jsonFilePath = PATH_DRAFT + SLASH + draftListing + JSON_EXT;
        // INIT THE WRITER
        OutputStream os = new FileOutputStream(jsonFilePath);
        JsonWriter jsonWriter = Json.createWriter(os);  
        // THE HITTERS ARRAY
        JsonArray hittersJsonArray = makeHittersJsonArray(draftToSave.getHitters());
        
        // THE LECTURES ARRAY
        JsonArray pitchersJsonArray = makePitchersJsonArray(draftToSave.getPitchers());
        // NOW BUILD THE COURSE USING EVERYTHING WE'VE ALREADY MADE
        JsonObject courseJsonObject = Json.createObjectBuilder()
                                    .add("Team Name", draftToSave.getName())
                                    .add("Owner", draftToSave.getOwner())
                                    
                                    .add(JSON_HITTERS, hittersJsonArray)
                                    .add(JSON_PITCHERS, pitchersJsonArray)
                                    
                .build();
        
        // AND SAVE EVERYTHING AT ONCE
        jsonWriter.writeObject(courseJsonObject);
    
    }

    
    public void loadDraft(Team draftToLoad, String jsonFilePath) throws IOException {
        // LOAD THE JSON FILE WITH ALL THE DATA
        
        JsonObject json = loadJSONFile(jsonFilePath);
        
        // GET THE Hitter ITEMS
        
        draftToLoad.setName(json.getString("Team Name"));
        draftToLoad.setOwner(json.getString("Owner"));
         
        JsonArray jsonHittersItemsArray = json.getJsonArray(JSON_HITTERS);
        
        for (int i = 0; i < jsonHittersItemsArray.size(); i++) {
            System.out.println("loaded the json file");
            JsonObject jso = jsonHittersItemsArray.getJsonObject(i);
            Hitters si = new Hitters();
            si.setTeamName(jso.getString(JSON_TEAM));
            si.setLastName(jso.getString(JSON_LAST_NAME));
            si.setFirstName(jso.getString(JSON_FIRST_NAME));
            si.setQp(jso.getString(JSON_QP));
            si.setAb(Integer.parseInt(jso.getString(JSON_AB)));
            si.setR(Integer.parseInt(jso.getString(JSON_R)));
            si.setH(jso.getString(JSON_H));
            si.setHr(Integer.parseInt(jso.getString(JSON_HR)));
            si.setRbi(Integer.parseInt(jso.getString(JSON_RBI)));
            si.setSb(Integer.parseInt(jso.getString(JSON_SB)));
            si.setNotes(jso.getString(JSON_NOTES));
            si.setYear_of_birth(jso.getString(JSON_YEAR_OF_BIRTH));
            si.setNation_of_birth(jso.getString(JSON_NATION_OF_BIRTH));
            // ADD IT TO THE DRAFT
            draftToLoad.addHitters(si);
            
        }
        System.out.println(draftToLoad.getOwner());
        // GET THE Pitchers
        JsonArray jsonPitchersItemsArray = json.getJsonArray(JSON_PITCHERS);
            for (int i = 0; i < jsonPitchersItemsArray.size(); i++) {
            JsonObject jso = jsonPitchersItemsArray.getJsonObject(i);
            Pitchers si = new Pitchers();
            si.setTeamName(jso.getString(JSON_TEAM));
            si.setLastName(jso.getString(JSON_LAST_NAME));
            si.setFirstName(jso.getString(JSON_FIRST_NAME));
            si.setIP(Integer.parseInt(jso.getString(JSON_IP)));
            si.setSb(Integer.parseInt(jso.getString(JSON_ER)));
            si.setR(Integer.parseInt(jso.getString(JSON_W)));
            si.setHr(Integer.parseInt(jso.getString(JSON_SV)));
            si.setH(Integer.parseInt(jso.getString(JSON_H)));
            si.setBB(Integer.parseInt(jso.getString(JSON_BB)));
            si.setRbi(Integer.parseInt(jso.getString(JSON_K)));
            si.setNotes(jso.getString(JSON_NOTES));
            si.setYear_of_birth(jso.getString(JSON_YEAR_OF_BIRTH));
            si.setNation_of_birth(jso.getString(JSON_NATION_OF_BIRTH));
            // ADD IT TO THE DRAFT
            draftToLoad.addPitchers(si);
        }
    
    }
    
    // LOADS A JSON FILE AS A SINGLE OBJECT AND RETURNS IT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    }    

   
    public ObservableList<Hitters> loadHitters(String jsonFilePath) throws IOException {
        ObservableList<Hitters> hittersArray = loadArrayFromJSONFile(jsonFilePath, JSON_HITTERS);
        ObservableList<Hitters> cleanedArray = FXCollections.observableArrayList();
        for (Hitters s : hittersArray) {
            // GET RID OF ALL THE QUOTE CHARACTERS
           // s = s.replaceAll("\"", "");
            cleanedArray.add(s);
        }
        return cleanedArray;
    }
     public ObservableList<Hitters> loadHittersSaved(String jsonFilePath) throws IOException {
        ObservableList<Hitters> hittersArray = loadArrayFromJSONFileSaved(jsonFilePath, JSON_HITTERS);
        ObservableList<Hitters> cleanedArray = FXCollections.observableArrayList();
        for (Hitters s : hittersArray) {
            // GET RID OF ALL THE QUOTE CHARACTERS
           // s = s.replaceAll("\"", "");
            cleanedArray.add(s);
        }
        return cleanedArray;
    }
    public ObservableList<Pitchers> loadPitchersSaved(String jsonFilePath) throws IOException {
        ObservableList<Pitchers> pitchersArray = loadArrayFromJSONFileSaved1(jsonFilePath, JSON_PITCHERS);
        ObservableList<Pitchers> cleanedArray = FXCollections.observableArrayList();
        for (Pitchers s : pitchersArray) {
            // GET RID OF ALL THE QUOTE CHARACTERS
            //s = s.replaceAll("\"", "");
            cleanedArray.add(s);
        }
        return cleanedArray;
    }
    
     public ObservableList<Pitchers> loadPitchers(String jsonFilePath) throws IOException {
        ObservableList<Pitchers> pitchersArray = loadArrayFromJSONFile1(jsonFilePath, JSON_PITCHERS);
        ObservableList<Pitchers> cleanedArray = FXCollections.observableArrayList();
        for (Pitchers s : pitchersArray) {
           //  GET RID OF ALL THE QUOTE CHARACTERS
          //  s = s.replaceAll("\"", "");
            cleanedArray.add(s);
        }
        return cleanedArray;
    }

    
    private ObservableList<Pitchers> loadArrayFromJSONFile1(String jsonFilePath, String arrayName) throws IOException {
        JsonObject json = loadJSONFile(jsonFilePath);
        ObservableList<Pitchers> items = FXCollections.observableArrayList();
        JsonArray jsonArray = json.getJsonArray(JSON_PITCHERS);
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jso = jsonArray.getJsonObject(i);
            Pitchers si = new Pitchers();
            si.setTeamName(jso.getString(JSON_TEAM1));
            si.setLastName(jso.getString(JSON_LAST_NAME));
            si.setFirstName(jso.getString(JSON_FIRST_NAME));
            //si.setIP(Integer.parseInt(jso.getString(JSON_IP)));
            si.setSb(Integer.parseInt(jso.getString(JSON_ER)));
            si.setR(Integer.parseInt(jso.getString(JSON_W)));
            si.setH(Integer.parseInt(jso.getString(JSON_H)));
            si.setHr(Integer.parseInt(jso.getString(JSON_SV)));
            si.setRbi(Integer.parseInt(jso.getString(JSON_K)));
            si.setBB(Integer.parseInt(jso.getString(JSON_BB)));
            si.setNotes(jso.getString(JSON_NOTES));
            si.setYear_of_birth(jso.getString(JSON_YEAR_OF_BIRTH));
            si.setNation_of_birth(jso.getString(JSON_NATION_OF_BIRTH));
            // ADD IT TO THE DRAFT
            items.add(si);
            
        }
        return items;
    }



    // LOADS AN ARRAY OF A SPECIFIC NAME FROM A JSON FILE AND
    // RETURNS IT AS AN ArrayList FULL OF THE DATA FOUND
    private ObservableList<Hitters> loadArrayFromJSONFile(String jsonFilePath, String arrayName) throws IOException {
        JsonObject json = loadJSONFile(jsonFilePath);
        ObservableList<Hitters> items = FXCollections.observableArrayList();
        JsonArray jsonArray = json.getJsonArray(JSON_HITTERS);
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jso = jsonArray.getJsonObject(i);
            Hitters si = new Hitters();
            si.setTeamName(jso.getString(JSON_TEAM));
            si.setLastName(jso.getString(JSON_LAST_NAME));
            si.setFirstName(jso.getString(JSON_FIRST_NAME));
            si.setQp(jso.getString(JSON_QP));
            si.setAb(Integer.parseInt(jso.getString(JSON_AB)));
            si.setR(Integer.parseInt(jso.getString(JSON_R)));
            si.setH(jso.getString(JSON_H));
            si.setHr(Integer.parseInt(jso.getString(JSON_HR)));
            si.setRbi(Integer.parseInt(jso.getString(JSON_RBI)));
            si.setSb(Integer.parseInt(jso.getString(JSON_SB)));
            si.setNotes(jso.getString(JSON_NOTES));
           si.setYear_of_birth(jso.getString(JSON_YEAR_OF_BIRTH));
            si.setNation_of_birth(jso.getString(JSON_NATION_OF_BIRTH));
            // ADD IT TO THE DRAFT
            items.add(si);
            
        }
        return items;
    }
     private ObservableList<Hitters> loadArrayFromJSONFileSaved(String jsonFilePath, String arrayName) throws IOException {
        JsonObject json = loadJSONFile(jsonFilePath);
        ObservableList<Hitters> items = FXCollections.observableArrayList();
        
        JsonArray jsonArray = json.getJsonArray(JSON_HITTERS);
        
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jso = jsonArray.getJsonObject(i);
            Hitters si = new Hitters();
            si.setTeamName(jso.getString(JSON_TEAM));
            si.setLastName(jso.getString(JSON_LAST_NAME));
            si.setFirstName(jso.getString(JSON_FIRST_NAME));
            si.setQp(jso.getString(JSON_QP));
            //si.setAb(Integer.parseInt(jso.getString(JSON_AB)));
           // si.setR(Integer.parseInt(jso.getString(JSON_R)));
           // si.setH(jso.getString(JSON_H));
           // si.setHr(Integer.parseInt(jso.getString(JSON_HR)));
           // si.setRbi(Integer.parseInt(jso.getString(JSON_RBI)));
            //si.setSb(Integer.parseInt(jso.getString(JSON_SB)));
      //      si.setNotes(jso.getString(JSON_NOTES));
            //si.setYear_of_birth(jso.getString(JSON_YEAR_OF_BIRTH));
           // si.setNation_of_birth(jso.getString(JSON_NATION_OF_BIRTH));
            // ADD IT TO THE DRAFT
            items.add(si);
            
        }
        return items;
    }
      private ObservableList<Pitchers> loadArrayFromJSONFileSaved1(String jsonFilePath, String arrayName) throws IOException {
        JsonObject json = loadJSONFile(jsonFilePath);
        ObservableList<Pitchers> items = FXCollections.observableArrayList();
        
        JsonArray jsonArray = json.getJsonArray(JSON_PITCHERS);
        
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jso = jsonArray.getJsonObject(i);
            Pitchers si = new Pitchers();
            si.setTeamName(jso.getString(JSON_TEAM));
            si.setLastName(jso.getString(JSON_LAST_NAME));
            si.setFirstName(jso.getString(JSON_FIRST_NAME));
            si.setQp(jso.getString(JSON_QP));
            //si.setAb(Integer.parseInt(jso.getString(JSON_AB)));
           // si.setR(Integer.parseInt(jso.getString(JSON_R)));
           // si.setH(jso.getString(JSON_H));
           // si.setHr(Integer.parseInt(jso.getString(JSON_HR)));
           // si.setRbi(Integer.parseInt(jso.getString(JSON_RBI)));
            //si.setSb(Integer.parseInt(jso.getString(JSON_SB)));
      //      si.setNotes(jso.getString(JSON_NOTES));
            //si.setYear_of_birth(jso.getString(JSON_YEAR_OF_BIRTH));
           // si.setNation_of_birth(jso.getString(JSON_NATION_OF_BIRTH));
            // ADD IT TO THE DRAFT
            items.add(si);
            
        }
        return items;
    }
    // MAKE AN ARRAY OF SCHEDULE ITEMS
    private JsonArray makeHittersJsonArray(ObservableList<Hitters> data) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Hitters si : data) {
            jsb.add(makeHittersJsonObject(si));
        }
        JsonArray jA = jsb.build();
        return jA;
    }
    
    // MAKE AN ARRAY OF LECTURE ITEMS
    private JsonArray makePitchersJsonArray(ObservableList<Pitchers> data) {
        JsonArrayBuilder jsb = Json.createArrayBuilder();
        for (Pitchers l : data) {
            jsb.add(makePitchersJsonObject(l));
        }
        JsonArray jA = jsb.build();
        return jA;
    }
    // MAKES AND RETURNS A JSON OBJECT FOR THE PROVIDED SCHEDULE ITEM
    private JsonObject makeHittersJsonObject(Hitters scheduleItem) {
        
        JsonObject jso = Json.createObjectBuilder().add(JSON_LAST_NAME, scheduleItem.getLastName())
                                                    .add(JSON_TEAM, scheduleItem.getTeamName())
                                                    .add(JSON_FIRST_NAME, scheduleItem.getFirstName())
                                                    .add(JSON_QP,scheduleItem.getQp())
                                                    .add(JSON_R,scheduleItem.getR())
                                                    .add(JSON_HR,scheduleItem.getHr())
                                                    .add(JSON_RBI,scheduleItem.getRbi())
                                                    .add(JSON_SB,scheduleItem.getSb())
                                                    .build();
        return jso;
    }
    
    // MAKES AND RETURNS A JSON OBJECT FOR THE PROVIDED LECTURE
    private JsonObject makePitchersJsonObject(Pitchers lecture) {
        JsonObject jso = Json.createObjectBuilder().add(JSON_LAST_NAME, lecture.getLastName())
                                                    .add(JSON_FIRST_NAME, lecture.getFirstName())
                                                    .add(JSON_TEAM, lecture.getTeamName())
                                                    .add(JSON_QP,lecture.getQp())
                                                    .add(JSON_R,lecture.getR())
                                                    .add(JSON_HR,lecture.getHr())
                                                    .add(JSON_RBI,lecture.getRbi())
                                                    .add(JSON_SB,lecture.getSb())
                                                .build();
        return jso;
    }






}


