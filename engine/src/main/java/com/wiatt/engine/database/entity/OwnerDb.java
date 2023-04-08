package com.wiatt.engine.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.JoinProperty;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.wiatt.engine.database.greenDao.dao.DaoSession;
import com.wiatt.engine.database.greenDao.dao.RepoDbDao;
import com.wiatt.engine.database.greenDao.dao.OwnerDbDao;

@Entity
public class OwnerDb {

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
     * login
     */
    private String login;
    /**
     * node_id
     */
    @Unique
    @NotNull
    private String node_id;
    /**
     * Repos
     */
    @ToMany(joinProperties = {@JoinProperty(name = "idReal", referencedName = "ownerId")})
    private List<RepoDb> repoDbs;
    /**
     * html_url
     */
    private String html_url;
    /**
     * subscriptions_url
     */
    private String subscriptions_url;
    /**
     * repos_url
     */
    private String repos_url;
    /**
     * type
     */
    private String type;
    /**
     * name
     */
    private String name;
    /**
     * company
     */
    private String company;
    /**
     * location
     */
    private String location;
    /**
     * public_repos
     */
    private Integer public_repos;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 143139229)
    private transient OwnerDbDao myDao;
    @Generated(hash = 1846381691)
    public OwnerDb(Long id, Integer idReal, String login, @NotNull String node_id,
            String html_url, String subscriptions_url, String repos_url, String type,
            String name, String company, String location, Integer public_repos) {
        this.id = id;
        this.idReal = idReal;
        this.login = login;
        this.node_id = node_id;
        this.html_url = html_url;
        this.subscriptions_url = subscriptions_url;
        this.repos_url = repos_url;
        this.type = type;
        this.name = name;
        this.company = company;
        this.location = location;
        this.public_repos = public_repos;
    }
    @Generated(hash = 1150316313)
    public OwnerDb() {
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
    public String getLogin() {
        return this.login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getNode_id() {
        return this.node_id;
    }
    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }
    public String getHtml_url() {
        return this.html_url;
    }
    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }
    public String getSubscriptions_url() {
        return this.subscriptions_url;
    }
    public void setSubscriptions_url(String subscriptions_url) {
        this.subscriptions_url = subscriptions_url;
    }
    public String getRepos_url() {
        return this.repos_url;
    }
    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCompany() {
        return this.company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public Integer getPublic_repos() {
        return this.public_repos;
    }
    public void setPublic_repos(Integer public_repos) {
        this.public_repos = public_repos;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 913431795)
    public List<RepoDb> getRepoDbs() {
        if (repoDbs == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            RepoDbDao targetDao = daoSession.getRepoDbDao();
            List<RepoDb> repoDbsNew = targetDao._queryOwnerDb_RepoDbs(idReal);
            synchronized (this) {
                if (repoDbs == null) {
                    repoDbs = repoDbsNew;
                }
            }
        }
        return repoDbs;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 2060285891)
    public synchronized void resetRepoDbs() {
        repoDbs = null;
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
    @Generated(hash = 938930076)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getOwnerDbDao() : null;
    }
}
