/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package HelloWorldBuilder;

/**
 *
 * @author Administrator
 */
public class SkytapConfiguration {
   public String buildCreateConfigRequestURL(String templateId) {

		JenkinsLogger.log("Building request url ...");

		StringBuilder sb = new StringBuilder("https://cloud.skytap.com/");
		sb.append("configurations/");
		sb.append("?template_id=");
		sb.append(templateId);

		JenkinsLogger.log("Request URL: " + sb.toString());
		return sb.toString();

	} 
   
   public String createCfgfromTpl(String templateId)
   {
       return null;
   }
}
