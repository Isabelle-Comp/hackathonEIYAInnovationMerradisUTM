package com.hackthon.service.serviceInter;

public interface GeoLocationService {
    double[] extractCoordinates(String googleMapsLink);
    double distance(double lat1, double lon1, double lat2, double lon2);
}
