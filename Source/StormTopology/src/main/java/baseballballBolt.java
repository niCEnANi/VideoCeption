import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class baseballballBolt extends BaseBasicBolt {
    private static final Logger LOG = LoggerFactory.getLogger(baseballballBolt.class);
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        try {
            String s = tuple.getString(0);
            String features = s;

            double[] feature = fromString(s);
            Boolean check = checkbaseballball(feature);
            insertIntoMongoDB(check);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("context","status"));
    }

    private static double[] fromString(String string) {
        String[] strings = string.split(" ");
        double result[] = new double[strings.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Double.parseDouble(strings[i]);
        }
        return result;
    }

    public static void insertIntoMongoDB(Boolean check) {
        String API_KEY = "ztCS-x7D_40BrPn_vDa4LXIqW_VXO5qk";
        String DATABASE_NAME = "videoception";
        String COLLECTION_NAME = "baseballmodel";
        String urlString = "https://api.mlab.com/api/1/databases/" +
                DATABASE_NAME + "/collections/" + COLLECTION_NAME + "?apiKey=" + API_KEY;
        LOG.info(urlString);

        StringBuilder result = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Context", "baseballball");
            jsonObject.put("Decision", check);
            //jsonObject.put("Timestamp", System.currentTimeMillis());
            writer.write(jsonObject.toString());
            LOG.info(jsonObject.toString());
            writer.close();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Uploaded data to Mongo");

    }

    public Boolean checkbaseballball(double[] feature) {
        

  if (feature[2] <= 22.0) 
   if (feature[0] <= 116.0) 
    if (feature[0] <= 53.0) 
     if (feature[0] <= 36.0) 
      return false;
     else if (feature[0] > 36.0) 
      return false;
    else if (feature[0] > 53.0) 
     if (feature[4] <= 104.0) 
      return false;
     else if (feature[4] > 104.0) 
      if (feature[0] <= 82.0) 
       return false;
      else if (feature[0] > 82.0) 
       return false;
   else if (feature[0] > 116.0) 
    if (feature[5] <= 119.0) 
     if (feature[5] <= 56.0) 
      if (feature[1] <= 65.0) 
       return false;
      else if (feature[1] > 65.0) 
       return false;
     else if (feature[5] > 56.0) 
      if (feature[0] <= 122.0) 
       return false;
      else if (feature[0] > 122.0) 
       return false;
    else if (feature[5] > 119.0) 
     if (feature[4] <= 109.0) 
      if (feature[0] <= 118.0) 
       return false;
      else if (feature[0] > 118.0) 
       return false;
     else if (feature[4] > 109.0) 
      if (feature[0] <= 118.0) 
       return false;
      else if (feature[0] > 118.0) 
       return false;
  else if (feature[2] > 22.0) 
   if (feature[9] <= 63.0) 
    if (feature[0] <= 36.0) 
     if (feature[3] <= 118.0) 
      return false;
     else if (feature[3] > 118.0) 
      if (feature[4] <= 123.0) 
       return false;
      else if (feature[4] > 123.0) 
       return false;
    else if (feature[0] > 36.0) 
     if (feature[3] <= 58.0) 
      if (feature[0] <= 127.0) 
       return false;
      else if (feature[0] > 127.0) 
       return false;
     else if (feature[3] > 58.0) 
      if (feature[0] <= 53.0) 
       return false;
      else if (feature[0] > 53.0) 
       return false;
   else if (feature[9] > 63.0) 
    if (feature[0] <= 14.0) 
     if (feature[1] <= 12.0) 
      if (feature[2] <= 48.0) 
       return false;
      else if (feature[2] > 48.0) 
       return false;
     else if (feature[1] > 12.0) 
      if (feature[2] <= 48.0) 
       return false;
      else if (feature[2] > 48.0) 
       return false;
    else if (feature[0] > 14.0) 
     if (feature[0] <= 75.0) 
      if (feature[1] <= 65.0) 
       return false;
      else if (feature[1] > 65.0) 
       return false;
     else if (feature[0] > 75.0) 
      if (feature[0] <= 88.0) 
       return false;
      else if (feature[0] > 88.0) 
       return false;
    return false;
    }
}
