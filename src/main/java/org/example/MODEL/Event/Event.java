package org.example.MODEL.Event;

public class Event {
    private EventType type;
    private String name;
    private int[] date;
    private double weight;
    private String description;
    private String formattedDate;

    public Event(int eventType, String name, int[] date, double weight, String description) {
        switch(eventType) {
            case 0:
                this.type = EventType.TEST;
                break;
            case 1:
                this.type = EventType.ASSIGNMENT;
                break;
            case 2:
                this.type = EventType.EXAM;
                break;
            case 3:
                this.type = EventType.QUIZ;
                break;
            case 4:
                this.type = EventType.LAB;
                break;
            case 5:
                this.type = EventType.OTHER;
                break;
            default:
                System.out.println("Error occurred, invalid event type (must be 0-5).");
                break;
        }
        this.name = name;
        this.date = date;
        this.weight = weight / 100;
        this.description = description;
        this.formattedDate = getFormattedDate(date);

        checkEvent(); // check invariants
    }

    private String getFormattedDate(int[] date) {
        // passed {day, month, year}
        String result = "";

        // get month
        switch(date[1]) {
            case 1:
                result += "January ";
                break;
            case 2:
                result += "February ";
                break;
            case 3:
                result += "March ";
                break;
            case 4:
                result += "April ";
                break;
            case 5:
                result += "May ";
                break;
            case 6:
                result += "June ";
                break;
            case 7:
                result += "July ";
                break;
            case 8:
                result += "August ";
                break;
            case 9:
                result += "September ";
                break;
            case 10:
                result += "October ";
                break;
            case 11:
                result += "November ";
                break;
            case 12:
                result += "December ";
                break;
            default:
                // something went wrong;
                break;
        }

        // get day
        result += date[0] + ", ";

        // get year
        result += date[2];

        return result;
    }

    //converts values into CSV readable files
    public String convertToCSVLine() {
        String dateString = getMonth() + this.date[0] + "/" + this.date[2];
        String weightString = String.format("Worth %.2f%% of final grade. ", this.weight * 100);
        return this.name + "," + dateString + ",," + dateString + ",,True," + weightString + this.description +",";
    }

    //gets month according to give value
    public String getMonth() {
        String dateString = "";
        switch (this.date[1]) {
            case 1:
                dateString = "01/";
                break;
            case 2:
                dateString = "02/";
                break;
            case 3:
                dateString = "03/";
                break;
            case 4:
                dateString = "04/";
                break;
            case 5:
                dateString = "05/";
                break;
            case 6:
                dateString = "06/";
                break;
            case 7:
                dateString = "07/";
                break;
            case 8:
                dateString = "08/";
                break;
            case 9:
                dateString = "09/";
                break;
            case 10:
                dateString = "10/";
                break;
            case 11:
                dateString = "11/";
                break;
            case 12:
                dateString = "12/";
                break;
            default:
                break;
        }
        return dateString; //returns formated month
    }

    private void checkEvent() {
        assert type != null : "Type must not be null.";
        assert name != null : "Name must not be null.";
        assert date != null : "Date must not be null.";
        assert weight >= 0 : "Event weight must be non-negative.";
        assert description != null : "Description must not be null.";
    }

    public String getEventInfo() { // print event info nicely.
        // TEST:
        // Name. Weighs x% of the final grade.
        // Description

        String result = "";

        result += this.type.toString() + ":\n";
        result += String.format("%s (%.2f%%) - %s",this.name, this.weight, this.formattedDate);

        if (!this.description.isEmpty()) { // if there is a description, add it
            result += String.format("\n%s", this.description);
        }

        return result;
    }

    // getters
    public String getType() {
        return type.toString();
    }
    public String getName() {
        return name;
    }
    public int[] getDate() {
        return date;
    }
    public double getWeight() {
        return weight;
    }
    public String getDescription() {
        return description;
    }
}
