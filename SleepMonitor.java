package FitnessTracker;

import java.io.*;
import java.util.*;

/**
 * Created by Emanuele Tattolo on 06/12/2020
 * The program helps user to track his sleeps, writing and reading on file.
 **/

public class SleepMonitor extends Fitter {

   //variables all private as they will be shared only in the class
   private int sleepHour, sleepMinute, sleepGoal;
   private int wakeHour, wakeMinute;
   private int hour, minute, hourApprox;
   private String level = null;
   private int hourTop = 0, hourAverage = 0;

   private int days; //variable used as index in the array of object
   private SleepMonitor [] sleepList; //array of objects



   public SleepMonitor() {} // Default constructor
   public SleepMonitor(String name, int age) {
      super(name, age);
      this.sleepHour = sleepHour;
      this.sleepMinute = sleepMinute;
      this.wakeHour = wakeHour;
      this.wakeMinute = wakeMinute;
   } //constructor when getData() is invoked
   public SleepMonitor(String name, int age, int sleepHour, int sleepMinute, int wakeHour, int wakeMinute) {
      super(name, age);
      this.sleepHour = sleepHour;
      this.sleepMinute = sleepMinute;
      this.wakeHour = wakeHour;
      this.wakeMinute = wakeMinute;
      sleepTime();
      hourApprox = getHourApprox();
      sleepEfficiency();
      goalTracker();
   } //constructor for array of objects

   protected void getData() {
      System.out.println("\nPlease add your sleep-time for the day here.");

      System.out.println("What time did you go to sleep? ");
      System.out.print("Hours 0-24: ");
      sleepHour = keyboard.nextInt();
      System.out.print("Minutes: ");
      sleepMinute = keyboard.nextInt();
      System.out.println("What time did you wake up? ");
      System.out.print("Hours 0-24: ");
      wakeHour = keyboard.nextInt();
      System.out.print("Minutes: ");
      wakeMinute = keyboard.nextInt();
      sleepTime();
      hourApprox = getHourApprox();
      sleepGoal = setSleepGoal();//evaluate if it could go in askData()
      sleepEfficiency();
      goalTracker();

   } //unique method to collect data from user required from the constructor and saved into file
   private void sleepTime() {
      final int SECMINHOUR = 60;
      final int HOURSINDAY = 24;

      minute = wakeMinute - sleepMinute;
      hour = wakeHour - sleepHour;

      if (minute < 0)
      {
         minute = minute + SECMINHOUR;
         hour = hour - 1;
      }//if
      if (hour < 0)
      {
         hour = hour + HOURSINDAY;
      }//if
   } //setter to calculate sleep hours and minutes
   private int getHourApprox(){
      if (minute >= 30)
         return hourApprox = hour+1;
      else
         return hourApprox = hour;
   } //getter for approximate minutes into hour
   private int getHourApprox(int hour, int minute) {
      this.hour = hour;
      this.minute = minute;
      if (minute >= 30)
         return hourApprox = hour+1;
      else
         return hourApprox = hour;
   }
   private int setSleepGoal() {
      System.out.print("How many hours per night is your target? ");
      return sleepGoal = keyboard.nextInt();
   } //getter for asking the sleep goal to user only once
   private int goalTracker() {
      return (int)Math.round(hourApprox * 100.0/ sleepGoal);
   }
   private void sleepEfficiency() {
      int age = super.getAge(); //create an array?
      String top = "great";
      String average = "not optimal but average";
      String low = "poor";

      if (age >= 65) {
         if (hourApprox >= 7)
         {
            level = top;
            hourTop = 7;
            hourAverage = 5;
         }
         else if (hourApprox >= 5)
         {
            level = average;
            hourTop = 7;
            hourAverage = 5;
         }
         else
            level = low;
            hourTop = 7;
            hourAverage = 5;}
      else if (age >= 18) {
         if (hourApprox >= 8) {
            level = top;
            hourTop = 8;
            hourAverage = 6;}
         else if (hourApprox >= 6) {
            level = average;
            hourAverage = 6;
            hourTop = 8;}
         else
            level = low;
            hourAverage = 6;
            hourTop = 8;}
      else {
         if (hourApprox >= 10) {
            level = top;
            hourTop = 10;
            hourAverage = 8;}
         else if (hourApprox >= 8) {
            level = average;
            hourAverage = 8;
            hourTop = 10;
         }
         else
            level = low;
            hourAverage = 8;
            hourTop = 10; }
   } //setter for calculating sleep efficiency
   public String toString() {
      String data;
      data = "\nOk " + super.getName() + ", you slept a total of " + df2.format(hour) + ":" + df3.format(minute) + " hours.\n" +
              "Based on your sleep gol of " + sleepGoal + " hours, you are " + goalTracker() + "% on target.\n"
              + "For a " + super.getAge() + " year old, this is " + level + ".";
      return data;
   } //toString with main text
   protected String getNSFtext() {
      String text;
      System.out.print("Would you like to know more? ");
      char input = keyboard.next().charAt(0);
      if (input == 'y' || input == 'Y')
         text = "\nNice! For a " + super.getAge() + " year old, the National Sleep Foundation recommends from " + hourAverage + " to " +
                 hourTop + " hours of total sleep per day.\nHowever, the NSF recommends against getting more than " +
                 hourTop + " hours or less than " + hourAverage + " hours in a single day for a " + super.getAge() + " year old.\n";
      else
         text = "";
      return text;
   } //getter with further text

