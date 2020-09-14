package com.jaalee.sdk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jaalee.sdk.Beacon;
import com.jaalee.sdk.Utils;

import java.util.ArrayList;
import java.util.Collection;


/**
 * http://www.jaalee.com/
 * @author JAALEE, Inc.
 * We have been trying to provide better services and products! Jaalee Beacon makes
 * life more simple and cheerful! If you are interested in our product,
 * please contact us in following ways. We will provide the best service wholeheartedly for you!
 *
 * Buy Jaalee Beacon: sales@jaalee.com
 *
 * Technical Support: dev@jaalee.com
 *
 */
public class LeDeviceListAdapter {

  private ArrayList<Beacon> beacons;
  private LayoutInflater inflater;

  public LeDeviceListAdapter(Context context) {
    this.inflater = LayoutInflater.from(context);
    this.beacons = new ArrayList<Beacon>();
  }

  public void replaceWith(Collection<Beacon> newBeacons) {
    this.beacons.clear();
    this.beacons.addAll(newBeacons);
    // notifyDataSetChanged();
  }

  public int getCount() {
    return beacons.size();
  }

  public Beacon getItem(int position) {
    return beacons.get(position);
  }

  public long getItemId(int position) {
    return position;
  }

}
