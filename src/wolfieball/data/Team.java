/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wolfieball.data;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import static javafx.beans.property.IntegerProperty.integerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author shilinlu
 */
public class Team {
    private StringProperty name;
    ObservableList<Hitters> hitters;
    ObservableList<Pitchers> pitchers;
    private String owner;
    private IntegerProperty playersNeeded;
    private IntegerProperty moneyLeft;
    private IntegerProperty p;
    private IntegerProperty r;
    private IntegerProperty hr;
    private IntegerProperty rbi;
    private IntegerProperty sb;
    private DoubleProperty ba;
    private IntegerProperty w;
    private IntegerProperty sv;
    private IntegerProperty k;
    private DoubleProperty era;
    private DoubleProperty whip;
    private IntegerProperty totalPoints;
    public Team(){
    name=new SimpleStringProperty();
    hitters= FXCollections.observableArrayList();
    pitchers= FXCollections.observableArrayList();
    moneyLeft=new SimpleIntegerProperty(260);
    p=new SimpleIntegerProperty(11);
    r=new SimpleIntegerProperty();
    hr=new SimpleIntegerProperty();
    rbi=new SimpleIntegerProperty();
    sb=new SimpleIntegerProperty();
    ba=new SimpleDoubleProperty();
    w=new SimpleIntegerProperty();
    sv=new SimpleIntegerProperty();
    k=new SimpleIntegerProperty();
    era=new SimpleDoubleProperty();
    whip=new SimpleDoubleProperty();
    playersNeeded=new SimpleIntegerProperty(23);
    totalPoints=new SimpleIntegerProperty();
    }

public int getTotalPoints(){
    return totalPoints.get();
}
public void setTotalPoints(int initpp){
    totalPoints.set(initpp);
 }          
 public int getPlayersNeeded(){
    return playersNeeded.get();
}
public void setplayerNeeded(int initpp){
    playersNeeded.set(initpp);
 }          
public Double getEra(){
    return era.get();
}
public void setEra(Double initpp){
    era.set(initpp);
 }          
 public Double getWhip(){
    return whip.get();
}
public void setWhip(Double initpp){
    whip.set(initpp);
 }         
public int getSv(){
    return sv.get();
}
public void setSv(int initpp){
    sv.set(initpp);
 }   
 public int getK(){
    return k.get();
}
public void setK(int initpp){
    k.set(initpp);
 }      
public int getW(){
    return w.get();
}
public void setW(int initpp){
    w.set(initpp);
 }   
 public Double getBa(){
    return ba.get();
}
public void setBa(Double initpp){
    ba.set(initpp);
 }      
    
 public int getSb(){
    return sb.get();
}
public void setSb(int initpp){
    sb.set(initpp);
 }   
    
public int getR(){
    return r.get();
}
public void setR(int initpp){
    r.set(initpp);
 }   
 public int getHr(){
    return hr.get();
}
public void setHr(int initpp){
    hr.set(initpp);
 }   
public int getRbi(){
    return rbi.get();
}
public void setRbi(int initpp){
    rbi.set(initpp);
 }    
public int getP(){
    return p.get();
}
public void setP(int initpp){
    p.set(initpp);
 }


public int getMoneyLeft(){
    return moneyLeft.get();
}
public void setMoneyLeft(int initmoneyLeft){
    moneyLeft.set(initmoneyLeft);
}    
public String getOwner(){
    return owner;
}
public void setOwner(String owner){
    this.owner=owner;
}

public void setName(String initname){
    name.set(initname);
}

public String getName(){
    return name.get();
}

public ObservableList<Hitters> getHitters() {
        return hitters;
    }

public ObservableList<Pitchers> getPitchers() {
        return pitchers;
    }
public void setHitters(ObservableList<Hitters> hitters){
    this.hitters=hitters;
    }
public void setPitchers(ObservableList<Pitchers> pitchers){
    this.pitchers=pitchers;
    }

public void addHitters(Hitters l) {
        hitters.add(l);
    }
public void addPitchers(Pitchers l) {
        pitchers.add(l);
    }
public void addPlayer(Object player){
    if(player instanceof Hitters){
        hitters.add((Hitters)player);
    }
    if(player instanceof Pitchers){
        pitchers.add((Pitchers)player);
    }

}

public void removeHitter(Hitters itemToRemove) {
        hitters.remove(itemToRemove);
    }
public void removePlayer(Object player){
    if(player instanceof Hitters){
        hitters.remove(player);
    }
    if(player instanceof Pitchers){
        pitchers.remove(player);
    }

}

}