   protected void saveSleeps() {
      PrintWriter sleepfile = null;
      String FileName = "sleeps.txt";
      FileReader myFileName;
      Scanner readMyFile = null;
      boolean open;

      try { //exception handling for FileReader
         myFileName = new FileReader("sleeps.txt"); //reader for counting lines for variable "days"
         readMyFile = new Scanner(myFileName);
      }
      catch (FileNotFoundException error) {
         System.out.println("Cannot open input file ");
         System.out.println(error.getMessage());
      }

      try {
         // very important while loop to read into file and counting the lines of sleeps for determining the
         // number of objects and array's size. Days is initialized from 0 (and will stay 0 in case the file is empty).
         // it is important the reading for the variable is done before saving it
         while (readMyFile.hasNextLine()) {
            String emptyLine = readMyFile.nextLine();
            days++; } //read the days
      } catch (Exception error) {
         System.out.println("Error reading from file");
         System.out.println("Exception " + error.getMessage() + " caught");
      }

      System.out.print("Would you like to save the day sleep? "); // ask user if he wants to save the data
      char input = keyboard.next().charAt(0);
      if (input == 'y' || input == 'Y') {
         System.out.println("It will be saved as day " + (days + 1) + ".");
         try //exception handling for writing the file
         {  //PrintWriter with BufferedWriter to do not overwrite
            sleepfile = new PrintWriter(new BufferedWriter(new FileWriter("sleeps.txt", true)));
            open = true;
         } catch (FileNotFoundException error)
         {
            System.out.println("Error opening the file");
            open = false;
         } catch (IOException e)
         {
            e.printStackTrace();
         }

         try
         {
            if (open = true)
            {  //writing on file only the variables for objects parameters
               sleepfile.println(sleepHour + " " + sleepMinute + " " + wakeHour + " " +  wakeMinute);
               sleepfile.close();
               open = false;
               System.out.print("Successfully saved. ");
               days++;

               pressEnter();

            }
         } catch (Exception error)
         {
            System.out.println("Exception " + error.getMessage() + " caught");
         }
      }
   } //this method allows to save sleep-day data by writing over file
   protected void readSleeps(){
      FileReader myFileName;
      Scanner readMyFile = null;
      sleepList = new SleepMonitor[days]; // array of objects

      try {
      myFileName = new FileReader("sleeps.txt");
      readMyFile = new Scanner(myFileName);
      }
      catch (FileNotFoundException error) {
         System.out.println("Cannot open input file ");
         System.out.println(error.getMessage());
      }

      try {
         // for loop and array of objects creation reading from file
         for (int index = 0; index < days; index++) {
               sleepHour = readMyFile.nextInt();
               sleepMinute = readMyFile.nextInt();
               wakeHour = readMyFile.nextInt();
               wakeMinute = readMyFile.nextInt();
               String name = super.getName(); int age = super.getAge();
               sleepList[index] = new SleepMonitor(name, age, sleepHour, sleepMinute, wakeHour, wakeMinute);
         }
      } catch (Exception error) {
         System.out.println(error.getMessage());
      }//catch
   } //this method allows to read sleep data from file and creates an array of objects

