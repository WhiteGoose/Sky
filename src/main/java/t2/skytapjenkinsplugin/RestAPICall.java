/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package t2.skytapjenkinsplugin;

import org.apache.http.HttpResponse;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

/**
 *
 * @author Administrator
 */
public  class RestAPICall {
    
    public static String skytapString="https://cloud.skytap.com/";
    
    public static String SkytapUsers="users/";
    public static String SkytapVMs="vms/";
    
    public static String SkytapJson=".json";
    
//    public static SkytapNetWork GetNetWork(String ID)//This function Not been implemented yet
//    {
//        
//        SkytapNetWork skyNet; 
//        return null;
//    }
    
    public static SkytapTemplate GetTemplate(String ID)//This function Not been implemented yet
    {
        SkytapTemplate skyTpl; 
        return null;
    }
    
    public static SkytapVM CreateVMfromTpl(String tplID)//This function Not been implemented yet
    {
        SkytapTemplate skyVM; 
        return null;
    }
}
