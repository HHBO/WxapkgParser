package cn.hhh.wxapkg;

public class HeaderStruct {
	
	public byte magic1;
	public int unknow;
	public int offsetLen;
	public int bodyDataLen;
	public byte magic2;
	
	public int fileCount;
	
	public HeaderStruct() {
		magic1 = 0;
		unknow = 0;
		offsetLen = 0;
		bodyDataLen = 0;
		magic2 = 0;
	}
	
	public int getLen() {
		return 1 + 4 + 4 + 4 + 1 + 4;
	}
	
	public boolean isValid() {
		return (-66 == magic1) && (-19 == magic2);
	}

}
