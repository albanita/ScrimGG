package com.igalda.scrimgg.pers;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.igalda.scrimgg.JsonTask;
import com.igalda.scrimgg.dom.LeagueProfesional;
import com.igalda.scrimgg.dom.TeamProfesional;
import com.igalda.scrimgg.dom.TournamentProfesional;
import com.igalda.scrimgg.dom.WinnerTypeProfesional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class PersistenciaLigaProfesional {

    private static String resourceURL="https://api.pandascore.co/lol/tournaments";
    private static String token = "?token=g-IIlf_PmPh7RYJFHuZImu2HzJpz_0QqZsPZpAk4H0inzGB-GdQ";


    public PersistenciaLigaProfesional(){}

    /**
     *
     * @param leagueNames
     * @return una lista con los torneos de ligas profesionales cuyos nombres empiezan por los que se dan en la lista pasada como parametro; si a la lista se le pasa un "*" (asterísco),
     * o se deja vacía, cogerá todos los torneos de todas las ligas
     */
    public static List<TournamentProfesional> getLigasProfesionales(List<String> leagueNames){
        // para coger la liga internacional MSI, hay que pasar a la lista un elemento llamado "Mid-"

        List<TournamentProfesional> ltp = new ArrayList<TournamentProfesional>();
        JsonTask jsonTask = new JsonTask();
        try {
            String jsonData =  jsonTask.execute(resourceURL+token).get();
            //Log.d("Pruebas", jsonData);

            JSONArray array = new JSONArray(jsonData);

            for(int i=0; i<array.length(); i++){
                JSONObject jsonObject = array.getJSONObject(i);

                String id = jsonObject.getString("id");
                String dBegin = jsonObject.getString("begin_at").split("T")[0];
                String dEnd = jsonObject.getString("end_at").split("T")[0];

                Date begin = null; //new Date(System.currentTimeMillis());
                Date end = null; //new Date(System.currentTimeMillis());

                /*Log.d("Pruebas", "Begin: " + dBegin);
                Log.d("Pruebas", "\nEnd: " + dEnd);*/
                if(!dBegin.equalsIgnoreCase("null")){
                    begin = new SimpleDateFormat("yyyy-MM-dd").parse(dBegin);
                }
                if(!dEnd.equalsIgnoreCase("null")){
                    end = new SimpleDateFormat("yyyy-MM-dd").parse(dEnd);
                }


                JSONObject leagueJSON = jsonObject.getJSONObject("league");
                String leagueID = leagueJSON.getString("id");
                String leagueIMG = leagueJSON.getString("image_url");
                String leagueName = leagueJSON.getString("name");
                LeagueProfesional leagueProfesional = new LeagueProfesional(leagueID, leagueIMG, leagueName);

                JSONArray jsonTeams = jsonObject.getJSONArray("teams");
                List<TeamProfesional> teams = new ArrayList<TeamProfesional>();
                for(int j=0; j<jsonTeams.length(); j++){
                    JSONObject aTeam = jsonTeams.getJSONObject(j);
                    String tId = aTeam.getString("id");
                    String acronym = aTeam.getString("acronym");
                    String imgUrl = aTeam.getString("image_url");
                    String loc = aTeam.getString("location");
                    String tName = aTeam.getString("name");
                    teams.add(new TeamProfesional(tId, acronym, imgUrl, loc, tName));
                }

                String winnerID  = jsonObject.getString("winner_id");

                String wType = jsonObject.getString("winner_type");
                WinnerTypeProfesional winnerTypeProfesional;
                if(wType.equalsIgnoreCase("Team")){
                    winnerTypeProfesional = WinnerTypeProfesional.TEAM;
                }
                else{
                    winnerTypeProfesional = WinnerTypeProfesional.PLAYER;
                }

                TournamentProfesional tp = new TournamentProfesional(id, begin, end, leagueProfesional, teams, winnerID, winnerTypeProfesional);
                if(leagueNames.contains("*") || leagueNames.isEmpty()){
                    ltp.add(tp);
                }
                else{
                    for(String n: leagueNames){
                        if(tp.getLeague().getName().toUpperCase().startsWith(n.toUpperCase())){
                            ltp.add(tp);
                        }
                    }
                }
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ltp;
    }
}
