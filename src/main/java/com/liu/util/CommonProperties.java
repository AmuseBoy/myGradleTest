package com.liu.util;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置信息实现类，支持自动注入 Created by Liuhongbin on 2016/4/7.
 */
@ConfigurationProperties(prefix = "common")
public class CommonProperties {
    private static Map<String, Object> address;
    private static Map<String, Object> file;
    private static Map<String, Object> sbs;
    private static Map<String, Object> scheduled;
    private static Map<String, Object> vr;
    private static Map<String, Object> company;
    private static Map<String, Object> custTagId;

   

	public Map<String, Object> getVr() {
		return vr;
	}

	public void setVr(Map<String, Object> vr) {
		CommonProperties.vr = vr;
	}

	public Map<String, Object> getScheduled() {
		return scheduled;
	}

	public void setScheduled(Map<String, Object> scheduled) {
		CommonProperties.scheduled = scheduled;
	}

    public Map<String, Object> getAddress() {
        return address;
    }

    public void setAddress(Map<String, Object> address) {
        CommonProperties.address = address;
    }

    public Map<String, Object> getFile() {
        return file;
    }

    public void setFile(Map<String, Object> file) {
        CommonProperties.file = file;
    }
    
    public Map<String, Object> getSbs() {
        return sbs;
    }

    public void setSbs(Map<String, Object> sbs) {
        CommonProperties.sbs = sbs;
    }

    
    

    public Map<String, Object> getCompany() {
		return company;
	}

	public void setCompany(Map<String, Object> company) {
		CommonProperties.company = company;
	}
	
	
	

	public Map<String, Object> getCustTagId() {
		return custTagId;
	}

	public void setCustTagId(Map<String, Object> custTagId) {
		CommonProperties.custTagId = custTagId;
	}

	public static Object get(String key) {
        if (key == null || key.equals("")) {
            return null;
        }
        if (key.contains(".")) {
            String[] keys = key.split("\\.");
            if (keys.length == 2) {
                Map<String, Object> map = (Map<String, Object>) get(keys[0]);
                if (map != null) {
                    return map.get(keys[1]);
                }
            }
        } else if (key.equals("address")) {
            return address;
        } else if (key.equals("file")) {
            return file;
        } else if (key.equals("sbs")) {
            return sbs;
        }else if (key.equals("scheduled")) {
            return scheduled;
        }else if (key.equals("vr")) {
            return vr;
        }else if (key.equals("company")) {
            return company;
        }else if (key.equals("custTagId")) {
            return custTagId;
        }
        return null;
    }
}
