package com.powerhouse.assignment.model;


import com.powerhouse.assignment.exception.ValidationException;
import com.powerhouse.assignment.util.Utils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

public class ModelTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testFractionSizeNot12() throws ValidationException {
        thrown.expectMessage("Size of fraction should be 12 for profile: A");
        Profile profile = new Profile("A");
        profile.addFraction(new Fraction("Jan", 0.16));
        profile.addFraction(new Fraction("Feb", 0.15));

        Assert.assertTrue(profile.isValid());

    }

    @Test
    public void testFractionSumNotEqualTo1() throws ValidationException {
        thrown.expectMessage("Sum of fraction should be equal to 1");
        Profile profile = new Profile("A");
        profile.addFraction(new Fraction("Jan", 0.16));
        profile.addFraction(new Fraction("Feb", 0.15));
        profile.addFraction(new Fraction("Mar", 0.13));
        profile.addFraction(new Fraction("Apr", 0.13));
        profile.addFraction(new Fraction("May", 0.12));
        profile.addFraction(new Fraction("Jun", 0.09));
        profile.addFraction(new Fraction("Jul", 0.07));
        profile.addFraction(new Fraction("Aug", 0.05));
        profile.addFraction(new Fraction("Sep", 0.04));
        profile.addFraction(new Fraction("Oct", 0.03));
        profile.addFraction(new Fraction("Nov", 0.01));
        profile.addFraction(new Fraction("Dec", 0.01));

        Assert.assertTrue(profile.isValid());

    }

    @Test
    public void testProfileValid() throws ValidationException {
        Profile profile = new Profile("A");
        profile.addFraction(new Fraction("Jan", 0.16));
        profile.addFraction(new Fraction("Feb", 0.15));
        profile.addFraction(new Fraction("Mar", 0.13));
        profile.addFraction(new Fraction("Apr", 0.13));
        profile.addFraction(new Fraction("May", 0.12));
        profile.addFraction(new Fraction("Jun", 0.09));
        profile.addFraction(new Fraction("Jul", 0.07));
        profile.addFraction(new Fraction("Aug", 0.05));
        profile.addFraction(new Fraction("Sep", 0.04));
        profile.addFraction(new Fraction("Oct", 0.03));
        profile.addFraction(new Fraction("Nov", 0.02));
        profile.addFraction(new Fraction("Dec", 0.01));

        Assert.assertTrue(profile.isValid());

    }

    @Test
    public void testMeterReadingSizeNot12() throws ValidationException {

        thrown.expectMessage("Size of meter reading should be 12 for profile: A");
        Connection connection = new Connection("001", "A");
        connection.addMeterReading(new MeterReading("Jan", 10));
        connection.addMeterReading(new MeterReading("Feb", 11));
        connection.isValid();

    }

    @Test
    public void testMeterReadingLowerFromPrevMonth() throws ValidationException {
        thrown.expectMessage("For Profile: A Reading for month: Aug is less than previous month");
        Connection connection = new Connection("001", "A");
        connection.addMeterReading(new MeterReading("Jan", 10));
        connection.addMeterReading(new MeterReading("Aug", 11));
        connection.addMeterReading(new MeterReading("Mar", 12));
        connection.addMeterReading(new MeterReading("Apr", 13));
        connection.addMeterReading(new MeterReading("May", 14));
        connection.addMeterReading(new MeterReading("Jun", 15));
        connection.addMeterReading(new MeterReading("Jul", 16));
        connection.addMeterReading(new MeterReading("Feb", 11));
        connection.addMeterReading(new MeterReading("Sep", 19));
        connection.addMeterReading(new MeterReading("Dec", 9));
        connection.addMeterReading(new MeterReading("Oct", 20));
        connection.addMeterReading(new MeterReading("Nov", 21));
        connection.isValid();

    }


    @Test
    public void testProfileDataContainsConnection(){
        Profile profile1 = new Profile("A");
        profile1.addFraction(new Fraction("Jan", 0.12));
        profile1.addFraction(new Fraction("Feb", 0.32));

        Profile profile2 = new Profile("B");
        profile2.addFraction(new Fraction("Jan", 0.12));
        profile2.addFraction(new Fraction("Mar", 0.42));

        List<Profile> list = new ArrayList<>();
        list.add(profile1);
        list.add(profile2);

        Connection connection1 = new Connection("001", "A");
        connection1.addMeterReading(new MeterReading("Jan", 10));
        connection1.addMeterReading(new MeterReading("Feb", 11));

        Assert.assertTrue(list.contains(connection1));

        Connection connection2 = new Connection("002", "B");
        connection2.addMeterReading(new MeterReading("Feb", 10));
        connection2.addMeterReading(new MeterReading("Mar", 11));

        Assert.assertFalse(list.contains(connection2));

    }

    @Test
    public void testIsValidConsumption() throws ValidationException {

        thrown.expectMessage("Invalid consumption for profile: A: in the month of: Feb");
        Connection connection = new Connection("001", "A");
        connection.addMeterReading(new MeterReading("Jan", 14));
        connection.addMeterReading(new MeterReading("Feb", 15));
        connection.addMeterReading(new MeterReading("Mar", 16));
        connection.addMeterReading(new MeterReading("Apr", 17));
        connection.addMeterReading(new MeterReading("May", 18));
        connection.addMeterReading(new MeterReading("Jun", 19));
        connection.addMeterReading(new MeterReading("Jul", 20));
        connection.addMeterReading(new MeterReading("Aug", 21));
        connection.addMeterReading(new MeterReading("Sep", 22));
        connection.addMeterReading(new MeterReading("Oct", 24));
        connection.addMeterReading(new MeterReading("Nov", 25));
        connection.addMeterReading(new MeterReading("Dec", 29));


        Profile profile = new Profile("A");
        profile.addFraction(new Fraction("Jan", 0.07));
        profile.addFraction(new Fraction("Feb", 0.2));
        profile.addFraction(new Fraction("Mar", 0.13));
        profile.addFraction(new Fraction("Apr", 0.13));
        profile.addFraction(new Fraction("May", 0.12));
        profile.addFraction(new Fraction("Jun", 0.09));
        profile.addFraction(new Fraction("Jul", 0.07));
        profile.addFraction(new Fraction("Aug", 0.05));
        profile.addFraction(new Fraction("Sep", 0.04));
        profile.addFraction(new Fraction("Oct", 0.03));
        profile.addFraction(new Fraction("Nov", 0.02));
        profile.addFraction(new Fraction("Dec", 0.01));

        Utils.isValidConsumption(profile, connection);
    }

}
