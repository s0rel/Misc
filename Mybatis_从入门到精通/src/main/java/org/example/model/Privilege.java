package org.example.model;

import java.io.Serializable;

public class Privilege implements Serializable {
    private static final long serialVersionUID = 6320941908222932113L;

    private Long id;
    private String privilegeName;
    private String privilegeUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrivilegeName() {
        return privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public String getPrivilegeUrl() {
        return privilegeUrl;
    }

    public void setPrivilegeUrl(String privilegeUrl) {
        this.privilegeUrl = privilegeUrl;
    }

    @Override
    public String toString() {
        return "SysPrivilege{" +
                "id=" + id +
                ", privilegeName='" + privilegeName + '\'' +
                ", privilegeUrl='" + privilegeUrl + '\'' +
                '}';
    }
}
