/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieball.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import wolfieball.data.Hitters;
import wolfieball.data.Pitchers;
import wolfieball.data.Team;

/**
 *
 * @author shilinlu
 */
public interface DraftFileManager {
   
    public void                 saveDraft(Team draftToSave) throws IOException;
    public void                 loadDraft(Team draftToLoad, String coursePath) throws IOException;
    public ObservableList<Hitters> loadHitters(String filePath) throws IOException;
    public ObservableList<Pitchers> loadPitchers(String filePath) throws IOException;
}
