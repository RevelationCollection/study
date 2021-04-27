package com.study.dubbo.loadblance;


import java.math.BigDecimal;
import java.util.Date;

public class SmsChannel {
    private static final long serialVersionUID = 1L;


    private Long id;

    private Date createTime;

    private Date updateTime;

    private Integer version;
    private String channelCode;
    private String channelName;
    private BigDecimal channelCost;
    private String channelHost;
    private String signature;
    private int singleMaxSize;
    private String accountName;
    private String accountPassword;
    private Boolean isTemplate;
    private Boolean payMethod;
    private Boolean channelStatus;
    private String channelType;
    private String location;
    private Integer weight;
    private String remark;
    private String serviceName;
    private String extendField;

    public Integer getWeight() {
        if (weight==null) return 1;
        return weight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public BigDecimal getChannelCost() {
        return channelCost;
    }

    public void setChannelCost(BigDecimal channelCost) {
        this.channelCost = channelCost;
    }

    public String getChannelHost() {
        return channelHost;
    }

    public void setChannelHost(String channelHost) {
        this.channelHost = channelHost;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getSingleMaxSize() {
        return singleMaxSize;
    }

    public void setSingleMaxSize(int singleMaxSize) {
        this.singleMaxSize = singleMaxSize;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public Boolean getTemplate() {
        return isTemplate;
    }

    public void setTemplate(Boolean template) {
        isTemplate = template;
    }

    public Boolean getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Boolean payMethod) {
        this.payMethod = payMethod;
    }

    public Boolean getChannelStatus() {
        return channelStatus;
    }

    public void setChannelStatus(Boolean channelStatus) {
        this.channelStatus = channelStatus;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getExtendField() {
        return extendField;
    }

    public void setExtendField(String extendField) {
        this.extendField = extendField;
    }
}