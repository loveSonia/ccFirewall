package bolts;

import java.util.Map;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Queue;
import java.util.HashMap;
import java.util.LinkedList;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.task.OutputCollector;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import backtype.storm.tuple.Fields;


public class RiskBolt extends BaseRichBolt {

	private OutputCollector collector;
	private Queue<HashMap<String, Integer>> queue;
	
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
		queue = new LinkedList<HashMap<String, Integer>>();
	}

	@Override
	public void execute(Tuple tuple) {
		System.out.println("risk bolt send his greet!");
		String content = tuple.getString(0);
		int pv = tuple.getInteger(1);
		try {
			if (pv >= 2) {
				System.out.println("which domain is in risk :" + content);
				File file = new File("/Users/Sonia/tmp.txt");
				if (!file.exists())
					file.createNewFile();
				FileWriter output = new FileWriter(file, true);
				output.write(new Date().toString() + " Risk Domain: " + content + " PV: " + pv + "\n");
				output.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("risk"));
	}
	
	

}
