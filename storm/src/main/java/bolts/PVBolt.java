package bolts;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.LinkedList;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.*;
import backtype.storm.tuple.Values;
import backtype.storm.tuple.Fields;
import backtype.storm.*;
import org.json.JSONObject;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.task.OutputCollector;


public class PVBolt extends BaseRichBolt {
	private OutputCollector collector;
	private HashMap<String, Integer> counter;
	private Queue< HashMap<String,Integer>> queue;
	
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		counter = new HashMap<String, Integer>();
		queue = new LinkedList<HashMap<String,Integer>>() ;
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		Config conf = new Config();
		conf.put(conf.TOPOLOGY_TICK_TUPLE_FREQ_SECS, 1);
		return conf;
	}

	@Override
	public void execute(Tuple tuple) {
		System.out.println("CountBolt received something!");
		if (this.isTickTuple(tuple)) {
			System.out.println("I am sending message to riskbolt");
			for (Map.Entry<String, Integer> e : counter.entrySet()) {
		        collector.emit(new Values(e.getKey(), e.getValue()));
			}
			counter.clear();
		} 
		else {
			String domain = tuple.getString(0);
			JSONObject json = new JSONObject(domain);
			Object host = json.get("host");
			Object ip = json.get("client_ip");
			System.out.println("host is :" + host);
			System.out.println("ip is :" + ip);
			//System.out.println("There is tuple :" + domain);



			if (!counter.containsKey(host)) {
				counter.put(host + "", 0);
			}
			counter.put(host + "", counter.get(host + "") + 1);
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("domain", "pv"));
	}

	protected static boolean isTickTuple(Tuple tuple) {
        return tuple.getSourceComponent().equals(Constants.SYSTEM_COMPONENT_ID)
            && tuple.getSourceStreamId().equals(Constants.SYSTEM_TICK_STREAM_ID);
    }
}
/*package test;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Test {
	public static int maxBucket = 3;
	public static long beginTime;
	
	public static long getTime( Date date ) {
		return date.getTime();
	}
	
	public static void printMap( HashMap<String,Integer> hostCount ) {
		System.out.println("map : ");
		for(String key : hostCount.keySet()) {
			System.out.println(key + " : " + hostCount.get(key));
		}
	}
	
	public static void printQueue( Queue< HashMap<String,Integer> > que ) {
		System.out.println("queue : ");
		while(que.size() >= 1) {
			System.out.println(que.poll());
		}
	}
	
	public static void pushQueue( HashMap<String,Integer> hostCount , Queue< HashMap<String,Integer> > que) {
		Date date = new Date();
		long time = getTime(date);
		//System.out.println("time : " + time); 
		long timeDistance = time - beginTime;
		if( timeDistance % 3 == 0) {
		//if(count % 2 == 0){
			System.out.println(" beginTime : " + beginTime + " time : " + time + " timeDistance : " + timeDistance); 
			HashMap<String,Integer> hosttemp = new HashMap<String,Integer>();
			for (String key : hostCount.keySet()) {
				hosttemp.put(key, hostCount.get(key));
			}
			que.add(hosttemp);
			//System.out.println(que.peek());
			if( que.size() > maxBucket ){
				que.poll();
			}
		}
	}
	
	public static void sleepRandom() throws InterruptedException {
		int number = (int)(Math.random() * 10);
		//System.out.println("number : " + number);
		Thread.sleep(number);
	}
	
	public static void main(String[] argvs) throws Exception {
		File file = new File("in.txt");
		if( !file.exists() ){
			file.createNewFile();
		}
		Scanner input = new Scanner(file);
				
		HashMap<String,Integer> hostCount = new HashMap<String,Integer>();
		Queue< HashMap<String,Integer> > que = new LinkedList< HashMap<String,Integer> > () ;
		
		Date date = new Date();
		beginTime = getTime(date);
		System.out.println("beginTime : " + beginTime); 
		
		while(input.hasNext() ) {
			//count++;
			sleepRandom();
			String host = input.nextLine();
			//System.out.println(host);
			if( hostCount.containsKey(host) ){
				hostCount.put(host, hostCount.get(host) + 1);
			}
			else{
				hostCount.put(host, 1);
			}	
			pushQueue(hostCount,que);
			//System.out.println(que.peek());
			//hostCount[temp]++;
		}
		System.out.println("Hello World!");
		
		printMap(hostCount);
		printQueue(que);
		input.close();
	}
	
	
}
*/
