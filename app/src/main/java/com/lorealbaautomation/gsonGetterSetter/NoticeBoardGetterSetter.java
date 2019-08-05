
package com.lorealbaautomation.gsonGetterSetter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NoticeBoardGetterSetter {

    @Expose
    private List<NoticeBoard> noticeBoard = null;

    public List<NoticeBoard> getNoticeBoard() {
        return noticeBoard;
    }

    public void setNoticeBoard(List<NoticeBoard> noticeBoard) {
        this.noticeBoard = noticeBoard;
    }

}
