package client.utilities;

public class GoogleMap {
    public static String getHtml(String geoLocation, int zoom, String title) {

        return "<html>\n" +
                "<head>\n" +
                "    <meta name='viewport' content='initial-scale=1.0, user-scalable=no'/>\n" +
                "    <style type='text/css'>\n" +
                "        html {\n" +
                "            height: 100%\n" +
                "        }\n" +
                "\n" +
                "        body {\n" +
                "            height: 100%;\n" +
                "            margin: 0px;\n" +
                "            padding: 0px\n" +
                "        }\n" +
                "\n" +
                "        #map_canvas {\n" +
                "            height: 100%;\n" +
                "            background-color: #666970;\n" +
                "        }\n" +
                "    </style>\n" +
                "    <script type='text/javascript' src='http://maps.google.com/maps/api/js?sensor=false'></script>\n" +
                "    <script type='text/javascript'>\n" +
                "        function initialize() {\n" +
                "            var myLatlng = new google.maps.LatLng(" + geoLocation + ");\n" +
                "            var myOptions = {\n" +
                "                zoom: " + String.valueOf(zoom) + ",\n" +
                "                center: myLatlng,\n" +
                "                mapTypeId: google.maps.MapTypeId.HYBRID\n" +
                "            }\n" +
                "            maps = new google.maps.Map(document.getElementById('map_canvas'), myOptions);\n" +
                "            addMarker(myLatlng, maps);\n" +
                "        }\n" +
                "        function addMarker(location, maps) {\n" +
                "            marker = new google.maps.Marker({\n" +
                "                position: location,\n" +
                "                map: maps,\n" +
                "                title: '" + title + "'\n" +
                "            });\n" +
                "        }    </script>\n" +
                "</head>\n" +
                "<body onload='initialize()'>\n" +
                "<div id='map_canvas' style='width:100%; height:100%'></div>\n" +
                "</body>\n" +
                "</html>";
    }
}
