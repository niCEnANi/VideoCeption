import dtParsing.Parsing;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import topology.CreateBolt;
import topology.CreateStormMainClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class MainClass {
    public static void main(String args[]) throws IOException, JSONException {

        String Path = "C:\\Users\\Anil Cherukuri\\Desktop\\StormTopology\\src\\main\\java\\";
        /*
        Getting model from mongo DB
         */
//        String urlString = "https://api.mlab.com/api/1/databases/kafkaconsumer/collections/model/581b8d16bd966f752bf04520?apiKey=NV6PEwYt13rsIJu21LnqTqGtnC_pZv3X";
        String API_KEY = "ztCS-x7D_40BrPn_vDa4LXIqW_VXO5qk";
        String DATABASE_NAME = "videoception";
        String COLLECTION_NAME = "baseballmodel";
        String urlString = "https://api.mlab.com/api/1/databases/" +
                DATABASE_NAME + "/collections/" + COLLECTION_NAME + "?apiKey=" + API_KEY;

        StringBuilder sb1 = new StringBuilder();
        InputStreamReader  in = null;
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        if (conn != null && conn.getInputStream() != null) {
            in = new InputStreamReader(conn.getInputStream(),
                    Charset.defaultCharset());
            InputStream is = conn.getInputStream();


            BufferedReader bufferedReader = new BufferedReader(in);
            if (bufferedReader != null) {
                int cp;
                while ((cp = bufferedReader.read()) != -1) {
                    sb1.append((char) cp);
                }
                bufferedReader.close();
            }
        }

        JSONArray jsonArray = new JSONArray(sb1.toString());

//        JSONObject jsonObject = new JSONObject(sb1.toString());
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        System.out.println("Model Object:" + jsonObject);
        String model = jsonObject.get("Model").toString();
        model = model.replaceAll("(?m)^DecisionTreeModel.*", "");
        System.out.println("JSONModel: " + model);

        System.out.println("Got Model from Mongo");

         /*
        From the input model, derive paths for each class
         */

        Parsing parsing1 = new Parsing(model, "data/baseballbat.txt", "1.0");
        Parsing parsing2 = new Parsing(model, "data/baseballball.txt", "2.0");
//        Parsing parsing3 = new Parsing(model, "data/aish.txt", "3.0");
//        Parsing parsing4 = new Parsing(model, "data/Class4.txt", "4.0");

        System.out.println("Derived Class Paths");
        /*
        Create Storm Topology
        Bolt for Each Path
         */

        String[] bolts = {"baseballbat", "baseballball"};
        String[] classDTpaths = {"data/baseballbat.txt", "data/baseballball.txt"};

        /*
        Create Bolt Class files
         */
        for(int i=0; i<bolts.length; i++){
            CreateBolt createBolt = new CreateBolt(Path, bolts[i], classDTpaths[i]);
        }

        /*
        Creating Storm Main Class
         */

        String spoutName = "kafka_spout_classification";
        StringBuilder sb = new StringBuilder();
        String spout = "        topology.setSpout(\"" + spoutName+ "\", new KafkaSpout(kafkaConf), 4);";
        sb.append(spout).append("\n");
        for(int i=0; i<bolts.length; i++){
            String boltName = bolts[i] + "Bolt";
            String s2 = "        topology.setBolt(\"" + bolts[i] + "\", new " +boltName +"(), 4).shuffleGrouping(\"" + spoutName+ "\");";
            sb.append(s2);
            sb.append("\n");
        }


        CreateStormMainClass createStromMainClass = new CreateStormMainClass(sb.toString(), Path);

        System.out.println("Created Storm Topology");
    }
}