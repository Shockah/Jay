package test;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import pl.shockah.json.JSONObject;
import pl.shockah.json.JSONParser;
import pl.shockah.json.JSONPrettyPrinter;

public class JsonTest {
	public static void main(String[] args) {
		try {
			String json = new String(Files.readAllBytes(new File("json3.json").toPath()), Charset.forName("UTF-8"));
			JSONObject j = new JSONParser().parseObject(json);
			json = new JSONPrettyPrinter().toString(j);
			System.out.println(json);
			
			json = new String(Files.readAllBytes(new File("json1.json").toPath()), Charset.forName("UTF-8"));
			j = new JSONParser().parseObject(json);
			json = new JSONPrettyPrinter().toString(j);
			System.out.println(json);
			
			json = new String(Files.readAllBytes(new File("json2.json").toPath()), Charset.forName("UTF-8"));
			j = new JSONParser().parseObject(json);
			json = new JSONPrettyPrinter().toString(j);
			System.out.println(json);
			
			json = new String(Files.readAllBytes(new File("json4.json").toPath()), Charset.forName("UTF-8"));
			j = new JSONParser().parseObject(json);
			json = new JSONPrettyPrinter().toString(j);
			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}