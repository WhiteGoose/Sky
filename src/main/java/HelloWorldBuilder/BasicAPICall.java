/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package HelloWorldBuilder;

import org.apache.http.HttpResponse;

import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.lang.String;


/**
 *
 * @author Administrator
 */
public  class BasicAPICall {
    
    public static String skytapString="https://cloud.skytap.com/";
    
    public static String SkytapUsers="users/";
    public static String SkytapVMs="vms/";
    
    public static String SkytapJson=".json";
    
    public static int NUMBER_OF_RETRIES=3;
    public static int RETRY_INTERVAL_SECONDS=1;
    
//    public static SkytapNetWork GetNetWork(String ID)//This function Not been implemented yet
//    {
//        
//        SkytapNetWork skyNet; 
//        return null;
//    }
    
      public static String getAuthentication()
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document document;

        try{
          DocumentBuilder db = dbf.newDocumentBuilder();
          document =db.parse("./settings.xml");
          
          Element rootXml= document.getDocumentElement();
          NodeList noList= rootXml.getChildNodes();
          Node userName= noList.item(1);//xml username
          Node passWord = noList.item(3);//xml password 
                 
          if(userName==null||passWord==null)
          {
              throw new Exception("username or password missing, please check the settings.xml");
          }
          
          String authKey= userName.getTextContent()+":"+ passWord.getTextContent();
          
          JenkinsLogger.log(authKey);
          
          String encodedAuth = encodeAuth(authKey);
          return encodedAuth;//+password.item(0).toString();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /*
    Skytap auth Credentical encoded to use in API request
    */
    public static String encodeAuth(String unencodedAuth)
    {
        byte[] encoded =Base64.encodeBase64(unencodedAuth.getBytes());
        String encodedCredential=new String(encoded);
        return encodedCredential;
    }
/**
	 * This method packages an http get request object, given a url and the
	 * encoded Skytap authorization token.
	 * 
	 * @param requestUrl
	 * @param AuthToken
	 * @return
	 */
	public static HttpGet buildHttpGetRequest(String requestUrl,
			String AuthToken) {

		HttpGet hg = new HttpGet(requestUrl);
		String authHeaderValue = "Basic " + AuthToken;

		hg.addHeader("Authorization", authHeaderValue);
		hg.addHeader("Accept", "application/json");//Accept Json Format Result
		hg.addHeader("Content-Type", "application/json");
                
                String Headers=null;
                
                for(int i=0;i<hg.getAllHeaders().length;i++)
                {
                    Headers+=hg.getAllHeaders()[i];
                }

		JenkinsLogger.log("HTTP GET Request: "+ Headers + hg.toString());
		return hg;
	}

	/**
	 * This method packages an http post request object, given a url and the
	 * encoded Skytap authorization token.
	 * 
	 * @param requestUrl
	 * @param AuthToken
	 * @return
	 */
	public static HttpPost buildHttpPostRequest(String requestUrl,
			String AuthToken) {

		HttpPost hp = new HttpPost(requestUrl);
		String authHeaderValue = "Basic " + AuthToken;

		hp.addHeader("Authorization", authHeaderValue);
		hp.addHeader("Accept", "application/json");
		hp.addHeader("Content-Type", "application/json");

		JenkinsLogger.log("HTTP POST Request: " + hp.toString());

		return hp;
	}


}