   protected void arraysSleeps() {
      int choice;
      readSleeps();
      do
      {  System.out.print("\nCheck some interesting fact about your sleep "
              + super.getName() + "\n" +
              "Choose one of the options or 8 to exit\n" +
              "1. Read data on a sleep day\n" +
              "2. Average sleep time last 3 days\n" +
              "3. Average sleep time last 10 days\n" +
              "4. Earliest wake up day so far\n" +
              "5. Latest wake up day so far\n" +
              "6. Earliest night so far\n" +
              "7. Latest night so far\n" +
              "8. Exit\n" +
              "Your choice: ");
         choice = keyboard.nextInt();

         switch (choice)
         {
            case 1:
               readAdaySleep();
               pressEnter();
               break;
            case 2:
               System.out.println("\nIn the last 3 days, you slept an average of " + average3days() + " hours.");
               pressEnter();
               break;
            case 3:
               System.out.println("\nIn the last 10 days, you slept an average of " + average10days() + " hours.");
               pressEnter();
               break;
            case 4:
               earliestDay();
               pressEnter();
               break;
            case 5:
               latestDay();
               pressEnter();
               break;
            case 6:
               earliestNight();
               pressEnter();
               break;
            case 7:
               latestNight();
               pressEnter();
               break;
            default:
               if (choice == 8)
                  System.out.println("");
               else
                  System.out.println("Wrong choice");
         }
      } while (choice != 8);
      System.out.println("\nThank you for using sleep monitor " + super.getName() +
              "!\nPlease don't forget to have a proper sleep tonight :) \nSee you again tomorrow for a new day!\n");

      pressEnter();

   } //method interface with switch

