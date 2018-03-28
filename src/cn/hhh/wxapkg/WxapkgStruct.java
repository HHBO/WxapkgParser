package cn.hhh.wxapkg;


import java.util.ArrayList;
import java.util.List;

import javax.rmi.CORBA.Util;
import javax.swing.text.AbstractDocument.LeafElement;

public class WxapkgStruct {
	public DataIndexStruct data;
	public HeaderStruct header;
	public List<DataIndexStruct> indexStructList;
	
	WxapkgStruct() {
		data = new DataIndexStruct();
		header = new HeaderStruct();
		indexStructList = new ArrayList<DataIndexStruct>();
	}
	
	//解析小程序
	public boolean parse(byte[] data) {
		parseHeaderStruct(data);
		System.out.println("head info:");
		System.out.println("magic1 = "+header.magic1);
		System.out.println("offsetLen = "+header.offsetLen);
		System.out.println("bodyDataLen = "+header.bodyDataLen);
		System.out.println("magic2 = "+header.magic2);
		System.out.println("fileCount = "+header.fileCount);
		System.out.println("data.length = "+data.length);
		parseIndexStructList(Utils.copyByte(data, 18, data.length - header.getLen()));
		return header.isValid();
	}
	
	//解析头部信息
	private void parseHeaderStruct(byte[] data) {
		try {
			header.magic1 = data[0];
			header.unknow = Utils.byte2int(Utils.copyByte(data, 1, 4));
			header.offsetLen = Utils.byte2int(Utils.copyByte(data, 5, 4));
			header.bodyDataLen = Utils.byte2int(Utils.copyByte(data, 9, 4));
			header.magic2 = data[13];
			header.fileCount = Utils.byte2int(Utils.copyByte(data, 14, 4));
		}catch(Exception e) {
			System.out.println("parse header err:" + e.toString());
			System.exit(1);
		}
	}
	
	//解析一个索引段
	private DataIndexStruct parseIndexStruct(byte[] data, int offset) {
		DataIndexStruct indexStruct = new DataIndexStruct();
		try {
			indexStruct.nameLen = Utils.byte2int(Utils.copyByte(data, 0 + offset, 4));
			indexStruct.name = new String(Utils.copyByte(data, 4+offset, indexStruct.nameLen), "utf-8");
			indexStruct.dataOffset = Utils.byte2int(Utils.copyByte(data, 4+indexStruct.nameLen + offset, 4));
			indexStruct.dataLen = Utils.byte2int(Utils.copyByte(data, 8+indexStruct.nameLen + offset, 4));
			System.out.println("indexStruct.nameLen = "+indexStruct.nameLen);
			System.out.println("indexStruct.name = "+indexStruct.name);
			System.out.println("indexStruct.dataOffset = "+indexStruct.dataOffset);
			System.out.println("indexStruct.dataLen = "+indexStruct.dataLen);
		} catch (Exception e) {
			System.out.println("parse data index err:" + e.toString());
			System.exit(1);
		}
		return indexStruct;
	}
	
	//解析出所有的索引段信息
	private void parseIndexStructList(byte[] data) {
		try {
			DataIndexStruct indexStruct = parseIndexStruct(data, 0);
			int offset = 0;
			for(int i=1; i<header.fileCount; i++) {
				offset += indexStruct.getLen();
				indexStructList.add(indexStruct);
				indexStruct = parseIndexStruct(data, offset);
			}
		} catch (Exception e) {
			System.out.println("parse index struct list err:" + e.toString());
		}
	}
	
}

















