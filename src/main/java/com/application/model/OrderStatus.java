package com.application.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class OrderStatus {
	public static final Integer CANCEL = 0;
	public static final Integer SUCCESS = 1;
	public static final Integer WAITING = 2;
	public static final Integer WAITINGCONFIRM = 3;
	public static Map<Integer, String> map = new LinkedHashMap();
	static {
		map.put(WAITINGCONFIRM, "Chờ xác nhận");
		map.put(WAITING, "Đang giao hàng");
		map.put(SUCCESS, "Thành công");
		map.put(CANCEL, "Hủy đơn");
	}
	
}
