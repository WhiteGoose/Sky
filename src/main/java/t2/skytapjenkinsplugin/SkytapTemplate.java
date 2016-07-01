/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package t2.skytapjenkinsplugin;

import java.util.logging.Level;
import java.util.logging.Logger;
import jenkins.model.Jenkins;

/**
 *
 * @author Administrator
 */
public class SkytapTemplate {

    public String tpl_ID;
    public String tpl_Desc;
    public String tpl_name;
    public String tpl_busy;

    public SkytapTemplate(String ID)
            {
                SkytapTemplate skyTpl= RestAPICall.GetTemplate(ID);
                this.tpl_ID=ID;
                this.tpl_Desc= skyTpl.tpl_Desc;
                this.tpl_name= skyTpl.tpl_name;
                this.tpl_busy= skyTpl.tpl_busy;
            }
    
     public int SkyTapTemplateStatusRef()
            {
              try {
                   Thread.sleep(5000);
                   } 
              catch (InterruptedException ex) 
                   {
                     Logger.getLogger(SkytapTemplate.class.getName()).log(Level.SEVERE, null, ex);
                   }
                String status= (RestAPICall.GetTemplate(this.tpl_ID)).tpl_busy;
                
                if(status!="null")
                {
                    return 1;//Template is busy 
                }
                else 
                    return 0;//Template is good
            }
     
     public String SkytapTemplateCreateVM()//return the Created VM ID;
     {
         for(int i=0;i<12;i++)
         {
             if(this.tpl_busy=="null")
             {
                 break;
             }
             else 
             {
                 SkyTapTemplateStatusRef();
             }
         }
         if(this.tpl_busy!="null")
         {
             JenkinsLogger.error("Fail to create VM due to the template is still busy");
             return null;
         }
         
         SkytapVM NewVM=RestAPICall.CreateVMfromTpl(this.tpl_ID);
          
         String NewVMID= NewVM.VMID1;
         
         JenkinsLogger.log("VM Created ID="+NewVMID+" from template"+this.tpl_name+" Teample ID="+this.tpl_ID);
         
         return NewVMID;
     }
}
