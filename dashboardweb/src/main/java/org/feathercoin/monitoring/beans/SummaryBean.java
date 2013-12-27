package org.feathercoin.monitoring.beans;

import org.feathercoin.monitoring.dto.Dev;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SummaryBean implements Serializable{
    private BigDecimal mhs5s;
    private BigDecimal mhsav;
    private String json;
    private String ip;
    private boolean hasErrors;

    private ArrayList<Dev> gpus = new ArrayList<Dev>();
    private String gpuSummaryJson;

    public SummaryBean(BigDecimal mhs5s, BigDecimal mhsav, String json, String ip) {
        this.mhs5s = mhs5s;
        this.mhsav = mhsav;
        this.json = json;
        this.ip = ip;
    }

    public SummaryBean(){

    }

    public String getGpuSummaryJson() {
        return gpuSummaryJson;
    }

    public void setGpuSummaryJson(String gpuSummaryJson) {
        this.gpuSummaryJson = gpuSummaryJson;
    }

    public List<Dev> getGpus() {
        return gpus;
    }

    public void addGpu(Dev gpuBean){
        gpus.add(gpuBean);
    }

    public void setMhs5s(BigDecimal mhs5s) {
        this.mhs5s = mhs5s;
    }

    public void setMhsav(BigDecimal mhsav) {
        this.mhsav = mhsav;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }

    public BigDecimal getMhs5s() {
        return mhs5s;
    }

    public BigDecimal getMhsav() {
        return mhsav;
    }

    public String getJson() {
        return json;
    }

    public String getIp() {
        return ip;
    }
}
