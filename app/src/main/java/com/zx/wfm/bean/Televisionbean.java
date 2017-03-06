package com.zx.wfm.bean;

import android.os.Parcel;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.List;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVUser;
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
 @AVClassName("Televisionbean")
public class Televisionbean extends com.zx.wfm.bean.Basebean  implements java.io.Serializable {

    private String objectId;
    private String TelevisionId;
    private String addressUrl;
    private String desc;
    private String videoName;
    private String headUrl;
    private String rating;
    private String from;
    private Long time;
    private String playTimes;
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

    public Televisionbean(String objectId, String TelevisionId, String addressUrl, String desc, String videoName, String headUrl, String rating, String from, Long time, String playTimes, String netPage) {
        this.objectId = objectId;
        this.TelevisionId = TelevisionId;
        this.addressUrl = addressUrl;
        this.desc = desc;
        this.videoName = videoName;
        this.headUrl = headUrl;
        this.rating = rating;
        this.from = from;
        this.time = time;
        this.playTimes = playTimes;
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

    public String getPlayTimes() {
        return playTimes;
    }

    public void setPlayTimes(String playTimes) {
        this.playTimes = playTimes;
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
    //此处为我们的默认实现，当然你也可以自行实现


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Televisionbean{" +
                "objectId='" + objectId + '\'' +
                ", TelevisionId='" + TelevisionId + '\'' +
                ", addressUrl='" + addressUrl + '\'' +
                ", desc='" + desc + '\'' +
                ", videoName='" + videoName + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", rating='" + rating + '\'' +
                ", from='" + from + '\'' +
                ", time=" + time +
                ", playTimes='" + playTimes + '\'' +
                ", netPage='" + netPage + '\'' +
                ", daoSession=" + daoSession +
                ", myDao=" + myDao +
                ", moviebeans=" + moviebeans +
                ", movieCourseList=" + movieCourseList +
                '}';
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(objectId);
        out.writeString(TelevisionId);
        out.writeString(addressUrl);
        out.writeString(desc);
        out.writeString(videoName);
        out.writeString(headUrl);
        out.writeString(rating);
        out.writeString(from);
        out.writeLong(time);
        out.writeString(playTimes);
        out.writeString(netPage);

//        private String objectId;
//        private String TelevisionId;
//        private String addressUrl;
//        private String desc;
//        private String videoName;
//        private String headUrl;
//        private String rating;
//        private String from;
//        private Long time;
//        private String netPage;
    }
    private Televisionbean(Parcel in)
    {
        objectId = in.readString();
        TelevisionId=in.readString();
        addressUrl=in.readString();
        desc=in.readString();
        videoName=in.readString();
        headUrl=in.readString();
        rating=in.readString();
        from=in.readString();
        time=in.readLong();
        playTimes=in.readString();
        netPage=in.readString();
    }

    public static final Creator CREATOR = new Creator<Televisionbean>() {
        @Override
        public Televisionbean createFromParcel(Parcel source) {
            return new Televisionbean(source);
        }

        @Override
        public Televisionbean[] newArray(int size) {
            return new Televisionbean[0];
        }
    };

    public void setMoviebeans(List<Moviebean> moviebeans) {
        this.moviebeans = moviebeans;
    }

    public List<Moviebean> childrens(){
        return moviebeans;
    }


    @Override
    protected List<String> getField() {
        return Arrays.asList("objectId","netPage","time","playTimes","from","rating","headUrl","videoName","desc","addressUrl","TelevisionId");
    }
    public String getObjectId() {
        if(!TextUtils.isEmpty(objectId)){
            return objectId;
        }
        return super.getObjectId();
    }
    @Override
    public boolean equals(Object obj) {
        String self=get("TelevisionId")+"";
        Televisionbean bean= (Televisionbean) obj;
        if(bean==null){
            return  false;
        }
        if(!TextUtils.isEmpty(self)){
            return self.equals(bean.get("TelevisionId"))||self.equals(bean.getTelevisionId());
        }else if(!TextUtils.isEmpty(getTelevisionId())){
            return getTelevisionId().equals(bean.getTelevisionId())||getTelevisionId().equals(bean.get("TelevisionId"));
        }
        return false;
    }

    public void setObjectId(String objectId) {
        this.objectId=objectId;
        super.setObjectId(objectId);
    }
    public Televisionbean toself(){
        Televisionbean bean=new Televisionbean();
        bean.setObjectId(getObjectId());
        bean.setFrom(AVUser.getCurrentUser().getObjectId());
        bean.setTelevisionId(getTelevisionId());
        bean.setTime(getTime());
        bean.setAddressUrl(getAddressUrl());
        bean.setDesc(getDesc());
        bean.setHeadUrl(getHeadUrl());
        bean.setNetPage(getNetPage());
        bean.setRating(getRating());
        bean.setVideoName(getVideoName());
        bean.setPlayTimes(getPlayTimes());
        return bean;
    }


    // KEEP METHODS END
// KEEP METHODS END

}
