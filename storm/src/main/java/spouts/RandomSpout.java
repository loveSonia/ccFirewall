package spouts;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import java.util.Random;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
//-------------------------------------------------------------------------------------------------
public class RandomSpout extends BaseRichSpout
{
 private SpoutOutputCollector collector;
 private Random rand;	
 private static String[] sentences = new String[] {"a b", "x y", "a a a", "m n q", "a b u"};
	
 @Override public void open(Map conf, TopologyContext context, SpoutOutputCollector collector)
 {
  this.collector = collector;
  this.rand = new Random();
  System.out.println("kobetest: spout open");
 }

 @Override public void nextTuple()
 {
  String toSay="";
  int i=0;
  while(i++<10)
       {
        toSay=sentences[rand.nextInt(sentences.length)];
        this.collector.emit(new Values(toSay));
       }
 }

 @Override public void declareOutputFields(OutputFieldsDeclarer declarer)
 {
  declarer.declare(new Fields("sentence"));
 }
}

