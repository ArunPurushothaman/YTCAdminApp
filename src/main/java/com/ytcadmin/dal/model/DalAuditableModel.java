package com.ytcadmin.dal.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;


@MappedSuperclass
public abstract class DalAuditableModel extends DalModel {


    /**
	 * Default serial version.
	 */
	private static final long serialVersionUID = 1L;
	/*protected DalUser createdBy;*/
    protected DalUserMaster createdBy;
    protected Calendar createdDate;
    /*protected DalUser modifiedBy;*/
    protected DalUserMaster modifiedBy;
    protected Calendar modifiedDate;
    
    public DalAuditableModel() {
        super();
        long curTimeMillis = System.currentTimeMillis();
        createdDate = Calendar.getInstance();
        createdDate.clear();
        createdDate.setTimeInMillis(curTimeMillis);
        modifiedDate= Calendar.getInstance();
        modifiedDate.clear();
        modifiedDate.setTimeInMillis(curTimeMillis);
    }

    public DalAuditableModel(DalAuditableModel m) {
        super();
        this.modifiedBy = m.modifiedBy;
        this.createdBy = m.createdBy;
        this.modifiedDate = m.modifiedDate;
        this.createdDate = m.createdDate;
    }

    @OneToOne(targetEntity = com.ytcadmin.dal.model.DalUserMaster.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "CREATED_BY", updatable = false, insertable = true)
    public DalUserMaster getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(DalUserMaster createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "CREATED_DATE", columnDefinition = "timestamp", updatable = false, insertable = true)
    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    @OneToOne(targetEntity = com.ytcadmin.dal.model.DalUserMaster.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "MODIFIED_BY")
    public DalUserMaster getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(DalUserMaster modifiedBy) {
        this.modifiedBy = modifiedBy;
    }


    //@Version
    @Column(name = "MODIFIED_DATE", columnDefinition = "timestamp",updatable = true, insertable = true)
    public Calendar getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Calendar modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

   
   
}
