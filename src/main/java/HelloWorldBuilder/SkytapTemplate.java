/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HelloWorldBuilder;

import java.util.logging.Level;
import java.util.logging.Logger;
import jenkins.model.Jenkins;

import java.lang.String;
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
                SkytapTemplate skytpl= SkytapRestAPICall.GetTemplate(ID);
                this.tpl_ID=skytpl.tpl_ID;
                this.tpl_Desc= skytpl.tpl_Desc;
                this.tpl_name= skytpl.tpl_name;
                this.tpl_busy= skytpl.tpl_busy;
            }
    public SkytapTemplate()
    {}
     public int SkyTapTemplateStatusRef()
            {
              try {
                   Thread.sleep(5000);
                   } 
              catch (InterruptedException ex) 
                   {
                     Logger.getLogger(SkytapTemplate.class.getName()).log(Level.SEVERE, null, ex);
                   }
                String status= (SkytapRestAPICall.GetTemplate(this.tpl_ID)).tpl_busy;
                
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
         
         SkytapVM NewVM=SkytapRestAPICall.CreateVMfromTpl(this.tpl_ID);
          
         String NewVMID= NewVM.VMID1;
         
         JenkinsLogger.log("VM Created ID="+NewVMID+" from template"+this.tpl_name+" Teample ID="+this.tpl_ID);
         
         return NewVMID;
     }
}
