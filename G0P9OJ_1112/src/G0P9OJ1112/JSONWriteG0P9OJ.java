package G0P9OJ1112;

import java.io.FileReader;
import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONWriteG0P9OJ {

	public static void main(String[] args) {
			
		try {
			
			JSONArray lessons = new JSONArray();
			lessons.add(createLesson("Oracle", "hétfõ", "8", "10", "Inf. 103", "Kovács László", "Mérnök informatika"));
			lessons.add(createLesson("Oracle", "kedd", "14", "15", "Inf. 202", "Szûcs Miklós", "Mérnök informatika"));
			lessons.add(createLesson("Webtech1", "hétfõ", "10", "12", "Elõadó 1.", "Agárdi Anita", "Mérnök informatika"));
			lessons.add(createLesson("Webtech1", "hétfõ", "14", "16", "Inf. 101", "Agárdi Anita", "Mérnök informatika"));
			lessons.add(createLesson("Korszerû tech", "hétfõ", "12", "14", "Inf. 103", "Árvai László", "Mérnök informatika"));
			lessons.add(createLesson("Korszerû tech", "szerda", "16", "18", "Inf. 103", "Árvai László", "Mérnök informatika"));
			lessons.add(createLesson("Webalkc#", "kedd", "14", "16", "Inf. 101", "Árvai László", "Mérnök informatika"));
			lessons.add(createLesson("Webalkc#", "kedd", "16", "18", "Inf. 101", "Árvai László", "Mérnök informatika"));
			
			JSONObject root = new JSONObject();
			root.put("ora", lessons);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("G0P9OJ_orarend", root);
			
			fileWrite(jsonObject, "orarendG0P9OJ1.json");
			consoleWrite(jsonObject);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void fileWrite(JSONObject jsonObject, String fileName) {
		try(FileWriter writer = new FileWriter(fileName)){
			writer.write(indentJson(jsonObject.toJSONString()));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void consoleWrite(JSONObject jsonObject) {
		System.out.println("A felépített JSON dokumentum tartalma:\n");
		JSONObject root = (JSONObject) jsonObject.get("G0P9OJ_orarend");
		JSONArray lessons = (JSONArray) root.get("ora");
		for(int i=0; i<lessons.size(); i++) {
			JSONObject lesson = (JSONObject) lessons.get(i);
			JSONObject time = (JSONObject) lesson.get("idopont");
			System.out.println("Tárgy: "+lesson.get("targy"));
			System.out.println("Idõpont: "+time.get("nap")+" "+time.get("tol")+"-"+time.get("ig"));
			System.out.println("Helyszín: "+lesson.get("helyszin"));
			System.out.println("Oktató: "+lesson.get("oktato"));
			System.out.println("Szak: "+lesson.get("szak")+"\n");
		}
	}
	
	private static String indentJson(String json) {
		String out = "";
		int indent = 0;
		for (int i = 0; i < json.length()-1; i++) {
			out += json.charAt(i);
			if (json.charAt(i) == ',') {
				out += "\n" + "  ".repeat(indent>0 ? indent : 0);
			} else if (json.charAt(i) == '{' | json.charAt(i) == '[') {
				indent++;
				out += "\n" + "  ".repeat(indent>0 ? indent : 0);
			}else if ((json.charAt(i+1) == '}' || json.charAt(i+1) == ']')) {
				indent--;
				out += "\n" + "  ".repeat(indent>0 ? indent : 0);
			}
		}
		out+=json.charAt(json.length()-1);
		return out;
	}
	
	private static JSONObject createLesson(String subject, String day, String from, String to, String place, String teacher, String major) {
		JSONObject lesson = new JSONObject();
		JSONObject time = new JSONObject();
		time.put("nap", day);
		time.put("tol", from);
		time.put("ig", to);
		lesson.put("targy", subject);
		lesson.put("idopont", time);
		lesson.put("helyszin", place);
		lesson.put("oktato", teacher);
		lesson.put("szak", major);
		return lesson;
	}
	
	
	

}