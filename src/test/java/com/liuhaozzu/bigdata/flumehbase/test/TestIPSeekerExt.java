package com.liuhaozzu.bigdata.flumehbase.test;

import java.util.List;

import com.liuhaozzu.bigdata.hadoophbase.etl.util.IPSeekerExt;
import com.liuhaozzu.bigdata.hadoophbase.etl.util.IPSeekerExt.RegionInfo;

public class TestIPSeekerExt {
	public static void main(String[] args) {
		IPSeekerExt ipSeekerExt = new IPSeekerExt();
		RegionInfo info = ipSeekerExt.analyticIp("192.168.1.9");
		System.out.println(info);

		List<String> ips = ipSeekerExt.getAllIp();
		for (String ip : ips) {
			System.out.println(ipSeekerExt.analyticIp(ip));
		}
	}
}
