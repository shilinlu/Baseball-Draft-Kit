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
public class Pitchers {
    private StringProperty teamName;
    private StringProperty lastName;
    private StringProperty firstName;
    private IntegerProperty IP;
    private IntegerProperty sb;
    private IntegerProperty r;
    private IntegerProperty hr;
    private IntegerProperty H;
    private IntegerProperty BB;
    private IntegerProperty rbi;
    private StringProperty notes;
    private StringProperty year_of_birth;
    private StringProperty nation_of_birth;
    private StringProperty qp;
    private StringProperty contract;
    private StringProperty salary;
    private IntegerProperty pick;
    private IntegerProperty estimatedValue;
    public Pitchers(){
    teamName=new SimpleStringProperty();
    lastName=new SimpleStringProperty();
    firstName=new SimpleStringProperty();
    IP=new SimpleIntegerProperty();
    sb=new SimpleIntegerProperty();
    r=new SimpleIntegerProperty();
    hr=new SimpleIntegerProperty();
    H=new SimpleIntegerProperty();
    BB=new SimpleIntegerProperty();
    rbi=new SimpleIntegerProperty();
    notes=new SimpleStringProperty();
    year_of_birth=new SimpleStringProperty();
    nation_of_birth=new SimpleStringProperty();
    qp=new SimpleStringProperty();
    contract=new SimpleStringProperty();
    salary=new SimpleStringProperty();
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
    public String getQp(){
        return qp.get();
    }
    public void setQp(String initqp) {
        qp.set(initqp);
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

    public int getIP() {
        return IP.get();
    }

    public void setIP(int initIP) {
        IP.set(initIP);
    }

    public int getSb() {
        return sb.get();
    }

    public void setSb(int initER) {
       sb.set(initER);
    }

    public int getR() {
        return r.get();
    }

    public void setR(int initW) {
        r.set(initW);
    }

    public int getHr() {
        return hr.get();
    }

    public void setHr(int initSV) {
        hr.set(initSV);
    }

    public int getH() {
        return H.get();
    }

    public void setH(int initH) {
        H.set(initH);
    }

    public int getBB() {
        return BB.get();
    }

    public void setBB(int initBB) {
        BB.set(initBB);
    }

    public int getRbi() {
        return rbi.get();
    }

    public void setRbi(int initK) {
        rbi.set(initK);
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