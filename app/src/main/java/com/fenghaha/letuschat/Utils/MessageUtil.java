package com.fenghaha.letuschat.Utils;

import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;

/**
 * Created by FengHaHa on2018/8/23 0023 18:30
 */
public class MessageUtil {
 public static   boolean isFromMe(AVIMTypedMessage msg){
       String selfId = AVUser.getCurrentUser().getObjectId();
       return msg.getFrom() != null && msg.getFrom().equals(selfId);
   }
}
