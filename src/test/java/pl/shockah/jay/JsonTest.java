package pl.shockah.jay;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class JsonTest {
	public static void main(String[] args) {
		try {
			String json = new String(Files.readAllBytes(new File("json3.jay").toPath()), Charset.forName("UTF-8"));
			JSONObject j = new JSONParser().parseObject(json);
			json = new JSONPrettyPrinter().toString(j);
			System.out.println(json);
			
			json = new String(Files.readAllBytes(new File("json1.jay").toPath()), Charset.forName("UTF-8"));
			j = new JSONParser().parseObject(json);
			json = new JSONPrettyPrinter().toString(j);
			System.out.println(json);
			
			json = new String(Files.readAllBytes(new File("json2.jay").toPath()), Charset.forName("UTF-8"));
			j = new JSONParser().parseObject(json);
			json = new JSONPrettyPrinter().toString(j);
			System.out.println(json);
			
			json = new String(Files.readAllBytes(new File("json4.jay").toPath()), Charset.forName("UTF-8"));
			j = new JSONParser().parseObject(json);
			json = new JSONPrettyPrinter().toString(j);
			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}