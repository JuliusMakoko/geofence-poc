package com.poc.android.geofencepoc.model;

import android.database.Cursor;
import android.net.Uri;

import com.poc.android.geofencepoc.App;

import java.util.Date;

import static com.poc.android.geofencepoc.contentprovider.GeoFenceContentProvider.GEOFENCE_CONTENT_URI;
import static com.poc.android.geofencepoc.model.dao.DBHelper.GEOFENCES_ALL_COLUMNS;
import static com.poc.android.geofencepoc.model.dao.DBHelper.GEOFENCES_COLUMN_ID;

@SuppressWarnings("UnusedDeclaration")
public class GeoFence {
    private long id;
    private String name;
    private float latitude;
    private float longitude;
    private float radius;
    private Date createTime;
    private Date exitTime;

    public GeoFence() {}

    public GeoFence(long id) throws ModelException {
        Uri uri = Uri.parse(GEOFENCE_CONTENT_URI + "/" + id);
        load(uri);
    }

    public GeoFence(Uri uri) throws ModelException {
        load(uri);
    }

    public GeoFence(Cursor cursor) throws ModelException {
        load(cursor, false);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }

    public static GeoFence findLatestGeoFence() throws ModelException {
        GeoFence geoFence = null;

        Cursor cursor = App.context.getContentResolver().query(
                GEOFENCE_CONTENT_URI,
                GEOFENCES_ALL_COLUMNS,
                null,
                null,
                GEOFENCES_COLUMN_ID + " desc"
        );

        if (cursor != null) {
            cursor.moveToFirst();
            geoFence = new GeoFence();
            geoFence.load(cursor, true);
        }

        return geoFence;
    }

    @Override
    public String toString() {
        return "GeoFence{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", radius=" + radius +
                ", createTime=" + createTime +
                ", exitTime=" + exitTime +
                '}';
    }

    private void load(Uri uri) throws ModelException {
        Cursor cursor = App.context.getContentResolver().query(
                uri,
                GEOFENCES_ALL_COLUMNS,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        load(cursor, true);
    }

    private void load(Cursor cursor, boolean closeCursor) throws ModelException {
        if (cursor != null && cursor.getCount() > 0) {
            setId(cursor.getLong(0));
            setName(cursor.getString(1));
            setLatitude(cursor.getFloat(2));
            setLongitude(cursor.getFloat(3));
            setRadius(cursor.getFloat(4));
            setCreateTime(new Date(cursor.getLong(5)));
            setExitTime(new Date(cursor.getLong(6)));
            if (closeCursor) cursor.close();
        } else {
            throw new ModelException("unable to load: cursor null or count is 0");
        }
    }

}
