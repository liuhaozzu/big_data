package com.liuhaozzu.bigdata.flumehbase.test;
//package com.laoxiao.test;
//
//import java.nio.charset.Charset;
//import java.util.Random;
//import java.util.TimerTask;
//import java.util.UUID;
//
//import org.apache.flume.Event;
//import org.apache.flume.EventDeliveryException;
//import org.apache.flume.api.RpcClient;
//import org.apache.flume.api.RpcClientFactory;
//import org.apache.flume.event.EventBuilder;
//
//public class WebServer extends TimerTask {
//	
//	Random r =new Random();
//	String[] urls = new String[] {
//			"http://item.yhd.com/item/37837059?tc=3.0.5.37837059.16",
//			"http://www.yhd.com/?tracker_u=98",
//			"http://cms.yhd.com/sale/130650",
//			"http://search.yhd.com/c0-0/k%E4%BA%91%E5%8D%97%E7%99%BD%E8%8D%AF" };
//
//	String prefix_si = "QWEQA6Y9N32J2YSGB24DRKT736EFF78455";
//
//	public static MyRpcClientFacade client=null;
//	/**
//	 * 多线程模拟点击页面，调用该方法
//	 */
//	public void writeLog() {
//		if(client==null){
//			client = new MyRpcClientFacade();
//			client.init("node13", 55555);
//		}
//		int i=r.nextInt(5);
//		String refer=i==4?"":urls[i];
//		String si=prefix_si+String.format("%02d", r.nextInt(20));
//		//日志编号，访问URL，来源URL，sessionID,IP
//		String log = UUID.randomUUID()+","+urls[r.nextInt(4)]+","+refer+","+si+","+"ip";
//		client.sendDataToFlume(log);
////		client.cleanUp();
//	}
//
//	public void run() {
//		writeLog();
//	}
//}
//
//class MyRpcClientFacade {
//	private RpcClient client;
//	private String hostname;
//	private int port;
//
//	public void init(String hostname, int port) {
//		// Setup the RPC connection
//		this.hostname = hostname;
//		this.port = port;
//		this.client = RpcClientFactory.getDefaultInstance(hostname, port,1024);
//		// Use the following method to create a thrift client (instead of the
//		// above line):
//		// this.client = RpcClientFactory.getThriftInstance(hostname, port);
//	}
//
//	public void sendDataToFlume(String data) {
//		// Create a Flume Event object that encapsulates the sample data
//		Event event = EventBuilder.withBody(data, Charset.forName("UTF-8"));
//
//		// Send the event
//		try {
//			client.append(event);
//		} catch (EventDeliveryException e) {
//			client.close();
//			client = null;
//			client = RpcClientFactory.getDefaultInstance(hostname, port);
//		}
//	}
//
//	public void cleanUp() {
//		client.close();
//	}
//
//}
