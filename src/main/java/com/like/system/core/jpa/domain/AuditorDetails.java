package com.like.system.core.jpa.domain;

import java.io.Serializable;
import java.util.Objects;

public final class AuditorDetails implements Serializable {    
	private static final long serialVersionUID = 6038720304169228462L;
	   
    private final String hostIp;
    private final String loggedUser;

    public AuditorDetails(String hostIp, String loggedUser) {
    	this.hostIp = hostIp;
    	this.loggedUser = loggedUser;                
    }
    
    public String getHostIp() {
        return hostIp;
    }
    
    public String getLoggedUser() {
        return loggedUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuditorDetails that = (AuditorDetails) o;
        return Objects.equals(hostIp, that.hostIp) && Objects.equals(loggedUser, that.loggedUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hostIp, loggedUser);
    }
}