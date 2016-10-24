package code2040;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.util.ArrayList;

public class Code2040Challenge {
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
	    // Create an instance of HttpClient.
		step1();
		step2();
		step3();
		step4();
		step5();
	}
	
	public static void step1() {
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost("http://challenge.code2040.org/api/register");
            StringEntity params = new StringEntity("{\"token\":\"33ebd7305a7ec9ceadadb129b3c1d0e5\",\"github\":\"https://github.com/Nyhita/Code2040ApiChallenge\"}");
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse result = httpClient.execute(request);

            String json = EntityUtils.toString(result.getEntity(), "UTF-8");
            System.out.println("json response: " + json); 
         

        } catch (IOException ex) {
        }
	}
	
	public static void step2() {
		String json = "";
		// Retrieving the string
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost("http://challenge.code2040.org/api/reverse");
            StringEntity params = new StringEntity("{\"token\":\"33ebd7305a7ec9ceadadb129b3c1d0e5\"}");
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse result = httpClient.execute(request);

            json = EntityUtils.toString(result.getEntity(), "UTF-8");
        } catch (IOException ex) {
        }
		System.out.println("json response: " + json); 
		
		// String reversal
		int str_len = json.length();
		
		StringBuilder mutablejson = new StringBuilder(json);
		for(int i = 0; i < (str_len/2); i++) {
			char temp = mutablejson.charAt(i);
			mutablejson.setCharAt(i, mutablejson.charAt(str_len-i-1));
			mutablejson.setCharAt(str_len-i-1, temp);
		}
		
		json = mutablejson.toString();
		
		System.out.println("json response reversed: " + json); 
		
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost("http://challenge.code2040.org/api/reverse/validate");
            StringEntity params = new StringEntity("{\"token\":\"33ebd7305a7ec9ceadadb129b3c1d0e5\",\"string\":\"" + json + "\"}");
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse result = httpClient.execute(request);

            json = EntityUtils.toString(result.getEntity(), "UTF-8");
        } catch (IOException ex) {
        }
		System.out.println("json response: " + json);
	}
	
	public static void step3() {
		String json = "";
		// Retrieving the string
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost("http://challenge.code2040.org/api/haystack");
            StringEntity params = new StringEntity("{\"token\":\"33ebd7305a7ec9ceadadb129b3c1d0e5\"}");
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse result = httpClient.execute(request);

            json = EntityUtils.toString(result.getEntity(), "UTF-8");
        } catch (IOException ex) {
        }
		
		System.out.println("json response: " + json); 
		// Finding index
		int res = 0;
		try {
		    JSONParser parser = new JSONParser();
		    Object resultObject = parser.parse(json);
		    JSONObject obj =(JSONObject)resultObject;
		    String key = obj.get("needle").toString();
	    	String[] tokens = obj.get("haystack").toString().split("[\",\\[\\]]+");
	        int i = 1;
	        while(i < tokens.length && !(tokens[i].equals(key))) {
	        	i++;
	        }
	        res = i-1;
		} catch (Exception e) {
		    // TODO: handle exception
		}
		
		// Posting the answer
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {			
            HttpPost request = new HttpPost("http://challenge.code2040.org/api/haystack/validate");
            StringEntity params = new StringEntity("{\"token\":\"33ebd7305a7ec9ceadadb129b3c1d0e5\",\"needle\":" + res + "}");
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse result = httpClient.execute(request);

            json = EntityUtils.toString(result.getEntity(), "UTF-8");
        } catch (IOException ex) {
        }
		System.out.println("json response: " + json);
	}
	
	public static void step4() {
		String json = "";
		// Retrieving the input
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost("http://challenge.code2040.org/api/prefix");
            StringEntity params = new StringEntity("{\"token\":\"33ebd7305a7ec9ceadadb129b3c1d0e5\"}");
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse result = httpClient.execute(request);

            json = EntityUtils.toString(result.getEntity(), "UTF-8");
        } catch (IOException ex) {
        }
		
		System.out.println("json response: " + json); 
		
		// Constructing the array
		String answer = "";
		try {
		    JSONParser parser = new JSONParser();
		    Object resultObject = parser.parse(json);
		    JSONObject obj =(JSONObject)resultObject;
		    String key = obj.get("prefix").toString();
	    	String[] tokens = obj.get("array").toString().split("[\",\\[\\]]+");
	    	ArrayList<String> my_array = new ArrayList<String>();
	    	int key_len = key.length();
	    	System.out.println(key);
	    	System.out.println(key_len);
	    	for(String member : tokens) {
	    		System.out.println(member);
	    	}
	    	
	    	for(int i = 1; i < tokens.length; i++) {
	    		if(tokens[i].length() < key_len || !(key.equals(tokens[i].substring(0, key_len)))) {
	    			my_array.add(tokens[i]);
	    		}
	    	}
	    	StringBuilder answer_str = new StringBuilder("");
	    	
	    	int my_array_len = my_array.size();
	    	for(int i = 0; i < my_array_len; i++) {
	    		answer_str.append('\"');
	    		answer_str.append(my_array.get(i));
	    		answer_str.append('\"');
	    		if(i < my_array_len-1)
	    		  answer_str.append(',');
	    	}
	    	answer = answer_str.toString();
	    	
		} catch (Exception e) {
		    // TODO: handle exception
		}
		// Posting the answer
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost("http://challenge.code2040.org/api/prefix/validate");
            StringEntity params = new StringEntity("{\"token\":\"33ebd7305a7ec9ceadadb129b3c1d0e5\",\"array\":[" + answer + "]}");
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse result = httpClient.execute(request);

            json = EntityUtils.toString(result.getEntity(), "UTF-8");
        } catch (IOException ex) {
        }
		System.out.println("json response: " + json);
	}
	
	public static void step5() {
		String json = "";
		// Retrieving the input
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost("http://challenge.code2040.org/api/dating");
            StringEntity params = new StringEntity("{\"token\":\"33ebd7305a7ec9ceadadb129b3c1d0e5\"}");
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse result = httpClient.execute(request);

            json = EntityUtils.toString(result.getEntity(), "UTF-8");
            
        } catch (IOException ex) {
        }
		
		System.out.println("json response: " + json);
		String answer = "";
		
		// Calculate the new data
		try {
		    JSONParser parser = new JSONParser();
		    Object resultObject = parser.parse(json);
		    JSONObject obj =(JSONObject)resultObject;
		    //String key = obj.get("datestamp").toString();
		    int interval = Integer.parseInt(obj.get("interval").toString());
		    System.out.println(interval);
		    
		    // Separating all the time components
	    	//String[] main_split = obj.get("array").toString().split("[\",\\[\\]]+");
	    	String[] main_split = obj.get("datestamp").toString().split("[TZ]+");
	    	//System.out.println(main_split[0]);
	    	//System.out.println(main_split[1]);
	    	String[] date_split = main_split[0].split("[\\-]+");
	    	String[] time_split = main_split[1].split("[:]+");
	    	int year = Integer.parseInt(date_split[0]);
	    	int month = Integer.parseInt(date_split[1]);
	    	int day = Integer.parseInt(date_split[2]);
	    	int hours = Integer.parseInt(time_split[0]);
	    	int minutes = Integer.parseInt(time_split[1]);
	    	int seconds = Integer.parseInt(time_split[2]);
	    	System.out.println(year + "-" + month + "-" + day);
	    	System.out.println(hours + ":" + minutes + ":" + seconds);
	    	
	    	// All conversions
	    	int min_to_secs = 60;
	    	int hour_to_secs = min_to_secs*60;
	    	int day_to_secs = hour_to_secs*24;
	    	int year_to_secs = day_to_secs*365;
	    	int[] month_days = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	    	
	    	// Time changes
	    	int add_year = interval/year_to_secs;
	    	interval -= add_year*year_to_secs;
	    	int add_day = interval/day_to_secs;
	    	interval -= add_day*day_to_secs;
	    	int add_hours = interval/hour_to_secs;
	    	interval -= add_hours*hour_to_secs;
	    	int add_minutes = interval/min_to_secs;
	    	interval -= add_minutes*min_to_secs;
	    	int add_seconds = interval;
	    	System.out.println("add year: " + add_year + 
	    					   " add_day: " + add_day +
	    					   " add_hours: " + add_hours +
	    					   " add_minutes: " + add_minutes + 
	    					   " add_seconds: " + add_seconds);
	    	
	    	// Adding the time
	    	seconds += add_seconds;
	    	if(seconds >= 60)
	    		minutes++;
	    	seconds %= 60;
	    	
	    	minutes += add_minutes;
	    	if(minutes >= 60)
	    		hours++;
	    	minutes %= 60;
	    	
	    	hours += add_hours;
	    	if(hours >= 24)
	    		day++;
	    	hours %= 24;
	    	
	    	day += add_day;
	    	
	    	year += add_year;
	    	
	    	System.out.println("new year: " + year + 
					   " new_day: " + day +
					   " new_hours: " + hours +
					   " new_minutes: " + minutes + 
					   " new_seconds: " + seconds);	
	    	
	    	// Code doesn't handle change in months with days over the limit based on low input
	    	
	    	StringBuilder answer_str = new StringBuilder();
	    	answer_str.append(year);
	    	answer_str.append('-');
	    	if(month < 10) {
	    		answer_str.append('0');
	    	}
	    	answer_str.append(month);
	    	answer_str.append('-');
	    	if(day < 10) {
	    		answer_str.append('0');
	    	}
	    	answer_str.append(day);
	    	answer_str.append('T');
	    	if(hours < 10) {
	    		answer_str.append('0');
	    	}
	    	answer_str.append(hours);
	    	answer_str.append(':');
	    	if(minutes < 10) {
	    		answer_str.append('0');
	    	}
	    	answer_str.append(minutes);
	    	answer_str.append(':');
	    	if(seconds < 10) {
	    		answer_str.append('0');
	    	}
	    	answer_str.append(seconds);
	    	answer_str.append('Z');
	    	
	    	answer = answer_str.toString();
	    	System.out.println(answer);
	    	
		} catch (Exception e) {
		    // TODO: handle exception
		}
		// Posting the answer
		try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost("http://challenge.code2040.org/api/dating/validate");
            StringEntity params = new StringEntity("{\"token\":\"33ebd7305a7ec9ceadadb129b3c1d0e5\",\"datestamp\":\"" + answer + "\"}");
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse result = httpClient.execute(request);

            json = EntityUtils.toString(result.getEntity(), "UTF-8");
        } catch (IOException ex) {
        }
		System.out.println("json response: " + json);
		
	}
}