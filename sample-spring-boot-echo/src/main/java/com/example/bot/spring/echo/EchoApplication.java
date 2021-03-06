/*
 * Copyright 2016 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.example.bot.spring.echo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

@SpringBootApplication
@LineMessageHandler
public class EchoApplication {
	
	static HashMap<String, String> hmap = new HashMap<String, String>();
	static boolean power = false;
	static String code = "";
	static String urlString = "";
	static String result = "";
	static URL url;
	static HttpURLConnection connection;
	static BufferedReader bufferedReader;
	static StringBuilder response;
	static String line;
	static String time = "";
	static String money = "";
	static String up = "";
	
    public static void main(String[] args) {
        SpringApplication.run(EchoApplication.class, args);
    }

    @EventMapping
    public static TextMessage handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        System.out.println("event: " + event);

		if(hmap.containsKey(event.getMessage().getText())) {
			return new TextMessage(hmap.get(event.getMessage().getText()));
		} else if(event.getMessage().getText().equals("早安")) {
			return new TextMessage("早安吃飽了嘛?");
		} else if(event.getMessage().getText().contains("愛我") && event.getMessage().getText().contains("?")) {
			return new TextMessage("愛");
		} else if(event.getMessage().getText().contains("美") && event.getMessage().getText().contains("?")) {
			return new TextMessage("美");
		} else if(event.getMessage().getText().contains("能") && event.getMessage().getText().contains("?")) {
			return new TextMessage("能");
		} else if(event.getMessage().getText().contains("帥") && event.getMessage().getText().contains("?")) {
			return new TextMessage("帥");
		} else if(event.getMessage().getText().contains("胖") && event.getMessage().getText().contains("?")) {
			return new TextMessage("不胖");
		} else if(event.getMessage().getText().contains("好") && event.getMessage().getText().contains("?")) {
			return new TextMessage("好");
		} else if(event.getMessage().getText().contains("對") && event.getMessage().getText().contains("?")) {
			return new TextMessage("對");
		} else if(event.getMessage().getText().contains("是") && event.getMessage().getText().contains("?")) {
			return new TextMessage("是");
		} else if(event.getMessage().getText().contains("學:")){
			hmap.put(event.getMessage().getText().split("學:")[1].split("#31#")[0], event.getMessage().getText().split("學:")[1].split("#31#")[1]);
			return new TextMessage("我學起來了。");
		} else if(event.getMessage().getText().contains("#") && event.getMessage().getText().length() == 5) { 
			code = event.getMessage().getText().split("#")[1];
			urlString = "http://finance.google.com/finance/info?client=ig&q=" + code;
			result = "查不到此股票";
			
			try {
				result = "1";
				url = new URL(urlString);
				result = "2";
				connection = (HttpURLConnection) url.openConnection();
				result = "3";
				connection.setRequestMethod("GET");
				result = "4";
				connection.setConnectTimeout(30000);
				result = "5";
				connection.setReadTimeout(30000);
				result = "6";
				connection.connect();
				result = "7";
				bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				result = "8";
				response = new StringBuilder();
				result = "9";
					
				while ((line = bufferedReader.readLine()) != null) {
					response.append(line+"\n");
				}
				result = response.toString();
				
				//time = result.split("\"lt\" : \"")[1].split("\"")[0];
				//money = result.split("\"l_cur\" : \"")[1].split("\"")[0];
				//up = result.split("\"c\" : \"")[1].split("\"")[0];
				//result = "交易時間 : " + time + "\n價格 : " + money + "\n漲幅 : " + up;
				
				return new TextMessage(result);
			} catch (Exception e) {
				e.printStackTrace();
				return new TextMessage(result);
			}
		} else {
			return null;
		}
    }

    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        System.out.println("event: " + event);
    }
}
