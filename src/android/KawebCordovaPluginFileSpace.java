package co.uk.kaweb.cordovaplugin.filespace;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Base64;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.LOG;
import org.apache.cordova.PermissionHelper;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.security.Permission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class KawebCordovaPluginFileSpace extends CordovaPlugin {
    
    private interface FileOp {
        void run(JSONArray args) throws Exception;
    }

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getFreeDiskSpace")) {
            threadhelper( new FileOp( ){
                public void run(JSONArray args) {
                    // The getFreeDiskSpace plugin API is not documented, but some apps call it anyway via exec().
                    // For compatibility it always returns free space in the primary external storage, and
                    // does NOT fallback to internal store if external storage is unavailable.
                    long l = DirectoryManager.getFreeExternalStorageSpace();
                    callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, l));
                }
            }, rawArgs, callbackContext);
        }
        else {
            return false;
        }
    }
    
    
    
    /* 
     * helper to execute functions async and handle the result codes
     */
    private void threadhelper(final FileOp f, final String rawArgs, final CallbackContext callbackContext){
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    JSONArray args = new JSONArray(rawArgs);
                    f.run(args);
                } catch ( Exception e) {
                    if( e instanceof EncodingException){
                        callbackContext.error(FileUtils.ENCODING_ERR);
                    } else if(e instanceof FileNotFoundException) {
                        callbackContext.error(FileUtils.NOT_FOUND_ERR);
                    } else if(e instanceof FileExistsException) {
                        callbackContext.error(FileUtils.PATH_EXISTS_ERR);
                    } else if(e instanceof NoModificationAllowedException ) {
                        callbackContext.error(FileUtils.NO_MODIFICATION_ALLOWED_ERR);
                    } else if(e instanceof InvalidModificationException ) {
                        callbackContext.error(FileUtils.INVALID_MODIFICATION_ERR);
                    } else if(e instanceof MalformedURLException ) {
                        callbackContext.error(FileUtils.ENCODING_ERR);
                    } else if(e instanceof IOException ) {
                        callbackContext.error(FileUtils.INVALID_MODIFICATION_ERR);
                    } else if(e instanceof EncodingException ) {
                        callbackContext.error(FileUtils.ENCODING_ERR);
                    } else if(e instanceof TypeMismatchException ) {
                        callbackContext.error(FileUtils.TYPE_MISMATCH_ERR);
                    } else if(e instanceof JSONException ) {
                        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.JSON_EXCEPTION));
                    } else if (e instanceof SecurityException) {
                        callbackContext.error(FileUtils.SECURITY_ERR);
                    } else {
                        e.printStackTrace();
                    	callbackContext.error(FileUtils.UNKNOWN_ERR);
                    }
                }
            }
        });
    }
}
