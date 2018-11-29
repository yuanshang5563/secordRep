package org.ys.core.model;

import java.io.Serializable;
import java.util.Date;

public class CoreRole implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8524242104473256948L;

	/**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core_role.core_role_id
     *
     * @mbg.generated
     */
    private Long coreRoleId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core_role.role_name
     *
     * @mbg.generated
     */
    private String roleName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core_role.role
     *
     * @mbg.generated
     */
    private String role;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core_role.created_time
     *
     * @mbg.generated
     */
    private Date createdTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column core_role.modified_time
     *
     * @mbg.generated
     */
    private Date modifiedTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core_role.core_role_id
     *
     * @return the value of core_role.core_role_id
     *
     * @mbg.generated
     */
    public Long getCoreRoleId() {
        return coreRoleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core_role.core_role_id
     *
     * @param coreRoleId the value for core_role.core_role_id
     *
     * @mbg.generated
     */
    public void setCoreRoleId(Long coreRoleId) {
        this.coreRoleId = coreRoleId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core_role.role_name
     *
     * @return the value of core_role.role_name
     *
     * @mbg.generated
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core_role.role_name
     *
     * @param roleName the value for core_role.role_name
     *
     * @mbg.generated
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core_role.role
     *
     * @return the value of core_role.role
     *
     * @mbg.generated
     */
    public String getRole() {
        return role;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core_role.role
     *
     * @param role the value for core_role.role
     *
     * @mbg.generated
     */
    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core_role.created_time
     *
     * @return the value of core_role.created_time
     *
     * @mbg.generated
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core_role.created_time
     *
     * @param createdTime the value for core_role.created_time
     *
     * @mbg.generated
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column core_role.modified_time
     *
     * @return the value of core_role.modified_time
     *
     * @mbg.generated
     */
    public Date getModifiedTime() {
        return modifiedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column core_role.modified_time
     *
     * @param modifiedTime the value for core_role.modified_time
     *
     * @mbg.generated
     */
    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core_role
     *
     * @mbg.generated
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        CoreRole other = (CoreRole) that;
        return (this.getCoreRoleId() == null ? other.getCoreRoleId() == null : this.getCoreRoleId().equals(other.getCoreRoleId()))
            && (this.getRoleName() == null ? other.getRoleName() == null : this.getRoleName().equals(other.getRoleName()))
            && (this.getRole() == null ? other.getRole() == null : this.getRole().equals(other.getRole()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getModifiedTime() == null ? other.getModifiedTime() == null : this.getModifiedTime().equals(other.getModifiedTime()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core_role
     *
     * @mbg.generated
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCoreRoleId() == null) ? 0 : getCoreRoleId().hashCode());
        result = prime * result + ((getRoleName() == null) ? 0 : getRoleName().hashCode());
        result = prime * result + ((getRole() == null) ? 0 : getRole().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getModifiedTime() == null) ? 0 : getModifiedTime().hashCode());
        return result;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core_role
     *
     * @mbg.generated
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", coreRoleId=").append(coreRoleId);
        sb.append(", roleName=").append(roleName);
        sb.append(", role=").append(role);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", modifiedTime=").append(modifiedTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}