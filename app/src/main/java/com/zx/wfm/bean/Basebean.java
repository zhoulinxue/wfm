package com.zx.wfm.bean;

import android.annotation.SuppressLint;
import android.util.Log;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.zx.wfm.service.ObjectToAVObject;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by ${zhouxue} on 16/12/19 01: 17.
 * QQ:515278502
 */
@AVClassName("Basebean")
public class Basebean extends AVObject implements Serializable,ObjectToAVObject{
    private int id;
    public static final Creator CREATOR = AVObjectCreator.instance;


    @Override
    public AVObject toAVObject() {
        try {
            //"com.geocompass.model.STSTBPRPModel"
            String path=getClass().getName();
            Class cls = Class.forName(path);  //com.geocompass.model.STSTBPRPModel
            Field[] fieldlist = cls.getDeclaredFields();
            put("ower", AVUser.getCurrentUser());
            for (int i = 0; i < fieldlist.length; i++) {
                Field fld = fieldlist[i];
                fld.setAccessible(true);
                try {
                    Log.e("实体名",fld.getName()+"路径："+fld.get(this));
                    if(!"$change".equals(fld.getName())) {
                        put(fld.getName(), fld.get(this));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
            return this;
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

     public static void reflect(Object obj) {  
                if (obj == null) return;  
                Field[] fields = obj.getClass().getDeclaredFields();  
                for (int j = 0; j < fields.length; j++) {  
                        fields[j].setAccessible(true);  
                        // 字段名  
                        System.out.print(fields[j].getName() + ",");  
                        // 字段值  
                        if (fields[j].getType().getName().equals(  
                                        java.lang.String.class.getName())) {  
                                // String type  
                                try {  
                                        System.out.print(fields[j].get(obj));  
                                    } catch (IllegalArgumentException e) {  
                                        // TODO Auto-generated catch block  
                                        e.printStackTrace();  
                                    } catch (IllegalAccessException e) {  
                                        // TODO Auto-generated catch block  
                                        e.printStackTrace();  
                                    }  
                            } else if (fields[j].getType().getName().equals(  
                                        java.lang.Integer.class.getName())  
                                || fields[j].getType().getName().equals("int")) {  
                                // Integer type  
                                try {  
                                        System.out.println(fields[j].getInt(obj));  
                                    } catch (IllegalArgumentException e) {  
                                        // TODO Auto-generated catch block  
                                        e.printStackTrace();  
                                    } catch (IllegalAccessException e) {  
                                        // TODO Auto-generated catch block  
                                        e.printStackTrace();  
                                    }  
                            }  
                        // 其他类型。。。  
                    }  
                System.out.println();  
            }  
}
