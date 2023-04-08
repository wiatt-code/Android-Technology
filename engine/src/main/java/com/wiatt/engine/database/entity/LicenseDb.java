package com.wiatt.engine.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class LicenseDb {

    /**
     * 默认主键
     */
    @Id(autoincrement = true)
    private Long id;
    /**
     * node_id
     */
    @Index(unique = true)
    private String node_id;
    /**
     * key
     */
    private String key;
    /**
     * name
     */
    private String name;
    /**
     * spdx_id
     */
    private String spdx_id;
    /**
     * url
     */
    private String url;
    @Generated(hash = 447937292)
    public LicenseDb(Long id, String node_id, String key, String name,
            String spdx_id, String url) {
        this.id = id;
        this.node_id = node_id;
        this.key = key;
        this.name = name;
        this.spdx_id = spdx_id;
        this.url = url;
    }
    @Generated(hash = 1398332820)
    public LicenseDb() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNode_id() {
        return this.node_id;
    }
    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }
    public String getKey() {
        return this.key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSpdx_id() {
        return this.spdx_id;
    }
    public void setSpdx_id(String spdx_id) {
        this.spdx_id = spdx_id;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}
