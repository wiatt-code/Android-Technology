package com.wiatt.engine.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.wiatt.engine.database.greenDao.dao.DaoSession;
import com.wiatt.engine.database.greenDao.dao.LicenseDbDao;
import com.wiatt.engine.database.greenDao.dao.RepoDbDao;

@Entity
public class RepoDb {

    /**
     * 默认主键
     */
    @Id(autoincrement = true)
    private Long id;
    /**
     * id
     */
    @Index(unique = true)
    private Integer idReal;
    /**
     * node_id
     */
    private String node_id;
    /**
     * name
     */
    private String name;
    /**
     * full_name
     */
    private String full_name;

    /**
     * OwnerId
     */
    @NotNull
    private Integer ownerId;

    /**
     * LicenseNodeId
     */
    private Long LicenseId;
    /**
     * license
     */
    @ToOne( joinProperty = "LicenseId")
    private LicenseDb license;

    /**
     * html_url
     */
    private String html_url;
    /**
     * description
     */
    private String description;
    /**
     * events_url
     */
    private String events_url;
    /**
     * created_at
     */
    private String created_at;
    /**
     * updated_at
     */
    private String updated_at;
    /**
     * pushed_at
     */
    private String pushed_at;
    /**
     * git_url
     */
    private String git_url;
    /**
     * ssh_url
     */
    private String ssh_url;
    /**
     * clone_url
     */
    private String clone_url;
    /**
     * svn_url
     */
    private String svn_url;
    /**
     * stargazers_count
     */
    private Integer stargazers_count;
    /**
     * watchers_count
     */
    private Integer watchers_count;
    /**
     * forks_count
     */
    private Integer forks_count;
    /**
     * open_issues_count
     */
    private Integer open_issues_count;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 108874412)
    private transient RepoDbDao myDao;
    @Generated(hash = 1279193429)
    public RepoDb(Long id, Integer idReal, String node_id, String name,
            String full_name, @NotNull Integer ownerId, Long LicenseId,
            String html_url, String description, String events_url,
            String created_at, String updated_at, String pushed_at, String git_url,
            String ssh_url, String clone_url, String svn_url,
            Integer stargazers_count, Integer watchers_count, Integer forks_count,
            Integer open_issues_count) {
        this.id = id;
        this.idReal = idReal;
        this.node_id = node_id;
        this.name = name;
        this.full_name = full_name;
        this.ownerId = ownerId;
        this.LicenseId = LicenseId;
        this.html_url = html_url;
        this.description = description;
        this.events_url = events_url;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.pushed_at = pushed_at;
        this.git_url = git_url;
        this.ssh_url = ssh_url;
        this.clone_url = clone_url;
        this.svn_url = svn_url;
        this.stargazers_count = stargazers_count;
        this.watchers_count = watchers_count;
        this.forks_count = forks_count;
        this.open_issues_count = open_issues_count;
    }
    @Generated(hash = 1571608222)
    public RepoDb() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Integer getIdReal() {
        return this.idReal;
    }
    public void setIdReal(Integer idReal) {
        this.idReal = idReal;
    }
    public String getNode_id() {
        return this.node_id;
    }
    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFull_name() {
        return this.full_name;
    }
    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
    public Integer getOwnerId() {
        return this.ownerId;
    }
    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }
    public Long getLicenseId() {
        return this.LicenseId;
    }
    public void setLicenseId(Long LicenseId) {
        this.LicenseId = LicenseId;
    }
    public String getHtml_url() {
        return this.html_url;
    }
    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getEvents_url() {
        return this.events_url;
    }
    public void setEvents_url(String events_url) {
        this.events_url = events_url;
    }
    public String getCreated_at() {
        return this.created_at;
    }
    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    public String getUpdated_at() {
        return this.updated_at;
    }
    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
    public String getPushed_at() {
        return this.pushed_at;
    }
    public void setPushed_at(String pushed_at) {
        this.pushed_at = pushed_at;
    }
    public String getGit_url() {
        return this.git_url;
    }
    public void setGit_url(String git_url) {
        this.git_url = git_url;
    }
    public String getSsh_url() {
        return this.ssh_url;
    }
    public void setSsh_url(String ssh_url) {
        this.ssh_url = ssh_url;
    }
    public String getClone_url() {
        return this.clone_url;
    }
    public void setClone_url(String clone_url) {
        this.clone_url = clone_url;
    }
    public String getSvn_url() {
        return this.svn_url;
    }
    public void setSvn_url(String svn_url) {
        this.svn_url = svn_url;
    }
    public Integer getStargazers_count() {
        return this.stargazers_count;
    }
    public void setStargazers_count(Integer stargazers_count) {
        this.stargazers_count = stargazers_count;
    }
    public Integer getWatchers_count() {
        return this.watchers_count;
    }
    public void setWatchers_count(Integer watchers_count) {
        this.watchers_count = watchers_count;
    }
    public Integer getForks_count() {
        return this.forks_count;
    }
    public void setForks_count(Integer forks_count) {
        this.forks_count = forks_count;
    }
    public Integer getOpen_issues_count() {
        return this.open_issues_count;
    }
    public void setOpen_issues_count(Integer open_issues_count) {
        this.open_issues_count = open_issues_count;
    }
    @Generated(hash = 1286534898)
    private transient Long license__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1326325923)
    public LicenseDb getLicense() {
        Long __key = this.LicenseId;
        if (license__resolvedKey == null || !license__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LicenseDbDao targetDao = daoSession.getLicenseDbDao();
            LicenseDb licenseNew = targetDao.load(__key);
            synchronized (this) {
                license = licenseNew;
                license__resolvedKey = __key;
            }
        }
        return license;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 563891545)
    public void setLicense(LicenseDb license) {
        synchronized (this) {
            this.license = license;
            LicenseId = license == null ? null : license.getId();
            license__resolvedKey = LicenseId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 509600528)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRepoDbDao() : null;
    }
}
