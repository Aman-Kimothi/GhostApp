package com.google.engedu.ghost;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    public String BinarySearch(String st , int min , int max)  {

        int mid= (min+max) / 2;
        String str;

        if(min>=max)
            return null;

        if(words.get(mid).startsWith(st))
            return words.get(mid);
        else {

            if(words.get(mid).compareTo(st) < 0)
                str=BinarySearch(st,mid+1,max);
            else
                str=BinarySearch(st,min,mid-1);

        }
        return str;
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        String temp;
        if(prefix == ""){

            Random random = new Random();
            while((temp=words.get(random.nextInt(words.size()))).length() == 3);
            Log.w("random value : " , ""+temp);
            return temp;


        }
        else  {

            Log.w("Prefix+Word : ",""+BinarySearch(prefix,0,prefix.length()-1));
            return BinarySearch(prefix,0,words.size()-1);

        }

    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        String selected = null;



        return selected;
    }

    @Override
    public String ComputerWins(String prefix) {
        return null;
    }
}
