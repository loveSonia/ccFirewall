import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import spouts.TmpSpout;
import bolts.RiskBolt;
import bolts.PVBolt;
import config.CCConfig;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.StormTopology;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import storm.kafka.ZkHosts;
import storm.kafka.BrokerHosts;
import storm.kafka.ZkHosts;
import storm.kafka.SpoutConfig;
import storm.kafka.*;
import storm.kafka.StringScheme;


public class TopologyMain {

    public static void main(String[] args) throws Exception{

        if (args.length != 1) {
            System.out.println("Argument Error!");
            return ;
        }

        CCConfig.getConfigInfo(args[0]);
      
        TopologyBuilder builder = new TopologyBuilder();
      
        BrokerHosts hosts = new ZkHosts(CCConfig.ZkHosts);
        SpoutConfig spoutConfig = new SpoutConfig(hosts, "test", "", UUID.randomUUID().toString());
        spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
        KafkaSpout kafkaSpout = new KafkaSpout(spoutConfig);

        // set Spout.
        builder.setSpout("word", kafkaSpout, 3);
        //builder.setSpout("word", new TmpSpout());
        builder.setBolt("count", new PVBolt()).shuffleGrouping("word");
        builder.setBolt("risk", new RiskBolt(), 2).shuffleGrouping("count");
        System.out.println("set down");

        Config conf = new Config();
        conf.setDebug(true);
        conf.setMaxTaskParallelism(2);
        System.out.println("conf down");

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("test", conf, builder.createTopology());

        Thread.sleep(30000);
        System.out.println("Ready to shut down");
        cluster.shutdown();
        System.out.println("shut down");
    }
  

}
