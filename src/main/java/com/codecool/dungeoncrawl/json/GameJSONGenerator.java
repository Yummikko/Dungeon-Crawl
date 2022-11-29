package com.codecool.dungeoncrawl.json;


import javax.json.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class GameJSONGenerator {
    public static void main(String args[])
            throws FileNotFoundException
    {

        // JsonReader: an interface that reads a JSON object
        // or an array structure from an input source.

        JsonReader reader = Json.createReader(
                new FileReader("sample.txt"));

        // JsonStructure: an interface that is a super type
        // for the two structured types in JSON
        // (objects and arrays)
        JsonStructure jsonst = reader.read();

        // navigateTree method takes two arguments: a JSON
        // element and a key. The key is used only to help
        // print the key-value pairs inside objects.
        // Elements in a tree are represented by the
        // JsonValue type.

        navigateTree(jsonst, null);
    }

    // A JsonValue is one of the following: an object
    // (JsonObject), an array (JsonArray), a number
    // (JsonNumber), a string (JsonString), true
    // (JsonValue.TRUE), false (JsonValue.FALSE), or null
    // (JsonValue.NULL).

    // Method 2
    // To navigate through the model
    // and print the key-value pairs
    public static void navigateTree(JsonValue tree,
                                    String key)
    {
        if (key != null)
            System.out.print("Key " + key + ": ");

        // Switch case

        // Method 3
        // getValueType() returns the value type of
        // this JSON value.
        switch (tree.getValueType()) {

            // Case 1
            case OBJECT:

                System.out.println("OBJECT");
                JsonObject object = (JsonObject)tree;

                for (String name : object.keySet())
                    navigateTree(object.get(name), name);

                break;

            // Case 2
            case ARRAY:

                System.out.println("ARRAY");
                JsonArray array = (JsonArray)tree;

                for (JsonValue val : array)
                    navigateTree(val, null);

                break;

            // Case 3
            case STRING:

                JsonString st = (JsonString)tree;

                System.out.println("STRING " + st.getString());
                break;

            // Case 4
            case NUMBER:

                JsonNumber num = (JsonNumber)tree;
                System.out.println("NUMBER " + num.toString());

                break;

            // Case 5
            case TRUE:

                // Case 6
            case FALSE:

                // Case 7
            case NULL:

                // Print statement
                System.out.println(
                        tree.getValueType().toString());
                break;
        }
    }
}
