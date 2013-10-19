package client;

/**
 * Created with IntelliJ IDEA.
 * User: Timface
 * Date: 19/10/13
 * Time: 11:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class GoogleMap {
    public static String getHtml(String geoLocation, int zoom, String title) {
        return "<html>" +
                "<head>" +
                "    <meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\"/>" +
                "    <style type=\"text/css\">" +
                "        html { height: 100% }" +
                "        body { height: 100%; margin: 0px; padding: 0px }" +
                "        #map_canvas { height: 100%; background-color: #666970; }" +
                "    </style>" +
                "    <script type=\"text/javascript\" src=\"http://maps.google.com/maps/api/js?sensor=false\">" +
                "    </script>" +
                "    <script type=\"text/javascript\">" +
                "" +
                "        // Standard google maps function" +
                "        function initialize() {" +
                "        var myLatlng = new google.maps.LatLng(" + geoLocation + ");" +
                "        var myOptions = {" +
                "        zoom: " + String.valueOf(zoom) + "," +
                "        center: myLatlng," +
                "        mapTypeId: google.maps.MapTypeId.SATELLITE" +
                "        }" +
                "        map = new google.maps.Map(document.getElementById(\"map_canvas\"), myOptions);" +
                "        addMarker(myLatlng, map);" +
                "        }" +
                "" +
                "        // Function for adding a marker to the page." +
                "        function addMarker(location, map) {" +
                "        marker = new google.maps.Marker({" +
                "        position: location," +
                "        map: map," +
                "        title: '" + title + "'" +
                "        });" +
                "        }" +
                "" +
                "" +
                "" +
                "    </script>" +
                "</head>" +
                "<body onload='initialize()'>" +
                "<div id=\"map_canvas\" style=\"width:100%; height:100%\"></div>" +
                "</body>" +
                "</html>";
    }
}
