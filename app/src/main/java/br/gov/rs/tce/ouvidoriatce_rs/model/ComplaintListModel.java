package br.gov.rs.tce.ouvidoriatce_rs.model;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.gov.rs.tce.ouvidoriatce_rs.R;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: adjust this class before publishing your app.
 */
public class ComplaintListModel {

    /**
     * An array of sample (model) items.
     */
    // public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    public static final List<ComplaintItem> ITEMS = new ArrayList<>();

    /**
     * A map of model items, by ID. TODO: WHY???
     */
    public static final Map<String, ComplaintItem> ITEM_MAP = new HashMap<>();

    /**
     * Constructor builds a list by retrieving arrays from resources
     */
    public ComplaintListModel(Resources resources)
    {
        // prevents duplication of items after navigating up
        if (ITEMS.isEmpty()) {
            // recupera todos os resources
            Resources currentResources = resources;
            // recupera array com referências aos subjects
            TypedArray categories = currentResources.obtainTypedArray(R.array.categories);
            Log.i("TYPED ARRAY", categories.toString());
            // conta quantos elementos o array de subjects possui
            int n = categories.length();
            Log.i("CATEGORIES COUNT", String.valueOf(n));
            // recupera array de nomes de categorias
            String[] names = currentResources.getStringArray(R.array.categories_names);
            // cria um array para receber arrays de subjects
            String[][] subjects = new String[n][];
            // cria variável para receber o id de cada array de subjects
            int id;
            for (int i = 0; i < n; ++i) {
                // recupera o id do array "i"
                id = categories.getResourceId(i, 0);
                if (id > 0) {
                    // recupera o array de subjects
                    subjects[i] = currentResources.getStringArray(id);
                    // cria um item Complaint completo
                    ComplaintItem complaint = new ComplaintItem(String.valueOf(id), names[i], subjects[i]);
                    addItem(complaint);
                } else {
                    // TODO: add code when something wrong with the XML
                    Log.i("SEM ARRAY", "Não deu certo!");
                }
            }
            Log.i("ARRAY", ITEMS.toString());
            // recycle the fetched resources?
            categories.recycle();
        }

    }

    /**
     * A model item representing a piece of content.
     */
    public static class ComplaintItem {
        public final String id;
        public final String category;
        public static List<String> subjects = new ArrayList<String>();

        public ComplaintItem(String id, String category, String[] subjects)
        {
            this.id = id;
            this.category = category;
            this.subjects = Arrays.asList(subjects);
        }

        @Override
        public String toString() {
            return category;
        }
    }

    private static void addItem(ComplaintItem complaint) {
        ITEMS.add(complaint);
        ITEM_MAP.put(complaint.id, complaint);
    }

    /**
     * TODO: SEE WHAT TO DO WITH THESE
     * @param id
     * @param category
     * @param subjects
     * @return
     */
    /*private static ComplaintItem createComplaintItem(int id, String category, List<String> subjects) {
        //return new ComplaintItem(String.valueOf(position), CATEGORIES.get(position), makeDetails(position));
        ComplaintItem complaint = new ComplaintItem(int id, String category, subjects);
    }*/

    /*private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }*/


}
