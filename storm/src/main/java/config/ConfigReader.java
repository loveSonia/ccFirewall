package config;

import java.util.HashMap;
import java.io.*;

public class ConfigReader {

    private HashMap<String,HashMap<String, String>>  map = null;
    /**
     * 当前Section的引用
     */
    private String currentSection = null;

    /**
     * 读取
     * @param path
     */
    public ConfigReader(String path) {
        map = new HashMap<String,HashMap<String, String>>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            read(reader);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IO Exception:" + e);
        }
         
    }
 
    /**
     * 读取文件
     * @param reader
     * @throws IOException
     */
    private void read(BufferedReader reader) throws IOException {
        String line = null;
        while((line = reader.readLine()) != null) {
            parseLine(line);
        }
    }
     
    /**
     * 转换
     * @param line
     */
    private void parseLine(String line) {
        line = line.trim();
        // 去除=左右空格
        int i = line.indexOf("=");
        if (i > 0) {
        	String left = line.substring(0, i);
        	String right = line.substring(i + 1);
            if (line.charAt(i - 1) == ' '){
            	left = line.substring(0, i - 1);
            }

            if (line.charAt(i + 1) == ' '){
            	right = line.substring(i + 2);
            }
            line = left + "=" + right;
           // System.out.println(line);
        }
        
        // 此部分为注释
        if(line.matches("^\\#.*$")) {
            return;
        }
        else if (line.matches("^\\[\\S+\\]$")) {
            // section
            String section = line.replaceFirst("^\\[(\\S+)\\]$","$1");
            addSection(map,section);
        }
        else if (line.matches("^\\S+=.*$")) {
            // key ,value
            int index = line.indexOf("=");
            String key = line.substring(0, index).trim();
            String value =line.substring(index + 1).trim();
            addKeyValue(map,currentSection,key,value);
        }
    }
 
 
    /**
     * 增加新的Key和Value
     * @param map2
     * @param currentSection
     * @param key
     * @param value
     */
    private void addKeyValue(HashMap<String, HashMap<String, String>> map2,
            String currentSection,String key, String value) {
        if(!map2.containsKey(currentSection)) {
            return;
        }
         
        HashMap<String, String> childMap = map2.get(currentSection);
         
        childMap.put(key, value);
    }
 
 
    /**
     * 增加Section
     * @param map2
     * @param section
     */
    private void addSection(HashMap<String, HashMap<String, String>> map2,
            				String section) {
        if (!map2.containsKey(section)) {
            currentSection = section;
            HashMap<String, String> childMap = new HashMap<String, String>();
            map2.put(section, childMap);
        }
    }
     
    /**
     * 获取配置文件指定Section和指定子键的值
     * @param section
     * @param key
     * @return
     */
    public String get(String section,String key){
        if(map.containsKey(section)) {
            if (get(section).containsKey(key))
				return get(section).get(key);
			else
				return null;
        }
        return null;
    }
     
     
    /**
     * 获取配置文件指定Section的子键和值
     * @param section
     * @return
     */
    public HashMap<String, String> get(String section){
        if (map.containsKey(section))
			return map.get(section);
		else
			return null;
    }
     
    /**
     * 获取这个配置文件的节点和值
     * @return
     */
    public HashMap<String, HashMap<String, String>> get(){
        return map;
    }


}