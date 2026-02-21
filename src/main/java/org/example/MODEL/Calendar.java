package org.example.MODEL;

import org.example.MODEL.Event.Event;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;

public class Calendar {
    private ArrayList<Event> events;

    final static private String OUTPUT_FILE_NAME = "output.csv";
    final static String outputFormat = "Subject,Start Date,Start Time,End Date,End Time,All Day Event,Description,Location";

    public Calendar() {
        events = new ArrayList<Event>();
    }

    public Event addEvent(Event event) {
        events.add(event);

        return event;
    }

    public ArrayList<Event> getEvents() {
        return this.events;
    }

    public File generateCSV() {
        File outputFile = new File(OUTPUT_FILE_NAME);

        try (PrintWriter writer = new PrintWriter(outputFile);) {
            writer.println(outputFormat); // header

            for(Event event : events) {
                writer.println(event.convertToCSVLine());
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        // print out resulting .csv file contents to see if it actually worked
//        try {
//            Files.lines(outputFile.toPath())
//                    .forEach(System.out::println);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        return outputFile;
    }
}
