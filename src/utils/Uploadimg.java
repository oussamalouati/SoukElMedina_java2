/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import controllers.VendeurController;

/**
 *
 * @author INETEL
 */
public class Uploadimg {
    public Uploadimg(File img){
     //upload de l'image vers le serveur
                HttpClient httpclient = new DefaultHttpClient();
                httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
                HttpPost httppost = new HttpPost("http://localhost:80/skmedina/imgmag/upload.php");
                MultipartEntity mpEntity = new MultipartEntity();
                ContentBody cbFile = new FileBody(img, "image/jpeg");
                mpEntity.addPart("userfile", cbFile);
                httppost.setEntity(mpEntity);
                System.out.println("executing request " + httppost.getRequestLine());
                HttpResponse response = null;
                try {
                    response = httpclient.execute(httppost);
                } catch (IOException ex) {
                    Logger.getLogger(VendeurController.class.getName()).log(Level.SEVERE, null, ex);
                }
                HttpEntity resEntity = response.getEntity();
                System.out.println(response.getStatusLine());//-------------------//
                if (resEntity != null) {
                    try {
                        System.out.println(EntityUtils.toString(resEntity));
                    } catch (IOException ex) {
                        Logger.getLogger(VendeurController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (resEntity != null) {
                    try {
                        resEntity.consumeContent();
                    } catch (IOException ex) {
                        Logger.getLogger(VendeurController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                httpclient.getConnectionManager().shutdown();
    }
}
