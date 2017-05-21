/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieball.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author shilinlu
 */
public class Hitters {
    private StringProperty teamName;
    private StringProperty lastName;
    private StringProperty firstName;
    private StringProperty qp;
    private IntegerProperty ab;
    private IntegerProperty r;
    private StringProperty h;
    private IntegerProperty hr;
    private IntegerProperty rbi;
    private IntegerProperty sb;
    private StringProperty notes;
    private StringProperty year_of_birth;
    private StringProperty nation_of_birth;
    private StringProperty salary;
    private StringProperty contract;
    private IntegerProperty pick;
    private IntegerProperty estimatedValue;
    public Hitters(){
        teamName=new SimpleStringProperty();
        lastName=new SimpleStringProperty();
        firstName=new SimpleStringProperty();
        qp=new SimpleStringProperty();
        ab=new SimpleIntegerProperty();
        r=new SimpleIntegerProperty();
        h=new SimpleStringProperty();
        hr=new SimpleIntegerProperty();
        rbi=new SimpleIntegerProperty();
        sb=new SimpleIntegerProperty();
        notes=new SimpleStringProperty();
        year_of_birth=new SimpleStringProperty();
        nation_of_birth=new SimpleStringProperty();
        salary=new SimpleStringProperty();
        contract=new SimpleStringProperty();
        pick=new SimpleIntegerProperty();
        estimatedValue=new SimpleIntegerProperty();
    }   
    public int getEstimatedValue(){
        return estimatedValue.get();
    }
    public void setEstimatedValue(int initcontract){
        estimatedValue.set(initcontract);
    }
    
    
    public String getContract(){
        return contract.get();
    }
    public void setContract(String initcontract){
        contract.set(initcontract);
    }
    public String getSalary(){
        return salary.get();
    }
    public void setSalary(String initcontract){
        salary.set(initcontract);
    }
    
    public String getTeamName() {
        return teamName.get();
    }

    public void setTeamName(String initteamName) {
        teamName.set(initteamName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String initlastName) {
        lastName.set(initlastName);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String initfirstName) {
        firstName.set(initfirstName);
    }

    public String getQp() {
        return qp.get();
    }

    public void setQp(String initqp) {
        qp.set(initqp);
    }

    public int getAb() {
        return ab.get();
    }

    public void setAb(int initab) {
        ab.set(initab);
    }

    public int getR() {
        return r.get();
    }

    public void setR(int initr) {
        r.set(initr);
    }

    public String getH() {
        return h.get();
    }

    public void setH(String inith) {
        h.set(inith);
    }

    public int getHr() {
        return hr.get();
    }

    public void setHr(int inithr) {
        hr.set(inithr);
    }

    public int getRbi() {
        return rbi.get();
    }

    public void setRbi(int initrbi) {
        rbi.set(initrbi);
    }

    public int getSb() {
        return sb.get();
    }

    public void setSb(int initsb) {
        sb.set(initsb);
    }

    public String getNotes() {
        return notes.get();
    }

    public void setNotes(String initnotes) {
        notes.set(initnotes);
    }

    public String getYear_of_birth() {
        return year_of_birth.get();
    }

    public void setYear_of_birth(String inityear_of_birth) {
        year_of_birth.set(inityear_of_birth);
    }

    public String getNation_of_birth() {
        return nation_of_birth.get();
    }

    public void setNation_of_birth(String initnation_of_birth) {
        nation_of_birth.set(initnation_of_birth);
    }
    public int getPick() {
        return pick.get();
    }

    public void setPick(int initrbi) {
        pick.set(initrbi);
    }



}