/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelloWorldBuilder;

import static HelloWorldBuilder.BasicAPICall.NUMBER_OF_RETRIES;
import static HelloWorldBuilder.BasicAPICall.RETRY_INTERVAL_SECONDS;
import static HelloWorldBuilder.BasicAPICall.buildHttpGetRequest;
import static HelloWorldBuilder.BasicAPICall.getAuthentication;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author Administrator
 */
public class SkytapTplRestAPICall {
        private static String buildCheckTemplateURL(String id) {

        JenkinsLogger.log("Building request url ...");

        StringBuilder sb = new StringBuilder("https://cloud.skytap.com/");
        sb.append("templates/");
        sb.append(id);

        JenkinsLogger.log("Request URL: " + sb.toString()+".json");
        return sb.toString();

        // https://cloud.skytap.com/templates/322249
    }

   
    
    public static SkytapTemplate GetTemplate(String templateID)//This function Not been implemented yet
    {
        //SkytapTemplate skyTpl; 
        
        Boolean templateAvailable = false;

        JenkinsLogger.log("Checking availability of template with id: "
                + templateID);

        // build busy check request
        String requestURL = buildCheckTemplateURL(templateID);
        String authCredentials = getAuthentication();

        HttpGet hg = buildHttpGetRequest(requestURL,
                authCredentials);

        // repeatedly execute request until template is not busy
        String httpRespBody=null;

        JsonParser parser = new JsonParser();
        
        try {
            int attemptsTime = 0;
            while (!templateAvailable && (attemptsTime < NUMBER_OF_RETRIES)) {
                httpRespBody = BasicAPICall.executeHttpRequest(hg);

                JsonElement je = parser.parse(httpRespBody);
                JsonObject jo = je.getAsJsonObject();

                if (jo.get("busy").isJsonNull()) {
                    templateAvailable = true;
                    JenkinsLogger.log("Template is available.");
                } else {
                    templateAvailable = false;
                    JenkinsLogger.log("Template is busy.");

                    // wait before trying again
                    int sleepTime = RETRY_INTERVAL_SECONDS;
                    JenkinsLogger.log("Sleeping for " + sleepTime + " seconds.");
                    Thread.sleep(sleepTime * 1000);

                    if (attemptsTime == NUMBER_OF_RETRIES - 1) {
                        JenkinsLogger.log("Load Template Time Out.");
                    }
                }

                attemptsTime++;
            }
        } catch (SkytapException ex) {
            JenkinsLogger.error("Request returned an error: " + ex.getError());
            JenkinsLogger.error("Failing build step.");
            return null;
        } catch (InterruptedException e1) {
            JenkinsLogger.error("Request: " + e1.getMessage());
            return null;
        }
        
        JsonElement je = parser.parse(httpRespBody);
        JsonObject jo = je.getAsJsonObject();
        
       // jo.get("")
        
        SkytapTemplate skyTpl= new SkytapTemplate();
        //String strinfo[] = new String[4];
        skyTpl.tpl_ID=jo.get("id").toString();
        skyTpl.tpl_Desc=jo.get("description").toString();
        skyTpl.tpl_busy=jo.get("busy").toString();
        skyTpl.tpl_name=jo.get("name").toString();
        
        return skyTpl;
    }
    
    public static SkytapVM CreateVMfromTpl(String templateID)//This function Not been implemented yet
    {
//       JenkinsLogger.log("Create environment by template with id: " + templateID);
       
       SkytapVM skyvm=null;
       
       String requestURL = buildCheckTemplateURL(templateID);
       String authCredentials = BasicAPICall.getAuthentication();
       
       
       return skyvm;

    }
}
