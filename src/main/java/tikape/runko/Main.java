package tikape.runko;
import java.util.ArrayList;
import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import tikape.runko.database.AiheDao;
import tikape.runko.database.DbAihealue;
import tikape.runko.domain.Aihe;
import tikape.runko.database.DbViestiketju;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws Exception {
        //Luodaan tietokannat
        DbAihealue db = new DbAihealue("jdbc:sqlite:aihealueet.db");
        db.init();
        DbViestiketju db_2 = new DbViestiketju("jdbc:sqlite:aihealueet.db");
        db_2.init();
        
      
        AiheDao aiheDao = new AiheDao(db);



         

        //Aiheiden listaus
         get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            //Luodaan aihelista
            ArrayList<String> aiheet = new ArrayList<>(); 
            ArrayList<Aihe> lista = new ArrayList<>();
            int i=1;
            //tÄYTETÄÄN LISTA
            while(true){
                if(aiheDao.findOne(i)==null){
                    break;
            }
            lista.add(aiheDao.findOne(i));
            aiheet.add(lista.get(i-1).getNimi());
            i++;
            
            };
            //Tehdään Hashmap
            map.put("viesti","Aiheet");
            map.put("lista", aiheet);

            
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());             
        
        //aiheen lisäys
        post("/lisays:id", (req, res) -> {
            String id = req.queryParams("id");

            db.add(id);
            res.redirect("/");
            return "";
        });        
         
         
         
         
        //Viestiketjut
        get("/viestiketjut", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti",req.queryParams("id") );
            


            return new ModelAndView(map, "viestiketjut");
         }, new ThymeleafTemplateEngine());         
        
        /*
        post("/lisays:id", (req, res) -> {
            HashMap map = new HashMap<>();
            String id = req.queryParams("id");
            aiheet.add(id);
            db.add(id);
            map.put("viesti","Aiheet");
            map.put("lista", aiheet);
            res.redirect("/");
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());







        get("/opiskelijat/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("opiskelija", opiskelijaDao.findOne(Integer.parseInt(req.params("id"))));

            return new ModelAndView(map, "opiskelija");
    }, new ThymeleafTemplateEngine());
   */     }
}
