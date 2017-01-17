package com.zx.wfm.bean;

import java.util.List;
import com.zx.wfm.dao.DaoSession;
import de.greenrobot.dao.DaoException;

import com.zx.wfm.dao.MovieCourseDao;
import com.zx.wfm.dao.MoviebeanDao;
import com.zx.wfm.dao.TelevisionbeanDao;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "TELEVISIONBEAN".
 */
public class Televisionbean extends com.zx.wfm.bean.Basebean  implements java.io.Serializable {

    private String TelevisionId;
    private String addressUrl;
    private String desc;
    private String videoName;
    private String headUrl;
    private String rating;
    private String from;
    private Long time;
    private String netPage;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient TelevisionbeanDao myDao;

    private List<Moviebean> moviebeans;
    private List<MovieCourse> movieCourseList;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Televisionbean() {
    }

    public Televisionbean(String TelevisionId) {
        this.TelevisionId = TelevisionId;
    }

    public Televisionbean(String TelevisionId, String addressUrl, String desc, String videoName, String headUrl, String rating, String from, Long time, String netPage) {
        this.TelevisionId = TelevisionId;
        this.addressUrl = addressUrl;
        this.desc = desc;
        this.videoName = videoName;
        this.headUrl = headUrl;
        this.rating = rating;
        this.from = from;
        this.time = time;
        this.netPage = netPage;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTelevisionbeanDao() : null;
    }

    public String getTelevisionId() {
        return TelevisionId;
    }

    public void setTelevisionId(String TelevisionId) {
        this.TelevisionId = TelevisionId;
    }

    public String getAddressUrl() {
        return addressUrl;
    }

    public void setAddressUrl(String addressUrl) {
        this.addressUrl = addressUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getNetPage() {
        return netPage;
    }

    public void setNetPage(String netPage) {
        this.netPage = netPage;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<Moviebean> getMoviebeans() {
        if (moviebeans == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MoviebeanDao targetDao = daoSession.getMoviebeanDao();
            List<Moviebean> moviebeansNew = targetDao._queryTelevisionbean_Moviebeans(TelevisionId);
            synchronized (this) {
                if(moviebeans == null) {
                    moviebeans = moviebeansNew;
                }
            }
        }
        return moviebeans;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetMoviebeans() {
        moviebeans = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<MovieCourse> getMovieCourseList() {
        if (movieCourseList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MovieCourseDao targetDao = daoSession.getMovieCourseDao();
            List<MovieCourse> movieCourseListNew = targetDao._queryTelevisionbean_MovieCourseList(TelevisionId);
            synchronized (this) {
                if(movieCourseList == null) {
                    movieCourseList = movieCourseListNew;
                }
            }
        }
        return movieCourseList;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetMovieCourseList() {
        movieCourseList = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

    // KEEP METHODS - put your custom methods here
    public void setMoviebeans(List<Moviebean> moviebeans) {
        this.moviebeans = moviebeans;
    }

    public List<Moviebean> childrens(){
        return moviebeans;
    }

    @Override
    public boolean equals(Object o) {
        if(o!=null){
           return getAddressUrl().equals(((Televisionbean) o).getAddressUrl());
        }
        return super.equals(o);
    }

    @Override
    public String toString() {
        return "Televisionbean{" +
                "TelevisionId='" + TelevisionId + '\'' +
                ", addressUrl='" + addressUrl + '\'' +
                ", desc='" + desc + '\'' +
                ", videoName='" + videoName + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", rating='" + rating + '\'' +
                ", from='" + from + '\'' +
                ", time=" + time +
                ", daoSession=" + daoSession +
                ", myDao=" + myDao +
                ", moviebeans=" + moviebeans +
                ", movieCourseList=" + movieCourseList +
                '}';
    }
    // KEEP METHODS END

}
