package com.github.wp17.lina.db.mybatis.model;
import com.github.wp17.lina.db.mybatis.model.RoleInfo;
public class RoleInfoSharding extends RoleInfo {
	private int tableId;
	
	public int getTableId() {
		return tableId;
	}
	
	public void setTableId(int tableId) {
		this.tableId = tableId;
	}
	
	public RoleInfoSharding(){}
	
	public RoleInfoSharding(int tableId) {
		this.tableId = tableId;
	}
	
	public RoleInfoSharding(RoleInfo info, int tableId){
		this.tableId = tableId;
		setBirthday(info.getBirthday());
		setCity(info.getCity());
		setFeeling(info.getFeeling());
		setIcon(info.getIcon());
		setIcon2(info.getIcon2());
		setLastChargeTime(info.getLastChargeTime());
		setMood(info.getMood());
		setPopular(info.getPopular());
		setPropSet(info.getPropSet());
		setRoleUuid(info.getRoleUuid());
		setSoundFeeling(info.getSoundFeeling());
		setTotalZan(info.getTotalZan());
	}
}