   private void pressEnter() {
      System.out.println("Press Enter key to continue");
      try
      {
         System.in.read();
      } catch (Exception e)
      {
      }
   }
   private void readAdaySleep() {
      int day = 0; int choice;

      System.out.print("Please enter the day you would like to know the data: ");
      choice = keyboard.nextInt();

      for (int i = 0; i <= days; i++) {
         if (i == (choice - 1)) {
            day = i;
         }
      }

      hourApprox = sleepList[day].getHourApprox(sleepList[day].hour, sleepList[day].minute);
      System.out.print("\nDay " + choice + " sleep data: \n" +
              "-------------------\n" +
              "You went to sleep at " + df3.format(sleepList[day].sleepHour) + ":" + df3.format(sleepList[day].sleepMinute) +
              " and wake up at " + df3.format(sleepList[day].wakeHour) + ":" + df3.format(sleepList[day].wakeMinute) +
              ".\nYou slept a total of " + df2.format(sleepList[day].hour) + ":" + df3.format(sleepList[day].minute) +
              " hours.\n" + "Based on your sleep gol of " + sleepGoal + " hours, you are "
              + goalTracker() + "% on target.\n"
              + "For a " + super.getAge() + " year old, this is " + sleepList[day].level + ".\n");
   } //method for reading from the file for 1 specific day
   private int average3days() {
      int sum = 0;
      for (int i = days - 1; i > days - 4; i--) {
         sum += getHourApprox(sleepList[i].hour, sleepList[i].minute);
      }
      return sum / 3;


   } //method for reading the last 3 days and calculate average
   private int average10days() {
   int sum = 0;
   for (int i = days - 1; i > days - 11; i--) {
      sum += getHourApprox(sleepList[i].hour, sleepList[i].minute);
   }
   return sum / 10;
   } //average 10 days
   private void earliestDay() {
      int earliestHour = sleepList[0].wakeHour, count = 0, index = 0;
      int earliestMinute = sleepList[0].wakeMinute;


      for (int i = 0; i < days; i++) {
         if (sleepList[i].wakeHour < earliestHour) {
            earliestHour = sleepList[i].wakeHour;
            }
      }

      for (int i = 0; i < days; i++) {
         if (sleepList[i].wakeHour == earliestHour) {
            if (sleepList[0].wakeHour == earliestHour) {
               if (sleepList[0].wakeMinute < earliestMinute)
                  earliestMinute = sleepList[0].wakeMinute; }
            else {
               earliestMinute = sleepList[i].wakeMinute;
               for (i = 0; i < days; i++)
               {  if (sleepList[i].wakeHour == earliestHour)
                     if (sleepList[i].wakeMinute < earliestMinute)
                        earliestMinute = sleepList[i].wakeMinute;
               }
            }
         }
      }

      while (index < days) {
         if (sleepList[index].wakeHour == earliestHour && sleepList[index].wakeMinute == earliestMinute) {
            count = index;
         }//if
         index = index + 1;
      }//while



      System.out.println("In "+ days + " days, you woke up the earliest in day " + (count + 1)
              + ". You early bird!\nThe alarm went off at " + df2.format(earliestHour) + ":"
              + df3.format(earliestMinute));
   } //method for finding the earliest wake up day
   private void latestDay() {
      int latestHour = sleepList[0].wakeHour, count = 0, index = 0;
      int latestMinute = sleepList[0].wakeMinute;


      for (int i = 0; i < days; i++) {
         if (sleepList[i].wakeHour > latestHour) {
            latestHour = sleepList[i].wakeHour;
         }
      }

      for (int i = 0; i < days; i++) {
         if (sleepList[i].wakeHour == latestHour)
            if (sleepList[0].wakeHour == latestHour)
               if (sleepList[0].wakeMinute > latestMinute)
                  latestMinute = sleepList[0].wakeMinute;
            else {
                  latestMinute = sleepList[i].wakeMinute;
                  for (i = 0; i < days; i++)
                  { if (sleepList[i].wakeHour == latestHour)
                      if (sleepList[i].wakeMinute > latestMinute)
                         latestMinute = sleepList[i].wakeMinute;
                  }
            }
      }

      while (index < days) {
         if (sleepList[index].wakeHour == latestHour && sleepList[index].wakeMinute == latestMinute) {
            count = index;
         }//if
         index = index + 1;
      }//while



      System.out.println("In "+ days + " days, you woke up the latest in day " + (count + 1)
              + ". You sleepy head!\nYou put your feet down at " + df2.format(latestHour) + ":"
              + df3.format(latestMinute));
   } ////method for finding the latest wake up day
   private void earliestNight() {
      int earliestHour = sleepList[0].sleepHour, count = 0, index = 0;
      int earliestMinute = sleepList[0].sleepMinute;


      for (int i = 0; i < days; i++) {
         if (sleepList[i].sleepHour < earliestHour) {
            earliestHour = sleepList[i].sleepHour;
         }
      }

      for (int i = 0; i < days; i++) {
         if (sleepList[i].sleepHour == earliestHour)
            if (sleepList[0].sleepHour == earliestHour)
               if (sleepList[0].sleepMinute < earliestMinute)
                  earliestMinute = sleepList[0].sleepMinute;
            else
               earliestMinute = sleepList[i].sleepMinute;
               for (i = 0; i < days; i++) {
                    if (sleepList[i].sleepHour == earliestHour)
                       if (sleepList[i].sleepMinute < earliestMinute)
                         earliestMinute = sleepList[i].sleepMinute;
                  }

      }


      while (index < days) {
         if (sleepList[index].sleepHour == earliestHour && sleepList[index].sleepMinute == earliestMinute) {
            count = index;
         }//if
         index = index + 1;
      }//while



      System.out.println("In "+ days + " days, you went to sleep the earliest in day " + (count + 1)
              + ". You lark!\nYou were in bed at " + df2.format(earliestHour) + ":"
              + df3.format(earliestMinute));
   } //method for finding the earliest sleep day
   private void latestNight() {
      int latestHour = sleepList[0].sleepHour, count = 0, index = 0;
      int latestMinute = sleepList[0].sleepMinute;


      for (int i = 0; i < days; i++) {
         if (sleepList[i].sleepHour > latestHour) {
            latestHour = sleepList[i].sleepHour;
         }
      }

      for (int i = 0; i < days; i++) {
         if (sleepList[i].sleepHour == latestHour)
            if (sleepList[0].sleepHour == latestHour)
              if  (sleepList[0].sleepMinute > latestMinute)
               latestMinute = sleepList[0].sleepMinute;
            else {
               latestMinute = sleepList[i].sleepMinute;
               for (i = 0; i < days; i++){
                  if (sleepList[i].sleepHour == latestHour)
                     if (sleepList[i].sleepMinute > latestMinute)
                        latestMinute = sleepList[i].sleepMinute;
               }
            }
      }

      while (index < days) {
         if (sleepList[index].sleepHour == latestHour && sleepList[index].sleepMinute == latestMinute) {
            count = index;
         }//if
         index = index + 1;
      }//while

      System.out.println("In "+ days + " days, you went to sleep the latest in day " + (count + 1)
              + ". You night owl!\nYou went to bed at " + df2.format(latestHour) + ":"
              + df3.format(latestMinute));
   } //method for finding the latest sleep day
}//class

