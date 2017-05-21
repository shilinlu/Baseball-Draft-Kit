/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieball.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wolfieball.gui.AvailablePlayerScreen;
import wolfieball.gui.Wolfie_GUI;

/**
 *
 * @author shilinlu
 */
public class DraftDataManager {
    // THIS IS THE COURSE BEING EDITED
    Team team;
    ObservableList <Team> teams;
    // THIS IS THE UI, WHICH MUST BE UPDATED
    // WHENEVER OUR MODEL'S DATA CHANGES
    DraftDataView view;
    AvailablePlayerScreen screen;
    public DraftDataManager( DraftDataView initView  ) {
            view=initView;
        team=new Team();
        teams=FXCollections.observableArrayList();
        //view = initView;
        //course = new Course(lastInstructor);
    }
    public Team getTeam() {
        return team;
    }
    public ObservableList getTeams(){
        return teams;
    }
    
    /**
     * Resets the course to its default initialized settings, triggering
     * the UI to reflect these changes.
     */
    public void reset() {
        // CLEAR ALL THE COURSE VALUES
        team.setName("cool");
        /**
        
        course.setNumber(DEFAULT_NUM);
        course.setTitle(DEFAULT_TEXT);
        course.setSemester(DEFAULT_SEMESTER);
        course.setYear(LocalDate.now().getYear());
        LocalDate nextMonday = getNextMonday();
        course.setStartingMonday(nextMonday);
        course.setEndingFriday(getNextFriday(nextMonday));
        course.clearLectureDays();
        course.clearPages();
        */
        // AND THEN FORCE THE UI TO RELOAD THE UPDATED COURSE
        view.reloadCourse(team);
    }
    public DraftDataView getDraftDataView(){
        return view;
    }


}
