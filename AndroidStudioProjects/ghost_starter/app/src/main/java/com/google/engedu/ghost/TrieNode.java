package com.google.engedu.ghost;

import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;


public class TrieNode {
    private HashMap<String, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {

        String str = "";
        TrieNode currNode = this;

        for (int i = 0; i < s.length(); i++) {

            str += s.charAt(i);
            TrieNode temp;
            if (!currNode.children.containsKey(str)) {
                temp = new TrieNode();
//                Log.d("doesn't contain : " , ""+str);
            } else {
                temp = currNode.children.get(str);
//                Log.d("contain : " , ""+str);
            }

            if(i==s.length()-1)
                temp.isWord=true;
            currNode.children.put(str, temp);
            currNode=temp;

        }
    }

    public boolean isWord(String s) {
        TrieNode currNode = this;
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            str += s.charAt(i);
            if (!currNode.children.containsKey(str)) {
                Log.d("doesnt contain : " , ""+str);
                return false;
            }
            Log.d("contain : " , ""+str);
            currNode = currNode.children.get(str);
        }

        if(currNode.isWord)
            return true;
        else
            return false;
    }

    public String getAnyWordStartingWith(String s) {
        String str = "";
        TrieNode currNode = this;

        if(s == ""){
            Random random =  new Random();
            String keyR = "";
            while (!currNode.isWord){
                String[] keys = currNode.children.keySet().toArray(new String[currNode.children.keySet().size()]);
                keyR = keys[random.nextInt(keys.length - 1)];
                currNode = currNode.children.get(keyR);
            }

            return keyR;
        }


        for (int i = 0; i < s.length(); i++) {
            str += s.charAt(i);
            if (!currNode.children.containsKey(str)) {
                Log.d("doesnt contain : " , ""+str);
                return null;
            }
            currNode = currNode.children.get(str);
        }
        if(currNode!=null) {
            String keyRet="";
            for(String key:currNode.children.keySet()){
                keyRet = key;
            }
            return keyRet;
        }
        return null;
    }



    public String ComputerWin(String st) {

        Log.d("Word found in CW" , " "+st);
        TrieNode currNode = this;
//        currNode = currNode.children.get(st);
//        Log.d("Word found in CW" , " "+currNode.children.keySet().size());
//        String res1=currNode.children.keySet().toString();
        String res="";

        if(currNode != null)   {

            Log.d("KeySey size in CW" , " "+currNode.children.keySet().size());
            currNode = currNode.children.get(st);
//            Log.d("KeySey size in CW" , " "+currNode.children.keySet().size());
//            res=currNode.children.keySet().toString();

            while (true) {

                if (currNode.isWord) {
                    Log.d("Word found", "" + currNode.isWord);
                    return res;
                }

                for (char i = 'a'; i < 'z'; i++) {
                    if (isWord(res + i)) {
                        Log.d("Different words ", "" + (res+i));
                        res += i;
                    }
                }
            }


        }


//
////        Log.d("Word found in CW" , " ");
//        if(currNode != null)  {
//
//            res=currNode.children.keySet().toString();
//
//            if(currNode.isWord) {
//                Log.d("Word found" , ""+currNode.isWord);
//                return res;
//            }
//
//            for (char i='a' ; i<'z' ; i++) {
//                if (isWord(res+i))
//                    ComputerWin(res+i);
//            }
//        }
        return res;
    }

    public String getGoodWordStartingWith(String s) {
        return null;
    }
}
