package com.jaalee.sdk.service;

import android.os.Parcel;
import android.os.Parcelable;
import com.jaalee.sdk.Region;
import com.jaalee.sdk.internal.Objects;
import com.jaalee.sdk.internal.Preconditions;
/**
 * http://www.jaalee.com/
 * Jaalee, Inc.
 * This project is for developers, not for commercial purposes.
 * For the source codes which can be  used for commercial purposes, please contact us directly.
 * 
 * @author Alvin.Bert
 * Alvin.Bert.hu@gmail.com
 * 
 * service@jaalee.com
 */
public class MonitoringResult
  implements Parcelable
{
	public final Region region;
	public final Region.State state;
	public static final Parcelable.Creator<MonitoringResult> CREATOR = new Parcelable.Creator<MonitoringResult>()
	{
		public MonitoringResult createFromParcel(Parcel source) {
	       ClassLoader classLoader = getClass().getClassLoader();
	       Region region = (Region)source.readParcelable(classLoader);
	       Region.State event = Region.State.values()[source.readInt()];
	       return new MonitoringResult(region, event);
		}

		public MonitoringResult[] newArray(int size)
		{
			return new MonitoringResult[size];
		}
	};

	public MonitoringResult(Region region, Region.State state)
	{
		this.region = ((Region)Preconditions.checkNotNull(region, "region cannot be null"));
		this.state = ((Region.State)Preconditions.checkNotNull(state, "state cannot be null"));
	}

	public boolean equals(Object o)
	{
		if (this == o) return true;
		if ((o == null) || (getClass() != o.getClass())) return false;

		MonitoringResult that = (MonitoringResult)o;

		if (this.state != that.state) return false;
		if (this.region != null ? !this.region.equals(that.region) : that.region != null) return false;

		return true;
	}

	public int hashCode()
	{
		int result = this.region != null ? this.region.hashCode() : 0;
		result = 31 * result + (this.state != null ? this.state.hashCode() : 0);
		return result;
	}

	public String toString()
	{
		return Objects.toStringHelper(this).add("region", this.region).add("state", this.state.name()).toString();
	}

	public int describeContents()
	{
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeParcelable(this.region, flags);
		dest.writeInt(this.state.ordinal());
	}
}
