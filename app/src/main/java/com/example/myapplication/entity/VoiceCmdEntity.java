package com.example.myapplication.entity;

import java.util.List;

public class VoiceCmdEntity {


    /**
     * code : 200
     * data : {"play":["$P(_PLAY)"],"next":["下一个","下一期","下一集"],"previous":["上一个","上一期","上一集"],"skipHead":["跳过片头"],"forward":["快进","$P(_PLAY)"],"back":["返回","退出","退出全屏"],"backward":["快退","$P(_PLAY)"],"episode":["$P(_EPISODE)"],"fill":["全屏","全屏播放","最大化","放大"],"skipTail":["跳过片尾"]}
     * message : 请求成功
     */

    private int code;
    private DataBean data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        private List<String> play;
        private List<String> next;
        private List<String> previous;
        private List<String> skipHead;
        private List<String> forward;
        private List<String> back;
        private List<String> backward;
        private List<String> episode;
        private List<String> fill;
        private List<String> skipTail;

        public List<String> getPlay() {
            return play;
        }

        public void setPlay(List<String> play) {
            this.play = play;
        }

        public List<String> getNext() {
            return next;
        }

        public void setNext(List<String> next) {
            this.next = next;
        }

        public List<String> getPrevious() {
            return previous;
        }

        public void setPrevious(List<String> previous) {
            this.previous = previous;
        }

        public List<String> getSkipHead() {
            return skipHead;
        }

        public void setSkipHead(List<String> skipHead) {
            this.skipHead = skipHead;
        }

        public List<String> getForward() {
            return forward;
        }

        public void setForward(List<String> forward) {
            this.forward = forward;
        }

        public List<String> getBack() {
            return back;
        }

        public void setBack(List<String> back) {
            this.back = back;
        }

        public List<String> getBackward() {
            return backward;
        }

        public void setBackward(List<String> backward) {
            this.backward = backward;
        }

        public List<String> getEpisode() {
            return episode;
        }

        public void setEpisode(List<String> episode) {
            this.episode = episode;
        }

        public List<String> getFill() {
            return fill;
        }

        public void setFill(List<String> fill) {
            this.fill = fill;
        }

        public List<String> getSkipTail() {
            return skipTail;
        }

        public void setSkipTail(List<String> skipTail) {
            this.skipTail = skipTail;
        }
    }
}
