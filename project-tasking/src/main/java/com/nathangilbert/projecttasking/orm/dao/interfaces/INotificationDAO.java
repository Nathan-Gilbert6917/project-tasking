package com.nathangilbert.projecttasking.orm.dao.interfaces;

import com.nathangilbert.projecttasking.orm.entity.Notification;

public interface INotificationDAO {

    // Create (Save) a new notification
    Notification save(Notification notification);

}
