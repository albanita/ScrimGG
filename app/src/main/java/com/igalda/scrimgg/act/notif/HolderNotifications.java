package com.igalda.scrimgg.act.notif;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.igalda.scrimgg.R;

public class HolderNotifications extends RecyclerView.ViewHolder {

    private TextView notifTitle;
    private TextView notifText;
    private TextView notifTime;

    public HolderNotifications (View itemView){
        super(itemView);

        notifTitle = (TextView) itemView.findViewById(R.id.tvNotificationTitle);
        notifText = (TextView) itemView.findViewById(R.id.tvNotificationText);
        notifTime = (TextView) itemView.findViewById(R.id.tvNotificationTime);
    }

    public TextView getNotifTitle() {
        return notifTitle;
    }

    public void setNotifTitle(TextView notifTitle) {
        this.notifTitle = notifTitle;
    }

    public TextView getNotifText() {
        return notifText;
    }

    public void setNotifText(TextView notifText) {
        this.notifText = notifText;
    }

    public TextView getNotifTime() {
        return notifTime;
    }

    public void setNotifTime(TextView notifTime) {
        this.notifTime = notifTime;
    }
}
