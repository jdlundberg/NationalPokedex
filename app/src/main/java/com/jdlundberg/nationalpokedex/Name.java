package com.jdlundberg.nationalpokedex;

/**
 * Created by Architect on 3/14/2015.
 */
public class Name {

    private long id;
    private String name;

    public Name() {

        id = 0;
        name = "";

    }

    public long getID() {

        return id;

    }

    public void setID(long id) {

        this.id = id;

    }

    public String getName() {

        return name;
    }

    public void setName() {

        this.name = name;

    }

    @Override
    public String toString() {

        return name;

    }

}
