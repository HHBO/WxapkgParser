package cn.hhh.wxapkg;

public class DataIndexStruct {
	
	public int nameLen;
	public String name;
	public int dataOffset;
	public int dataLen;
	
	public DataIndexStruct() {
		nameLen = 0;
		name = null;
		dataOffset = 0;
		dataLen = 0;
	}
	
	public int getLen() {
		return 4 + nameLen + 4 + 4;
	}
}
