package net.computeering.newschoolbus.DataPackage;

/**
 * Created by kimtaewoo on 2016-03-22.
 */
public class UserData {
    public static String id;
    public static String pw;
    public static String name;

    public void SetId(String id) {
        this.id = id;
    }
    public void SetName(String name){
        this.name = name;
    }
    public String GetId(){
        return this.id;
    }
    public String GetName(){
        return this.name;
    }

}
