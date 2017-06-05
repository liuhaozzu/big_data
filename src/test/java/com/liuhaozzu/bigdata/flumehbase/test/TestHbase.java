package com.liuhaozzu.bigdata.flumehbase.test;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.MultipleColumnPrefixFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import com.liuhaozzu.bigdata.hadoophbase.common.EventLogConstants;
import com.liuhaozzu.bigdata.hadoophbase.common.EventLogConstants.EventEnum;
import com.liuhaozzu.bigdata.hadoophbase.common.GlobalConstants;
import com.liuhaozzu.bigdata.hadoophbase.util.TimeUtil;

public class TestHbase {

	public static void main(String[] args) {
		try {
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://hadoop1:8020");
			conf.set("yarn.resourcemanager.hostname", "hadoop1");
			conf.set("hbase.zookeeper.quorum", "hadoop1,hadoop2,hadoop3");
			conf = HBaseConfiguration.create(conf);
			HTable table = new HTable(conf, "event_logs".getBytes());

			String date = "2016-03-23";
			long startDate = TimeUtil.parseString2Long(date);
			long endDate = startDate + GlobalConstants.DAY_OF_MILLISECONDS;
			System.out.println();
			Scan scan = new Scan();
			// 定义hbase扫描的开始rowkey和结束rowkey
			scan.setStartRow(Bytes.toBytes("" + startDate));
			scan.setStopRow(Bytes.toBytes("" + endDate));

			FilterList filterList = new FilterList();
			// 过滤数据，只分析launch事件
			filterList.addFilter(new SingleColumnValueFilter(Bytes.toBytes(EventLogConstants.EVENT_LOGS_FAMILY_NAME),
					Bytes.toBytes(EventLogConstants.LOG_COLUMN_NAME_EVENT_NAME), CompareOp.EQUAL,
					Bytes.toBytes(EventEnum.LAUNCH.alias)));
			// 定义mapper中需要获取的列名
			String[] columns = new String[] { EventLogConstants.LOG_COLUMN_NAME_EVENT_NAME,
					EventLogConstants.LOG_COLUMN_NAME_UUID, EventLogConstants.LOG_COLUMN_NAME_SERVER_TIME,
					EventLogConstants.LOG_COLUMN_NAME_PLATFORM, EventLogConstants.LOG_COLUMN_NAME_BROWSER_NAME,
					EventLogConstants.LOG_COLUMN_NAME_BROWSER_VERSION };
			// scan.addColumn(family, qualifier)
			filterList.addFilter(getColumnFilter(columns));

			scan.setAttribute(Scan.SCAN_ATTRIBUTES_TABLE_NAME, Bytes.toBytes(EventLogConstants.HBASE_NAME_EVENT_LOGS));
			scan.setFilter(filterList);

			ResultScanner ress = table.getScanner(scan);
			for (Result res : ress) {
				Cell cell = res.getColumnLatestCell("info".getBytes(),
						EventLogConstants.LOG_COLUMN_NAME_UUID.getBytes());
				System.out.println(new String(CellUtil.cloneValue(cell)));
			}
			ress.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Filter getColumnFilter(String[] columns) {
		int length = columns.length;
		byte[][] filter = new byte[length][];
		for (int i = 0; i < length; i++) {
			filter[i] = Bytes.toBytes(columns[i]);
		}
		return new MultipleColumnPrefixFilter(filter);
	}
}
