package br.sofex.com.androidinfo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    ListView lv1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv1 = findViewById(R.id.lv1);
        List<String> List1 = new ArrayList<>();

        List1.add("MODEL: " + Build.MODEL);
        List1.add("Device CodeName: " + android.os.Build.DEVICE); // TODO: Codenome do celular
        List1.add("Manufacture: " + Build.MANUFACTURER);
        List1.add("Imei Único: " + ImeiUnique());
        List1.add(ValidadorApp(Build.MODEL,Build.MANUFACTURER,android.os.Build.DEVICE,ImeiUnique()));

        ArrayAdapter<String> adaptador1 = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, List1);
        lv1.setAdapter(adaptador1);

    }

    public String ValidadorApp(String Manufacture,String Model,String CodeName,String imei){

        //TODO: Convert String to int ASCII
        String Manuf = "";
        for (char ch: Manufacture.toCharArray()) {
            //int char_number = Character.getNumericValue(ch);
            int char_number = ch;
            //Log.e("App1","int "+char_number+" Char "+ch);
            Manuf = Manuf + char_number;
        }

        String Mod = "";
        for (char ch: Model.toCharArray()) {
            //int char_number = Character.getNumericValue(ch);
            int char_number = ch;
            //Log.e("App1","int "+char_number+" Char "+ch);
            Mod = Mod + char_number;
        }

        String CN = "";
        for (char ch: CodeName.toCharArray()) {
            //int char_number = Character.getNumericValue(ch);
            int char_number = ch;
            //Log.e("App1","int "+char_number+" Char "+ch);
            CN = CN + char_number;
        }


        String IMEI = "";
        for (char ch: imei.toCharArray()) {
            //int char_number = Character.getNumericValue(ch);
            int char_number = ch;
            //Log.e("App1","int "+char_number+" Char "+ch);
            IMEI = IMEI + char_number;
        }


        String Code = Manuf+" - "+Mod+" - "+CN+" - "+IMEI;
        return Code;
    }

    public String ImeiUnique(){
        String imei = "";
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                if (telephonyManager != null) {
                    try {
                        imei = telephonyManager.getImei();
                    } catch (Exception e) {
                        e.printStackTrace();
                        imei = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
                    }
                }
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1010);
            }
        } else {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                if (telephonyManager != null) {
                    imei = telephonyManager.getDeviceId();
                }
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1010);
            }
        }
        return imei;
    }


}
