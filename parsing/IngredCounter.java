package com.example.kelvs.parsing;

import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class IngredCounter {

    List<Ingredient> countedIngredients = new ArrayList<Ingredient>();

    List<String> sampleUserIngreds = Arrays.asList("pork","onion","oil");

    public void countIngredients(List<Match> matches) {

        Ingredient ci_temp = new Ingredient();

        for (String userIngred : this.sampleUserIngreds) {

            for (Match m : matches) {

                for (Iterator<String> iterator = m.getIngredients().iterator(); iterator.hasNext(); ) {

                    String ingred = iterator.next();
                   // Log.d("CHECK: ", "INGRED: " + ingred);

                    // if match is found
                    if (ingred.contains(userIngred)) {

                        //Log.d("CHECK: ", "Ingred contains userIngred!");

                        // check if ingred in countedIngredients List
                        Boolean isInList = false;

                        for (Ingredient ci : countedIngredients) {
                            if (ingred.contains(ci.getName())) {
                                ci_temp = ci;
                                isInList = true;
                                break;
                            }
                        }

                        // if ingred not in countedIngredient list
                        if (isInList == false) {

                         //   Log.d("CHECK: ", "Ingred not in list");

                            // add the ingredient
                            Ingredient newCountedIngred = new Ingredient();
                            newCountedIngred.setName(userIngred);
                            newCountedIngred.addCount();
                            newCountedIngred.addOccurence(ingred);

                            this.countedIngredients.add(newCountedIngred);
                        }

                        // else: ingred in countedIngredients list
                        else {

                           // Log.d("CHECK: ", "Ingred in list");

                            isInList = false;

                            for (String occ : ci_temp.getOccurences()) {
                                if (occ.equalsIgnoreCase(ingred))
                                    isInList = true;
                            }

                            //occurence not in list
                            if (isInList == false) {
                           //     Log.d("CHECK: ", "Occ not in list");
                                ci_temp.addOccurence(ingred);
                            }

                            ci_temp.addCount();
                        }
                    }

                    else {

                        // else: ingred not in list
                       // Log.d("CHECK: ", "Not Found");
//                      iterator.remove();
                        continue;
                    }
                }
            }
        }
    }

    public void showCountedIngredients() {

        for (Ingredient ci : this.countedIngredients)
            System.out.println("NAME: " + ci.getName() +
                    "\nCOUNT: " + ci.getCount() +
                    "\nOCCS: " + ci.getOccurences() + "\n ");
    }

    public void getAllMatches(String keyword) {

        int matchCountTotal = 0;

        List<String> sources = new ArrayList<>();
        List<Match> matches = new ArrayList<>();

        ParseYummly py = new ParseYummly();
        py.parseJSON(keyword, null, matchCountTotal);
        py.showMatches();

        matchCountTotal = py.getMatchCount();
        System.out.println("matchCountTotal 1 : " + matchCountTotal);

        /*
            1. get only valid names and sources
            2. get at least 5 sources, eliminate redundant sources
            3. get ingredients of selected sources
            4. make a list of compiled matches
            5. call countIngredients
         */

        for(Match m : py.getMatches())
            matches.add(m);

        if (matchCountTotal < 5) {

            //formatting sources from Yummly
            for (Match m : py.getMatches()) {
                sources.add(m.getSourceURL().replace(",", "").toLowerCase());
            }

            ParseRecPuppy pr = new ParseRecPuppy();
            pr.parseJSON(keyword, sources, matchCountTotal);
            pr.showMatches();

            matchCountTotal += pr.getMatchCount();
            System.out.println("matchCountTotal 2 : " + matchCountTotal);

            for(Match m : pr.getMatches())
                matches.add(m);

            if (matchCountTotal < 5) {

                for (Match m : pr.getMatches())
                    sources.add(m.getSourceURL());

                ParseEdamam pe = new ParseEdamam();
                pe.parseJSON(keyword, sources, matchCountTotal);
                pe.showMatches();

                matchCountTotal += pe.getMatchCount();
                System.out.println("matchCountTotal 3 : " + matchCountTotal);

                for(Match m : pe.getMatches())
                    matches.add(m);
            }
        }

        countIngredients(matches);
    }
}