package spouts;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Map;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import redis.clients.jedis.*;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;
//-------------------------------------------------------------------------------------------------
public class WordReader extends BaseRichSpout
{
 private SpoutOutputCollector collector;
 private FileReader fileReader;
 private Jedis redis;
 private boolean completed=false;
 //----------------------------------------------
 @Override public void nextTuple()
 {
  /*if(completed)
    {
	 try {Thread.sleep(1000);}
     catch (InterruptedException e) {}
	 return;
	}
  String str;
  BufferedReader reader=new BufferedReader(fileReader);
  try
     {
	  while((str=reader.readLine())!=null)
			this.collector.emit(new Values(str));
	 }
  catch(Exception e)
     {throw new RuntimeException("kobetest Error reading tuple",e);}
  finally
     {completed=true;}
  */
  String str=redis.lpop("jedis");
  if(str!=null)
     this.collector.emit(new Values(str));
 }
 @Override public void open(Map conf,TopologyContext context,SpoutOutputCollector collector)
 {
  redis=new Jedis("127.0.0.1",6379);
  /*try
     {
	  this.fileReader=new FileReader("/tmp/words.txt");
	 }
  catch(FileNotFoundException e)
     {
	  throw new RuntimeException("kobetest failed to read file ["+conf.get("wordsFile")+"]");
	 }*/
  this.collector=collector;
 }
 @Override public void declareOutputFields(OutputFieldsDeclarer declarer)
 {declarer.declare(new Fields("line"));}
}//end Class
