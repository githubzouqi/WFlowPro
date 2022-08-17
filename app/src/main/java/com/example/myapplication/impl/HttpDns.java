package com.example.myapplication.impl;

import android.util.Log;

import com.qiniu.android.dns.DnsManager;
import com.qiniu.android.dns.IResolver;
import com.qiniu.android.dns.NetworkInfo;
import com.qiniu.android.dns.local.Resolver;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Dns;

public class HttpDns implements Dns {

    private DnsManager dnsManager;

    public HttpDns (){
        IResolver[] resolvers = new IResolver[1];
        try {
            resolvers[0] = new Resolver(InetAddress.getByName("114.114.114.114"));
            dnsManager = new DnsManager(NetworkInfo.normal, resolvers);
        }catch (Exception e){
            e.printStackTrace();
            Log.e("TAG", "> exception = " + e.getMessage());
        }
    }

    @Override
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {

        if (dnsManager == null){
            // 构造失败使用默认方式
            return Dns.SYSTEM.lookup(hostname);
        }

        try {
            String[] ips = dnsManager.query(hostname);
            if (ips == null || ips.length == 0){
                return Dns.SYSTEM.lookup(hostname);
            }
            List<InetAddress> result = new ArrayList<>();
            for (String ip : ips){
                //将ip地址数组转换成所需要的对象列表
                result.addAll(Arrays.asList(InetAddress.getAllByName(ip)));
            }
            Log.e("TAG", "> result = " + result.toString());
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        // 当发生异常时，使用系统默认解析
        return Dns.SYSTEM.lookup(hostname);
    }
}
