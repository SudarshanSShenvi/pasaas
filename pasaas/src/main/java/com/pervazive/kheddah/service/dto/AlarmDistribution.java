package com.pervazive.kheddah.service.dto;

public class AlarmDistribution {
	
	public AlarmDistribution(Integer alarmno, Long count) {
		super();
		this.alarmno = alarmno;
		this.count = count;
	}
	
	public AlarmDistribution(){
		
	}
	
	private Integer alarmno;
    private Long count;
    
    
	public Integer getAlarmno() {
		return alarmno;
	}
	public void setAlarmno(Integer alarmno) {
		this.alarmno = alarmno;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
    
    
    
}
