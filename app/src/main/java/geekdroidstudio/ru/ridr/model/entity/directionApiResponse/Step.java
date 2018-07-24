package geekdroidstudio.ru.ridr.model.entity.directionApiResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step {
        @SerializedName("distance")
        @Expose
        private ApiObject distance;
        @SerializedName("duration")
        @Expose
        private ApiObject duration;
        @SerializedName("end_location")
        @Expose
        private Point endLocation;
        @SerializedName("html_instructions")
        @Expose
        private String htmlInstructions;
        @SerializedName("polyline")
        @Expose
        private Polyline polyline;
        @SerializedName("start_location")
        @Expose
        private Point startLocation;
        @SerializedName("travel_mode")
        @Expose
        private String travelMode;
        @SerializedName("maneuver")
        @Expose
        private String maneuver;

        public ApiObject getDistance() {
            return distance;
        }

        public void setDistance(ApiObject distance) {
            this.distance = distance;
        }

        public ApiObject getDuration() {
            return duration;
        }

        public void setDuration(ApiObject duration) {
            this.duration = duration;
        }

        public Point getEndLocation() {
            return endLocation;
        }

        public void setEndLocation(Point endLocation) {
            this.endLocation = endLocation;
        }

        public String getHtmlInstructions() {
            return htmlInstructions;
        }

        public void setHtmlInstructions(String htmlInstructions) {
            this.htmlInstructions = htmlInstructions;
        }

        public Polyline getPolyline() {
            return polyline;
        }

        public void setPolyline(Polyline polyline) {
            this.polyline = polyline;
        }

        public Point getStartLocation() {
            return startLocation;
        }

        public void setStartLocation(Point startLocation) {
            this.startLocation = startLocation;
        }

        public String getTravelMode() {
            return travelMode;
        }

        public void setTravelMode(String travelMode) {
            this.travelMode = travelMode;
        }

        public String getManeuver() {
            return maneuver;
        }

        public void setManeuver(String maneuver) {
            this.maneuver = maneuver;
        }
}
