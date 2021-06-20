package com.igalda.scrimgg.pers;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.igalda.scrimgg.JsonTask;
import com.igalda.scrimgg.dom.Champ;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public abstract class PersistenciaChamp {
    private static String resourceURL="https://api.pandascore.co/lol/champions?page[size]=100&page[number]=1";
    private static String resourceURL2="https://api.pandascore.co/lol/champions?page[size]=100&page[number]=2";
    private static String token = "&token=g-IIlf_PmPh7RYJFHuZImu2HzJpz_0QqZsPZpAk4H0inzGB-GdQ";

    public static List<Champ> getAllChamps(){
        JsonTask jsonTask = new JsonTask();
        JsonTask jsonTask2 = new JsonTask();
        List<Champ> ret = new ArrayList<Champ>();
        Champ aux;
        //Log.d("Pruebas", jsonData);
        try {
            String jsonData =  jsonTask.execute(resourceURL+token).get();
            JSONArray array = new JSONArray(jsonData);
           // List<String> champsArray = array.get(1);
            for(int i=0; i<array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                aux = getChamp(jsonObject);
                ret.add(aux);
            }
            String jsonData2 = jsonTask2.execute(resourceURL2+token).get();
            JSONArray array2 = new JSONArray(jsonData2);
            for(int i=0;i<array2.length();i++){
                JSONObject jsonObject = array2.getJSONObject(i);
                aux = getChamp(jsonObject);
                ret.add(aux);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public static Champ getChamp(JSONObject obj) throws JSONException {
        Champ ret;
        float armor = Float.parseFloat(obj.getString("armor"));
        float armorPerLevel = Float.parseFloat(obj.getString("armorperlevel"));
        float attackDamage = Float.parseFloat((obj.getString("attackdamage")));
        float attackDamagePerLevel = Float.parseFloat((obj.getString("attackdamageperlevel")));
        float attackRange = Float.parseFloat((obj.getString("attackrange")));
        float attackSpeedPerLevel = Float.parseFloat((obj.getString("attackspeedperlevel")));
        String bigImgURL = obj.getString("big_image_url");
        float crit = Float.parseFloat(obj.getString("crit"));
        float critPerLevel = Float.parseFloat(obj.getString("critperlevel"));
        float hp = Float.parseFloat(obj.getString("hp"));
        float hpPerLevel = Float.parseFloat(obj.getString("hpperlevel"));
        float hpRegen = Float.parseFloat(obj.getString("hpregen"));
        float hpRegenPerLevel = Float.parseFloat((obj.getString("hpregenperlevel")));
        float id = Float.parseFloat(obj.getString("id"));
        String imageURL = obj.getString("image_url");
        float moveSpeed = Float.parseFloat(obj.getString("movespeed"));
        float mp = Float.parseFloat(obj.getString("mp"));
        float mpPerLevel = Float.parseFloat(obj.getString("mpperlevel"));
        float mpRegen = Float.parseFloat(obj.getString("mpregen"));
        float mpRegenPerLevel = Float.parseFloat(obj.getString("mpregenperlevel"));
        String name = obj.getString("name");
        float spellBlock = Float.parseFloat(obj.getString("spellblock"));
        float spellBlockPerLevel = Float.parseFloat(obj.getString("spellblockperlevel"));
        ret = new Champ(armor,armorPerLevel,attackDamage,attackDamagePerLevel,attackRange,attackSpeedPerLevel,bigImgURL,crit,critPerLevel,hp,hpPerLevel,hpRegen,hpRegenPerLevel,id,imageURL,moveSpeed,mp,mpPerLevel,mpRegen,mpRegenPerLevel,name,spellBlock,spellBlockPerLevel);

        return ret;
    }
}
