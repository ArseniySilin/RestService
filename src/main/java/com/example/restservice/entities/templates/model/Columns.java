package com.example.restservice.entities.templates.model;

import java.util.HashMap;
import java.util.Map;

public class Columns {
 public static Map<String, String> indicies = new HashMap<String, String>() {{
   put("1", "createddatetimeutc");
   put("2", "updateddatetimeutc");
   put("3", "createdusername");
   // put("4", "links"); // not implemented yet
   put("5", "name");
 }};
}
