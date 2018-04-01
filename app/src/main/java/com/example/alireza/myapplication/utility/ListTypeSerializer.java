package com.example.alireza.myapplication.utility;

import android.util.Log;

import com.activeandroid.serializer.TypeSerializer;
import com.example.alireza.myapplication.config.MyApplication;
import com.example.alireza.myapplication.model.Product;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by alireza on 3/31/2018.
 */
public class ListTypeSerializer extends TypeSerializer {

    private static final String TAG = ListTypeSerializer.class.getSimpleName();


    @Override
    public Class<?> getDeserializedType() {
        return List.class;
    }


    @Override
    public Class<?> getSerializedType() {
        return String.class;
    }


    @Override
    public String serialize(Object data) {

        Log.i(TAG, "in serialize");

        StringBuilder sBuilder = new StringBuilder();

        // If we succesfully convert it to a String, print out the resulting string,
        // otherwise print an error
        if(getListAsJsonString(data, sBuilder)){
            Log.i(TAG, "serialize: " + sBuilder.toString());
        }
        else{
            Log.e(TAG, "Error ");
        }

        return sBuilder.toString();
    }


    /**
     * Helper function to build the Serialized Json String of our wheel list. It is written
     * in such a way that we could potentially add more List<T> types other than List<Wheel>
     * @param o data Object
     * @param sBuilder Reference to stringbuilder
     * @return False if the object is not a supported List<T> type
     */
    private boolean getListAsJsonString(Object o, StringBuilder sBuilder){

        if(o instanceof List){
            Log.i(TAG, "getListType is a List");

            List<?> list = (List<?>) o;

            if(!list.isEmpty()){
                Object type = list.get(0);

                if(type instanceof Product){
                    List<Product> finalList = (List<Product>) list;
                    sBuilder.append("{\"")
                            .append("wheelList")
                            .append("\":")
                            .append(finalList.toString())
                            .append("}");
                    return true;
                }
            }
        }

        return false;
    }


    @Override
    public List<?> deserialize(Object data) {
        Log.i(TAG, "in deserialize");
        String s = (String) data;

            List<Product>  products = MyApplication.getApp().getGson().fromJson(s, new TypeToken<List<Product>>(){}.getType());
            return products;

    }

}